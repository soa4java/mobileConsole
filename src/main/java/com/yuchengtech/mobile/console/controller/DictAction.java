package com.yuchengtech.mobile.console.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import antlr.StringUtils;

import com.yuchengtech.core.orm.Page;
import com.yuchengtech.core.orm.PropertyFilter;
import com.yuchengtech.mobile.console.common.Constants;
import com.yuchengtech.mobile.console.entity.dict.Dict;
import com.yuchengtech.mobile.console.entity.dict.DictItem;
import com.yuchengtech.mobile.console.service.dict.DictItemManager;
import com.yuchengtech.mobile.console.service.dict.DictManager;

/**
 * 字典管理Action.
 */
//@Namespace("/dict")
//@Results( {
//	@Result(name = CrudActionSupport.RELOAD, location = "dict.action", type = "redirect"),
//	@Result(name = CrudActionSupport.INPUT, location = "/WEB-INF/pages/sys/dict/dict/form.jsp"),
//	@Result(name = CrudActionSupport.VIEW, location = "/WEB-INF/pages/sys/dict/dict/view.jsp"),
//	@Result(name = CrudActionSupport.SUCCESS, location = "/WEB-INF/pages/sys/dict/dict/list.jsp")
//})
@Controller
@RequestMapping(value = "/dict")
public class DictAction {


	private Logger logger=LoggerFactory.getLogger(DictAction.class);
	@Autowired
	private DictManager dictManager;
	@Autowired
	private DictItemManager dictItemManager;
	//-- 页面属性 --//
 
	//-- CRUD Action 函数 --//
	 @RequestMapping(value="list")
	public String list(HttpServletRequest request) throws Exception {
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
	 	Page<Dict> page = new Page<Dict>(Constants.PAGE_SIZE);//每页5条记录
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
		
		page = dictManager.findPage(page, filters);
		request.setAttribute("page", page);
		return "sys/dict/dict/list";
	}

	@RequestMapping(value="input")
	public String input(HttpServletRequest request,@RequestParam(required=false) String id){
		if(id!=null){
			Dict entity = dictManager.get(id);
			request.setAttribute("entity", entity);
			request.setAttribute("id", id);
		}
		return "sys/dict/dict/form";
	}

	@RequestMapping(value="save")
	public @ResponseBody Map<String, Object> save(HttpServletRequest request,@RequestBody Dict entity 
			) throws Exception {
		Map<String,Object> result = new HashMap<String,Object>();
		dictManager.save(entity);
		result.put("success", true);
		result.put("msg", "提交成功");
		return result;
	}

	@RequestMapping(value="update")
	public @ResponseBody Map<String, Object> update(HttpServletRequest request,@RequestBody Dict entity 
			) throws Exception {
		Map<String,Object> result = new HashMap<String,Object>();
		dictManager.update(entity);
		result.put("success", true);
		result.put("msg", "更新成功");
		return result;
	}

	
	@RequestMapping(value="delete")
	public String delete(HttpServletRequest request,@RequestParam String id) throws Exception {
		dictManager.delete(id);
		request.setAttribute("msg", "删除字典成功");
		//addActionMessage("删除字典成功");
		return list(request);
	}
//
	 
	@RequestMapping(value="view") 
	public String view(HttpServletRequest request,@RequestParam String id) throws Exception {
		Dict entity = dictManager.get(id);
		request.setAttribute("entity", entity);
		return "sys/dict/dict/view";
	}
 

//	
//	/**
//	 * 支持使用Jquery.validate Ajax检验字典代码是否重复.
//	 */
//	public String checkDictCode() {
//		HttpServletRequest request = ServletActionContext.getRequest();
//		String code = request.getParameter("code");
//		if (dictManager.isPropertyValueExist("code", code)) {
//			//验证未通过
//			Struts2Utils.renderText("false");
//		} else {
//			//验证通过
//			Struts2Utils.renderText("true");
//		}
//		//因为直接输出内容而不经过jsp,因此返回null.
//		return null;
//	}
}
