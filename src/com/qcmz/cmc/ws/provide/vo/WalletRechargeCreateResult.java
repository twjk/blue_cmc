package com.qcmz.cmc.ws.provide.vo;

public class WalletRechargeCreateResult {
	/**
	 * 充值单号
	 */
	private String orderId;
	/**
	 * 预支付结果
	 */
	private PrepayResult prepayResult; 

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public PrepayResult getPrepayResult() {
		return prepayResult;
	}

	public void setPrepayResult(PrepayResult prepayResult) {
		this.prepayResult = prepayResult;
	}
}
