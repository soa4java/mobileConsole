/******************************************************************************
 * 
 * 北京宇信易诚科技有限公司-移动平台
 *
 * Copyright © 2012 Yucheng Technologies Limited All Rights Reserved.
 * 
 * Created on 2012-10-7 20:53:37
 *
 *******************************************************************************/
package com.yuchengtech.mobile.console.dao.log;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.yuchengtech.core.orm.hibernate.HibernateDao;
import com.yuchengtech.mobile.console.entity.log.ServerLoginLog;

/**
 * 登录日志 数据访问类
 *
 * @version 1.0
 *
 * @author mark
 */
@Repository
public class ServerLoginLogDao extends HibernateDao<ServerLoginLog, Long> {
	
	public Long countByProperty(String propertyName, Object value) {
		String hql = "from ServerLoginLog where "+propertyName+"=?";
		return countHqlResult(hql, value);
	}
	
	public int batchDelete(Long[] ids) {
		String lp = StringUtils.join(ids, ',');	
		StringBuilder hql =  new StringBuilder();
		hql.append("delete from ServerLoginLog entity where entity.id in");
		hql.append("(").append(lp).append(")");
		return batchExecute(hql.toString());
	}
	
}
