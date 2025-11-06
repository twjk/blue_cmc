package com.qcmz.cmc.ws.provide.vo;

public class UserCrowdTaskQcQueryBean {
	/**
	 * 审校状态
	 */
	private Integer qcStatus;
	/**
	 * 审校人用户编号
	 */
	private Long qcUid;
	/**
	 * 最后一条记录编号
	 */
	private String moreBaseId;
	
	public Integer getQcStatus() {
		return qcStatus;
	}
	public void setQcStatus(Integer qcStatus) {
		this.qcStatus = qcStatus;
	}
	public Long getQcUid() {
		return qcUid;
	}
	public void setQcUid(Long qcUid) {
		this.qcUid = qcUid;
	}
	public String getMoreBaseId() {
		return moreBaseId;
	}
	public void setMoreBaseId(String moreBaseId) {
		this.moreBaseId = moreBaseId;
	}
}
