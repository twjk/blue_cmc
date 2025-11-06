package com.qcmz.cmc.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.qcmz.cmc.entity.CmcRpCash;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.log.Operator;

public interface IRedPacketCashService {
	/**
	 * 分页获取提现列表
	 * @param map
	 * @param pageBean<CmcRpCash>
	 * 修改历史：
	 */
	public void findCash(Map<String, String> map, PageBean pageBean);
	/**
	 * 获取用户提现列表
	 * @param userId
	 * @return
	 */
	public List<CmcRpCash> findCash(Long userId);
	/**
	 * 获取提现信息
	 * @param cashId
	 * @return
	 */
	public CmcRpCash getCash(Long cashId);
	/**
	 * 获取提现信息，含用户、帐户
	 * @param cashId
	 * @return
	 */
	public CmcRpCash getCashJoin(Long cashId);
	/**
	 * 申请提现
	 * @param userId
	 * @param cashAmount
	 */
	public void applyCash(Long userId, Double cashAmount);
	/**
	 * 成功提现
	 * @param cashId
	 * @param oper
	 */
	public void successCash(Long cashId, String outTradeId, Date cashTime, Operator oper);
	/**
	 * 驳回提现申请
	 * @param cashId
	 * @param reason
	 * @param oper
	 */
	public void rejectCash(Long cashId, String reason, Operator oper);
}
