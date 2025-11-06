package com.qcmz.cmc.thrd;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.qcmz.bdc.ws.provide.locator.MsgPushWS;
import com.qcmz.bdc.ws.provide.locator.SmsWS;
import com.qcmz.cmc.cache.DictMap;
import com.qcmz.cmc.cache.LangMap;
import com.qcmz.cmc.config.SystemConfig;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.util.CmcUtil;
import com.qcmz.framework.thrd.AbstractThrd;
import com.qcmz.framework.util.CheckUtil;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.MailUtil;
import com.qcmz.framework.util.MobileUtil;
import com.qcmz.framework.util.SpringUtil;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.srm.client.cache.GroupUserCache;
import com.qcmz.srm.ws.provide.vo.GroupUserBean;

/**
 * 类说明：用户取消订单通知客服
 * 修改历史：
 */
public class OrderCancelNotifyCsThrd extends AbstractThrd {
	
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

	public OrderCancelNotifyCsThrd(String orderId, Integer orderType, String from, String to) {
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
		String sound = null;
		String lang = CmcUtil.getLang(from, to);
		String langDesc = null;
		
		String orderTypeDesc = DictMap.getOrderTypeMean(orderType);
		
//		langDesc = CmcUtil.getLangDesc(from, to);
		LangMap langMap = (LangMap) SpringUtil.getBean("langMap");
		langDesc = langMap.getLangName4Text(to);
		
		String orderTail = StringUtil.right(orderId, 4);
		String notifyTitle = new StringBuilder().append(orderTypeDesc).append("订单[").append(orderTail).append("]用户取消：").append(langDesc).toString();
		String notifyContent = new StringBuilder()
			.append("订单类型：").append(orderTypeDesc)
			.append("<br/>订单编号：").append(orderId)
			.append("<br/>订单信息：").append(langDesc)
			.toString();
		
		//通知目标
		if(DictConstant.DICT_ORDERTYPE_TRANSTEXT.equals(orderType)){
			groupIdentify = "transTextOperator_"+lang;
		}else if(DictConstant.DICT_ORDERTYPE_TRANSPIC.equals(orderType)){
			groupIdentify = "transPicOperator_"+lang;
		}else if(DictConstant.DICT_ORDERTYPE_TRANSVIDEO.equals(orderType)){
			groupIdentify = "transVideoOperator_"+lang;
		}
		
		//操作员信息
		List<GroupUserBean> users = GroupUserCache.findGroupUser(groupIdentify);
		Set<String> mobiles = new HashSet<String>();
		Set<String> mails = new HashSet<String>();
		Set<Long> userIds = new HashSet<Long>();
		Date now = new Date();
		for (GroupUserBean user : users) {
			if(StringUtil.notBlankAndNull(user.getTimeZone())){
				if(!DateUtil.inTime4(DateUtil.formatTime4(now, user.getTimeZone()), SystemConfig.CS_WORKTIME)){
					continue;
				}
			}
			
			userIds.add(user.getUserId());
			if(MobileUtil.isMobile(user.getMobile())){
				mobiles.add(user.getMobile());
			}
			if(CheckUtil.isEmail(user.getEmail())){
				mails.add(user.getEmail());
			}
		}
		
		//无可通知的操作员，发邮件
		if(userIds.isEmpty()){
			try {
				MailUtil.sendHtmlMail(SystemConfig.CS_MAILS, null, notifyTitle, notifyContent);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return;
		}
		
		//推送
		identify = new StringBuilder(orderId).append("|").append(orderType).toString();
		notifyToType = MsgPushWS.PUSHTOTYPE_ALIAS;
		//为避免一个失败导致全部失败，所以拆分推送
		for (Long userId : userIds) {
			MsgPushWS.pushMsg(7004L, notifyToType, String.valueOf(userId), notifyTitle, identify, null, sound, null);
		}
		
		//视频口译订单发送短信
//		mobiles.clear();	//暂不发短信
		if(DictConstant.DICT_ORDERTYPE_TRANSVIDEO.equals(orderType)
				&& !mobiles.isEmpty()
				&& !"ca".equals(lang)){
			String sms = notifyTitle.replace("⇌", "<>");
			for (String mobile : mobiles) {
				SmsWS.sendSms(1002L, mobile, sms);
			}
		}
		
		//邮件
		if(!mails.isEmpty()){
			try {
				MailUtil.sendHtmlMail(mails, null, notifyTitle, notifyContent);
			} catch (Exception e) {	e.printStackTrace(); }
		}
	}
	
	/**
	 * 用户取消订单通知
	 * @param orderId
	 * @param orderType
	 * @param dealProcess
	 * @param from
	 * @param to
	 */
	public static void cancelOrderNotify(String orderId, Integer orderType, String dealProcess, String from, String to){
		OrderCancelNotifyCsThrd notifyThrd = new OrderCancelNotifyCsThrd(orderId, orderType, from, to);
		new Thread(notifyThrd).start();
	}
}
