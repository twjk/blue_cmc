package com.qcmz.cmc.entity;

import java.util.Date;

import com.qcmz.umc.ws.provide.vo.UserSimpleBean;

/**
 * CmcRewardAccount entity. @author MyEclipse Persistence Tools
 */

public class CmcRewardAccount implements java.io.Serializable {

	// Fields

	private Long userid;
	private String servicetype;
	private Long inviterid;
	private Double balance = 0.0;
	private Double rewardtotal = 0.0;
	private String remark;
	private Date createtime;
	private Integer status;
	
	private UserSimpleBean user;

	// Constructors

	/** default constructor */
	public CmcRewardAccount() {
	}

	// Property accessors

	public Long getUserid() {
		return this.userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getServicetype() {
		return servicetype;
	}

	public void setServicetype(String servicetype) {
		this.servicetype = servicetype;
	}

	public Long getInviterid() {
		return inviterid;
	}

	public void setInviterid(Long inviterid) {
		this.inviterid = inviterid;
	}

	public Double getBalance() {
		return this.balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Double getRewardtotal() {
		return this.rewardtotal;
	}

	public void setRewardtotal(Double rewardtotal) {
		this.rewardtotal = rewardtotal;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public UserSimpleBean getUser() {
		return user;
	}

	public void setUser(UserSimpleBean user) {
		this.user = user;
	}
	
}