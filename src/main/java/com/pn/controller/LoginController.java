package com.pn.controller;

import com.google.code.kaptcha.Producer;
import com.pn.entity.*;
import com.pn.service.AuthService;
import com.pn.service.UserService;
import com.pn.utils.DigestUtil;
import com.pn.utils.TokenUtils;
import com.pn.utils.WarehouseConstants;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
public class LoginController {
//    注入
    @Resource(name = "captchaProducer")
    private Producer producer;

    @Autowired
    private StringRedisTemplate redisTemplate;
    @RequestMapping("/captcha/captchaImage")
    public void captchaImage(HttpServletResponse response){
        ServletOutputStream out = null;
        try{

    //           生成验证码图片的文件
            String text = producer.createText() ;
    //           使用验证码文本生成验证码图片
            BufferedImage image = producer.createImage(text);
    //        将验证码文本作为键保存到redis---并设置过期时间为30分钟
            redisTemplate.opsForValue().set(text,"",1800, TimeUnit.SECONDS);
    //        将验证码图片相应给前端
            response.setContentType("image/jpeg");
            out = response.getOutputStream();
            ImageIO.write(image,"jpg",out);
//            刷新
            out.flush();
        } catch (IOException e){
            e.printStackTrace();
        } finally {

//            关闭字节流输出
            if(out!=null){
                try {
                    out.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    @Autowired
    private UserService userService;
    @Autowired
    private TokenUtils tokenUtils;




    @RequestMapping("/login")
    public Result login(@RequestBody LoginUser loginUser){

        String code = loginUser.getVerificationCode();
        if(!redisTemplate.hasKey(code)){
         return  Result.err(Result.CODE_ERR_BUSINESS,"验证码错误");
        }
        User user = userService.queryUserByCode(loginUser.getUserCode());
        if(user!=null){
            if(user.getUserState().equals(WarehouseConstants.USER_STATE_PASS)){
                String userPwd = loginUser.getUserPwd();
                userPwd = DigestUtil.hmacSign(userPwd);
                if(userPwd.equals(user.getUserPwd())){
                    CurrentUser currentUser = new CurrentUser(user.getUserId(),user.getUserCode(),user.getUserName());
                   String token = tokenUtils.loginSign(currentUser,userPwd);
//                   响应jwt token
                    return  Result.ok("登录成功",token);
                }else{
                    return Result.err(Result.CODE_ERR_BUSINESS,"密码错误");
                }
            }else{
                return  Result.err(Result.CODE_ERR_BUSINESS,"用户未审核");
            }
        }else{
            return Result.err(Result.CODE_ERR_BUSINESS,"账号不存在");
        }
    }

    @RequestMapping("/curr-user")
    public Result currentUser(@RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token){

        CurrentUser currentUser =  tokenUtils.getCurrentUser(token);
        return Result.ok(currentUser);
    }

    @Autowired
    private AuthService authService;
    @RequestMapping("/user/auth-list")
    public Result loadAuthTree(@RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token){
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        int userId = currentUser.getUserId();

        List<Auth> authTreeList = authService.authTreeByUid(userId);
        return  Result.ok(authTreeList);

    }
    @RequestMapping("/logout")
    public Result logout(@RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token){
        redisTemplate.delete(token);
        return  Result.ok("退出系统");
    }
}
