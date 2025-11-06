package com.qcmz.cmc.vo;

public class UserCrowdRewardGrantResult {
	/**
	 * 奖励分类
	 */
	private Integer rewardCat;
	/**
	 * 用户任务编号
	 */
	private String utId;
	/**
	 * 用户编号
	 */
	private Long userId;
	/**
	 * 发放奖励
	 */
	private Double reward;
	/**
	 * 奖励单位名称
	 */
	private String unitName;
	/**
	 * 任务名称
	 */
	private String title;
	public Integer getRewardCat() {
		return rewardCat;
	}
	public void setRewardCat(Integer rewardCat) {
		this.rewardCat = rewardCat;
	}
	public String getUtId() {
		return utId;
	}
	public void setUtId(String utId) {
		this.utId = utId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Double getReward() {
		return reward;
	}
	public void setReward(Double reward) {
		this.reward = reward;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
