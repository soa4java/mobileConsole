package com.yuchengtech.mobile.console.utils;

import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.util.Assert;

public class SecurityUtil {
	
	public static String shaPassword(String rawPass, String salt){	
		Assert.notNull(rawPass, "密码不能为空");
		Assert.notNull(salt, "salt不能为空");
		PasswordEncoder encoder = new ShaPasswordEncoder();
		String shaPassword = encoder.encodePassword(rawPass, salt);
		return shaPassword;
	}
	
}
