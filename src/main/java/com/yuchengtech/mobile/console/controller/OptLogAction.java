/******************************************************************************
 * 
 * 北京宇信易诚科技有限公司-移动平台
 *
 * Copyright © 2012 Yucheng Technologies Limited All Rights Reserved.
 * 
 * Created on 2012-10-29 17:46:31
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
import com.yuchengtech.mobile.console.service.log.OptLogManager;
import com.yuchengtech.mobile.console.utils.excel.ExcelExporter;
/**
 * 操作日志Action类,负责请求的处理及转发.
 *
 * @version 1.0
 *
 * @author mark
 */
//@Namespace("/log")
//@Results( {
//	@Result(name = CrudActionSupport.RELOAD, location = "opt-log.action", type = "redirect"),
//	@Result(name = CrudActionSupport.INPUT, location = "/WEB-INF/pages/log/OptLog/form.jsp"),
//	@Result(name = CrudActionSupport.VIEW, location = "/WEB-INF/pages/log/OptLog/view.jsp"),
//	@Result(name = CrudActionSupport.SUCCESS, location = "/WEB-INF/pages/log/OptLog/list.jsp")
//})
@Controller
@RequestMapping(value = "/opt-log")
public class OptLogAction {

 	
	 
	private Page<OptLog> page = new Page<OptLog>(Constants.PAGE_SIZE);
	
	@Autowired
	private OptLogManager optLogManager;

	 
	
	 
	
	public Page<OptLog> getPage() {
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
		page = optLogManager.findPage(page, filters);
		request.setAttribute("page", page);
		return "log/OptLog/list";
	}

	@RequestMapping(value="exportExcel")
	public String exportExcel(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String operator = request.getParameter("filter_LIKES_operator");
		String time = request.getParameter("filter_LIKES_time");	
		StringBuilder builder = new StringBuilder("from OptLog where 1=1");
		if (StringUtils.isNotBlank(operator)) {
			builder.append(" and operator like '%").append(operator).append("%'");
			request.setAttribute("filter_LIKES_operator", operator);
		}
		if (StringUtils.isNotBlank(time)) {
			builder.append(" and time like '%").append(time).append("%'");
			request.setAttribute("filter_LIKES_time", time);
		}
		builder.append(" order by id desc");
		List<OptLog> data = optLogManager.findOptLogOrderby(builder.toString());	
		//生成Excel文件.
		Workbook wb = new ExcelExporter().export("操作日志", data);		
		//输出Excel文件.
		//HttpServletResponse response = Struts2Utils.getResponse();
		response.setContentType(ServletUtils.EXCEL_TYPE);
		ServletUtils.setFileDownloadHeader(response, "操作日志.xls");		
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
//			optLogManager.save(entity);
//		}else {
//			optLogManager.update(entity);
//		}
//		addActionMessage("保存操作日志成功");
//		return RELOAD;
//	}
//
	@RequestMapping(value="delete")
	public String delete(HttpServletRequest request,@RequestParam Long id) throws Exception {
		optLogManager.delete(id);
		request.setAttribute("msg", "删除操作日志成功");
		//addActionMessage("删除操作日志成功");
		return list(request);
	}
 
	@RequestMapping(value="view")
	public String view(HttpServletRequest request,@RequestParam Long id) throws Exception {
		OptLog entity = optLogManager.get(id);
		request.setAttribute("entity", entity);
		return "log/OptLog/view";
	}
	
	
	@RequestMapping(value="batchDelete")
	public String batchDelete(HttpServletRequest request,@RequestParam Long[] ids) throws Exception {
		if(ids!=null){
			int num = optLogManager.batchDelete(ids);
			request.setAttribute("msg","已经删除"+num+"条操作日志信息");	
 		}
		return list(request);
	}
//	
//	public String checkUnique() throws Exception {
//		HttpServletRequest request = ServletActionContext.getRequest();
//		String propertyName = request.getParameter("propertyName");
//		String value = request.getParameter(propertyName);
//		if (optLogManager.isUnique(propertyName, value)) {
//			Struts2Utils.renderText("true");
//		} else {
//			Struts2Utils.renderText("false");
//		}
//		//因为直接输出内容而不经过jsp,因此返回null.
//		return null;
//	}	
//
//	@Autowired
//	public void setOptLogManager(OptLogManager optLogManager) {
//		this.optLogManager = optLogManager;
//	}
}
