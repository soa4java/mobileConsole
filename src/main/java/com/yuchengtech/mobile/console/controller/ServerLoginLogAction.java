/******************************************************************************
 * 
 * 北京宇信易诚科技有限公司-移动平台
 *
 * Copyright © 2012 Yucheng Technologies Limited All Rights Reserved.
 * 
 * Created on 2012-10-7 20:53:39
 *
 *******************************************************************************/
package com.yuchengtech.mobile.console.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yuchengtech.core.orm.Page;
import com.yuchengtech.core.orm.PropertyFilter;
import com.yuchengtech.core.utils.web.ServletUtils;
import com.yuchengtech.mobile.console.common.Constants;
import com.yuchengtech.mobile.console.entity.log.OptLog;
import com.yuchengtech.mobile.console.entity.log.VServerLoginLog;
import com.yuchengtech.mobile.console.service.log.OptLogManager;
import com.yuchengtech.mobile.console.service.log.ServerLoginLogManager;
import com.yuchengtech.mobile.console.utils.excel.ExcelExporter;
/**
 * 登录日志Action类,负责请求的处理及转发.
 *
 * @version 1.0
 *
 * @author mark
 */
@Controller
@RequestMapping(value = "/server-login-log")
public class ServerLoginLogAction{

 	
	 	
	private Page<VServerLoginLog> page = new Page<VServerLoginLog>(Constants.PAGE_SIZE);
	@Autowired
	private ServerLoginLogManager loginLogManager;
	@Autowired
	private OptLogManager optLogManager;

	 
	
	 
	public Page<VServerLoginLog> getPage() {
		return page;
	}

	 

	//-- CRUD Action 函数 --//
	@RequestMapping(value="list")
	public String list(HttpServletRequest request) throws Exception {
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
		page = loginLogManager.findVServerLoginLogByPage(page, filters);
		request.setAttribute("page",page );
		return "log/LoginLog/list";
	}
	
	@RequestMapping(value="exportExcel")
	public String exportExcel(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String username = request.getParameter("filter_LIKES_username");
		String status = request.getParameter("filter_EQS_status");	
		String empName = request.getParameter("filter_LIKES_empName");	
		String loginTime = request.getParameter("filter_LIKES_loginTime");

		StringBuilder builder = new StringBuilder("from VServerLoginLog where 1=1");
		if (StringUtils.isNotBlank(username)) {
			builder.append(" and username like '%").append(username).append("%'");
			request.setAttribute("filter_LIKES_username", username);
		}
		if (StringUtils.isNotBlank(status)) {
			builder.append(" and status like '%").append(status).append("%'");
			request.setAttribute("filter_EQS_status", status);
		}
		if (StringUtils.isNotBlank(empName)) {
			builder.append(" and emp_name like '%").append(empName).append("%'");
			request.setAttribute("filter_LIKES_empName", empName);
		}
		if (StringUtils.isNotBlank(loginTime)) {
			builder.append(" and login_time like '%").append(loginTime).append("%'");
			request.setAttribute("filter_LIKES_loginTime", loginTime);
		}
		builder.append(" order by id desc");
		
		List<VServerLoginLog> data = loginLogManager.findVServerLoginLogOrderby(builder.toString());	
		//生成Excel文件.
		Workbook wb = new ExcelExporter().export("后台登录日志", data);		
		//输出Excel文件.
		//HttpServletResponse response = Struts2Utils.getResponse();
		response.setContentType(ServletUtils.EXCEL_TYPE);
		ServletUtils.setFileDownloadHeader(response, "后台登录日志.xls");		
		wb.write(response.getOutputStream());
		response.getOutputStream().flush();
		return null;
	}
	

//	public String input() throws Exception {
//		return INPUT;
//	}
//
//	public String save() throws Exception {
//		if (id==null) {		
//			loginLogManager.save(entity);
//		}else {
//			loginLogManager.update(entity);
//		}
//		addActionMessage("保存登录日志成功");
//		return RELOAD;
//	}
//
	@RequestMapping(value="delete")
	public String delete(HttpServletRequest request,@RequestParam Long id) throws Exception {
		loginLogManager.delete(id);
		request.setAttribute("msg", "删除登录日志成功");
		return list(request);
	}
//
//	public String view() throws Exception {
//		return VIEW;
//	}
//	
	@RequestMapping(value="viewLog")
	public String viewLog(HttpServletRequest request,@RequestParam Long id) throws Exception {
		VServerLoginLog entity = loginLogManager.getVServerLoginLog(id);
		List<OptLog> list = optLogManager.findBy("loginId", id);
		request.setAttribute("entity", entity);
		request.setAttribute("list", list);
		return "log/LoginLog/view";
	}
//	
//	
//	
	@RequestMapping(value="batchDelete")
	public String batchDelete(HttpServletRequest request,@RequestParam Long[] ids) throws Exception {
		if(ids!=null){
			int num = loginLogManager.batchDelete(ids);
			request.setAttribute("msg","已经删除"+num+"条登录日志信息");			
		}
		return list(request);
	}
//	
//	public String checkUnique() throws Exception {
//		HttpServletRequest request = ServletActionContext.getRequest();
//		String propertyName = request.getParameter("propertyName");
//		String value = request.getParameter(propertyName);
//		if (loginLogManager.isUnique(propertyName, value)) {
//			Struts2Utils.renderText("true");
//		} else {
//			Struts2Utils.renderText("false");
//		}
//		//因为直接输出内容而不经过jsp,因此返回null.
//		return null;
//	}	

}
