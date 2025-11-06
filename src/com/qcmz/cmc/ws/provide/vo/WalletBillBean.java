package com.qcmz.cmc.ws.provide.vo;

import java.util.Date;

public class WalletBillBean {
	/**
	 * 账单编号
	 */
	private Long billId;
	/**
	 * 用户编号
	 */
	private Long uid;
	/**
	 * 收支类型
	 */
	private Integer incexp;
	/**
	 * 账单类型
	 */
	private Integer billType;	
	/**
	 * 金额
	 */
	private double amount;
	/**
	 * 账单描述
	 */
	private String billDesc;
	/**
	 * 账单时间
	 */
	private Long billDate;
	
	public WalletBillBean() {
		super();
	}

	public WalletBillBean(Long billid, Long uid, Integer incexp, Integer billType,
			double amount, String billDesc, Date billDate) {
		super();
		this.billId = billid;
		this.uid = uid;
		this.incexp = incexp;
		this.billType = billType;
		this.amount = amount;
		this.billDesc = billDesc;
		this.billDate = billDate.getTime();
	}

	public Long getBillId() {
		return billId;
	}

	public void setBillId(Long billId) {
		this.billId = billId;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Integer getIncexp() {
		return incexp;
	}

	public void setIncexp(Integer incexp) {
		this.incexp = incexp;
	}

	public Integer getBillType() {
		return billType;
	}

	public void setBillType(Integer billType) {
		this.billType = billType;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getBillDesc() {
		return billDesc;
	}

	public void setBillDesc(String billDesc) {
		this.billDesc = billDesc;
	}

	public Long getBillDate() {
		return billDate;
	}

	public void setBillDate(Long billDate) {
		this.billDate = billDate;
	}	
}
