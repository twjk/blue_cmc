package com.qcmz.cmc.vo;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class TransferBean {
	/**
	 * 订单号
	 */
	private String orderId;
	/**
	 * 订单描述
	 */
	private String orderDesc;
	/**
	 * 金额，元
	 */
	private Double amount;
	/**
	 * 小程序支付用户标识
	 */
	private String openid;
	/**
	 * 平台
	 */
	private String platform;
	/**
	 * 业务类型
	 */
	private String serviceType;
	/**
	 * 子业务类型
	 */
	private String subServiceType;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderDesc() {
		return orderDesc;
	}
	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getSubServiceType() {
		return subServiceType;
	}
	public void setSubServiceType(String subServiceType) {
		this.subServiceType = subServiceType;
	}
}
