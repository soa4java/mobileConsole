package com.yuchengtech.core.utils.security;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * AES加解密工具类
 * 
 * @author SunLiang
 * @version 1.0
 * @since 2012-12-24
 */
public class AESUtil {

	/**
	 * 原文的字符编码
	 */
	private static String charset="utf-8";
	
	public static String getCharset() {
		return charset;
	}

	public static void setCharset(String charset) {
		AESUtil.charset = charset;
	}

	/**
	 * 生成密钥种子
	 * 
	 * @return
	 */
	private static String createKeySeed() {
		//时间戳
		long ts=System.currentTimeMillis();
		
		//两位随机数
		String randomCode = "";
		Random random = new Random();
		float randomFload = random.nextFloat();
		int randomInt = (int) (randomFload * 100);
		if (randomInt > 0) {
			if (randomInt < 10) {
				randomCode = "0" + randomInt;
			} else {
				randomCode = "" + randomInt;
			}
		}
		
		return ts+randomCode;
	}
	
	/**
	 * 生成AES128位密钥
	 * 
	 * @return
	 * @throws AESException 
	 */
	public static String createAESKey() throws AESException {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");

			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(createKeySeed().getBytes());

			kgen.init(256, secureRandom);
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();

			//转化成字符串
			String result=parseByte2Str(enCodeFormat);
			
			return result;
		} catch (NoSuchAlgorithmException e) {
			throw new AESException(e);
		}
	}

	/**
	 * 加密
	 * 
	 * @param content
	 *            原文
	 * @param enCodekey
	 *            加密密码
	 * @return
	 * @throws AESException 
	 */
	public static String encrypt(String content, String enCodekey) throws AESException {
		try {
			//转换参数为字节数组
			byte[] byteContent = content.getBytes(charset);
			byte[] enCodeFormat=parseStr2Byte(enCodekey);
			
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
			byte[] encrypted = cipher.doFinal(byteContent);// 加密

			//将加密结果转化成字符串
			String result=parseByte2Str(encrypted);
			
			return result;
		} catch (NoSuchAlgorithmException e) {
			throw new AESException(e);
		} catch (NoSuchPaddingException e) {
			throw new AESException(e);
		} catch (InvalidKeyException e) {
			throw new AESException(e);
		} catch (UnsupportedEncodingException e) {
			throw new AESException(e);
		} catch (IllegalBlockSizeException e) {
			throw new AESException(e);
		} catch (BadPaddingException e) {
			throw new AESException(e);
		}
	}

	/**
	 * 解密
	 * 
	 * @param content
	 *            密文
	 * @param enCodekey
	 *            解密密钥
	 * @return
	 * @throws AESException 
	 */
	public static String decrypt(String encrypted, String enCodekey) throws AESException {
		try {
			//转换参数为字节数组
			byte[] enCodeFormat=parseStr2Byte(enCodekey);
			byte[] content=parseStr2Byte(encrypted);
			
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
			byte[] decrypted = cipher.doFinal(content);//解密
			
			//将解密后的内容转化成字符串
			String result=new String(decrypted,charset);
			
			return result;
		} catch (NoSuchAlgorithmException e) {
			throw new AESException(e);
		} catch (NoSuchPaddingException e) {
			throw new AESException(e);
		} catch (InvalidKeyException e) {
			throw new AESException(e);
		} catch (IllegalBlockSizeException e) {
			throw new AESException(e);
		} catch (BadPaddingException e) {
			throw new AESException(e);
		} catch (UnsupportedEncodingException e) {
			throw new AESException(e);
		}
	}

	/**
	 * 将二进制转换成字符串
	 * 
	 * @param buf
	 * @return
	 */
	public static String parseByte2Str(byte buf[]) {
		// StringBuffer sb = new StringBuffer();
		// for (int i = 0; i < buf.length; i++) {
		// String hex = Integer.toHexString(buf[i] & 0xFF);
		// if (hex.length() == 1) {
		// hex = '0' + hex;
		// }
		// sb.append(hex.toUpperCase());
		// }
		// return sb.toString();

		BASE64Encoder base64encoder = new BASE64Encoder();
		String encode = base64encoder.encode(buf);

		return encode;
	}

	/**
	 * 将字符串转换为二进制
	 * 
	 * @param hexStr
	 * @return
	 * @throws AESException 
	 */
	public static byte[] parseStr2Byte(String hexStr) throws AESException {
		// if (hexStr.length() < 1)
		// return null;
		// byte[] result = new byte[hexStr.length() / 2];
		// for (int i = 0; i < hexStr.length() / 2; i++) {
		// int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
		// int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
		// 16);
		// result[i] = (byte) (high * 16 + low);
		// }
		// return result;

		BASE64Decoder base64decoder = new BASE64Decoder();
		byte[] encodeByte = null;
		try {
			encodeByte = base64decoder.decodeBuffer(hexStr);
			return encodeByte;
		} catch (IOException e) {
			throw new AESException(e);
		}
	}

	/**
	 * @param args
	 * @throws AESException 
	 * @throws UnsupportedEncodingException 
	 */
	public static void main(String[] args) throws AESException, UnsupportedEncodingException {

		String content = "AES";

		// 生成密钥
		String key = createAESKey();
		System.err.println(key);
		// 加密
		System.out.println("加密前：" + content);
		String encryptResultStr = encrypt(content, key);
		System.out.println("加密后：" + encryptResultStr);
		// 解密
		String decryptResult = decrypt(encryptResultStr, key);
		System.out.println("解密后：" + decryptResult);

	}

}
