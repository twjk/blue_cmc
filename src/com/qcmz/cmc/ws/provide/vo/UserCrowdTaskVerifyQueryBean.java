package com.qcmz.cmc.ws.provide.vo;

public class UserCrowdTaskVerifyQueryBean {
	/**
	 * 审核状态
	 */
	private Integer verifyStatus;
	/**
	 * 审核人用户编号
	 */
	private Long verifyUid;
	/**
	 * 更多基准编号
	 */
	private String moreBaseId;
	
	public Integer getVerifyStatus() {
		return verifyStatus;
	}
	public void setVerifyStatus(Integer verifyStatus) {
		this.verifyStatus = verifyStatus;
	}
	public Long getVerifyUid() {
		return verifyUid;
	}
	public void setVerifyUid(Long verifyUid) {
		this.verifyUid = verifyUid;
	}
	public String getMoreBaseId() {
		return moreBaseId;
	}
	public void setMoreBaseId(String moreBaseId) {
		this.moreBaseId = moreBaseId;
	}
}
