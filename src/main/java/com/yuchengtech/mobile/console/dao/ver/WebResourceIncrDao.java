/******************************************************************************
 * 
 * 北京宇信易诚科技有限公司-移动平台
 *
 * Copyright © 2012 Yucheng Technologies Limited All Rights Reserved.
 * 
 * Created on 2012-11-18 20:04:06
 *
 *******************************************************************************/
package com.yuchengtech.mobile.console.dao.ver;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yuchengtech.core.orm.hibernate.HibernateDao;
import com.yuchengtech.mobile.console.entity.ver.WebResourceIncr;

/**
 * VRole 数据访问类
 *
 * @version 1.0
 *
 * @author mark
 */
@Repository
public class WebResourceIncrDao extends HibernateDao<WebResourceIncr, Long> {
	public  WebResourceIncr  getIncr(Long vid,Long clientVid) {
 		String hql= "from WebResourceIncr where   vid =? and increamentvid=?";
 		return findUnique(hql, vid,clientVid);
	}
	public void delByVid(Long vid) {
		String hql = "delete from WebResourceIncr where vid=?";
		batchExecute(hql, vid);
	}
	
	public List<WebResourceIncr>  getIncr(Long vid) {
 		String hql= "from WebResourceIncr where   vid =?";
 		return find(hql, vid);
	}
	
}
