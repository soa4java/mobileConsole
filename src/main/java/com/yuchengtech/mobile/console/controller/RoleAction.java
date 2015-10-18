package com.yuchengtech.mobile.console.controller;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yuchengtech.core.orm.Page;
import com.yuchengtech.core.orm.PropertyFilter;
import com.yuchengtech.mobile.console.common.Constants;
import com.yuchengtech.mobile.console.entity.account.Role;
import com.yuchengtech.mobile.console.entity.account.VRole;
import com.yuchengtech.mobile.console.service.account.RoleManager;
import com.yuchengtech.mobile.console.service.account.VRoleManager;

/**
 * 角色管理Action.
 * 
 * @author calvin
 */

@Controller
@RequestMapping(value = "/role")
public class RoleAction {

	@Autowired
	private RoleManager roleManager;
	
	@Autowired
	private VRoleManager vRoleManager;

	 

	//-- CRUD Action 函数 --//
	@RequestMapping(value = "list")
	public String list(HttpServletRequest request)  {
		
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		Page<VRole> page = new Page<VRole>(Constants.PAGE_SIZE);

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
		page = vRoleManager.findPage(page, filters);
		request.setAttribute("page", page);
		return "sys/acl/role/list";
	}

	 
	@RequestMapping(value = "input")
	public String input(HttpServletRequest request,@RequestParam(required=false) Long id) throws Exception {
		if(id!=null){
			Role role = roleManager.get(id);
			request.setAttribute("id", id);
			request.setAttribute("role", role);
		}
 		return "sys/acl/role/form";
	}
//
//	 
	@RequestMapping(value = "save")
	public String save(HttpServletRequest request,Role role) throws Exception {
		//根据页面上的checkbox 整合Role的Authorities Set.
//		HibernateUtils.mergeByCheckedIds(entity.getAuthorityList(), checkedAuthIds, Authority.class);
		//保存用户并放入成功信息.
		if (role.getId() != null) {
			roleManager.update(role);
			request.setAttribute("msg","修改角色【"+role.getName()+"】成功");
		}else {
			roleManager.save(role);
			request.setAttribute("msg","新增角色【"+role.getName()+"】成功");
		}
		return list(request);
	}
//
//	 
	@RequestMapping(value = "delete")
	public String delete(HttpServletRequest request,@RequestParam Long id) throws Exception {
		roleManager.delete(id);
		request.setAttribute("msg", "删除角色成功");
		//addActionMessage("删除角色【"+entity.getName()+"】成功");
		return list(request);
	}
//
//	//-- 页面属性访问函数 --//
//	/**
//	 * list页面显示所有角色列表.
//	 */
//	public List<Role> getAllRoleList() {
//		return allRoleList;
//	}
//
//	/**
//	 * input页面显示所有授权列表.
//	 */
////	public List<Authority> getAllAuthorityList() {
////		return roleManager.getAllAuthority();
////	}
//
//	/**
//	 * input页面显示角色拥有的授权.
//	 */
//	public List<Long> getCheckedAuthIds() {
//		return checkedAuthIds;
//	}
//
//	/**
//	 * input页面提交角色拥有的授权.
//	 */
//	public void setCheckedAuthIds(List<Long> checkedAuthIds) {
//		this.checkedAuthIds = checkedAuthIds;
//	}
//	
//	 
//	public void setRoleManager(RoleManager roleManager) {
//		this.roleManager = roleManager;
//	}
//
	
	@RequestMapping(value = "view")
	public String view(HttpServletRequest request,@RequestParam Long id) throws Exception {
		Role role = roleManager.get(id);
		request.setAttribute("role", role);
		return "sys/acl/role/view";
	}
}