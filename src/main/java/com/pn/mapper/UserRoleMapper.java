package com.pn.mapper;

import com.pn.entity.Role;
import com.pn.entity.User;
import com.pn.entity.UserRole;

import java.util.List;

public interface UserRoleMapper {

    public List<UserRole> userRoleByUid(Integer userId);

    public int removeUserRoleByUid(Integer userId);

    public int insertUserRole(UserRole userRole);
}
