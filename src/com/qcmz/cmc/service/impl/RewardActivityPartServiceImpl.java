package com.qcmz.cmc.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.dao.IRewardActivityPartDao;
import com.qcmz.cmc.entity.CmcRewardPart;
import com.qcmz.cmc.service.IRewardActivityPartService;
import com.qcmz.cmc.ws.provide.vo.RewardInviteBean;

@Service
public class RewardActivityPartServiceImpl implements IRewardActivityPartService {
	@Autowired
	private IRewardActivityPartDao rewardPartDao;
	
	/**
	 * 获取用户参加活动信息
	 * @param actId
	 * @param inviteBean
	 * @return
	 */
	public CmcRewardPart getPart(Long actId, RewardInviteBean inviteBean){
		return rewardPartDao.getPart(actId, inviteBean);
	}
	
	/**
	 * 获取用户参加活动次数
	 * @param userId
	 * @param actId
	 * @param partFreq
	 * @return
	 */
	public Long getPartTimes(Long userId, Long actId, Integer partFreq){
		return rewardPartDao.getPartTimes(userId, actId, partFreq);
	}
	
	/**
	 * 保存用户活动参加记录
	 * @param actId
	 * @param userId
	 * @param inviteeId
	 * @param subServiceType
	 * @param serviceId
	 */
	public void saveRewardPart(Long actId, Long userId, Long inviteeId, String subServiceType, String serviceId){
		CmcRewardPart entity = new CmcRewardPart();
		entity.setActid(actId);
		entity.setUserid(userId);
		entity.setInviteeid(inviteeId);
		entity.setSubservicetype(subServiceType);
		entity.setServiceid(serviceId);
		entity.setParttime(new Date());
		rewardPartDao.save(entity);
	}
}
