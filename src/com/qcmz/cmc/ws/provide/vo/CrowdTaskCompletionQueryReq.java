package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class CrowdTaskCompletionQueryReq extends Request {
	/**
	 * 任务编号
	 */
	private Long taskId;

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
}
