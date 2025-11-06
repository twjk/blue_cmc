package com.qcmz.cmc.ws.provide.vo;

public class LocalTaskListBean {
	/**
	 * 任务编号
	 */
	private Long taskId;
	/**
	 * 任务名称
	 */
	private String title;
	/**
	 * 公司名称
	 */
	private String companyName;
	/**
	 * 工作时间类型
	 */
	private Integer workTimeType;
	/**
	 * 报酬类型
	 */
	private Integer rewardType;
	/**
	 * 报酬
	 */
	private String reward;
	/**
	 * 城市名称
	 */
	private String cityName;
	/**
	 * 工作地址
	 */
	private String address;
	/**
	 * 工作经验
	 */
	private String exp;
	/**
	 * 学历要求
	 */
	private String edu;
	/**
	 * 排序标识
	 */
	private String sortId;
	/**
	 * 有效期开始时间
	 */
	@Deprecated
	private Long startTime;
	/**
	 * 发布时间
	 */
	private Long publishTime;
	/**
	 * 最高邀请奖励金额，元
	 */
	private Double maxInviteRewardAmount = 0.0;
	
	public LocalTaskListBean() {
		super();
	}
	public String getSortId() {
		return sortId;
	}
	public void setSortId(String sortId) {
		this.sortId = sortId;
	}
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Integer getWorkTimeType() {
		return workTimeType;
	}
	public void setWorkTimeType(Integer workTimeType) {
		this.workTimeType = workTimeType;
	}
	public Integer getRewardType() {
		return rewardType;
	}
	public void setRewardType(Integer rewardType) {
		this.rewardType = rewardType;
	}
	public String getReward() {
		return reward;
	}
	public void setReward(String reward) {
		this.reward = reward;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getExp() {
		return exp;
	}
	public void setExp(String exp) {
		this.exp = exp;
	}
	public String getEdu() {
		return edu;
	}
	public void setEdu(String edu) {
		this.edu = edu;
	}
	public Long getStartTime() {
		return startTime;
	}
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}
	public Long getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Long publishTime) {
		this.publishTime = publishTime;
	}
	public Double getMaxInviteRewardAmount() {
		return maxInviteRewardAmount;
	}
	public void setMaxInviteRewardAmount(Double maxInviteRewardAmount) {
		this.maxInviteRewardAmount = maxInviteRewardAmount;
	}
}
