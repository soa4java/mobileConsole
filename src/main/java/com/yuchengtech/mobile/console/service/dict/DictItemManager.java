package com.yuchengtech.mobile.console.service.dict;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.core.orm.Page;
import com.yuchengtech.core.orm.hibernate.HibernateDao;
import com.yuchengtech.mobile.console.common.EntityInfo;
import com.yuchengtech.mobile.console.common.ModuleInfo;
import com.yuchengtech.mobile.console.dao.dict.DictItemDao;
import com.yuchengtech.mobile.console.entity.dict.DictItem;
import com.yuchengtech.mobile.console.service.EntityManager;

//Spring Bean的标识.
@Component
// 默认将类中的所有函数纳入事务管理.
@Transactional
@EntityInfo(name=ModuleInfo.DictItem, identityFiled = "id")
public class DictItemManager extends EntityManager<DictItem, Long> {

	private DictItemDao dictItemDao;

	@Autowired
	public void setDictItemDao(DictItemDao dictItemDao) {
		this.dictItemDao = dictItemDao;
	}

	@Override
	protected HibernateDao<DictItem, Long> getEntityDao() {
		return dictItemDao;
	}
	
	public Map<String, String> findDictItemMap(String dictCode){
		List<DictItem> list = dictItemDao.findByDictCode(dictCode);
		Map<String, String> map = new LinkedHashMap<String, String>();
		if (!list.isEmpty()) {
			for (DictItem d:list) {
				map.put(d.getCode(), d.getName());
			}		
		}
		return map;
	}
	
	public List<DictItem> findDictItemList(String dictCode){
		List<DictItem> list = dictItemDao.findByDictCode(dictCode);
		 
		return list;
	}
	
	public boolean isPropertyValueExist(String code, String codeValue,String dictCode, String dictCodeValue) {
		return dictItemDao.isPropertyValueExist(code,codeValue,dictCode,dictCodeValue);
	}
	
	public Page<DictItem> findPage(Page<DictItem> page,String hql,Object...values){
		return getEntityDao().findPage(page, hql, values);
	}
	
	public Page<DictItem> findPage(Page<DictItem> page,String hql,Map<String, Object> map){
		return getEntityDao().findPage(page, hql, map);
	}
}