package com.yuchengtech.mobile.console.entity.ver; 

 import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
 
/**
 * 注意实体类的private属性和getter、setter方法由系统生成，请不要修改
 * 另外添加以下信息不会被覆盖：
 *   1.import语句     2. 非private属性、static属性     3. 非 getter、setter方法、 static方法       4. 继承接口
 */
@Entity
@Table(name = "TBL_APK")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)  //打包ver.jar是去掉注释
public class Apk implements Serializable  {
 
    
	private static final long serialVersionUID = -7683022150325126481L;


	private Long id;
	 private Long vid;
	private String version;
	private String sign;
     private String forceUpdate;
     private String status;
     private String des;
     private String vsize;
     private Date update_dt;
     private String url;
     private String fileName;
     private byte[] resourceData             ;
     @Column(name="resourcedata")
 	public byte[] getResourceData() {
		return resourceData;
	}

	public void setResourceData(byte[] resourceData) {
		this.resourceData = resourceData;
	}
	@Column(name="filename")
     public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

     @Id
  	@GeneratedValue(strategy=GenerationType.AUTO) 
  	@Column(name = "id", unique = true, nullable = false, precision = 19, scale = 0)
    public Long getId(){
       return id;
    }
    public void setId(Long id){
         this.id = id;
    }
    
    @Column(name="FORCE_UPDATE")
    public String getForceUpdate(){
       return forceUpdate;
    }
    public void setForceUpdate(String forceUpdate){
         this.forceUpdate = forceUpdate;
    }
    @Column(name="vid")
	public Long getVid() {
		return vid;
	}
	public void setVid(Long vid) {
		this.vid = vid;
	}
	 @Column(name="version")
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	 @Column(name="sign")
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	 @Column(name="status")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	 @Column(name="des")
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	 @Column(name="vsize")
	public String getVsize() {
		return vsize;
	}
	public void setVsize(String vsize) {
		this.vsize = vsize;
	}
	 @Column(name="update_dt")
	public Date getUpdate_dt() {
		return update_dt;
	}
	public void setUpdate_dt(Date update_dt) {
		this.update_dt = update_dt;
	}
	 @Column(name="url")
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
    
     
  
}

