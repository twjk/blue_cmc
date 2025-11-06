package com.qcmz.cmc.util;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.qcmz.bdc.ws.provide.cache.CityMap;
import com.qcmz.bdc.ws.provide.cache.CountryMap;
import com.qcmz.bdc.ws.provide.cache.RegionMap;
import com.qcmz.bdc.ws.provide.locator.MsgPushWS;
import com.qcmz.bdc.ws.provide.locator.SmsWS;
import com.qcmz.bdc.ws.provide.vo.CityBean;
import com.qcmz.bdc.ws.provide.vo.CountryBean;
import com.qcmz.bdc.ws.provide.vo.RegionBean;
import com.qcmz.cmc.config.SystemConfig;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.geography.GeographyBean;
import com.qcmz.framework.util.CheckUtil;
import com.qcmz.framework.util.CollectionUtil;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.MailUtil;
import com.qcmz.framework.util.MobileUtil;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.srm.client.cache.GroupUserCache;
import com.qcmz.srm.ws.provide.vo.GroupUserBean;

public class SystemUtil {
	/**
	 * 别名通知客服
	 * @param groupIdentify
	 * @param notifyTitle
	 * @param notifyContent
	 * @param identify
	 * @param pushTypeId
	 * @param pushSound
	 * @param smsTypeId
	 * @param smsContent
	 */
	public static void notifyCsByAlias(String groupIdentify, String notifyTitle, String notifyContent, String identify
			, Long pushTypeId, String pushSound
			, Long smsTypeId, String smsContent){
		//操作员信息
		List<GroupUserBean> users = GroupUserCache.findGroupUser(groupIdentify);
		Set<String> mobiles = new LinkedHashSet<String>();
		Set<String> mails = new LinkedHashSet<String>();
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
		String notifyToType = MsgPushWS.PUSHTOTYPE_ALIAS;
		if(!fulltimeUserIds.isEmpty()){
			MsgPushWS.pushMsg(pushTypeId, notifyToType, CollectionUtil.toString(fulltimeUserIds, ";"), notifyTitle, identify, null, pushSound, null);
		}
		if(!parttimeUserIds.isEmpty()){
			MsgPushWS.pushMsg(pushTypeId, notifyToType, CollectionUtil.toString(parttimeUserIds, ";"), notifyTitle, identify, null, pushSound, null);
		}
		
		//发送短信
		if(!mobiles.isEmpty() && smsTypeId!=null){
			if(StringUtil.isBlankOrNull(smsContent)){
				smsContent = notifyTitle;
			}
			for (String mobile : mobiles) {
				SmsWS.sendSms(smsTypeId, mobile, smsContent);
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
	 * 获取完整的地理信息
	 * countryCode和countryName必须有一项不为空
	 * cityId和cityName必须有一项不为空
	 * regionId和regionName可以都为空
	 * @param countryCode
	 * @param countryName
	 * @param cityId
	 * @param cityName
	 * @param regionId
	 * @param regionName
	 * @return 不匹配返回null
	 */
	public static GeographyBean getGeography(String countryCode, String countryName, Long cityId, String cityName, Long regionId, String regionName){
		GeographyBean result = new GeographyBean();
		
		//国家
		CountryBean countryBean = null;
		if(StringUtil.notBlankAndNull(countryCode)){
			countryBean = CountryMap.getBean(countryCode);
		}else if(StringUtil.notBlankAndNull(countryName)){
			countryBean = CountryMap.getBeanByName(countryName);
		}
		if(countryBean==null) return null;
		result.setCountryCode(countryBean.getCountryCode());
		result.setCountryName(countryBean.getCountryName());
		
		//城市
		CityBean cityBean = null;
		if(cityId!=null){
			cityBean = CityMap.getBean(cityId);
		}else if(StringUtil.notBlankAndNull(cityName)){
			cityBean = CityMap.getBean(result.getCountryCode(), cityName);
		}
		if(cityBean==null) return null;
		result.setCityId(cityBean.getCityId());
		result.setCityName(cityBean.getCityName());
		result.setTelCode(cityBean.getTelCode());
		
		//区县
		RegionBean regionBean = null;
		if(regionId!=null){
			regionBean = RegionMap.getBean(regionId);
			if(regionBean==null) return null;
			result.setRegionId(regionBean.getRegionId());
			result.setRegionName(regionBean.getRegionName());
		}else if(StringUtil.notBlankAndNull(regionName)){
			regionBean = RegionMap.getBean(result.getCityId(), regionName);
			if(regionBean==null) return null;
			result.setRegionId(regionBean.getRegionId());
			result.setRegionName(regionBean.getRegionName());
		}
		
		return result;
	}
	
	/**
	 * 获取完整的地理信息
	 * @param countryCode not null
	 * @param cityName not null
	 * @return 不匹配返回null
	 */
	public static GeographyBean getGeography(String countryCode, String cityName){
		return getGeography(countryCode, null, null, cityName, null, null);
	}
	
	/**
	 * 根据中国城市名获取完整的地理信息
	 * @param cityName not null
	 * @return 不匹配返回null
	 */
	public static GeographyBean getGeography(String cityName){
		return getGeography(DictConstants.DICT_COUNTRYCODE_CN, null, null, cityName, null, null);
	}
	
	/**
	 * 获取中国城市编号
	 * @param cityName
	 * @return 城市编号，不匹配返回null
	 */
	public static Long getCityIdOfChina(String cityName){
		if(StringUtil.notBlankAndNull(cityName)){
			GeographyBean geo = getGeography(DictConstants.DICT_COUNTRYCODE_CN, null, null, cityName, null, null);
			if(geo!=null){
				return geo.getCityId(); 
			}
		}
		return null;
	}
}
