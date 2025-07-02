package com.pn.service.impl;

import com.pn.entity.Role;
import com.pn.mapper.RoleMapper;
import com.pn.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@CacheConfig(cacheNames = "com.pn.service.impl.RoleServicelmpl")
@Service
public class RoleServicelmpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Cacheable(key="'all:role'")
    @Override
    public List<Role> queryAllRole() {
        return roleMapper.findAllRole();
    }

    @Override
    public List<Role> querUserRoleByUid(Integer userId) {
        return roleMapper.findUserRoleByUid(userId);
    }
}
