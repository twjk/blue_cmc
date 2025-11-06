package com.qcmz.cmc.ws.provide.vo;

public class DialogMsgBean {
	/**
	 * 消息编号
	 */
	private Long msgId;
	/**
	 * 消息方
	 */
	private Integer msgSide;
	/**
	 * 消息内容
	 */
	private String msg;
	/**
	 * 创建时间
	 */
	private Long createTime;
	/**
	 * 排序标识
	 */
	private String sortId;
	public Long getMsgId() {
		return msgId;
	}
	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}
	public Integer getMsgSide() {
		return msgSide;
	}
	public void setMsgSide(Integer msgSide) {
		this.msgSide = msgSide;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public String getSortId() {
		return sortId;
	}
	public void setSortId(String sortId) {
		this.sortId = sortId;
	}
}
