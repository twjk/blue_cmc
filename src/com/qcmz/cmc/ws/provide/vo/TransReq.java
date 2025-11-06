package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

/**
 * 类说明：翻译请求
 * 修改历史：
 */
public class TransReq extends Request {
	private TransBean bean = new TransBean();

	public TransBean getBean() {
		return bean;
	}

	public void setBean(TransBean bean) {
		this.bean = bean;
	}
	
}
