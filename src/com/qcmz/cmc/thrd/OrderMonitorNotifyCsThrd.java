package com.qcmz.cmc.thrd;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.qcmz.bdc.ws.provide.locator.MsgPushWS;
import com.qcmz.cmc.config.SystemConfig;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.thrd.AbstractThrd;
import com.qcmz.framework.util.CollectionUtil;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.srm.client.cache.GroupUserCache;
import com.qcmz.srm.ws.provide.vo.GroupUserBean;

/**
 * 类说明：订单监控通知客服
 * 修改历史：
 */
public class OrderMonitorNotifyCsThrd extends AbstractThrd {
	/**
	 * 订单类型
	 */
	private Integer orderType;
	/**
	 * 语言
	 */
	private String lang;
	/**
	 * 内容
	 */
	private String content;
	
	public OrderMonitorNotifyCsThrd(Integer orderType, String content, String lang) {
		super();
		this.orderType = orderType;
		this.lang = lang;
		this.content = content;
	}

	/** 
	 * 
	 * 修改历史：
	 */
	@Override
	protected void work() {
		String notifyTitle = content;
		String notifyToType = MsgPushWS.PUSHTOTYPE_ALIAS;
		String groupIdentify = null;
		
		//推送目标
		if(DictConstant.DICT_ORDERTYPE_TRANSTEXT.equals(orderType)){
			groupIdentify = "transTextOperator_"+lang;
		}else if(DictConstant.DICT_ORDERTYPE_TRANSPIC.equals(orderType)){
			groupIdentify = "transPicOperator_"+lang;
		}else if(DictConstant.DICT_ORDERTYPE_TRANSVIDEO.equals(orderType)){
			groupIdentify = "transVideoOperator_"+lang;
		}
		
		//操作员信息
		List<GroupUserBean> users = GroupUserCache.findGroupUser(groupIdentify);
		Set<Long> fulltimeUserIds = new LinkedHashSet<Long>();	//全职操作员
		Set<Long> parttimeUserIds = new LinkedHashSet<Long>();	//兼职操作员
		Date now = new Date();
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
		}
		
		//推送
		if(!fulltimeUserIds.isEmpty()){
			MsgPushWS.pushMsg(7002L, notifyToType, CollectionUtil.toString(fulltimeUserIds, ";"), notifyTitle);
		}
		if(!parttimeUserIds.isEmpty()){
			MsgPushWS.pushMsg(7002L, notifyToType, CollectionUtil.toString(parttimeUserIds, ";"), notifyTitle);
		}
	}
	
	/**
	 * 订单监控通知
	 * @param orderType not null
	 * @param content not null
	 * @param lang
	 */
	public static void monitorNotify(Integer orderType, String content, String lang){
		OrderMonitorNotifyCsThrd notifyThrd = new OrderMonitorNotifyCsThrd(orderType, content, lang);
		new Thread(notifyThrd).start();
	}
}
