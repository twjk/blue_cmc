package com.qcmz.cmc.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * CmcCombo entity. @author MyEclipse Persistence Tools
 */

public class CmcCombo implements java.io.Serializable {

	// Fields

	private Long comboid;
	private String title;
	private Integer combotype;
	private String transtype;
	private Integer priceunit;
	private String unitname;
	private Double oriunitprice;
	private Double unitprice;
	private Integer validitytype;
	private Integer cycletype;
	private Integer cyclevalue;
	private Date starttime;
	private Date endtime;
	private Integer serviceway;
	private String serviceaccount;
	private String icon;
	private String pic;
	private String tags;
	private String overview;
	private String description;
	private Integer sortindex;
	private Integer status;
	private List<CmcComboPackage> packages = new ArrayList<CmcComboPackage>(0);
	private List<CmcComboPic> pics = new ArrayList<CmcComboPic>(0);
	private List<CmcComboLang> langs = new ArrayList<CmcComboLang>(0);
	private List<CmcComboScene> scenes = new ArrayList<CmcComboScene>(0);
	private List<CmcComboPlatform> platforms = new ArrayList<CmcComboPlatform>(0);

	// Constructors

	/** default constructor */
	public CmcCombo() {
	}

	public Long getComboid() {
		return comboid;
	}

	public void setComboid(Long comboid) {
		this.comboid = comboid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getCombotype() {
		return combotype;
	}

	public void setCombotype(Integer combotype) {
		this.combotype = combotype;
	}

	public String getTranstype() {
		return transtype;
	}

	public void setTranstype(String transtype) {
		this.transtype = transtype;
	}

	public Integer getPriceunit() {
		return priceunit;
	}

	public void setPriceunit(Integer priceunit) {
		this.priceunit = priceunit;
	}

	public String getUnitname() {
		return unitname;
	}

	public void setUnitname(String unitname) {
		this.unitname = unitname;
	}

	public Double getOriunitprice() {
		return oriunitprice;
	}

	public void setOriunitprice(Double oriunitprice) {
		this.oriunitprice = oriunitprice;
	}

	public Double getUnitprice() {
		return unitprice;
	}

	public void setUnitprice(Double unitprice) {
		this.unitprice = unitprice;
	}

	public Integer getValiditytype() {
		return validitytype;
	}

	public void setValiditytype(Integer validitytype) {
		this.validitytype = validitytype;
	}

	public Integer getCycletype() {
		return cycletype;
	}

	public void setCycletype(Integer cycletype) {
		this.cycletype = cycletype;
	}

	public Integer getCyclevalue() {
		return cyclevalue;
	}

	public void setCyclevalue(Integer cyclevalue) {
		this.cyclevalue = cyclevalue;
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

	public Integer getServiceway() {
		return serviceway;
	}

	public void setServiceway(Integer serviceway) {
		this.serviceway = serviceway;
	}

	public String getServiceaccount() {
		return serviceaccount;
	}

	public void setServiceaccount(String serviceaccount) {
		this.serviceaccount = serviceaccount;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getSortindex() {
		return sortindex;
	}

	public void setSortindex(Integer sortindex) {
		this.sortindex = sortindex;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<CmcComboLang> getLangs() {
		return langs;
	}

	public void setLangs(List<CmcComboLang> langs) {
		this.langs = langs;
	}

	public List<CmcComboPackage> getPackages() {
		return packages;
	}

	public void setPackages(List<CmcComboPackage> packages) {
		this.packages = packages;
	}

	public List<CmcComboScene> getScenes() {
		return scenes;
	}

	public void setScenes(List<CmcComboScene> scenes) {
		this.scenes = scenes;
	}

	public List<CmcComboPic> getPics() {
		return pics;
	}

	public void setPics(List<CmcComboPic> pics) {
		this.pics = pics;
	}

	public List<CmcComboPlatform> getPlatforms() {
		return platforms;
	}

	public void setPlatforms(List<CmcComboPlatform> platforms) {
		this.platforms = platforms;
	}
}