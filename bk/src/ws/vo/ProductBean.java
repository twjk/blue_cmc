package com.qcmz.cmc.ws.provide.vo;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class ProductBean {
	/**
	 * 产品编号
	 */
	private Long productId;
	/**
	 * 产品名称
	 */
	private String productName;
	/**
	 * 产品代码
	 */
	private String productCode;
	/**
	 * 单价
	 */
	private Double price;
	/**
	 * 交易状态
	 */
	private String tradeStatus;
	/**
	 * 交易状态描述
	 */
	private String tradeStatusDesc;
	/**
	 * 描述
	 */
	private String description;
	
	public ProductBean() {
		super();
	}
	
	public ProductBean(Long productId, String productName, String productCode,
			Double price, String description) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.productCode = productCode;
		this.price = price;
		this.description = description;
	}

	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public String getTradeStatus() {
		return tradeStatus;
	}

	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	public String getTradeStatusDesc() {
		return tradeStatusDesc;
	}

	public void setTradeStatusDesc(String tradeStatusDesc) {
		this.tradeStatusDesc = tradeStatusDesc;
	}
}
