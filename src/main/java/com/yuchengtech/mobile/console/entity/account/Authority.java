/******************************************************************************
 * 
 * 北京宇信易诚科技有限公司-移动平台
 *
 * Copyright © 2012 Yucheng Technologies Limited All Rights Reserved.
 * 
 * Created on 2012-11-16 0:55:13
 *
 *******************************************************************************/
package com.yuchengtech.mobile.console.entity.account;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 资源权限实体类
 *
 * @version 1.0
 *
 * @author mark
 */
@Entity
@Table(name="TBL_AUTHORITY")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Authority  implements java.io.Serializable {

	/**
 	  *  ID
      */
 	private Long id;
	/**
 	  *  上级权限ID
      */
 	private Long pid;
	/**
 	  *  权限代码
      */
 	private String code;
	/**
 	  *  权限名称
      */
 	private String name;
	/**
 	  *  类型
      */
 	private String type;
	/**
 	  *  排列顺序
      */
 	private Long rank;
	/**
 	  *  URL
      */
 	private String url;

    public Authority() {
    }
	
    public Authority(Long id) {
        this.id = id;
    }
    
    public Authority(Long id, Long pid, String code, String name, String type) {
        this.id = id;
        this.pid = pid;
        this.code = code;
        this.name = name;
        this.type = type;
        this.rank = 0L;
     }
    
    public Authority(Long id, Long pid, String code, String name, String type, Long rank, String url) {
       this.id = id;
       this.pid = pid;
       this.code = code;
       this.name = name;
       this.type = type;
       this.rank = rank;
       this.url = url;
    }
   
   /**     
     *访问<ID>属性
     */
    @Id 
    @Column(name="ID", unique=true, nullable=false, scale=0)
 	@GeneratedValue(strategy=GenerationType.AUTO) 
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
     *访问<上级权限ID>属性
     */
    @Column(name="PID", scale=0)
    public Long getPid() {
        return this.pid;
    }	    
    /**     
     *设置<上级权限ID>属性
     */
    public void setPid(Long pid) {
        this.pid = pid;
    }
   /**     
     *访问<权限代码>属性
     */
    @Column(name="CODE", length=40)
    public String getCode() {
        return this.code;
    }	    
    /**     
     *设置<权限代码>属性
     */
    public void setCode(String code) {
        this.code = code;
    }
   /**     
     *访问<权限名称>属性
     */
    @Column(name="NAME", length=40)
    public String getName() {
        return this.name;
    }	    
    /**     
     *设置<权限名称>属性
     */
    public void setName(String name) {
        this.name = name;
    }
   /**     
     *访问<类型>属性
     */
    @Column(name="TYPE", length=10)
    public String getType() {
        return this.type;
    }	    
    /**     
     *设置<类型>属性
     */
    public void setType(String type) {
        this.type = type;
    }
   /**     
     *访问<排列顺序>属性
     */
    @Column(name="RANK", precision=11, scale=0)
    public Long getRank() {
        return this.rank;
    }	    
    /**     
     *设置<排列顺序>属性
     */
    public void setRank(Long rank) {
        this.rank = rank;
    }
   /**     
     *访问<URL>属性
     */
    @Column(name="URL", length=100)
    public String getUrl() {
        return this.url;
    }	    
    /**     
     *设置<URL>属性
     */
    public void setUrl(String url) {
        this.url = url;
    }

}
