package com.yuchengtech.mobile.console.entity.device;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TblDevice entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TBL_DEVICE")
public class TblDevice implements java.io.Serializable {

	// Fields

	private Long id;
	private String username;
	private String applyname;
	private String clintname;
	private String deviceid;

	// Constructors

	/** default constructor */
	public TblDevice() {
	}

	/** full constructor */
	public TblDevice(String username, String applyname, String clintname,
			String deviceid) {
		this.username = username;
		this.applyname = applyname;
		this.clintname = clintname;
		this.deviceid = deviceid;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "ID", unique = true, nullable = false, length = 10)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "USERNAME", length = 30)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "APPLYNAME", length = 30)
	public String getApplyname() {
		return this.applyname;
	}

	public void setApplyname(String applyname) {
		this.applyname = applyname;
	}

	@Column(name = "CLINTNAME", length = 50)
	public String getClintname() {
		return this.clintname;
	}

	public void setClintname(String clintname) {
		this.clintname = clintname;
	}

	@Column(name = "DEVICEID", length = 50)
	public String getDeviceid() {
		return this.deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

}