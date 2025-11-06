package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

public class OrderDealDetailBean {
	/**
	 * 订单编号
	 */
	protected String orderId;
	/**
	 * 订单分类
	 */
	protected Integer orderCat;
	/**
	 * 订单类型
	 */
	protected Integer orderType;
	/**
	 * 用户编号
	 */
	protected Long uid;
	/**
	 * 用户同类型订单数
	 */
	private long orderCount;
	/**
	 * 订单金额，元
	 */
	private Double amount;
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
	 * 预约订单，0否1是
	 */
	protected Integer appointFlag;
	/**
	 * 操作员是否可以接单
	 */
	protected boolean canAccept;
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
	 * 用户需求
	 */
	private String requirement;
	/**
	 * 待处理时间
	 */
	protected Long waitTime;
	/**
	 * 订单开始处理时间
	 */
	private Long operTime;
	/**
	 * 预计处理时长，秒
	 */
	private Long needTime;
	/**
	 * 完成时间
	 */
	protected Long finishTime;
	/**
	 * 操作员用户名
	 */
	protected String operator;
	/**
	 * 操作员姓名
	 */
	protected String operatorName;
	/**
	 * 处理状态
	 */
	protected String dealStatus;
	/**
	 * 处理进度
	 */
	protected String dealProgress;
	/**
	 * 日志
	 */
	protected List<OrderLogBean> logs = new ArrayList<OrderLogBean>();
	/**
	 * 留言
	 */
	protected List<OrderMsgBean> msgs = new ArrayList<OrderMsgBean>();
	
	public OrderDealDetailBean() {
		super();
	}
	
	//转为子类TransPicDealDetailBean、TransTextDealDetailBean、TransVideoDealDetailBean时使用
	//BeanUtil.copyProperties会把null转为0，所以不用
	public OrderDealDetailBean(OrderDealDetailBean orderDealBean) {
		super();
		this.orderId = orderDealBean.getOrderId();
		this.orderCat = orderDealBean.getOrderCat();
		this.orderType = orderDealBean.getOrderType();
		this.uid = orderDealBean.getUid();
		this.orderCount = orderDealBean.getOrderCount();
		this.amount = orderDealBean.getAmount();
		this.couponAmount = orderDealBean.getCouponAmount();
		this.walletAmount = orderDealBean.getWalletAmount();
		this.payableAmount = orderDealBean.getPayableAmount();
		this.appointFlag = orderDealBean.getAppointFlag();
		this.canAccept = orderDealBean.isCanAccept();
		this.lon = orderDealBean.getLon();
		this.lat = orderDealBean.getLat();
		this.address = orderDealBean.getAddress();
		this.requirement = orderDealBean.getRequirement();
		this.waitTime = orderDealBean.getWaitTime();
		this.operTime = orderDealBean.getOperTime();
		this.needTime = orderDealBean.getNeedTime();
		this.finishTime = orderDealBean.getFinishTime();
		this.operator = orderDealBean.getOperator();
		this.operatorName = orderDealBean.getOperatorName();
		this.dealStatus = orderDealBean.getDealStatus();
		this.dealProgress = orderDealBean.getDealProgress();
		this.logs = orderDealBean.getLogs();
		this.msgs = orderDealBean.getMsgs();
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	@JSON(serialize=false)
	public Integer getOrderCat() {
		return orderCat;
	}
	public void setOrderCat(Integer orderCat) {
		this.orderCat = orderCat;
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
	public long getOrderCount() {
		return orderCount;
	}
	public void setOrderCount(long orderCount) {
		this.orderCount = orderCount;
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
	public Integer getAppointFlag() {
		return appointFlag;
	}

	public void setAppointFlag(Integer appointFlag) {
		this.appointFlag = appointFlag;
	}
	public boolean isCanAccept() {
		return canAccept;
	}

	public void setCanAccept(boolean canAccept) {
		this.canAccept = canAccept;
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
	public String getRequirement() {
		return requirement;
	}
	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}
	public Long getWaitTime() {
		return waitTime;
	}
	public void setWaitTime(Long waitTime) {
		this.waitTime = waitTime;
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
	public Long getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Long finishTime) {
		this.finishTime = finishTime;
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
	public String getDealStatus() {
		return dealStatus;
	}
	public void setDealStatus(String dealStatus) {
		this.dealStatus = dealStatus;
	}
	public String getDealProgress() {
		return dealProgress;
	}
	public void setDealProgress(String dealProgress) {
		this.dealProgress = dealProgress;
	}
	public List<OrderLogBean> getLogs() {
		return logs;
	}
	public void setLogs(List<OrderLogBean> logs) {
		this.logs = logs;
	}
	public List<OrderMsgBean> getMsgs() {
		return msgs;
	}
	public void setMsgs(List<OrderMsgBean> msgs) {
		this.msgs = msgs;
	}
}
