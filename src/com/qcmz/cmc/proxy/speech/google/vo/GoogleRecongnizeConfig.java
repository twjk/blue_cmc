package com.qcmz.cmc.proxy.speech.google.vo;

public class GoogleRecongnizeConfig {
	private String encoding;
	private int sampleRateHertz;
	private String languageCode;
	private boolean enableAutomaticPunctuation = true;
	public String getEncoding() {
		return encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	public int getSampleRateHertz() {
		return sampleRateHertz;
	}
	public void setSampleRateHertz(int sampleRateHertz) {
		this.sampleRateHertz = sampleRateHertz;
	}
	public String getLanguageCode() {
		return languageCode;
	}
	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}
	public boolean isEnableAutomaticPunctuation() {
		return enableAutomaticPunctuation;
	}
	public void setEnableAutomaticPunctuation(boolean enableAutomaticPunctuation) {
		this.enableAutomaticPunctuation = enableAutomaticPunctuation;
	}
}
