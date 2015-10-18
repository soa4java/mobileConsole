package com.yuchengtech.mobile.console.service.dict;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.core.orm.hibernate.HibernateDao;
import com.yuchengtech.mobile.console.common.EntityInfo;
import com.yuchengtech.mobile.console.common.ModuleInfo;
import com.yuchengtech.mobile.console.dao.dict.DictDao;
import com.yuchengtech.mobile.console.entity.dict.Dict;
import com.yuchengtech.mobile.console.entity.dict.DictItem;
import com.yuchengtech.mobile.console.service.EntityManager;

//Spring Bean的标识.
@Component
// 默认将类中的所有函数纳入事务管理.
@Transactional
@EntityInfo(name=ModuleInfo.Dict, identityFiled = "code")
public class DictManager extends EntityManager<Dict, String>{

	private DictDao dictDao;
	
	@Autowired
	public void setDictDao(DictDao dictDao) {
		this.dictDao = dictDao;
	}
	
	public List<DictItem> getItem(String id) {
		Dict dict=super.get(id);
		List<DictItem> list=dict.getDictItems();
		for(DictItem d:list){
			logger.debug(d.getName());	
		}
		return list;
	}
	 
	@Override
	protected HibernateDao<Dict, String> getEntityDao() {
		return dictDao;
	}

	public boolean isPropertyValueExist(String property, Object value) {
		return dictDao.isPropertyValueExist(property,value);
	}
}
