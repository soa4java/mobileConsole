package com.yuchengtech.mobile.console.entity.push;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import static javax.persistence.GenerationType.SEQUENCE;

import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * TblPushinfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TBL_PUSHINFO")
public class TblPushinfo implements java.io.Serializable {

	// Fields

	private Long id;
	private String pushinfo;
	private String pushtype;
	private Timestamp pushdate;
	private String masgId;

	// Constructors

	/** default constructor */
	public TblPushinfo() {
	}

	/** full constructor */
	public TblPushinfo(String pushinfo, String pushtype, Timestamp pushdate,
			String pushresult) {
		this.pushinfo = pushinfo;
		this.pushtype = pushtype;
		this.pushdate = pushdate;
		this.masgId = pushresult;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "ID", unique = true, nullable = false, precision = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "PUSHINFO", length = 200)
	public String getPushinfo() {
		return this.pushinfo;
	}

	public void setPushinfo(String pushinfo) {
		this.pushinfo = pushinfo;
	}

	@Column(name = "PUSHTYPE", length = 1)
	public String getPushtype() {
		return this.pushtype;
	}

	public void setPushtype(String pushtype) {
		this.pushtype = pushtype;
	}

	@Column(name = "PUSHDATE", length = 7)
	public Timestamp getPushdate() {
		return this.pushdate;
	}

	public void setPushdate(Timestamp pushdate) {
		this.pushdate = pushdate;
	}

	@Column(name = "MSG_ID", length = 100)
	public String getMasgId() {
		return this.masgId;
	}

	public void setMasgId(String pushresult) {
		this.masgId = pushresult;
	}

}