package com.pn.service.impl;

import com.pn.dto.AssignRoleData;
import com.pn.entity.Result;
import com.pn.entity.User;
import com.pn.entity.UserRole;
import com.pn.mapper.RoleMapper;
import com.pn.mapper.UserMapper;
import com.pn.mapper.UserRoleMapper;
import com.pn.page.Page;
import com.pn.service.UserService;
import com.pn.utils.DigestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Transactional
    @Override
    public Result assignRole(AssignRoleData assignRoleData) {

       userRoleMapper.removeUserRoleByUid(assignRoleData.getUserId());

       List<String> roleNameList = assignRoleData.getRoleCheckList();
       for (String roleName : roleNameList){
           Integer roleId = roleMapper.findRoleIdByName(roleName);
           UserRole userRole = new UserRole();
           userRole.setRoleId(roleId);
           userRole.setUserId(assignRoleData.getUserId());
           userRoleMapper.insertUserRole(userRole);
       }

        return null;
    }

    @Override
    public Result remoeUserByIds(List<Integer> userIdList) {

        int i = userMapper.setIsDeleteByUids(userIdList);
        if(i>0){
            return Result.ok("用户删除成功");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"删除失败");
    }

    @Override
    public Result setUserById(User user) {
        int i  = userMapper.setUserNameByUid(user);

        if(i>0){
            return Result.ok("用户修改成功");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"修改失败");
    }

    @Override
    public Result setPwdById(Integer userId) {
//        给定初始密码123456并对其加密
       String password = DigestUtil.hmacSign("123456");
        int i  =  userMapper.setPwdByUid(userId,password);
        if(i>0){
            return Result.ok("重置成功");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"重置失败");
    }
}
