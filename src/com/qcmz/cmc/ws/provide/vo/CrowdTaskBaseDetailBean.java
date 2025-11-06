package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

import com.qcmz.framework.ws.vo.UserBean;

public class CrowdTaskBaseDetailBean {
	/**
	 * 任务编号
	 */
	private Long taskId;
	/**
	 * 任务名称
	 */
	private String title;
	/**
	 * 详情图片
	 */
	private String pic2;
	/**
	 * 任务奖励
	 */
	private Double taskReward;
	/**
	 * 任务奖励结算方式
	 */
	private Integer taskRewardSettle;
	/**
	 * 奖励类型
	 */
	private Integer rewardType;
	/**
	 * 奖励单位名称
	 */
	private String unitName;
	/**
	 * 题目数
	 */
	private Integer subjectNum;
	/**
	 * 题目分配方式
	 */
	private Integer subjectAssign;
	/**
	 * 开始时间
	 */
	private Long startTime;
	/**
	 * 结束时间
	 */
	private Long endTime;
	/**
	 * 可参与人数
	 */
	private Integer maxNum;
	/**
	 * 报名人数
	 */
	private Integer applyNum;
	/**
	 * 完成人数
	 */
	private Integer finishNum;
	/**
	 * 任务详述
	 */
	private String description;
	/**
	 * 是否自动审核
	 */
	private boolean autoVerify;
	/**
	 * 是否自动审校
	 */
	private boolean autoQc;
	/**
	 * 最高邀请奖励金额，元
	 */
	private Double maxInviteRewardAmount = 0.0;
	/**
	 * 用户任务编号
	 */
	private String utId;
	/**
	 * 用户任务状态
	 */
	private Integer utStatus;
	/**
	 * 已完成用户列表
	 */
	private List<UserBean> finishUsers = new ArrayList<UserBean>();
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
	public String getPic2() {
		return pic2;
	}
	public void setPic2(String pic2) {
		this.pic2 = pic2;
	}
	public Double getTaskReward() {
		return taskReward;
	}
	public void setTaskReward(Double taskReward) {
		this.taskReward = taskReward;
	}
	public Integer getTaskRewardSettle() {
		return taskRewardSettle;
	}
	public void setTaskRewardSettle(Integer taskRewardSettle) {
		this.taskRewardSettle = taskRewardSettle;
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
	public Integer getSubjectNum() {
		return subjectNum;
	}
	public void setSubjectNum(Integer subjectNum) {
		this.subjectNum = subjectNum;
	}
	public Integer getSubjectAssign() {
		return subjectAssign;
	}
	public void setSubjectAssign(Integer subjectAssign) {
		this.subjectAssign = subjectAssign;
	}
	public Long getStartTime() {
		return startTime;
	}
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	public Integer getMaxNum() {
		return maxNum;
	}
	public void setMaxNum(Integer maxNum) {
		this.maxNum = maxNum;
	}
	public Integer getApplyNum() {
		return applyNum;
	}
	public void setApplyNum(Integer applyNum) {
		this.applyNum = applyNum;
	}
	public Integer getFinishNum() {
		return finishNum;
	}
	public void setFinishNum(Integer finishNum) {
		this.finishNum = finishNum;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isAutoVerify() {
		return autoVerify;
	}
	public void setAutoVerify(boolean autoVerify) {
		this.autoVerify = autoVerify;
	}
	public boolean isAutoQc() {
		return autoQc;
	}
	public void setAutoQc(boolean autoQc) {
		this.autoQc = autoQc;
	}
	public Double getMaxInviteRewardAmount() {
		return maxInviteRewardAmount;
	}
	public void setMaxInviteRewardAmount(Double maxInviteRewardAmount) {
		this.maxInviteRewardAmount = maxInviteRewardAmount;
	}
	public String getUtId() {
		return utId;
	}
	public void setUtId(String utId) {
		this.utId = utId;
	}
	public Integer getUtStatus() {
		return utStatus;
	}
	public void setUtStatus(Integer utStatus) {
		this.utStatus = utStatus;
	}
	public List<UserBean> getFinishUsers() {
		return finishUsers;
	}
	public void setFinishUsers(List<UserBean> finishUsers) {
		this.finishUsers = finishUsers;
	}
}
