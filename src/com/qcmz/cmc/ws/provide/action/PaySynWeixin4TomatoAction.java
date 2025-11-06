package com.qcmz.cmc.ws.provide.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.process.OrderProcess;
import com.qcmz.cmc.process.WalletRechargeProcess;
import com.qcmz.framework.constant.DictConstants;

/**
 * 类说明：番茄翻译接收微信支付异步通知
 * 交易成功
 * 修改历史：
 */
public class PaySynWeixin4TomatoAction extends PaySynWeixinBaseAction{
	@Autowired
	private OrderProcess orderProcess;
	@Autowired
	private WalletRechargeProcess walletProcess;
	
	@Override
	public boolean synWxpay(String queryString, String subServiceType) {
		if(DictConstants.DICT_SUBSERVICETYPE_RECHARGE.equals(subServiceType)){
			return walletProcess.synWxpay(queryString, DictConstants.DICT_SERVICETYPE_TOMATO, subServiceType, null);
		}else{
			return orderProcess.synWxpay(queryString, DictConstants.DICT_SERVICETYPE_TOMATO, subServiceType, null);
		}
	}
}
