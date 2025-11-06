package com.qcmz.cmc.ws.provide.vo;

public class LocalZhaopinJobDutyUpdateBean {
	/**
	 * 用户编号
	 */
	private Long uid;
	/**
	 * 任务编号
	 */
	private Long taskId;
	/**
	 * 职责描述
	 */
	private String job;
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
}
