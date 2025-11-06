package com.qcmz.cmc.ws.provide.vo;

public class UserCrowdOptionBean {
	/**
	 * 用户答案编号
	 */
	private Long uaId;
	/**
	 * 选项编号
	 */
	private Long optionId;
	/**
	 * 选项标题
	 */
	private String optionTitle;
	/**
	 * 是否填写更多
	 */
	private boolean more;
	/**
	 * 用户是否选中该选项
	 */
	private boolean checked;
	/**
	 * 用户填写的更多内容
	 */
	private String moreContent;
	
	public Long getUaId() {
		return uaId;
	}
	public void setUaId(Long uaId) {
		this.uaId = uaId;
	}
	public Long getOptionId() {
		return optionId;
	}
	public void setOptionId(Long optionId) {
		this.optionId = optionId;
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
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public String getMoreContent() {
		return moreContent;
	}
	public void setMoreContent(String moreContent) {
		this.moreContent = moreContent;
	}
}
