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

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.core.orm.hibernate.HibernateDao;
import com.yuchengtech.core.utils.reflection.ReflectionUtils;
import com.yuchengtech.core.utils.security.SpringSecurityUtils;
import com.yuchengtech.mobile.console.common.EntityInfo;
import com.yuchengtech.mobile.console.common.ModuleInfo;
import com.yuchengtech.mobile.console.dao.account.RoleDao;
import com.yuchengtech.mobile.console.entity.account.Role;
import com.yuchengtech.mobile.console.service.EntityManager;
import com.yuchengtech.mobile.console.service.ServiceException;
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
@EntityInfo(name=ModuleInfo.Role, identityFiled = "name")
public class RoleManager extends EntityManager<Role, Long>{
	
	@Autowired
	private RoleDao roleDao;

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	@Override
	protected HibernateDao<Role, Long> getEntityDao() {
		return roleDao;
	}
	
	public int batchDelete(Long[] ids) {
		return roleDao.batchDelete(ids);
	}
	
	public boolean isUnique(String propertyName, String strValue) {
		Object value = StringUtil.convertType(strValue,ReflectionUtils.getFieldType(Role.class, propertyName));
		Long count = roleDao.countByProperty(propertyName,value);
		return count==0L;
	}
	
	public void delete(Role entity) {
		if (isAdminRole(entity.getId())) {
			logger.warn("操作员{}尝试删除超级管理员角色", SpringSecurityUtils.getCurrentUserName());
			throw new ServiceException("不能删除超级管理员角色");
		};
		roleDao.delete(entity);
	}
	
	public void delete(Long id) {
		if (isAdminRole(id)) {
			logger.warn("操作员{}尝试删除超级管理员角色", SpringSecurityUtils.getCurrentUserName());
			throw new ServiceException("不能删除超级管理员角色");
		};
		roleDao.delete(id);
	}

	private boolean isAdminRole(Long id) {
		return 1L==id;
	}
	
	public HashMap<String,Object> getRoleAndAuthor(Long id){
		HashMap<String,Object> RoleAndAuthorID=new HashMap<String,Object>(); 
		Role role=roleDao.get(id);
		RoleAndAuthorID.put("role", role);
		RoleAndAuthorID.put("AuthorID", role.getAuthIds());
		return RoleAndAuthorID;
	}
}
