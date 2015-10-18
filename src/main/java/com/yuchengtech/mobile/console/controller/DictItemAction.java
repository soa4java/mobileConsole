package com.yuchengtech.mobile.console.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;




import com.yuchengtech.core.orm.Page;
import com.yuchengtech.mobile.console.common.Constants;
import com.yuchengtech.mobile.console.entity.dict.Dict;
import com.yuchengtech.mobile.console.entity.dict.DictItem;
import com.yuchengtech.mobile.console.service.dict.DictItemManager;
import com.yuchengtech.mobile.console.service.dict.DictManager;

/**
 * 字典子项管理Action.
 */

@Controller
@RequestMapping(value = "/dictItem")
public class DictItemAction {


	private Logger logger=LoggerFactory.getLogger(DictItemAction.class);
	@Autowired
	private DictManager dictManager;
	@Autowired
	private DictItemManager dictItemManager;
	//-- 页面属性 --//
 


	 
	 @RequestMapping(value="list")
	public String list(HttpServletRequest request,String dictCode,String name,String code){
		Page<DictItem> page = new Page<DictItem>(Constants.PAGE_SIZE);//每页5条记录
		if(request.getSession().getAttribute("dictCode")==null){
			request.getSession().setAttribute("dictCode", dictCode);
		}else{
			dictCode=(String) request.getSession().getAttribute("dictCode");
		}
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
		
		StringBuilder builder = new StringBuilder("from DictItem where 1=1");
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(dictCode)) {
			builder.append(" and dict.code=:dictCode");
			map.put("dictCode", dictCode);				
		}
		if (StringUtils.isNotBlank(code)) {
			builder.append(" and code=:code");
			map.put("code", code);
		}
		if (StringUtils.isNotBlank(name)) {
			builder.append(" and name=:name");
			map.put("name", name);
		}
		page = dictItemManager.findPage(page, builder.toString(), map);
		
		request.setAttribute("page", page);
		request.setAttribute("dictCode", dictCode);
		
		return "sys/dict/dictitem/list";
	}
	
	

	@RequestMapping(value="input")
	public String input(HttpServletRequest request,@RequestParam(required=false) String id){
		String dictCode = (String) request.getSession().getAttribute("dictCode");
		request.setAttribute("dictCode", dictCode);
		if(StringUtils.isNotBlank(id)){
			DictItem entity = dictItemManager.get(Long.parseLong(id));
			request.setAttribute("entity", entity);
			request.setAttribute("id", id);
		}
		return "sys/dict/dictitem/form";
	}

	@RequestMapping(value="save")
	public String save(HttpServletRequest request,DictItem entity 
			) throws Exception {
		String dictCode = (String) request.getSession().getAttribute("dictCode");
		entity.setDict(new Dict(dictCode));
		if (entity.getId()!=null) {
			dictItemManager.update(entity);
		}else {
			dictItemManager.save(entity);
		}
		
		return list(request, dictCode, null, null);
	}


	
	@RequestMapping(value="delete")
	public String delete(HttpServletRequest request,String id) throws Exception {
		dictItemManager.delete(Long.parseLong(id));
		String dictCode = (String) request.getSession().getAttribute("dictCode");
		request.setAttribute("msg", "删除字典成功");
		//addActionMessage("删除字典成功");
		return list(request, dictCode, null, null);
	}
//
	 
	@RequestMapping(value="view") 
	public String view(HttpServletRequest request,@RequestParam String id) throws Exception {
		DictItem entity = dictItemManager.get(Long.parseLong(id));
		request.setAttribute("entity", entity);
		return "sys/dict/dictitem/view";
	}

}
