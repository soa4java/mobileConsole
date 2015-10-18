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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.core.orm.hibernate.HibernateDao;
import com.yuchengtech.mobile.console.dao.account.VUserRoleDao;
import com.yuchengtech.mobile.console.entity.account.VUserRole;
import com.yuchengtech.mobile.console.service.EntityManager;


@Service
@Transactional
public class VUserRoleManager extends EntityManager<VUserRole, Long>{
	
	@Autowired
	private VUserRoleDao vUserRoleDao;

	@Override
	protected HibernateDao<VUserRole, Long> getEntityDao() {
		return vUserRoleDao;
	}

	public VUserRole findUniqueBy(Long roleId,Long userId) {
		return vUserRoleDao.findUniqueBy(roleId, userId);
	}
	
	public void delete(VUserRole entity)  {
		vUserRoleDao.delete(entity.getRoleId(), entity.getUserId());
	}

	public int batchDelete(Long roleId, Long[] userIds) {
		return vUserRoleDao.delete(roleId, userIds);
	}
	
}
