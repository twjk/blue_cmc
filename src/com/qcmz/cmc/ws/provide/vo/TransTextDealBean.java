package com.qcmz.cmc.ws.provide.vo;


/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class TransTextDealBean {
	/**
	 * 订单编号
	 */
	private String orderId;
	/**
	 * 译文
	 */
	private String dst;
	/**
	 * 源语言
	 */
	private String from;
	/**
	 * 目标语言
	 */
	private String to;
	/**
	 * 操作员用户名
	 */
	private String operator;
	/**
	 * 操作员姓名
	 */
	private String operatorName;
	/**
	 * 预计翻译时长，秒
	 */
	private Long needTime;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getDst() {
		return dst;
	}
	public void setDst(String dst) {
		this.dst = dst;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
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
	public Long getNeedTime() {
		return needTime;
	}
	public void setNeedTime(Long needTime) {
		this.needTime = needTime;
	}
}
