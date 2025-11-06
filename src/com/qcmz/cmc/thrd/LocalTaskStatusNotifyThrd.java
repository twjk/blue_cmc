package com.qcmz.cmc.thrd;

import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.framework.thrd.AbstractThrd;
import com.qcmz.umc.ws.provide.locator.UserMsgWS;
import com.qcmz.umc.ws.provide.vo.UserMsgAddBean;

/**
 * 类说明：岗位状态变动通知用户
 */
public class LocalTaskStatusNotifyThrd extends AbstractThrd {
	/**
	 * 用户编号
	 */
	private Long userId;
	/**
	 * 岗位编号
	 */
	private Long taskId;
	/**
	 * 岗位
	 */
	private String title;
	/**
	 * 状态
	 */
	private Integer status;
	
	public LocalTaskStatusNotifyThrd(Long userId, Long taskId, String title, Integer status) {
		super();
		this.userId = userId;
		this.taskId = taskId;
		this.title = title;
		this.status = status;
	}

	/** 
	 * 
	 * 修改历史：
	 */
	@Override
	protected void work() {
		Long msgType = null;
		if(DictConstant.DICT_LOCALTASK_STATUS_ON.equals(status)){
			msgType = 75L;
		}else if(DictConstant.DICT_LOCALTASK_STATUS_VERIFYREFUSE.equals(status)){
			msgType = 76L;
		}
		
		if(msgType!=null){
			UserMsgAddBean bean = new UserMsgAddBean();
			bean.setMsgType(msgType);
			bean.getToUserIds().add(userId);
			bean.setMsg(title);
			bean.setIdentify(String.valueOf(taskId));
			
			UserMsgWS.addMsg(bean);
		}
	}
	
	/**
	 * 通知岗位状态
	 * @param userId
	 * @param title
	 * @param status
	 */
	public static void notifyStatus(Long userId, Long taskId, String title, Integer status){
		LocalTaskStatusNotifyThrd notifyThrd = new LocalTaskStatusNotifyThrd(userId, taskId, title, status);
		new Thread(notifyThrd).start();
	}
}
