package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class TransWordQueryReq extends Request {
	private TransWordQueryBean bean = new TransWordQueryBean();

	public TransWordQueryBean getBean() {
		return bean;
	}

	public void setBean(TransWordQueryBean bean) {
		this.bean = bean;
	}
}
