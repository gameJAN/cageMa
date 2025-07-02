package com.pn.service;

import com.pn.entity.Role;
import com.pn.entity.UserRole;

import java.util.List;

public interface UserRoleService {

    public List<UserRole> queryUserRole(Integer userId);

}
