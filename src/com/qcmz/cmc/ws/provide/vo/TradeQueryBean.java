package com.qcmz.cmc.ws.provide.vo;

import java.util.Date;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class TradeQueryBean {
	/**
	 * 用户编号
	 */
	private Long userId;
	/**
	 * 交易类型
	 */
	private String tradeType;
	/**
	 * 交易起始日期
	 */
	private Date begin;
	/**
	 * 交易结束日期
	 */
	private Date end;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public Date getBegin() {
		return begin;
	}
	public void setBegin(Date begin) {
		this.begin = begin;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
}
