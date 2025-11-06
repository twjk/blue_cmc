package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

/**
 * 类说明：获取文本翻译详细信息请求
 * 修改历史：
 */
public class OrderDealGetReq extends Request {
	/**
	 * 订单编号
	 */
	private String orderId;
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
}