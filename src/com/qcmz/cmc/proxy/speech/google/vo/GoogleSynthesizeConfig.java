package com.qcmz.cmc.proxy.speech.google.vo;

public class GoogleSynthesizeConfig {
	/**
	 * LINEAR16、MP3、OGG_OPUS
	 * Required 
	 */
	private String audioEncoding;
	/**
	 * 语速，值范围[0.25, 4.0]，1.0为正常语速
	 */
	private double speakingRate = 1.0;
	/**
	 * 音高，值范围[-20.0, 20.0]，由轻声到尖锐，0为正常
	 */
	private double pitch = 0.0;
	/**
	 * 音量增强，db， 值范围[-96.0, 16.0]
	 */
	private double volumeGainDb = 0.0;
	/**
	 * 采样率
	 */
	private double sampleRateHertz = 16000.0;
	public String getAudioEncoding() {
		return audioEncoding;
	}
	public void setAudioEncoding(String audioEncoding) {
		this.audioEncoding = audioEncoding;
	}
	public double getSpeakingRate() {
		return speakingRate;
	}
	public void setSpeakingRate(double speakingRate) {
		this.speakingRate = speakingRate;
	}
	public double getPitch() {
		return pitch;
	}
	public void setPitch(double pitch) {
		this.pitch = pitch;
	}
	public double getVolumeGainDb() {
		return volumeGainDb;
	}
	public void setVolumeGainDb(double volumeGainDb) {
		this.volumeGainDb = volumeGainDb;
	}
	public double getSampleRateHertz() {
		return sampleRateHertz;
	}
	public void setSampleRateHertz(double sampleRateHertz) {
		this.sampleRateHertz = sampleRateHertz;
	}
}
