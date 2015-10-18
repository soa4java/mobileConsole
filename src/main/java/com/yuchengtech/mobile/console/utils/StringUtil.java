package com.yuchengtech.mobile.console.utils;

public class StringUtil {
	
	public static Object convertType(String strValue, Class fieldType) {
		Object value =null;
		String type = fieldType.getName();
		if("java.lang.Integer".equals(type)){
			value = Integer.parseInt(strValue);
		}else if ("java.lang.Long".equals(type)){
			value = Long.parseLong(strValue);
		}else {
			value = strValue;
		}
		return value;
	}

}
