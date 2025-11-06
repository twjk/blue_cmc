package com.qcmz.cmc.proxy.pay.alipay;

public class AlipayAccountBean {
	/**
	 * 应用ID
	 */
	private String appid;
	/**
	 * 应用私钥
	 */
	private String appPrivateKey;
	/**
	 * 支付宝公钥
	 */
	private String alipayPublicKey;
	/**
	 * 签名算法，RSA2和RSA
	 */
	private String signType;
	/**
	 * 通知地址
	 */
	private String notifyUrl;
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getAppPrivateKey() {
		return appPrivateKey;
	}
	public void setAppPrivateKey(String appPrivateKey) {
		this.appPrivateKey = appPrivateKey;
	}
	public String getAlipayPublicKey() {
		return alipayPublicKey;
	}
	public void setAlipayPublicKey(String alipayPublicKey) {
		this.alipayPublicKey = alipayPublicKey;
	}
	public String getSignType() {
		return signType;
	}
	public void setSignType(String signType) {
		this.signType = signType;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
}
