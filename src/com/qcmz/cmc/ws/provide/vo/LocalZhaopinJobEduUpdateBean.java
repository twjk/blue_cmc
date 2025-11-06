package com.qcmz.cmc.ws.provide.vo;

public class LocalZhaopinJobEduUpdateBean {
	/**
	 * 用户编号
	 */
	private Long uid;
	/**
	 * 任务编号
	 */
	private Long taskId;
	/**
	 * 学历要求描述
	 */
	private String edu;
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
	public String getEdu() {
		return edu;
	}
	public void setEdu(String edu) {
		this.edu = edu;
	}
}
