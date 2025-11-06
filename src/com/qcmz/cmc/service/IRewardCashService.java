package com.qcmz.cmc.service;

import java.util.List;
import java.util.Map;

import com.qcmz.cmc.entity.CmcRewardCash;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.log.Operator;

public interface IRewardCashService {
	/**
	 * 分页获取提现列表
	 * @param map
	 * @param pageBean<CmcRewardCash>
	 * 修改历史：
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean);
	/**
	 * 分页获取待打款的提现编号
	 * @param pageSize not null
	 * @param maxId not null
	 * @param lastId
	 * @return
	 */
	public List<Long> findCashId4Transfer(int pageSize, Long maxId, Long lastId);
	/**
	 * 获取用户提现列表
	 * @param userId
	 * @return
	 */
	public List<CmcRewardCash> findCash(Long userId);
	/**
	 * 获取提现信息
	 * @param cashId
	 * @return
	 */
	public CmcRewardCash getCash(Long cashId);
	/**
	 * 获取提现信息，带cmcRewardAccount
	 * @param cashId
	 * @return
	 */
	public CmcRewardCash getCashJoinAccount(Long cashId);
	/**
	 * 获取提现信息，含用户、帐户
	 * @param cashId
	 * @return
	 */
	public CmcRewardCash getCashJoin(Long cashId);
	/**
	 * 获取提现数
	 * @param status not null
	 * @return
	 */
	public Long getCashCount(Integer status);
	/**
	 * 更新
	 * @param entity
	 */
	public void update(CmcRewardCash entity);
	/**
	 * 申请提现
	 * @param userId
	 * @param cashAmount
	 */
	public Long applyCash(Long userId, Double cashAmount, String cashAccountType, String cashAccount);
	/**
	 * 驳回提现申请
	 * @param cashId
	 * @param reason
	 * @param oper
	 */
	public void rejectCash(Long cashId, String reason, Operator oper);
}
