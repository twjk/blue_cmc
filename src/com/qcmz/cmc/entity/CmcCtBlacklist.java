package com.qcmz.cmc.entity;

import java.util.Date;

/**
 * CmcCtBlacklist entity. @author MyEclipse Persistence Tools
 */

public class CmcCtBlacklist implements java.io.Serializable {

	// Fields

	private Long userid;
	private String reason;
	private Date createtime;

	// Constructors

	/** default constructor */
	public CmcCtBlacklist() {
	}

	
	// Property accessors

	public Long getUserid() {
		return this.userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

}