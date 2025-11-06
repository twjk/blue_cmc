package com.qcmz.cmc.proxy.speech.azure.vo;

public class AzureRecongnizeResp {
	private String RecognitionStatus;
	private String DisplayText;
	public String getRecognitionStatus() {
		return RecognitionStatus;
	}
	public void setRecognitionStatus(String recognitionStatus) {
		RecognitionStatus = recognitionStatus;
	}
	public String getDisplayText() {
		return DisplayText;
	}
	public void setDisplayText(String displayText) {
		DisplayText = displayText;
	}
}
