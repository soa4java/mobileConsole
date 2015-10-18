package com.yuchengtech.mobile.console.service;

import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import com.yuchengtech.mobile.console.common.Constants;
import com.yuchengtech.mobile.console.entity.ver.Param;
import com.yuchengtech.mobile.console.service.ver.ParamManager;
public class InitDataService implements InitializingBean, ServletContextAware {
	@Autowired
	private ParamManager paramManager;

	public void afterPropertiesSet() throws Exception {
		List<Param> list = paramManager.getAll();
		Constants.initData(list);
	}

	 public void setServletContext(ServletContext arg0) { }
}