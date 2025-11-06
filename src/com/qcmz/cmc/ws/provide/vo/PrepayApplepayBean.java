package com.qcmz.cmc.ws.provide.vo;

public class PrepayApplepayBean {
	/**
	 * 苹果内付费标识
	 */
	private String iapid;

	public PrepayApplepayBean() {
		super();
	}

	public PrepayApplepayBean(String iapid) {
		super();
		this.iapid = iapid;
	}

	public String getIapid() {
		return iapid;
	}

	public void setIapid(String iapid) {
		this.iapid = iapid;
	}
	
}
