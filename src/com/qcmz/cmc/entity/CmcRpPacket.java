package com.qcmz.cmc.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.qcmz.umc.ws.provide.vo.UserSimpleBean;

/**
 * CmcRpPacket entity. @author MyEclipse Persistence Tools
 */

public class CmcRpPacket implements java.io.Serializable {

	// Fields

	private String packetid;
	private Long userid;
	
	private UserSimpleBean user;//隔外加的用户信息
	
	private String title;
	private String langcode;
	private String titlecn;
	private Integer packetnum;
	private Integer receivednum = 0;
	private Double amount;
	private Double packetamount;
	private Double serviceamount;
	private Double deductamount;
	private Double payableamount;
	private Double payamount;
	private String tradeway;
	private String outtradeid;
	private Date paytime;
	private Date createtime;
	private Date starttime;
	private Date endtime;
	private String platform;
    private String version;
    private String payplatform;
	private Integer status;
	private List<CmcRpReceive> cmcRpReceives = new ArrayList<CmcRpReceive>(0);

	// Constructors

	/** default constructor */
	public CmcRpPacket() {
	}

	// Property accessors

	public String getPacketid() {
		return this.packetid;
	}

	public void setPacketid(String packetid) {
		this.packetid = packetid;
	}

	public Long getUserid() {
		return this.userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLangcode() {
		return this.langcode;
	}

	public void setLangcode(String langcode) {
		this.langcode = langcode;
	}

	public String getTitlecn() {
		return titlecn;
	}

	public void setTitlecn(String titlecn) {
		this.titlecn = titlecn;
	}

	public Integer getPacketnum() {
		return this.packetnum;
	}

	public void setPacketnum(Integer packetnum) {
		this.packetnum = packetnum;
	}

	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getPacketamount() {
		return this.packetamount;
	}

	public void setPacketamount(Double packetamount) {
		this.packetamount = packetamount;
	}

	public Integer getReceivednum() {
		return receivednum;
	}

	public void setReceivednum(Integer receivednum) {
		this.receivednum = receivednum;
	}

	public Double getServiceamount() {
		return this.serviceamount;
	}

	public void setServiceamount(Double serviceamount) {
		this.serviceamount = serviceamount;
	}

	public Double getDeductamount() {
		return this.deductamount;
	}

	public void setDeductamount(Double deductamount) {
		this.deductamount = deductamount;
	}

	public Double getPayableamount() {
		return this.payableamount;
	}

	public void setPayableamount(Double payableamount) {
		this.payableamount = payableamount;
	}

	public Double getPayamount() {
		return this.payamount;
	}

	public void setPayamount(Double payamount) {
		this.payamount = payamount;
	}

	public String getTradeway() {
		return this.tradeway;
	}

	public void setTradeway(String tradeway) {
		this.tradeway = tradeway;
	}

	public String getOuttradeid() {
		return this.outtradeid;
	}

	public void setOuttradeid(String outtradeid) {
		this.outtradeid = outtradeid;
	}

	public Date getPaytime() {
		return this.paytime;
	}

	public void setPaytime(Date paytime) {
		this.paytime = paytime;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public Date getEndtime() {
		return this.endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getPayplatform() {
		return payplatform;
	}

	public void setPayplatform(String payplatform) {
		this.payplatform = payplatform;
	}

	public List<CmcRpReceive> getCmcRpReceives() {
		return cmcRpReceives;
	}

	public void setCmcRpReceives(List<CmcRpReceive> cmcRpReceives) {
		this.cmcRpReceives = cmcRpReceives;
	}

	public UserSimpleBean getUser() {
		return user;
	}

	public void setUser(UserSimpleBean user) {
		this.user = user;
	}
}