package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class LangReq extends Request {
	/**
	 * 语言类型，01通用02语音03翻译
	 */
	private String langType;

	public String getLangType() {
		return langType;
	}

	public void setLangType(String langType) {
		this.langType = langType;
	}
	
}
