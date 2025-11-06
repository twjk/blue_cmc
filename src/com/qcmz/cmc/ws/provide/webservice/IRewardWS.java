package com.qcmz.cmc.ws.provide.webservice;

import com.qcmz.cmc.ws.provide.vo.RewardBestowReq;
import com.qcmz.framework.ws.vo.Response;

public interface IRewardWS {
	/**
	 * 赠送奖励金
	 * @param req
	 * @return
	 */
	public Response bestowReward(RewardBestowReq req);
}
