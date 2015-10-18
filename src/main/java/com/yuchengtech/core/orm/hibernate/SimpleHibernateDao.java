
package com.yuchengtech.core.orm.hibernate;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.yuchengtech.core.utils.reflection.ReflectionUtils;

@SuppressWarnings("unchecked")
public class SimpleHibernateDao<T, PK extends Serializable> {


	protected SessionFactory sessionFactory;

	protected Class<T> entityClass;

	public SimpleHibernateDao() {
		this.entityClass = ReflectionUtils.getSuperClassGenricType(getClass());
	}

	public SimpleHibernateDao(final SessionFactory sessionFactory, final Class<T> entityClass) {
		this.sessionFactory = sessionFactory;
		this.entityClass = entityClass;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Autowired
	public void setSessionFactory(final SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public void saveOrUpdate(final T entity) {
		Assert.notNull(entity, "entity不能为空");
		getSession().saveOrUpdate(entity);
	}
	
	public void save(final T entity) {
		Assert.notNull(entity, "entity不能为空");
		getSession().save(entity);
	}
	
	
	public void update(final T entity) {
		Assert.notNull(entity, "entity不能为空");
		getSession().update(entity);
	}

	
	public void delete(final T entity) {
		Assert.notNull(entity, "entity不能为空");
		getSession().delete(entity);
//		logger.debug("delete entity: {}", entity);
	}

	
	public void delete(final PK id) {
		Assert.notNull(id, "id不能为空");
		delete(get(id));
//		logger.debug("delete entity {},id is {}", entityClass.getSimpleName(), id);
	}

	
	public T get(final PK id) {
		Assert.notNull(id, "id不能为空");
		return (T) getSession().get(entityClass, id);
	}
	
	
	public T load(final PK id) {
		Assert.notNull(id, "id不能为空");
		return (T) getSession().load(entityClass, id);
	}
	
	
	public List<T> get(final Collection<PK> ids) {
		return find(Restrictions.in(getIdName(), ids));
	}

	
	public List<T> getAll() {
		return find();
	}

	
	public List<T> getAll(String orderByProperty, boolean isAsc) {
		Criteria c = createCriteria();
		if (isAsc) {
			c.addOrder(Order.asc(orderByProperty));
		} else {
			c.addOrder(Order.desc(orderByProperty));
		}
		return c.list();
	}

	
	public List<T> findBy(final String propertyName, final Object value) {
		Assert.hasText(propertyName, "propertyName不能为空");
		Criterion criterion = Restrictions.eq(propertyName, value);
		return find(criterion);
	}

	
	public T findUniqueBy(final String propertyName, final Object value) {
		Assert.hasText(propertyName, "propertyName不能为空");
		Criterion criterion = Restrictions.eq(propertyName, value);
		return (T) createCriteria(criterion).uniqueResult();
	}

	
	public <X> List<X> find(final String hql, final Object... values) {
		return createQuery(hql, values).list();
	}

	
	public <X> List<X> find(final String hql, final Map<String, ?> values) {
		return createQuery(hql, values).list();
	}

	
	public <X> X findUnique(final String hql, final Object... values) {
		return (X) createQuery(hql, values).uniqueResult();
	}

	
	public <X> X findUnique(final String hql, final Map<String, ?> values) {
		return (X) createQuery(hql, values).uniqueResult();
	}

	
	public int batchExecute(final String hql, final Object... values) {
		return createQuery(hql, values).executeUpdate();
	}

	
	public int batchExecute(final String hql, final Map<String, ?> values) {
		return createQuery(hql, values).executeUpdate();
	}

	
	public Query createQuery(final String queryString, final Object... values) {
		Assert.hasText(queryString, "queryString不能为空");
		Query query = getSession().createQuery(queryString);
		query.setCacheable(true);//打包ver.jar是去掉注释
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	
	public Query createQuery(final String queryString, final Map<String, ?> values) {
		Assert.hasText(queryString, "queryString不能为空");
		Query query = getSession().createQuery(queryString);
		query.setCacheable(true);//打包ver.jar是去掉注释
		if (values != null) {
			query.setProperties(values);
		}
		return query;
	}

	
	public List<T> find(final Criterion... criterions) {
		return createCriteria(criterions).list();
	}

	
	public T findUnique(final Criterion... criterions) {
		return (T) createCriteria(criterions).uniqueResult();
	}

	
	public Criteria createCriteria(final Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(entityClass);
//		criteria.setCacheable(true);//打包ver.jar是去掉注释
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}

	
	public void initProxyObject(Object proxy) {
		Hibernate.initialize(proxy);
	}

	
	public void flush() {
		getSession().flush();
	}

	
	public Query distinct(Query query) {
		query.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return query;
	}

	
	public Criteria distinct(Criteria criteria) {
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return criteria;
	}

	
	public String getIdName() {
		ClassMetadata meta = getSessionFactory().getClassMetadata(entityClass);
		return meta.getIdentifierPropertyName();
	}

	
	public boolean isPropertyUnique(final String propertyName, final Object newValue, final Object oldValue) {
		if (newValue == null || newValue.equals(oldValue)) {
			return true;
		}
		Object object = findUniqueBy(propertyName, newValue);
		return (object == null);
	}
}