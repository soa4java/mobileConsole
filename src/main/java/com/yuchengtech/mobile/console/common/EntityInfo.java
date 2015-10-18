package com.yuchengtech.mobile.console.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 对POJO的注解
 * 
 * @author mark
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EntityInfo {
	/**
	 * 名称
	 */
	public String name();
	/**
	 * 可以标识实体、且具有可读性的属性
	 */
	public String identityFiled();
	
}
