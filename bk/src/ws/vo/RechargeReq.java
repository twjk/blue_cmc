package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class RechargeReq extends Request {
	/**
	 * 充值信息
	 */
	private RechargeAddBean recharge = new RechargeAddBean();

	public RechargeAddBean getRecharge() {
		return recharge;
	}

	public void setRecharge(RechargeAddBean recharge) {
		this.recharge = recharge;
	}
}
