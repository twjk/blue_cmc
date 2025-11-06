package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

import com.qcmz.framework.ws.vo.UserBean;

public class RedPacketBean {
	/**
	 * 红包编号
	 */
	private String packetId;
	/**
	 * 暗语
	 */
	private String title;
	/**
	 * 语言代码
	 */
	private String langCode;
	/**
	 * 中文暗语
	 */
	private String titleCn;
	/**
	 * 剩余可领数量
	 */
	private int remainderNum;
	/**
	 * 截止时间
	 */
	private Long endTime;
	/**
	 * 发红包用户信息
	 */
	private UserBean user;
	/**
	 * 红包总金额
	 */
	private Double packetAmount;
	/**
	 * 用户领取金额（receiverId不为空，并且已领红包后有值）
	 */
	private Double receiveAmount;
	
	/**
	 * 红包领取列表
	 */
	private List<RedPacketReceivedBean> receives = new ArrayList<RedPacketReceivedBean>();
	public String getPacketId() {
		return packetId;
	}
	public void setPacketId(String packetId) {
		this.packetId = packetId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLangCode() {
		return langCode;
	}
	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}
	public String getTitleCn() {
		return titleCn;
	}
	public void setTitleCn(String titleCn) {
		this.titleCn = titleCn;
	}
	public int getRemainderNum() {
		return remainderNum;
	}
	public void setRemainderNum(int remainderNum) {
		this.remainderNum = remainderNum;
	}
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	public UserBean getUser() {
		return user;
	}
	public void setUser(UserBean user) {
		this.user = user;
	}
	public Double getPacketAmount() {
		return packetAmount;
	}
	public void setPacketAmount(Double packetAmount) {
		this.packetAmount = packetAmount;
	}
	public Double getReceiveAmount() {
		return receiveAmount;
	}
	public void setReceiveAmount(Double receiveAmount) {
		this.receiveAmount = receiveAmount;
	}
	public List<RedPacketReceivedBean> getReceives() {
		return receives;
	}
	public void setReceives(List<RedPacketReceivedBean> receives) {
		this.receives = receives;
	}
}
