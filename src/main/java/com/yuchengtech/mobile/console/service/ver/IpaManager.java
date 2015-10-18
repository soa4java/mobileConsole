package com.yuchengtech.mobile.console.service.ver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.dongliu.apk.parser.bean.CertificateMeta;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dd.plist.NSDictionary;
import com.dd.plist.NSString;
import com.dd.plist.PropertyListParser;
import com.yuchengtech.core.orm.hibernate.HibernateDao;
import com.yuchengtech.mobile.console.common.Constants;
import com.yuchengtech.mobile.console.dao.ver.IpaDao;
import com.yuchengtech.mobile.console.entity.ver.Ipa;
import com.yuchengtech.mobile.console.service.EntityManager;

/**
 * 安全相关实体的管理类, 包括用户,角色,资源与授权类.
 */
// Spring Bean的标识.
@Service(value = "ipaManager")
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class IpaManager extends EntityManager<Ipa, Long> {

	private static Logger logger = LoggerFactory.getLogger(IpaManager.class);

	@Autowired
	private IpaDao ipaDao;

	@Override
	protected HibernateDao<Ipa, Long> getEntityDao() {
		return ipaDao;
	}

	public Ipa getMaxVer() {
		return ipaDao.getMaxVer();
	}

	public Ipa getMaxVerNoStatus() {
		return ipaDao.getMaxVerNoStatus();
	}

	public Ipa getVerByVid(String vid) {
		return ipaDao.getVerByVid(vid);
	}

	public void ativekWebResource(Long id) throws Exception {
		Ipa ipa = ipaDao.get(id);
		ipa.setStatus("1");
		ipaDao.update(ipa);
	}

	public int rollbackIpa(String realPath, Long id) throws Exception {
		Ipa ipa = ipaDao.get(id);
		File a = new File(realPath + Constants.UPLOAD_PATH + "/" + ipa.getFilename());
		if (a.exists())
			a.delete();
		ipaDao.delete(id);
		return 1;
	}

	public int saveIpa(Ipa ipa, File uploadFile, String realPath, String ipaName) throws Exception {
		Map<String, String> map = getIpaInfoMap(uploadFile);
		String v=map.get("CFBundleShortVersionString");
		if(v==null||v.equals("")){
			return -1;
		}
		Ipa ipa_max = ipaDao.getMaxVer();
		if (ipa_max != null) {
			/*
			 * if(ipa_max.getVid().equals(String.valueOf(version.longValue()))){
			 * //上传的版本和上一个版本的一样 return -1; }
			 */
			if (ipa_max.getVid().equals(v)) {
				// 上传的版本小于或等于上一个版本
				return -1;
			}
		}
		String size = String.valueOf(uploadFile.length());
		ipa.setVsize(size);
		ipa.setVid(v);
		ipa.setUpdate_dt(new Date());
		if (ipa_max == null) {
			ipa.setStatus(null);
			ipa.setForceUpdate(null);
		} else {
			ipa.setStatus("0");
		}

		// ipa.setUrl(Constants.MOBILE_SERVER_URL+Constants.UPLOAD_PATH+"/"+ipaName);
//		ipa.setResourceData(WebResourceManager.getBytes(uploadFile.getAbsolutePath()));
		ipa.setFilename(ipaName);
		File f = new File(realPath + Constants.UPLOAD_PATH + "/" + ipaName);
		FileUtils.copyFile(uploadFile, f);
		ipaDao.save(ipa);

		uploadFile.deleteOnExit();
		return 1;
	}

	/*
	 * public static void main(String[] args) throws Exception { JFrame jframe =
	 * new JFrame(); jframe.setDefaultCloseOperation(3); FileDialog filedialog =
	 * new FileDialog(jframe); filedialog.setVisible(true); if
	 * (filedialog.getFile() == null) System.exit(1); String s = (new
	 * StringBuilder
	 * ()).append(filedialog.getDirectory()).append(filedialog.getFile
	 * ()).toString(); File file = new File(s); File file1 = new File((new
	 * StringBuilder()).append(s).append(".xml.plist").toString()); long l =
	 * System.currentTimeMillis(); PropertyListParser.convertToXml(file, file1);
	 * long l1 = System.currentTimeMillis(); System.out.println((new
	 * StringBuilder()).append("Conversion took ").append(l1 -
	 * l).append(" ms").toString()); System.exit(0); }
	 */
	/**
	 * 解压IPA文件，只获取IPA文件的Info.plist文件存储指定位置
	 * 
	 * @param file
	 *            zip文件
	 * @param unzipDirectory
	 *            解压到的目录
	 * @throws Exception
	 */
	private static File getZipInfo(File file, String unzipDirectory) throws Exception {
		// 定义输入输出流对象
		InputStream input = null;
		OutputStream output = null;
		File result = null;
		File unzipFile = null;
		ZipFile zipFile = null;
		try {
			// 创建zip文件对象
			zipFile = new ZipFile(file);
			// 创建本zip文件解压目录
			String name = file.getName().substring(0, file.getName().lastIndexOf("."));
			unzipFile = new File(unzipDirectory + "/" + name);
			if (unzipFile.exists()) {
				unzipFile.delete();
			}
			unzipFile.mkdir();
			// 得到zip文件条目枚举对象
			Enumeration<ZipArchiveEntry> zipEnum = zipFile.getEntries();
			// 定义对象
			ZipArchiveEntry entry = null;
			String entryName = null;
			String names[] = null;
			int length;
			// 循环读取条目
			while (zipEnum.hasMoreElements()) {
				// 得到当前条目
				entry = zipEnum.nextElement();
				entryName = new String(entry.getName());
				// 用/分隔条目名称
				names = entryName.split("\\/");
				length = names.length;
				for (int v = 0; v < length; v++) {
					if (entryName.endsWith(".app/Info.plist")) { // 为Info.plist文件,则输出到文件
						input = zipFile.getInputStream(entry);
						result = new File(unzipFile.getAbsolutePath() + "/Info.plist");
						output = new FileOutputStream(result);
						byte[] buffer = new byte[1024 * 8];
						int readLen = 0;
						while ((readLen = input.read(buffer, 0, 1024 * 8)) != -1) {
							output.write(buffer, 0, readLen);
						}
						break;
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (input != null)
				input.close();
			if (output != null) {
				output.flush();
				output.close();
			}
			// 必须关流，否则文件无法删除
			if (zipFile != null) {
				zipFile.close();
			}
		}
		// 如果有必要删除多余的文件
		if (file.exists()) {
			file.delete();
		}
		return result;
	}

	/**
	 * IPA文件的拷贝，把一个IPA文件复制为Zip文件,同时返回Info.plist文件 参数 oldfile 为 IPA文件
	 */
	private static File getIpaInfo(File oldfile) throws IOException {
		try {
			int byteread = 0;
			String filename = oldfile.getAbsolutePath().replaceAll(".ipa", ".zip");
			File newfile = new File(filename);
			if (oldfile.exists()) {
				// 创建一个Zip文件
				InputStream inStream = new FileInputStream(oldfile);
				FileOutputStream fs = new FileOutputStream(newfile);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					fs.write(buffer, 0, byteread);
				}
				if (inStream != null) {
					inStream.close();
				}
				if (fs != null) {
					fs.close();
				}
				// 解析Zip文件
				return getZipInfo(newfile, newfile.getParent());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 通过IPA文件获取Info信息
	 * 这个方法可以重构，原因是指获取了部分重要信息，如果想要获取全部，那么应该返回一个Map<String,Object>
	 * 对于plist文件中的数组信息应该序列化存储在Map中，那么只需要第三发jar提供的NSArray可以做到！
	 */
	public static Map<String, String> getIpaInfoMap(File ipa) throws Exception {

		Map<String, String> map = new HashMap<String, String>();
		File file = getIpaInfo(ipa);
		// 第三方jar包提供
		NSDictionary rootDict = (NSDictionary) PropertyListParser.parse(file);
		// 应用包名
		NSString parameters = (NSString) rootDict.objectForKey("CFBundleIdentifier");
		map.put("CFBundleIdentifier", parameters.toString());
		// 应用名称
		parameters = (NSString) rootDict.objectForKey("CFBundleName");
		map.put("CFBundleName", parameters.toString());
		// 应用版本
		parameters = (NSString) rootDict.objectForKey("CFBundleVersion");
		map.put("CFBundleVersion", parameters.toString());
		
		// 应用版本
				parameters = (NSString) rootDict.objectForKey("CFBundleShortVersionString");
				map.put("CFBundleShortVersionString", parameters.toString());
				
				
		// 应用展示的名称
		parameters = (NSString) rootDict.objectForKey("CFBundleDisplayName");
		map.put("CFBundleDisplayName", parameters.toString());
		// 应用所需IOS最低版本
		parameters = (NSString) rootDict.objectForKey("MinimumOSVersion");
		map.put("MinimumOSVersion", parameters.toString());

		// 如果有必要，应该删除解压的结果文件
		file.delete();
		file.getParentFile().delete();

		return map;
	}

	public static void main(String[] args) throws Exception {

		File file = new File("C:\\Users\\Administrator\\Downloads\\20140919142959.ipa");
		Map<String, String> map = getIpaInfoMap(file);
		for (String key : map.keySet()) {
			System.out.println(key + " : " + map.get(key));
		}

		// CFBundleIdentifier : com.qihoo.installSafety
		// CFBundleDisplayName : 360手机卫士
		// CFBundleName : 360MobileSafe
		// CFBundleVersion : 4.2.0.2
		// MinimumOSVersion : 5.0

	}

}
