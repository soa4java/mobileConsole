/******************************************************************************
 * 
 * 北京宇信易诚科技有限公司-移动平台
 *
 * Copyright © 2012 Yucheng Technologies Limited All Rights Reserved.
 * 
 * Created on 2012-11-22 0:32:35
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
 * 登录日志实体类
 *
 * @version 1.0
 *
 * @author mark
 */
@Entity
@Table(name="TBL_IPAD_LOGIN_LOG")
public class IpadLoginLog  implements java.io.Serializable {

	/**
 	  *  ID
      */
 	private Long id;
	/**
 	  *  用户
      */
 	@Excel(fieldLabel="用户",fieldWidth=10)
 	private String username;
	/**
 	  *  操作
      */
 	@Excel(fieldLabel="操作",fieldWidth=10)
 	private String opt;
	/**
 	  *  登录时间
      */
 	@Excel(fieldLabel="登录时间",fieldWidth=20)
 	private String loginTime;
	/**
 	  *  来源
      */
 	@Excel(fieldLabel="来源",fieldWidth=10)
 	private String source;
	/**
 	  *  IP地址
      */
 	@Excel(fieldLabel="IP地址",fieldWidth=10)
 	private String ip;
	/**
 	  *  状态
      */
 	@Excel(fieldLabel="状态",fieldWidth=10)
 	private String status;
 	/**
	  *  结果
    */
	@Excel(fieldLabel="结果",fieldWidth=15)
	private String result;

    public IpadLoginLog() {
    }
	
    public IpadLoginLog(Long id, String username, String opt, String loginTime, String source, String ip,String result) {
        this.id = id;
        this.username = username;
        this.opt = opt;
        this.loginTime = loginTime;
        this.source = source;
        this.ip = ip;
        this.result = result;
    }
    public IpadLoginLog(Long id, String username, String opt, String loginTime, String source, String ip, String status,String result) {
       this.id = id;
       this.username = username;
       this.opt = opt;
       this.loginTime = loginTime;
       this.source = source;
       this.ip = ip;
       this.status = status;
       this.result = result;
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
     *访问<用户>属性
     */
    @Column(name="USERNAME", nullable=false, length=20)
    public String getUsername() {
        return this.username;
    }	    
    /**     
     *设置<用户>属性
     */
    public void setUsername(String username) {
        this.username = username;
    }
   /**     
     *访问<操作>属性
     */
    @Column(name="OPT", nullable=false, length=40)
    public String getOpt() {
        return this.opt;
    }	    
    /**     
     *设置<操作>属性
     */
    public void setOpt(String opt) {
        this.opt = opt;
    }
   /**     
     *访问<登录时间>属性
     */
    @Column(name="LOGIN_TIME", nullable=false, length=20)
    public String getLoginTime() {
        return this.loginTime;
    }	    
    /**     
     *设置<登录时间>属性
     */
    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }
   /**     
     *访问<来源>属性
     */
    @Column(name="SOURCE", nullable=false, length=40)
    public String getSource() {
        return this.source;
    }	    
    /**     
     *设置<来源>属性
     */
    public void setSource(String source) {
        this.source = source;
    }
   /**     
     *访问<IP地址>属性
     */
    @Column(name="IP", nullable=false, length=20)
    public String getIp() {
        return this.ip;
    }	    
    /**     
     *设置<IP地址>属性
     */
    public void setIp(String ip) {
        this.ip = ip;
    }
   /**     
     *访问<状态>属性
     */
    @Column(name="STATUS", length=200)
    public String getStatus() {
        return this.status;
    }	    
    /**     
     *设置<状态>属性
     */
    public void setStatus(String status) {
        this.status = status;
    }
    /**     
     *访问<结果>属性
     */
    public String getResult() {
 		return result;
 	}
    /**     
     *设置<结果>属性
     */
 	public void setResult(String result) {
 		this.result = result;
 	}

}
