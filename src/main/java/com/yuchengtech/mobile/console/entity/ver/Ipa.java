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
 * 注意实体类的private属性和getter、setter方法由系统生成，请不要修改 另外添加以下信息不会被覆盖： 1.import语句 2.
 * 非private属性、static属性 3. 非 getter、setter方法、 static方法 4. 继承接口
 */
@Entity
@Table(name = "TBL_Ipa")
// @Cache(usage = CacheConcurrencyStrategy.READ_WRITE) //打包ver.jar是去掉注释
public class Ipa implements Serializable {

	private static final long serialVersionUID = -7683022150325126491L;
	/** @pdOid 47d325a3-cb0c-45df-86fb-adbe36971591 */
	private Long id;
	/** @pdOid 0ddf09ab-0b58-41e2-9b1f-00e718ac8c3d */
	private String vid;
	/** @pdOid 2ddbe654-3faa-4379-81d4-92065fbaf146 */
	private String version;
	/** @pdOid 97739bfd-fd66-403a-843c-5214781468de */
	private String forceUpdate;
	/** @pdOid 7d108669-c4e1-40b4-8308-b1a9a688aa8d */
	private String status;
	/** @pdOid 1b52a2df-2e2a-4634-8fb4-0506399d5847 */
	private String url;
	/** @pdOid 47a2287b-8d71-4174-9d2a-c2589a1eae5f */
	private String vsize;
	/** @pdOid dd6bbec7-7270-4da0-90e5-7a3b9a23f23e */
	private Date update_dt;
	/** @pdOid 92f9c57f-071a-4904-9261-5c34e19bf039 */
	private String filename;
	/** @pdOid 7901ca69-bbf0-4fd3-b6a5-8605c6006a20 */
	private byte[] resourceData;
	/** @pdOid 74dede37-1409-4647-8fcf-c0df77e04eb0 */
	private String des;
	/** @pdOid 2452e1a4-32c1-4da6-a256-154f1caa478b */
	private String appstoreid;
	/** @pdOid f75a2757-f444-4a1d-aa34-63ac9683abfa */
	private String websiteurl;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false, precision = 19, scale = 0)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "vid")
	public String getVid() {
		return vid;
	}

	public void setVid(String vid) {
		this.vid = vid;
	}

	@Column(name = "version")
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Column(name = "forceupdate")
	public String getForceUpdate() {
		return forceUpdate;
	}

	public void setForceUpdate(String forceUpdate) {
		this.forceUpdate = forceUpdate;
	}

	@Column(name = "status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "url")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "vsize")
	public String getVsize() {
		return vsize;
	}

	public void setVsize(String vsize) {
		this.vsize = vsize;
	}

	@Column(name = "update_dt")
	public Date getUpdate_dt() {
		return update_dt;
	}

	public void setUpdate_dt(Date update_dt) {
		this.update_dt = update_dt;
	}

	@Column(name = "filename")
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Column(name = "resourcedata")
	public byte[] getResourceData() {
		return resourceData;
	}

	public void setResourceData(byte[] resourceData) {
		this.resourceData = resourceData;
	}

	@Column(name = "des")
	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	@Column(name = "appstoreid")
	public String getAppstoreid() {
		return appstoreid;
	}

	public void setAppstoreid(String appstoreid) {
		this.appstoreid = appstoreid;
	}

	public String getWebsiteurl() {
		return websiteurl;
	}

	public void setWebsiteurl(String websiteurl) {
		this.websiteurl = websiteurl;
	}

}
