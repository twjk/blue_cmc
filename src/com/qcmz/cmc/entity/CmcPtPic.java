package com.qcmz.cmc.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.qcmz.umc.ws.provide.vo.UserSimpleBean;



/**
 * CmcPtPic entity. @author MyEclipse Persistence Tools
 */

public class CmcPtPic  implements java.io.Serializable {


    // Fields    

     private String picid;
     private String orderid;
     private CmcROrder cmcROrder;
     private Long userid;
     private String transway;
     private String picurl;
     private String thumburl;
     private String src;
     private String fromlang;
     private String tolang;
     private String transcd;
     private String transname;
     private Date transtime;

     private String dst;
     private List<CmcPtTrans> trans = new ArrayList<CmcPtTrans>();
     private UserSimpleBean user;
     
    // Constructors

    /** default constructor */
    public CmcPtPic() {
    }

	public String getPicid() {
		return picid;
	}

	public void setPicid(String picid) {
		this.picid = picid;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getTransway() {
		return transway;
	}

	public void setTransway(String transway) {
		this.transway = transway;
	}

	public String getPicurl() {
		return picurl;
	}

	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}

	public String getThumburl() {
		return thumburl;
	}

	public void setThumburl(String thumburl) {
		this.thumburl = thumburl;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
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

	public String getDst() {
		return dst;
	}

	public void setDst(String dst) {
		this.dst = dst;
	}

	public List<CmcPtTrans> getTrans() {
		return trans;
	}

	public void setTrans(List<CmcPtTrans> trans) {
		this.trans = trans;
	}

	public CmcROrder getCmcROrder() {
		return cmcROrder;
	}

	public void setCmcROrder(CmcROrder cmcROrder) {
		this.cmcROrder = cmcROrder;
	}

	public UserSimpleBean getUser() {
		return user;
	}

	public void setUser(UserSimpleBean user) {
		this.user = user;
	}

	public String getTranscd() {
		return transcd;
	}

	public void setTranscd(String transcd) {
		this.transcd = transcd;
	}

	public String getTransname() {
		return transname;
	}

	public void setTransname(String transname) {
		this.transname = transname;
	}

	public Date getTranstime() {
		return transtime;
	}

	public void setTranstime(Date transtime) {
		this.transtime = transtime;
	}
}