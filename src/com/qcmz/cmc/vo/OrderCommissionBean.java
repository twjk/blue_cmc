package com.qcmz.cmc.vo;

import java.util.Date;

public class OrderCommissionBean {
	/**
	 * 佣金金额
	 */
	private Double commissionAmount;
	/**
	 * 佣金结算状态
	 */
	private Integer commissionStatus;
	/**
	 * 佣金归口日期
	 */
	private Date commissionDate;
	public Double getCommissionAmount() {
		return commissionAmount;
	}
	public void setCommissionAmount(Double commissionAmount) {
		this.commissionAmount = commissionAmount;
	}
	public Integer getCommissionStatus() {
		return commissionStatus;
	}
	public void setCommissionStatus(Integer commissionStatus) {
		this.commissionStatus = commissionStatus;
	}
	public Date getCommissionDate() {
		return commissionDate;
	}
	public void setCommissionDate(Date commissionDate) {
		this.commissionDate = commissionDate;
	}
}
