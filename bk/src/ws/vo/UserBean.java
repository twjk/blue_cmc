package com.qcmz.cmc.ws.provide.vo;

public class UserBean {
	/**
	 * 用户编号
	 */
	private Long uid;
	/**
	 * 昵称
	 */
	private String nick;
	/**
	 * 头像地址
	 */
	private String iconUrl;
	
	public UserBean() {
		super();
	}
	
	public UserBean(Long uid, String nick, String iconUrl) {
		super();
		this.uid = uid;
		this.nick = nick;
		this.iconUrl = iconUrl;
	}

	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
}
