package com.yuchengtech.mobile.console.controller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.yuchengtech.core.orm.Page;
import com.yuchengtech.core.orm.PropertyFilter;
import com.yuchengtech.mobile.console.common.Constants;
import com.yuchengtech.mobile.console.entity.account.Role;
import com.yuchengtech.mobile.console.entity.ver.Apk;
import com.yuchengtech.mobile.console.entity.ver.WebResource;
import com.yuchengtech.mobile.console.service.ServiceException;
import com.yuchengtech.mobile.console.service.ver.ApkManager;

/**
 * 用户管理Action.
 * 
 * 使用Struts2 convention-plugin annotation定义Action参数.
 * 演示带分页的管理界面.
 */
@Controller
@RequestMapping(value = "/apk")
public class ApkAction {

 	@Autowired
	private ApkManager apkManager;
	 
	protected Logger logger = LoggerFactory.getLogger(getClass());

 
 	@RequestMapping(value = "input")
	public String input(HttpServletRequest request,
			@RequestParam(required = false) Long id) {
		return "ver/apk/form";
	}

	@RequestMapping(value = "save")
	public String save(@RequestParam MultipartFile resFile,
			HttpServletRequest request) 	throws Exception {

		String ver = request.getParameter("ver");
		String name = request.getParameter("name");
		String isForceUpdate = request.getParameter("isForceUpdate");
		String des = request.getParameter("des");

		// 最新生成文件夹
		String realPath = request.getSession().getServletContext()
				.getRealPath("/");
		Apk apk = new Apk();
		apk.setVersion(ver);
		apk.setForceUpdate(isForceUpdate);
		apk.setDes(des);
		try {
			File file = File.createTempFile("webFix.", "zip");
			FileUtils.copyInputStreamToFile(resFile.getInputStream(), file);
			apkManager.saveApk(apk, file, realPath,resFile.getOriginalFilename());
		} catch (Exception e) {
			logger.error(e.getMessage());
			request.setAttribute("msg", "失败！发生严重错误，请联系系统管理员");
		}

		return list(request);

	}
	@RequestMapping(value = "rollback")
	public String rollback(HttpServletRequest request, @RequestParam Long id)
			throws Exception {
		try {
			String realPath = request.getSession().getServletContext()
					.getRealPath("/");
			apkManager.rollbackApk(realPath,id);
			request.setAttribute("msg", "删除资源成功");
		} catch (ServiceException e) {
			// logger.error(e.getMessage(), e);
			request.setAttribute("msg", "删除资源失败");
		}
		return list(request);
	}
	@RequestMapping(value = "active")
	public String active(HttpServletRequest request, @RequestParam Long id)
			throws Exception {
		try {
			apkManager.ativekWebResource(id);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return list(request);
	}
	@RequestMapping(value = "list")
	public String list(HttpServletRequest request) throws Exception {
		
		 Page<Apk> page = new Page<Apk>(Constants.PAGE_SIZE);//每页5条记录
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		//设置默认排序方式
		if (!page.isOrderBySetted()) {
			page.setOrderBy("id");
			page.setOrder(Page.DESC);
		}
		page = apkManager.findPage(page, filters);
		request.setAttribute("page", page);
		return "ver/apk/list";
	}

//	@RequestMapping(value = "input")
//	public String input(HttpServletRequest request,@RequestParam(required=false) Long id) {
//		if(id!=null){
//			User user = userManager.get(id);
//			request.setAttribute("id", id);
//			request.setAttribute("user", user);
//			List<Role> checkedRoleIds = user.getRoleList();
//	 		request.setAttribute("checkedRoleIds", checkedRoleIds);
//		}
//		 List<DictItem> itemList = dictItemManager.findDictItemList(DictCode.USER_STATE);
//		 request.setAttribute("itemList", itemList);
//		
//		return "sys/acl/user/form";
//	}

	 
//	public String save() throws Exception {
//		//根据页面上的checkbox选择 整合User的Roles Set
//		HibernateUtils.mergeByCheckedIds(entity.getRoleList(), checkedRoleIds, Role.class);
//		if (id==null) {
//			userManager.save(entity);
//			//addActionMessage("新增用户【"+entity.getUsername()+"】成功");
//		}else {
//			userManager.update(entity);
//			//addActionMessage("修改用户【"+entity.getUsername()+"】成功");
//		}
//		return "";
//	}
//
//	 
//	@RequestMapping(value = "delete")
//	public String delete(HttpServletRequest request,@RequestParam Long id) throws Exception {
//		try {
//			userManager.delete(id);
//			request.setAttribute("msg", "删除用户成功");
//		} catch (ServiceException e) {
//			//logger.error(e.getMessage(), e);
//			request.setAttribute("msg", "删除用户失败");
//		}
//		return list(request);
//	}
//	
//	public String changePassword() throws Exception {
//		return "changePassword";
//	}
//	
//	 
	@RequestMapping(value = "view")
	public String view(HttpServletRequest request,@RequestParam Long id) throws Exception {
		
		Apk apk = apkManager.get(id);
		request.setAttribute("apk", apk);
		return "ver/apk/view";
 	}
//	
//	public String batchDelete() throws Exception {
//		if(ids!=null){
//			int num = userManager.batchDelete(ids);
//			//addActionMessage("已经删除"+num+"个用户");			
//		}
//		return "";
//	}
//
//	//-- 其他Action函数 --//
//	/**
//	 * 支持使用Jquery.validate Ajax检验用户名是否重复.
//	 */
//	@RequestMapping(value="checkUsername")
//	public String checkUsername(HttpServletRequest request,HttpServletResponse response) throws IOException {
//		//HttpServletRequest request = ServletActionContext.getRequest();
//		String newUsername = request.getParameter("username");
//		String oldUsername = request.getParameter("oldUsername");
//		if (userManager.isUsernameUnique(newUsername, oldUsername)) {
//			response.getWriter().write("true");
//		} else {
//			response.getWriter().write("false");
// 		}
//		response.getWriter().flush();
//		//因为直接输出内容而不经过jsp,因此返回null.
//		return null;
//	}

//	//-- 页面属性访问函数 --//
//	/**
//	 * list页面显示用户分页列表.
//	 */
//	public Page<VUser> getPage() {
//		return page;
//	}
//
//	/**
//	 * input页面显示所有角色列表.
//	 */
//	public List<Role> getAllRoleList() {
//		return roleManager.getAll("id", true);
//	}
//
//	/**
//	 * input页面显示用户拥有的角色.
//	 */
//	public List<Long> getCheckedRoleIds() {
//		return checkedRoleIds;
//	}
//
//	/**
//	 * input页面提交用户拥有的角色.
//	 */
//	public void setCheckedRoleIds(List<Long> checkedRoleIds) {
//		this.checkedRoleIds = checkedRoleIds;
//	}
//	
//	@Override
//	public String toString() {
//		return ToStringBuilder.reflectionToString(this);
//	}

}
