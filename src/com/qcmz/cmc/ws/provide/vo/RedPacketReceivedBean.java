package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.UserBean;

public class RedPacketReceivedBean {
	/**
	 * 红包编号
	 */
	private String packetId;
	/**
	 * 领取金额
	 */
	private Double amount;
	/**
	 * 发红包用户信息
	 */
	private UserBean user;
	/**
	 * 领红包用户信息
	 */
	private UserBean receiver;
	/**
	 * 领取时间
	 */
	private Long receiveTime;
	/**
	 * 领取音频链接
	 */
	private String voice;
	public String getPacketId() {
		return packetId;
	}
	public void setPacketId(String packetId) {
		this.packetId = packetId;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public UserBean getUser() {
		return user;
	}
	public void setUser(UserBean user) {
		this.user = user;
	}
	public UserBean getReceiver() {
		return receiver;
	}
	public void setReceiver(UserBean receiver) {
		this.receiver = receiver;
	}
	public Long getReceiveTime() {
		return receiveTime;
	}
	public void setReceiveTime(Long receiveTime) {
		this.receiveTime = receiveTime;
	}
	public String getVoice() {
		return voice;
	}
	public void setVoice(String voice) {
		this.voice = voice;
	}
}
