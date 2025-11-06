package com.qcmz.cmc.entity;

import java.util.Date;

/**
 * CmcRpBill entity. @author MyEclipse Persistence Tools
 */

public class CmcRpBill implements java.io.Serializable {

	// Fields

	private Long billid;
	private Long userid;
	private Integer billtype;
	private Double amount;
	private Long cashid;
	private String packetid;
	private Date createtime;

	// Constructors

	/** default constructor */
	public CmcRpBill() {
	}

	// Property accessors

	public Long getBillid() {
		return this.billid;
	}

	public void setBillid(Long billid) {
		this.billid = billid;
	}

	public Long getUserid() {
		return this.userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
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

	public Long getCashid() {
		return this.cashid;
	}

	public void setCashid(Long cashid) {
		this.cashid = cashid;
	}

	public String getPacketid() {
		return this.packetid;
	}

	public void setPacketid(String packetid) {
		this.packetid = packetid;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

}