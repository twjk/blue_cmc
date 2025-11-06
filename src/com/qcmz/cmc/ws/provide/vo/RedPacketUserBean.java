package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

public class RedPacketUserBean {
	/**
	 * 用户红包帐户
	 */
	private RedPacketAccountBean account;
	/**
	 * 红包领取列表
	 */
	private List<RedPacketReceivedBean> receiveds = new ArrayList<RedPacketReceivedBean>();
	/**
	 * 发红包列表
	 */
	private List<RedPacketSentBean> sents = new ArrayList<RedPacketSentBean>();
	public RedPacketAccountBean getAccount() {
		return account;
	}
	public void setAccount(RedPacketAccountBean account) {
		this.account = account;
	}
	public List<RedPacketReceivedBean> getReceiveds() {
		return receiveds;
	}
	public void setReceiveds(List<RedPacketReceivedBean> receiveds) {
		this.receiveds = receiveds;
	}
	public List<RedPacketSentBean> getSents() {
		return sents;
	}
	public void setSents(List<RedPacketSentBean> sents) {
		this.sents = sents;
	}
}
