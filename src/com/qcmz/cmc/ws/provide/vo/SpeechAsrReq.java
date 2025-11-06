package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class SpeechAsrReq extends Request {
	private SpeechAsrBean bean = new SpeechAsrBean();

	public SpeechAsrBean getBean() {
		return bean;
	}

	public void setBean(SpeechAsrBean bean) {
		this.bean = bean;
	}
}
