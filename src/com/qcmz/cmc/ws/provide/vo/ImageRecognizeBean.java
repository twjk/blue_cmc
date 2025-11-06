package com.qcmz.cmc.ws.provide.vo;

import java.io.File;

public class ImageRecognizeBean {
	/**
	 * 用户编号
	 */
	private Long uid;
	/**
	 * 分类编号
	 */
	private Long catId;
	/**
	 * 图像文件
	 */
	private File file;
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public Long getCatId() {
		return catId;
	}
	public void setCatId(Long catId) {
		this.catId = catId;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
}
