package com.qcmz.cmc.ws.provide.vo;

public class LocalZhaopinJobListBean {
	/**
	 * 任务编号
	 */
	private Long taskId;
	/**
	 * 任务名称
	 */
	private String title;
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
	 * 工作经验
	 */
	private String exp;
	/**
	 * 学历要求
	 */
	private String edu;
	/**
	 * 城市名称
	 */
	private String cityName;
	/**
	 * 工作地址
	 */
	private String address;
	/**
	 * 状态
	 */
	private Integer status;
	
	public LocalZhaopinJobListBean() {
		super();
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
