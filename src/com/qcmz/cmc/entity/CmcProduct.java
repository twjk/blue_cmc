package com.qcmz.cmc.entity;

import java.util.HashSet;
import java.util.Set;

/**
 * CmcProduct entity. @author MyEclipse Persistence Tools
 */

public class CmcProduct implements java.io.Serializable {

	// Fields

	private Long productid;
	private String title;
	private String content;
	private Integer status;

	// Constructors

	/** default constructor */
	public CmcProduct() {
	}

	// Property accessors

	public Long getProductid() {
		return this.productid;
	}

	public void setProductid(Long productid) {
		this.productid = productid;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}