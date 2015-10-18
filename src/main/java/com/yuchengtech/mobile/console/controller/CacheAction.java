package com.yuchengtech.mobile.console.controller;

import javax.servlet.http.HttpServletRequest;

import net.sf.ehcache.CacheManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/sys")
public class CacheAction {

	@Autowired
	private CacheManager ehcacheManager;
	
	@RequestMapping(value = "cache")
	public String execute() {	
		return "sys/cache/cache";
	}
	
	@RequestMapping(value = "flush")
	public String flush(HttpServletRequest request) throws Exception {
		String[] cacheNames = ehcacheManager.getCacheNames();
		for (String name:cacheNames) {
			ehcacheManager.getCache(name).flush();
		}
		request.setAttribute("msg", "系统缓存已经刷新");
		return "sys/cache/cache";
	}
	
}