package com.qcmz.cmc.ws.provide.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.ws.provide.service.CrowdTaskInterface;
import com.qcmz.cmc.ws.provide.vo.CrowdTaskCompletionQueryReq;
import com.qcmz.cmc.ws.provide.vo.CrowdTaskGetReq;
import com.qcmz.cmc.ws.provide.vo.CrowdTaskQueryReq;
import com.qcmz.framework.action.BaseWSAction;

public class CrowdTaskWSAction extends BaseWSAction {
	@Autowired
	private CrowdTaskInterface crowdTaskInterface;
	
	/**
	 * 用户编号
	 */
	private Long uid;
	/**
	 * 任务编号
	 */
	private Long taskId;
	
	
	/**
	 * 获取任务列表
	 * @return
	 */
	public String findTask(){
		CrowdTaskQueryReq req = new CrowdTaskQueryReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setUid(uid);
		
		jsonObj = crowdTaskInterface.findTask(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 获取任务描述
	 * @return
	 */
	public String getTask(){
		CrowdTaskGetReq req = new CrowdTaskGetReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setTaskId(taskId);
		req.setUid(uid);
		
		jsonObj = crowdTaskInterface.getTask(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 获取任务完成明细
	 * @return
	 */
	public String findCompletion(){
		CrowdTaskCompletionQueryReq req = new CrowdTaskCompletionQueryReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setTaskId(taskId);
		
		jsonObj = crowdTaskInterface.findCompletion(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
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
