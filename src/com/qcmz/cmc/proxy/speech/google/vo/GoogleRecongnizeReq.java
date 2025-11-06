package com.qcmz.cmc.proxy.speech.google.vo;

public class GoogleRecongnizeReq {
	private GoogleRecongnizeConfig config = new GoogleRecongnizeConfig();
	private GoogleRecognizeAudio audio = new GoogleRecognizeAudio();
	public GoogleRecongnizeConfig getConfig() {
		return config;
	}
	public void setConfig(GoogleRecongnizeConfig config) {
		this.config = config;
	}
	public GoogleRecognizeAudio getAudio() {
		return audio;
	}
	public void setAudio(GoogleRecognizeAudio audio) {
		this.audio = audio;
	}
}
