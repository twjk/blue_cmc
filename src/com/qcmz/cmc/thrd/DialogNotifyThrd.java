package com.qcmz.cmc.thrd;

import java.util.ArrayList;
import java.util.List;

import com.qcmz.framework.thrd.AbstractThrd;
import com.qcmz.umc.ws.provide.locator.UserMsgWS;
import com.qcmz.umc.ws.provide.vo.UserMsgAddBean;

/**
 * 类说明：对话通知用户
 */
public class DialogNotifyThrd extends AbstractThrd {
	/**
	 * 用户编号
	 */
	private Long userId;
	/**
	 * 对话编号
	 */
	private Long dialogId;

	public DialogNotifyThrd(Long userId, Long dialogId) {
		super();
		this.userId = userId;
		this.dialogId = dialogId;
	}

	/** 
	 * 
	 * 修改历史：
	 */
	@Override
	protected void work() {
		List<Long> toUserIds = new ArrayList<Long>();
		toUserIds.add(userId);
		
		UserMsgAddBean bean = new UserMsgAddBean();
		bean.setMsgType(72L);
		bean.setToUserIds(toUserIds);
		bean.setIdentify(String.valueOf(dialogId));
		
		UserMsgWS.addMsg(bean);
	}
	
	/**
	 * 客服回复通知 
	 */
	public static void notifyMsg(Long userId, Long dialogId){
		DialogNotifyThrd notifyThrd = new DialogNotifyThrd(userId, dialogId);
		Thread thrd = new Thread(notifyThrd);
		thrd.start();
	}
}
