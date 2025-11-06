package com.qcmz.cmc.entity;

import java.util.Date;

/**
 * CmcSLock entity. @author MyEclipse Persistence Tools
 */

public class CmcSLock implements java.io.Serializable {

	// Fields

	private Long lockid;
	private String locktype;
	private String lockkey;
	private String createip;
	private Date createtime;
	private Date expiretime;

	// Constructors

	/** default constructor */
	public CmcSLock() {
	}

	// Property accessors

	public Long getLockid() {
		return this.lockid;
	}

	public void setLockid(Long lockid) {
		this.lockid = lockid;
	}

	public String getLocktype() {
		return this.locktype;
	}

	public void setLocktype(String locktype) {
		this.locktype = locktype;
	}

	public String getLockkey() {
		return this.lockkey;
	}

	public void setLockkey(String lockkey) {
		this.lockkey = lockkey;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
	public String getCreateip() {
		return createip;
	}

	public void setCreateip(String createip) {
		this.createip = createip;
	}
	
	public Date getExpiretime() {
		return this.expiretime;
	}

	public void setExpiretime(Date expiretime) {
		this.expiretime = expiretime;
	}

}