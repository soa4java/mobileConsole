package com.yuchengtech.mobile.console.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yuchengtech.mobile.console.entity.account.User;
import com.yuchengtech.mobile.console.service.account.UserManager;
import com.yuchengtech.mobile.console.utils.SecurityUtil;

/**
 * 用户管理Action.
 * 
 * 使用Struts2 convention-plugin annotation定义Action参数.
 * 演示带分页的管理界面.
 */
@Controller
@RequestMapping(value = "/personal")
public class PersonalAction {

	private static final long serialVersionUID = 8683878162525847072L;
	
	@Autowired
	private UserManager userManager;

	
	@RequestMapping(value="savePswd")
	public String savePswd(Long id,HttpServletRequest request) throws Exception {
		if(checkPswd(id,request)){
		User entity = userManager.get(id);
		entity.setPassword(request.getParameter("password"));
		userManager.changePassword(entity);
		request.setAttribute("msg", "修改密码成功！");
		}else{
		request.setAttribute("msg", "原密码输入不符！");
		}
		return "sys/acl/user/change-pswd";
	}
	@RequestMapping(value="changePswd")
	public String changePswd() throws Exception {
		return "sys/acl/user/change-pswd";
	}

	public boolean checkPswd(Long id,HttpServletRequest request) throws Exception {
		String origPwd = request.getParameter("origPwd");
		User user = userManager.get(id);	
		String hashPass = SecurityUtil.shaPassword(origPwd, user.getSalt());
		if (user.getPassword().equals(hashPass)){
			return true;
		} else {
			return false;
		}
	}	

	public String list() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String input() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String save() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String delete() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String view() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
