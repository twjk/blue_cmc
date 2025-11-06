package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

/**
 * 类说明：账单查询
 * 修改历史：
 */
public class RewardBillQueryReq extends Request {
	private RewardBillQueryBean bean = new RewardBillQueryBean();

	public RewardBillQueryBean getBean() {
		return bean;
	}

	public void setBean(RewardBillQueryBean bean) {
		this.bean = bean;
	}
}
