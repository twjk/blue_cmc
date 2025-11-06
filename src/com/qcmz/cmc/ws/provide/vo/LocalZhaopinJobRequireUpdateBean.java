package com.qcmz.cmc.ws.provide.vo;

public class LocalZhaopinJobRequireUpdateBean {
	/**
	 * 用户编号
	 */
	private Long uid;
	/**
	 * 任务编号
	 */
	private Long taskId;
	/**
	 * 任职要求
	 */
	private String jobRequire;
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
	public String getJobRequire() {
		return jobRequire;
	}
	public void setJobRequire(String jobRequire) {
		this.jobRequire = jobRequire;
	}
}
