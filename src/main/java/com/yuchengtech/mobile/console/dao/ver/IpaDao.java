package com.yuchengtech.mobile.console.dao.ver;
import org.springframework.stereotype.Component;

import com.yuchengtech.core.orm.hibernate.HibernateDao;
import com.yuchengtech.mobile.console.entity.ver.Ipa;
import com.yuchengtech.mobile.console.entity.ver.Ipa;
import com.yuchengtech.mobile.console.entity.ver.WebResource;


/**
 * 用户对象的泛型DAO类.
 * 
 * @author calvin
 */
@Component
public class IpaDao extends HibernateDao<Ipa, Long> {
	public Ipa getMaxVer() {
 		String hql= "from Ipa where vid =(select max(vid) from Ipa where status='1' or status is null)";
 		 
 		return findUnique(hql);
	}
	public Ipa getMaxVerNoStatus() {
 		String hql= "from Ipa where vid =(select max(vid) from Ipa)";
 		 
 		return findUnique(hql);
	}
	public Ipa getVerByVid(String vid) {
 		String hql= "from Ipa where vid =?";
 		 
 		return findUnique(hql,vid);
	}
	 


}
