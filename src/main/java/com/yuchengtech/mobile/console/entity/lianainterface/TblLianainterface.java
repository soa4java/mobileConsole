package com.yuchengtech.mobile.console.entity.lianainterface;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TblLianainterface entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TBL_LIANAINTERFACE", schema = "PLATFORM")
public class TblLianainterface implements java.io.Serializable {

	// Fields

	private Long id;
	private String interfacename;
	private String interfaceAddress;

	// Constructors

	/** default constructor */
	public TblLianainterface() {
	}

	/** full constructor */
	public TblLianainterface(String interfacename, String interfaceAddress) {
		this.interfacename = interfacename;
		this.interfaceAddress = interfaceAddress;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "ID", unique = true, nullable = false, length = 12)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "INTERFACENAME", length = 20)
	public String getInterfacename() {
		return this.interfacename;
	}

	public void setInterfacename(String interfacename) {
		this.interfacename = interfacename;
	}

	@Column(name = "INTERFACEADDRESS", length = 30)
	public String getinterfaceAddress() {
		return this.interfaceAddress;
	}

	public void setinterfaceAddress(String interfaceAddress) {
		this.interfaceAddress = interfaceAddress;
	}

}