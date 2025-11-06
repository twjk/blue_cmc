package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class CrowdTaskGetReq extends Request {
	/**
	 * 任务编号
	 */
	private Long taskId;
	/**
	 * 用户编号
	 */
	private Long uid;

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}
}
