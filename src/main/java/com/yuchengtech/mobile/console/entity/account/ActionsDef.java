package com.yuchengtech.mobile.console.entity.account;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
@Entity
@Table(name = "TBL_MB_ACTIONSDEF")
public class ActionsDef  implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5490035750604836420L;
	/**
	 * 
	 */
	private Long id;
	private String actionurl;
	private String actioncontroller;
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "actionurl")
	public String getActionurl() {
		return actionurl;
	}
	public void setActionurl(String actionurl) {
		this.actionurl = actionurl;
	}
	@Column(name = "actioncontroller")
	public String getActioncontroller() {
		return actioncontroller;
	}
	public void setActioncontroller(String actioncontroller) {
		this.actioncontroller = actioncontroller;
	}

}
