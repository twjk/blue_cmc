package com.qcmz.cmc.service;

import com.qcmz.cmc.entity.CmcRewardActivity;
import com.qcmz.cmc.ws.provide.vo.RewardInviteBean;

public interface IRewardService {
	/**
	 * 发放奖励
	 * @param userId
	 * @param amount
	 * @param billDesc
	 * @param orderId
	 */
	public void grantReward(String serviceType, Long userId, Double amount, String billDesc, String subServiceType, String orderId);
	/**
	 * 发放活动奖励
	 * @param act
	 * @param inviteBean
	 * @param serviceType
	 */
	public void grantActivityReward(CmcRewardActivity act, RewardInviteBean inviteBean, String serviceType);
	/**
	 * 赠送奖励
	 * @param userId
	 * @param amount
	 * @param billDesc
	 */
	public void bestowReward(String serviceType, Long userId, Double amount, String billDesc);
}
