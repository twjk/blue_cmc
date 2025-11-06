package com.qcmz.cmc.ws.provide.vo;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class PrepayWxpayBean {
	/**
	 * 应用ID
	 */
	private String appid;
	/**
	 * 商户号
	 */
	private String partnerid;	
	/**
	 * 预支付交易会话标识
	 */
	private String prepayid;
	/**
	 * 扩展字段,暂填写固定值Sign=WXPay
	 */
	private String pkg;
	/**
	 * 随机字符串
	 */
	private String noncestr;
	/**
	 * 时间戳，秒
	 */
	private String timestamp;
	/**
	 * 签名
	 */	
	private String sign;
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getPartnerid() {
		return partnerid;
	}
	public void setPartnerid(String partnerid) {
		this.partnerid = partnerid;
	}
	public String getPrepayid() {
		return prepayid;
	}
	public void setPrepayid(String prepayid) {
		this.prepayid = prepayid;
	}
	public String getPkg() {
		return pkg;
	}
	public void setPkg(String pkg) {
		this.pkg = pkg;
	}
	public String getNoncestr() {
		return noncestr;
	}
	public void setNoncestr(String noncestr) {
		this.noncestr = noncestr;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
}
