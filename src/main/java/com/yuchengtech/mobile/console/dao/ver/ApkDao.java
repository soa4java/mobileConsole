package com.yuchengtech.mobile.console.dao.ver;
import org.springframework.stereotype.Component;

import com.yuchengtech.core.orm.hibernate.HibernateDao;
import com.yuchengtech.mobile.console.entity.ver.Apk;
import com.yuchengtech.mobile.console.entity.ver.Apk;
import com.yuchengtech.mobile.console.entity.ver.WebResource;


/**
 * 用户对象的泛型DAO类.
 * 
 * @author calvin
 */
@Component
public class ApkDao extends HibernateDao<Apk, Long> {
	public Apk getMaxVer() {
 		String hql= "from Apk where vid =(select max(vid) from Apk where status='1' or status is null)";
 		 
 		return findUnique(hql);
	}
	public Apk getMaxVerNoStatus() {
 		String hql= "from Apk where vid =(select max(vid) from Apk)";
 		 
 		return findUnique(hql);
	}
	public Apk getVerByVid(Long vid) {
 		String hql= "from Apk where vid =?";
 		 
 		return findUnique(hql,vid);
	}
	 


}
