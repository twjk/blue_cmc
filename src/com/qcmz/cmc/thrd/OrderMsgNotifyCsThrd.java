package com.qcmz.cmc.thrd;

import java.util.ArrayList;
import java.util.List;

import com.qcmz.cmc.cache.DictMap;
import com.qcmz.cmc.config.SystemConfig;
import com.qcmz.framework.thrd.AbstractThrd;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.MailUtil;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.srm.ws.provide.locator.UserMsgWS;

/**
 * 类说明：用户留言通知客服
 * 修改历史：
 */
public class OrderMsgNotifyCsThrd extends AbstractThrd {
	
	/**
	 * 订单编号
	 */
	private String orderId;
	/**
	 * 订单类型
	 */
	private Integer orderType;
	/**
	 * 留言内容
	 */
	private String content;
	/**
	 * 操作员用户名
	 */
	private String operCd;
	
	public OrderMsgNotifyCsThrd(String orderId, Integer orderType, String content, String operCd) {
		super();
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
		String notifyTitle = new StringBuilder().append("用户留言：").append(content).toString();
		String notifyContent = new StringBuilder()
		.append("订单类型：").append(DictMap.getOrderTypeMean(orderType))
		.append("<br/>订单编号：").append(orderId)
		.append("<br/>用户留言：").append(content)
		.toString();
		
		//工作时间内推送通知操作员
		if(DateUtil.inTime4(SystemConfig.CS_WORKTIME)
				&& StringUtil.notBlankAndNull(operCd)){
			List<String> toUserNames = new ArrayList<String>();
			toUserNames.add(operCd);
			String identify = new StringBuilder(orderId).append("|").append(orderType).toString();
			UserMsgWS.addMsgToUserName(12L, toUserNames, content, identify);
		}
		
		//邮件客服
		try {
			MailUtil.sendHtmlMail(SystemConfig.CS_MAILS, null, notifyTitle, notifyContent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 用户留言通知
	 * @param orderId
	 * @param orderType
	 * @param msg
	 * @param operCd
	 */
	public static void msgNotify(String orderId, Integer orderType, String msg, String operCd){
		OrderMsgNotifyCsThrd notifyThrd = new OrderMsgNotifyCsThrd(orderId, orderType, msg, operCd);
		new Thread(notifyThrd).start();
	}
}
