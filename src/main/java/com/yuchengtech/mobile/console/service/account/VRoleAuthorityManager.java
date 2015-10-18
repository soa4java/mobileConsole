/******************************************************************************
 * 
 * 北京宇信易诚科技有限公司-移动平台
 *
 * Copyright © 2012 Yucheng Technologies Limited All Rights Reserved.
 * 
 * Created on 2012-11-19 16:51:34
 *
 *******************************************************************************/
package com.yuchengtech.mobile.console.service.account;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import com.yuchengtech.core.orm.hibernate.HibernateDao;
import com.yuchengtech.mobile.console.common.status.AuthorityType;
import com.yuchengtech.mobile.console.dao.account.VRoleAuthorityDao;
import com.yuchengtech.mobile.console.entity.account.VRoleAuthority;
import com.yuchengtech.mobile.console.service.EntityManager;
/**
 * VRoleAuthority业务逻辑处理类，Spring Bean采用注解方式来定义，默认将类中的所有函数纳入事务管理.
 *
 * @version 1.0
 *
 * @author mark
 */
@Service
@Transactional
public class VRoleAuthorityManager extends EntityManager<VRoleAuthority, Long>{
	
	@Autowired
	private VRoleAuthorityDao vRoleAuthorityDao;
	@Autowired
	private CacheManager ehcacheManager;

	public void setVRoleAuthorityDao(VRoleAuthorityDao vRoleAuthorityDao) {
		this.vRoleAuthorityDao = vRoleAuthorityDao;
	}

	@Override
	protected HibernateDao<VRoleAuthority, Long> getEntityDao() {
		return vRoleAuthorityDao;
	}
	
	@SuppressWarnings("unchecked")
	public ListMultimap<String, VRoleAuthority> loadMenuAuthorityByRole(List<Long> ids) {	
		ListMultimap<String, VRoleAuthority> map = null;
		if (!ids.isEmpty()) {
			String key = new StringBuilder(AuthorityType.MENU).append("_").append(StringUtils.join(ids, '_')).toString();
			Cache cache = ehcacheManager.getCache("MenuAuthority");
			Element element = cache.get(key);
			if (element == null) {
				List<VRoleAuthority> list = vRoleAuthorityDao.loadMenuAuthorityByRole(ids);
				map = list2multimap(list);
				cache.put(new Element(key, map));
			} else {
				map = (ListMultimap<String, VRoleAuthority>) element.getObjectValue();
			}
		}
		return map;
	}
	
	private ListMultimap<String, VRoleAuthority> list2multimap(List<VRoleAuthority> list) {
		ListMultimap<String, VRoleAuthority> map = ArrayListMultimap.create();
		Set<Long> authIds = new HashSet<Long>();
		for (VRoleAuthority authority:list) {
			if(!authIds.contains(authority.getAuthId())){
				authIds.add(authority.getAuthId());			
				map.put(String.valueOf(authority.getPid()), authority);
			}
		}
		list.clear();
		authIds.clear();
		return map;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Boolean> loadOperationAuthorityByRole(List<Long> ids) {
		Map<String, Boolean> map  = null;		
		if (!ids.isEmpty()) {
			String key = new StringBuilder(AuthorityType.OPT).append("_").append(StringUtils.join(ids, '_')).toString();
			Cache cache = ehcacheManager.getCache("OperationAuthority");
			Element element = cache.get(key);
			if (element == null) {
				List<String> list = vRoleAuthorityDao.loadOperationAuthorityByRole(ids);
				map = list2map(list);
				cache.put(new Element(key, map));
			} else {
				map = (Map<String, Boolean>) element.getObjectValue();
			}
		}
		return map;
	}

	private Map<String, Boolean> list2map(List<String> list) {
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		for (String code:list) {
			map.put(code, true);
		}
		list = null;
		return map;
	}
	
}
