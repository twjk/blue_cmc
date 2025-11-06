package com.qcmz.cmc.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * CmcTpPrice entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CmcTpPrice implements java.io.Serializable {

	// Fields

	private Long priceid;
	private String transtype;
	private String transway;
	private Integer priceunit;
	private Double price;
	private Double oriprice;
	private Double oristartprice;
	private Double startprice;
	private Integer startnum;
	private Integer timeout;
	private String starttime;
	private String endtime;
	private List<CmcTpIntl> cmcTpIntls = new ArrayList<CmcTpIntl>(0);
	private List<CmcTpLang> cmcTpLangs = new ArrayList<CmcTpLang>(0);

	// Constructors

	/** default constructor */
	public CmcTpPrice() {
	}

	// Property accessors

	public Long getPriceid() {
		return this.priceid;
	}

	public void setPriceid(Long priceid) {
		this.priceid = priceid;
	}

	public String getTranstype() {
		return this.transtype;
	}

	public void setTranstype(String transtype) {
		this.transtype = transtype;
	}

	public String getTransway() {
		return this.transway;
	}

	public void setTransway(String transway) {
		this.transway = transway;
	}
	
	public Integer getPriceunit() {
		return priceunit;
	}

	public void setPriceunit(Integer priceunit) {
		this.priceunit = priceunit;
	}

	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getOristartprice() {
		return oristartprice;
	}

	public void setOristartprice(Double oristartprice) {
		this.oristartprice = oristartprice;
	}

	public Double getOriprice() {
		return this.oriprice;
	}

	public void setOriprice(Double oriprice) {
		this.oriprice = oriprice;
	}

	public Double getStartprice() {
		return startprice;
	}

	public void setStartprice(Double startprice) {
		this.startprice = startprice;
	}

	public Integer getStartnum() {
		return startnum;
	}

	public void setStartnum(Integer startnum) {
		this.startnum = startnum;
	}

	public Integer getTimeout() {
		return this.timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	public String getStarttime() {
		return this.starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return this.endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public List<CmcTpIntl> getCmcTpIntls() {
		return cmcTpIntls;
	}

	public void setCmcTpIntls(List<CmcTpIntl> cmcTpIntls) {
		this.cmcTpIntls = cmcTpIntls;
	}

	public List<CmcTpLang> getCmcTpLangs() {
		return cmcTpLangs;
	}

	public void setCmcTpLangs(List<CmcTpLang> cmcTpLangs) {
		this.cmcTpLangs = cmcTpLangs;
	}
}