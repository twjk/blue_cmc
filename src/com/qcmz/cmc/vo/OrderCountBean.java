package com.qcmz.cmc.vo;

import com.qcmz.framework.util.DoubleUtil;

public class OrderCountBean {
	private Integer orderType;
	private String dealStatus;
	private Long count = 0L;
	private Double amount = 0.0;
	private Double couponAmount = 0.0;
	private Double walletAmount = 0.0;
	private Double payAmount = 0.0;
	
	public OrderCountBean() {
		super();
	}
	public OrderCountBean(Integer orderType, String dealStatus, Long count,
			Double amount, Double couponAmount, Double walletAmount, Double payAmount) {
		super();
		this.orderType = orderType;
		this.dealStatus = dealStatus;
		this.count = count;
		this.amount = amount;
		this.couponAmount = couponAmount;
		this.walletAmount = walletAmount;
		this.payAmount = payAmount;
	}
	public void increase(OrderCountBean bean){
		this.count = this.count + bean.getCount();
		this.amount = DoubleUtil.add(this.amount, bean.getAmount());
		this.couponAmount = DoubleUtil.add(this.couponAmount, bean.getCouponAmount());
		this.walletAmount = DoubleUtil.add(this.walletAmount, bean.getWalletAmount());
		this.payAmount = DoubleUtil.add(this.payAmount, bean.getPayAmount());
	}
	public void increase(WalletRechargeCountBean bean){
		this.count = this.count + bean.getCount();
		this.amount = DoubleUtil.add(this.amount, bean.getAmount());
		this.payAmount = DoubleUtil.add(this.payAmount, bean.getPayAmount());
	}
	
	public Integer getOrderType() {
		return orderType;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	public String getDealStatus() {
		return dealStatus;
	}
	public void setDealStatus(String dealStatus) {
		this.dealStatus = dealStatus;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getCouponAmount() {
		return couponAmount;
	}
	public void setCouponAmount(Double couponAmount) {
		this.couponAmount = couponAmount;
	}
	public Double getWalletAmount() {
		return walletAmount;
	}
	public void setWalletAmount(Double walletAmount) {
		this.walletAmount = walletAmount;
	}
	public Double getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}
}
