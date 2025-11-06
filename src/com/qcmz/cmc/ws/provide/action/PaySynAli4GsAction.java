package com.qcmz.cmc.ws.provide.action;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.process.OrderProcess;
import com.qcmz.cmc.process.WalletRechargeProcess;
import com.qcmz.framework.constant.DictConstants;

/**
 * 类说明：句迷接收支付宝支付异步通知
 */
public class PaySynAli4GsAction extends PaySynAliBaseAction{
	@Autowired
	private OrderProcess orderProcess;
	@Autowired
	private WalletRechargeProcess walletProcess;
	
	@Override
	public boolean synAlipay(Map<String, String> params, String subServieType){
		if(DictConstants.DICT_SUBSERVICETYPE_RECHARGE.equals(subServieType)){
			return walletProcess.synAlipay(params, DictConstants.DICT_SERVICETYPE_GLODSENTENCE);
		}else{
			return orderProcess.synAlipay(params, DictConstants.DICT_SERVICETYPE_GLODSENTENCE);
		}
	}
}
