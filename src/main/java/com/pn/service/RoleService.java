package com.pn.service;

import com.pn.entity.Role;
import com.pn.page.Page;

import java.util.List;

public interface RoleService {

    public List<Role> queryAllRole();

    public List<Role> querUserRoleByUid(Integer userId);

    public Page queryRolePage(Page page, Role role);
}
