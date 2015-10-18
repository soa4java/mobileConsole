package com.yuchengtech.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MD5 {
	private  final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	private  String inputFileName;
	private  String zipFileName;

	private  String toHexString(byte[] b) {
		StringBuilder sb = new StringBuilder(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
			sb.append(HEX_DIGITS[b[i] & 0x0f]);
		}
		return sb.toString();
	}



	public  StringBuffer C = new StringBuffer();
	public int count=0;

	/**
	 * 计算单个文件的哈希值
	 * */
	public  String md5CodeByFile(String filename) {
		InputStream fis;
		byte[] buffer = new byte[1024];
		int numRead = 0;
		MessageDigest md5;
		try {
			fis = new FileInputStream(filename);
			md5 = MessageDigest.getInstance("MD5");
			while ((numRead = fis.read(buffer)) > 0) {
				md5.update(buffer, 0, numRead);
			}
			fis.close();
			return toHexString(md5.digest());
		} catch (Exception e) {
//			System.out.println("error");
			return null;
		}
	}
	
	/**
	 * 递归计算指定文件夹下每个文件的哈希值，输出字符串
	 * */
    @SuppressWarnings("unchecked")
	public  String md5CodeByEveryFile(String fileAbsolutePath)  {  
        try {
			File file = new File(fileAbsolutePath);  
			File[] subFile = file.listFiles();  
			
			List<File> files = Arrays.asList(subFile);
			//按文件名称排序，为了避免file.listFiles()方法在不同系统中的顺序不同
			Collections.sort(files, new Comparator< File>() {
				   public int compare(File o1, File o2) {
/*        		if (o1.isDirectory() && o2.isFile())
				          return 1;
					if (o1.isFile() && o2.isDirectory())
				          return -1;*/
					   
					/*忽略大小写排序*/
					if(o1.getName().equalsIgnoreCase(o2.getName())){
						return o1.getName().compareTo(o2.getName());
					}else{
						return o1.getName().compareToIgnoreCase(o2.getName());
					}
					
				   }
				  });

			for (int iFileLength = 0; iFileLength < files.size(); iFileLength++) {  
				// 如果file是一个目录(该目录下面可能有文件、目录文件、空文件三种情况)  
			    if (files.get(iFileLength).isDirectory()) {
			    	md5CodeByEveryFile(files.get(iFileLength).getAbsolutePath());            
			    }else{        	           
			        if(files.get(iFileLength).getName().endsWith("cordova.js")){
			        	//files.get(iFileLength).delete();//删除cordova.js
			        	continue;
			        }
			        
			    	//String fileName = subFile[iFileLength].getName();  
			        byte[] b=createChecksum(files.get(iFileLength).getAbsolutePath());
			        String result = "";
			        for (int i=0; i < b.length; i++) {
			            result += Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
			        } 
/*    	        if(result.equals("c5b2517d2c6564f574f7de13a314fd4c")){
			        	System.out.println("文件1111-------"+files.get(iFileLength).getAbsolutePath());
			        }
			        if(result.equals("94258843b03e01fc7b0d8f3b48c8c54f")){
			        	System.out.println("文件2222-------"+files.get(iFileLength).getAbsolutePath());
			        }
			        if(result.equals("8e8b87d9b53656cf62f8cf29749f68f6")){
			        	System.out.println("文件3333-------"+files.get(iFileLength).getAbsolutePath());
			        }
			        if(result.equals("02d8287616f077a823e5c7d34e158f64")){
			        	System.out.println("文件4444-------"+files.get(iFileLength).getAbsolutePath());
			        }*/

			     //  System.out.println("文件======="+files.get(iFileLength));
			     //  System.out.println("每个MD5======"+result);
			       C .append(result);
			       count++;
			    } 
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        return C.toString();  
    } 
    
  /**
   * 计算字符串的哈希值
   */
  public  String md5CodeByString(String string) {
     byte[] hash;
    
     try {
     hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
     } catch (NoSuchAlgorithmException e) {
     e.printStackTrace();
     return null;
     } catch (UnsupportedEncodingException e) {
     e.printStackTrace();
     return null;
     }
    
     StringBuilder hex = new StringBuilder(hash.length * 2);
     for (byte b : hash) {
     if ((b & 0xFF) < 0x10)
     hex.append("0");
     hex.append(Integer.toHexString(b & 0xFF));
     }
    
     return hex.toString();
    /*StringBuffer buf = new StringBuffer("");
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      md.update(string.getBytes());
      byte b[] = md.digest();
      int i;
      for (int offset = 0; offset < b.length; offset++) {
        i = b[offset];
        if (i < 0)
          i += 256;
        if (i < 16)
          buf.append("0");
        buf.append(Integer.toHexString(i));
      }
      // System.out.println("result: " + buf.toString());//32位的加密
      // System.out.println("result: " +
      // buf.toString().substring(8,24));//16位的加密
    }
    catch (NoSuchAlgorithmException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return buf.toString();*/
  }


    
	public  byte[] createChecksum(String path)  {
        try {
			InputStream fis =  new FileInputStream(path);
			byte[] buffer = new byte[1024];
			MessageDigest complete = MessageDigest.getInstance("MD5");
			int numRead;
			do {
			    numRead = fis.read(buffer);
			    if (numRead > 0) {
			        complete.update(buffer, 0, numRead);
			    }
			} while (numRead != -1);
			fis.close();
			return complete.digest();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    }
 
/*   public  String getMD5Checksum(Context context,String path) throws Exception {
	  
	   //压缩资源文件
	  inputFileName=context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()+"/login/";
	  zipFileName=context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()+"/login.zip";
	   CompressUtil.zip(inputFileName, zipFileName);
	    
	   //获取资源文件的压缩文件的哈希值
        byte[] b = createChecksum(path);
        String result = "";
        for (int i=0; i < b.length; i++) {
            result += Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
        } 
        //删除压缩文件
        File file=new File(zipFileName);
        if(file.exists()){
        file.delete();
        }
        return result;
    }*/

	
	
}
