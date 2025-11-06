package com.qcmz.cmc.service;

import java.util.Map;

import com.qcmz.cmc.entity.CmcWalletRecharge;
import com.qcmz.cmc.vo.WalletOfflineRechargeBean;
import com.qcmz.cmc.ws.provide.vo.WalletRechargeCreateBean;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.log.Operator;

public interface IWalletRechargeService {
	/**
	 * 分页获取充值列表，带帐户
	 * @param map
	 * @param pageBean<CmcWalletRecharge>
	 * @return 
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean);
	/**
	 * 获取充值信息
	 * @param orderId
	 * @return
	 */
	public CmcWalletRecharge getRecharge(String orderId);
	/**
	 * 获取充值信息
	 * @param orderId
	 * @param userId
	 * @return
	 */
	public CmcWalletRecharge getRecharge(String orderId, Long userId);
	/**
	 * 获取充值信息，带帐户
	 * @param orderId
	 * @return
	 */
	public CmcWalletRecharge getRechargeJoinAccount(String orderId);
	/**
	 * 获取充值信息，带帐户
	 * @param orderId
	 * @param userId
	 * @return
	 */
	public CmcWalletRecharge getRechargeJoinAccount(String orderId, Long userId);
	/**
	 * 创建充值订单
	 * @param orderId
	 * @param bean
	 * @param version
	 * @return
	 */
	public CmcWalletRecharge createRecharge(String orderId, WalletRechargeCreateBean bean, String platform, String version);
	/**
	 * 取消充值
	 * @param orderId
	 */
	public void cancelRecharge(String orderId);
	/**
	 * 支付成功
	 * @param charge
	 */
	public void paySuccess(CmcWalletRecharge charge);
	/**
	 * 手工充值
	 * @param bean
	 * @param operator
	 */
	public void offlineRecharge(WalletOfflineRechargeBean bean, Operator operator);
	/**
	 * 退款成功
	 */
	public void refundSuccess(CmcWalletRecharge charge);
}
