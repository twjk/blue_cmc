package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class TransTextPriceQueryReq extends Request {
	private TransTextPriceQueryBean bean = new TransTextPriceQueryBean();

	public TransTextPriceQueryBean getBean() {
		return bean;
	}

	public void setBean(TransTextPriceQueryBean bean) {
		this.bean = bean;
	}
}
