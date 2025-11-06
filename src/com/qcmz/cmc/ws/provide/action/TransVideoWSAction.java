package com.qcmz.cmc.ws.provide.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.ws.provide.service.TransVideoInterface;
import com.qcmz.cmc.ws.provide.vo.OrderGetReq;
import com.qcmz.cmc.ws.provide.vo.TransVideoAddReq;
import com.qcmz.cmc.ws.provide.vo.TransVideoPriceQueryReq;
import com.qcmz.framework.action.BaseWSAction;
import com.qcmz.umc.constant.DictConstant;

public class TransVideoWSAction extends BaseWSAction {
	@Autowired
	private TransVideoInterface transVideoInterface;
	
	/**
	 * 用户编号
	 */
	private Long uid;
	/**
	 * 用户类型
	 */
	private Integer userType;
	/**
	 * 员工编号
	 */
	private String employeeId;
	/**
	 * 员工姓名
	 */
	private String employeeName;
	/**
	 * 订单编号
	 */
	private String orderId;
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
	 * 经度
	 */
	private String lon;
	/**
	 * 纬度
	 */
	private String lat;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 使用的优惠券编号
	 */
	private Long couponId;
	/**
	 * 优惠券面额
	 */
	private double couponAmount;
	/**
	 * 使用的用户套餐编号
	 */
	private Long ucid;
	
	/**
	 * 获取价格
	 * @return
	 * 修改历史：
	 */
	public String findPrice(){
		TransVideoPriceQueryReq req = new TransVideoPriceQueryReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		req.setLanguage(language);
		
		req.setFrom(from);
		req.setTo(to);
		
		jsonObj = transVideoInterface.findPrice(req, interfaceType, getRemoteAddr());
		
		return JSON;		
	}

	/**
	 * 创建视频口译订单
	 * @return
	 * 修改历史：
	 */
	public String addVideo(){
		TransVideoAddReq req = new TransVideoAddReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		req.setLanguage(language);
		
		req.getBean().setUid(uid);
		req.getBean().setUserType(DictConstant.getUserType(userType));
		req.getBean().setEmployeeId(employeeId);
		req.getBean().setEmployeeName(employeeName);
		req.getBean().setFrom(from);
		req.getBean().setTo(to);
		req.getBean().setSceneId(sceneId);
		req.getBean().setRoomId(roomId);
		req.getBean().setCallType(callType);
		req.getBean().setAppointTime(appointTime);
		req.getBean().setLon(lon);
		req.getBean().setLat(lat);
		req.getBean().setAddress(address);
		req.getBean().setCouponId(couponId);
		req.getBean().setCouponAmount(couponAmount);
		req.getBean().setUcid(ucid);
		
		jsonObj = transVideoInterface.addVideo(req, interfaceType, getRemoteAddr());
		
		return JSON;		
	}
	
	/**
	 * 获取视频口译订单详细信息
	 * @return
	 * 修改历史：
	 */
	public String getVideo(){
		OrderGetReq req = new OrderGetReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		req.setLanguage(language);
		
		req.setUid(uid);
		req.setOrderId(orderId);
		
		jsonObj = transVideoInterface.getVideo(req, interfaceType, getRemoteAddr());
		
		return JSON;		
	}
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getCouponId() {
		return couponId;
	}

	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}

	public double getCouponAmount() {
		return couponAmount;
	}

	public void setCouponAmount(double couponAmount) {
		this.couponAmount = couponAmount;
	}

	public Long getUcid() {
		return ucid;
	}

	public void setUcid(Long ucid) {
		this.ucid = ucid;
	}
}
