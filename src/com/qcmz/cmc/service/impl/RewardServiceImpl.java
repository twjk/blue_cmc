package com.qcmz.cmc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.entity.CmcRewardActivity;
import com.qcmz.cmc.service.IRewardAccountService;
import com.qcmz.cmc.service.IRewardActivityPartService;
import com.qcmz.cmc.service.IRewardBillService;
import com.qcmz.cmc.service.IRewardService;
import com.qcmz.cmc.util.RewardUtil;
import com.qcmz.cmc.ws.provide.vo.RewardInviteBean;

@Service
public class RewardServiceImpl implements IRewardService {
	@Autowired
	private IRewardAccountService rewardAccountService;
	@Autowired
	private IRewardBillService rewardBillService;
	@Autowired
	private IRewardActivityPartService rewardActivityPartService;
		
	/**
	 * 发放奖励
	 * @param userId
	 * @param amount
	 * @param billDesc
	 * @param subServiceType
	 * @param orderId
	 */
	public void grantReward(String serviceType, Long userId, Double amount, String billDesc, String subServiceType, String orderId){
		//帐户
		rewardAccountService.createAccount(serviceType, userId);
		
		//账单
		rewardBillService.saveReceiveBill(userId, amount, billDesc, subServiceType, orderId);
		
		//更新帐户
		rewardAccountService.updateAmount(userId);
	}
	
	/**
	 * 发放活动奖励
	 * @param act
	 * @param inviteBean
	 * @param serviceType
	 */
	public void grantActivityReward(CmcRewardActivity act, RewardInviteBean inviteBean, String serviceType){
		Double amount;
		String billDesc = "分享奖励";
		String orderId = String.valueOf(inviteBean.getServiceId());
		
		//给邀请人发放奖励
		amount = RewardUtil.getRewardAmount(act.getInviteramount());
		grantReward(serviceType, inviteBean.getInviterId(), amount, billDesc, inviteBean.getSubServiceType(), orderId);
		
		//给被邀请人发放奖励
		amount = RewardUtil.getRewardAmount(act.getInviteeamount());
		grantReward(serviceType, inviteBean.getInviteeId(), amount, billDesc, inviteBean.getSubServiceType(), orderId);
		
		//参加记录
		rewardActivityPartService.saveRewardPart(act.getActid(), inviteBean.getInviterId(), inviteBean.getInviteeId(), inviteBean.getSubServiceType(), inviteBean.getServiceId());	
	}
	
	/**
	 * 赠送奖励
	 * @param userId
	 * @param amount
	 * @param billDesc
	 */
	public void bestowReward(String serviceType, Long userId, Double amount, String billDesc){
		//帐户
		rewardAccountService.createAccount(serviceType, userId);
		
		//账单
		rewardBillService.saveBestowBill(userId, amount, billDesc);
		
		//更新帐户
		rewardAccountService.updateAmount(userId);
	}
}
