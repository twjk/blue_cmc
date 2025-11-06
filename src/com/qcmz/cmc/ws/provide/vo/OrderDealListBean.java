package com.qcmz.cmc.ws.provide.vo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class OrderDealListBean {
	/**
	 * 排序标识
	 */
	protected String sortId;
	/**
	 * 订单编号
	 */
	protected String orderId;
	/**
	 * 订单分类
	 */
	protected Integer orderCat;
	/**
	 * 订单类型
	 */
	protected Integer orderType;
	/**
	 * 用户编号
	 */
	protected Long uid;
	/**
	 * 用户昵称
	 */
	protected String nick;
	/**
	 * 订单金额，元
	 */
	protected Double amount;
	/**
	 * 优惠券金额，元
	 */
	protected Double couponAmount;
	/**
	 * 用户套餐编号
	 */
	protected Long ucid;
	/**
	 * 用户套餐信息
	 */
	protected UserTransComboBean ucBean;
	/**
	 * 预约订单，0否1是
	 */
	protected Integer appointFlag;
	/**
	 * 操作员是否可以接单
	 */
	protected boolean canAccept;
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
	 * 用户需求
	 */
	private String requirement;
	/**
	 * 创建时间
	 */
	private Long createTime;
	/**
	 * 待处理时间
	 */
	protected Long waitTime;
	/**
	 * 订单开始处理时间
	 */
	protected Long operTime;
	/**
	 * 预计处理时长，秒
	 */
	protected Long needTime;
	/**
	 * 完成时间
	 */
	protected Long finishTime;
	/**
	 * 操作员用户名
	 */
	protected String operator;
	/**
	 * 操作员姓名
	 */
	protected String operatorName;
	/**
	 * 处理状态
	 */
	protected String dealStatus;
	/**
	 * 处理进度
	 */
	protected String dealProgress;
	/**
	 * 校对人用户名
	 */
	protected String checkCd;
	/**
	 * 校对人姓名
	 */
	protected String checkName;
	/**
	 * 校对状态
	 */
	protected Integer checkStatus;
	/**
	 * 评价状态
	 */
	protected Integer evalStatus;
	/**
	 * 评价信息
	 */
	protected OrderEvalDetailBean evalBean;
	/**
	 * 源语言
	 */
	private String from;
	/**
	 * 目标语言
	 */
	private String to;
	/**
	 * 译员佣金金额
	 */
	private Double commissionAmount;
	/**
	 * 佣金结算状态
	 */
	private Integer commissionStatus;
	/**
	 * 下单平台
	 */
	private String platform;
	/**
	 * 下单平台版本
	 */
	private String platformVersion;
	
	public OrderDealListBean() {
		super();
	}
	//基础信息，用户昵称、套餐、评价信息另外获取
	public OrderDealListBean(String sortId, String orderId, Integer orderCat, Integer orderType, Long uid, Double amount, Double couponAmount, Long ucid,
			Integer appointFlag, String lon, String lat, String address, String requirement,
			Date createTime, Date waitTime, Date operTime, Long needTime, Date finishTime, String operator, String operatorName,
			String dealStatus, String dealProgress, String checkCd, String checkName, Integer checkStatus, Integer evalStatus,
			String from, String to, Double commissionAmount, Integer commissionStatus, String platform, String platformVersion) {
		super();
		this.sortId = sortId;
		this.orderId = orderId;
		this.orderCat = orderCat;
		this.orderType = orderType;
		this.uid = uid;
		this.amount = amount;
		this.couponAmount = couponAmount;
		this.ucid = ucid;
		this.appointFlag = appointFlag;
		this.lon = lon;
		this.lat = lat;
		this.address = address;
		this.requirement = requirement;
		this.createTime = createTime.getTime();
		this.waitTime = waitTime==null?null:waitTime.getTime();
		this.operTime = operTime==null?null:operTime.getTime();
		this.needTime = needTime;
		this.finishTime = finishTime==null?null:finishTime.getTime();
		this.operator = operator;
		this.operatorName = operatorName;
		this.dealStatus = dealStatus;
		this.dealProgress = dealProgress;
		this.checkCd = checkCd;
		this.checkName = checkName;
		this.checkStatus = checkStatus;
		this.evalStatus = evalStatus;
		this.from = from;
		this.to = to;
		this.commissionAmount = commissionAmount;
		this.commissionStatus = commissionStatus;
		this.platform = platform;
		this.platformVersion = platformVersion;
	}

	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	@JSON(serialize=false)
	public Integer getOrderCat() {
		return orderCat;
	}
	public void setOrderCat(Integer orderCat) {
		this.orderCat = orderCat;
	}
	public Integer getOrderType() {
		return orderType;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	public String getSortId() {
		return sortId;
	}
	public void setSortId(String sortId) {
		this.sortId = sortId;
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
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getCouponAmount() {
		return couponAmount;
	}
	public void setCouponAmount(Double couponAmount) {
		this.couponAmount = couponAmount;
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
	public String getRequirement() {
		return requirement;
	}
	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Long getWaitTime() {
		return waitTime;
	}
	public void setWaitTime(Long waitTime) {
		this.waitTime = waitTime;
	}
	public Long getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Long finishTime) {
		this.finishTime = finishTime;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public Integer getAppointFlag() {
		return appointFlag;
	}
	public void setAppointFlag(Integer appointFlag) {
		this.appointFlag = appointFlag;
	}
	public String getDealStatus() {
		return dealStatus;
	}
	public void setDealStatus(String dealStatus) {
		this.dealStatus = dealStatus;
	}
	public String getCheckCd() {
		return checkCd;
	}
	public void setCheckCd(String checkCd) {
		this.checkCd = checkCd;
	}
	public String getCheckName() {
		return checkName;
	}
	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}
	public Integer getCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}
	public Long getUcid() {
		return ucid;
	}
	public void setUcid(Long ucid) {
		this.ucid = ucid;
	}
	public UserTransComboBean getUcBean() {
		return ucBean;
	}
	public void setUcBean(UserTransComboBean ucBean) {
		this.ucBean = ucBean;
	}
	public String getDealProgress() {
		return dealProgress;
	}
	public void setDealProgress(String dealProgress) {
		this.dealProgress = dealProgress;
	}
	public Integer getEvalStatus() {
		return evalStatus;
	}
	public void setEvalStatus(Integer evalStatus) {
		this.evalStatus = evalStatus;
	}
	public OrderEvalDetailBean getEvalBean() {
		return evalBean;
	}
	public void setEvalBean(OrderEvalDetailBean evalBean) {
		this.evalBean = evalBean;
	}
	public Long getOperTime() {
		return operTime;
	}
	public void setOperTime(Long operTime) {
		this.operTime = operTime;
	}
	public Long getNeedTime() {
		return needTime;
	}
	public void setNeedTime(Long needTime) {
		this.needTime = needTime;
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
	public Double getCommissionAmount() {
		return commissionAmount;
	}
	public void setCommissionAmount(Double commissionAmount) {
		this.commissionAmount = commissionAmount;
	}
	public Integer getCommissionStatus() {
		return commissionStatus;
	}
	public void setCommissionStatus(Integer commissionStatus) {
		this.commissionStatus = commissionStatus;
	}
	public boolean isCanAccept() {
		return canAccept;
	}
	public void setCanAccept(boolean canAccept) {
		this.canAccept = canAccept;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getPlatformVersion() {
		return platformVersion;
	}
	public void setPlatformVersion(String platformVersion) {
		this.platformVersion = platformVersion;
	}
}
