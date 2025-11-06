package com.qcmz.cmc.proxy.pay.wxpay;

public class WxpayAccountBean {
	/**
	 * 应用ID
	 */
	private String appid;
	/**
	 * 支付商户号
	 */
	private String mchid;
	/**
	 * 商户API密钥（账户设置-->API安全-->密钥设置）
	 */
	private String apikey;
	/**
	 * 商户支付证书路径
	 */
	private String cert;
	/**
	 * 通知地址
	 */
	private String notifyUrl;
	/**
	 * 交易类型
	 */
	private String tradeType;
	
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getMchid() {
		return mchid;
	}
	public void setMchid(String mchid) {
		this.mchid = mchid;
	}
	public String getApikey() {
		return apikey;
	}
	public void setApikey(String apikey) {
		this.apikey = apikey;
	}
	public String getCert() {
		return cert;
	}
	public void setCert(String cert) {
		this.cert = cert;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
}
