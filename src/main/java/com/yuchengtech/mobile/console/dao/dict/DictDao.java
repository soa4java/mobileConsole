package com.yuchengtech.mobile.console.dao.dict;

import org.springframework.stereotype.Component;

import com.yuchengtech.core.orm.hibernate.HibernateDao;
import com.yuchengtech.mobile.console.entity.dict.Dict;


/**
 * 字典对象的泛型DAO类.
 * 
 * @author calvin
 */
@Component
public class DictDao extends HibernateDao<Dict, String> {

	public boolean isPropertyValueExist(String propertyName, Object value) {
		Dict dict = findUniqueBy(propertyName, value);
		if (dict!=null) {
			return true;
		}
		return false;
	}

}
