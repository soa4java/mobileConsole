package com.yuchengtech.mobile.sso.utils;


import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Random;

import javax.crypto.Cipher;

public class EncryptUtils {
	public static void main(String args[]){
		String url="http://127.0.0.1:8080/lianaSSO/login";
//		sentHttp(url);
	}
	private static String getEncrypt(){
		String m="141096961237818211009474156651931445761741225854119207051065948487282493644239753010007792788010582090710435865734764818911765969160838462682895104414704741791665566507562530019103250219559423151959694079470416464248231073457391066085289491601978063676238062487195139487050567104020430678532932685159172649489";
		String p="65537";
		String e=getSendPara(m,p);
		return e;
	}
/*	private static void sentHttp(String url){
		try {
			DefaultHttpClient httpclient = new DefaultHttpClient(); // 实例化一个HttpClient

			HttpResponse response = null;

			HttpEntity entity = null;
			String m="141096961237818211009474156651931445761741225854119207051065948487282493644239753010007792788010582090710435865734764818911765969160838462682895104414704741791665566507562530019103250219559423151959694079470416464248231073457391066085289491601978063676238062487195139487050567104020430678532932685159172649489";
			String p="65537";

			HttpPost httpost = new HttpPost(url); // 引号中的参数是：servlet的地址

			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			String e=getSendPara(m,p);
			System.out.println(e);
			nvps.add(new BasicNameValuePair("e",e));
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8)); // 将参数传入post方法中
			response = httpclient.execute(httpost); // 执行
			entity = response.getEntity(); // 返回服务器响应

			System.out.println("send result: " + response.getStatusLine());
		} catch (Exception e) {
			e.printStackTrace();
		} 

	}*/
	private static String getSendPara(String modulus, String public_exponent) {
		try {
			RSAPublicKey pubKey = getPublicKey(modulus, public_exponent);
			Random r = new Random(1000);
			long random = r.nextLong();
			long curr = System.currentTimeMillis();
			String text = random + "|" + curr;
			String en = encryptByPublicKey(text, pubKey);
			System.out.println(en);
			return en;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	

	public static RSAPublicKey getPublicKey(String modulus, String exponent) {
		try {
			BigInteger b1 = new BigInteger(modulus);
			BigInteger b2 = new BigInteger(exponent);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			RSAPublicKeySpec keySpec = new RSAPublicKeySpec(b1, b2);
			return (RSAPublicKey) keyFactory.generatePublic(keySpec);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String encryptByPublicKey(String data, RSAPublicKey publicKey)
			throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		// 模长
		int key_len = publicKey.getModulus().bitLength() / 8;
		// 加密数据长度 <= 模长-11
		String[] datas = splitString(data, key_len - 11);
		String mi = "";
		// 如果明文长度大于模长-11则要分组加密
		for (String s : datas) {
			mi += bcd2Str(cipher.doFinal(s.getBytes()));
		}
		return mi;
	}
    public static String[] splitString(String string, int len) {  
        int x = string.length() / len;  
        int y = string.length() % len;  
        int z = 0;  
        if (y != 0) {  
            z = 1;  
        }  
        String[] strings = new String[x + z];  
        String str = "";  
        for (int i=0; i<x+z; i++) {  
            if (i==x+z-1 && y!=0) {  
                str = string.substring(i*len, i*len+y);  
            }else{  
                str = string.substring(i*len, i*len+len);  
            }  
            strings[i] = str;  
        }  
        return strings;  
    } 
    public static String bcd2Str(byte[] bytes) {  
        char temp[] = new char[bytes.length * 2], val;  
  
        for (int i = 0; i < bytes.length; i++) {  
            val = (char) (((bytes[i] & 0xf0) >> 4) & 0x0f);  
            temp[i * 2] = (char) (val > 9 ? val + 'A' - 10 : val + '0');  
  
            val = (char) (bytes[i] & 0x0f);  
            temp[i * 2 + 1] = (char) (val > 9 ? val + 'A' - 10 : val + '0');  
        }  
        return new String(temp);  
    }  
}
