package com.qcmz.cmc.entity;

import java.util.Date;

/**
 * CmcLtSpdtask entity. @author MyEclipse Persistence Tools
 */

public class CmcLtSpdtask implements java.io.Serializable {

	// Fields

	private Long spdtaskid;
	private Long sourceid;
	private CmcLtSource cmcLtSource;
	private Date lasttime;
	private String lastidentify;
	private Integer status;

	// Constructors

	/** default constructor */
	public CmcLtSpdtask() {
	}

	// Property accessors

	public Long getSpdtaskid() {
		return this.spdtaskid;
	}

	public void setSpdtaskid(Long spdtaskid) {
		this.spdtaskid = spdtaskid;
	}

	public Long getSourceid() {
		return sourceid;
	}

	public void setSourceid(Long sourceid) {
		this.sourceid = sourceid;
	}

	public CmcLtSource getCmcLtSource() {
		return cmcLtSource;
	}

	public void setCmcLtSource(CmcLtSource cmcLtSource) {
		this.cmcLtSource = cmcLtSource;
	}

	public Date getLasttime() {
		return this.lasttime;
	}

	public void setLasttime(Date lasttime) {
		this.lasttime = lasttime;
	}

	public String getLastidentify() {
		return this.lastidentify;
	}

	public void setLastidentify(String lastidentify) {
		this.lastidentify = lastidentify;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}