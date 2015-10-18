package com.yuchengtech.mobile.console.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yuchengtech.core.orm.Page;
import com.yuchengtech.core.orm.PropertyFilter;
import com.yuchengtech.mobile.console.common.Constants;
import com.yuchengtech.mobile.console.dao.HibernateUtils;
import com.yuchengtech.mobile.console.entity.account.Role;
import com.yuchengtech.mobile.console.entity.account.User;
import com.yuchengtech.mobile.console.entity.account.VUser;
import com.yuchengtech.mobile.console.entity.dict.DictItem;
import com.yuchengtech.mobile.console.service.ServiceException;
import com.yuchengtech.mobile.console.service.account.RoleManager;
import com.yuchengtech.mobile.console.service.account.UserManager;
import com.yuchengtech.mobile.console.service.account.VUserManager;
import com.yuchengtech.mobile.console.service.dict.DictCode;
import com.yuchengtech.mobile.console.service.dict.DictItemManager;

/**
 * 用户管理Action.
 * 
 * 使用Struts2 convention-plugin annotation定义Action参数.
 * 演示带分页的管理界面.
 */
@Controller
@RequestMapping(value = "/user")
public class UserAction {

 	@Autowired
	private UserManager userManager;
	@Autowired
	private RoleManager roleManager;
	@Autowired
	private VUserManager vUserManager;
	@Autowired
	private DictItemManager dictItemManager;


	//-- CRUD Action 函数 --//
	
	@RequestMapping(value = "list")
	public String list(HttpServletRequest request){
		 List<DictItem> itemList = dictItemManager.findDictItemList(DictCode.USER_STATE);
		 request.setAttribute("itemList", itemList);
		
		 Page<VUser> page = new Page<VUser>(Constants.PAGE_SIZE);//每页5条记录
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		//设置默认排序方式
		if (!page.isOrderBySetted()) {
			page.setOrderBy("id");
			page.setOrder(Page.DESC);
		}
		if(request.getParameter("page.pageNo")!=null){
			page.setPageNo(Integer.parseInt(request.getParameter("page.pageNo")));
			page.setOrderBy(request.getParameter("page.orderBy"));
			page.setOrder(request.getParameter("page.order"));
		}
		page = vUserManager.findPage(page, filters);
		request.setAttribute("page", page);
		return "sys/acl/user/list";
	}

	@RequestMapping(value = "input")
	public String input(HttpServletRequest request,@RequestParam(required=false) Long id) {
		List<Long> roleIDs=new ArrayList<Long>();  
		if(id!=null){
			User user = userManager.get(id);
			request.setAttribute("id", id);
			request.setAttribute("user", user);
			List<Role> checkedRoleIds = user.getRoleList();
			for(Role r:checkedRoleIds){
				roleIDs.add(r.getId());
			}
		}
		request.setAttribute("roleIDs", roleIDs);

		
		 List<DictItem> itemList = dictItemManager.findDictItemList(DictCode.USER_STATE);
		 request.setAttribute("itemList", itemList);
		 request.setAttribute("roles", roleManager.getAll("id", true));
		return "sys/acl/user/form";
	}

	@RequestMapping(value = "save") 
	public String save(HttpServletRequest request,User entity)  {
		//根据页面上的checkbox选择 整合User的Roles Set
		String[] roleIDtemp=request.getParameterValues("roleID");
		List<Long> roleIds=new ArrayList<Long>();
		for(String t:roleIDtemp){
			roleIds.add(Long.parseLong(t));
		}
		HibernateUtils.mergeByCheckedIds(entity.getRoleList(), roleIds, Role.class);
		if (entity.getId()==null) {
			userManager.save(entity);
			request.setAttribute("msg","增加用户【"+entity.getUsername()+"】成功");
		}else {
			userManager.update(entity);
			request.setAttribute("msg","修改用户【"+entity.getUsername()+"】成功");
		}
		return list(request);
	}

	 
	@RequestMapping(value = "delete")
	public String delete(HttpServletRequest request,@RequestParam Long id) throws Exception {
		try {
			userManager.delete(id);
			request.setAttribute("msg", "删除用户成功");
		} catch (ServiceException e) {
			//logger.error(e.getMessage(), e);
			request.setAttribute("msg", "删除用户失败");
		}
		return list(request);
	}

	//	//-- 其他Action函数 --//
//	/**
//	 * 支持使用Jquery.validate Ajax检验用户名是否重复.
//	 */
	@RequestMapping(value="checkUsername")
	public String checkUsername(HttpServletRequest request,HttpServletResponse response) throws IOException {
		//HttpServletRequest request = ServletActionContext.getRequest();
		String newUsername = request.getParameter("username");
		String oldUsername = request.getParameter("oldUsername");
		if (userManager.isUsernameUnique(newUsername, oldUsername)) {
			response.getWriter().write("true");
		} else {
			response.getWriter().write("false");
 		}
		response.getWriter().flush();
		//因为直接输出内容而不经过jsp,因此返回null.
		return null;
	}

}
