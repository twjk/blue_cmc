package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

public class OrderBean {
	/**
	 * 订单编号
	 */
	private String orderId;
	/**
	 * 订单类型
	 */
	private Integer orderType;
	/**
	 * 用户编号
	 */
	private Long uid;
	/**
	 * 总金额
	 */
	private double amount;
	/**
	 * 优惠券金额
	 */
	private double couponAmount;
	/**
	 * 应付金额
	 */
	private double payableAmount;
	/**
	 * 实际支付金额
	 */
	private double payAmount;
	/**
	 * 创建时间
	 */
	private Long createTime;
	/**
	 * 日志
	 */
	private List<OrderLogBean> logs = new ArrayList<OrderLogBean>();
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Integer getOrderType() {
		return orderType;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getCouponAmount() {
		return couponAmount;
	}
	public void setCouponAmount(double couponAmount) {
		this.couponAmount = couponAmount;
	}
	public double getPayableAmount() {
		return payableAmount;
	}
	public void setPayableAmount(double payableAmount) {
		this.payableAmount = payableAmount;
	}
	public double getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(double payAmount) {
		this.payAmount = payAmount;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public List<OrderLogBean> getLogs() {
		return logs;
	}
	public void setLogs(List<OrderLogBean> logs) {
		this.logs = logs;
	}
}
