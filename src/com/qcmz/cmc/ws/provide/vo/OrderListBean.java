package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderListBean {
	/**
	 * 订单编号
	 */
	private String orderId;
	/**
	 * 用户编号
	 */
	private Long uid;
	/**
	 * 订单类型
	 */
	private Integer orderType;
	/**
	 * 源语言代码
	 */
	private String from;
	/**
	 * 目标语言代码
	 */
	private String to;
	/**
	 * 订单金额，元
	 */
	private Double amount;
	/**
	 * 创建时间
	 */
	private Long createTime;
	/**
	 * 预计处理时长，秒
	 */
	private Long needTime;
	/**
	 * 状态
	 */
	private String dealStatus;
	/**
	 * 评价状态
	 */
	private Integer evalStatus;
	/**
	 * 预约订单，0否1是
	 */
	protected Integer appointFlag;
	/**
	 * 预约时间
	 */
	protected Long appointTime;
	
	/**
	 * 商品列表
	 */
	private List<OrderItemListBean> items = new ArrayList<OrderItemListBean>();
	
	public OrderListBean() {
		super();
	}

	public OrderListBean(String orderId, Long uid, Integer orderType, String from, String to
			, Double amount, Date createTime, Long needTime, String dealStatus, Integer evalStatus
			, Integer appointFlag, Date appointTime) {
		super();
		this.orderId = orderId;
		this.uid = uid;
		this.orderType = orderType;
		this.from = from;
		this.to = to;
		this.amount = amount;
		this.createTime = createTime.getTime();
		this.needTime = needTime;
		this.dealStatus = dealStatus;
		this.evalStatus = evalStatus;
		this.appointFlag = appointFlag;
		this.appointTime = appointTime==null?null:appointTime.getTime();
	}

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

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
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

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getNeedTime() {
		return needTime;
	}

	public void setNeedTime(Long needTime) {
		this.needTime = needTime;
	}

	public String getDealStatus() {
		return dealStatus;
	}

	public void setDealStatus(String dealStatus) {
		this.dealStatus = dealStatus;
	}

	public Integer getEvalStatus() {
		return evalStatus;
	}

	public void setEvalStatus(Integer evalStatus) {
		this.evalStatus = evalStatus;
	}

	public Integer getAppointFlag() {
		return appointFlag;
	}

	public void setAppointFlag(Integer appointFlag) {
		this.appointFlag = appointFlag;
	}

	public Long getAppointTime() {
		return appointTime;
	}

	public void setAppointTime(Long appointTime) {
		this.appointTime = appointTime;
	}

	public List<OrderItemListBean> getItems() {
		return items;
	}

	public void setItems(List<OrderItemListBean> items) {
		this.items = items;
	}
	
}
