package com.yuchengtech.mobile.console.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.yuchengtech.mobile.console.common.Constants;

public class UploadFileUtil {
	
	public static String uploadCreditImage(File cardImage,String cardImageFileName,String subDir) throws IOException{
		String pathSubDir="";
		String urlSubDir="";
		if (subDir!=null&&!subDir.trim().equals("")) {
			pathSubDir="credit"+File.separator+subDir+File.separator;
			urlSubDir="credit/"+subDir+"/";
		}
		String uploadPath = Constants.UPLOAD_PATH+pathSubDir;
//		long current = System.currentTimeMillis();
		String suffix = DateUtil.format("yyyyMMddHHmmssSSS")+cardImageFileName.substring(cardImageFileName.lastIndexOf('.'));
		String url=Constants.UPLOAD_PATH+urlSubDir+suffix;
		String filePath = uploadPath+suffix;
		
		File dir = new File(uploadPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File file = new File(filePath);
		if (!file.exists()) {
			file.createNewFile();
		}
		FileUtils.copyFile(cardImage, file);
		
		return Constants.MOBILE_SERVER_URL+url;
	}
	
	public static void deleteCreditImage(String cardImageFileName,String subDir) throws IOException{
		String pathSubDir="";
		if (subDir!=null&&!subDir.trim().equals("")) {
			pathSubDir="credit"+File.separator+subDir+File.separator;
		}
		String uploadPath = Constants.UPLOAD_PATH+pathSubDir;
		String filePath = uploadPath+cardImageFileName;
		
		System.out.println("filePath---------"+filePath);
		
		File file = new File(filePath);
		if (file.exists()) {
			file.delete();
		}
	}
}
