package com.yuchengtech.mobile.console.service.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.mobile.console.service.account.VUserManager;

@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private VUserManager vUserManager;

	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException, DataAccessException {
		UserDetails user = vUserManager.loadUserByUsername(userName);
		if (user == null){
			throw new UsernameNotFoundException("用户不存在");	
		}
		if (user.getAuthorities().isEmpty()) {
			throw new UsernameNotFoundException("尚未给该用户分配后台管理权限");	
		}
		return user;
	}
	
}
