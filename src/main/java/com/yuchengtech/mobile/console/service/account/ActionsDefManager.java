package com.yuchengtech.mobile.console.service.account;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.core.orm.hibernate.HibernateDao;
import com.yuchengtech.mobile.console.common.EntityInfo;
import com.yuchengtech.mobile.console.common.ModuleInfo;
import com.yuchengtech.mobile.console.dao.account.ActionsDefDao;
import com.yuchengtech.mobile.console.entity.account.ActionsDef;
import com.yuchengtech.mobile.console.service.EntityManager;


@Service(value="ActionsDefManager")
@Transactional
@EntityInfo(name=ModuleInfo.ActionsDef, identityFiled = "id")
public class ActionsDefManager  extends EntityManager<ActionsDef, Long>{

	@Autowired
	private ActionsDefDao ActionsDefDao;
	
	public void setUrlDao(ActionsDefDao actionsdefdao) {
		this.ActionsDefDao = ActionsDefDao;
	}
	
	@Override
	protected HibernateDao<ActionsDef, Long> getEntityDao() {
		// TODO Auto-generated method stub
		return ActionsDefDao;
	}
	
 
	
}
