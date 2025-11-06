package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

/**
 * 类说明：开始翻译
 * 修改历史：
 */
public class TransPicFinishReq extends Request {
	private TransPicDealBean bean = new TransPicDealBean();

	public TransPicDealBean getBean() {
		return bean;
	}

	public void setBean(TransPicDealBean bean) {
		this.bean = bean;
	}
}
