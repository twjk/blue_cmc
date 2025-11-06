package com.qcmz.cmc.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.umc.ws.provide.vo.UserSimpleBean;

/**
 * CmcROrder entity. @author MyEclipse Persistence Tools
 */

public class CmcROrder implements java.io.Serializable {

	// Fields

	private String orderid;
	private Long userid;
	private Integer usertype = 1;
	private String employeeid;
	private String employeename;
	private String servicetype;
	private Integer ordercat;
	private Integer ordertype;
	private String fromlang;
	private String tolang;
	private Double amount;
	private Double couponamount = 0D;
	private Double walletamount = 0D;
	private Double payableamount;
	private Double payamount = 0D;
	private String iapid;
	private String tradeway;
	private Long ucid;
	private CmcUCombo cmcUCombo;
	private Double commissionamount;
	private Date commissiondate;
	private Integer commissionstatus;
	private Date createtime;
	private Date paytime;
	private Integer appointflag = 0;
	private Date appointtime;
	private Date waittime;
	private Date opertime;
	private Long needtime;
    private Date finishtime;
	private String dealstatus;
	private String dealprogress;
    private String opercd;
    private String opername;
    private Integer checkstatus;
    private Integer revise = 0;
    private String checkcd;
    private String checkname;
    private Date checkstarttime;
    private Date checkfinishtime;
    private String checkresult;
    private String requirement;
    private String mobile;
    private String invitecode;
    private String platform;
    private String version;
    private String address;
    private String lon;
    private String lat;
    private String payplatform;
	private Integer status = 1;
	private Integer pickstatus = 0;
	private Integer evalstatus = 0;
	private Integer soondealstatus;
	
	private String needTimeStr;
	private String dealTimeStr;
	
	private List<CmcRTrade> cmcRTrades = new ArrayList<CmcRTrade>(0);
	private List<CmcRMsg> cmcRMsgs = new ArrayList<CmcRMsg>(0);
	private List<CmcRLog> cmcRLogs = new ArrayList<CmcRLog>(0);
	private List<CmcRItem> cmcRItems = new ArrayList<CmcRItem>(0);
	
	private CmcPtPic cmcPtPic;
	private CmcTtTrans cmcTtTrans;
	private CmcVtTrans cmcVtTrans;
	private CmcRPackage cmcRPackage;
	private CmcRCombo cmcRCombo;
	private CmcRDub cmcRDub;
	private CmcRTask cmcRTask;
	
	private CmcREval cmcREval;
	
	private UserSimpleBean user;

	// Constructors

	/** default constructor */
	public CmcROrder() {
	}

	// Property accessors

