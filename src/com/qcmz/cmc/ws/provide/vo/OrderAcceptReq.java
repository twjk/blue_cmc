package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

/**
 * 类说明：客服接单请求
 * 修改历史：
 */
public class OrderAcceptReq extends Request {
	/**
	 * 订单编号
	 */
	private String orderId;
	/**
	 * 操作员用户名
	 */
	private String operator;
	/**
	 * 操作员姓名
	 */
	private String operatorName;
	
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
}
