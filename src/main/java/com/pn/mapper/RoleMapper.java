package com.pn.mapper;

import com.pn.entity.Role;

import java.util.List;

public interface RoleMapper {
    public List<Role> findAllRole();

    public List<Role> findUserRoleByUid(Integer userId);

}
