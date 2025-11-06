package com.qcmz.cmc.entity;

import java.util.Date;

/**
 * CmcCtDevice entity. @author MyEclipse Persistence Tools
 */

public class CmcCtDevice implements java.io.Serializable {

	// Fields

	private Long deviceid;
	private String uuid;
	private Long userid;
	private Date createtime;

	// Constructors

	/** default constructor */
	public CmcCtDevice() {
	}

	/** full constructor */
	public CmcCtDevice(String uuid, Long userid, Date createtime) {
		this.uuid = uuid;
		this.userid = userid;
		this.createtime = createtime;
	}

	// Property accessors

	public Long getDeviceid() {
		return this.deviceid;
	}

	public void setDeviceid(Long deviceid) {
		this.deviceid = deviceid;
	}

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Long getUserid() {
		return this.userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

}