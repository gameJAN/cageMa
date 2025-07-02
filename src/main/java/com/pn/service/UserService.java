package com.pn.service;

import com.pn.dto.AssignRoleData;
import com.pn.entity.Result;
import com.pn.entity.User;
import com.pn.page.Page;

public interface UserService {
    public User queryUserByCode(String userCode);

    public Page queryUserByPage(Page page ,User user);

    public Result saveUser(User user);

    public Result setUserState(User user);

    public Result assignRole(AssignRoleData assignRoleData);
}