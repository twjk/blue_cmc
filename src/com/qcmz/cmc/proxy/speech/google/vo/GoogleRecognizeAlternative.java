package com.qcmz.cmc.proxy.speech.google.vo;

public class GoogleRecognizeAlternative {
	/**
	 * 结果
	 */
	private String transcript;
	/**
	 * 分值,0.9520726
	 */
	private Double confidence;
	public String getTranscript() {
		return transcript;
	}
	public void setTranscript(String transcript) {
		this.transcript = transcript;
	}
	public Double getConfidence() {
		return confidence;
	}
	public void setConfidence(Double confidence) {
		this.confidence = confidence;
	}
}
