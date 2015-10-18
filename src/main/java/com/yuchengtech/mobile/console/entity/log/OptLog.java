/******************************************************************************
 * 
 * 北京宇信易诚科技有限公司-移动平台
 *
 * Copyright © 2012 Yucheng Technologies Limited All Rights Reserved.
 * 
 * Created on 2012-10-29 17:46:25
 *
 *******************************************************************************/
package com.yuchengtech.mobile.console.entity.log;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.yuchengtech.mobile.console.utils.excel.Excel;

/**
 * 操作日志实体类
 *
 * @version 1.0
 *
 * @author mark
 */
@Entity
@Table(name="TBL_OPT_LOG")
public class OptLog  implements java.io.Serializable {

	/**
 	  *  ID
      */
 	private Long id;
	/**
 	  *  操作人员
      */
 	@Excel(fieldLabel="操作人员",fieldWidth=10)
 	private String operator;
	/**
 	  *  执行时间
      */
 	@Excel(fieldLabel="执行时间",fieldWidth=10)
 	private String time;
	/**
 	  *  操作类型
      */
 	@Excel(fieldLabel="操作类型",fieldWidth=10)
 	private String opt;
	/**
 	  *  操作对象
      */
 	@Excel(fieldLabel="操作对象",fieldWidth=10)
 	private String obj;
	/**
 	  *  操作对象名称
      */
 	@Excel(fieldLabel="操作对象名称",fieldWidth=10)
 	private String label;
	/**
 	  *  操作对象标识
      */
 	@Excel(fieldLabel="操作对象标识",fieldWidth=10)
 	private String identifier;
 	
 	private Long loginId;

    public OptLog() {
    }
	
    public OptLog(Long id, String operator) {
        this.id = id;
        this.operator = operator;
    }
    public OptLog(Long id, String operator, String time, String opt, String obj, String label, String identifier,Long loginId) {
       this.id = id;
       this.operator = operator;
       this.time = time;
       this.opt = opt;
       this.obj = obj;
       this.label = label;
       this.identifier = identifier;
       this.loginId = loginId;
    }
   
   /**     
     *访问<ID>属性
     */
     @Id 
  	 @GeneratedValue(strategy=GenerationType.AUTO) 
     @Column(name="ID", unique=true, nullable=false, precision=22, scale=0)
    public Long getId() {
        return this.id;
    }	    
    /**     
     *设置<ID>属性
     */
    public void setId(Long id) {
        this.id = id;
    }
   /**     
     *访问<操作人员>属性
     */
    @Column(name="OPERATOR", nullable=false, length=20)
    public String getOperator() {
        return this.operator;
    }	    
    /**     
     *设置<操作人员>属性
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }
   /**     
     *访问<执行时间>属性
     */
    @Column(name="TIME", length=20)
    public String getTime() {
        return this.time;
    }	    
    /**     
     *设置<执行时间>属性
     */
    public void setTime(String time) {
        this.time = time;
    }
   /**     
     *访问<操作类型>属性
     */
    @Column(name="OPT", length=20)
    public String getOpt() {
        return this.opt;
    }	    
    /**     
     *设置<操作类型>属性
     */
    public void setOpt(String opt) {
        this.opt = opt;
    }
   /**     
     *访问<操作对象>属性
     */
    @Column(name="OBJ", length=20)
    public String getObj() {
        return this.obj;
    }	    
    /**     
     *设置<操作对象>属性
     */
    public void setObj(String obj) {
        this.obj = obj;
    }
   /**     
     *访问<操作对象名称>属性
     */
    @Column(name="LABEL", length=20)
    public String getLabel() {
        return this.label;
    }	    
    /**     
     *设置<操作对象名称>属性
     */
    public void setLabel(String label) {
        this.label = label;
    }
   /**     
     *访问<操作对象标识>属性
     */
    @Column(name="IDENTIFIER", length=400)
    public String getIdentifier() {
        return this.identifier;
    }	    
    /**     
     *设置<操作对象标识>属性
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

	public Long getLoginId() {
		return loginId;
	}

	public void setLoginId(Long loginId) {
		this.loginId = loginId;
	}
	
}
