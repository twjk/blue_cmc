package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class OrderMsgQueryReq extends Request {
	/**
	 * 用户编号
	 */
	private Long uid;
	/**
	 * 订单编号
	 */
	private String orderId;
	/**
	 * 最后一条留言编号
	 */
	private Long lastMsgId;
	
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
	public Long getLastMsgId() {
		return lastMsgId;
	}
	public void setLastMsgId(Long lastMsgId) {
		this.lastMsgId = lastMsgId;
	}
}
