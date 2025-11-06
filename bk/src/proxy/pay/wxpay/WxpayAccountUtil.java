package com.qcmz.cmc.proxy.pay.wxpay;

import com.qcmz.cmc.config.SystemConfig;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.util.StringUtil;

public class WxpayAccountUtil {
	/**
	 * 出国翻译官帐户
	 */
	public static WxpayAccountBean ACCOUNT_VOICETRANS;
	/**
	 * 番茄翻译帐户
	 */
	public static WxpayAccountBean ACCOUNT_TOMATO;
	/**
	 * 小程序梅出过国帐户
	 */
	public static WxpayAccountBean ACCOUNT_VOICETRANS_MCGG_MP;
	/**
	 * 小程序尖叫红包帐户
	 */
	public static WxpayAccountBean ACCOUNT_REDPACKET;
	/**
	 * 小程序视频口译帐户
	 */
	public static WxpayAccountBean ACCOUNT_VOICETRANS_TRANSVIDEO_MP;
	/**
	 * 小程序出国翻译官帐户
	 */
	public static WxpayAccountBean ACCOUNT_VOICETRANS_MP;
	
	public static boolean init;	
	public static void init(String notifyServer){
		if(StringUtil.isBlankOrNull(notifyServer)) return;
		
		//出国翻译官
		ACCOUNT_VOICETRANS = new WxpayAccountBean();
		ACCOUNT_VOICETRANS.setMchid("1369553402");
		ACCOUNT_VOICETRANS.setCert("cert/wx1369553402.p12");
		ACCOUNT_VOICETRANS.setApikey("d641925d9dde59c464d0e7ad1658381e");
		ACCOUNT_VOICETRANS.setAppid("wx79a259e3baaf017d");
		ACCOUNT_VOICETRANS.setNotifyUrl(notifyServer+"/services-ssl/paySynWeixin.do");
		ACCOUNT_VOICETRANS.setTradeType("APP");
		
		//出国翻译官-小程序
		ACCOUNT_VOICETRANS_MP = new WxpayAccountBean();
		ACCOUNT_VOICETRANS_MP.setMchid("1369553402");
		ACCOUNT_VOICETRANS_MP.setCert("cert/wx1369553402.p12");
		ACCOUNT_VOICETRANS_MP.setApikey("d641925d9dde59c464d0e7ad1658381e");
		ACCOUNT_VOICETRANS_MP.setAppid("wx364a7bfece35ed2d");
		ACCOUNT_VOICETRANS_MP.setNotifyUrl(notifyServer+"/services-ssl/paySynWeixin4Mini.do");
		ACCOUNT_VOICETRANS_MP.setTradeType("JSAPI");

		//视频口译-小程序
		ACCOUNT_VOICETRANS_TRANSVIDEO_MP = new WxpayAccountBean();
		ACCOUNT_VOICETRANS_TRANSVIDEO_MP.setAppid("wx67df5bacff58cc00");
		ACCOUNT_VOICETRANS_TRANSVIDEO_MP.setMchid("1369553402");
		ACCOUNT_VOICETRANS_TRANSVIDEO_MP.setApikey("d641925d9dde59c464d0e7ad1658381e");
		ACCOUNT_VOICETRANS_TRANSVIDEO_MP.setCert("cert/wx1369553402.p12");
		ACCOUNT_VOICETRANS_TRANSVIDEO_MP.setNotifyUrl(notifyServer+"/services-ssl/paySynWeixin4Mini.do");
		ACCOUNT_VOICETRANS_TRANSVIDEO_MP.setTradeType("JSAPI");

		//梅出过国-小程序
		ACCOUNT_VOICETRANS_MCGG_MP = new WxpayAccountBean();
		ACCOUNT_VOICETRANS_MCGG_MP.setAppid("wxa879c215d90371a6");
		ACCOUNT_VOICETRANS_MCGG_MP.setMchid("1493947592");
		ACCOUNT_VOICETRANS_MCGG_MP.setApikey("o6JLXmXqA4AAXqzrcsy5MwRQp9y1VFKK");
		ACCOUNT_VOICETRANS_MCGG_MP.setCert("cert/wx1493947592.p12");
		ACCOUNT_VOICETRANS_MCGG_MP.setNotifyUrl(notifyServer+"/services-ssl/paySynWeixin4Mini.do");
		ACCOUNT_VOICETRANS_MCGG_MP.setTradeType("JSAPI");

		//TODO:番茄翻译订单
		ACCOUNT_TOMATO = new WxpayAccountBean();
		ACCOUNT_TOMATO.setAppid("wxdb04c3f8d640af20");
		ACCOUNT_TOMATO.setMchid("1450659202");
		ACCOUNT_TOMATO.setApikey("V33G9C97V763N415bP37Em088T989xwB");
		ACCOUNT_TOMATO.setCert("cert/wx1450659202.p12");
		ACCOUNT_TOMATO.setNotifyUrl(notifyServer+"/services-ssl/paySynWeixin4Tomato.do");
		ACCOUNT_TOMATO.setTradeType("APP");
		
		//红包-小程序
		ACCOUNT_REDPACKET = new WxpayAccountBean();
		ACCOUNT_REDPACKET.setAppid("wx67df5bacff58cc00");
		ACCOUNT_REDPACKET.setMchid("1369553402");
		ACCOUNT_REDPACKET.setApikey("d641925d9dde59c464d0e7ad1658381e");
		ACCOUNT_REDPACKET.setCert("cert/wx1369553402.p12");
		ACCOUNT_REDPACKET.setNotifyUrl(notifyServer+"/services-ssl/paySynWeixin4RedPacket.do");
		ACCOUNT_REDPACKET.setTradeType("JSAPI");
		
		init = true;
	}
	
	public static WxpayAccountBean getAccount(String serviceType, String subServiceType, String platform){
		if(!init){
			init(SystemConfig.CMC_SERVER);
		}
		if(DictConstants.DICT_SERVICETYPE_VOICETRANS.equals(serviceType)){
			//出国翻译官
			if(DictConstants.DICT_PLATFORM_MINIPROGRAM.equals(platform)){
				if(DictConstants.DICT_SUBSERVICETYPE_RECHARGE.equals(subServiceType)){
					return ACCOUNT_VOICETRANS_TRANSVIDEO_MP;
				}else if(DictConstants.DICT_SUBSERVICETYPE_MCGGSUB.equals(subServiceType)){
					return ACCOUNT_VOICETRANS_MCGG_MP;
				}else{
					return ACCOUNT_VOICETRANS_MP;
				}
			}else{
				return ACCOUNT_VOICETRANS;
			}
		}else if(DictConstants.DICT_SERVICETYPE_TOMATO.equals(serviceType)){
			return ACCOUNT_TOMATO;
		}else if(DictConstants.DICT_SERVICETYPE_REDPACKET.equals(serviceType)){
			return ACCOUNT_REDPACKET;
		}
		return null;
	}
}
