package com.qcmz.cmc.service;

import java.util.Map;

import com.qcmz.cmc.entity.CmcRewardAccount;
import com.qcmz.cmc.ws.provide.vo.RewardAccountBean;
import com.qcmz.framework.page.PageBean;

public interface IRewardAccountService {
	/**
	 * 分页获取帐户
	 * @param map
	 * @param pageBean<CmcRewardAccount>
	 * @return
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean);
	/**
	 * 获取帐户
	 * @param userId
	 * @return
	 */
	public CmcRewardAccount getAccount(Long userId);
	/**
	 * 获取帐户，带用户信息
	 * @param userId
	 * @return
	 */
	public CmcRewardAccount getAccountJoinUser(Long userId);
	/**
	 * 获取账户信息
	 * @param userId
	 * @return
	 */
	public RewardAccountBean getAccountInfo(Long userId);
	/**
	 * 创建帐户，如果已经存在不创建
	 * @param userId
	 * @return
	 */
	public CmcRewardAccount createAccount(String serviceType, Long userId);
	/**
	 * 更新帐户金额
	 * @param userId
	 */
	public void updateAmount(Long userId);
	/**
	 * 更新帐户状态
	 * @param userId
	 * @param status
	 */
	public void updateStatus(Long userId, Integer status);
}
