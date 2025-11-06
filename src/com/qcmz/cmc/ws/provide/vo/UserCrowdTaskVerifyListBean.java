package com.qcmz.cmc.ws.provide.vo;

import java.util.Date;

public class UserCrowdTaskVerifyListBean {
	/**
	 * 排序标识
	 */
	private String sortId;
	/**
	 * 用户任务编号
	 */
	private String utId;
	/**
	 * 任务名称
	 */
	private String title;
	/**
	 * 任务图片
	 */
	private String pic;
	/**
	 * 报名时间
	 */
	private Long applyTime;
	/**
	 * 国家名称
	 */
	private String countryName;
	/**
	 * 城市名称
	 */
	private String cityName;
	/**
	 * 审核奖励
	 */
	private Double verifyReward;
	/**
	 * 奖励类型
	 */
	private Integer rewardType;
	/**
	 * 任务奖励单位名称
	 */
	private String unitName;
	/**
	 * 审核奖励到账时间
	 */
	private Long verifyRewardTime;
	/**
	 * 审核状态
	 */
	private Integer verifyStatus;
	/**
	 * 审核开始时间
	 */
	private Long verifyStartTime;
	/**
	 * 审核完成时间
	 */
	private Long verifyFinishTime;
	
	public UserCrowdTaskVerifyListBean() {
		super();
	}
	
	public UserCrowdTaskVerifyListBean(String sortId, String utId, String title, String pic, Date applyTime, String countryName, String cityName
			, Double verifyReward, Integer rewardType, String unitName, Date verifyRewardTime
			, Integer verifyStatus, Date verifyStartTime, Date verifyFinishTime) {
		super();
		this.sortId = sortId;
		this.utId = utId;
		this.title = title;
		this.pic = pic;
		this.applyTime = applyTime!=null?applyTime.getTime():null;
		this.countryName = countryName;
		this.cityName = cityName;
		this.verifyReward = verifyReward;
		this.rewardType = rewardType;
		this.unitName = unitName;
		this.verifyRewardTime = verifyRewardTime!=null?verifyRewardTime.getTime():null;
		this.verifyStatus = verifyStatus;
		this.verifyStartTime = verifyStartTime!=null?verifyStartTime.getTime():null;
		this.verifyFinishTime = verifyFinishTime!=null?verifyFinishTime.getTime():null;
	}
	public String getSortId() {
		return sortId;
	}
	public void setSortId(String sortId) {
		this.sortId = sortId;
	}
	public String getUtId() {
		return utId;
	}
	public void setUtId(String utId) {
		this.utId = utId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public Long getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(Long applyTime) {
		this.applyTime = applyTime;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public Double getVerifyReward() {
		return verifyReward;
	}
	public void setVerifyReward(Double verifyReward) {
		this.verifyReward = verifyReward;
	}
	public Integer getRewardType() {
		return rewardType;
	}
	public void setRewardType(Integer rewardType) {
		this.rewardType = rewardType;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public Long getVerifyRewardTime() {
		return verifyRewardTime;
	}
	public void setVerifyRewardTime(Long verifyRewardTime) {
		this.verifyRewardTime = verifyRewardTime;
	}
	public Integer getVerifyStatus() {
		return verifyStatus;
	}
	public void setVerifyStatus(Integer verifyStatus) {
		this.verifyStatus = verifyStatus;
	}
	public Long getVerifyStartTime() {
		return verifyStartTime;
	}
	public void setVerifyStartTime(Long verifyStartTime) {
		this.verifyStartTime = verifyStartTime;
	}
	public Long getVerifyFinishTime() {
		return verifyFinishTime;
	}
	public void setVerifyFinishTime(Long verifyFinishTime) {
		this.verifyFinishTime = verifyFinishTime;
	}
}
