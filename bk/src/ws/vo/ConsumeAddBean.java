package com.qcmz.cmc.ws.provide.vo;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class ConsumeAddBean {
	/**
	 * 用户编号
	 */
	private Long userId;
	/**
	 * 消费项目
	 */
	private String item;
	/**
	 * 语言代码
	 */
	private String langCode;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 消费时间，格式yyyy-MM-dd HH:mm:ss
	 */
	private String time;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
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
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
