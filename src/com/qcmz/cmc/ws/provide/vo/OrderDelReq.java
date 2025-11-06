package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

/**
 * 类说明：删除订单请求
 * 修改历史：
 */
public class OrderDelReq extends Request {
	/**
	 * 订单编号
	 */
	private String orderId;
	/**
	 * 用户编号
	 */
	private Long uid;
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
}
