package com.qcmz.cmc.thrd;

import com.qcmz.cmc.cache.DictMap;
import com.qcmz.cmc.cache.LangMap;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.entity.CmcROrder;
import com.qcmz.cmc.util.CmcUtil;
import com.qcmz.cmc.util.SystemUtil;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.thrd.AbstractThrd;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.SpringUtil;
import com.qcmz.framework.util.StringUtil;

/**
 * 类说明：新单通知客服
 * 修改历史：
 */
public class OrderNewNotifyCsThrd extends AbstractThrd {
	
	/**
	 * 订单
	 */
	private CmcROrder order;
	

	public OrderNewNotifyCsThrd(CmcROrder order) {
		super();
		this.order = order;
	}
	
	/** 
	 * 
	 * 修改历史：
	 */
	@Override
	protected void work() {
		Long pushTypeId = null;
		String identify = null;
		String groupIdentify = null;
		String pushSound = null;
		String lang = CmcUtil.getLang(order.getFromlang(), order.getTolang());
		String langDesc = null;
		Long smsTypeId = null;
		String smsContent = null;
		
		String orderTypeDesc = DictMap.getOrderTypeMean(order.getOrdertype());
		String appoint = null;
		if(SystemConstants.STATUS_ON.equals(order.getAppointflag())){
			appoint = "预约";
		}else{
			appoint = "";
		}
		
		LangMap langMap = (LangMap) SpringUtil.getBean("langMap");
		langDesc = langMap.getLangName4Text(lang);
		
		String orderTail = StringUtil.right(order.getOrderid(), 4);
		
		StringBuilder sbNotifyTitle = new StringBuilder("新").append(orderTypeDesc).append(appoint).append("订单[").append(orderTail).append("]待处理");
		if(StringUtil.notBlankAndNull(langDesc)){
			sbNotifyTitle.append("：").append(langDesc);
		}
		String notifyTitle = sbNotifyTitle.toString();
		
		StringBuilder sbNotifyContent = new StringBuilder()
			.append("订单类型：").append(orderTypeDesc)
			.append("<br/>订单编号：").append(order.getOrderid())
			;
		if(StringUtil.notBlankAndNull(langDesc)){
			sbNotifyContent.append("<br/>订单信息：").append(langDesc);
		}
		if(order.getAppointtime()!=null){
			sbNotifyContent.append("<br/>预约时间：").append(DateUtil.formatDateTime(order.getAppointtime()));
		}
		String notifyContent = sbNotifyContent.toString();
		
		
		//通知目标及语音
		pushTypeId = 7001L;
		if(DictConstant.DICT_ORDERTYPE_TRANSTEXT.equals(order.getOrdertype())){
			pushSound = "push_audio_text.mp3";
			groupIdentify = "transTextOperator_"+lang;
		}else if(DictConstant.DICT_ORDERTYPE_TRANSPIC.equals(order.getOrdertype())){
			pushSound = "push_audio_pic.mp3";
			groupIdentify = "transPicOperator_"+lang;
		}else if(DictConstant.DICT_ORDERTYPE_TRANSVIDEO.equals(order.getOrdertype())){
			pushSound = "push_audio_video.mp3";
			groupIdentify = "transVideoOperator_"+lang;
		}else if(DictConstant.DICT_ORDERTYPE_HUMANDUB.equals(order.getOrdertype())){
			groupIdentify = "humanDubOperator";
		}
		
		identify = new StringBuilder(order.getOrderid()).append("|").append(order.getOrdertype()).toString();
		
		if(DictConstant.DICT_ORDERTYPE_TRANSVIDEO.equals(order.getOrdertype())
				&& !"ca".equals(lang)){
			smsTypeId = 1002L;
			smsContent = notifyTitle.replace("⇌", "<>");
		}
		
		SystemUtil.notifyCsByAlias(groupIdentify, notifyTitle, notifyContent, identify, pushTypeId, pushSound, smsTypeId, smsContent);
	}
	
	/**
	 * 新单通知
	 * @param order
	 */
	public static void newOrderNotify(CmcROrder order){
		if(DictConstants.DICT_DEALPROGRESS_WAITING.equals(order.getDealprogress())){
			new Thread(new OrderNewNotifyCsThrd(order)).start();
		}
	}
}
