package com.qcmz.cmc.proxy.spider.weixin.vo;

import org.openqa.selenium.WebDriver;

public class WeixinLoginResult {
	private WebDriver driver;
	private String token;
	public WebDriver getDriver() {
		return driver;
	}
	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
}
