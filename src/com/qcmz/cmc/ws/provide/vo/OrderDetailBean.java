package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailBean {
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
	 * 订单金额，元
	 */
	private Double amount;
	/**
	 * 使用的套餐名称
	 */
	private String comboTitle;
	/**
	 * 优惠券金额
	 */
	private double couponAmount;
	/**
	 * 钱包抵扣金额
	 */
	private double walletAmount;
	/**
	 * 应付金额
	 */
	private double payableAmount;
	/**
	 * 交易途径
	 */
	private String tradeWay;
	/**
	 * 订单创建时间
	 */
	private Long createTime;
	/**
	 * 订单开始处理时间
	 */
	private Long operTime;
	/**
	 * 预计处理时长，秒
	 */
	private Long needTime;
	/**
	 * 预约订单，0否1是
	 */
	protected Integer appointFlag;
	/**
	 * 预约时间
	 */
	protected Long appointTime;
	/**
	 * 用户需求
	 */
	private String requirement;
	/**
	 * 用户手机号码
	 */
	private String mobile;
	/**
	 * 经度
	 */
	private String lon;
	/**
	 * 纬度
	 */
	private String lat;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 状态
	 */
	private String dealStatus;
	/**
	 * 评价状态
	 */
	private Integer evalStatus;
	/**
	 * 商品列表
	 */
	private List<OrderItemBean> items = new ArrayList<OrderItemBean>();
	/**
	 * 日志
	 */
	private List<OrderLogBean> logs = new ArrayList<OrderLogBean>();
	public OrderDetailBean() {
		super();
	}
	
	//转为子类TransPicDetailBean、TransTextDetailBean、TransVideoDetailBean时使用
	//BeanUtil.copyProperties会把null转为0，所以不用
	public OrderDetailBean(OrderDetailBean orderDetail) {
		super();
		this.orderId = orderDetail.getOrderId();
		this.uid = orderDetail.getUid();
		this.orderType = orderDetail.getOrderType();
		this.amount = orderDetail.getAmount();
		this.comboTitle = orderDetail.getComboTitle();
		this.couponAmount = orderDetail.getCouponAmount();
		this.walletAmount = orderDetail.getWalletAmount();
		this.payableAmount = orderDetail.getPayableAmount();
		this.tradeWay = orderDetail.getTradeWay();
		this.createTime = orderDetail.getCreateTime();
		this.operTime = orderDetail.getOperTime();
		this.needTime = orderDetail.getNeedTime();
		this.appointFlag = orderDetail.getAppointFlag();
		this.appointTime = orderDetail.getAppointTime();
		this.requirement = orderDetail.getRequirement();
		this.mobile = orderDetail.getMobile();
		this.lon = orderDetail.getLon();
		this.lat = orderDetail.getLat();
		this.address = orderDetail.getAddress();
		this.dealStatus = orderDetail.getDealStatus();
		this.evalStatus = orderDetail.getEvalStatus();
		this.items = orderDetail.getItems();
		this.logs = orderDetail.getLogs();
	}
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public Integer getOrderType() {
		return orderType;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	public String getRequirement() {
		return requirement;
	}
	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTradeWay() {
		return tradeWay;
	}
	public void setTradeWay(String tradeWay) {
		this.tradeWay = tradeWay;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public double getCouponAmount() {
		return couponAmount;
	}
	public void setCouponAmount(double couponAmount) {
		this.couponAmount = couponAmount;
	}
	public double getWalletAmount() {
		return walletAmount;
	}
	public void setWalletAmount(double walletAmount) {
		this.walletAmount = walletAmount;
	}
	public double getPayableAmount() {
		return payableAmount;
	}
	public void setPayableAmount(double payableAmount) {
		this.payableAmount = payableAmount;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public String getDealStatus() {
		return dealStatus;
	}
	public void setDealStatus(String dealStatus) {
		this.dealStatus = dealStatus;
	}
	public List<OrderItemBean> getItems() {
		return items;
	}
	public void setItems(List<OrderItemBean> items) {
		this.items = items;
	}
	public List<OrderLogBean> getLogs() {
		return logs;
	}
	public void setLogs(List<OrderLogBean> logs) {
		this.logs = logs;
	}	
	public String getLon() {
		return lon;
	}
	public void setLon(String lon) {
		this.lon = lon;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getComboTitle() {
		return comboTitle;
	}
	public void setComboTitle(String comboTitle) {
		this.comboTitle = comboTitle;
	}
	public Long getOperTime() {
		return operTime;
	}
	public void setOperTime(Long operTime) {
		this.operTime = operTime;
	}
	public Long getNeedTime() {
		return needTime;
	}
	public void setNeedTime(Long needTime) {
		this.needTime = needTime;
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
}
