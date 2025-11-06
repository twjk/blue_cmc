package com.qcmz.cmc.entity;

import java.util.Date;


/**
 * CmcApikey entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CmcApikey implements java.io.Serializable {

	// Fields

	private Long keyid;
	private String apitype;
	private String apikey;
	private Date regdate;
	private Date restartdate;
	private Integer status;

	// Constructors

	/** default constructor */
	public CmcApikey() {
	}

	// Property accessors

	public CmcApikey(Long keyid) {
		super();
		this.keyid = keyid;
	}



	public Long getKeyid() {
		return this.keyid;
	}

	public void setKeyid(Long keyid) {
		this.keyid = keyid;
	}

	public String getApitype() {
		return this.apitype;
	}

	public void setApitype(String apitype) {
		this.apitype = apitype;
	}

	public String getApikey() {
		return this.apikey;
	}

	public void setApikey(String apikey) {
		this.apikey = apikey;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	public Date getRestartdate() {
		return restartdate;
	}

	public void setRestartdate(Date restartdate) {
		this.restartdate = restartdate;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}