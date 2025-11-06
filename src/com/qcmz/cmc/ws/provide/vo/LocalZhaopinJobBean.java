package com.qcmz.cmc.ws.provide.vo;

public class LocalZhaopinJobBean {
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
	 * 招聘人数
	 */
	private String peopleNum;
	/**
	 * 公司信息
	 */
	private LocalCompanyBasicBean company;
	/**
	 * 城市名称
	 */
	private String cityName;
	/**
	 * 工作地址
	 */
	private String address;
	/**
	 * 岗位职责
	 */
	private String job;
	/**
	 * 任职要求
	 */
	private String jobRequire;
	/**
	 * 岗位状态
	 */
	private Integer status;
	/**
	 * 岗位审核结果
	 */
	private String verifyResult;

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
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getJobRequire() {
		return jobRequire;
	}
	public void setJobRequire(String jobRequire) {
		this.jobRequire = jobRequire;
	}
	public LocalCompanyBasicBean getCompany() {
		return company;
	}
	public void setCompany(LocalCompanyBasicBean company) {
		this.company = company;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getVerifyResult() {
		return verifyResult;
	}
	public void setVerifyResult(String verifyResult) {
		this.verifyResult = verifyResult;
	}
	public String getPeopleNum() {
		return peopleNum;
	}
	public void setPeopleNum(String peopleNum) {
		this.peopleNum = peopleNum;
	}
}
