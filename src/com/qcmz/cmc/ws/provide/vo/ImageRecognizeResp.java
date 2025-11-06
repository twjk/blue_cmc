package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Response;

public class ImageRecognizeResp extends Response{
	private ImageRecognitionImageBean result;

	public ImageRecognitionImageBean getResult() {
		return result;
	}

	public void setResult(ImageRecognitionImageBean result) {
		this.result = result;
	}
}
