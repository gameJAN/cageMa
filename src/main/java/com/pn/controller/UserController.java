package com.pn.controller;
import com.pn.entity.*;
import com.pn.page.Page;
import com.pn.service.RoleService;
import com.pn.service.UserRoleService;
import com.pn.service.UserService;
import com.pn.utils.TokenUtils;
import com.pn.utils.WarehouseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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

    @RequestMapping("/updateState")
    public Result updateUserState(@RequestBody User user, @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token){
        Result result =  userService.setUserState(user);
        return  result;
    }

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;
    @RequestMapping("/user-role-list/{userId}")
    public Result userRoleList(@PathVariable Integer userId){

//        List<UserRole>  userRoleList = userRoleService.queryUserRole(userId);

        List<Role> roleList = roleService.querUserRoleByUid(userId);
        return Result.ok(roleList);
    }
}
