package com.qcmz.cmc.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.qcmz.cmc.ws.provide.vo.RewardBillBean;
import com.qcmz.cmc.ws.provide.vo.RewardBillQueryBean;
import com.qcmz.framework.page.PageBean;

public interface IRewardBillService {
	/**
	 * 分页获取账单
	 * @param map
	 * @param pageBean<CmcRewardBill>
	 * @return
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean);
	/**
	 * 分页获取用户账单列表
	 * @param query
	 * @return
	 */
	public List<RewardBillBean> findBillInfo(RewardBillQueryBean query);
	/**
	 * 获取账单数量
	 * @param userId not null
	 * @param billType not null
	 * @param subServiceType
	 * @param date 日期
	 * @return
	 */
	public Long getCount(Long userId, Integer billType, String subServiceType, Date date);
	/**
	 * 是否存在
	 * @param userId
	 * @param billType
	 * @param subServiceType
	 * @param orderId
	 * @return
	 */
	public Boolean isExist(Long userId, Integer billType, String subServiceType, String orderId);
	/**
	 * 添加获得奖励账单
	 * @param userId
	 * @param amount
	 * @param billDesc
	 * @param orderId
	 */
	public void saveReceiveBill(Long userId, Double amount, String billDesc, String subServiceType, String orderId);
	/**
	 * 添加赠送账单
	 * @param userId
	 * @param amount
	 * @param billDesc
	 */
	public void saveBestowBill(Long userId, Double amount, String billDesc);
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
}
