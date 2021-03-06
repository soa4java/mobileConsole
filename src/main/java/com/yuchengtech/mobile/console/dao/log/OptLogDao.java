/******************************************************************************
 * 
 * 北京宇信易诚科技有限公司-移动平台
 *
 * Copyright © 2012 Yucheng Technologies Limited All Rights Reserved.
 * 
 * Created on 2012-10-29 17:46:29
 *
 *******************************************************************************/
package com.yuchengtech.mobile.console.dao.log;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.yuchengtech.core.orm.hibernate.HibernateDao;
import com.yuchengtech.mobile.console.entity.log.OptLog;

/**
 * 操作日志 数据访问类
 *
 * @version 1.0
 *
 * @author mark
 */
@Repository
public class OptLogDao extends HibernateDao<OptLog, Long> {
	
	public Long countByProperty(String propertyName, Object value) {
		String hql = "from OptLog where "+propertyName+"=?";
		return countHqlResult(hql, value);
	}
	
	public int batchDelete(Long[] ids) {
		String lp = StringUtils.join(ids, ',');	
		StringBuilder hql =  new StringBuilder();
		hql.append("delete from OptLog entity where entity.id in");
		hql.append("(").append(lp).append(")");
		return batchExecute(hql.toString());
	}
	
}
