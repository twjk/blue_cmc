package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

public class LocalTaskBean {
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
	 * 公司名称
	 */
	private String companyName;
	/**
	 * 公司Logo
	 */
	private String companyLogo;
	/**
	 * 公司简介
	 */
	private String companyIntroduce;
	/**
	 * 联系方式
	 */
	private String contact;
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
	 * 人数
	 */
	private String peopleNum;
	/**
	 * 职责描述
	 */
	private String job;
	/**
	 * 任职要求
	 */
	private String jobRequire;
	/**
	 * 任务来源（1公司2小红书）
	 */
	@Deprecated
	private Long taskSource = 1L;
	/**
	 * 来源名称
	 */
	private String sourceName;
	/**
	 * 链接
	 */
	private String link;
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
	/**
	 * 图片
	 */
	private List<LocalTaskPicBean> pics = new ArrayList<LocalTaskPicBean>();

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
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getPeopleNum() {
		return peopleNum;
	}
	public void setPeopleNum(String peopleNum) {
		this.peopleNum = peopleNum;
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
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyLogo() {
		return companyLogo;
	}
	public void setCompanyLogo(String companyLogo) {
		this.companyLogo = companyLogo;
	}
	public String getCompanyIntroduce() {
		return companyIntroduce;
	}
	public void setCompanyIntroduce(String companyIntroduce) {
		this.companyIntroduce = companyIntroduce;
	}
	public Long getTaskSource() {
		return taskSource;
	}
	public void setTaskSource(Long taskSource) {
		this.taskSource = taskSource;
	}
	
	public String getSourceName() {
		return sourceName;
	}
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
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
	public List<LocalTaskPicBean> getPics() {
		return pics;
	}
	public void setPics(List<LocalTaskPicBean> pics) {
		this.pics = pics;
	}
}
