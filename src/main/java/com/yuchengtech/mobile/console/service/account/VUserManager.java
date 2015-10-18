/******************************************************************************
 * 
 * 北京宇信易诚科技有限公司-移动平台
 *
 * Copyright © 2012 Yucheng Technologies Limited All Rights Reserved.
 * 
 * Created on 2012-11-18 15:30:20
 *
 *******************************************************************************/
package com.yuchengtech.mobile.console.service.account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.core.orm.hibernate.HibernateDao;
import com.yuchengtech.mobile.console.dao.account.VUserDao;
import com.yuchengtech.mobile.console.entity.account.VUser;
import com.yuchengtech.mobile.console.service.EntityManager;
/**
 * VUser业务逻辑处理类，Spring Bean采用注解方式来定义，默认将类中的所有函数纳入事务管理.
 *
 * @version 1.0
 *
 * @author mark
 */
@Service
@Transactional
public class VUserManager extends EntityManager<VUser, Long>{
	
	@Autowired
	private VUserDao vUserDao;

	public void setVUserDao(VUserDao vUserDao) {
		this.vUserDao = vUserDao;
	}

	@Override
	protected HibernateDao<VUser, Long> getEntityDao() {
		return vUserDao;
	}

	public VUser loadUserByUsername(String userName) {	
		return vUserDao.findUniqueBy("username", userName);
	}
	
}
