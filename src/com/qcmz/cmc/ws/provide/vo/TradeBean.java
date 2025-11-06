package com.qcmz.cmc.ws.provide.vo;

/**
 * 类说明：交易信息
 * 修改历史：
 */
public class TradeBean {
	/**
	 * 交易编号
	 */
	private Long tradeId;
	/**
	 * 交易类型
	 */
	private String tradeType;
	/**
	 * 交易类型描述
	 */
	private String tradeTypeDesc;
	/**
	 * 交易时间
	 */
	private String tradeTime;
	/**
	 * 交易点数
	 */
	private int point;
	/**
	 * 交易描述
	 */
	private String description;
	
	public Long getTradeId() {
		return tradeId;
	}
	public void setTradeId(Long tradeId) {
		this.tradeId = tradeId;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public String getTradeTypeDesc() {
		return tradeTypeDesc;
	}
	public void setTradeTypeDesc(String tradeTypeDesc) {
		this.tradeTypeDesc = tradeTypeDesc;
	}
	public String getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
