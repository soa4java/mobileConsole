package com.yuchengtech.mobile.console.controller;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yuchengtech.core.orm.Page;
import com.yuchengtech.core.orm.PropertyFilter;
import com.yuchengtech.core.utils.web.struts2.Struts2Utils;
import com.yuchengtech.mobile.console.entity.account.VUserRole;
import com.yuchengtech.mobile.console.service.account.VUserRoleManager;

@Controller
@RequestMapping(value = "/acl")
public class VUserRoleAction  {

	private static final long serialVersionUID = -4052047494894591405L;
	
	@Autowired
	private VUserRoleManager vUserRoleManager; 

	private Long userId;  
	private Long[] ids;  
	private Page<VUserRole> page = new Page<VUserRole>(8);
	
	@RequestMapping(value = "/v-user-role")
	public String execute(HttpServletRequest request) throws Exception {
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		if (!page.isOrderBySetted()) {
			page.setOrderBy("userId");
			page.setOrder(Page.ASC);
		}
		if(request.getParameter("page.pageNo")!=null){
			page.setPageNo(Integer.parseInt(request.getParameter("page.pageNo")));
			page.setOrderBy(request.getParameter("page.orderBy"));
			page.setOrder(request.getParameter("page.order"));
		}
		page = vUserRoleManager.findPage(page, filters);
		request.setAttribute("page", page);
		return "sys/acl/user/user-role-list";
	}

	@RequestMapping(value="delete")
	public String delete(HttpServletRequest request,@RequestParam Long roleId,@RequestParam Long userId) throws Exception {
		//Long roleId = Long.parseLong(request.getParameter("filter_EQL_roleId"));
		VUserRole vUserRole = vUserRoleManager.findUniqueBy(roleId,userId);
		request.setAttribute("msg",new StringBuilder("已将用户").
				append(vUserRole.getUsername()).append("的【").append(vUserRole.getRoleName()).append("】角色移除").toString());
//		addActionMessage(new StringBuilder("已将用户").
//				append(vUserRole.getUsername()).append("的【").append(vUserRole.getRoleName()).append("】角色移除").toString());
		vUserRoleManager.delete(vUserRole);
		return  execute(request);
	}
	
/*	public String batchDelete() throws Exception {
		Long roleId = Long.parseLong(Struts2Utils.getParameter("filter_EQL_roleId"));
		int num = vUserRoleManager.batchDelete(roleId,ids);
		addActionMessage("批量删除成功");
		return  execute();
	}*/

	public Long getUserId() {
		return userId;
	}

	public Long[] getIds() {
		return ids;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setIds(Long[] ids) {
		this.ids = ids;
	}

	public Page<VUserRole> getPage() {
		return page;
	}
	
}