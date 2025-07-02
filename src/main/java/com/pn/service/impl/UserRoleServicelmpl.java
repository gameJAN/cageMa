package com.pn.service.impl;

import com.pn.entity.UserRole;
import com.pn.mapper.UserRoleMapper;
import com.pn.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleServicelmpl implements UserRoleService {
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public List<UserRole> queryUserRole(Integer userId) {

        return userRoleMapper.userRoleByUid(userId);
    }
}
