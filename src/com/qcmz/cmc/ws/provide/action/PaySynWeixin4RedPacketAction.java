package com.qcmz.cmc.ws.provide.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.process.RedPacketProcess;
import com.qcmz.framework.constant.DictConstants;

/**
 * 类说明：小程序尖叫红包接收微信支付异步通知
 * 交易成功
 * 修改历史：
 */
public class PaySynWeixin4RedPacketAction extends PaySynWeixinBaseAction{
	@Autowired
	private RedPacketProcess redPacketProcess;
	
	@Override
	public boolean synWxpay(String queryString, String subServieType) {
		return redPacketProcess.synWxpay(queryString, DictConstants.DICT_PLATFORM_MINIPROGRAM);
	}
}
