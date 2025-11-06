package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

import com.qcmz.framework.ws.vo.Response;

public class ImageRecognitionQueryResp extends Response {
	private List<ImageRecognitionImageBean> result = new ArrayList<ImageRecognitionImageBean>();

	public List<ImageRecognitionImageBean> getResult() {
		return result;
	}

	public void setResult(List<ImageRecognitionImageBean> result) {
		this.result = result;
	}
}
