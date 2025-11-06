package com.qcmz.cmc.entity;

import java.util.Date;

/**
 * CmcCtQc entity. @author MyEclipse Persistence Tools
 */

public class CmcCtQc implements java.io.Serializable {

	// Fields
	private Long qcid;
	private String utid;
	private CmcCtUsertask cmcCtUsertask;
	private Long taskid;
	private CmcCtTask cmcCtTask;
	private Integer qcstatus;
	private Long qcuserid;
	private Date qcstarttime;
	private Date qcfinishtime;
	private Integer qcfinishnum;
	private Double qcreward;
	private Integer qcrewardstatus;
	private Date qcrewardtime;
	private Date createtime;

	// Constructors

	/** default constructor */
	public CmcCtQc() {
	}

	// Property accessors

	public Long getQcid() {
		return this.qcid;
	}

	public void setQcid(Long qcid) {
		this.qcid = qcid;
	}

	public String getUtid() {
		return utid;
	}

	public void setUtid(String utid) {
		this.utid = utid;
	}

	public CmcCtUsertask getCmcCtUsertask() {
		return this.cmcCtUsertask;
	}

	public void setCmcCtUsertask(CmcCtUsertask cmcCtUsertask) {
		this.cmcCtUsertask = cmcCtUsertask;
	}

	public Long getTaskid() {
		return taskid;
	}

	public void setTaskid(Long taskid) {
		this.taskid = taskid;
	}

	public CmcCtTask getCmcCtTask() {
		return cmcCtTask;
	}

	public void setCmcCtTask(CmcCtTask cmcCtTask) {
		this.cmcCtTask = cmcCtTask;
	}

	public Integer getQcstatus() {
		return this.qcstatus;
	}

	public void setQcstatus(Integer qcstatus) {
		this.qcstatus = qcstatus;
	}

	public Long getQcuserid() {
		return this.qcuserid;
	}

	public void setQcuserid(Long qcuserid) {
		this.qcuserid = qcuserid;
	}

	public Date getQcstarttime() {
		return this.qcstarttime;
	}

	public void setQcstarttime(Date qcstarttime) {
		this.qcstarttime = qcstarttime;
	}

	public Date getQcfinishtime() {
		return this.qcfinishtime;
	}

	public void setQcfinishtime(Date qcfinishtime) {
		this.qcfinishtime = qcfinishtime;
	}

	public Integer getQcfinishnum() {
		return this.qcfinishnum;
	}

	public void setQcfinishnum(Integer qcfinishnum) {
		this.qcfinishnum = qcfinishnum;
	}

	public Double getQcreward() {
		return this.qcreward;
	}

	public void setQcreward(Double qcreward) {
		this.qcreward = qcreward;
	}

	public Integer getQcrewardstatus() {
		return this.qcrewardstatus;
	}

	public void setQcrewardstatus(Integer qcrewardstatus) {
		this.qcrewardstatus = qcrewardstatus;
	}

	public Date getQcrewardtime() {
		return this.qcrewardtime;
	}

	public void setQcrewardtime(Date qcrewardtime) {
		this.qcrewardtime = qcrewardtime;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

}