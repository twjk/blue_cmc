package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class ConsumeRuleBean {
	/**
	 * 消费规则描述
	 */
	private String description;
	/**
	 * 消费规则列表
	 */
	private List<ConsumeRuleDetailBean> rules = new ArrayList<ConsumeRuleDetailBean>();
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<ConsumeRuleDetailBean> getRules() {
		return rules;
	}
	public void setRules(List<ConsumeRuleDetailBean> rules) {
		this.rules = rules;
	}
}
