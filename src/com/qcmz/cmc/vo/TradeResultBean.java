package com.qcmz.cmc.vo;

import java.util.Date;

/**
 * 类说明：支付结果信息
 * 修改历史：
 */
public class TradeResultBean {
	/**
	 * 订单号
	 */
	private String orderId;
	/**
	 * 交易途径
	 */
	private String tradeWay;
	/**
	 * 交易类型
	 */
	private String tradeType;
	/**
	 * 交易状态
	 */
	private String tradeStatus;
	/**
	 * 交易结果
	 */
	private String tradeResult;
	/**
	 * 交易时间
	 */
	private Date tradeTime;
	/**
	 * 金额
	 */
	private Double amount;
	/**
	 * 苹果内付费标识
	 */
	private String iapId;
	/**
	 * 外部交易号
	 */
	private String outTradeId;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getTradeWay() {
		return tradeWay;
	}
	public void setTradeWay(String tradeWay) {
		this.tradeWay = tradeWay;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public String getTradeStatus() {
		return tradeStatus;
	}
	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}
	public String getTradeResult() {
		return tradeResult;
	}
	public void setTradeResult(String tradeResult) {
		this.tradeResult = tradeResult;
	}
	public Date getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(Date tradeTime) {
		this.tradeTime = tradeTime;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getOutTradeId() {
		return outTradeId;
	}
	public void setOutTradeId(String outTradeId) {
		this.outTradeId = outTradeId;
	}
	public String getIapId() {
		return iapId;
	}
	public void setIapId(String iapId) {
		this.iapId = iapId;
	}
}