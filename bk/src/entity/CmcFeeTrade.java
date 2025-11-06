package com.qcmz.cmc.entity;

import java.util.Date;

/**
 * CmcFeeTrade entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CmcFeeTrade implements java.io.Serializable {

	// Fields

	private Long tradeid;
	private Long userid;
	private String tradetype;
	private Integer point;
	private Double amount;
	private String item;
	private String langcode;
	private Long productid;
	private CmcFeeProduct cmcFeeProduct;
	private Integer productnum;
	private Long proxyid;
	private CmcProxy cmcProxy;
	private String serial;
	private String description;
	private Date tradetime;

	// Constructors

	/** default constructor */
	public CmcFeeTrade() {
	}

	// Property accessors

	public Long getTradeid() {
		return this.tradeid;
	}

	public void setTradeid(Long tradeid) {
		this.tradeid = tradeid;
	}

	public Long getUserid() {
		return this.userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getTradetype() {
		return this.tradetype;
	}

	public void setTradetype(String tradetype) {
		this.tradetype = tradetype;
	}

	public Integer getPoint() {
		return this.point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getItem() {
		return this.item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getLangcode() {
		return this.langcode;
	}

	public void setLangcode(String langcode) {
		this.langcode = langcode;
	}

	public Long getProductid() {
		return this.productid;
	}

	public void setProductid(Long productid) {
		this.productid = productid;
	}

	public Integer getProductnum() {
		return this.productnum;
	}

	public void setProductnum(Integer productnum) {
		this.productnum = productnum;
	}

	public Long getProxyid() {
		return this.proxyid;
	}

	public void setProxyid(Long proxyid) {
		this.proxyid = proxyid;
	}

	public String getSerial() {
		return this.serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getTradetime() {
		return this.tradetime;
	}

	public void setTradetime(Date tradetime) {
		this.tradetime = tradetime;
	}

	public CmcFeeProduct getCmcFeeProduct() {
		return cmcFeeProduct;
	}

	public void setCmcFeeProduct(CmcFeeProduct cmcFeeProduct) {
		this.cmcFeeProduct = cmcFeeProduct;
	}

	public CmcProxy getCmcProxy() {
		return cmcProxy;
	}

	public void setCmcProxy(CmcProxy cmcProxy) {
		this.cmcProxy = cmcProxy;
	}
}