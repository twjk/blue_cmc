package com.qcmz.cmc.thrd;

import java.util.ArrayList;
import java.util.List;

import com.qcmz.cmc.cache.ParamMap;
import com.qcmz.framework.thrd.AbstractThrd;
import com.qcmz.framework.util.SpringUtil;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.umc.ws.provide.locator.UserMsgWS;
import com.qcmz.umc.ws.provide.vo.UserMsgAddBean;

/**
 * 任务审校通知
 */
public class UserCrowdTaskQcNotifyThrd extends AbstractThrd {
	/**
	 * 业务类型
	 */
	private String serviceType;
	/**
	 * 用户任务编号
	 */
	private String utId;
	/**
	 * 任务名称
	 */
	private String title;

	
	public UserCrowdTaskQcNotifyThrd(String serviceType, String utId, String title) {
		super();
		this.serviceType = serviceType;
		this.utId = utId;
		this.title = title;
	}

	@Override
	protected void work() {
		Long msgType = null;
		String pushExtra = null;
		StringBuilder sbMsg = new StringBuilder();
		
		String utIdTail = StringUtil.right(utId, 4);
		
		List<Long> toUserIds = new ArrayList<Long>();
		msgType = 66L;
		toUserIds.addAll(((ParamMap) SpringUtil.getBean("paramMap")).findCrowdTaskQcUserId(serviceType));
		sbMsg.append(utIdTail).append("|").append(title);
		
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
	
	public static void notify(String serviceType, String utId, String title){
		UserCrowdTaskQcNotifyThrd notifyThrd = new UserCrowdTaskQcNotifyThrd(serviceType, utId, title);
		new Thread(notifyThrd).start();
	}
}
