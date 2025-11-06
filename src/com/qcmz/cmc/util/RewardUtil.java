package com.qcmz.cmc.util;

import com.qcmz.framework.util.DoubleUtil;
import com.qcmz.framework.util.NumberUtil;
import com.qcmz.framework.util.StringUtil;

public class RewardUtil {
	/**
	 * 转账订单号
	 * @param userId
	 * @param cashId
	 * @return
	 */
	public static String getTransferOrderId(Long userId, Long cashId){
		return new StringBuilder("RW").append(userId).append("C").append(cashId).toString();
	}
	
	/**
	 * 转账描述
	 * @param serviceTypeMean
	 * @param orderId
	 * @return
	 */
	public static String getTransferOrderDesc(String serviceTypeMean, String orderId){
		StringBuilder sb = new StringBuilder();
		if(StringUtil.notBlankAndNull(serviceTypeMean)){
			sb.append(serviceTypeMean);
		}
		return sb.append("奖励金提现-").append(orderId).toString();
	}
	
	/**
	 * 获取奖励金额
	 * @param rewardAmount
	 * @return
	 */
	public static Double getRewardAmount(String rewardAmount){
		if(NumberUtil.isNumber(rewardAmount)){
			return DoubleUtil.valueOf(rewardAmount);
		}
		String[] arr = StringUtil.split(rewardAmount, "-");
		Double min = DoubleUtil.valueOf(arr[0]);
		Double max = DoubleUtil.valueOf(arr[1]);
		Double result = Math.random()*(max-min)+min;
		return DoubleUtil.floor(result, 2);
	}
	
	/**
	 * 获取最高奖励金额
	 * @param rewardAmount
	 * @return
	 */
	public static Double getMaxRewardAmount(String rewardAmount){
		if(NumberUtil.isNumber(rewardAmount)){
			return DoubleUtil.valueOf(rewardAmount);
		}
		String[] arr = StringUtil.split(rewardAmount, "-");
		return DoubleUtil.valueOf(arr[1]);
	}
}
