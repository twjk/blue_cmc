package com.qcmz.cmc.ws.provide.vo;

/**
 * 类说明：对该类的主要功能进行说明
 * @author 李炳煜
 * 修改历史：
 */
public class ThemeBean {
	/**
	 * 主题编号
	 */
	private Long themeId;
	/**
	 * 主题代码
	 */
	private String themeCode;
	/**
	 * 中文名称
	 */
	private String titleCn;
	/**
	 * 当地名称
	 */
	private String titleLc;
	/**
	 * 图片地址
	 */
	private String picUrl;
	/**
	 * 离线包地址
	 */
	private String downloadUrl;
	/**
	 * 离线包大小
	 */
	private String fileSize;
	
	public ThemeBean() {
		super();
	}
	
	public ThemeBean(Long themeId, String themeCode, String titleCn, String titleLc,
			String picUrl, String downloadUrl, String fileSize) {
		super();
		this.themeId = themeId;
		this.themeCode = themeCode;
		this.titleCn = titleCn;
		this.titleLc = titleLc;
		this.picUrl = picUrl;
		this.downloadUrl = downloadUrl;
		this.fileSize = fileSize;
	}

	public Long getThemeId() {
		return themeId;
	}
	public void setThemeId(Long themeId) {
		this.themeId = themeId;
	}
	public String getThemeCode() {
		return themeCode;
	}
	public void setThemeCode(String themeCode) {
		this.themeCode = themeCode;
	}
	public String getTitleCn() {
		return titleCn;
	}
	public void setTitleCn(String titleCn) {
		this.titleCn = titleCn;
	}
	public String getTitleLc() {
		return titleLc;
	}
	public void setTitleLc(String titleLc) {
		this.titleLc = titleLc;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getDownloadUrl() {
		return downloadUrl;
	}
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
}
