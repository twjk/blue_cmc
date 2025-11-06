package com.qcmz.cmc.ws.provide.vo;

public class UserCrowdTaskDealCountBean {
	/**
	 * 待处理数
	 */
	private long waitingCount;
	/**
	 * 本人处理中数
	 */
	private long userProcessingCount;
	public Long getWaitingCount() {
		return waitingCount;
	}
	public void setWaitingCount(Long waitingCount) {
		this.waitingCount = waitingCount;
	}
	public Long getUserProcessingCount() {
		return userProcessingCount;
	}
	public void setUserProcessingCount(Long userProcessingCount) {
		this.userProcessingCount = userProcessingCount;
	}
}
