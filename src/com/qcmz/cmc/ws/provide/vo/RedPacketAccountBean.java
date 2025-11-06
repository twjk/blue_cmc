package com.qcmz.cmc.ws.provide.vo;

public class RedPacketAccountBean {
	/**
	 * 用户编号
	 */
	private Long uid;
	/**
	 * 可用余额
	 */
	private Double balance = 0.0;
	/**
	 * 已提现金额
	 */
	private Double cashAmount = 0.0;
	/**
	 * 领红包数量
	 */
	private Integer receiveNum = 0;
	/**
	 * 领红包总额
	 */
	private Double receiveAmount = 0.0;
	/**
	 * 发红包数量
	 */
	private Integer sendNum = 0;
	/**
	 * 发红包总额
	 */
	private Double sendAmount = 0.0;
	
	public RedPacketAccountBean() {
		super();
	}
	
	public RedPacketAccountBean(Long uid) {
		super();
		this.uid = uid;
	}

	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public Double getCashAmount() {
		return cashAmount;
	}
	public void setCashAmount(Double cashAmount) {
		this.cashAmount = cashAmount;
	}
	public Integer getReceiveNum() {
		return receiveNum;
	}
	public void setReceiveNum(Integer receiveNum) {
		this.receiveNum = receiveNum;
	}
	public Double getReceiveAmount() {
		return receiveAmount;
	}
	public void setReceiveAmount(Double receiveAmount) {
		this.receiveAmount = receiveAmount;
	}
	public Integer getSendNum() {
		return sendNum;
	}
	public void setSendNum(Integer sendNum) {
		this.sendNum = sendNum;
	}
	public Double getSendAmount() {
		return sendAmount;
	}
	public void setSendAmount(Double sendAmount) {
		this.sendAmount = sendAmount;
	}
}
