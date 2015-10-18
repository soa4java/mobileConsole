package com.yuchengtech.mobile.console.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.ArrayListMultimap;
import com.yuchengtech.mobile.console.entity.account.Authority;
import com.yuchengtech.mobile.console.entity.account.Role;
import com.yuchengtech.mobile.console.service.account.AuthorityManager;
import com.yuchengtech.mobile.console.service.account.RoleManager;

/**
 * 角色管理Action.
 * 
 * @author calvin
 */
//@Namespace("/acl")
//@Results( {
//	@Result(name = "main", location = "/WEB-INF/pages/sys/acl/role/main.jsp"),
//	@Result(name = "role-list", location = "/WEB-INF/pages/sys/acl/role/role-list.jsp"),
//	@Result(name = "assign-authority", location = "/WEB-INF/pages/sys/acl/role/assign-authority.jsp")
//})
@Controller
@RequestMapping(value = "/assign-authority")
public class AssignAuthorityAction {

	private static final long serialVersionUID = -4052047494894591406L;
	
	@Autowired
	private RoleManager roleManager;
	@Autowired
	private AuthorityManager authorityManager;
	
	@RequestMapping(value = "/main")
	public String execute() {
		return "sys/acl/role/main";
	}
	
	
	@RequestMapping(value = "/roleList")
	public String roleList(HttpServletRequest request) {
		List<Role> list = roleManager.getAll("id", true);
		request.setAttribute("list", list);
		return "sys/acl/role/role-list";
	}
	
	@RequestMapping(value = "/assignAuthority")
	public String assignAuthority(HttpServletRequest request,Long roleId) {
		ArrayListMultimap<String, Authority> menu = authorityManager.getAllMenu();
		ArrayListMultimap<String, Authority> opt = authorityManager.getAllOpt();
		HashMap<String, Object> roleAndAuthorID=roleManager.getRoleAndAuthor(roleId);
		request.setAttribute("menu", menu);
		request.setAttribute("roleId", roleId);
		request.setAttribute("opt", opt);
		request.setAttribute("ids", roleAndAuthorID.get("AuthorID"));
		return "sys/acl/role/assign-authority";
	}
	
	@RequestMapping(value="/save")
	public String save(HttpServletRequest request,Long roleId) {
		Role role  = roleManager.get(roleId);
		List<Authority> authorityList = new ArrayList<Authority>();
		String[] id=request.getParameterValues("id");
		if (id!=null) {
			for (String i:id) {
				authorityList.add(new Authority(Long.parseLong(i)));
			}
		}
		role.setAuthorityList(authorityList);
		roleManager.update(role);
		request.setAttribute("msg", "已经给【"+role.getName()+"】角色授权");
		return assignAuthority(request,roleId);
	}

}