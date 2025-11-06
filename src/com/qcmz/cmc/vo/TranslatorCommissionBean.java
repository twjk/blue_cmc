package com.qcmz.cmc.vo;

import java.util.ArrayList;
import java.util.List;

import com.qcmz.cmc.entity.CmcROrder;

public class TranslatorCommissionBean {
	/**
	 * 操作员帐户
	 */
	private String operCd;
	/**
	 * 操作员姓名
	 */
	private String operName;
	/**
	 * 订单数
	 */
	private Long orderCount = 0L;
	/**
	 * 订单总金额
	 */
	private Double orderAmount = 0.0;
	/**
	 * 佣金金额
	 */
	private Double commissionAmount = 0.0;
	
	/**
	 * 订单列表，带使用的用户套餐信息
	 */
	private List<CmcROrder> orderList = new ArrayList<CmcROrder>();
	
	public TranslatorCommissionBean() {
		super();
	}
	public TranslatorCommissionBean(Double commissionAmount, Double orderAmount, Long orderCount, String operCd, String operName) {
		super();
		this.commissionAmount = commissionAmount;
		this.orderAmount = orderAmount;
		this.orderCount = orderCount;
		this.operCd = operCd;
		this.operName = operName;
	}
	public String getOperCd() {
		return operCd;
	}
	public void setOperCd(String operCd) {
		this.operCd = operCd;
	}
	public String getOperName() {
		return operName;
	}
	public void setOperName(String operName) {
		this.operName = operName;
	}
	public Long getOrderCount() {
		return orderCount;
	}
	public void setOrderCount(Long orderCount) {
		this.orderCount = orderCount;
	}
	public Double getCommissionAmount() {
		return commissionAmount;
	}
	public void setCommissionAmount(Double commissionAmount) {
		this.commissionAmount = commissionAmount;
	}
	public Double getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(Double orderAmount) {
		this.orderAmount = orderAmount;
	}
	public List<CmcROrder> getOrderList() {
		return orderList;
	}
	public void setOrderList(List<CmcROrder> orderList) {
		this.orderList = orderList;
	}
}
