package com.yuchengtech.mobile.console.controller;

import java.util.List;













import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;











import com.yuchengtech.core.orm.Page;
import com.yuchengtech.core.orm.PropertyFilter;
import com.yuchengtech.mobile.console.common.Constants;
import com.yuchengtech.mobile.console.entity.lianainterface.TblLianainterface;
import com.yuchengtech.mobile.console.service.lianainterface.LianaInterfaceManeger;

/**
 */
@Controller
@RequestMapping(value = "/interface")
public class LianaInterfaceAction {


	private Logger logger=LoggerFactory.getLogger(LianaInterfaceAction.class);
	@Autowired
	private LianaInterfaceManeger lianaInterfaceManeger; 
	
	//-- CRUD Action 函数 --//
	 @RequestMapping(value="list")
	public String list(HttpServletRequest request) throws Exception {
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
	 	Page<TblLianainterface> page = new Page<TblLianainterface>(Constants.PAGE_SIZE);//每页5条记录
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
		
		page = lianaInterfaceManeger.findPage(page, filters);
		request.setAttribute("page", page);
		return "sys/lianaInterface/list";
	}

	@RequestMapping(value="input")
	public String input(HttpServletRequest request,@RequestParam(required=false) Long id){
		if(id!=null){
			TblLianainterface entity = lianaInterfaceManeger.get(id);
			request.setAttribute("entity", entity);
			request.setAttribute("id", id);
		}
		return "sys/lianaInterface/form";
	}

	@RequestMapping(value="save")
	public String save(HttpServletRequest request,TblLianainterface entity 
			) throws Exception {
		lianaInterfaceManeger.saveOrUpdate(entity);
		request.setAttribute("success", true);
		request.setAttribute("msg", "提交成功");
		return list(request);
	}

	@RequestMapping(value="delete")
	public String delete(HttpServletRequest request,@RequestParam Long id) throws Exception {
		lianaInterfaceManeger.delete(id);
		request.setAttribute("msg", "删除成功");
		//addActionMessage("删除字典成功");
		return list(request);
	}
	 
}
