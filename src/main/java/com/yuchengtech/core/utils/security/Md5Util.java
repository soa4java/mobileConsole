package com.yuchengtech.core.utils.security;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {
	 private static StringBuffer buf;

		public static String stringToMd5(String plainText ) { 
			
			System.out.println("===============plainText:"+plainText);
			
			try { 
			MessageDigest md = MessageDigest.getInstance("MD5"); 
			md.update(plainText.getBytes()); 
			byte b[] = md.digest(); 

			int i; 

			buf = new StringBuffer(""); 
			for (int offset = 0; offset < b.length; offset++) { 
			i = b[offset]; 
			if(i<0) i+= 256; 
			if(i<16) 
			buf.append("0"); 
			buf.append(Integer.toHexString(i)); 
			} 

			//System.out.println("result: " + buf.toString());//32位的加密 

			//System.out.println("result: " + buf.toString().substring(8,24));//16位的加密 

			} catch (NoSuchAlgorithmException e) { 
			// TODO Auto-generated catch block 
			e.printStackTrace(); 
			}
			return buf.toString(); 
			} 
		
		
		public static byte[] createChecksum(String filename) throws Exception {
	        InputStream fis =  new FileInputStream(filename);

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
	    }
	 
	   public static String fileToMd5(String filename) throws Exception {
//		   System.out.println("=======================filename:"+filename);
		   
		   //如果是苹果系统zip包中的内置文件夹则不作hash校验
		   if (filename.indexOf("__MACOSX")!=-1) {
			   System.out.println("=======================md5:");
			   return "";
		   }
		   
	        byte[] b = createChecksum(filename);
	        String result = "";

	        for (int i=0; i < b.length; i++) {
	            result += Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
	        }
	        
			System.out.println("filename:"+filename+" md5:"+result);
	        return result;
	    }
		
}
