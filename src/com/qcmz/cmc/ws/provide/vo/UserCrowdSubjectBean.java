package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

public class UserCrowdSubjectBean extends CrowdSubjectBaseBean{
	/**
	 * 答题状态
	 */
	private Integer answerStatus;
	/**
	 * 答题时间
	 */
	private Long answerTime;
	/**
	 * 用户题目奖励
	 */
	private Double userSubjectReward;
	/**
	 * 用户题目得分
	 */
	private Integer subjectScore;
	/**
	 * 选项
	 */
	private List<UserCrowdOptionBean> options = new ArrayList<UserCrowdOptionBean>();
	/**
	 * 用户答案
	 */
	private List<UserCrowdAnswerBean> answers = new ArrayList<UserCrowdAnswerBean>();
	
	public Integer getAnswerStatus() {
		return answerStatus;
	}
	public void setAnswerStatus(Integer answerStatus) {
		this.answerStatus = answerStatus;
	}
	public Long getAnswerTime() {
		return answerTime;
	}
	public void setAnswerTime(Long answerTime) {
		this.answerTime = answerTime;
	}
	public Double getUserSubjectReward() {
		return userSubjectReward;
	}
	public void setUserSubjectReward(Double userSubjectReward) {
		this.userSubjectReward = userSubjectReward;
	}
	public Integer getSubjectScore() {
		return subjectScore;
	}
	public void setSubjectScore(Integer subjectScore) {
		this.subjectScore = subjectScore;
	}
	public List<UserCrowdOptionBean> getOptions() {
		return options;
	}
	public void setOptions(List<UserCrowdOptionBean> options) {
		this.options = options;
	}
	public List<UserCrowdAnswerBean> getAnswers() {
		return answers;
	}
	public void setAnswers(List<UserCrowdAnswerBean> answers) {
		this.answers = answers;
	}
}
