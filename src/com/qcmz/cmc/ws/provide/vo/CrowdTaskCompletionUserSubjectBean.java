package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

public class CrowdTaskCompletionUserSubjectBean {
	/**
	 * 题目编号
	 */
	private Long subjectId;
	/**
	 * 用户答案列表
	 */
	private List<CrowdTaskCompletionUserAnswerBean> answers = new ArrayList<CrowdTaskCompletionUserAnswerBean>();

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	public List<CrowdTaskCompletionUserAnswerBean> getAnswers() {
		return answers;
	}

	public void setAnswers(List<CrowdTaskCompletionUserAnswerBean> answers) {
		this.answers = answers;
	}
}
