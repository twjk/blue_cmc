package com.qcmz.cmc.ws.provide.vo;

public class RedPacketSentBean {
	/**
	 * 红包编号
	 */
	private String packetId;
	/**
	 * 暗语
	 */
	private String title;
	/**
	 * 语言代码
	 */
	private String langCode;
	/**
	 * 中文暗语
	 */
	private String titleCn;
	/**
	 * 红包金额
	 */
	private Double packetAmount;
	/**
	 * 创建时间
	 */
	private Long createTime;
	public Double getPacketAmount() {
		return packetAmount;
	}
	public void setPacketAmount(Double packetAmount) {
		this.packetAmount = packetAmount;
	}
	public String getPacketId() {
		return packetId;
	}
	public void setPacketId(String packetId) {
		this.packetId = packetId;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLangCode() {
		return langCode;
	}
	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}
	public String getTitleCn() {
		return titleCn;
	}
	public void setTitleCn(String titleCn) {
		this.titleCn = titleCn;
	}
}
