package com.qcmz.cmc.util;

import com.qcmz.cmc.cache.DictMap;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.util.StringUtil;

public class WalletUtil {
	/**
	 * 支付描述
	 * @param orderId
	 * @return
	 */
	public static String getPayOrderDesc(String orderId){
		return new StringBuilder("充值-").append(orderId).toString();
	}
	
	/**
	 * 账单描述
	 * @param billType
	 * @param orderId
	 * @param description
	 * @return
	 */
	public static String getBillDesc(Integer billType, String orderId, String description){
		StringBuilder sbBillDesc = new StringBuilder(DictMap.getWalletBillTypeMean(billType));
		if(StringUtil.notBlankAndNull(orderId)){
			sbBillDesc.append("-").append(orderId);
		}
		if(StringUtil.notBlankAndNull(description)){
			sbBillDesc.append("-").append(description);
		}
		return sbBillDesc.toString();
	}
	
	/**
	 * 操作员是否可以强行退款
	 * @param payAmount
	 * @param tradeWay
	 * @return
	 */
	public static boolean canForceRefund(double payAmount, String tradeWay){
		if(payAmount>0){
			if(DictConstants.DICT_TRADEWAY_ALIPAY.equals(tradeWay)
					|| DictConstants.DICT_TRADEWAY_WXPAY.equals(tradeWay)){
				return true;
			}
		}
		return false;
	}
}
