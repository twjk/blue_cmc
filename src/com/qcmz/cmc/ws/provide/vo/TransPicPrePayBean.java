package com.qcmz.cmc.ws.provide.vo;


/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class TransPicPrePayBean {
	/**
	 * 用户编号
	 */
	private Long uid;
	/**
	 * 图片编号
	 */
	private String picId;
	/**
	 * 金额，元
	 */
	private Double amount;
	/**
	 * 交易途径
	 */
	private String tradeWay;
	
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public String getPicId() {
		return picId;
	}
	public void setPicId(String picId) {
		this.picId = picId;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getTradeWay() {
		return tradeWay;
	}
	public void setTradeWay(String tradeWay) {
		this.tradeWay = tradeWay;
	}
}
