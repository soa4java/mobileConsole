/******************************************************************************
 * 
 * 北京宇信易诚科技有限公司-移动平台
 *
 * Copyright © 2012 Yucheng Technologies Limited All Rights Reserved.
 * 
 * Created on 2012-11-22 0:32:37
 *
 *******************************************************************************/
package com.yuchengtech.mobile.console.dao.log;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.yuchengtech.core.orm.hibernate.HibernateDao;
import com.yuchengtech.mobile.console.entity.log.VIpadLoginLog;

/**
 * 登录日志 数据访问类
 *
 * @version 1.0
 *
 * @author mark
 */
@Repository
public class VIpadLoginLogDao extends HibernateDao<VIpadLoginLog, Long> {
	
	public Long countByProperty(String propertyName, Object value) {
		String hql = "from VIpadLoginLog where "+propertyName+"=?";
		return countHqlResult(hql, value);
	}
	
	public int batchDelete(Long[] ids) {
		String lp = StringUtils.join(ids, ',');	
		StringBuilder hql =  new StringBuilder();
		hql.append("delete from VIpadLoginLog entity where entity.id in");
		hql.append("(").append(lp).append(")");
		return batchExecute(hql.toString());
	}

	public List<VIpadLoginLog> getLog(String mon, String deptCode) {
		StringBuilder builder = new StringBuilder("from VIpadLoginLog where loginTime like");
		builder.append(" '").append(mon).append("%'");
		builder.append(" and deptCode like '").append(deptCode).append("%'");
		builder.append(" order by id desc");
		return find(builder.toString());
	}
	
}
