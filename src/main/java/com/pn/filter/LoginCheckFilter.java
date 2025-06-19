package com.pn.filter;


import com.alibaba.fastjson.JSON;
import com.pn.entity.Result;
import com.pn.utils.WarehouseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginCheckFilter implements Filter {

    private StringRedisTemplate redisTemplate;

    public void setRedisTemplate(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        List<String> urlList = new ArrayList<>();
        urlList.add("/login");
        urlList.add("/captcha/captchaImage");
        String url =  request.getServletPath();
        if(urlList.contains(url)){
            chain.doFilter(request,response);
            return;
        }

        String token = request.getHeader(WarehouseConstants.HEADER_TOKEN_NAME);

        if(StringUtils.hasText(token) &&  redisTemplate.hasKey(token)){
            chain.doFilter(request,response);
            return;
        }
//        Map<String , Object> data = new HashMap<>();
//        data.put("code",401);
//        data.put("message","您尚未登录");
        Result result = Result.err(Result.CODE_ERR_UNLOGINED,"您尚未登录");
        String jsonStr = JSON.toJSONString(result);
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.write(jsonStr);
        out.flush();
        out.close();
    }
}
