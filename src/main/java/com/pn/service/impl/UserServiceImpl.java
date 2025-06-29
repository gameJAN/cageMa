package com.pn.service.impl;

import com.pn.entity.User;
import com.pn.mapper.UserMapper;
import com.pn.page.Page;
import com.pn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User queryUserByCode(String userCode) {
        return userMapper.findUserByCode(userCode);
    }

    @Override
    public Page queryUserByPage(Page page, User user) {
        Integer count = userMapper.findUserRowCount(user);
        List<User> userList = userMapper.findUserByPage(page,user);

        page.setTotalNum(count);
        page.setResultList(userList);
        return page;
    }
}
