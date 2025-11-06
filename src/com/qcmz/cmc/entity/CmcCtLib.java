package com.qcmz.cmc.entity;

/**
 * CmcCtLib entity. @author MyEclipse Persistence Tools
 */

public class CmcCtLib implements java.io.Serializable {

	// Fields

	private Long libid;
	private String libname;
	private Long assignedsubjectid;
	private Integer minidlesubject = 0;
	private String remark;

	// Constructors

	/** default constructor */
	public CmcCtLib() {
	}

	/** minimal constructor */
	public CmcCtLib(String libname) {
		this.libname = libname;
	}

	/** full constructor */
	public CmcCtLib(String libname, Long assignedsubjectid, String remark) {
		this.libname = libname;
		this.assignedsubjectid = assignedsubjectid;
		this.remark = remark;
	}

	// Property accessors

	public Long getLibid() {
		return this.libid;
	}

	public void setLibid(Long libid) {
		this.libid = libid;
	}

	public String getLibname() {
		return this.libname;
	}

	public void setLibname(String libname) {
		this.libname = libname;
	}

	public Long getAssignedsubjectid() {
		return this.assignedsubjectid;
	}

	public void setAssignedsubjectid(Long assignedsubjectid) {
		this.assignedsubjectid = assignedsubjectid;
	}

	public Integer getMinidlesubject() {
		return minidlesubject;
	}

	public void setMinidlesubject(Integer minidlesubject) {
		this.minidlesubject = minidlesubject;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}