package com.qcmz.cmc.entity;

/**
 * CmcComboPackage entity. @author MyEclipse Persistence Tools
 */

public class CmcComboPackage implements java.io.Serializable {

	// Fields

	private Long pkgid;
	private Long comboid;
	private CmcCombo cmcCombo;
	private String pkgtitle;
	private String pkgoverview;
	private Integer num;
	private Double price;
	private Integer perbuytimes;
	private Integer status;

	// Constructors

	/** default constructor */
	public CmcComboPackage() {
	}

	// Property accessors

	public Long getPkgid() {
		return this.pkgid;
	}

	public void setPkgid(Long pkgid) {
		this.pkgid = pkgid;
	}

	public Long getComboid() {
		return comboid;
	}

	public void setComboid(Long comboid) {
		this.comboid = comboid;
	}

	public CmcCombo getCmcCombo() {
		return this.cmcCombo;
	}

	public void setCmcCombo(CmcCombo cmcCombo) {
		this.cmcCombo = cmcCombo;
	}

	public String getPkgtitle() {
		return this.pkgtitle;
	}

	public void setPkgtitle(String pkgtitle) {
		this.pkgtitle = pkgtitle;
	}

	public String getPkgoverview() {
		return pkgoverview;
	}

	public void setPkgoverview(String pkgoverview) {
		this.pkgoverview = pkgoverview;
	}

	public Integer getNum() {
		return this.num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getPerbuytimes() {
		return perbuytimes;
	}

	public void setPerbuytimes(Integer perbuytimes) {
		this.perbuytimes = perbuytimes;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}