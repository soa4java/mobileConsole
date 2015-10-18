/******************************************************************************
 * 
 * 北京宇信易诚科技有限公司-移动平台
 *
 * Copyright © 2012 Yucheng Technologies Limited All Rights Reserved.
 * 
 * Created on 2012-11-18 15:29:48
 *
 *******************************************************************************/
package com.yuchengtech.mobile.console.dao.account;

import org.springframework.stereotype.Repository;

import com.yuchengtech.core.orm.hibernate.HibernateDao;
import com.yuchengtech.mobile.console.entity.account.VUser;

/**
 * VUser 数据访问类
 *
 * @version 1.0
 *
 * @author mark
 */
@Repository
public class VUserDao extends HibernateDao<VUser, Long> {
	
}
