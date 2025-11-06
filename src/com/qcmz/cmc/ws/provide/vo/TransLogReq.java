package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class TransLogReq extends Request {
	private TransLogAddBean bean = new TransLogAddBean();

	public TransLogAddBean getBean() {
		return bean;
	}

	public void setBean(TransLogAddBean bean) {
		this.bean = bean;
	}
	
}
