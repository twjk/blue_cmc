package com.qcmz.cmc.ws.provide.locator;

import org.apache.log4j.Logger;

import com.qcmz.cmc.ws.provide.vo.RewardBestowReq;
import com.qcmz.framework.ws.util.AuthUtil;
import com.qcmz.framework.ws.vo.Response;

/**
 * 类说明：奖励金
 * 修改历史：
 */
public class RewardWS {
	private static Logger logger = Logger.getLogger(RewardWS.class);
	
	/**
	 * 赠送奖励金
	 * @param uid
	 * @param amount
	 * @param description
	 */
	public static boolean bestowReward(Long uid, Double amount, String description){
		try {
			RewardBestowReq req = new RewardBestowReq();
			req.setAuthAccount(AuthUtil.AUTH_ACCOUNT);
			req.setAuthToken(AuthUtil.getAuthToken());
			req.getBean().setUid(uid);
			req.getBean().setAmount(amount);
			req.getBean().setDescription(description);
			
			Response resp = CmcWSLocator.getRewardWS().bestowReward(req);
			if(!resp.successed()){
				logger.error("赠送奖励金失败："+resp.toString());
			}
			return resp.successed();
		} catch (Exception e) {
			logger.error("赠送奖励金失败", e);
			return false;
		}
	}
}
