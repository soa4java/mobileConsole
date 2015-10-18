/******************************************************************************
 * 
 * 北京宇信易诚科技有限公司-移动平台
 *
 * Copyright © 2012 Yucheng Technologies Limited All Rights Reserved.
 * 
 * Created on 2012-10-7 20:53:38
 *
 *******************************************************************************/
package com.yuchengtech.mobile.console.service.log;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.core.orm.Page;
import com.yuchengtech.core.orm.PropertyFilter;
import com.yuchengtech.core.orm.hibernate.HibernateDao;
import com.yuchengtech.core.utils.reflection.ReflectionUtils;
import com.yuchengtech.mobile.console.common.EntityInfo;
import com.yuchengtech.mobile.console.common.ModuleInfo;
import com.yuchengtech.mobile.console.dao.log.ServerLoginLogDao;
import com.yuchengtech.mobile.console.dao.log.VServerLoginLogDao;
import com.yuchengtech.mobile.console.entity.log.ServerLoginLog;
import com.yuchengtech.mobile.console.entity.log.VServerLoginLog;
import com.yuchengtech.mobile.console.service.EntityManager;
import com.yuchengtech.mobile.console.utils.StringUtil;
/**
 * 登录日志业务逻辑处理类，Spring Bean采用注解方式来定义，默认将类中的所有函数纳入事务管理.
 *
 * @version 1.0
 *
 * @author mark
 */
@Service
@Transactional
@EntityInfo(name=ModuleInfo.LoginLog, identityFiled = "username")
public class ServerLoginLogManager extends EntityManager<ServerLoginLog, Long>{
	
	@Autowired
	private ServerLoginLogDao loginLogDao;
	@Autowired
	private VServerLoginLogDao vServerLoginLogDao;

	@Override
	protected HibernateDao<ServerLoginLog, Long> getEntityDao() {
		return loginLogDao;
	}
	
	public int batchDelete(Long[] ids) {
		return loginLogDao.batchDelete(ids);
	}
	
	public void  insert(ServerLoginLog entity) {
		getEntityDao().save(entity);
	}
	
	public boolean isUnique(String propertyName, String strValue) {
		Object value = StringUtil.convertType(strValue,ReflectionUtils.getFieldType(ServerLoginLog.class, propertyName));
		Long count = loginLogDao.countByProperty(propertyName,value);
		if (count==0L) {
			return true;
		}
		return false;
	}

	public Page<VServerLoginLog> findVServerLoginLogByPage(Page<VServerLoginLog> page,List<PropertyFilter> filters) {
		return vServerLoginLogDao.findPage(page, filters);
	}

	public List<VServerLoginLog> findVServerLoginLog(List<PropertyFilter> filters) {
		return vServerLoginLogDao.find(filters);
	}
	
	public List<VServerLoginLog> findVServerLoginLogOrderby(String hql) {
		return vServerLoginLogDao.find(hql);
	}
	
	public VServerLoginLog getVServerLoginLog(Long id) {
		return vServerLoginLogDao.get(id);
	}
	
}
