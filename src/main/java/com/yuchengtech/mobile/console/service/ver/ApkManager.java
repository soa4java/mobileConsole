package com.yuchengtech.mobile.console.service.ver;


import java.io.File;
import java.util.Date;
import java.util.List;

import net.dongliu.apk.parser.ApkParser;
import net.dongliu.apk.parser.bean.ApkMeta;
import net.dongliu.apk.parser.bean.CertificateMeta;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.core.orm.hibernate.HibernateDao;
import com.yuchengtech.mobile.console.common.Constants;
import com.yuchengtech.mobile.console.dao.ver.ApkDao;
import com.yuchengtech.mobile.console.entity.ver.Apk;
import com.yuchengtech.mobile.console.entity.ver.WebResource;
import com.yuchengtech.mobile.console.service.EntityManager;


/**
 * 安全相关实体的管理类, 包括用户,角色,资源与授权类.
 */
//Spring Bean的标识.
@Service(value = "apkManager")
//默认将类中的所有函数纳入事务管理.
@Transactional
public class ApkManager extends EntityManager<Apk, Long> {

	private static Logger logger = LoggerFactory.getLogger(ApkManager.class);
	
	
	@Autowired
	private ApkDao apkDao;

	@Override
	protected HibernateDao<Apk, Long> getEntityDao() {
		return apkDao;
	}
	
	
	public Apk getMaxVer() {
		return apkDao.getMaxVer();
	}

	public Apk getMaxVerNoStatus() {
		return apkDao.getMaxVerNoStatus();
	} 
	public Apk getVerByVid(Long vid) {
		return apkDao.getVerByVid(vid);
	}
	
	public void ativekWebResource( Long id) throws Exception {
		Apk apk=	apkDao.get(id);
		apk.setStatus("1");
		apkDao.update(apk);
	}
	
	public int rollbackApk( String realPath,Long id) throws Exception {
		Apk apk=apkDao.get(id);
		File a=new File(realPath+Constants.UPLOAD_PATH+"/"+apk.getFileName());
		if(a.exists())
		a.delete();
		apkDao.delete(id);
		return 1;
	}
	
	public int saveApk(Apk apk, File uploadFile, String realPath,String apkName) throws Exception {
		
		ApkParser apkParser = new ApkParser(uploadFile);
		ApkMeta am= apkParser.getApkMeta();
		    List<CertificateMeta> certs = apkParser.getCertificateMetas();
		    for (CertificateMeta certificateMeta : certs) {
		    	logger.debug(certificateMeta.getSignAlgorithm());
		    }
		  String sign=  certs.get(0).getCertMd5();
		  Long version=am.getVersionCode();
		Apk apk_max=apkDao.getMaxVer();
		if(apk_max!=null){
		/*if(apk_max.getVid().equals(String.valueOf(version.longValue()))){
			//上传的版本和上一个版本的一样
			return -1;
		}*/
		if(Long.valueOf(apk_max.getVid())>=version.longValue()){
			//上传的版本小于或等于上一个版本
			return -1;
		}
		}
		String size=String.valueOf(uploadFile.length());
		apk.setVsize(size);
		apk.setVid(version);
		apk.setSign(sign);apk.setUpdate_dt(new Date());
		if(apk_max==null){
			apk.setStatus(null); 
			apk.setForceUpdate(null);
		}else{
			apk.setStatus("0");
		}
		
		//apk.setUrl(Constants.MOBILE_SERVER_URL+Constants.UPLOAD_PATH+"/"+apkName);
		apk.setResourceData(WebResourceManager.getBytes(uploadFile.getAbsolutePath()));
		apk.setFileName(apkName);
		File f=new File(realPath+Constants.UPLOAD_PATH+"/"+apkName);
		FileUtils.copyFile(uploadFile, f);
		apkDao.save(apk);
		
		uploadFile.deleteOnExit();
		return 1;
	}

	 
}
