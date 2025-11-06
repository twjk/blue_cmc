package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Response;

public class OrderCreateResp extends Response {
	/**
	 * 订单编号
	 */
	private String orderId;
	/**
	 * 图片编号，兼容老版本图片翻译订单
	 */
	private String picId;
	/**
	 * 预支付结果
	 */
	private PrepayResult prepayResult;
	/**
	 * 处理状态
	 */
	private String dealStatus;
	/**
	 * 兑换码
	 */
	private String exchangeCode;
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

	public String getPicId() {
		return picId;
	}

	public void setPicId(String picId) {
		this.picId = picId;
	}

	public PrepayResult getPrepayResult() {
		return prepayResult;
	}

	public void setPrepayResult(PrepayResult prepayResult) {
		this.prepayResult = prepayResult;
	}

	public String getExchangeCode() {
		return exchangeCode;
	}

	public void setExchangeCode(String exchangeCode) {
		this.exchangeCode = exchangeCode;
	}

	public String getDealStatus() {
		return dealStatus;
	}

	public void setDealStatus(String dealStatus) {
		this.dealStatus = dealStatus;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
}
