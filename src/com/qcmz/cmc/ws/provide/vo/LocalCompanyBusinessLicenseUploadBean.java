package com.qcmz.cmc.ws.provide.vo;

import java.io.File;

public class LocalCompanyBusinessLicenseUploadBean {
	/**
	 * 用户编号
	 */
	private Long uid;
	/**
	 * 文件
	 */
	private File file;
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
}
