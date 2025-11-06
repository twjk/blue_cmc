package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class TransPicPrePayReq extends Request {
	private TransPicPrePayBean bean = new TransPicPrePayBean();

	public TransPicPrePayBean getBean() {
		return bean;
	}

	public void setBean(TransPicPrePayBean bean) {
		this.bean = bean;
	}
}
