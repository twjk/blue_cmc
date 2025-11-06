package com.qcmz.cmc.service;

public interface IRedPacketBillService {
	/**
	 * 添加收红包账单
	 * @param userId
	 * @param amount
	 * @param packetId
	 */
	public void saveReceiveBill(Long userId, Double amount, String packetId);
	/**
	 * 添加发红包账单
	 * @param userId
	 * @param amount
	 * @param packetId
	 */
	public void saveSendBill(Long userId, Double amount, String packetId);
	/**
	 * 添加提现账单
	 * @param userId
	 * @param amount
	 * @param cashId
	 */
	public void saveCashBill(Long userId, Double amount, Long cashId);
	/**
	 * 添加驳回提现申请账单
	 * @param userId
	 * @param amount
	 * @param cashId
	 */
	public void saveRejectCashBill(Long userId, Double amount, Long cashId);
	/**
	 * 添加红包未领取余额账单
	 * @param userId
	 * @param amount
	 * @param packetId
	 */
	public void saveUnReceiveBill(Long userId, Double amount, String packetId);
}
