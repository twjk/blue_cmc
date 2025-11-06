package com.qcmz.cmc.proxy.speech.google.vo;

import java.util.List;

public class GoogleRecognizeResp {
	private List<GoogleRecognizeResult> results;

	public List<GoogleRecognizeResult> getResults() {
		return results;
	}

	public void setResults(List<GoogleRecognizeResult> results) {
		this.results = results;
	}
}
