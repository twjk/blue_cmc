package com.qcmz.cmc.ws.provide.vo;

public class RewardBestowBean {
	/**
	 * 用户编号
	 */
	private Long uid;
	/**
	 * 赠送金额
	 */
	private Double amount;
	/**
	 * 描述
	 */
	private String description;
	
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
