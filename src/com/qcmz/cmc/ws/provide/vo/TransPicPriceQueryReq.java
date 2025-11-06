package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class TransPicPriceQueryReq extends Request {
	private TransPicPriceQueryBean bean = new TransPicPriceQueryBean();

	public TransPicPriceQueryBean getBean() {
		return bean;
	}

	public void setBean(TransPicPriceQueryBean bean) {
		this.bean = bean;
	}
}
