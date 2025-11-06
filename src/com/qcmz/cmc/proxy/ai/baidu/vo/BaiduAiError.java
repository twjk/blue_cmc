package com.qcmz.cmc.proxy.ai.baidu.vo;

public class BaiduAiError {
	private String code;
	private String message;
	private String type;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "BaiduAiError [code=" + code + ", message=" + message + "]";
	}
}
