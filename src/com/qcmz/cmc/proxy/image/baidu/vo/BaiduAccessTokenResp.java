package com.qcmz.cmc.proxy.image.baidu.vo;

public class BaiduAccessTokenResp {
	private String error;
	private String error_description;
	private String access_token;
	/**
	 * Access Token的有效期(秒为单位，有效期30天)；
	 */
	private Long expires_in;
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getError_description() {
		return error_description;
	}
	public void setError_description(String error_description) {
		this.error_description = error_description;
	}
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public Long getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(Long expires_in) {
		this.expires_in = expires_in;
	}
}
