package com.yuchengtech.mobile.console.controller;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.collect.ListMultimap;
import com.yuchengtech.mobile.console.entity.account.VRoleAuthority;
import com.yuchengtech.mobile.console.entity.account.VUser;
import com.yuchengtech.mobile.console.service.account.VRoleAuthorityManager;

@Controller
@RequestMapping(value = "/frame")
public class FrameAction {

	private static final long serialVersionUID = 4989311084866378322L;
	
 	
	@Autowired
	private VRoleAuthorityManager vRoleAuthorityManager;
	
	
	@RequestMapping(value = "main")
	public String main() throws Exception {
		return "index";
	}
	
	@RequestMapping(value = "right")
	public String right() throws Exception {
		return "right";
	}
	
	@RequestMapping(value = "top")
	public String top(HttpServletRequest request) throws Exception {
		VUser user = (VUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ListMultimap<String, VRoleAuthority> menus = vRoleAuthorityManager.loadMenuAuthorityByRole(user.getRoleIds());
		List<VRoleAuthority> list =menus.get("1");
		if (menus!=null) {
			request.setAttribute("menu",list);
			//ActionContext.getContext().put("menu", menus.get("1"));	
		}
		return "top";
	}
	
	@RequestMapping(value = "left")
	public String left(HttpServletRequest request,@RequestParam(required=false)  String  id) throws Exception {
		VUser user = (VUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ListMultimap<String, VRoleAuthority> menus = vRoleAuthorityManager.loadMenuAuthorityByRole(user.getRoleIds());
		if (menus!=null) {
			//初始化二级菜单
			if (StringUtils.isBlank(id)) {
				Collection<VRoleAuthority> firstMenus = menus.get("1");
				VRoleAuthority first = firstMenus.iterator().next();
				if (first!=null) {
					id = String.valueOf(first.getAuthId());
				}			
			}
			List<VRoleAuthority> list =menus.get(id);
 			request.setAttribute("menu", list);		
		}
		return "left";
	}

	
}