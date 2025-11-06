package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Response;

public class SpeechAsrResp extends Response {
	private SpeechAsrResult result = new SpeechAsrResult();

	public SpeechAsrResult getResult() {
		return result;
	}

	public void setResult(SpeechAsrResult result) {
		this.result = result;
	}
	
}
