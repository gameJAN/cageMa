package com.pn.mapper;

import com.pn.entity.User;


public interface UserMapper {
    //    根据账号查询用户信息的方法

    public User findUserByCode(String userCode);
}
