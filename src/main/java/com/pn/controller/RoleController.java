package com.pn.controller;

import com.pn.entity.Result;
import com.pn.entity.Role;
import com.pn.page.Page;
import com.pn.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/role")
@RestController
public class RoleController {
    @Autowired
    private RoleService roleService;

    @RequestMapping("/role-list")
    public Result roleList(){
        List<Role> roleList = roleService.queryAllRole();
        return Result.ok(roleList);
    }
    @RequestMapping("/role-page-list")
    public Result roleListPage(Page page , Role role){
        page = roleService.queryRolePage(page , role);
        return Result.ok(page);
    }
}
