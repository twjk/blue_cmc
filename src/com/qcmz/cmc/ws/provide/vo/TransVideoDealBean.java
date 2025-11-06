package com.qcmz.cmc.ws.provide.vo;


/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class TransVideoDealBean {
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
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
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
