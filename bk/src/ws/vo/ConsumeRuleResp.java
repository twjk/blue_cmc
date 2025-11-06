package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Response;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class ConsumeRuleResp extends Response {
	/**
	 * 消费规则
	 */
	private ConsumeRuleBean rule;

	public ConsumeRuleBean getRule() {
		return rule;
	}

	public void setRule(ConsumeRuleBean rule) {
		this.rule = rule;
	}
}
