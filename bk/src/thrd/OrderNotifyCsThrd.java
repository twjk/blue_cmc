package com.qcmz.cmc.thrd;

import java.util.ArrayList;
import java.util.List;

import com.qcmz.bdc.ws.provide.locator.MsgPushWS;
import com.qcmz.bdc.ws.provide.locator.SmsWS;
import com.qcmz.cmc.cache.DictMap;
import com.qcmz.cmc.config.SystemConfig;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.thrd.AbstractThrd;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.MailUtil;
import com.qcmz.framework.util.MobileUtil;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.srm.ws.provide.locator.UserMsgWS;

/**
 * 类说明：通知客服
 * 修改历史：
 */
public class OrderNotifyCsThrd extends AbstractThrd {
	
	/**
	 * 订单编号
	 */
	private String orderId;
	/**
	 * 订单类型
	 */
	private Integer orderType;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 操作员用户名
	 */
	private String operCd;

	/**
	 * 通知类型，1系统通知2新的图片翻译通知3用户留言通知
	 */
	private int notifyType;
	
	public OrderNotifyCsThrd(int notifyType, String content) {
		super();
		this.notifyType = notifyType;
		this.content = content;
	}

	public OrderNotifyCsThrd(int notifyType, String orderId, Integer orderType, String content, String operCd) {
		super();
		this.notifyType = notifyType;
		this.orderId = orderId;
		this.orderType = orderType;
		this.content = content;
		this.operCd = operCd;
	}
	
	/** 
	 * 
	 * 修改历史：
	 */
	@Override
	protected void work() {
		String notifyTitle = null;
		String notifyContent = null;
		String identify = null;
		String orderTypeDesc = null;
		
		boolean inTime = DateUtil.inTime4(SystemConfig.CS_WORKTIME);
		
		//推送
		switch (notifyType) {
		case 1:		//系统通知（x条翻译订单未完成处理）
			notifyTitle = content;
			notifyContent = content;
			
			//工作时间内推送和短信通知客服
			if(inTime){
				MsgPushWS.pushMsg(7002L, MsgPushWS.PUSHTOTYPE_TAG, "transOperator", notifyTitle);
				sendSms(notifyContent);
			}
			
			//邮件未处理明细，放外部处理
//			sendHtmlMail2Cs(notifyTitle, notifyContent);
			break;
		case 2:		//新的订单通知			
			orderTypeDesc = DictMap.getOrderTypeMean(orderType);
			notifyTitle = new StringBuilder().append("新的").append(orderTypeDesc).append("订单待处理：").append(content).toString();
			notifyContent = new StringBuilder()
				.append("订单类型：").append(orderTypeDesc)
				.append("<br/>订单编号：").append(orderId)
				.append("<br/>订单信息：").append(content)
				.toString();
			
			//工作时间内推送和短信通知客服
			if(inTime){
				identify = new StringBuilder(orderId).append("|").append(orderType).toString();
				
				String toType = MsgPushWS.PUSHTOTYPE_TAG;
				String to = "transOperator";
				String sound = null;
				//只给开发人员推送：王东海、李炳煜、薛世一、卢成龙、于延宇
//				toType = MsgPushWS.PUSHTOTYPE_ALIAS;
//				to = "17;26;28907;28910;28916";
				if(DictConstant.DICT_ORDERTYPE_TRANSTEXT.equals(orderType)){
					sound = "push_audio_text.mp3";
				}else if(DictConstant.DICT_ORDERTYPE_TRANSPIC.equals(orderType)){
					sound = "push_audio_pic.mp3";
				}else if(DictConstant.DICT_ORDERTYPE_TRANSVIDEO.equals(orderType)){
//					to = "transVideoOperator";
					sound = "push_audio_video.mp3";
				}
				
				MsgPushWS.pushMsg(7001L, toType, to, notifyTitle, identify, null, sound, null);
				
				sendSms(notifyContent);
			}
			
			//邮件客服
			sendHtmlMail2Cs(notifyTitle, notifyContent);
			break;
		case 3:		//用户留言通知
			identify = new StringBuilder(orderId).append("|").append(orderType).toString();
			orderTypeDesc = DictMap.getOrderTypeMean(orderType);
			notifyTitle = new StringBuilder().append("用户留言：").append(content).toString();
			notifyContent = new StringBuilder()
				.append("订单类型：").append(orderTypeDesc)
				.append("<br/>订单编号：").append(orderId)
				.append("<br/>用户留言：").append(content)
				.toString();
			
			//推送操作员
			if(StringUtil.notBlankAndNull(operCd)){
				List<String> toUserNames = new ArrayList<String>();
				toUserNames.add(operCd);
				UserMsgWS.addMsgToUserName(12L, toUserNames, content, identify);
			}
			
			//邮件客服
			sendHtmlMail2Cs(notifyTitle, notifyContent);
			break;
		}
	}
	
	/**
	 * 给客服发送短信
	 * @param content
	 */
	private void sendSms(String content){
		for (String mobile : SystemConfig.CS_MOBILES) {
			if(MobileUtil.isMobile(mobile)){
				SmsWS.sendSms(1002L, mobile, content);
			}
		}
	}
	
	private void sendHtmlMail2Cs(String subject, String msg){
		try {
			MailUtil.sendHtmlMail(SystemConfig.CS_MAILS, null, subject, msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 待处理订单通知：待处理、翻译订单
	 * @param orderId
	 * @param orderTypeDesc
	 * @param dealProcess
	 * @param langDesc
	 * @return
	 * 修改历史：
	 */
	public static void waitDealNotify(String orderId, Integer orderTpe, String dealProcess, String langDesc){
		if(DictConstants.DICT_DEALPROGRESS_WAITING.equals(dealProcess)){
			OrderNotifyCsThrd notifyThrd = new OrderNotifyCsThrd(2, orderId, orderTpe, langDesc, null);
			Thread thrd = new Thread(notifyThrd);
			thrd.start();
		}
	}
	
	/**
	 * 用户留言通知
	 * @param picId
	 * @param msg
	 * @return
	 * 修改历史：
	 */
	public static void msgNotify(String orderId, Integer orderType, String msg, String operCd){
		OrderNotifyCsThrd notifyThrd = new OrderNotifyCsThrd(3, orderId, orderType, msg, operCd);
		new Thread(notifyThrd).start();
	}
	
	/**
	 * 系统通知
	 * @param picId
	 * @param msg
	 * @return
	 * 修改历史：
	 */
	public static void systemNotify(String content){
		OrderNotifyCsThrd notifyThrd = new OrderNotifyCsThrd(1, content);
		new Thread(notifyThrd).start();
	}
}
