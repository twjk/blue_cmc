package com.qcmz.cmc.thrd;

import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.framework.thrd.AbstractThrd;
import com.qcmz.umc.ws.provide.locator.UserMsgWS;
import com.qcmz.umc.ws.provide.vo.UserMsgAddBean;

/**
 * 类说明：通知用户公司认证结果
 */
public class LocalCompanyCertifyNotifyThrd extends AbstractThrd {
	/**
	 * 用户编号
	 */
	private Long userId;
	/**
	 * 公司名称
	 */
	private String companyName;
	/**
	 * 认证状态
	 */
	private Integer certifyStatus;
	
	public LocalCompanyCertifyNotifyThrd(Long userId, String companyName, Integer certifyStatus) {
		super();
		this.userId = userId;
		this.companyName = companyName;
		this.certifyStatus = certifyStatus;
	}

	/** 
	 * 
	 * 修改历史：
	 */
	@Override
	protected void work() {
		Long msgType = null;
		if(DictConstant.DICT_LOCACOMPANY_CERTIFYSTATUS_CERTIFIED.equals(certifyStatus)){
			msgType = 73L;
		}else if(DictConstant.DICT_LOCACOMPANY_CERTIFYSTATUS_FAIL.equals(certifyStatus)){
			msgType = 74L;
		}
		
		if(msgType!=null){
			UserMsgAddBean bean = new UserMsgAddBean();
			bean.setMsgType(msgType);
			bean.getToUserIds().add(userId);
			bean.setMsg(companyName);
			
			UserMsgWS.addMsg(bean);
		}
	}
	
	/**
	 * 通知公司认证结果
	 * @param userId
	 * @param companyName
	 * @param certifyStatus
	 */
	public static void notifyCertifyResult(Long userId, String companyName, Integer certifyStatus){
		LocalCompanyCertifyNotifyThrd notifyThrd = new LocalCompanyCertifyNotifyThrd(userId, companyName, certifyStatus);
		new Thread(notifyThrd).start();
	}
}
