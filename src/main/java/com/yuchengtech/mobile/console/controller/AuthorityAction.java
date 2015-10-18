package com.yuchengtech.mobile.console.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yuchengtech.mobile.console.common.status.AuthorityType;
import com.yuchengtech.mobile.console.entity.account.Authority;
import com.yuchengtech.mobile.console.entity.account.VAuthority;
import com.yuchengtech.mobile.console.service.account.AuthorityManager;

/**
 * 角色管理Action.
 * 
 * @author calvin
 */
//@Namespace("/acl")
//@Results( {
//	@Result(name = CrudActionSupport.RELOAD, location = "authority.action?resId=${resId}", type = "redirect"),
//	@Result(name = CrudActionSupport.INPUT, location = "/WEB-INF/pages/sys/acl/auth/form.jsp"),
//	@Result(name = CrudActionSupport.VIEW, location = "/WEB-INF/pages/sys/acl/auth/view.jsp"),
//	@Result(name = "main", location = "/WEB-INF/pages/sys/acl/auth/main.jsp"),
//	@Result(name = "tree", location = "/WEB-INF/pages/sys/acl/auth/tree.jsp"),
//	@Result(name = CrudActionSupport.SUCCESS, location = "/WEB-INF/pages/sys/acl/auth/authority.jsp")
//})
@Controller
@RequestMapping(value = "/authority")
public class AuthorityAction{

 	
	@Autowired
	private AuthorityManager authorityManager;

	 
	@RequestMapping(value="main")
	public String main() {
		return "sys/acl/auth/authority";
	}

	@RequestMapping(value="save")
	public String save(HttpServletRequest request,Authority entity) throws Exception {
		if (entity.getId()==null) {		
			authorityManager.save(entity);
			request.setAttribute("msg", "增加【"+entity.getCode()+"】成功");
		}else {
			authorityManager.update(entity);
			request.setAttribute("msg","修改【"+entity.getCode()+"】成功");
		}
		saveOperation(request,entity);
		request.setAttribute("isReload", true);
		return viewAuthority(request,entity.getId());
	}

	private void saveOperation(HttpServletRequest request,Authority entity) {
		Long pid = entity.getId();
		String pcode = entity.getCode()+":";
		String[] optIds = request.getParameterValues("optId");
		String[] optCodes = request.getParameterValues("optCode");
		String[] optNames = request.getParameterValues("optName");
		if (optIds!=null) {
			Authority authority= null;
			Long id = null;
			for (int i = 0; i < optCodes.length; i++) {
			  if (StringUtils.isNotBlank(optCodes[i])) {
				 if (StringUtils.isNotBlank(optIds[i])) {
						id = Long.parseLong(optIds[i]);
						authority = authorityManager.get(id);
						authority.setCode(genOptCode(pcode,optCodes[i]));
						authority.setName(optNames[i]);
						authorityManager.update(authority);
					}else {
						id = null;
						authority = new Authority(id, pid, genOptCode(pcode,optCodes[i]), optNames[i],AuthorityType.OPT);
						authorityManager.save(authority);
					}	
				}
			}
		}
		
	}

	private String genOptCode(String pcode, String code) {
		if (!StringUtils.startsWith(code, pcode)) {
			code = pcode+code;
		}
		return code;
	}

	 
	@RequestMapping(value="delete")
	public String delete(HttpServletRequest request,@RequestParam Long eid) throws Exception {
		Authority entity = authorityManager.get(eid); 
		authorityManager.delete(entity);
		request.setAttribute("isReload", true);
		request.setAttribute("msg", "删除【"+entity.getCode()+"】成功");
		return viewAuthority(request,eid);
	}
//	
//	public String ajaxDelete() throws Exception {
//		entity = authorityManager.get(eid);
//		authorityManager.delete(entity);
//		Struts2Utils.renderJson("{'code':'sucess'}");
//		return null;
//	}
//	
//	public String batchDelete() throws Exception {
//		if(ids!=null){
//			int num = authorityManager.batchDelete(ids);
//			addActionMessage("已经删除"+num+"个权限");			
//		}
//		return RELOAD;
//	}
//	
	@RequestMapping(value="tree")
	public String tree(HttpServletRequest request) throws Exception {
		List<Authority> list = authorityManager.loadAuthorityTree();
		request.setAttribute("list", list);
		return "sys/acl/auth/tree";
	}
//	
	@RequestMapping(value="viewAuthority")
	public String viewAuthority(HttpServletRequest request,@RequestParam Long eid) throws Exception {
		VAuthority entity = authorityManager.getVAuthority(eid);
		request.setAttribute("entity", entity);
		loadOperation(request,eid);
		return "sys/acl/auth/view";
	}
//	
	private void loadOperation(HttpServletRequest request,Long eid) {
		if (eid!=null) {
			List<Authority> list = authorityManager.loadOperation(eid);
			if (list.size()>0) {
				request.setAttribute("list", list);				
			}
		}
	}
//	
	@RequestMapping(value="form")
	public String form(HttpServletRequest request,@RequestParam(required=false) Long eid) throws Exception {
		if (eid==null) {
			String pid = request.getParameter("pid");
			if (StringUtils.isNotBlank(pid)) {
				VAuthority entity = authorityManager.getSimpleVAuthority(Long.parseLong(pid));
				request.setAttribute("entity", entity);
			}
		}else {			
			VAuthority entity = authorityManager.getVAuthority(eid);
			request.setAttribute("entity", entity);
			loadOperation(request,eid);
		}
		return "sys/acl/auth/form";
	}
//	
//	public String checkUnique() throws Exception {
//		HttpServletRequest request = ServletActionContext.getRequest();
//		String propertyName = request.getParameter("propertyName");
//		String value = request.getParameter(propertyName);
//		if (authorityManager.isUnique(propertyName, value)) {
//			Struts2Utils.renderText("true");
//		} else {
//			Struts2Utils.renderText("false");
//		}
//		//因为直接输出内容而不经过jsp,因此返回null.
//		return null;
//	}	
//
//	 
//	public String view() throws Exception {
//		return null;
//	}
//
//	 
//	public String input() throws Exception {
//		return null;
//	}
}