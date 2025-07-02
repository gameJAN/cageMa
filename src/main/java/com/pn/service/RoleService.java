package com.pn.service;

import com.pn.entity.Role;

import java.util.List;

public interface RoleService {

    public List<Role> queryAllRole();

    public List<Role> querUserRoleByUid(Integer userId);

}
