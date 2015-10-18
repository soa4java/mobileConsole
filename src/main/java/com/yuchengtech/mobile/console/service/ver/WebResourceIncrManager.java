package com.yuchengtech.mobile.console.service.ver;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.core.orm.hibernate.HibernateDao;
import com.yuchengtech.core.utils.CompressUtil;
import com.yuchengtech.core.utils.FileMd5Util;
import com.yuchengtech.mobile.console.common.Constants;
import com.yuchengtech.mobile.console.dao.ver.WebResourceDao;
import com.yuchengtech.mobile.console.dao.ver.WebResourceIncrDao;
import com.yuchengtech.mobile.console.entity.ver.WebResource;
import com.yuchengtech.mobile.console.entity.ver.WebResourceIncr;
import com.yuchengtech.mobile.console.service.EntityManager;

/**
 * 安全相关实体的管理类, 包括用户,角色,资源与授权类.
 */
// Spring Bean的标识.
@Service(value = "webResourceIncrManager")
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class WebResourceIncrManager extends EntityManager<WebResourceIncr, Long> {

	private static Logger logger = LoggerFactory
			.getLogger(WebResourceIncrManager.class);

	@Autowired
	private WebResourceIncrDao webResourceIncrDao;

	@Override
	protected HibernateDao<WebResourceIncr, Long> getEntityDao() {
		// TODO Auto-generated method stub
		return webResourceIncrDao;
	}

	
	public  List<WebResourceIncr>  getIncr(Long vid) {
		return webResourceIncrDao.getIncr(vid);
	}
	
}
