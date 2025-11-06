package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

import com.qcmz.framework.ws.vo.Response;

public class ImageRecognitionCatQueryResp extends Response {
	private List<ImageRecognitionCatBean> result = new ArrayList<ImageRecognitionCatBean>();

	public List<ImageRecognitionCatBean> getResult() {
		return result;
	}

	public void setResult(List<ImageRecognitionCatBean> result) {
		this.result = result;
	}
}
