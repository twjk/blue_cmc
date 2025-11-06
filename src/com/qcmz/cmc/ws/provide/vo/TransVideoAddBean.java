package com.qcmz.cmc.ws.provide.vo;


/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class TransVideoAddBean extends OrderCreateBean{
	/**
	 * 源语言
	 */
	private String from;
	/**
	 * 目标语言
	 */
	private String to;
	/**
	 * 场景编号
	 */
	private Long sceneId;
	/**
	 * 房间号
	 */
	private String roomId;
	/**
	 * 通话类型，1视频2语音
	 */
	private Integer callType;
	/**
	 * 预约时间
	 */
	private Long appointTime;	
	/**
	 * 使用的用户套餐编号
	 */
	private Long ucid;
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public Long getSceneId() {
		return sceneId;
	}
	public void setSceneId(Long sceneId) {
		this.sceneId = sceneId;
	}
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public Integer getCallType() {
		return callType;
	}
	public void setCallType(Integer callType) {
		this.callType = callType;
	}
	public Long getAppointTime() {
		return appointTime;
	}
	public void setAppointTime(Long appointTime) {
		this.appointTime = appointTime;
	}	
	public Long getUcid() {
		return ucid;
	}
	public void setUcid(Long ucid) {
		this.ucid = ucid;
	}
}