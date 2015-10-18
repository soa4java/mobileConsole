package com.yuchengtech.mobile.console.utils.excel;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Excel {
	
	public String fieldLabel();
	
	public int fieldWidth();
	
	public boolean convert() default false;

}
