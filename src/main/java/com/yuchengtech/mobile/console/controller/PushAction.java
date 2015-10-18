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




import com.yuchengtech.core.orm.Page;
import com.yuchengtech.core.orm.PropertyFilter;
import com.yuchengtech.mobile.console.common.Constants;
import com.yuchengtech.mobile.console.entity.dict.DictItem;
import com.yuchengtech.mobile.console.entity.push.TblPushinfo;
import com.yuchengtech.mobile.console.service.dict.DictItemManager;
import com.yuchengtech.mobile.console.service.push.PushInfoManager;

/**
 * 消息推送的Action.
 */
@Controller
@RequestMapping(value = "/push")
public class PushAction {


	private Logger logger=LoggerFactory.getLogger(PushAction.class);
	@Autowired
	private PushInfoManager pushInfoManager;
	@Autowired
	private DictItemManager dictItemManager;

	
	//-- CRUD Action 函数 --//
	 @RequestMapping(value="list")
	public String list(HttpServletRequest request) throws Exception {
		 List<DictItem> itemList = dictItemManager.findDictItemList("PUSH_TYPE");
		 request.setAttribute("itemList", itemList);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
	 	Page<TblPushinfo> page = new Page<TblPushinfo>(Constants.PAGE_SIZE);//每页5条记录
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
		
		page = pushInfoManager.findPage(page, filters);
		request.setAttribute("page", page);
		return "sys/push/list";
	}

	@RequestMapping(value="input")
	public String input(HttpServletRequest request){
		List<DictItem> itemList = dictItemManager.findDictItemList("PUSH_TYPE");
		request.setAttribute("itemList", itemList);
		return "sys/push/form";
	}

	@RequestMapping(value="save")
	public String save(HttpServletRequest request,TblPushinfo entity 
			) throws Exception {
		entity.setPushdate(new java.sql.Timestamp(new java.util.Date().getTime()));
		pushInfoManager.pushInfo(entity);
		request.setAttribute("success", true);
		request.setAttribute("msg", "提交成功");
		return list(request);
	}

	 
	@RequestMapping(value="view") 
	public String view(HttpServletRequest request,@RequestParam Long id) throws Exception {
		TblPushinfo entity = pushInfoManager.get(id);
		request.setAttribute("entity", entity);
		return "sys/push/view";
	}
}
