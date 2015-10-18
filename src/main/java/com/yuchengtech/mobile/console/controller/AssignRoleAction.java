package com.yuchengtech.mobile.console.controller;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yuchengtech.core.orm.Page;
import com.yuchengtech.core.orm.PropertyFilter;
import com.yuchengtech.mobile.console.common.Constants;
import com.yuchengtech.mobile.console.entity.account.Role;
import com.yuchengtech.mobile.console.entity.account.User;
import com.yuchengtech.mobile.console.entity.account.VUser;
import com.yuchengtech.mobile.console.entity.dict.DictItem;
import com.yuchengtech.mobile.console.service.account.RoleManager;
import com.yuchengtech.mobile.console.service.account.UserManager;
import com.yuchengtech.mobile.console.service.account.VUserManager;
import com.yuchengtech.mobile.console.service.dict.DictCode;
import com.yuchengtech.mobile.console.service.dict.DictItemManager;

/**
 * 角色管理Action.
 * 
 * @author calvin
 */
//@Namespace("/acl")
//@Results( {
//	@Result(name = "assign-role-list", location = "/WEB-INF/pages/sys/acl/user/assign-role-list.jsp"),
//	@Result(name = "assign-role", location = "/WEB-INF/pages/sys/acl/user/assign-role.jsp")
//})
@Controller
@RequestMapping(value = "/acl")
public class AssignRoleAction   {

	private static final long serialVersionUID = -4052047494894591406L;
	
	private Page<VUser> page = new Page<VUser>(Constants.PAGE_SIZE);
	@Autowired
	private RoleManager roleManager;
	@Autowired
	private VUserManager vUserManager;
	@Autowired
	private UserManager userManager;
	@Autowired
	private DictItemManager dictItemManager;
	
	
	@RequestMapping(value="assignRoleList")
	public String execute(HttpServletRequest request) {
		List<DictItem> itemList = dictItemManager.findDictItemList(DictCode.USER_STATE);
		 request.setAttribute("itemList", itemList);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
	 	Page<VUser> page = new Page<VUser>(Constants.PAGE_SIZE);//每页5条记录
		//设置默认排序方式
		if (!page.isOrderBySetted()) {
			page.setOrderBy("id");
			page.setOrder(Page.ASC);
		}
		if(request.getParameter("page.pageNo")!=null){
			page.setPageNo(Integer.parseInt(request.getParameter("page.pageNo")));
			page.setOrderBy(request.getParameter("page.orderBy"));
			page.setOrder(request.getParameter("page.order"));
		}
		
		page = vUserManager.findPage(page, filters);
		request.setAttribute("page", page);

		return "sys/acl/user/assign-role-list";
	}
	
	@RequestMapping(value="assignRole")
	public String assignRole(HttpServletRequest request,Long id)  {
		 List<DictItem> itemList = dictItemManager.findDictItemList(DictCode.USER_STATE);
		 request.setAttribute("itemList", itemList);
		
		VUser vuser = vUserManager.get(id);
		List<Long> checkedRoleIds = vuser.getRoleIds();
		List<Role> allRoleList = roleManager.getAll();
		request.setAttribute("allRoleList", allRoleList);
		request.setAttribute("checkeRoleIds", checkedRoleIds);
		request.setAttribute("vuser", vuser);
		return "/sys/acl/user/assign-role";
	}
	@RequestMapping(value="save")
	public String save(HttpServletRequest request,User user){
		String[] roleIDtemp=request.getParameterValues("checkedRoleIds");
		List<Long> roleIds=new ArrayList<Long>();
		for(String t:roleIDtemp){
			roleIds.add(Long.parseLong(t));
		}
		userManager.update(user,roleIds);
		request.setAttribute("msg","分配角色成功");
		return assignRole(request,user.getId());
	}

}