	public String getOrderid() {
		return this.orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public Long getUserid() {
		return this.userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Integer getUsertype() {
		return usertype;
	}

	public void setUsertype(Integer usertype) {
		this.usertype = usertype;
	}

	public String getEmployeeid() {
		return employeeid;
	}

	public void setEmployeeid(String employeeid) {
		this.employeeid = employeeid;
	}

	public String getEmployeename() {
		return employeename;
	}

	public void setEmployeename(String employeename) {
		this.employeename = employeename;
	}

	public Integer getOrdercat() {
		return ordercat;
	}

	public void setOrdercat(Integer ordercat) {
		this.ordercat = ordercat;
	}

	public Integer getOrdertype() {
		return this.ordertype;
	}

	public void setOrdertype(Integer ordertype) {
		this.ordertype = ordertype;
	}

	public String getFromlang() {
		return fromlang;
	}

	public void setFromlang(String fromlang) {
		this.fromlang = fromlang;
	}

	public String getTolang() {
		return tolang;
	}

	public void setTolang(String tolang) {
		this.tolang = tolang;
	}

	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getCouponamount() {
		return this.couponamount;
	}

	public void setCouponamount(Double couponamount) {
		this.couponamount = couponamount;
	}

	public Double getWalletamount() {
		return walletamount;
	}

	public void setWalletamount(Double walletamount) {
		this.walletamount = walletamount;
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

	public String getServicetype() {
		return servicetype;
	}

	public void setServicetype(String servicetype) {
		this.servicetype = servicetype;
	}

	public String getPlatform() {
		return this.platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDealstatus() {
		return this.dealstatus;
	}

	public void setDealstatus(String dealstatus) {
		this.dealstatus = dealstatus;
	}

	public String getDealprogress() {
		return dealprogress;
	}

	public void setDealprogress(String dealprogress) {
		this.dealprogress = dealprogress;
	}

	public String getOpercd() {
		return opercd;
	}

	public void setOpercd(String opercd) {
		this.opercd = opercd;
	}

	public String getOpername() {
		return opername;
	}

	public void setOpername(String opername) {
		this.opername = opername;
	}

	public Date getOpertime() {
		return opertime;
	}

	public void setOpertime(Date opertime) {
		this.opertime = opertime;
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

	public Integer getStatus() {
		return this.status;
	}

	public Integer getAppointflag() {
		return appointflag;
	}

	public void setAppointflag(Integer appointflag) {
		this.appointflag = appointflag;
	}

	public Date getAppointtime() {
		return appointtime;
	}

	public void setAppointtime(Date appointtime) {
		this.appointtime = appointtime;
	}

	public Date getWaittime() {
		return waittime;
	}

	public void setWaittime(Date waittime) {
		this.waittime = waittime;
	}

	public Date getFinishtime() {
		return finishtime;
	}

	public void setFinishtime(Date finishtime) {
		this.finishtime = finishtime;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getPickstatus() {
		return pickstatus;
	}

	public void setPickstatus(Integer pickstatus) {
		this.pickstatus = pickstatus;
	}

	public List<CmcRTrade> getCmcRTrades() {
		return cmcRTrades;
	}

	public void setCmcRTrades(List<CmcRTrade> cmcRTrades) {
		this.cmcRTrades = cmcRTrades;
	}

	public List<CmcRMsg> getCmcRMsgs() {
		return cmcRMsgs;
	}

	public void setCmcRMsgs(List<CmcRMsg> cmcRMsgs) {
		this.cmcRMsgs = cmcRMsgs;
	}

	public List<CmcRLog> getCmcRLogs() {
		return cmcRLogs;
	}

	public void setCmcRLogs(List<CmcRLog> cmcRLogs) {
		this.cmcRLogs = cmcRLogs;
	}

	public List<CmcRItem> getCmcRItems() {
		return cmcRItems;
	}

	public void setCmcRItems(List<CmcRItem> cmcRItems) {
		this.cmcRItems = cmcRItems;
	}

	public String getIapid() {
		return iapid;
	}

	public void setIapid(String iapid) {
		this.iapid = iapid;
	}

	public String getPayplatform() {
		return payplatform;
	}

	public void setPayplatform(String payplatform) {
		this.payplatform = payplatform;
	}

	public String getInvitecode() {
		return invitecode;
	}

	public void setInvitecode(String invitecode) {
		this.invitecode = invitecode;
	}

	public CmcPtPic getCmcPtPic() {
		return cmcPtPic;
	}

	public void setCmcPtPic(CmcPtPic cmcPtPic) {
		this.cmcPtPic = cmcPtPic;
	}

	public CmcTtTrans getCmcTtTrans() {
		return cmcTtTrans;
	}

	public void setCmcTtTrans(CmcTtTrans cmcTtTrans) {
		this.cmcTtTrans = cmcTtTrans;
	}

	public CmcVtTrans getCmcVtTrans() {
		return cmcVtTrans;
	}

	public void setCmcVtTrans(CmcVtTrans cmcVtTrans) {
		this.cmcVtTrans = cmcVtTrans;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public CmcRPackage getCmcRPackage() {
		return cmcRPackage;
	}

	public void setCmcRPackage(CmcRPackage cmcRPackage) {
		this.cmcRPackage = cmcRPackage;
	}

	public UserSimpleBean getUser() {
		return user;
	}

	public void setUser(UserSimpleBean user) {
		this.user = user;
	}

	public Integer getCheckstatus() {
		return checkstatus;
	}

	public void setCheckstatus(Integer checkstatus) {
		this.checkstatus = checkstatus;
	}

	public Integer getRevise() {
		return revise;
	}

	public void setRevise(Integer revise) {
		this.revise = revise;
	}

	public String getCheckcd() {
		return checkcd;
	}

	public void setCheckcd(String checkcd) {
		this.checkcd = checkcd;
	}

	public String getCheckname() {
		return checkname;
	}

	public void setCheckname(String checkname) {
		this.checkname = checkname;
	}
	public Date getCheckstarttime() {
		return checkstarttime;
	}

	public void setCheckstarttime(Date checkstarttime) {
		this.checkstarttime = checkstarttime;
	}

	public Date getCheckfinishtime() {
		return checkfinishtime;
	}

	public void setCheckfinishtime(Date checkfinishtime) {
		this.checkfinishtime = checkfinishtime;
	}

	public String getCheckresult() {
		return checkresult;
	}

	public void setCheckresult(String checkresult) {
		this.checkresult = checkresult;
	}

	public Long getUcid() {
		return ucid;
	}

	public void setUcid(Long ucid) {
		this.ucid = ucid;
	}

	public CmcUCombo getCmcUCombo() {
		return cmcUCombo;
	}

	public void setCmcUCombo(CmcUCombo cmcUCombo) {
		this.cmcUCombo = cmcUCombo;
	}

	public Double getCommissionamount() {
		return commissionamount;
	}

	public void setCommissionamount(Double commissionamount) {
		this.commissionamount = commissionamount;
	}

	public Date getCommissiondate() {
		return commissiondate;
	}

	public void setCommissiondate(Date commissiondate) {
		this.commissiondate = commissiondate;
	}

	public Integer getCommissionstatus() {
		return commissionstatus;
	}

	public void setCommissionstatus(Integer commissionstatus) {
		this.commissionstatus = commissionstatus;
	}

	public CmcRCombo getCmcRCombo() {
		return cmcRCombo;
	}

	public void setCmcRCombo(CmcRCombo cmcRCombo) {
		this.cmcRCombo = cmcRCombo;
	}

	public CmcRDub getCmcRDub() {
		return cmcRDub;
	}

	public void setCmcRDub(CmcRDub cmcRDub) {
		this.cmcRDub = cmcRDub;
	}

	public CmcRTask getCmcRTask() {
		return cmcRTask;
	}

	public void setCmcRTask(CmcRTask cmcRTask) {
		this.cmcRTask = cmcRTask;
	}

	public Integer getEvalstatus() {
		return evalstatus;
	}

	public void setEvalstatus(Integer evalstatus) {
		this.evalstatus = evalstatus;
	}

	public Integer getSoondealstatus() {
		return soondealstatus;
	}

	public void setSoondealstatus(Integer soondealstatus) {
		this.soondealstatus = soondealstatus;
	}

	public CmcREval getCmcREval() {
		return cmcREval;
	}

	public Long getNeedtime() {
		return needtime;
	}

	public void setNeedtime(Long needtime) {
		this.needtime = needtime;
	}

	public String getNeedTimeStr() {
		if(StringUtil.isBlankOrNull(needTimeStr) && needtime!=null){
			return DateUtil.formatTime(needtime);
		}
		return "";
	}

	public void setNeedTimeStr(String needTimeStr) {
		this.needTimeStr = needTimeStr;
	}

	public String getDealTimeStr() {
		if(StringUtil.isBlankOrNull(dealTimeStr)
				&& opertime!=null && finishtime!=null){
			return DateUtil.formatTime(DateUtil.betweenSecond(opertime, finishtime));
		}
		return "";
	}

	public void setDealTimeStr(String dealTimeStr) {
		this.dealTimeStr = dealTimeStr;
	}

	public void setCmcREval(CmcREval cmcREval) {
		this.cmcREval = cmcREval;
	}
}