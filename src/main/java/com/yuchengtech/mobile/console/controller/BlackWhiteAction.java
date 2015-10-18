package com.yuchengtech.mobile.console.controller;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

 import com.yuchengtech.core.orm.Page;
import com.yuchengtech.core.orm.PropertyFilter;
import com.yuchengtech.mobile.console.common.Constants;
import com.yuchengtech.mobile.console.entity.account.VUser;
import com.yuchengtech.mobile.console.service.account.UserManager;
import com.yuchengtech.mobile.console.service.account.VUserManager;

/**
 * 角色管理Action.
 * 
 * @author calvin
 */
@Controller
@RequestMapping(value = "/black-white")
public class BlackWhiteAction {

 	
	@Autowired
	private UserManager userManager;
	@Autowired
	private VUserManager vUserManager;
 
	@RequestMapping(value = "blackList")
	public String blackList(HttpServletRequest request) throws Exception {

		Page<VUser> page = new Page<VUser>(Constants.PAGE_SIZE);
		
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
		filters.add(new PropertyFilter("EQS_status", "0")); 
		page = vUserManager.findPage(page, filters);
		request.setAttribute("page", page);
		return "sys/acl/user/black-list";
	}
	
	@RequestMapping(value = "whiteList")
	public String whiteList(HttpServletRequest request) throws Exception {
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		Page<VUser> page = new Page<VUser>(Constants.PAGE_SIZE);
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
		filters.add(new PropertyFilter("EQS_status", "1")); 
		page = vUserManager.findPage(page, filters);
		request.setAttribute("page", page);
		return "sys/acl/user/white-list";
	}

	
	@RequestMapping(value = "toBlackList")
	public String toBlackList(HttpServletRequest request,@RequestParam Long id) throws Exception {
		if(id!=null){
			userManager.disabledUser(id);
			request.setAttribute("msg", "已经将该用户加入黑名单");
 		}
		return blackList(request);
	}
	
	@RequestMapping(value = "toWhiteList")
	public String toWhiteList(HttpServletRequest request,@RequestParam Long id) throws Exception {
		if(id!=null){
			userManager.enabledUser(id);
			request.setAttribute("msg", "已经将该用户加入白名单");
 		}
		return whiteList(request);
	}

	
	@RequestMapping(value = "batchToBlackList")
	public String batchToBlackList(HttpServletRequest request,@RequestParam(required=false) Long[] ids) throws Exception {
		if(ids!=null){
			int num = userManager.batchDisabledUser(ids);
			request.setAttribute("msg", "已经将这"+num+"个用户加入黑名单");
			//addActionMessage("已经将这"+num+"个用户加入黑名单");			
		}
		return blackList(request);
	}

	
	@RequestMapping(value = "batchToWhiteList")
	public String batchToWhiteList(HttpServletRequest request,@RequestParam(required=false) Long[] ids) throws Exception {
		if(ids!=null){
			int num = userManager.batchEnabledUser(ids);
			request.setAttribute("msg", "已经将这"+num+"个用户加入白名单");
 		}
		return whiteList(request);
	}
 

}