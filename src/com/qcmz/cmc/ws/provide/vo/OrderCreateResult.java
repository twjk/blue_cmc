package com.qcmz.cmc.ws.provide.vo;

public class OrderCreateResult {
	/**
	 * 订单编号
	 */
	private String orderId;
	/**
	 * 处理状态
	 */
	private String dealStatus;
	/**
	 * 兑换码
	 */
	private String exchangeCode;
	/**
	 * 预支付结果
	 */
	private PrepayResult prepayResult;
	/**
	 * 房间号，视频翻译订单
	 */
	private String roomId;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getDealStatus() {
		return dealStatus;
	}
	public void setDealStatus(String dealStatus) {
		this.dealStatus = dealStatus;
	}
	public String getExchangeCode() {
		return exchangeCode;
	}
	public void setExchangeCode(String exchangeCode) {
		this.exchangeCode = exchangeCode;
	}
	public PrepayResult getPrepayResult() {
		return prepayResult;
	}
	public void setPrepayResult(PrepayResult prepayResult) {
		this.prepayResult = prepayResult;
	}
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
}
