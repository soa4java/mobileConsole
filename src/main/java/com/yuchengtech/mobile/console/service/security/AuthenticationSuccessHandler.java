package com.yuchengtech.mobile.console.service.security;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import com.yuchengtech.mobile.console.common.status.LoginLogSource;
import com.yuchengtech.mobile.console.entity.account.VUser;
import com.yuchengtech.mobile.console.entity.log.ServerLoginLog;
import com.yuchengtech.mobile.console.service.account.VRoleAuthorityManager;
import com.yuchengtech.mobile.console.service.log.ServerLoginLogManager;
import com.yuchengtech.mobile.console.utils.DateUtil;

public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	
	@Autowired
	private ServerLoginLogManager loginLogManager;
	@Autowired
	private VRoleAuthorityManager vRoleAuthorityManager;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,HttpServletResponse response, Authentication authentication)
			throws ServletException, IOException {
		Long loginId = writeLog(authentication);
		keepUserInfo(request,authentication,loginId);
		super.onAuthenticationSuccess(request, response, authentication);
	}

	private void keepUserInfo(HttpServletRequest request,Authentication authentication,Long loginId) {
		VUser user = (VUser)authentication.getPrincipal();
		user.setLoginId(loginId);
		Map<String, Boolean> opts = vRoleAuthorityManager.loadOperationAuthorityByRole(user.getRoleIds());
		request.getSession().setAttribute("hasPermission", opts);
		request.getSession().setAttribute("headOffice", user.getHeadOffice());
		request.getSession().setAttribute("branchCode", user.getBranchCode());
		request.getSession().setAttribute("username", user.getUsername());
	}

	private Long writeLog(Authentication authentication) {
		WebAuthenticationDetails details  = (WebAuthenticationDetails)authentication.getDetails();
		ServerLoginLog log = new ServerLoginLog(null, authentication.getName(), "用户登录", DateUtil.format(),LoginLogSource.SERVER,details.getRemoteAddress(),"Y", "登录成功");
		loginLogManager.insert(log);
		return log.getId();
	}
	
}
