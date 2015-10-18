/******************************************************************************
 * 
 * 北京宇信易诚科技有限公司-移动平台
 *
 * Copyright © 2012 Yucheng Technologies Limited All Rights Reserved.
 * 
 * Created on 2012-10-29 17:46:30
 *
 *******************************************************************************/
package com.yuchengtech.mobile.console.service.log;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.core.orm.hibernate.HibernateDao;
import com.yuchengtech.core.utils.reflection.ReflectionUtils;
import com.yuchengtech.mobile.console.dao.log.OptLogDao;
import com.yuchengtech.mobile.console.entity.log.OptLog;
import com.yuchengtech.mobile.console.service.EntityManager;
import com.yuchengtech.mobile.console.utils.StringUtil;
/**
 * 操作日志业务逻辑处理类，Spring Bean采用注解方式来定义，默认将类中的所有函数纳入事务管理.
 *
 * @version 1.0
 *
 * @author mark
 */
@Service
@Transactional
public class OptLogManager extends EntityManager<OptLog, Long>{
	
	@Autowired
	private OptLogDao optLogDao;

	public void setOptLogDao(OptLogDao optLogDao) {
		this.optLogDao = optLogDao;
	}

	@Override
	protected HibernateDao<OptLog, Long> getEntityDao() {
		return optLogDao;
	}
	
	public int batchDelete(Long[] ids) {
		return optLogDao.batchDelete(ids);
	}
	
	public boolean isUnique(String propertyName, String strValue) {
		Object value = StringUtil.convertType(strValue,ReflectionUtils.getFieldType(OptLog.class, propertyName));
		Long count = optLogDao.countByProperty(propertyName,value);
		if (count==0L) {
			return true;
		}
		return false;
	}
	
	public List<OptLog> findOptLogOrderby(String hql) {
		return optLogDao.find(hql);
	}
	
}
