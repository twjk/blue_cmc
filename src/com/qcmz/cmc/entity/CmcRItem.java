package com.qcmz.cmc.entity;

import java.util.Date;

/**
 * CmcRItem entity. @author MyEclipse Persistence Tools
 */

public class CmcRItem implements java.io.Serializable {

	// Fields

	private Long ritemid;
	private String orderid;
	private CmcROrder cmcROrder;
	private Long itemid;
	private String itemname;
	private String refid;
	private String pic;
	private Double oriprice;
	private Double price;
	private Integer itemnum = 1;
	private Date starttime;
	private Date endtime;

	// Constructors

	/** default constructor */
	public CmcRItem() {
	}

	// Property accessors

	public Long getRitemid() {
		return this.ritemid;
	}

	public void setRitemid(Long ritemid) {
		this.ritemid = ritemid;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public CmcROrder getCmcROrder() {
		return this.cmcROrder;
	}

	public void setCmcROrder(CmcROrder cmcROrder) {
		this.cmcROrder = cmcROrder;
	}

	public Long getItemid() {
		return this.itemid;
	}

	public void setItemid(Long itemid) {
		this.itemid = itemid;
	}

	public String getItemname() {
		return this.itemname;
	}

	public void setItemname(String itemname) {
		this.itemname = itemname;
	}

	public String getRefid() {
		return refid;
	}

	public void setRefid(String refid) {
		this.refid = refid;
	}

	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
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

	public Integer getItemnum() {
		return this.itemnum;
	}

	public void setItemnum(Integer itemnum) {
		this.itemnum = itemnum;
	}

}