package com.qcmz.cmc.ws.provide.vo;

import java.io.File;

public class UserCrowdTaskUploadBean {
	/**
	 * 用户编号
	 */
	private Long uid;
	/**
	 * 用户任务编号
	 */
	private String utId;
	/**
	 * 任务编号
	 */
	private Long taskId;
	/**
	 * 题目编号
	 */
	private Long subjectId;
	/**
	 * 语音文件
	 */
	private File file;
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public String getUtId() {
		return utId;
	}
	public void setUtId(String utId) {
		this.utId = utId;
	}
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public Long getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
}
