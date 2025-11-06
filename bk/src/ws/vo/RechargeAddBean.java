package com.qcmz.cmc.ws.provide.vo;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class RechargeAddBean {
	/**
	 * 用户编号
	 */
	private Long userId;
	/**
	 * 产品编号
	 */
	private Long productId;
	/**
	 * 产品代码
	 */
	private String productCode;
	/**
	 * 数量
	 */
	private Integer num;
	/**
	 * 消费金额
	 */
	private Double amount;
	/**
	 * 支付代理编号
	 */
	private Long proxyId;
	/**
	 * 代理支付流水号
	 */
	private String serial;
	/**
	 * 充值时间，格式yyyy-MM-dd HH:mm:ss
	 */
	private String time;

	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Long getProxyId() {
		return proxyId;
	}
	public void setProxyId(Long proxyId) {
		this.proxyId = proxyId;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
}
