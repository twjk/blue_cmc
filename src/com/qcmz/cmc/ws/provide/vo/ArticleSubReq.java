package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class ArticleSubReq extends Request {
	private ArticleSubBean bean = new ArticleSubBean();

	public ArticleSubBean getBean() {
		return bean;
	}

	public void setBean(ArticleSubBean bean) {
		this.bean = bean;
	}
}
