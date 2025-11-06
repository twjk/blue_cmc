package com.qcmz.cmc.thrd;

import java.util.ArrayList;
import java.util.List;

import com.qcmz.cmc.cache.ParamMap;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.framework.thrd.AbstractThrd;
import com.qcmz.framework.util.SpringUtil;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.umc.ws.provide.locator.UserMsgWS;
import com.qcmz.umc.ws.provide.vo.UserMsgAddBean;

/**
 * 任务报名审核通知
 */
public class UserCrowdTaskVerifyNotifyThrd extends AbstractThrd {
	/**
	 * 业务类型
	 */
	private String serviceType;
	/**
	 * 用户编号
	 */
	private Long userId;
	/**
	 * 用户任务编号
	 */
	private String utId;
	/**
	 * 用户任务状态
	 */
	private Integer status;
	/**
	 * 任务名称
	 */
	private String title;
	/**
	 * 原因
	 */
	private String reason;
	
	public UserCrowdTaskVerifyNotifyThrd(String serviceType, Long userId, String utId, Integer status, String title, String reason) {
		super();
		this.serviceType = serviceType;
		this.userId = userId;
		this.utId = utId;
		this.status = status;
		this.title = title;
		this.reason = reason;
	}

	@Override
	protected void work() {
		Long msgType = null;
		String pushExtra = null;
		StringBuilder sbMsg = new StringBuilder();
		
		String utIdTail = StringUtil.right(utId, 4);
		
		List<Long> toUserIds = new ArrayList<Long>();
		if(DictConstant.DICT_USERCROWDTASK_STATUS_APPLY.equals(status)){
			msgType = 63L;
			toUserIds.addAll(((ParamMap) SpringUtil.getBean("paramMap")).findCrowdTaskVerifyUserId(serviceType));
			sbMsg.append(utIdTail).append("|").append(title);
		}else if(DictConstant.DICT_USERCROWDTASK_STATUS_ACCEPTAPPLY.equals(status)
				|| DictConstant.DICT_USERCROWDTASK_STATUS_ANSWERING.equals(status)){
			msgType = 64L;
			toUserIds.add(userId);
			sbMsg.append(title);
		}else if(DictConstant.DICT_USERCROWDTASK_STATUS_REJECTAPPLY.equals(status)){
			msgType = 65L;
			pushExtra = "reason="+reason;
			toUserIds.add(userId);
			sbMsg.append(title);
		}
		
		if(msgType!=null){
			UserMsgAddBean bean = new UserMsgAddBean();
			bean.setMsgType(msgType);
			bean.setToUserIds(toUserIds);
			bean.setMsg(sbMsg.toString());
			bean.setIdentify(utId);
			bean.setExtra(pushExtra);
			
			UserMsgWS.addMsg(bean);
		}
	}
	
	public static void notify(String serviceType, Long userId, String utId, Integer status, String title, String reason){
		UserCrowdTaskVerifyNotifyThrd notifyThrd = new UserCrowdTaskVerifyNotifyThrd(serviceType, userId, utId, status, title, reason);
		new Thread(notifyThrd).start();
	}
}
