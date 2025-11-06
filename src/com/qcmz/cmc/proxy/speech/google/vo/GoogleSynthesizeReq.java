package com.qcmz.cmc.proxy.speech.google.vo;

public class GoogleSynthesizeReq {
	private GoogleSynthesizeInput input = new GoogleSynthesizeInput();
	private GoogleSynthesizeParams voice = new GoogleSynthesizeParams();
	private GoogleSynthesizeConfig audioConfig = new GoogleSynthesizeConfig();
	public GoogleSynthesizeInput getInput() {
		return input;
	}
	public void setInput(GoogleSynthesizeInput input) {
		this.input = input;
	}
	public GoogleSynthesizeParams getVoice() {
		return voice;
	}
	public void setVoice(GoogleSynthesizeParams voice) {
		this.voice = voice;
	}
	public GoogleSynthesizeConfig getAudioConfig() {
		return audioConfig;
	}
	public void setAudioConfig(GoogleSynthesizeConfig audioConfig) {
		this.audioConfig = audioConfig;
	}
}
