package com.qcmz.cmc.entity;

import java.util.Date;

/**
 * CmcRewardPart entity. @author MyEclipse Persistence Tools
 */

public class CmcRewardPart implements java.io.Serializable {

	// Fields

	private Long partid;
	private Long actid;
	private Long userid;
	private Long inviteeid;
	private String subservicetype;
	private String serviceid;
	private Date parttime;

	// Constructors

	/** default constructor */
	public CmcRewardPart() {
	}

	// Property accessors

	public Long getPartid() {
		return this.partid;
	}

	public void setPartid(Long partid) {
		this.partid = partid;
	}

	public Long getActid() {
		return this.actid;
	}

	public void setActid(Long actid) {
		this.actid = actid;
	}

	public Long getUserid() {
		return this.userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Long getInviteeid() {
		return this.inviteeid;
	}

	public void setInviteeid(Long inviteeid) {
		this.inviteeid = inviteeid;
	}

	public String getSubservicetype() {
		return this.subservicetype;
	}

	public void setSubservicetype(String subservicetype) {
		this.subservicetype = subservicetype;
	}

	public String getServiceid() {
		return this.serviceid;
	}

	public void setServiceid(String serviceid) {
		this.serviceid = serviceid;
	}

	public Date getParttime() {
		return parttime;
	}

	public void setParttime(Date parttime) {
		this.parttime = parttime;
	}
}