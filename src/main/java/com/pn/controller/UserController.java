package com.pn.controller;
import com.pn.entity.CurrentUser;
import com.pn.entity.Result;
import com.pn.entity.User;
import com.pn.page.Page;
import com.pn.service.UserService;
import com.pn.utils.TokenUtils;
import com.pn.utils.WarehouseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/user")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/user-list")
    public Result userList(Page page, User user){
      page =  userService.queryUserByPage(page,user);
      return Result.ok(page);
    }
//    接收并封装前端传过来的用户信息

    @Autowired
    private TokenUtils tokenUtils;
    @RequestMapping("/addUser")
    public Result addUser(@RequestBody User user, @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token){
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        int createBy = currentUser.getUserId();
        user.setCreateBy(createBy);

        Result result = userService.saveUser(user);

        return result;
    }
}
