package com.qcmz.cmc.process;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.service.IUserCrowdTaskQcService;
import com.qcmz.cmc.service.IUserCrowdTaskService;
import com.qcmz.cmc.thrd.UserCrowdTaskRewardNotifyThrd;
import com.qcmz.cmc.vo.UserCrowdRewardGrantResult;

/**
 * 众包任务奖励处理
 */
@Component
public class UserCrowdTaskRewardProcess {
	@Autowired
	private IUserCrowdTaskService userCrowdTaskService;
	@Autowired
	private IUserCrowdTaskQcService userCrowdTaskQcService;
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 发放奖励
	 */
	public void grantReward(){
		//发放任务奖励
		grantTaskReward();
		//按题目发放任务奖励
		grantTaskRewardBySubject();

		//发放审核奖励
		grantVerifyReward();
		
		//发放审校奖励
		grantQcReward();
	}
	
	/**
	 * 发放任务奖励
	 */
	public void grantTaskReward(){
		UserCrowdRewardGrantResult grantResult;
		
		List<String> list = userCrowdTaskService.findUserTask4GrantTaskReward();
		for (String utId : list) {
			try {
				//发放奖励
				grantResult = userCrowdTaskService.grantTaskReward(utId);
				//通知
				UserCrowdTaskRewardNotifyThrd.notifyReward(grantResult);
			} catch (Exception e) {
				logger.error("发放任务奖励失败："+utId, e);
			}
		}
	}
	
	/**
	 * 按题目发放任务奖励
	 */
	public void grantTaskRewardBySubject(){
		UserCrowdRewardGrantResult grantResult;
		
		List<Long> list = userCrowdTaskService.findUserSubject4GrantTaskReward();
		for (Long usId : list) {
			try {
				//发放奖励
				grantResult = userCrowdTaskService.grantTaskRewardBySubject(usId);
				//通知
				UserCrowdTaskRewardNotifyThrd.notifyReward(grantResult);
			} catch (Exception e) {
				logger.error("按题目发放任务奖励失败："+usId, e);
			}
		}
	}
	
	/**
	 * 发放审核奖励
	 */
	public void grantVerifyReward(){
		UserCrowdRewardGrantResult grantResult;
		
		List<String> list = userCrowdTaskService.findUserTask4GrantVerifyReward();
		for (String utId : list) {
			try {
				//发放奖励
				grantResult = userCrowdTaskService.grantVerifyReward(utId);
				//通知
				UserCrowdTaskRewardNotifyThrd.notifyReward(grantResult);
			} catch (Exception e) {
				logger.error("发放报名审核奖励失败："+utId, e);
			}
		}
	}
	
	/**
	 * 发放审校奖励
	 */
	public void grantQcReward(){
		UserCrowdRewardGrantResult grantResult;
		
		List<Long> list = userCrowdTaskQcService.findQc4GrantQcReward();
		for (Long qcId : list) {
			try {
				//发放奖励
				grantResult = userCrowdTaskService.grantQcReward(qcId);
				//通知
				UserCrowdTaskRewardNotifyThrd.notifyReward(grantResult);
			} catch (Exception e) {
				logger.error("发放审校奖励失败："+qcId, e);
			}
		}
	}
}
