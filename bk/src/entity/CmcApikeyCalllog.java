package com.qcmz.cmc.entity;

import java.util.Date;

/**
 * CmcApikeyCalllog entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CmcApikeyCalllog implements java.io.Serializable {

	// Fields

	private Long logid;
	private Long keyid;
	private CmcApikey cmcApikey;
	private Date calltime;

	// Constructors

	/** default constructor */
	public CmcApikeyCalllog() {
	}

	
	// Property accessors

	public Long getKeyid() {
		return keyid;
	}

	public void setKeyid(Long keyid) {
		this.keyid = keyid;
	}



	public Long getLogid() {
		return this.logid;
	}

	public void setLogid(Long logid) {
		this.logid = logid;
	}

	public CmcApikey getCmcApikey() {
		return this.cmcApikey;
	}

	public void setCmcApikey(CmcApikey cmcApikey) {
		this.cmcApikey = cmcApikey;
	}

	public Date getCalltime() {
		return this.calltime;
	}

	public void setCalltime(Date calltime) {
		this.calltime = calltime;
	}

}