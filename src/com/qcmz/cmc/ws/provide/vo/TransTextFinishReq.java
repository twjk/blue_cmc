package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

/**
 * 类说明：完成翻译
 * 修改历史：
 */
public class TransTextFinishReq extends Request {
	private TransTextDealBean bean = new TransTextDealBean();

	public TransTextDealBean getBean() {
		return bean;
	}

	public void setBean(TransTextDealBean bean) {
		this.bean = bean;
	}
}
