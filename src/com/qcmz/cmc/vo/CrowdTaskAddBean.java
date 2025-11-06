package com.qcmz.cmc.vo;

import java.util.Date;

public class CrowdTaskAddBean {
	/**
	 * 订单号
	 */
	private String orderId;
	/**
	 * 题库名称
	 */
	private String libName;
	/**
	 * 任务名称
	 */
	private String taskTitle;
	/**
	 * 参与人数
	 */
	private Integer peopleNum;
	/**
	 * 分组代码
	 */
	private String groupCode;
	/**
	 * 开始时间
	 */
	private Date startTime;
	/**
	 * 结束时间
	 */
	private Date endTime;
	/**
	 * 排序，正序
	 */
	private Integer sortIndex = 999;
	/**
	 * 题目分类
	 */
	private Integer subjectCat;
	/**
	 * 题目奖励金额
	 */
	private Double subjectReward;
	/**
	 * 来源
	 */
	private Integer subjectSource;
	/**
	 * 题目或口令
	 */
	private String subjectContent;
	/**
	 * 链接
	 */
	private String subjectUrl;
	/**
	 * 图片链接
	 */
	private String subjectPicUrl;
	/**
	 * 限制用户编号，用,分隔
	 */
	private String limitUserId;
	/**
	 * 限制业务类型
	 */
	private String[] limitServiceType;
	/**
	 * 任务描述
	 */
	private String description;
	/**
	 * 奖励活动编号
	 */
	private Long actId;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getLibName() {
		return libName;
	}
	public void setLibName(String libName) {
		this.libName = libName;
	}
	public String getTaskTitle() {
		return taskTitle;
	}
	public void setTaskTitle(String taskTitle) {
		this.taskTitle = taskTitle;
	}
	public Integer getPeopleNum() {
		return peopleNum;
	}
	public void setPeopleNum(Integer peopleNum) {
		this.peopleNum = peopleNum;
	}
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Integer getSortIndex() {
		return sortIndex;
	}
	public void setSortIndex(Integer sortIndex) {
		this.sortIndex = sortIndex;
	}
	public Integer getSubjectCat() {
		return subjectCat;
	}
	public void setSubjectCat(Integer subjectCat) {
		this.subjectCat = subjectCat;
	}
	public Double getSubjectReward() {
		return subjectReward;
	}
	public void setSubjectReward(Double subjectReward) {
		this.subjectReward = subjectReward;
	}
	public Integer getSubjectSource() {
		return subjectSource;
	}
	public void setSubjectSource(Integer subjectSource) {
		this.subjectSource = subjectSource;
	}
	public String getSubjectContent() {
		return subjectContent;
	}
	public void setSubjectContent(String subjectContent) {
		this.subjectContent = subjectContent;
	}
	public String getSubjectUrl() {
		return subjectUrl;
	}
	public void setSubjectUrl(String subjectUrl) {
		this.subjectUrl = subjectUrl;
	}
	public String getSubjectPicUrl() {
		return subjectPicUrl;
	}
	public void setSubjectPicUrl(String subjectPicUrl) {
		this.subjectPicUrl = subjectPicUrl;
	}
	public String getLimitUserId() {
		return limitUserId;
	}
	public void setLimitUserId(String limitUserId) {
		this.limitUserId = limitUserId;
	}
	public String[] getLimitServiceType() {
		return limitServiceType;
	}
	public void setLimitServiceType(String[] limitServiceType) {
		this.limitServiceType = limitServiceType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getActId() {
		return actId;
	}
	public void setActId(Long actId) {
		this.actId = actId;
	}
}
