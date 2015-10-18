package com.yuchengtech.mobile.console.entity.ver;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 注意实体类的private属性和getter、setter方法由系统生成，请不要修改 另外添加以下信息不会被覆盖： 1.import语句 2.
 * 非private属性、static属性 3. 非 getter、setter方法、 static方法 4. 继承接口
 */
@Entity
@Table(name = "TBL_Param")
public class Param implements Serializable {

	private static final long serialVersionUID = -7683022150325126484L;

	private Long id;
	private String code;
	private String value;
	private String des;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false, precision = 19, scale = 0)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
    @Column(name="code")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
    @Column(name="value")
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
    @Column(name="des")
	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

}
