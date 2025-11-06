package com.qcmz.cmc.ws.provide.vo;

import java.util.Date;

/**
 * 类说明：真人口译信息
 * 修改历史：
 */
public class TransVideoDealListBean extends OrderDealListBean{
	/**
	 * 房间号
	 */
	private String roomId;
	/**
	 * 通话类型，1视频2语音
	 */
	private Integer callType;
	/**
	 * 单价
	 */
	private Double price;
	/**
	 * 用户余额
	 */
	private Double balance;
	/**
	 * 场景
	 */
	private SceneBean sceneBean;
	
	public TransVideoDealListBean() {
		super();
	}
	public TransVideoDealListBean(String sortId, String orderId, Integer orderCat, Integer orderType, Long uid, Double amount, Double couponAmount,
			Long ucid, Integer appointFlag, String lon, String lat, String address, String requirement, Date createTime, Date waitTime,
			Date operTime, Long needTime, Date finishTime, String operator, String operatorName, String dealStatus,
			String dealProgress, String checkCd, String checkName, Integer checkStatus, Integer evalStatus, 
			String from, String to, Double commissionAmount, Integer commissionStatus, String platform, String platformVersion,
			Long sceneId, String roomId, Integer callType, Double balance) {
		super(sortId, orderId, orderCat, orderType, uid, amount, couponAmount, ucid, appointFlag, lon, lat, address, requirement, createTime, waitTime,
				operTime, needTime, finishTime, operator, operatorName, dealStatus, dealProgress, checkCd, checkName,
				checkStatus, evalStatus, from, to, commissionAmount, commissionStatus, platform, platformVersion);
		if(sceneId!=null){
			this.sceneBean = new SceneBean(sceneId);
		}
		this.roomId = roomId;
		this.callType = callType;
		this.balance = balance;
	}

	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
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
	public Integer getCallType() {
		return callType;
	}
	public void setCallType(Integer callType) {
		this.callType = callType;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
}