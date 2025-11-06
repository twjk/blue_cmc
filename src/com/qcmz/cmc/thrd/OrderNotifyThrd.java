package com.qcmz.cmc.thrd;

import java.util.ArrayList;
import java.util.List;

import com.qcmz.bdc.ws.provide.locator.MsgPushWS;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.entity.CmcROrder;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.thrd.AbstractThrd;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.umc.ws.provide.locator.UserMsgWS;
import com.qcmz.umc.ws.provide.vo.UserMsgAddBean;

/**
 * 类说明：通知用户
 * 修改历史：
 */
public class OrderNotifyThrd extends AbstractThrd {
	/**
	 * 业务类型
	 */
	private String serviceType;
	/**
	 * 用户编号
	 */
	private Long userId;
	/**
	 * 订单编号
	 */
	private String orderId;
	/**
	 * 订单类型
	 */
	private Integer orderType;
	/**
	 * 处理状态
	 */
	private String dealStatus;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 取消原因
	 */
	private String reason;
	
	/**
	 * 通知类型，1订单通知2客服留言通知
	 */
	private int notifyType;
		
	public OrderNotifyThrd(int notifyType, Long userId, String orderId, Integer orderType, String dealStatus, String content, String reason) {
		super();
		this.notifyType = notifyType;
		this.userId = userId;
		this.orderId = orderId;
		this.orderType = orderType;
		this.content = content;
		this.dealStatus = dealStatus;
		this.reason = reason;
	}

	public OrderNotifyThrd(int notifyType, String serviceType, Long userId, String orderId, String content) {
		super();
		this.notifyType = notifyType;
		this.serviceType = serviceType;
		this.userId = userId;
		this.orderId = orderId;
		this.content = content;		
	} 
	
	/** 
	 * 
	 * 修改历史：
	 */
	@Override
	protected void work() {
		String pushExtra = null;
		switch (notifyType) {
		case 1:		//订单通知
			Long msgType = null;
			if(DictConstant.DICT_ORDER_DEALSTATUS_PROCESSING.equals(dealStatus)){
				//开始翻译
				if(DictConstant.DICT_ORDERTYPE_TRANSPIC.equals(orderType)){
					msgType = 41L;
				}else if(DictConstant.DICT_ORDERTYPE_TRANSTEXT.equals(orderType)){
					msgType = 46L;
				}else if(DictConstant.DICT_ORDERTYPE_TRANSVIDEO.equals(orderType)){
					msgType = 55L;
				}
			}else if(DictConstant.DICT_ORDER_DEALSTATUS_FINISH.equals(dealStatus)
					|| DictConstant.DICT_ORDER_DEALSTATUS_TRANSED.equals(dealStatus)){
				//完成翻译
				if(DictConstant.DICT_ORDERTYPE_TRANSPIC.equals(orderType)){
					msgType = 42L;
				}else if(DictConstant.DICT_ORDERTYPE_TRANSTEXT.equals(orderType)){
					msgType = 47L;
				}else if(DictConstant.DICT_ORDERTYPE_TRANSVIDEO.equals(orderType)){
					msgType = 56L;
				}else if(DictConstant.DICT_ORDERTYPE_PACKAGE.equals(orderType)){
					msgType = 59L;
				}else if(DictConstant.DICT_ORDERTYPE_TRANSCOMBO.equals(orderType)){
					msgType = 60L;
				}
			}else if(DictConstant.DICT_ORDER_DEALSTATUS_CANCEL.equals(dealStatus)){
				//取消翻译
				if(DictConstant.DICT_ORDERTYPE_TRANSPIC.equals(orderType)){
					msgType = 43L;
				}else if(DictConstant.DICT_ORDERTYPE_TRANSTEXT.equals(orderType)){
					msgType = 48L;
				}else if(DictConstant.DICT_ORDERTYPE_TRANSVIDEO.equals(orderType)){
					msgType = 57L;
				}
				pushExtra = "reason="+reason;
			}else if(DictConstant.DICT_ORDER_DEALSTATUS_REFUNDED.equals(dealStatus)){
				//完成退款
				if(DictConstant.DICT_ORDERTYPE_TRANSPIC.equals(orderType)){
					msgType = 44L;
				}else if(DictConstant.DICT_ORDERTYPE_TRANSTEXT.equals(orderType)){
					msgType = 49L;
				}else if(DictConstant.DICT_ORDERTYPE_TRANSVIDEO.equals(orderType)){
					msgType = 58L;
				}
			}else if(DictConstant.DICT_ORDER_DEALSTATUS_SUB.equals(dealStatus)){
				msgType = 45L;	//成功订阅
			}
			
			if(msgType!=null){
				List<Long> toUserIds = new ArrayList<Long>();
				toUserIds.add(userId);
				
				UserMsgAddBean bean = new UserMsgAddBean();
				bean.setMsgType(msgType);
				bean.setToUserIds(toUserIds);
				bean.setMsg(content);
				bean.setIdentify(orderId);
				bean.setExtra(pushExtra);
				
				UserMsgWS.addMsg(bean);
			}
			break;
		case 2:		//客服留言通知
			Long pushTypeId = null;
			if(DictConstants.DICT_SERVICETYPE_VOICETRANS.equals(serviceType)){
				pushTypeId = 1013L;
			}else if(DictConstants.DICT_SERVICETYPE_TOMATO.equals(serviceType)){
				pushTypeId = 12013L;
			}else{
				break;
			}
			
			MsgPushWS.pushMsg(pushTypeId, MsgPushWS.PUSHTOTYPE_ALIAS, String.valueOf(userId), content, orderId, null);
			break;
		}
	}
	
	/**
	 * 订单状态变更通知
	 * @param order
	 * @param transModel
	 * @param content
	 */
	public static void notifyStatus(CmcROrder order){
		notifyStatus(order, null, null);
	}
	
	/**
	 * 订单状态变更通知
	 * @param order
	 * @param transModel
	 * @param content
	 * @param reason 取消原因
	 */
	public static void notifyStatus(CmcROrder order, String content, String reason){
		if(StringUtil.isBlankOrNull(content)){
			if(order.getWaittime()!=null){
				content = DateUtil.formatDateTime4(order.getWaittime());
			}else{
				content = DateUtil.formatDateTime4(order.getCreatetime());
			}
		}
		
		OrderNotifyThrd notifyThrd = new OrderNotifyThrd(1, order.getUserid(), order.getOrderid(), order.getOrdertype(), order.getDealstatus(), content, reason);
		new Thread(notifyThrd).start();
	}
	
	/**
	 * 客服留言通知 
	 * 修改历史：
	 */
	public static void notifyMsg(String serviceType, Long userId, String orderId, String content){
		OrderNotifyThrd notifyThrd = new OrderNotifyThrd(2, serviceType, userId, orderId, content);
		Thread thrd = new Thread(notifyThrd);
		thrd.start();
	}
}
