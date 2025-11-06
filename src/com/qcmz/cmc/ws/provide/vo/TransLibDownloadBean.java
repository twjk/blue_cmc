package com.qcmz.cmc.ws.provide.vo;

/**
 * 类说明：译库下载信息
 * 修改历史：
 */
public class TransLibDownloadBean {
	/**
	 * 版本号
	 */
	private String ver;
	/**
	 * 下载地址
	 */
	private String url;
	/**
	 * 加密后的压缩密码
	 */
	private String key;
	/**
	 * 文件MD5
	 */
	private String md5;
	
	public String getVer() {
		return ver;
	}
	public void setVer(String ver) {
		this.ver = ver;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
}
