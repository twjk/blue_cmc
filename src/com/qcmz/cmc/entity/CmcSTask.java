package com.qcmz.cmc.entity;

import java.util.Date;

/**
 * UmcSTask entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CmcSTask implements java.io.Serializable {

	// Fields

	private Long taskid;
	private String tasktype;
	private String identify;
	private String content;
	private Integer status;
	private Date createtime;
	private Integer opertimes = 0;
	private Date opertime;
	private String operresult;

	// Constructors

	/** default constructor */
	public CmcSTask() {
	}

	/** minimal constructor */
	public CmcSTask(String tasktype, String content, Integer status,
			Date createtime, Integer opertimes) {
		this.tasktype = tasktype;
		this.content = content;
		this.status = status;
		this.createtime = createtime;
		this.opertimes = opertimes;
	}

	/** full constructor */
	public CmcSTask(String tasktype, String identify, String content,
			Integer status, Date createtime, Integer opertimes, Date opertime,
			String operresult) {
		this.tasktype = tasktype;
		this.identify = identify;
		this.content = content;
		this.status = status;
		this.createtime = createtime;
		this.opertimes = opertimes;
		this.opertime = opertime;
		this.operresult = operresult;
	}

	// Property accessors

	public Long getTaskid() {
		return this.taskid;
	}

	public void setTaskid(Long taskid) {
		this.taskid = taskid;
	}

	public String getTasktype() {
		return this.tasktype;
	}

	public void setTasktype(String tasktype) {
		this.tasktype = tasktype;
	}

	public String getIdentify() {
		return this.identify;
	}

	public void setIdentify(String identify) {
		this.identify = identify;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Integer getOpertimes() {
		return this.opertimes;
	}

	public void setOpertimes(Integer opertimes) {
		this.opertimes = opertimes;
	}

	public Date getOpertime() {
		return this.opertime;
	}

	public void setOpertime(Date opertime) {
		this.opertime = opertime;
	}

	public String getOperresult() {
		return this.operresult;
	}

	public void setOperresult(String operresult) {
		this.operresult = operresult;
	}

}