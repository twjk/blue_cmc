package com.qcmz.cmc.entity;

import java.sql.Timestamp;

/**
 * CmcTmVersion entity. @author MyEclipse Persistence Tools
 */

public class CmcTmVersion implements java.io.Serializable {

	// Fields

	private Long versionid;
	private String versioncode;
	private String url;
	private String description;
	private Timestamp createtime;

	// Constructors

	/** default constructor */
	public CmcTmVersion() {
	}

	/** minimal constructor */
	public CmcTmVersion(String versioncode, String url, Timestamp createtime) {
		this.versioncode = versioncode;
		this.url = url;
		this.createtime = createtime;
	}

	/** full constructor */
	public CmcTmVersion(String versioncode, String url, String description,
			Timestamp createtime) {
		this.versioncode = versioncode;
		this.url = url;
		this.description = description;
		this.createtime = createtime;
	}

	// Property accessors

	public Long getVersionid() {
		return this.versionid;
	}

	public void setVersionid(Long versionid) {
		this.versionid = versionid;
	}

	public String getVersioncode() {
		return this.versioncode;
	}

	public void setVersioncode(String versioncode) {
		this.versioncode = versioncode;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

}