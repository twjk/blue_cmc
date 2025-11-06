package com.qcmz.cmc.ws.provide.vo;

public class RewardInviteBean {
	/**
	 * 邀请人用户编号
	 */
	private Long inviterId;
	/**
	 * 被邀请人用户编号
	 */
	private Long inviteeId;
	/**
	 * 子业务类型
	 */
	private String subServiceType;
	/**
	 * 业务编号
	 */
	private String serviceId;
	public Long getInviterId() {
		return inviterId;
	}
	public void setInviterId(Long inviterId) {
		this.inviterId = inviterId;
	}
	public Long getInviteeId() {
		return inviteeId;
	}
	public void setInviteeId(Long inviteeId) {
		this.inviteeId = inviteeId;
	}
	public String getSubServiceType() {
		return subServiceType;
	}
	public void setSubServiceType(String subServiceType) {
		this.subServiceType = subServiceType;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
}
