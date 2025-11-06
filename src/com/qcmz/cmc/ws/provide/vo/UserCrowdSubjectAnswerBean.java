package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户答题信息
 */
public class UserCrowdSubjectAnswerBean {
	/**
	 * 用户编号
	 */
	private Long uid;
	/**
	 * 用户任务编号
	 */
	private String utId;
	/**
	 * 用户答案列表
	 */
	private List<UserCrowdAnswerBean> answers = new ArrayList<UserCrowdAnswerBean>();
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
	public List<UserCrowdAnswerBean> getAnswers() {
		return answers;
	}
	public void setAnswers(List<UserCrowdAnswerBean> answers) {
		this.answers = answers;
	}
}
