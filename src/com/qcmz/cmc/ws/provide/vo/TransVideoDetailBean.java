package com.qcmz.cmc.ws.provide.vo;


/**
 * 类说明：视频口译详细信息
 * 修改历史：
 */
public class TransVideoDetailBean extends OrderDetailBean{
	/**
	 * 源语言
	 */
	private String from;
	/**
	 * 目标语言
	 */
	private String to;
	/**
	 * 接通时间
	 */
	private Long connectedTime;
	/**
	 * 开始计费时间
	 */
	private Long billingTime;
	/**
	 * 挂断时间
	 */
	private Long handupTime;
	/**
	 * 计费时长，秒
	 */
	private Integer billingDuration;
	/**
	 * 计费时长，分钟
	 */
	private Integer billingMinute;
	/**
	 * 场景信息
	 */
	private SceneBean sceneBean;
	/**
	 * 房间号
	 */
	private String roomId;
	
	public TransVideoDetailBean() {
		super();
	}
	public TransVideoDetailBean(OrderDetailBean orderDetail) {
		super(orderDetail);
	}
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
	public Long getConnectedTime() {
		return connectedTime;
	}
	public void setConnectedTime(Long connectedTime) {
		this.connectedTime = connectedTime;
	}
	public Long getBillingTime() {
		return billingTime;
	}
	public void setBillingTime(Long billingTime) {
		this.billingTime = billingTime;
	}
	public Long getHandupTime() {
		return handupTime;
	}
	public void setHandupTime(Long handupTime) {
		this.handupTime = handupTime;
	}
	public Integer getBillingDuration() {
		return billingDuration;
	}
	public void setBillingDuration(Integer billingDuration) {
		this.billingDuration = billingDuration;
	}
	public Integer getBillingMinute() {
		if(billingDuration!=null){
			billingMinute = billingDuration/60;
			if(billingDuration%60>0){
				billingMinute++;
			}
		}
		return billingMinute;
	}
	public SceneBean getSceneBean() {
		return sceneBean;
	}
	public void setSceneBean(SceneBean sceneBean) {
		this.sceneBean = sceneBean;
	}
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
}