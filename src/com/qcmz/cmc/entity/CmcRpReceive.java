package com.qcmz.cmc.entity;

import java.util.Date;

import com.qcmz.umc.ws.provide.vo.UserSimpleBean;

/**
 * CmcRpReceive entity. @author MyEclipse Persistence Tools
 */

public class CmcRpReceive implements java.io.Serializable {

	// Fields

	private Long receiveid;
	private String packetid;
	private CmcRpPacket cmcRpPacket;
	private Long userid;
	private Double amount;
	private Long receiverid;
	
	private UserSimpleBean receiver;//隔外加的用户信息
	
	private Date receivetime;
	private String voice;

	// Constructors

	/** default constructor */
	public CmcRpReceive() {
	}

	// Property accessors

	public Long getReceiveid() {
		return this.receiveid;
	}

	public void setReceiveid(Long receiveid) {
		this.receiveid = receiveid;
	}

	public String getPacketid() {
		return packetid;
	}

	public void setPacketid(String packetid) {
		this.packetid = packetid;
	}

	public CmcRpPacket getCmcRpPacket() {
		return this.cmcRpPacket;
	}

	public void setCmcRpPacket(CmcRpPacket cmcRpPacket) {
		this.cmcRpPacket = cmcRpPacket;
	}

	public Long getUserid() {
		return this.userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Long getReceiverid() {
		return receiverid;
	}

	public void setReceiverid(Long receiverid) {
		this.receiverid = receiverid;
	}

	public Date getReceivetime() {
		return this.receivetime;
	}

	public void setReceivetime(Date receivetime) {
		this.receivetime = receivetime;
	}

	public String getVoice() {
		return voice;
	}

	public void setVoice(String voice) {
		this.voice = voice;
	}
	
	public UserSimpleBean getReceiver() {
		return receiver;
	}

	public void setReceiver(UserSimpleBean receiver) {
		this.receiver = receiver;
	}
}