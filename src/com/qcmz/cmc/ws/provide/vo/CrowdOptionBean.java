package com.qcmz.cmc.ws.provide.vo;

public class CrowdOptionBean {
	/**
	 * 选项编号
	 */
	private Long optionId;
	/**
	 * 选项值
	 */
	private String optionValue;
	/**
	 * 选项标题
	 */
	private String optionTitle;
	/**
	 * 是否填写更多
	 */
	private boolean more;
	public Long getOptionId() {
		return optionId;
	}
	public void setOptionId(Long optionId) {
		this.optionId = optionId;
	}
	public String getOptionValue() {
		return optionValue;
	}
	public void setOptionValue(String optionValue) {
		this.optionValue = optionValue;
	}
	public String getOptionTitle() {
		return optionTitle;
	}
	public void setOptionTitle(String optionTitle) {
		this.optionTitle = optionTitle;
	}
	public boolean isMore() {
		return more;
	}
	public void setMore(boolean more) {
		this.more = more;
	}
}
