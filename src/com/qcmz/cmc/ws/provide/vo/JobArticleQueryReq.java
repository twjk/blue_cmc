package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class JobArticleQueryReq extends Request {
	private JobArticleQueryBean bean = new JobArticleQueryBean();

	public JobArticleQueryBean getBean() {
		return bean;
	}

	public void setBean(JobArticleQueryBean bean) {
		this.bean = bean;
	}
}
