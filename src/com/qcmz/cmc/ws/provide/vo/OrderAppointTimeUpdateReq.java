package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class OrderAppointTimeUpdateReq extends Request {
	/**
	 * 订单编号
	 */
	private String orderId;
	/**
	 * 预约时间
	 */
	private Long appointTime;
	/**
	 * 操作员用户名
	 */
	private String operator;
	/**
	 * 操作员姓名
	 */
	private String operatorName;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Long getAppointTime() {
		return appointTime;
	}
	public void setAppointTime(Long appointTime) {
		this.appointTime = appointTime;
	}
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
}
