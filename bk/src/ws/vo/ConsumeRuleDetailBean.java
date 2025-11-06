package com.qcmz.cmc.ws.provide.vo;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class ConsumeRuleDetailBean {
	/**
	 * 消费项目，01识别02发声
	 */
	private String item;
	/**
	 * 语言代码
	 */
	private String langCode;
	/**
	 * 点数
	 */
	private int point;
	
	public ConsumeRuleDetailBean() {
		super();
	}
	
	public ConsumeRuleDetailBean(String item, String langCode, int point) {
		super();
		this.item = item;
		this.langCode = langCode;
		this.point = point;
	}

	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getLangCode() {
		return langCode;
	}
	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
}
