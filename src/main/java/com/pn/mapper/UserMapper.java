package com.pn.mapper;

import com.pn.entity.User;
import com.pn.page.Page;
import org.apache.ibatis.annotations.Param;


import java.util.List;


public interface UserMapper {
    //    根据账号查询用户信息的方法

    public User findUserByCode(String userCode);

    public List<User> findUserByPage(@Param("page") Page page, @Param("user") User user);

    public Integer findUserRowCount(User user);

    public int insertUser(User user);

    public int setStateByUid(Integer userId, String userState);
}
