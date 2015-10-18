package com.yuchengtech.mobile.console.service.device;

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
import com.yuchengtech.mobile.console.dao.push.TblPushinfoDAO;
import com.yuchengtech.mobile.console.entity.device.TblDevice;
import com.yuchengtech.mobile.console.entity.push.TblPushinfo;
import com.yuchengtech.mobile.console.service.EntityManager;

/**
 * 安全相关实体的管理类, 包括用户,角色,资源与授权类.
 */
// Spring Bean的标识.
@Service(value = "deviceManager")
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class DeviceManeger extends EntityManager<TblDevice, Long> {

	private static Logger logger = LoggerFactory
			.getLogger(DeviceManeger.class);

	@Autowired
	private DeviceDAO deviceDAO;

	@Override
	protected HibernateDao<TblDevice, Long> getEntityDao() {
		return deviceDAO;
	}
	
}
