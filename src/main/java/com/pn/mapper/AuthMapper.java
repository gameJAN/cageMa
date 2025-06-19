package com.pn.mapper;

import java.util.List;
import com.pn.entity.Auth;

public interface AuthMapper {

	public List<Auth> findAuthByUid(Integer userId);

}
