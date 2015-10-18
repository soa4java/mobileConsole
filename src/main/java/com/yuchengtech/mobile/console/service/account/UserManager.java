/******************************************************************************
 * 
 * 北京宇信易诚科技有限公司-移动平台
 *
 * Copyright © 2012 Yucheng Technologies Limited All Rights Reserved.
 * 
 * Created on 2012-11-10 22:34:58
 *
 *******************************************************************************/
package com.yuchengtech.mobile.console.service.account;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.core.orm.Page;
import com.yuchengtech.core.orm.hibernate.HibernateDao;
import com.yuchengtech.core.utils.reflection.ReflectionUtils;
import com.yuchengtech.core.utils.security.SpringSecurityUtils;
import com.yuchengtech.mobile.console.common.EntityInfo;
import com.yuchengtech.mobile.console.common.ModuleInfo;
import com.yuchengtech.mobile.console.dao.HibernateUtils;
import com.yuchengtech.mobile.console.dao.account.UserDao;
import com.yuchengtech.mobile.console.entity.account.Role;
import com.yuchengtech.mobile.console.entity.account.User;
import com.yuchengtech.mobile.console.service.EntityManager;
import com.yuchengtech.mobile.console.service.ServiceException;
import com.yuchengtech.mobile.console.utils.SecurityUtil;
import com.yuchengtech.mobile.console.utils.StringUtil;
/**
 *用户业务逻辑处理类，Spring Bean采用注解方式来定义，默认将类中的所有函数纳入事务管理.
 *
 * @version 1.0
 *
 * @author mark
 */
@Service
@Transactional
@EntityInfo(name=ModuleInfo.User, identityFiled = "username")
public class UserManager extends EntityManager<User, Long>{
	
	@Autowired
	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	protected HibernateDao<User, Long> getEntityDao() {
		return userDao;
	}
	
	public int batchDelete(Long[] ids) {
		return userDao.batchDelete(ids);
	}
	
	public boolean isUnique(String propertyName, String strValue) {
		Object value = StringUtil.convertType(strValue,ReflectionUtils.getFieldType(User.class, propertyName));
		Long count = userDao.countByProperty(propertyName,value);
		return count==0L;
	}
	
	public void save(User entity) {
		String pswd = SecurityUtil.shaPassword(entity.getPassword(), entity.getSalt());
		entity.setPassword(pswd);			
		userDao.save(entity);
	}
	
	public void update(User entity,List<Long> roleIds) {
		User user=get(entity.getId());
		HibernateUtils.mergeByCheckedIds(user.getRoleList(), roleIds, Role.class);
		userDao.update(user);
	}
	
	public void changePassword(User entity) {
		String pswd = SecurityUtil.shaPassword(entity.getPassword(), entity.getSalt());
		entity.setPassword(pswd);		
		userDao.update(entity);
	}
	
	/**
	 * 删除用户,如果尝试删除超级管理员将抛出异常.
	 */
	public void delete(User entity) {
		if (isSupervisor(entity.getId())) {
			logger.warn("操作员{}尝试删除超级管理员用户", SpringSecurityUtils.getCurrentUserName());
			throw new ServiceException("不能删除超级管理员用户");
		}
		userDao.delete(entity);
	}
	
	public void delete(Long id) {
		if (isSupervisor(id)) {
			logger.warn("操作员{}尝试删除超级管理员用户", SpringSecurityUtils.getCurrentUserName());
			throw new ServiceException("不能删除超级管理员用户");
		}
		userDao.delete(id);
	}
	
	/**
	 * 判断是否超级管理员.
	 */
	private boolean isSupervisor(Long id) {
		return id == 1;
	}
	
	@Transactional(readOnly = true)
	public User findUserByLoginName(String loginName) {
		return userDao.findUniqueBy("loginName", loginName);
	}
	
	/**
	 * 检查用户名是否唯一.
	 *
	 * @return username在数据库中唯一或等于oldUsername时返回true.
	 */
	@Transactional(readOnly = true)
	public boolean isUsernameUnique(String newUsername, String oldUsername) {
		return userDao.isPropertyUnique("username", newUsername, oldUsername);
	}
	
	public int enabledUser(Long id) {
		String status = "1";
		return userDao.batchUpdateUserStatus(status,id);
	}
	
	public int disabledUser(Long id) {
		String status = "0";
		return userDao.batchUpdateUserStatus(status,id);
	}
	
	public int batchEnabledUser(Long[] ids) {
		String status = "1";
		return userDao.batchUpdateUserStatus(status,ids);
	}
	
	public int batchDisabledUser(Long[] ids) {
		String status = "0";
		return userDao.batchUpdateUserStatus(status,ids);
	}
	
	public long[] findUserIdByRole(Long roleId) {
		return userDao.findUserIdByRole(roleId);
	}
	
	public Page<User> findPage(Page<User> page,String hql,Map<String, String> values) {
		return userDao.findPage(page, hql, values);
	}

	public void batchRegister(List<User> list) {
		for (User user:list) {
			userDao.save(user);
		}
	}
	
}
