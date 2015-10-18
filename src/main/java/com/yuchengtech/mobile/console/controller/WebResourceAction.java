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
import com.yuchengtech.mobile.console.entity.ver.WebResource;
import com.yuchengtech.mobile.console.entity.ver.WebResourceIncr;
import com.yuchengtech.mobile.console.service.ServiceException;
import com.yuchengtech.mobile.console.service.ver.WebResourceIncrManager;
import com.yuchengtech.mobile.console.service.ver.WebResourceManager;

/**
 * 用户管理Action.
 * 
 * 使用Struts2 convention-plugin annotation定义Action参数. 演示带分页的管理界面.
 */
@Controller
@RequestMapping(value = "/web-resource")
public class WebResourceAction {

	@Autowired
	private WebResourceManager webResourceManager;
	@Autowired
	private WebResourceIncrManager webResourceIncrManager;
	

	protected Logger logger = LoggerFactory.getLogger(getClass());

	// -- CRUD Action 函数 --//

	@RequestMapping(value = "list")
	public String list(HttpServletRequest request) {

		List<PropertyFilter> filters = PropertyFilter
				.buildFromHttpRequest(request);
		Page<WebResource> page = new Page<WebResource>(Constants.PAGE_SIZE);

		// 设置默认排序方式
		if (!page.isOrderBySetted()) {
			page.setOrderBy("id");
			page.setOrder(Page.DESC);
		}
		if (request.getParameter("page.pageNo") != null) {
			page.setPageNo(Integer.parseInt(request.getParameter("page.pageNo")));
			page.setOrderBy(request.getParameter("page.orderBy"));
			page.setOrder(request.getParameter("page.order"));
		}
		page = webResourceManager.findPage(page, filters);
		request.setAttribute("page", page);
		return "ver/res/list";
	}

	@RequestMapping(value = "input")
	public String input(HttpServletRequest request,
			@RequestParam(required = false) Long id) {
		return "ver/res/form";
	}

	@RequestMapping(value = "save")
	public String save(@RequestParam MultipartFile resFile,
			HttpServletRequest request) {

		String ver = request.getParameter("ver");
		String name = request.getParameter("name");
		String isForceUpdate = request.getParameter("isForceUpdate");
		String des = request.getParameter("des");

		// 最新生成文件夹
		String realPath = request.getSession().getServletContext()
				.getRealPath("/");
		realPath=realPath.endsWith("/")?realPath.substring(0,realPath.length()-1):realPath;
		
		WebResource webResource = new WebResource();
		webResource.setVersion(ver);
		webResource.setForceUpdate(isForceUpdate);
		webResource.setDes(des);
		try {
			File file = File.createTempFile("webFix.", "zip");
			FileUtils.copyInputStreamToFile(resFile.getInputStream(), file);

			webResourceManager.saveWebResource(webResource, file, realPath);
		} catch (Exception e) {
			logger.error(e.getMessage());
			request.setAttribute("msg", e.getMessage());
		}

		return list(request);

	}
	@RequestMapping(value = "active")
	public String active(HttpServletRequest request, @RequestParam Long id)
			throws Exception {
		try {
			webResourceManager.ativekWebResource(id);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return list(request);
	}
	@RequestMapping(value = "delete")
	public String delete(HttpServletRequest request, @RequestParam Long id)
			throws Exception {
		try {
			webResourceManager.delete(id);
			request.setAttribute("msg", "删除资源成功");
		} catch (ServiceException e) {
			// logger.error(e.getMessage(), e);
			request.setAttribute("msg", "删除资源失败");
		}
		return list(request);
	}
	@RequestMapping(value = "rollback")
	public String rollback(HttpServletRequest request, @RequestParam Long vid)
			throws Exception {
		try {
			String realPath = request.getSession().getServletContext()
					.getRealPath("/");
			webResourceManager.rollbackWebResource(realPath,vid);
			request.setAttribute("msg", "删除资源成功");
		} catch (ServiceException e) {
			// logger.error(e.getMessage(), e);
			request.setAttribute("msg", "删除资源失败");
		}
		return list(request);
	}

	@RequestMapping(value = "view")
	public String view(HttpServletRequest request, @RequestParam Long id)
			throws Exception {

		WebResource wr = webResourceManager.get(id);
		request.setAttribute("webResource", wr);
		return "ver/res/view";
	}

	@RequestMapping(value = "viewFix")
	public String viewFix(HttpServletRequest request,long filter_EQL_vid)
			throws Exception {
		List<PropertyFilter> filters = PropertyFilter
				.buildFromHttpRequest(request);
		Page<WebResourceIncr> page = new Page<WebResourceIncr>(Constants.PAGE_SIZE);

		// 设置默认排序方式
		if (!page.isOrderBySetted()) {
			page.setOrderBy("id");
			page.setOrder(Page.DESC);
		}
		if (request.getParameter("page.pageNo") != null) {
			page.setPageNo(Integer.parseInt(request.getParameter("page.pageNo")));
			page.setOrderBy(request.getParameter("page.orderBy"));
			page.setOrder(request.getParameter("page.order"));
		}
		page = webResourceIncrManager.findPage(page, filters);
		request.setAttribute("page", page);
		request.setAttribute("filter_EQL_vid", filter_EQL_vid);
		request.setAttribute("baseline", request.getParameter("baseline"));
		
		return "ver/res/viewFix";
	}

	//
	@RequestMapping(value = "modifyForceStatus")
	public String modifyForceStatus(HttpServletRequest request,
			@RequestParam Long id, @RequestParam String forceStatus)
			throws Exception {
		WebResource wr = webResourceManager.get(id);
		wr.setForceUpdate(forceStatus);
		webResourceManager.save(wr);
		return list(request);
	}


	public static void main(String[] args) {
		String path = "D:\\apache-tomcat-6\\webapps\\mobileConsole\\upload\\20141027\\v2.0\\v0.0";
		System.out.println(path.substring(path.lastIndexOf("\\")));
	}
}
