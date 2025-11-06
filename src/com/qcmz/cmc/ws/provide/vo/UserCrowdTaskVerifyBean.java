package com.qcmz.cmc.ws.provide.vo;

public class UserCrowdTaskVerifyBean {
	/**
	 * 用户任务编号
	 */
	private String utId;
	/**
	 * 审核人用户编号
	 */
	private Long verifyUid;
	/**
	 * 是否通过审核
	 */
	private Integer pass;
	/**
	 * 拒绝报名原因
	 */
	private String reason;
	public String getUtId() {
		return utId;
	}
	public void setUtId(String utId) {
		this.utId = utId;
	}
	public Long getVerifyUid() {
		return verifyUid;
	}
	public void setVerifyUid(Long verifyUid) {
		this.verifyUid = verifyUid;
	}
	public Integer getPass() {
		return pass;
	}
	public void setPass(Integer pass) {
		this.pass = pass;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
}
