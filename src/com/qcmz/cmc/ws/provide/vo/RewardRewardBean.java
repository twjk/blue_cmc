package com.qcmz.cmc.ws.provide.vo;

public class RewardRewardBean {
	/**
	 * 用户编号
	 */
	private Long uid;
	/**
	 * 子业务类型，31签到 32答题
	 */
	private String subServiceType;
	/**
	 * 业务编号
	 */
	private String serviceId;
	/**
	 * 奖励金额
	 */
	private Double rewardAmount;
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
	public String getSubServiceType() {
		return subServiceType;
	}
	public void setSubServiceType(String subServiceType) {
		this.subServiceType = subServiceType;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public Double getRewardAmount() {
		return rewardAmount;
	}
	public void setRewardAmount(Double rewardAmount) {
		this.rewardAmount = rewardAmount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
