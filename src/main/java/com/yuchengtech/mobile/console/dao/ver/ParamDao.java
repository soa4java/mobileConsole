package com.yuchengtech.mobile.console.dao.ver;


import org.springframework.stereotype.Component;

import com.yuchengtech.core.orm.hibernate.HibernateDao;
import com.yuchengtech.mobile.console.entity.ver.Param;


/**
 * 用户对象的泛型DAO类.
 * 
 * @author calvin
 */
@Component
public class ParamDao extends HibernateDao<Param, Long> { }
