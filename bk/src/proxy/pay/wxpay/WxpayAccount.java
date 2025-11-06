package com.qcmz.cmc.proxy.pay.wxpay;

import com.qcmz.framework.constant.DictConstants;

public class WxpayAccount {
	
	public static WxpayAccountBean ACCOUNT_VOICETRANS;
	public static WxpayAccountBean ACCOUNT_FNBEAR;
	public static WxpayAccountBean ACCOUNT_REDPACKET;
	
	static{
		//出国翻译官
		ACCOUNT_VOICETRANS = new WxpayAccountBean();
		ACCOUNT_VOICETRANS.setAppid("wx79a259e3baaf017d");
		ACCOUNT_VOICETRANS.setMchid("1369553402");
		ACCOUNT_VOICETRANS.setApikey("d641925d9dde59c464d0e7ad1658381e");
		ACCOUNT_VOICETRANS.setCert("cert/wx1369553402.p12");
		ACCOUNT_VOICETRANS.setNotifyUrl("https://trans.qcmuzhi.com:8443/cmc/services/paySynWeixin.do");
		ACCOUNT_VOICETRANS.setTradeType("APP");
		
		//梅出过国-小程序
		ACCOUNT_FNBEAR = new WxpayAccountBean();
		ACCOUNT_FNBEAR.setAppid("wxa879c215d90371a6");
		ACCOUNT_FNBEAR.setMchid("1493947592");
		ACCOUNT_FNBEAR.setApikey("o6JLXmXqA4AAXqzrcsy5MwRQp9y1VFKK");
		ACCOUNT_FNBEAR.setCert("cert/wx1493947592.p12");
		ACCOUNT_FNBEAR.setNotifyUrl("https://trans.qcmuzhi.com:8443/cmc/services/paySynWeixin4Mini.do");
		ACCOUNT_FNBEAR.setTradeType("JSAPI");
		
		//红包
		ACCOUNT_REDPACKET = new WxpayAccountBean();
		ACCOUNT_REDPACKET.setAppid("wx67df5bacff58cc00");
		ACCOUNT_REDPACKET.setMchid("1369553402");
		ACCOUNT_REDPACKET.setApikey("d641925d9dde59c464d0e7ad1658381e");
		ACCOUNT_REDPACKET.setCert("cert/wx1369553402.p12");
		ACCOUNT_REDPACKET.setNotifyUrl("https://trans.qcmuzhi.com:8443/cmc/services/paySynWeixin4RedPacket.do");
		ACCOUNT_REDPACKET.setTradeType("JSAPI");
	}
	
	public static WxpayAccountBean getAccount(String serviceType, String platform){
		if(DictConstants.DICT_SERVICETYPE_VOICETRANS.equals(serviceType)){
			if(DictConstants.DICT_PLATFORM_MINIPROGRAM.equals(platform)){
				return ACCOUNT_FNBEAR;
			}else{
				return ACCOUNT_VOICETRANS;
			}
		}else if(DictConstants.DICT_SERVICETYPE_REDPACKET.equals(serviceType)){
			return ACCOUNT_REDPACKET;
		}
		return null;
	}
}
