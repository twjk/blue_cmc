package com.qcmz.cmc.entity;

/**
 * CmcProductItem entity. @author MyEclipse Persistence Tools
 */

public class CmcProductItem implements java.io.Serializable {

	// Fields

	private Long itemid;
	private Long productid;
	private CmcProduct cmcProduct;
	private String itemname;
	private String iapid;
	private Double oriprice;
	private Double price;
	private Integer pricetype;
	private Integer minnum;
	private Integer maxnum;
	private String pic;
	private String slogan;
	private String overview;
	private String remark;
	private Integer sortindex;
	private Integer status;

	// Constructors

	/** default constructor */
	public CmcProductItem() {
	}

	// Property accessors

	public Long getItemid() {
		return this.itemid;
	}

	public void setItemid(Long itemid) {
		this.itemid = itemid;
	}

	public Long getProductid() {
		return productid;
	}

	public void setProductid(Long productid) {
		this.productid = productid;
	}

	public CmcProduct getCmcProduct() {
		return this.cmcProduct;
	}

	public void setCmcProduct(CmcProduct cmcProduct) {
		this.cmcProduct = cmcProduct;
	}

	public String getItemname() {
		return this.itemname;
	}

	public void setItemname(String itemname) {
		this.itemname = itemname;
	}

	public String getIapid() {
		return this.iapid;
	}

	public void setIapid(String iapid) {
		this.iapid = iapid;
	}

	public Double getOriprice() {
		return this.oriprice;
	}

	public void setOriprice(Double oriprice) {
		this.oriprice = oriprice;
	}

	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getPricetype() {
		return this.pricetype;
	}

	public void setPricetype(Integer pricetype) {
		this.pricetype = pricetype;
	}

	public Integer getMinnum() {
		return this.minnum;
	}

	public void setMinnum(Integer minnum) {
		this.minnum = minnum;
	}

	public Integer getMaxnum() {
		return this.maxnum;
	}

	public void setMaxnum(Integer maxnum) {
		this.maxnum = maxnum;
	}

	public String getPic() {
		return this.pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getSlogan() {
		return this.slogan;
	}

	public void setSlogan(String slogan) {
		this.slogan = slogan;
	}

	public String getOverview() {
		return this.overview;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public Integer getSortindex() {
		return this.sortindex;
	}

	public void setSortindex(Integer sortindex) {
		this.sortindex = sortindex;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}