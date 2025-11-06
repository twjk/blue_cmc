package com.qcmz.cmc.vo;

public class TransLibHitCountBean {
	/**
	 * 译库类
	 */
	private String libClass;
	/**
	 * 译库编号
	 */
	private Long libId;
	/**
	 * 命中次数
	 */
	private int times;
	
	public TransLibHitCountBean() {
		super();
	}
	
	public TransLibHitCountBean(String libClass, Long libId, int times) {
		super();
		this.libClass = libClass;
		this.libId = libId;
		this.times = times;
	}

	public String getLibClass() {
		return libClass;
	}
	public void setLibClass(String libClass) {
		this.libClass = libClass;
	}
	public Long getLibId() {
		return libId;
	}
	public void setLibId(Long libId) {
		this.libId = libId;
	}
	public int getTimes() {
		return times;
	}
	public void setTimes(int times) {
		this.times = times;
	}
}
