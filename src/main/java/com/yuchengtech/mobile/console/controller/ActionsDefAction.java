package com.yuchengtech.mobile.console.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yuchengtech.mobile.console.dao.account.ActionsDefDao;
import com.yuchengtech.mobile.console.entity.account.ActionsDef;
import com.yuchengtech.mobile.console.service.account.ActionsDefManager;

@Controller
@RequestMapping(value = "/url")
public class ActionsDefAction {
	
	

		@Autowired
		private ActionsDefManager ActionsDefManager ;
		

		@RequestMapping(value = "list")
		public String list(HttpServletRequest request)  throws Exception {
			
				
			List<ActionsDef> list = ActionsDefManager.getAll();
			request.setAttribute("list", list);
			  
				return "sys/acl/role/demo";
	}
}
