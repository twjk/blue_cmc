package com.qcmz.cmc.ws.provide.vo;

public class RedPacketCreateBean {
	/**
	 * 用户编号
	 */
	private Long uid;
	/**
	 * 语言代码
	 */
	private String langCode;
	/**
	 * 红包暗语
	 */
	private String title;
	/**
	 * 中文红包暗语
	 */
	private String titleCn;
	/**
	 * 红包数量
	 */
	private int packetNum;
	/**
	 * 红包金额
	 */
	private double packetAmount;
	/**
	 * 服务费
	 */
	private double serviceAmount;
	/**
	 * 应付金额
	 */
	private double payableAmount;
	/**
	 * 余额抵扣金额
	 */
	private double deductAmount;
	/**
	 * 交易途径
	 */
	private String tradeWay;
	/**
	 * 小程序支付用户标识
	 */
	private String openid;
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public String getLangCode() {
		return langCode;
	}
	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitleCn() {
		return titleCn;
	}
	public void setTitleCn(String titleCn) {
		this.titleCn = titleCn;
	}
	public int getPacketNum() {
		return packetNum;
	}
	public void setPacketNum(int packetNum) {
		this.packetNum = packetNum;
	}
	public double getPacketAmount() {
		return packetAmount;
	}
	public void setPacketAmount(double packetAmount) {
		this.packetAmount = packetAmount;
	}
	public double getServiceAmount() {
		return serviceAmount;
	}
	public void setServiceAmount(double serviceAmount) {
		this.serviceAmount = serviceAmount;
	}
	public double getPayableAmount() {
		return payableAmount;
	}
	public void setPayableAmount(double payableAmount) {
		this.payableAmount = payableAmount;
	}
	public double getDeductAmount() {
		return deductAmount;
	}
	public void setDeductAmount(double deductAmount) {
		this.deductAmount = deductAmount;
	}
	public String getTradeWay() {
		return tradeWay;
	}
	public void setTradeWay(String tradeWay) {
		this.tradeWay = tradeWay;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}	
}
