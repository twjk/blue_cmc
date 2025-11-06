package com.qcmz.cmc.process;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.service.ICrowdTaskBlacklistService;
import com.qcmz.framework.util.log.Operator;

@Component
public class CrowdTaskBlacklistProcess {
	@Autowired
	private ICrowdTaskBlacklistService crowdTaskBlacklistService;
	@Autowired
	private UserCrowdTaskProcess userCrowdTaskProcess;
	
	/**
	 * 用户加入黑名单
	 * @param userId
	 */
	public void addBlacklist(Long userId, Operator oper){
		//加入黑名单
		crowdTaskBlacklistService.addBlacklist(userId);
		
		//取消用户未完成任务
		userCrowdTaskProcess.cancelTaskByUserId(userId, "帐户异常", oper);
	}
}
