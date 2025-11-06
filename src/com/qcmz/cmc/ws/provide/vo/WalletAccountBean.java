package com.qcmz.cmc.ws.provide.vo;

public class WalletAccountBean {
	/**
	 * 用户编号
	 */
	private Long uid;
	/**
	 * 可用余额
	 */
	private double balance;
	
	public WalletAccountBean() {
		super();
	}
	
	public WalletAccountBean(Long uid) {
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
}
