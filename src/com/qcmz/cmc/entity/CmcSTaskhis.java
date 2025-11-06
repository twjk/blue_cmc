package com.qcmz.cmc.entity;

import java.util.Date;

/**
 * UmcSTaskhis entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CmcSTaskhis implements java.io.Serializable {

	// Fields

	private Long hisid;
	private String tasktype;
	private String identify;
	private String content;
	private Integer status;
	private Date createtime;
	private Integer opertimes;
	private Date opertime;
	private String operresult;

	// Constructors

	/** default constructor */
	public CmcSTaskhis() {
	}

	/** minimal constructor */
	public CmcSTaskhis(String tasktype, Integer status, Date createtime,
			Integer opertimes) {
		this.tasktype = tasktype;
		this.status = status;
		this.createtime = createtime;
		this.opertimes = opertimes;
	}

	/** full constructor */
	public CmcSTaskhis(String tasktype, String identify, String content,
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

	public Long getHisid() {
		return this.hisid;
	}

	public void setHisid(Long hisid) {
		this.hisid = hisid;
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