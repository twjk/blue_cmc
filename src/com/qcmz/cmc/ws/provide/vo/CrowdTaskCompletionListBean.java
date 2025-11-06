package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

import com.qcmz.framework.ws.vo.UserBean;

public class CrowdTaskCompletionListBean {
	/**
	 * 完成时间
	 */
	private Long finishTime;
	/**
	 * 用户信息
	 */
	private UserBean user;
	/**
	 * 用户题目列表
	 */
	private List<CrowdTaskCompletionUserSubjectBean> subjects = new ArrayList<CrowdTaskCompletionUserSubjectBean>();
	public Long getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Long finishTime) {
		this.finishTime = finishTime;
	}
	public UserBean getUser() {
		return user;
	}
	public void setUser(UserBean user) {
		this.user = user;
	}
	public List<CrowdTaskCompletionUserSubjectBean> getSubjects() {
		return subjects;
	}
	public void setSubjects(List<CrowdTaskCompletionUserSubjectBean> subjects) {
		this.subjects = subjects;
	}
}
