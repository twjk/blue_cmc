package com.qcmz.cmc.vo;

import com.qcmz.cmc.proxy.ai.baidu.BaiduAiUtil;

public class LocalTaskBean {
	private String companyName;
	private String title;
	private String reward;
	private String minReward;
	private String maxReward;
	private String peopleNum;
	private String exp;
	private String minExp;
	private String edu;
	private String job;
	private String jobRequire;
	private String contact;
	private String address;
	private String workTime;
	private String rewardType;
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = BaiduAiUtil.processData(companyName);
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title =  BaiduAiUtil.processData(title);
	}
	public String getReward() {
		return reward;
	}
	public void setReward(String reward) {
		this.reward =  BaiduAiUtil.processData(reward);
	}
	public String getExp() {
		return exp;
	}
	public void setExp(String exp) {
		this.exp =  BaiduAiUtil.processData(exp);
	}
	public String getEdu() {
		return edu;
	}
	public void setEdu(String edu) {
		this.edu =  BaiduAiUtil.processData(edu);
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
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact =  BaiduAiUtil.processData(contact);
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = BaiduAiUtil.processData(address);
	}
	public String getWorkTime() {
		return workTime;
	}
	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}
	public String getPeopleNum() {
		return peopleNum;
	}
	public void setPeopleNum(String peopleNum) {
		this.peopleNum =  BaiduAiUtil.processData(peopleNum);
	}
	public String getMinReward() {
		return minReward;
	}
	public void setMinReward(String minReward) {
		this.minReward =  BaiduAiUtil.processData(minReward);
	}
	public String getMaxReward() {
		return maxReward;
	}
	public void setMaxReward(String maxReward) {
		this.maxReward =  BaiduAiUtil.processData(maxReward);
	}
	public String getMinExp() {
		return minExp;
	}
	public void setMinExp(String minExp) {
		this.minExp =  BaiduAiUtil.processData(minExp);
	}
	public String getRewardType() {
		return rewardType;
	}
	public void setRewardType(String rewardType) {
		this.rewardType = rewardType;
	}
}
