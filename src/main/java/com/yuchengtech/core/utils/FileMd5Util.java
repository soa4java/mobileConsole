package com.yuchengtech.core.utils;

  

public class FileMd5Util {

	//获取某个文件夹的所有子文件的md5值（先取每个子文件的md5值，并连接成一个字符串，再取其md5值）
	public static String getMd5(String path){
		MD5 m =new MD5();
		String md5=m.md5CodeByEveryFile(path);
		String newmd5=m.md5CodeByString(md5);
		m.C=new StringBuffer();
		System.out.println("count...."+m.count);
		System.out.println("hash...."+newmd5);
		return newmd5;
		//得到所有子文件的md5值连接在一起的字符串
//		String mds =getMd5ForRecursion(path);
		
		//再次取md5值
//		return Md5Util.stringToMd5(mds);
	}
	/*
	//递归方式获取md5值
	public static String getMd5ForRecursion(String path){
		String mds ="";
		File fileFolder = new File(path);
 		File[] files = fileFolder.listFiles();
 		
		StringBuilder md5=new StringBuilder("");
		
		for(File file:files){
 			try {
				if (file.isDirectory()) {
					md5.append(getMd5ForRecursion(file.getPath()));
				}else{
					if(!file.getName().equals("cordova.js"))
					md5.append(Md5Util.fileToMd5(file.getPath()));
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return md5.toString();
	}*/
}