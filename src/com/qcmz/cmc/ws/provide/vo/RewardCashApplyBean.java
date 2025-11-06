package com.qcmz.cmc.ws.provide.vo;

public class RewardCashApplyBean {
	/**
	 * 用户编号
	 */
	private Long uid;
	/**
	 * 提现金额
	 */
	private Double amount;
	/**
	 * 帐户类型
	 */
	private String accountType;
	/**
	 * 帐户
	 */
	private String account;
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
}
