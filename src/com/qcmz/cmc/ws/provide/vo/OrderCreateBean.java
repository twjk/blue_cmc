package com.qcmz.cmc.ws.provide.vo;

public class OrderCreateBean {
	/**
	 * 用户编号
	 */
	private Long uid;
	/**
	 * 用户类型，默认个人用户
	 */
	private Integer userType = 1;
	/**
	 * 员工编号
	 */
	private String employeeId;
	/**
	 * 员工姓名
	 */
	private String employeeName;
	/**
	 * 订单类型
	 */
	private Integer orderType;
	/**
	 * 使用的优惠券编号
	 */
	private Long couponId;
	/**
	 * 优惠券面额
	 */
	private double couponAmount;
	/**
	 * 钱包抵扣金额
	 */
	private double walletAmount;
	/**
	 * 总金额
	 */
	private double amount;
	/**
	 * 交易途径
	 */
	private String tradeWay;
	/**
	 * 微信小程序支付用户标识
	 */
	private String openid;	
	/**
	 * 用户需求
	 */
	private String requirement;
	/**
	 * 联系手机号
	 */
	private String mobile;
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
	public double getWalletAmount() {
		return walletAmount;
	}
	public void setWalletAmount(double walletAmount) {
		this.walletAmount = walletAmount;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getTradeWay() {
		return tradeWay;
	}
	public void setTradeWay(String tradeWay) {
		this.tradeWay = tradeWay;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getRequirement() {
		return requirement;
	}
	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
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
	public Integer getOrderType() {
		return orderType;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
}
