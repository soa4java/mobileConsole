package com.yuchengtech.mobile.console.dao.ver;


import java.util.List;

import org.springframework.stereotype.Component;

import com.yuchengtech.core.orm.hibernate.HibernateDao;
import com.yuchengtech.mobile.console.entity.ver.WebResource;


/**
 * 用户对象的泛型DAO类.
 * 
 */
@Component
public class WebResourceDao extends HibernateDao<WebResource, Long> {

	public WebResource getMaxVer() {
 		String hql= "from WebResource where vid =(select max(vid) from WebResource where status='1' or status is null)";
 		 
 		return findUnique(hql);
	}
	public WebResource getMaxVerNoStatus() {
 		String hql= "from WebResource where vid =(select max(vid) from WebResource)";
 		 
 		return findUnique(hql);
	}

	public WebResource getVerByVid(Long vid) {
 		String hql= "from WebResource where vid =?";
 		 
 		return findUnique(hql,vid);
	}
	 


}
