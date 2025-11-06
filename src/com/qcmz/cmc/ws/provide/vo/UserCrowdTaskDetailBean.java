package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

public class UserCrowdTaskDetailBean extends CrowdTaskBaseDetailBean{
	/**
	 * 用户编号
	 */
	private Long uid;
	/**
	 * 用户申请时间
	 */
	private Long applyTime;
	/**
	 * 取消时间
	 */
	private Long cancelTime;
	/**
	 * 取消原因
	 */
	private String cancelReason;
	/**
	 * 用户完成时间
	 */
	private Long utFinishTime;
	/**
	 * 用户已完成题目数
	 */
	private Integer finishSubjectNum = 0;
	/**
	 * 用户任务奖励
	 */
	private Double utReward;
	/**
	 * 用户任务到账时间
	 */
	private Long utRewardTime;
	
	/**
	 * 报名审核人用户编号
	 */
	private Long verifyUid;
	/**
	 * 审核状态
	 */
	private Integer verifyStatus;
	/**
	 * 是否通过审核
	 */
	private Integer verifyPass;
	/**
	 * 审核不通过原因
	 */
	private String refuseReason;
	
	/**
	 * 前置任务题目
	 */
	private List<UserCrowdSubjectBean> preUserSubjects = new ArrayList<UserCrowdSubjectBean>();

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Long getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Long applyTime) {
		this.applyTime = applyTime;
	}

	public Long getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Long cancelTime) {
		this.cancelTime = cancelTime;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
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

	public Long getVerifyUid() {
		return verifyUid;
	}

	public void setVerifyUid(Long verifyUid) {
		this.verifyUid = verifyUid;
	}

	public Integer getVerifyStatus() {
		return verifyStatus;
	}

	public void setVerifyStatus(Integer verifyStatus) {
		this.verifyStatus = verifyStatus;
	}

	public Integer getVerifyPass() {
		return verifyPass;
	}

	public void setVerifyPass(Integer verifyPass) {
		this.verifyPass = verifyPass;
	}

	public String getRefuseReason() {
		return refuseReason;
	}

	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}

	public Integer getFinishSubjectNum() {
		return finishSubjectNum;
	}

	public void setFinishSubjectNum(Integer finishSubjectNum) {
		this.finishSubjectNum = finishSubjectNum;
	}

	public List<UserCrowdSubjectBean> getPreUserSubjects() {
		return preUserSubjects;
	}

	public void setPreUserSubjects(List<UserCrowdSubjectBean> preUserSubjects) {
		this.preUserSubjects = preUserSubjects;
	}
}
