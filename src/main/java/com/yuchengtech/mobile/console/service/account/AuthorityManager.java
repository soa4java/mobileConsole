/******************************************************************************
 * 
 * 北京宇信易诚科技有限公司-移动平台
 *
 * Copyright © 2012 Yucheng Technologies Limited All Rights Reserved.
 * 
 * Created on 2012-11-10 22:34:58
 *
 *******************************************************************************/
package com.yuchengtech.mobile.console.service.account;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ArrayListMultimap;
import com.yuchengtech.core.orm.hibernate.HibernateDao;
import com.yuchengtech.core.utils.reflection.ReflectionUtils;
import com.yuchengtech.mobile.console.common.EntityInfo;
import com.yuchengtech.mobile.console.common.ModuleInfo;
import com.yuchengtech.mobile.console.dao.account.AuthorityDao;
import com.yuchengtech.mobile.console.dao.account.VAuthorityDao;
import com.yuchengtech.mobile.console.entity.account.Authority;
import com.yuchengtech.mobile.console.entity.account.VAuthority;
import com.yuchengtech.mobile.console.service.EntityManager;
import com.yuchengtech.mobile.console.utils.StringUtil;
/**
 * 系统资源业务逻辑处理类，Spring Bean采用注解方式来定义，默认将类中的所有函数纳入事务管理.
 *
 * @version 1.0
 *
 * @author mark
 */
@Service
@Transactional
@EntityInfo(name=ModuleInfo.Authority, identityFiled = "name")
public class AuthorityManager extends EntityManager<Authority, Long>{
	
	@Autowired
	private AuthorityDao authorityDao;	
	@Autowired
	private VAuthorityDao vAuthorityDao;

	@Override
	protected HibernateDao<Authority, Long> getEntityDao() {
		return authorityDao;
	}
	
	public int batchDelete(Long[] ids) {
		return authorityDao.batchDelete(ids);
	}
	
	public boolean isUnique(String propertyName, String strValue) {
		Object value = StringUtil.convertType(strValue,ReflectionUtils.getFieldType(Authority.class, propertyName));
		Long count = authorityDao.countByProperty(propertyName,value);
		return count==0L;
	}
	
	public List<Authority> loadAuthorityTree() {
		return authorityDao.getAllMenu();
	}
	
	public VAuthority getVAuthority(Long id) {
		return vAuthorityDao.get(id);
	}

	public VAuthority getSimpleVAuthority(Long id) {
		return vAuthorityDao.getSimpleVAuthority(id);
	}
	
	public List<Authority> loadOperation(Long id) {
		return authorityDao.loadOperation(id);
	}
	
	@Override
	public void delete(Authority entity) {
		authorityDao.deleteOperation(entity.getId());
		authorityDao.delete(entity);
	}
	
	public ArrayListMultimap<String, Authority> getAllMenu() {
		List<Authority> list = authorityDao.getAllMenu();
		ArrayListMultimap<String, Authority> map = ArrayListMultimap.create();
		for (Authority a:list) {
			map.put(String.valueOf(a.getPid()), a);
		}
		return map;
	}
	
	public ArrayListMultimap<String, Authority> getAllOpt() {
		List<Authority> list = authorityDao.getAllOpt();
		ArrayListMultimap<String, Authority> map = ArrayListMultimap.create();
		for (Authority a:list) {
			map.put(String.valueOf(a.getPid()), a);
		}
		return map;
	}
	
	public String dealAuthority(HttpServletRequest request){
		return null;
	}
	
}
