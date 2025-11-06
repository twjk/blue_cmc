package com.qcmz.cmc.vo;

public class RewardOfflineBean {
	/**
	 * 用户编号
	 */
	private Long userId;
	/**
	 * 业务类型
	 */
	private String serviceType;
	/**
	 * 手机号码
	 */
	private String mobile;
	/**
	 * 账单类型
	 */
	private Integer billType;
	/**
	 * 充值金额
	 */
	private Double amount;
	/**
	 * 相关订单号
	 */
	private String orderId;
	/**
	 * 描述
	 */
	private String description;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getBillType() {
		return billType;
	}
	public void setBillType(Integer billType) {
		this.billType = billType;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
