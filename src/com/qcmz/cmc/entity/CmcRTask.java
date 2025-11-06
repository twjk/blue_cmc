package com.qcmz.cmc.entity;

import com.qcmz.umc.ws.provide.vo.UserSimpleBean;

/**
 * CmcRDub entity. @author MyEclipse Persistence Tools
 */

public class CmcRTask implements java.io.Serializable {

	// Fields

	private Long id;
	private String orderid;
	private CmcROrder cmcROrder;
	private Long userid;
	private String content;
	private String url;
	private String picurl;
	private Double price;
	private Integer peoplenum;
	private Integer days;
	private Long taskid;
	private CmcCtTask cmcCtTask;
	
	private UserSimpleBean user;

	// Constructors

	/** default constructor */
	public CmcRTask() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public CmcROrder getCmcROrder() {
		return cmcROrder;
	}

	public void setCmcROrder(CmcROrder cmcROrder) {
		this.cmcROrder = cmcROrder;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPicurl() {
		return picurl;
	}

	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getPeoplenum() {
		return peoplenum;
	}

	public void setPeoplenum(Integer peoplenum) {
		this.peoplenum = peoplenum;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public UserSimpleBean getUser() {
		return user;
	}

	public void setUser(UserSimpleBean user) {
		this.user = user;
	}

	public Long getTaskid() {
		return taskid;
	}

	public void setTaskid(Long taskid) {
		this.taskid = taskid;
	}

	public CmcCtTask getCmcCtTask() {
		return cmcCtTask;
	}

	public void setCmcCtTask(CmcCtTask cmcCtTask) {
		this.cmcCtTask = cmcCtTask;
	}
}