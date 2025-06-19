package com.pn.service.impl;

import java.util.ArrayList;
import java.util.List;
import com.alibaba.fastjson.JSON;
import org.springframework.util.StringUtils;
import com.pn.entity.Auth;
import com.pn.mapper.AuthMapper;
import com.pn.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;


@Service
public class AuthServiceImp implements AuthService {

	//注入AuthMapper
	@Autowired
	private AuthMapper authMapper;

	@Autowired
	private StringRedisTemplate redisTemplate;

	//查询整个权限(菜单)树的业务方法
	@Override
	public List<Auth> authTreeByUid(Integer userId){

		String authTreeJson = redisTemplate.opsForValue().get("authTree:"+userId);

		if(StringUtils.hasText(authTreeJson)){
		  List<Auth> authTreeList =	JSON.parseArray(authTreeJson,Auth.class);
		  return  authTreeList;
		}
		List<Auth> allAuthList = authMapper.findAuthByUid(userId);
		List<Auth> authTreeList =allAuthToAuthTree(allAuthList,0);

		redisTemplate.opsForValue().set("authTree:"+userId,JSON.toJSONString(authTreeList));
		return  null;
	}

	private List<Auth> allAuthToAuthTree(List<Auth> allAuthList , Integer pid){
		List<Auth> firstLevelAuthList = new ArrayList<>();
		for(Auth auth : allAuthList){
			if(auth.getParentId() == (pid)){
				firstLevelAuthList.add(auth);
			}
		}
		for(Auth firstAuth : firstLevelAuthList){
			 List<Auth> secondLevelAuthList = allAuthToAuthTree(allAuthList , firstAuth.getAuthId());
			firstAuth.setChildAuth(secondLevelAuthList);
		}
		return firstLevelAuthList;
	}
}
