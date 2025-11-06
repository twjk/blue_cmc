package com.qcmz.cmc.service;

import com.qcmz.cmc.entity.CmcRewardPart;
import com.qcmz.cmc.ws.provide.vo.RewardInviteBean;

public interface IRewardActivityPartService {
	/**
	 * 获取用户参加活动信息
	 * @param actId
	 * @param inviteBean
	 * @return
	 */
	public CmcRewardPart getPart(Long actId, RewardInviteBean inviteBean);
	/**
	 * 获取用户参加活动次数
	 * @param userId
	 * @param actId
	 * @param partFreq
	 * @return
	 */
	public Long getPartTimes(Long userId, Long actId, Integer partFreq);
	/**
	 * 保存用户活动参加记录
	 * @param actId
	 * @param userId
	 * @param inviteeId
	 * @param subServiceType
	 * @param serviceId
	 */
	public void saveRewardPart(Long actId, Long userId, Long inviteeId, String subServiceType, String serviceId);
}
