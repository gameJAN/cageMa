package com.pn.controller;
import com.pn.entity.Result;
import com.pn.entity.User;
import com.pn.page.Page;
import com.pn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
