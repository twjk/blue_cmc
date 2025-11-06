package com.qcmz.cmc.dao;

import com.qcmz.cmc.entity.CmcRewardPart;
import com.qcmz.cmc.ws.provide.vo.RewardInviteBean;
import com.qcmz.framework.dao.IBaseDAO;

public interface IRewardActivityPartDao extends IBaseDAO {
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
}
