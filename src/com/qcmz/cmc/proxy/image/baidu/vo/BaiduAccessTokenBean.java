package com.qcmz.cmc.proxy.image.baidu.vo;

public class BaiduAccessTokenBean {
	/**
	 * token
	 */
	private String accessToken;
	/**
	 * 过期时间，毫秒
	 */
	private Long expires;
	
	public boolean isValid(){
		return expires>=System.currentTimeMillis()+3600000;
	}
	
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public Long getExpires() {
		return expires;
	}
	public void setExpires(Long expires) {
		this.expires = expires;
	}
}
