package com.qcmz.cmc.thrd;

import java.util.ArrayList;
import java.util.List;

import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.vo.UserCrowdRewardGrantResult;
import com.qcmz.framework.thrd.AbstractThrd;
import com.qcmz.umc.ws.provide.locator.UserMsgWS;
import com.qcmz.umc.ws.provide.vo.UserMsgAddBean;

/**
 * 任务奖励到账通知
 */
public class UserCrowdTaskRewardNotifyThrd extends AbstractThrd {
	/**
	 * 发放奖励结果
	 */
	private UserCrowdRewardGrantResult grantResult;
	
	public UserCrowdTaskRewardNotifyThrd(UserCrowdRewardGrantResult grantResult) {
		super();
		this.grantResult = grantResult;
	}

	@Override
	protected void work() {
		Long msgType = null;
		
		if(DictConstant.DICT_USERCROWDTASK_REWARDCAT_TASK.equals(grantResult.getRewardCat())){
			msgType = 67L;
		}else if(DictConstant.DICT_USERCROWDTASK_REWARDCAT_VERIFY.equals(grantResult.getRewardCat())){
			msgType = 68L;
		}else if(DictConstant.DICT_USERCROWDTASK_REWARDCAT_QC.equals(grantResult.getRewardCat())){
			msgType = 69L;
		}
		
		List<Long> toUserIds = new ArrayList<Long>();
		toUserIds.add(grantResult.getUserId());
		
		if(msgType!=null){
			StringBuilder sbMsg	= new StringBuilder()					
				.append(grantResult.getTitle())
				.append("|").append(grantResult.getReward()).append(grantResult.getUnitName())
				;
			
			UserMsgAddBean bean = new UserMsgAddBean();
			bean.setMsgType(msgType);
			bean.setToUserIds(toUserIds);
			bean.setMsg(sbMsg.toString());
			bean.setIdentify(grantResult.getUtId());
			
			UserMsgWS.addMsg(bean);
		}
	}
	
	/**
	 * 奖励到账通知
	 * @param grantResult
	 */
	public static void notifyReward(UserCrowdRewardGrantResult grantResult){
		UserCrowdTaskRewardNotifyThrd notifyThrd = new UserCrowdTaskRewardNotifyThrd(grantResult);
		new Thread(notifyThrd).start();
	}
}
