package com.qcmz.cmc.service;

import com.qcmz.framework.exception.BusinessException;

public interface IWalletConsumeService {
	/**
	 * 钱包消费
	 * @param userId
	 * @param amount
	 * @param subServiceType
	 * @param orderId
	 * @exception BusinessException
	 */
	public void consume(Long userId, double amount, String subServiceType, String orderId);
	/**
	 * 钱包消费，不考虑余额
	 * @param userId
	 * @param amount
	 * @param subServiceType
	 * @param orderId
	 */
	public void consumeForce(Long userId, double amount, String subServiceType, String orderId);
	/**
	 * 退款
	 * @param userId
	 * @param amount
	 * @param orderId
	 */
	public boolean refund(Long userId, double amount, String orderId);
}
