package com.qcmz.cmc.vo;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class LibClassBean {
	private String from;
	private String to;
	private String libClass;
	private String libName;
	public LibClassBean() {
		super();
	}
	public LibClassBean(String from, String to, String libClass, String libName) {
		super();
		this.from = from;
		this.to = to;
		this.libClass = libClass;
		this.libName = libName;
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
	public String getLibClass() {
		return libClass;
	}
	public void setLibClass(String libClass) {
		this.libClass = libClass;
	}
	public String getLibName() {
		return libName;
	}
	public void setLibName(String libName) {
		this.libName = libName;
	}
	
}
