package com.pn.service;


import com.pn.entity.Auth;

import java.util.List;

public interface AuthService {

	public List<Auth> authTreeByUid(Integer suerId);
}
