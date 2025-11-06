package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

public class CrowdTaskDetailBean extends CrowdTaskBaseDetailBean{	
	/**
	 * 前置任务题目列表
	 */
	private List<CrowdSubjectBean> preSubjects = new ArrayList<CrowdSubjectBean>();

	public List<CrowdSubjectBean> getPreSubjects() {
		return preSubjects;
	}

	public void setPreSubjects(List<CrowdSubjectBean> preSubjects) {
		this.preSubjects = preSubjects;
	}
}
