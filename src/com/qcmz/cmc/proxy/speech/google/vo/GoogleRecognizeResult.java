package com.qcmz.cmc.proxy.speech.google.vo;

import java.util.List;

public class GoogleRecognizeResult {
	private List<GoogleRecognizeAlternative> alternatives;
	private String languageCode;

	public List<GoogleRecognizeAlternative> getAlternatives() {
		return alternatives;
	}

	public void setAlternatives(List<GoogleRecognizeAlternative> alternatives) {
		this.alternatives = alternatives;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}
	
}
