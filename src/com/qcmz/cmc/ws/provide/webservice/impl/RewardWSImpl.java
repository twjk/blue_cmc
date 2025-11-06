package com.qcmz.cmc.ws.provide.webservice.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.ws.provide.service.RewardInterface;
import com.qcmz.cmc.ws.provide.vo.RewardBestowReq;
import com.qcmz.cmc.ws.provide.webservice.IRewardWS;
import com.qcmz.framework.ws.BaseWS;
import com.qcmz.framework.ws.vo.Response;

/**
 * 类说明：奖励金接口
 * 修改历史：
 */
@Component
public class RewardWSImpl extends BaseWS implements IRewardWS {
	@Autowired
	private RewardInterface rewardInterface;
	
	/**
	 * 赠送奖励金
	 * @param req
	 * @return
	 */
	public Response bestowReward(RewardBestowReq req){
		return rewardInterface.bestowReward(req, interfaceType, getRemoteAddr());
	}
}
