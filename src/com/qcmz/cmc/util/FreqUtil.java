package com.qcmz.cmc.util;

import java.util.ArrayList;
import java.util.List;

import com.qcmz.framework.firewall.frequency.FrequencyCache;
import com.qcmz.framework.firewall.frequency.FrequencyRule;

/**
 * 类说明：频率限制
 * 修改历史：
 */
public class FreqUtil {
	/**
	 * 获取能力
	 */
	public static final String BUSINESSID_CFG_FINDCAP = "cmc.TransConfigInterface.findCap";
	/**
	 * 获取代理帐户
	 */
	public static final String BUSINESSID_CFG_FINDPROXYACCOUNT = "cmc.TransConfigInterface.findProxyAccount";
	/**
	 * 获取语音代理帐户
	 */
	public static final String BUSINESSID_CFG_FINDSPEECHACCOUNT = "cmc.TransConfigInterface.findSpeechAccount";
	/**
	 * 获取百度翻译个人账户
	 */
	public static final String BUSINESSID_BAIDUTRANSACCOUNT_GETPERSONALACCOUNT = "cmc.BaiduTransAccount.getPersonalAccount";
	
	private static FrequencyCache frequencyCache = null;
	static{
		//一天（24*60*60=86400）
		List<FrequencyRule> rules = new ArrayList<FrequencyRule>();
		rules.add(new FrequencyRule(BUSINESSID_CFG_FINDCAP, 3600, 2));
		rules.add(new FrequencyRule(BUSINESSID_CFG_FINDPROXYACCOUNT, 3600, 2));
		rules.add(new FrequencyRule(BUSINESSID_CFG_FINDSPEECHACCOUNT, 3600, 2));
		rules.add(new FrequencyRule(BUSINESSID_BAIDUTRANSACCOUNT_GETPERSONALACCOUNT, 1500l, 1));
		frequencyCache = new FrequencyCache(rules, 60);
	}
	
	/**
	 * 检查是否可以使用
	 * @param visitorId 访问者标识
	 * @param businessId 业务标识，不能重复
	 * @param authAccount 接口帐户
	 * @return
	 */
	public static boolean check(String businessId, Object visitorId, String ip,  String authAccount){
		return frequencyCache.check(businessId, getVisitor(visitorId, ip));
	}
	
	/**
	 * 检查是否可以使用
	 * @param businessId 业务标识，不能重复
	 * @param visitorId 访问者标识
	 * @return
	 */
	public static boolean check(String businessId, String visitorId){
		return frequencyCache.check(businessId, visitorId);
	}
	
	/**
	 * 拼接用户标识
	 * @param visitorId
	 * @param ip
	 * @return
	 * 修改历史：
	 */
	private static String getVisitor(Object visitorId, String ip){
		StringBuilder sb = new StringBuilder();
		String vid = "";
		if(visitorId!=null){
			vid = visitorId.toString();
		}
		if(visitorId!=null){
			sb.append(vid);
		}
		sb.append("|");
		if(ip==null){
			sb.append("");
		}else{
			sb.append(ip);
		}
		return sb.toString();
	}
}
