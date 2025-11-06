package com.qcmz.cmc.ws.provide.vo;

public class UserCrowdAnswerQcBean {
	/**
	 * 用户答案编号
	 */
	private Long uaId;
	/**
	 * 答案得分
	 */
	private Integer answerScore;
	public Long getUaId() {
		return uaId;
	}
	public void setUaId(Long uaId) {
		this.uaId = uaId;
	}
	public Integer getAnswerScore() {
		return answerScore;
	}
	public void setAnswerScore(Integer answerScore) {
		this.answerScore = answerScore;
	}
}
