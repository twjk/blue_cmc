package com.qcmz.cmc.ws.provide.vo;

public class LocalTaskUserSubQueryBean {
	/**
	 * 用户编号
	 */
	private Long uid;
	/**
	 * 最后一条sortId
	 */
	private String moreBaseId;
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public String getMoreBaseId() {
		return moreBaseId;
	}
	public void setMoreBaseId(String moreBaseId) {
		this.moreBaseId = moreBaseId;
	}
}
