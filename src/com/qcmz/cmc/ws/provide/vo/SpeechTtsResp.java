package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Response;

public class SpeechTtsResp extends Response {
	private SpeechTtsResult result;

	public SpeechTtsResult getResult() {
		return result;
	}

	public void setResult(SpeechTtsResult result) {
		this.result = result;
	}
}
