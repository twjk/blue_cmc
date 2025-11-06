package com.qcmz.cmc.ws.provide.vo;

public class UserCrowdTaskListBean {
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
	 * 任务奖励单位名称
	 */
	private String unitName;
	/**
	 * 任务开始时间
	 */
	private Long taskStartTime;
	/**
	 * 任务结束时间
	 */
	private Long taskEndTime;
	/**
	 * 任务题目数
	 */
	private Integer subjectNum;
	/**
	 * 题目分配方式
	 */
	private Integer subjectAssign;
	
	/**
	 * 用户申请时间
	 */
	private Long applyTime;
	/**
	 * 用户开始时间
	 */
	private Long utStartTime;
	/**
	 * 用户完成时间
	 */
	private Long utFinishTime;
	/**
	 * 用户已完成题目数
	 */
	private Integer finishSubjectNum;
	/**
	 * 用户任务奖励
	 */
	private Double utReward;
	/**
	 * 奖励到账时间
	 */
	private Long utRewardTime;
	/**
	 * 拒绝申请原因
	 */
	private String refuseReason;
	/**
	 * 用户任务状态
	 */
	private Integer utStatus;
	
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
	public Double getTaskReward() {
		return taskReward;
	}
	public void setTaskReward(Double taskReward) {
		this.taskReward = taskReward;
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
	public Long getTaskStartTime() {
		return taskStartTime;
	}
	public void setTaskStartTime(Long taskStartTime) {
		this.taskStartTime = taskStartTime;
	}
	public Long getTaskEndTime() {
		return taskEndTime;
	}
	public void setTaskEndTime(Long taskEndTime) {
		this.taskEndTime = taskEndTime;
	}
	public Integer getSubjectNum() {
		return subjectNum;
	}
	public void setSubjectNum(Integer subjectNum) {
		this.subjectNum = subjectNum;
	}
	public Integer getFinishSubjectNum() {
		return finishSubjectNum;
	}
	public void setFinishSubjectNum(Integer finishSubjectNum) {
		this.finishSubjectNum = finishSubjectNum;
	}
	public Long getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(Long applyTime) {
		this.applyTime = applyTime;
	}
	public Long getUtStartTime() {
		return utStartTime;
	}
	public void setUtStartTime(Long utStartTime) {
		this.utStartTime = utStartTime;
	}
	public Long getUtFinishTime() {
		return utFinishTime;
	}
	public void setUtFinishTime(Long utFinishTime) {
		this.utFinishTime = utFinishTime;
	}
	public Double getUtReward() {
		return utReward;
	}
	public void setUtReward(Double utReward) {
		this.utReward = utReward;
	}
	public Long getUtRewardTime() {
		return utRewardTime;
	}
	public void setUtRewardTime(Long utRewardTime) {
		this.utRewardTime = utRewardTime;
	}
	public String getRefuseReason() {
		return refuseReason;
	}
	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}
	public Integer getUtStatus() {
		return utStatus;
	}
	public void setUtStatus(Integer utStatus) {
		this.utStatus = utStatus;
	}
	public Integer getTaskRewardSettle() {
		return taskRewardSettle;
	}
	public void setTaskRewardSettle(Integer taskRewardSettle) {
		this.taskRewardSettle = taskRewardSettle;
	}
	public Integer getSubjectAssign() {
		return subjectAssign;
	}
	public void setSubjectAssign(Integer subjectAssign) {
		this.subjectAssign = subjectAssign;
	}
}
