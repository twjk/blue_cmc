package com.qcmz.cmc.ws.provide.vo;

/**
 * 类说明：对该类的主要功能进行说明
 * @author 李炳煜
 * 修改历史：
 */
public class ThemeDownloadBean {
	/**
	 * 主题编号
	 */
	private Long themeId;
	/**
	 * 主题代码
	 */
	private String themeCode;
	/**
	 * 离线包地址
	 */
	private String downloadUrl;
	/**
	 * 离线包大小
	 */
	private String fileSize;
	
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
