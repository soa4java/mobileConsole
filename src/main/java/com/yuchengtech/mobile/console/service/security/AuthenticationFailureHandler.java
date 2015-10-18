package com.yuchengtech.mobile.console.service.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import com.yuchengtech.mobile.console.entity.log.ServerLoginLog;
import com.yuchengtech.mobile.console.service.log.ServerLoginLogManager;
import com.yuchengtech.mobile.console.utils.DateUtil;

public class AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	
	@Autowired
	private ServerLoginLogManager loginLogManager;
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request,HttpServletResponse response, AuthenticationException exception)throws IOException, ServletException {		
		Authentication authentication = exception.getAuthentication();
		if(authentication!=null){
			WebAuthenticationDetails details  = (WebAuthenticationDetails)authentication.getDetails();
			ServerLoginLog log = new ServerLoginLog(null, authentication.getName(), "用户登录", DateUtil.format(),"WEB",details.getRemoteAddress(),"N", exception.getMessage());
			loginLogManager.insert(log);			
			request.getSession().setAttribute("SPRING_SECURITY_LAST_EXCEPTION", exception);
			request.getRequestDispatcher("/").forward(request, response);
		}
	}
	
}
