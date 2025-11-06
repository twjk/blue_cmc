package com.qcmz.cmc.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.qcmz.cmc.entity.CmcWalletBill;
import com.qcmz.cmc.ws.provide.vo.WalletBillBean;
import com.qcmz.cmc.ws.provide.vo.WalletBillQueryBean;
import com.qcmz.framework.page.PageBean;

public interface IWalletBillService {
	/**
	 * 分页获取账单
	 * @param map
	 * @param pageBean<CmcWalletBill>
	 * @return
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean);
	/**
	 * 分页获取账单列表
	 * @param query
	 * @return
	 */
	public List<WalletBillBean> findBillInfo(WalletBillQueryBean query);
	/**
	 * 获取账单数量
	 * @param userId not null
	 * @param billType not null
	 * @param date
	 * @return
	 */
	public Long getCount(Long userId, Integer billType, Date date);
	/**
	 * 获取用户账单
	 * @param userId not null
	 * @param orderId not null
	 * @param billType not null
	 */
	public CmcWalletBill getBill(Long userId, String orderId, Integer billType);
	/**
	 * 保存充值账单
	 * @param userId
	 * @param amount
	 * @param orderId
	 */
	public void saveRechargeBill(Long userId, double amount, String orderId);
	/**
	 * 保存消费账单
	 * @param userId
	 * @param amount
	 * @param subServiceType
	 * @param orderId
	 */
	public void saveConsumeBill(Long userId, double amount, String subServiceType, String orderId);
	/**
	 * 保存消费退款账单
	 * @param userId
	 * @param amount
	 * @param consumeBill
	 */
	public void saveConsumeRefundBill(Long userId, double amount, CmcWalletBill consumeBill);
	/**
	 * 保存充值退款账单
	 * @param userId
	 * @param amount
	 * @param orderId
	 */
	public void saveRechargeRefundBill(Long userId, double amount, String orderId);
	/**
	 * 保存账单
	 * @param userId
	 * @param amount
	 * @param billType
	 * @param orderId
	 * @param description
	 * @param operator
	 * @param operatorName
	 */
	public void saveBill(Long userId, double amount, Integer billType, String orderId, String description, String operator, String operatorName);
}
