package com.qcmz.cmc.ws.provide.vo;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class ProxyAccountCallAddBean {
	/**
	 * 用户标识，用户编号或jpush标识
	 */
	private String uid;
	/**
	 * 帐户编号
	 */
	private Long accountId;
	/**
	 * 访问次数
	 */
	private Long callCount;
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	public Long getCallCount() {
		return callCount;
	}
	public void setCallCount(Long callCount) {
		this.callCount = callCount;
	}
}