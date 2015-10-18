/******************************************************************************
 * 
 * 北京宇信易诚科技有限公司-移动平台
 *
 * Copyright © 2012 Yucheng Technologies Limited All Rights Reserved.
 * 
 * Created on 2012-11-22 0:32:38
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
import com.yuchengtech.mobile.console.dao.log.IpadLoginLogDao;
import com.yuchengtech.mobile.console.dao.log.VIpadLoginLogDao;
import com.yuchengtech.mobile.console.entity.log.IpadLoginLog;
import com.yuchengtech.mobile.console.entity.log.VIpadLoginLog;
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
public class IpadLoginLogManager extends EntityManager<IpadLoginLog, Long>{
	
	@Autowired
	private IpadLoginLogDao ipadLoginLogDao;
	@Autowired
	private VIpadLoginLogDao vIpadLoginLogDao;


	@Override
	protected HibernateDao<IpadLoginLog, Long> getEntityDao() {
		return ipadLoginLogDao;
	}
	
	public int batchDelete(Long[] ids) {
		return ipadLoginLogDao.batchDelete(ids);
	}
	
	public boolean isUnique(String propertyName, String strValue) {
		Object value = StringUtil.convertType(strValue,ReflectionUtils.getFieldType(IpadLoginLog.class, propertyName));
		Long count = ipadLoginLogDao.countByProperty(propertyName,value);
		if (count==0L) {
			return true;
		}
		return false;
	}
	public Page<VIpadLoginLog> findVIpadLoginLogByPage(Page<VIpadLoginLog> page,List<PropertyFilter> filters) {
		return vIpadLoginLogDao.findPage(page, filters);
	}
	
	public List<VIpadLoginLog> findVIpadLoginLog(List<PropertyFilter> filters) {
		return vIpadLoginLogDao.find(filters);
	}
	
	public List<VIpadLoginLog> findVIpadLoginLogOrderby(String hql) {
		return vIpadLoginLogDao.find(hql);
	}
	
	public VIpadLoginLog getVIpadLoginLog(Long id) {
		return vIpadLoginLogDao.get(id);		
	}
	
	public List<VIpadLoginLog> getLog(String mon, String deptCode) {
		return vIpadLoginLogDao.getLog(mon,deptCode);
	}
	
}
