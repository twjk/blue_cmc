package com.qcmz.cmc.ws.provide.vo;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class TransTermBean {
	/**
	 * 关键词
	 */
	private String key;
	/**
	 * 结果地址
	 */
	private String url;
	/**
	 * 图标
	 */
	private String typeIcon;
	/**
	 * 分类标识
	 */
	private Long typeId;
	
	public TransTermBean() {
		super();
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	public String getTypeIcon() {
		return typeIcon;
	}

	public void setTypeIcon(String typeIcon) {
		this.typeIcon = typeIcon;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}
}
