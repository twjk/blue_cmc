package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Response;

/**
 * 类说明：添加翻译图片结果
 * 修改历史：
 */
public class TransPicAddResp extends Response {
	/**
	 * 图片编号
	 */
	private String picId;
	/**
	 * 订单编号
	 */
	private String orderId;
	/**
	 * 预支付结果
	 */
	private PrepayResult prepayResult; 

	public String getPicId() {
		return picId;
	}
	public void setPicId(String picId) {
		this.picId = picId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public PrepayResult getPrepayResult() {
		return prepayResult;
	}
	public void setPrepayResult(PrepayResult prepayResult) {
		this.prepayResult = prepayResult;
	}
}
