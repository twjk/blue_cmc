package com.qcmz.cmc.vo;

import java.util.Date;

import com.qcmz.cmc.entity.CmcRewardActivity;

public class CrowdTaskEditBean {
	/**
	 * 任务编号
	 */
	private Long taskId;
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
	 * 可参与人数
	 */
	private Integer peopleNum;
	/**
	 * 报名人数
	 */
	private Integer applyNum;
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
	 * 限制用户编号
	 */
	private String limitUserId;
	/**
	 * 限制业务类型，多个用;分隔，用于展示
	 */
	private String limitServiceTypeStr;
	/**
	 * 限制业务类型，用于接受前端checkbox数据
	 */
	private String[] limitServiceType;
	/**
	 * 任务描述
	 */
	private String description;
	/**
	 * 排序，正序
	 */
	private Integer sortIndex = 999;
	/**
	 * 奖励活动编号
	 */
	private Long actId;
	/**
	 * 奖励活动
	 */
	private CmcRewardActivity activity;

	/**
	 * 题目编号
	 */
	private Long subjectId;
	/**
	 * 题目奖励金额
	 */
	private Double subjectReward;
	/**
	 * 来源(videosource)
	 */
	private Integer subjectSource;
	/**
	 * 题目或口令
	 */
	private String subjectContent;
	/**
	 * 链接(video)
	 */
	private String subjectUrl;
	/**
	 * 图片链接
	 */
	private String subjectPicUrl;
	
	
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
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
	public Integer getApplyNum() {
		return applyNum;
	}
	public void setApplyNum(Integer applyNum) {
		this.applyNum = applyNum;
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
	public Long getActId() {
		return actId;
	}
	public void setActId(Long actId) {
		this.actId = actId;
	}
	public CmcRewardActivity getActivity() {
		return activity;
	}
	public void setActivity(CmcRewardActivity activity) {
		this.activity = activity;
	}
	public Long getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
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
	public String getLimitServiceTypeStr() {
		return limitServiceTypeStr;
	}
	public void setLimitServiceTypeStr(String limitServiceTypeStr) {
		this.limitServiceTypeStr = limitServiceTypeStr;
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
}
