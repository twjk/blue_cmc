package com.qcmz.cmc.entity;

import java.util.Date;

/**
 * CmcRewardBill entity. @author MyEclipse Persistence Tools
 */

public class CmcRewardBill implements java.io.Serializable {

	// Fields

	private Long billid;
	private Long userid;
	private CmcRewardAccount cmcRewardAccount;
	private Integer incexp;
	private Integer billtype;
	private Double amount;
	private String billdesc;
	private String subservicetype;
	private String orderid;
	private Date createtime;
	private Date createdate;

	// Constructors

	/** default constructor */
	public CmcRewardBill() {
	}
	
	// Property accessors

	public Long getBillid() {
		return this.billid;
	}

	public void setBillid(Long billid) {
		this.billid = billid;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public CmcRewardAccount getCmcRewardAccount() {
		return this.cmcRewardAccount;
	}

	public void setCmcRewardAccount(CmcRewardAccount cmcRewardAccount) {
		this.cmcRewardAccount = cmcRewardAccount;
	}

	public Integer getIncexp() {
		return this.incexp;
	}

	public void setIncexp(Integer incexp) {
		this.incexp = incexp;
	}

	public Integer getBilltype() {
		return this.billtype;
	}

	public void setBilltype(Integer billtype) {
		this.billtype = billtype;
	}

	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getBilldesc() {
		return this.billdesc;
	}

	public void setBilldesc(String billdesc) {
		this.billdesc = billdesc;
	}

	public String getSubservicetype() {
		return this.subservicetype;
	}

	public void setSubservicetype(String subservicetype) {
		this.subservicetype = subservicetype;
	}

	public String getOrderid() {
		return this.orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

}