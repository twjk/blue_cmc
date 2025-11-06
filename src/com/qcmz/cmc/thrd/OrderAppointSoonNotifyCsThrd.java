package com.qcmz.cmc.thrd;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.qcmz.bdc.ws.provide.locator.MsgPushWS;
import com.qcmz.bdc.ws.provide.locator.SmsWS;
import com.qcmz.cmc.cache.DictMap;
import com.qcmz.cmc.cache.LangMap;
import com.qcmz.cmc.config.SystemConfig;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.entity.CmcROrder;
import com.qcmz.cmc.util.CmcUtil;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.thrd.AbstractThrd;
import com.qcmz.framework.util.CheckUtil;
import com.qcmz.framework.util.CollectionUtil;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.MailUtil;
import com.qcmz.framework.util.MobileUtil;
import com.qcmz.framework.util.SpringUtil;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.srm.client.cache.GroupUserCache;
import com.qcmz.srm.client.cache.UserCache;
import com.qcmz.srm.ws.provide.vo.GroupUserBean;
import com.qcmz.srm.ws.provide.vo.UserBaseBean;

/**
 * 类说明：即将开始的预约订单通知客服
 * 修改历史：
 */
public class OrderAppointSoonNotifyCsThrd extends AbstractThrd {
	
	/**
	 * 订单
	 */
	private CmcROrder order;
	
	public OrderAppointSoonNotifyCsThrd(CmcROrder order) {
		super();
		this.order = order;		
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
		String lang = CmcUtil.getLang(order.getFromlang(), order.getTolang());
		String langDesc = null;
		
		String orderTypeDesc = DictMap.getOrderTypeMean(order.getOrdertype());
		
		LangMap langMap = (LangMap) SpringUtil.getBean("langMap");
		langDesc = langMap.getLangName4Text(lang);
		
		String orderTail = StringUtil.right(order.getOrderid(), 4);
		String notifyTitle = new StringBuilder().append(orderTypeDesc).append("预约订单[").append(orderTail).append("]即将开始：").append(langDesc).toString();
		String notifyContent = new StringBuilder()
			.append("订单类型：").append(orderTypeDesc)
			.append("<br/>订单编号：").append(order.getOrderid())
			.append("<br/>订单信息：").append(langDesc)
			.append("<br/>开始时间：").append(DateUtil.formatDateTime(order.getWaittime()))			
			.toString();
		
		//通知目标及语音
		if(DictConstant.DICT_ORDERTYPE_TRANSTEXT.equals(order.getOrdertype())){
			sound = "push_audioappoint_text.mp3";
			groupIdentify = "transTextOperator_"+lang;
		}else if(DictConstant.DICT_ORDERTYPE_TRANSPIC.equals(order.getOrdertype())){
			sound = "push_audioappoint_pic.mp3";
			groupIdentify = "transPicOperator_"+lang;
		}else if(DictConstant.DICT_ORDERTYPE_TRANSVIDEO.equals(order.getOrdertype())){
			sound = "push_audioappoint_video.mp3";
			groupIdentify = "transVideoOperator_"+lang;
		}				
		
		//操作员信息
		Set<String> mobiles = new LinkedHashSet<String>();
		Set<String> mails = new LinkedHashSet<String>();
		Set<Long> fulltimeUserIds = new LinkedHashSet<Long>();	//全职操作员
		Set<Long> parttimeUserIds = new LinkedHashSet<Long>();	//兼职操作员
		Date now = new Date();
		
		List<GroupUserBean> users = new ArrayList<GroupUserBean>();
		if(StringUtil.notBlankAndNull(order.getOpercd())){
			UserBaseBean userBean = UserCache.getUser(order.getOpercd());
			if(userBean!=null){
				GroupUserBean userGroupBean = new GroupUserBean();
				userGroupBean.setUserId(userBean.getUserId());
				userGroupBean.setUserName(userBean.getUserName());
				userGroupBean.setName(userBean.getName());
				userGroupBean.setMobile(userBean.getMobile());
				userGroupBean.setEmail(userBean.getEmail());
				userGroupBean.setTimeZone(userBean.getTimeZone());
				userGroupBean.setTransStatus(userBean.getTransStatus());
				userGroupBean.setWorktimeType(userBean.getWorktimeType());
				users.add(userGroupBean);
			}
		}
		if(users.isEmpty()){
			users = GroupUserCache.findGroupUser(groupIdentify);
		}
		
		for (GroupUserBean user : users) {
			if(StringUtil.notBlankAndNull(user.getTimeZone())){
				if(!DateUtil.inTime4(DateUtil.formatTime4(now, user.getTimeZone()), SystemConfig.CS_WORKTIME)){
					continue;
				}
			}
			if(DictConstants.DICT_WORKTIMETYPE_FULLTIME.equals(user.getWorktimeType())){
				fulltimeUserIds.add(user.getUserId());
			}else if(DictConstants.DICT_WORKTIMETYPE_PARTTIME.equals(user.getWorktimeType())){
				parttimeUserIds.add(user.getUserId());
			}
			if(MobileUtil.isMobile(user.getMobile())){
				mobiles.add(user.getMobile());
			}
			if(CheckUtil.isEmail(user.getEmail())){
				mails.add(user.getEmail());
			}
		}
		
		//通知
		//无可通知的操作员，邮件
		if(fulltimeUserIds.isEmpty() && parttimeUserIds.isEmpty()){
			try {
				MailUtil.sendHtmlMail(SystemConfig.CS_MAILS, null, notifyTitle, notifyContent);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return;
		}
				
		//推送
		notifyToType = MsgPushWS.PUSHTOTYPE_ALIAS;
		identify = new StringBuilder(order.getOrderid()).append("|").append(order.getOrdertype()).toString();
		if(!fulltimeUserIds.isEmpty()){
			MsgPushWS.pushMsg(7006L, notifyToType, CollectionUtil.toString(fulltimeUserIds, ";"), notifyTitle, identify, null, sound, null);
		}
		if(!parttimeUserIds.isEmpty()){
			MsgPushWS.pushMsg(7006L, notifyToType, CollectionUtil.toString(parttimeUserIds, ";"), notifyTitle, identify, null, sound, null);
		}
		
		//视频口译订单发送短信
		if(DictConstant.DICT_ORDERTYPE_TRANSVIDEO.equals(order.getOrdertype())
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
	 * 预约订单即将开始通知
	 * @param order
	 */
	public static void notify(CmcROrder order){
		if(DictConstants.DICT_DEALPROGRESS_WAITING.equals(order.getDealprogress())){
			OrderAppointSoonNotifyCsThrd notifyThrd = new OrderAppointSoonNotifyCsThrd(order);
			new Thread(notifyThrd).start();
		}
	}
}
