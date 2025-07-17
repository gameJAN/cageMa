package com.pn.mapper;

import com.pn.entity.Role;
import com.pn.entity.User;
import com.pn.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    public List<Role> findAllRole();

    public List<Role> findUserRoleByUid(Integer userId);

    public Integer findRoleIdByName(String roleName);

    public Integer findRoleRowCount(Role role);

    public List<Role> findRolePage(@Param("page") Page page, @Param("role") Role role);
}
