package com.qcmz.cmc.thrd;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.qcmz.bdc.ws.provide.locator.MsgPushWS;
import com.qcmz.cmc.cache.DictMap;
import com.qcmz.cmc.cache.LangMap;
import com.qcmz.cmc.config.SystemConfig;
import com.qcmz.cmc.util.CmcUtil;
import com.qcmz.framework.thrd.AbstractThrd;
import com.qcmz.framework.util.CollectionUtil;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.MailUtil;
import com.qcmz.framework.util.SpringUtil;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.srm.client.cache.GroupUserCache;
import com.qcmz.srm.ws.provide.vo.GroupUserBean;

/**
 * 类说明：新单通知客服
 * 修改历史：
 */
public class OrderCheckNotifyCsThrd extends AbstractThrd {
	
	/**
	 * 订单编号
	 */
	private String orderId;
	/**
	 * 订单类型
	 */
	private Integer orderType;
	/**
	 * 源语言
	 */
	private String from;
	/**
	 * 目标语言
	 */
	private String to;

	public OrderCheckNotifyCsThrd(String orderId, Integer orderType, String from, String to) {
		super();
		this.orderId = orderId;
		this.orderType = orderType;
		this.from = from;
		this.to = to;
	}
	
	/** 
	 * 
	 * 修改历史：
	 */
	@Override
	protected void work() {
		String identify = null;
		String notifyToType = null;
		String groupIdentify = null;
		String lang = CmcUtil.getLang(from, to);
		String langDesc = null;
		
		String orderTypeDesc = DictMap.getOrderTypeMean(orderType);
		
		LangMap langMap = (LangMap) SpringUtil.getBean("langMap");
		langDesc = langMap.getLangName4Text(lang);
		
		String orderTail = StringUtil.right(orderId, 4);
		String notifyTitle = new StringBuilder().append("新").append(orderTypeDesc).append("订单[").append(orderTail).append("]待校对：").append(langDesc).toString();
		String notifyContent = new StringBuilder()
			.append("订单类型：").append(orderTypeDesc)
			.append("<br/>订单编号：").append(orderId)
			.append("<br/>订单信息：").append(langDesc)
			.toString();
		
		//通知目标
		groupIdentify = "transCheckOperator_"+lang;
		
		//操作员信息
		List<GroupUserBean> users = GroupUserCache.findGroupUser(groupIdentify);
		Set<Long> userIds = new HashSet<Long>();
		Date now = new Date();
		for (GroupUserBean user : users) {
			if(StringUtil.notBlankAndNull(user.getTimeZone())){
				if(!DateUtil.inTime4(DateUtil.formatTime4(now, user.getTimeZone()), SystemConfig.CS_WORKTIME)){
					continue;
				}
			}
			userIds.add(user.getUserId());
		}
		
		//通知
		//无可通知的操作员，邮件
		if(userIds.isEmpty()){
			try {
				MailUtil.sendHtmlMail(SystemConfig.CS_MAILS, null, notifyTitle, notifyContent);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return;
		}
				
		//推送
		notifyToType = MsgPushWS.PUSHTOTYPE_ALIAS;
		identify = new StringBuilder(orderId).append("|").append(orderType).toString();
		MsgPushWS.pushMsg(7005L, notifyToType, CollectionUtil.toString(userIds, ";"), notifyTitle, identify, null);
	}
	
	/**
	 * 校对通知
	 * @param orderId
	 * @param orderTpe
	 * @param from
	 * @param to
	 */
	public static void newCheckNotify(String orderId, Integer orderTpe, String from, String to){
		OrderCheckNotifyCsThrd notifyThrd = new OrderCheckNotifyCsThrd(orderId, orderTpe, from, to);
		new Thread(notifyThrd).start();
	}
}
