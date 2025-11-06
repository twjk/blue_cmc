package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class SpeechTtsReq extends Request {
	private SpeechTtsBean bean = new SpeechTtsBean();

	public SpeechTtsBean getBean() {
		return bean;
	}

	public void setBean(SpeechTtsBean bean) {
		this.bean = bean;
	}
}
