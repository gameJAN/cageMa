package com.pn.service.impl;

import com.pn.entity.Result;
import com.pn.entity.User;
import com.pn.mapper.UserMapper;
import com.pn.page.Page;
import com.pn.service.UserService;
import com.pn.utils.DigestUtil;
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

    @Override
    public Result saveUser(User user) {
        User u = userMapper.findUserByCode(user.getUserCode());
        if(u !=null){
            return Result.err(Result.CODE_ERR_BUSINESS,"账号已存在");
        }

        String password= DigestUtil.hmacSign(user.getUserPwd());
        user.setUserPwd(password);

        int i = userMapper.insertUser(user);
        if(i>0){
            return Result.ok("用户添加成功");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"用户添加失败");
    }

    @Override
    public Result setUserState(User user) {
         int i =  userMapper.setStateByUid(user.getUserId(),user.getUserState());
         if(i>0){
             return Result.ok("用户修改成功");
         }
        return Result.err(Result.CODE_ERR_BUSINESS,"用户修改失败");
    }
}
