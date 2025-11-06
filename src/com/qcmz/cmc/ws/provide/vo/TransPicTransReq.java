package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class TransPicTransReq extends Request {
	private TransPicDealBean bean = new TransPicDealBean();

	public TransPicDealBean getBean() {
		return bean;
	}

	public void setBean(TransPicDealBean bean) {
		this.bean = bean;
	}
}
