package com.qcmz.cmc.vo;

import com.qcmz.framework.util.DoubleUtil;

public class WalletRechargeCountBean {
	private Integer status;
	private Long count = 0L;
	private Double amount = 0.0;
	private Double payAmount = 0.0;
	
	public WalletRechargeCountBean() {
		super();
	}
	
	public WalletRechargeCountBean(Integer status, Long count, Double amount, Double payAmount) {
		super();
		this.status = status;
		this.count = count;
		this.amount = amount;
		this.payAmount = payAmount;
	}

	public void increase(WalletRechargeCountBean bean){
		this.count = this.count + bean.getCount();
		this.amount = DoubleUtil.add(this.amount, bean.getAmount());
		this.payAmount = DoubleUtil.add(this.payAmount, bean.getPayAmount());
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public Double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}
}
