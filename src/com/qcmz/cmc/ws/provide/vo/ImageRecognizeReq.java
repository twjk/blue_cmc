package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class ImageRecognizeReq extends Request {
	private ImageRecognizeBean bean = new ImageRecognizeBean();

	public ImageRecognizeBean getBean() {
		return bean;
	}

	public void setBean(ImageRecognizeBean bean) {
		this.bean = bean;
	}
}
