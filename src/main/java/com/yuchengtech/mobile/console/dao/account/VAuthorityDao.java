package com.yuchengtech.mobile.console.dao.account;


import org.springframework.stereotype.Component;

import com.yuchengtech.core.orm.hibernate.HibernateDao;
import com.yuchengtech.mobile.console.entity.account.VAuthority;

/**
 * 授权对象的泛型DAO.
 */
@Component
public class VAuthorityDao extends HibernateDao<VAuthority, Long> {

	public VAuthority getSimpleVAuthority(Long id) {
		String hql = "SELECT new VAuthority(id,name)  FROM VAuthority where id=?";
		return findUnique(hql,id);
	}
	
}
