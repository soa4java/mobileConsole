package com.yuchengtech.mobile.console.service.ver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.core.orm.hibernate.HibernateDao;
import com.yuchengtech.mobile.console.dao.ver.ParamDao;
import com.yuchengtech.mobile.console.entity.ver.Param;
import com.yuchengtech.mobile.console.service.EntityManager;

/**
 * 安全相关实体的管理类, 包括用户,角色,资源与授权类.
 */
// Spring Bean的标识.
@Service(value = "paramManager")
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class ParamManager extends EntityManager<Param, Long> {
	@Autowired
	private ParamDao paramDao;
	@Override
	protected HibernateDao<Param, Long> getEntityDao() {
		// TODO Auto-generated method stub
		return paramDao;
	}
	
}
