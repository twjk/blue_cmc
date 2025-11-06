package com.qcmz.cmc.vo;

import com.qcmz.framework.util.NumberUtil;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class BingQuotaBean {
	private int TotalCalls;
	private String CallCountSuccess;
	private int RemainingQuota;
	private String ExpiredDate;
	public int getTotalCalls() {
		return TotalCalls;
	}
	public void setTotalCalls(int totalCalls) {
		TotalCalls = totalCalls;
	}
	public String getCallCountSuccess() {
		return CallCountSuccess;
	}
	public Long getCallCount(){
		if(NumberUtil.isNumber(CallCountSuccess)){
			return Long.valueOf(CallCountSuccess);
		}
		return null;
	}
	public void setCallCountSuccess(String callCountSuccess) {
		CallCountSuccess = callCountSuccess;
	}
	public int getRemainingQuota() {
		return RemainingQuota;
	}
	public void setRemainingQuota(int remainingQuota) {
		RemainingQuota = remainingQuota;
	}
	public String getExpiredDate() {
		return ExpiredDate;
	}
	public void setExpiredDate(String expiredDate) {
		ExpiredDate = expiredDate;
	}
}
