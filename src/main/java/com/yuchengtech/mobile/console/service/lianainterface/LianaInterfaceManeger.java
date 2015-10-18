package com.yuchengtech.mobile.console.service.lianainterface;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;

import com.yuchengtech.core.orm.hibernate.HibernateDao;
import com.yuchengtech.core.utils.push.PushUtils;
import com.yuchengtech.mobile.console.dao.device.DeviceDAO;
import com.yuchengtech.mobile.console.dao.lianainterface.LianaInterfaceDAO;
import com.yuchengtech.mobile.console.dao.push.TblPushinfoDAO;
import com.yuchengtech.mobile.console.entity.device.TblDevice;
import com.yuchengtech.mobile.console.entity.lianainterface.TblLianainterface;
import com.yuchengtech.mobile.console.entity.push.TblPushinfo;
import com.yuchengtech.mobile.console.service.EntityManager;

/**
 * 安全相关实体的管理类, 包括用户,角色,资源与授权类.
 */
// Spring Bean的标识.
@Service(value = "lianaInterfaceManager")
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class LianaInterfaceManeger extends EntityManager<TblLianainterface, Long> {

	private static Logger logger = LoggerFactory
			.getLogger(LianaInterfaceManeger.class);

	@Autowired
	private LianaInterfaceDAO lianaInterfaceDAO;

	@Override
	protected HibernateDao<TblLianainterface, Long> getEntityDao() {
		return lianaInterfaceDAO;
	}
	
}
