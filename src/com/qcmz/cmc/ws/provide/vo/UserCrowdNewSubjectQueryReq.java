package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class UserCrowdNewSubjectQueryReq extends Request {
	/**
	 * 用户任务编号
	 */
	private String utId;
	/**
	 * 用户编号
	 */
	private Long uid;
	/**
	 * 最后一条题目编号
	 */
	private Long lastSubjectId;
	public String getUtId() {
		return utId;
	}
	public void setUtId(String utId) {
		this.utId = utId;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public Long getLastSubjectId() {
		return lastSubjectId;
	}
	public void setLastSubjectId(Long lastSubjectId) {
		this.lastSubjectId = lastSubjectId;
	}
}
