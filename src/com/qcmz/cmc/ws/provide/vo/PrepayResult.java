package com.qcmz.cmc.ws.provide.vo;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class PrepayResult {
	/**
	 * 微信支付信息
	 */
	private PrepayWxpayBean wxpay;
	/**
	 * 支付宝信息
	 */
	private PrepayAlipayBean alipay;
	/**
	 * 苹果支付信息
	 */
	private PrepayApplepayBean applepay;
	
	public PrepayWxpayBean getWxpay() {
		return wxpay;
	}
	public void setWxpay(PrepayWxpayBean wxpay) {
		this.wxpay = wxpay;
	}
	public PrepayAlipayBean getAlipay() {
		return alipay;
	}
	public void setAlipay(PrepayAlipayBean alipay) {
		this.alipay = alipay;
	}
	public PrepayApplepayBean getApplepay() {
		return applepay;
	}
	public void setApplepay(PrepayApplepayBean applepay) {
		this.applepay = applepay;
	}
}
