/******************************************************************************
 * 
 * 北京宇信易诚科技有限公司-移动平台
 *
 * Copyright © 2012 Yucheng Technologies Limited All Rights Reserved.
 * 
 * Created on 2012-11-18 15:29:39
 *
 *******************************************************************************/
package com.yuchengtech.mobile.console.entity.account;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

import com.google.common.collect.Lists;
import com.yuchengtech.core.utils.reflection.ConvertUtils;

/**
 * VUser实体类
 *
 * @version 1.0
 *
 * @author mark
 */
@Entity
@Table(name="V_USER")
public class VUser  implements UserDetails {

	/**
 	  *  id
      */
 	private Long id;
	/**
 	  *  username
      */
 	private String username;
	/**
 	  *  name
      */
 	private String name;
	/**
 	  *  deptCode
      */
 	private String deptCode;
	/**
 	  *  deptName
      */
 	private String deptName;
	/**
 	  *  deptNo
      */
 	private String deptNo;
	/**
 	  *  password
      */
 	private String password;
	/**
 	  *  remark
      */
 	private String remark;
 	/**
	  *  email
     */
	private String email;
	/**
 	  *  status
      */
 	private String status;
 
	private Integer headOffice;
	
	private String branchCode;
 	
 	private List<Role> roleList = Lists.newArrayList();
 	
 	private String salt;
 	
 	private Long loginId;

    public VUser() {
    }
	
    public VUser(Long id, String username) {
        this.id = id;
        this.username = username;
    }
    public VUser(Long id, String username, String name, String deptCode, String deptName, String deptNo, String password, String remark, String status) {
       this.id = id;
       this.username = username;
       this.name = name;
       this.deptCode = deptCode;
       this.deptName = deptName;
       this.deptNo = deptNo;
       this.password = password;
       this.remark = remark;
       this.status = status;
    }
   
   /**     
     *访问<id>属性
     */
     @Id 
    @Column(name="ID", unique=true, nullable=false, scale=0)
    public Long getId() {
        return this.id;
    }	    
    /**     
     *设置<id>属性
     */
    public void setId(Long id) {
        this.id = id;
    }
   /**     
     *访问<username>属性
     */
    @Column(name="USERNAME", nullable=false, length=510)
    public String getUsername() {
        return this.username;
    }	    
    /**     
     *设置<username>属性
     */
    public void setUsername(String username) {
        this.username = username;
    }
   /**     
     *访问<name>属性
     */
    @Column(name="NAME", length=510)
    public String getName() {
        return this.name;
    }	    
    /**     
     *设置<name>属性
     */
    public void setName(String name) {
        this.name = name;
    }
   /**     
     *访问<deptCode>属性
     */
    @Column(name="DEPT_CODE", length=100)
    public String getDeptCode() {
        return this.deptCode;
    }	    
    /**     
     *设置<deptCode>属性
     */
    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }
   /**     
     *访问<deptName>属性
     */
    @Column(name="DEPT_NAME", length=50)
    public String getDeptName() {
        return this.deptName;
    }	    
    /**     
     *设置<deptName>属性
     */
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
   /**     
     *访问<deptNo>属性
     */
    @Column(name="DEPT_NO", length=100)
    public String getDeptNo() {
        return this.deptNo;
    }	    
    /**     
     *设置<deptNo>属性
     */
    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }
   /**     
     *访问<password>属性
     */
    @Column(name="PASSWORD", length=510)
    public String getPassword() {
        return this.password;
    }	    
    /**     
     *设置<password>属性
     */
    public void setPassword(String password) {
        this.password = password;
    }
   /**     
     *访问<remark>属性
     */
    @Column(name="REMARK", length=200)
    public String getRemark() {
        return this.remark;
    }	    
    /**     
     *设置<remark>属性
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
   public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

/**     
     *访问<status>属性
     */
    @Column(name="STATUS", length=1)
    public String getStatus() {
        return this.status;
    }	    
    /**     
     *设置<status>属性
     */
    public void setStatus(String status) {
        this.status = status;
    }
    
	
	public Integer getHeadOffice() {
		return headOffice;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setHeadOffice(Integer headOffice) {
		this.headOffice = headOffice;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	@Transient
	 public String getOrgCode() {
		 if (this.deptNo==null) {
			 return null;
		}else {
			return StringUtils.substring(this.deptNo, 0, 8);		
		}
	}

	//多对多定义
  	@ManyToMany(fetch=FetchType.EAGER)
  	//中间表定义,表名采用默认命名规则
  	@JoinTable(name = "TBL_USER_ROLE", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
  	//Fecth策略定义
  	@Fetch(FetchMode.SUBSELECT)
  	//集合按id排序.	
  	@OrderBy("id")
  	//集合中对象id的缓存.
//  	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  	public List<Role> getRoleList() {
  		return roleList;
  	}

  	public void setRoleList(List<Role> roleList) {
  		this.roleList = roleList;
  	}
  	
	//非持久化属性.
	@Transient
	public String getRoleNames() {
		return ConvertUtils.convertElementPropertyToString(roleList, "name", ", ");
	}

	/**
	 * 用户拥有的角色id字符串, 多个角色id用','分隔.
	 */
	//非持久化属性.
	@Transient
	@SuppressWarnings("unchecked")
	public List<Long> getRoleIds() {
		return ConvertUtils.convertElementPropertyToList(roleList, "id");
	}
	
	/*******************************************************************/
	@Transient
	public Collection<GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();
		for (Role role : roleList) {
				authSet.add(new GrantedAuthorityImpl(String.valueOf(role.getId())));
		}
		return authSet;
	}

	@Transient
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Transient
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Transient
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Transient
	public boolean isEnabled() {
		return "1".equals(status);
	}
	
	@Transient
	public String getSalt() {
		if (salt==null) {
			salt = DigestUtils.md5Hex(username+808);
		}
		return salt;
	}

	@Transient
	public Long getLoginId() {
		return loginId;
	}

	public void setLoginId(Long loginId) {
		this.loginId = loginId;
	}

}
