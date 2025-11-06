package com.qcmz.cmc.ws.provide.vo;

public class TmBean {
	/**
	 * 翻译机编号
	 */
	private String tmcode;
	/**
	 * 状态，0不可用1可用
	 */
	private int status;
	/**
	 * 固件版本信息
	 */
	private TmVersionBean lastVersion;
	public String getTmcode() {
		return tmcode;
	}
	public void setTmcode(String tmcode) {
		this.tmcode = tmcode;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public TmVersionBean getLastVersion() {
		return lastVersion;
	}
	public void setLastVersion(TmVersionBean lastVersion) {
		this.lastVersion = lastVersion;
	}
}
