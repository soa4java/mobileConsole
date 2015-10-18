package com.yuchengtech.mobile.console.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.core.orm.Page;
import com.yuchengtech.core.orm.PropertyFilter;
import com.yuchengtech.core.orm.PropertyFilter.MatchType;
import com.yuchengtech.core.orm.hibernate.HibernateDao;

/**
 * service基类
 *
 * @param <T>
 * @param <PK>
 */
@Transactional
public abstract class EntityManager<T, PK extends Serializable> {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	protected abstract HibernateDao<T, PK> getEntityDao();

	public void saveOrUpdate(final T entity) {
		getEntityDao().saveOrUpdate(entity);
	}
	
	public void save(final T entity) {
		getEntityDao().save(entity);
	}
	
	public void update(final T entity) {
		getEntityDao().update(entity);
	}

	public void delete(final T entity) {
		getEntityDao().delete(entity);
	}

	public void delete(final PK id) {
		getEntityDao().delete(id);
	}
	
	public T get(final PK id) {
		return getEntityDao().get(id);
	}
	
	public T load(final PK id) {
		return getEntityDao().load(id);
	}
	
	public List<T> get(final Collection<PK> ids) {
		return getEntityDao().get(ids);
	}

	public List<T> getAll() {
		return getEntityDao().getAll();
	}

	public List<T> getAll(String orderByProperty, boolean isAsc) {
		return getEntityDao().getAll(orderByProperty,isAsc);
	}

	public List<T> findBy(final String propertyName, final Object value) {
		return getEntityDao().findBy(propertyName,value);
	}
	
	public Page<T> findBy(final Page<T> page, final String hql, final Object... values) {
		return getEntityDao().findPage(page, hql, values);
	}
	
	public Page<T> findBy(final Page<T> page, final String hql, final Map<String, ?> values) {
		return getEntityDao().findPage(page, hql, values);
	}

	public T findUniqueBy(final String propertyName, final Object value) {
		return getEntityDao().findUniqueBy(propertyName,value);
	}

	public boolean isPropertyUnique(final String propertyName, final Object newValue, final Object oldValue) {
		return getEntityDao().isPropertyUnique(propertyName,newValue,oldValue);
	}
	
	public Page<T> getAll(final Page<T> page) {
		return getEntityDao().getAll(page);
	}

	public List<T> findBy(final String propertyName, final Object value, final MatchType matchType) {
		return getEntityDao().findBy(propertyName,value,matchType);
	}

	public List<T> find(List<PropertyFilter> filters) {
		return getEntityDao().find(filters);
	}

	public Page<T> findPage(final Page<T> page, final List<PropertyFilter> filters) {
		return getEntityDao().findPage(page, filters);
	}
	
}
