package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

public class UserCrowdSubjectQcBean {
	/**
	 * 用户任务编号
	 */
	private String utId;
	/**
	 * 审校人用户编号
	 */
	private Long qcUid;
	/**
	 * 题目编号
	 */
	private Long subjectId;
	/**
	 * 题目得分
	 */
	private Integer subjectScore;
	/**
	 * 答案审校
	 */
	private List<UserCrowdAnswerQcBean> answerQcs = new ArrayList<UserCrowdAnswerQcBean>();
	
	public Long getQcUid() {
		return qcUid;
	}
	public void setQcUid(Long qcUid) {
		this.qcUid = qcUid;
	}
	public String getUtId() {
		return utId;
	}
	public void setUtId(String utId) {
		this.utId = utId;
	}
	public Long getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}
	public Integer getSubjectScore() {
		return subjectScore;
	}
	public void setSubjectScore(Integer subjectScore) {
		this.subjectScore = subjectScore;
	}
	public List<UserCrowdAnswerQcBean> getAnswerQcs() {
		return answerQcs;
	}
	public void setAnswerQcs(List<UserCrowdAnswerQcBean> answerQcs) {
		this.answerQcs = answerQcs;
	}
}
