package com.yuchengtech.mobile.console.entity.ver; 

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
@Table(name = "TBL_WEBRESOURCE")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)  //打包ver.jar是去掉注释
public class WebResource implements java.io.Serializable  {

	private static final long serialVersionUID = -6562666061237441914L;
	 
     private Long id;
    
     private Long vid;
     private String version;
     private String forceUpdate;
     private String des;
     private String hash;
     private String status;
     private String vsize;
     private String fullUrl;
     private Date updateDt;
     private Long baseline;
     private byte[] resourceData             ;

     @Column(name="resourcedata")
 	public byte[] getResourceData() {
		return resourceData;
	}
	public void setResourceData(byte[] resourceData) {
		this.resourceData = resourceData;
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
    
    @Column(name="DES")
    public String getDes(){
       return des;
    }
    public void setDes(String des){
         this.des = des;
    }
    
    @Column(name="FORCE_UPDATE")
    public String getForceUpdate(){
       return forceUpdate;
    }
    public void setForceUpdate(String forceUpdate){
         this.forceUpdate = forceUpdate;
    }
    
    @Column(name="HASH")
    public String getHash(){
       return hash;
    }
    public void setHash(String hash){
         this.hash = hash;
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
	@Column(name="baseline")
	public Long getBaseline() {
		return baseline;
	}
	public void setBaseline(Long baseline) {
		this.baseline = baseline;
	}
	@Column(name="status")
	 public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}


	@Column(name="vsize")
	public String getVsize() {
		return vsize;
	}



	public void setVsize(String vsize) {
		this.vsize = vsize;
	}


	@Column(name="fullurl")
	public String getFullUrl() {
		return fullUrl;
	}



	public void setFullUrl(String fullUrl) {
		this.fullUrl = fullUrl;
	}



	@Column(name="UPDATE_DT")
	public Date getUpdateDt() {
		return updateDt;
	}
	public void setUpdateDt(Date updateDt) {
		this.updateDt = updateDt;
	} 
}

