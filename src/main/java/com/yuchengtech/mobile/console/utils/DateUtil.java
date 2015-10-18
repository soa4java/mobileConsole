package com.yuchengtech.mobile.console.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期时间操作工具类
 * 
 * @author mark
 *
 */
public class DateUtil {
	/**
	 * 将当前时间格式化为指定的字符串
	 * 
	 * @param format 字符串格式 如：yyyy-MM-dd HH:mm:ss SSS
	 * 
	 * @return
	 */
	public static String format(String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		String s = df.format(new Date());
		return s;
	}
	
	/**
	 * 格式化当前时间
	 * 
	 * @param format 时间格式 "yyyy-MM-dd hh:mm:ss"
	 * 
	 * @return 时间串
	 */
	public static String format() {
		String defaultFormat = "yyyy-MM-dd kk:mm:ss";
		return format(defaultFormat);
	}
	
	public static String parse() {
		String defaultFormat = "yyyy-MM-dd kk:mm:ss";
		return format(defaultFormat);
	}
	
}

