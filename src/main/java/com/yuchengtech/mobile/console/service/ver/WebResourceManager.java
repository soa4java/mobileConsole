package com.yuchengtech.mobile.console.service.ver;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.core.orm.hibernate.HibernateDao;
import com.yuchengtech.core.utils.CompressUtil;
import com.yuchengtech.core.utils.FileMd5Util;
import com.yuchengtech.mobile.console.common.Constants;
import com.yuchengtech.mobile.console.dao.ver.WebResourceDao;
import com.yuchengtech.mobile.console.dao.ver.WebResourceIncrDao;
import com.yuchengtech.mobile.console.entity.ver.WebResource;
import com.yuchengtech.mobile.console.entity.ver.WebResourceIncr;
import com.yuchengtech.mobile.console.service.EntityManager;

/**
 * 安全相关实体的管理类, 包括用户,角色,资源与授权类.
 */
// Spring Bean的标识.
@Service(value = "webResourceManager")
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class WebResourceManager extends EntityManager<WebResource, Long> {

	private static Logger logger = LoggerFactory
			.getLogger(WebResourceManager.class);

	@Autowired
	private WebResourceDao webResourceDao;
	@Autowired
	private WebResourceIncrDao webResourceIncrDao;

	
	public WebResourceIncr getIncrVer(Long vid,Long clientvid) {
		return webResourceIncrDao.getIncr(vid,clientvid);
	}

	public WebResource getMaxVer() {
		return webResourceDao.getMaxVer();
	}

	public WebResource getMaxVerNoStatus() {
		return webResourceDao.getMaxVerNoStatus();
	} 
	public WebResource getVerByVid(Long vid) {
		return webResourceDao.getVerByVid(vid);
	}
	 
	public void save(WebResource entity) {
		webResourceDao.save(entity);
	}

	@Override
	protected HibernateDao<WebResource, Long> getEntityDao() {
		return webResourceDao;
	}
 
	
	public void ativekWebResource( Long id) throws Exception {
		WebResource web=	webResourceDao.get(id);
		web.setStatus("1");
		webResourceDao.update(web);
	}
	public int rollbackWebResource( String realPath,Long vid) throws Exception {
		//只能回退最新的一个版本，查询当前的用户选择的vid是否为最大的max_vid，如果不是，提示出错。
		//如果是full版本的（baseline为空，比如正好系统做了全量版本合并），则要删除baseline的目录，删除webresource和webresource_fix中的vid为max_vid记录。
		//如果是fix版本，则要删除baseline目录下的fix_baseline+1～fix_(max_vid)目录，然后删除对应的zip文件（fix_baseline+1_max_vid.zip....fix_maxvid-1_max_vid.zip)，再解压fix_baseline+1_(max_vid-1).zip～fix_(max_vid-1).zip到目录fix_baseline+1～fix_(max_vid-1)
		//然后再删除baseline目录下的full_baseline目录内的内容，删除baseline目录下full_baseline.zip，解压full_baseline_(max_vid-1).zip到full_baseline目录,重命名full_baseline_(max_vid-1).zip为full_baseline.zip
		//删除webresource和webresource_fix中的vid为max_vid记录

		WebResource maxWeb=webResourceDao.getMaxVerNoStatus();
		if(maxWeb.getVid().longValue()!=vid.longValue()){
			//只能删除最新版本
			return -1;
		}
		String resourcePath=realPath+Constants.UPLOAD_PATH;
		if(maxWeb.getBaseline()==null||maxWeb.getBaseline().longValue()==maxWeb.getVid().longValue()){
			//全量版本的回退
			File baseline=new File(resourcePath+"/"+vid);
			FileUtils.deleteDirectory(baseline);
			webResourceDao.delete(maxWeb.getId());
		}else{
			Long baseline=maxWeb.getBaseline();
			String basePath=resourcePath+"/"+baseline;
			//fix版本的回退
			for(long i=baseline+1;i<=vid;i++){
				String fix_vid_path_i=basePath+"/fix_"+i;
				String fix_vid_zip=basePath+"/fix_"+(i-1)+"_"+vid+".zip";//fix_baseline+1_max_vid.zip....fix_maxvid-1_max_vid.zip
				File iFile=new File(fix_vid_path_i);
				File iFile_zip=new File(fix_vid_zip);
				if(iFile.exists()){
					FileUtils.deleteDirectory(iFile);
					if(i!=vid)
					iFile.mkdirs();
				}
				if(iFile_zip.exists())iFile_zip.delete();
			}
			
			//fix_baseline+1_(max_vid-1).zip～fix_(max_vid-1).zip
			for(long i=baseline+1;i<=vid-1;i++){
				String fix_vid_path_i=basePath+"/fix_"+i;
				String fix_vid_zip=basePath+"/fix_"+(i-1)+"_"+(vid-1)+".zip";//fix_baseline+1_max_vid.zip....fix_maxvid-1_max_vid.zip
				CompressUtil.unZip(fix_vid_zip,fix_vid_path_i);
			}
			String full_path=basePath+"/full_"+baseline;
			String full_zip=basePath+"/full_"+vid+".zip";
			String full_last_zip=basePath+"/full_"+(vid-1)+".zip";
			File full=new File(full_path);
			File fullzipfile=new File(full_zip);
			if(fullzipfile.exists()){
			boolean d=	fullzipfile.delete();
			logger.debug("delete file:("+full_zip+") :"+d);
			}
			FileUtils.deleteDirectory(full);
			full.mkdirs();
			CompressUtil.unZip(full_last_zip,full_path);//full_baseline_(max_vid-1).zip
			//boolean r=new File(full_last_zip).renameTo(fullzipfile);
			//logger.debug("ReName file:("+full_last_zip+") to:"+fullzipfile+ " :"+r);
			webResourceDao.delete(maxWeb.getId());
			webResourceIncrDao.delByVid(vid);;
		}
		
		return 0;
		
	}
	/*
	 * 先文件操作，再数据库操作。文件操作不成功，不操作数据库，同时回滚文件操作
	 */
	public void saveWebResource(WebResource webResource, File uploadFile, String realPath) throws Exception {
		try {
			String resourcePath=realPath+Constants.UPLOAD_PATH;
			WebResource maxWeb=webResourceDao.getMaxVerNoStatus();
			
			if(maxWeb==null){
				//系统初始版本，上传全量包
				//上传初始安装包www.zip，填写版本信息，文件上传后，后台service取webresource最大vid的记录，如果记录为空，则为初始版本
				//文件操作：建立0目录，在0目录下，建立full_0目录，将www.zip解压到full_0目录，同时将www.zip重命名为full_0.zip，计算full_0目录的hash值。
				//然后将版本信息，其中vid设置为0，baseline设置null，以及hash值保存到webresource中
				File baseFile=new File(resourcePath);
				if(!baseFile.exists()){
					baseFile.mkdirs();
				}
				File version0=new File(resourcePath+"/0");
				if(!baseFile.exists())
				version0.mkdirs();
				String fullPath0=resourcePath+"/0/full_0";
				File version_full_0=new File(fullPath0);
				if(!version_full_0.exists())
					version_full_0.mkdirs();
				String saveUrl=resourcePath+"/0/full_0.zip";
				CompressUtil.unZip(uploadFile.getAbsolutePath(),fullPath0);
				CompressUtil.zip(fullPath0, saveUrl);
				String hash = FileMd5Util.getMd5(fullPath0);
				 logger.debug("Path ("+fullPath0+") Hash :"+hash);
				webResource.setHash(hash);
				webResource.setBaseline(0L);
				webResource.setVid(0L);
				webResource.setForceUpdate(null);
				webResource.setUpdateDt(new Date());
				webResource.setFullUrl(Constants.MOBILE_SERVER_URL+Constants.UPLOAD_PATH+"/"+"/0/full_0.zip");//先不生成，有前端mobileServer生成
				webResource.setResourceData(getBytes(saveUrl));
				webResource.setVsize(String.valueOf(new File(resourcePath+"/0/full_0.zip").length()));
				webResourceDao.save(webResource);
				
			}else {
				
				if("0".equals(maxWeb.getStatus())){
					throw new Exception("请先激活已上传的资源包！");
				}
				
				//上传增量包
				long diff=0;
				if(maxWeb.getBaseline()!=null)diff=maxWeb.getVid()-maxWeb.getBaseline();
				if(diff>=Constants.merge_max_fixs){
					//需要版本合并成全量版本
					//版本fix.zip上传，填写版本信息，文件上传后，后台service取webresource最大vid的记录，非空情况下，计算vid-baseline，如果vid-baseline大于或等于设定的merge_max_fixs，则执行合并操作，设置当前vid=vid+1,设置baseline=vid，
					//文件操作：拷贝旧baseline目录下full_baseline目录到新baseline目录下，并重命名为full_vid，再将fix.zip文件解压到full_baseline目录，然后重新打包full_baseline文件为full_baseline_tmp.zip,完后重命名full_baseline.zip为full_baseline_vid.zip，再重命名full_baseline_tmp.zip为full_baseline.zip
					//。重新打包成功后，再计算full_baseline的hash值。
					//然后将版本信息，baseline设置null，vid以及hash值保存到webresource中
					Long vid=maxWeb.getVid()+1;
			        long baseline=vid;
			        String basePath=resourcePath+"/"+baseline;
			        String basePath_old=resourcePath+"/"+maxWeb.getBaseline()+"/full_"+maxWeb.getBaseline();
			        File basePathDir=new File(basePath);
			        if(!basePathDir.exists())basePathDir.mkdirs();
			        String fullPath=basePath+"/full_"+baseline;
			        File fullPathDir=new File(fullPath);
			        if(!fullPathDir.exists())fullPathDir.mkdirs();
			        FileUtils.copyDirectory(new File(basePath_old), fullPathDir);
			        CompressUtil.unZip(uploadFile.getAbsolutePath(),fullPath);

					String saveUrl=reZipFull(baseline, basePath,vid);
					String hash = FileMd5Util.getMd5(fullPath);
					logger.debug("Path (" + fullPath + ") Hash :" + hash);
					webResource.setHash(hash);
					webResource.setBaseline(vid);
					webResource.setVid(vid);
					webResource.setUpdateDt(new Date());
					webResource.setStatus("0");//管理状态，未对外服务
					webResource.setVsize(String.valueOf(new File(resourcePath+"/0/full_0.zip").length()));
					webResource.setFullUrl(Constants.MOBILE_SERVER_URL+Constants.UPLOAD_PATH+"/"+"/"+baseline+"/full_"+vid+".zip");
					webResource.setResourceData(getBytes(saveUrl));
					webResourceDao.save(webResource);
					 
				}else{
					//直接更新版本信息
					//版本上传：
					//版本fix.zip上传，填写版本信息，文件上传后，后台service取webresource最大vid的记录，非空情况下，如果baseline为null，则取vid为baseline，否则取该记录的baseline，设置当前vid=vid+1
					//		文件操作：在baseline目录下，建立fix_vid目录， 解压fix.zip到fix_vid目录，同时拷贝fix_vid到full_baseline目录，然后计算vid-baseline的目录数，将fix_vid拷贝到baseline+1~~vid-1个目录中(fix_baseline+1,fix_baseline+2,...fix_vid-1)，然后重新打包full_baseline文件为full_baseline_tmp.zip,完后删除full_baseline.zip，再重命名full_baseline_tmp.zip为full_baseline.zip，然后重新将fix_baseline+1到fix_vid目录重新打包，命名规则为fix_n_vid.zip，打包过程中，同时生成n个webresource_fix（vid已知，clientvid和url动态生成，clientvid：baseline+1~vid，url:http://xxxx/upload/baseline/fix_n_vid;）对象添加到list中。重新打包成功后，再计算full_baseline的hash值。
					//		然后将版本信息，和vid，baseline，hash保存到webresource中
					//		然后list对象中的记录保存到数据库表webresource_fix
					Long baseline=maxWeb.getBaseline();
					if(baseline==null)
						baseline=maxWeb.getVid();
					Long vid=maxWeb.getVid()+1;
					
					String basePath=resourcePath+"/"+baseline;
					String fullPath=basePath+"/full_"+baseline;
					String fix_vid_path=basePath+"/fix_"+vid;
					File file_vid=new File(fix_vid_path);
					if (!file_vid.exists()) {
						file_vid.mkdirs();
					} 
					CompressUtil.unZip(uploadFile.getAbsolutePath(),fix_vid_path);
					FileUtils.copyDirectory(file_vid, new File(fullPath));
					
					//将fix_vid拷贝到baseline+1~~vid-1个目录中(fix_baseline+1,fix_baseline+2,...fix_vid-1)
					for(long i=baseline+1;i<=vid-1;i++){
						String fix_vid_path_i=basePath+"/fix_"+i;
						File iFile=new File(fix_vid_path_i);
						if(iFile.exists()){
							logger.debug("Copy file ("+fix_vid_path+") to Dir:"+fix_vid_path_i);
							FileUtils.copyDirectory(file_vid, iFile);
						}
					}
					
					String saveUrl=reZipFull(baseline, basePath,vid);
					 
				    
				    String hash = FileMd5Util.getMd5(fullPath);
				    logger.debug("Path ("+fullPath+") Hash :"+hash);
				    
			       List<WebResourceIncr> list=new ArrayList<WebResourceIncr>();
				    //fix_baseline+1到fix_vid目录重新打包，命名规则为fix_(n-1)_vid.zip，打包过程中，同时生成n个webresource_fix
				    for(long i=baseline+1;i<=vid;i++){
				    	String fix_vid_path_i=basePath+"/fix_"+i;
				    	String name="/fix_"+(i-1)+"_"+vid+".zip";
				    	String zip_name=basePath+name;
				    	CompressUtil.zip(fix_vid_path_i, zip_name);
						logger.debug("Package file ("+fix_vid_path_i+") to:"+zip_name);
						WebResourceIncr wi=new WebResourceIncr();
						wi.setForceUpdate(webResource.getForceUpdate());
						if(webResource.getForceUpdate().equals("0")){
							//检查中间版本是否有强制更新的，如果有，还是要强制更新，比如客户端版本是1，最新版本是 5，如果版本3是强制更新的，则从版本1升级到5还是强制的，尽管5是非强制的。
							boolean f=isForceUpdate((i-1), vid);
							if(f)wi.setForceUpdate("1");
						}
						wi.setVid(vid);
						wi.setIncreamentvid(i-1);
						wi.setUpdateDt(new Date());
						wi.setVsize(String.valueOf(new File(zip_name).length()));
						wi.setUrl(Constants.MOBILE_SERVER_URL+Constants.UPLOAD_PATH+"/"+baseline+name);//upload/baseline/fix_n_vid
						wi.setResourceData(getBytes(zip_name));
						list.add(wi);
				    }
				    
				    webResource.setHash(hash);
					webResource.setBaseline(baseline);
					webResource.setVid(vid);
					webResource.setUpdateDt(new Date());
					webResource.setStatus("0");//管理状态，未对外服务
                    webResource.setVsize(String.valueOf(new File(basePath+"/"+baseline+"/full_"+vid+".zip").length()));
                    webResource.setResourceData(getBytes(saveUrl));
					webResource.setFullUrl(Constants.MOBILE_SERVER_URL+Constants.UPLOAD_PATH+"/"+"/"+baseline+"/full_"+vid+".zip");

					webResourceDao.save(webResource);
					
				    for(WebResourceIncr i:list){
				    	webResourceIncrDao.save(i);
				    }
				    logger.debug("Db save done!");
				}
			}
		} catch (Exception e) {
			logger.error("error:",e);
			throw new Exception(e);
		}
		uploadFile.deleteOnExit();
	}
	private String reZipFull(Long baseline,String basePath,Long vid) throws Exception{
		String fullPath=basePath+"/full_"+baseline;
		String full_tmp_zip_name= "full_"+baseline+"_tmp.zip";
		String full_tmp_zip=basePath+"/"+full_tmp_zip_name;
//		String full_zip_name="full_"+baseline+".zip";
		String full_zip_name="full_"+vid+".zip";
		String full_zip=basePath+"/"+full_zip_name;
		/*CompressUtil.zip(fullPath, full_tmp_zip);	
		logger.debug("Package file ("+fullPath+") to:"+full_tmp_zip_name);
		
		String full_bak_zip=basePath+"/"+"full_"+baseline+"_"+(vid-1)+".zip";
		
		File file_full_zip = new File(full_zip);
		if (file_full_zip.exists()) {
			file_full_zip.renameTo(new File(full_bak_zip));
			logger.debug("Backup file (" + full_zip + ") to " + full_bak_zip);
		}
	    new File(full_tmp_zip).renameTo(file_full_zip);
	    logger.debug("Rename file ("+full_tmp_zip+") to:"+full_zip);*/
		CompressUtil.zip(fullPath, full_zip);	
		return full_zip;
	}

	private List<Long> getForceUpdateVersion() {
		List<Long> list = new ArrayList<Long>();
		List<WebResource> wlist = webResourceDao.getAll();
		for (WebResource w : wlist) {
			if (w.getForceUpdate().equals("1"))
				list.add(w.getVid());
		}
		return list;
	}

	private boolean isForceUpdate(Long startVersion, Long endVersion) {
		List<Long> forceList=getForceUpdateVersion();
		boolean force = false;
		for (long i = startVersion.longValue(); i <= endVersion.longValue(); i++) {
			if (forceList.contains(i))
				force = true;
		}
		return force;
	}
	public static byte[] getBytes(String filePath){
		byte[] buffer = null;
		try {
			File file = new File(filePath);
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] b = new byte[1000];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();
			bos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;
	}

	public static void main(String args[]) throws Exception {
	}
}
