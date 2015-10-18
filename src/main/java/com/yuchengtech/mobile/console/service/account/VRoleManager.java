/******************************************************************************
 * 
 * 北京宇信易诚科技有限公司-移动平台
 *
 * Copyright © 2012 Yucheng Technologies Limited All Rights Reserved.
 * 
 * Created on 2012-11-18 20:04:11
 *
 *******************************************************************************/
package com.yuchengtech.mobile.console.service.account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.core.orm.hibernate.HibernateDao;
import com.yuchengtech.mobile.console.dao.account.VRoleDao;
import com.yuchengtech.mobile.console.entity.account.VRole;
import com.yuchengtech.mobile.console.service.EntityManager;
/**
 * VRole业务逻辑处理类，Spring Bean采用注解方式来定义，默认将类中的所有函数纳入事务管理.
 *
 * @version 1.0
 *
 * @author mark
 */
@Service
@Transactional
public class VRoleManager extends EntityManager<VRole, Long>{
	
	@Autowired
	private VRoleDao vRoleDao;

	public void setVRoleDao(VRoleDao vRoleDao) {
		this.vRoleDao = vRoleDao;
	}

	@Override
	protected HibernateDao<VRole, Long> getEntityDao() {
		return vRoleDao;
	}
	
}
