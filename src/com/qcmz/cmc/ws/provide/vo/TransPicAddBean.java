package com.qcmz.cmc.ws.provide.vo;

import java.io.File;

import com.qcmz.framework.annotation.JsonIgore;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class TransPicAddBean extends OrderCreateBean{
	/**
	 * 翻译途径，01机器翻译02概要翻译03文档翻译
	 */
	private String transWay;
	/**
	 * 源语言
	 */
	private String from;
	/**
	 * 目标语言
	 */
	private String to;
	/**
	 * 原文
	 */
	private String src;
	/**
	 * 译文
	 */
	private String dst;
	/**
	 * 文件
	 */
	private File file;
	/**
	 * 缩略图
	 */
	private File thumb;	
		
	public String getTransWay() {
		return transWay;
	}
	public void setTransWay(String transWay) {
		this.transWay = transWay;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public String getDst() {
		return dst;
	}
	public void setDst(String dst) {
		this.dst = dst;
	}
	@JsonIgore
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	@JsonIgore
	public File getThumb() {
		return thumb;
	}
	public void setThumb(File thumb) {
		this.thumb = thumb;
	}
}