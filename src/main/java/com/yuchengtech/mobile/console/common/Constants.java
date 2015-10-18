package com.yuchengtech.mobile.console.common;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.yuchengtech.mobile.console.entity.ver.Param;

public class Constants {

	public static String CONTEXT_PATH;
	
	public static String CONTEXT_REAL_PATH;
	
	public static String UPLOAD_PATH;
	
	public static String  BACKEND_HTTP_URL;
	
	public static String  MOBILE_SERVER_URL;
	public static int  BACKEND_HTTP_MaxConnection;
	
	public static boolean   Client_debug_mode;
	
	public static double  MIN_RESOURCE_VER = 0;
	
	public static String appkey;
	
	public static String masterSecret;
	
	public static final int PAGE_SIZE = 10;
	
	public static   int merge_max_fixs = 100;
	
	public static   long direct_update_size = 100000000;
	
	
	private static Map<String, String> dataMap=new HashMap<String, String>();
	
	public static void initData(List<Param> list){
		for(Param p:list){
			dataMap.put(p.getCode(),p.getValue());
		}
		initField();
	}
	
	public static String getValue(String para){
		String v= dataMap.get(para);
		return v!=null?v:"";
	}
	
	private static void initField(){
		
		try {
			String value = null;
			String fieldName = null;
			String fieldType = null;
			
			Class<Constants> clazz = Constants.class;
			Constants obj = clazz.newInstance();
			Field[] fields = clazz.getDeclaredFields();
			for(Field field:fields){
				fieldName = field.getName();
				if(dataMap.containsKey(fieldName)){
					value = dataMap.get(fieldName);
					if (null!=value) {
						fieldType = field.getType().getName();
					    if (String.class.getName().equals(fieldType)) {
							field.set(obj, value);
						}else if ("int".equals(fieldType)) {
							field.setInt(obj, Integer.parseInt(value));
						}else if ("boolean".equals(fieldType)) {
							field.setBoolean(obj, Boolean.parseBoolean(value));
						}else {
							//do nothing
						}
					}					
				}
}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		}
	/*
	static {
		Properties properties = new Properties();
		InputStream in = null;
		String value = null;
		String fieldName = null;
		String fieldType = null;
		Method method =null;
		try {
			in = Constants.class.getResourceAsStream("/constants.properties");
			properties.load(in);	
			Class<Constants> clazz = Constants.class;
			Constants obj = clazz.newInstance();
			Field[] fields = clazz.getDeclaredFields();
			for(Field field:fields){
				fieldName = field.getName();
				if(properties.containsKey(fieldName)){
					method = Properties.class.getDeclaredMethod("getProperty", String.class);
					value = (String) method.invoke(properties, fieldName);
					if (null!=value) {
						fieldType = field.getType().getName();
					    if (String.class.getName().equals(fieldType)) {
							field.set(obj, value);
						}else if ("int".equals(fieldType)) {
							field.setInt(obj, Integer.parseInt(value));
						}else if ("boolean".equals(fieldType)) {
							field.setBoolean(obj, Boolean.parseBoolean(value));
						}else {
							//do nothing
						}
					}					
				}
			}
		} catch (Exception e) {
			System.err.println("载入配置文件/constants.properties时发生错误");
			e.printStackTrace();
		}finally{
			if (in!=null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	*//**
	 * 私有构造函数,仅允许Constants在初始化成员变量时创建实例,不允许其他类调用
	 *//*
	private Constants(){}*/
	
}