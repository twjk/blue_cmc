package com.qcmz.cmc.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.log.BeanDesc;
import com.qcmz.umc.ws.provide.vo.UserSimpleBean;

/**
 * CmcUCombo entity. @author MyEclipse Persistence Tools
 */

public class CmcUCombo implements java.io.Serializable {

	// Fields
	@BeanDesc(desc="用户套餐编号")
	private Long ucid;
	@BeanDesc(desc="用户编号")
	private Long userid;
	@BeanDesc(desc="套餐编号")
	private Long comboid;
	private CmcCombo cmcCombo;
	@BeanDesc(desc="套餐包")
	private Long pkgid;
	@BeanDesc(desc="套餐名称")
	private String combotitle;
	@BeanDesc(desc="翻译类型")
	private String transtype;
	@BeanDesc(desc="套餐类型")
	private Integer combotype;
	@BeanDesc(desc="数量")
	private Integer num;
	@BeanDesc(desc="可用数")
	private Integer usablenum;
	@BeanDesc(desc="单位")
	private Integer unit;
	@BeanDesc(desc="已用次数")
	private Integer usedtimes = 0;
	@BeanDesc(desc="开始时间")
	private Date starttime;
	@BeanDesc(desc="结束时间")
	private Date endtime;
	@BeanDesc(desc="订单号")
	private String orderid;
	@BeanDesc(desc="创建时间")
	private Date createtime;
	@BeanDesc(desc="兑换码")
	private String exchangecode;
	@BeanDesc(desc="兑换状态")
	private Integer exchangestatus;
	@BeanDesc(desc="兑换时间")
	private Date exchangetime;
	@BeanDesc(desc="付款方")
	private Integer payside;
	private List<CmcUCombohis> hises = new ArrayList<CmcUCombohis>(0);

	private UserSimpleBean user;
	private Integer usableNumReal;
	
	// Constructors

	/** default constructor */
	public CmcUCombo() {
	}

	// Property accessors
	public Long getUserid() {
		return this.userid;
	}

	public Long getUcid() {
		return ucid;
	}

	public void setUcid(Long ucid) {
		this.ucid = ucid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Long getComboid() {
		return this.comboid;
	}

	public void setComboid(Long comboid) {
		this.comboid = comboid;
	}

	public CmcCombo getCmcCombo() {
		return cmcCombo;
	}

	public void setCmcCombo(CmcCombo cmcCombo) {
		this.cmcCombo = cmcCombo;
	}

	public String getTranstype() {
		return this.transtype;
	}

	public void setTranstype(String transtype) {
		this.transtype = transtype;
	}

	public Integer getCombotype() {
		return combotype;
	}

	public void setCombotype(Integer combotype) {
		this.combotype = combotype;
	}

	public Integer getNum() {
		return this.num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getUsablenum() {
		return usablenum;
	}
	
	public void setUsablenum(Integer usablenum) {
		this.usablenum = usablenum;
	}
	
	public Integer getUsableNumReal() {
		if(DictConstant.DICT_TRANSCOMBO_UNIT_DAY.equals(this.unit)){
			usableNumReal = DateUtil.betweenDay(this.starttime, this.endtime)+1;
		}else{
			usableNumReal = this.usablenum;
		}
		return usableNumReal;
	}

	public Integer getUnit() {
		return this.unit;
	}

	public void setUnit(Integer unit) {
		this.unit = unit;
	}

	public Integer getUsedtimes() {
		return usedtimes;
	}

	public void setUsedtimes(Integer usedtimes) {
		this.usedtimes = usedtimes;
	}

	public Date getStarttime() {
		return this.starttime;
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

	public String getOrderid() {
		return this.orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
	public List<CmcUCombohis> getHises() {
		return hises;
	}

	public void setHises(List<CmcUCombohis> hises) {
		this.hises = hises;
	}

	public Long getPkgid() {
		return pkgid;
	}

	public void setPkgid(Long pkgid) {
		this.pkgid = pkgid;
	}

	public String getCombotitle() {
		return combotitle;
	}

	public void setCombotitle(String combotitle) {
		this.combotitle = combotitle;
	}

	public String getExchangecode() {
		return exchangecode;
	}

	public void setExchangecode(String exchangecode) {
		this.exchangecode = exchangecode;
	}

	public Integer getExchangestatus() {
		return exchangestatus;
	}

	public void setExchangestatus(Integer exchangestatus) {
		this.exchangestatus = exchangestatus;
	}

	public Date getExchangetime() {
		return exchangetime;
	}

	public void setExchangetime(Date exchangetime) {
		this.exchangetime = exchangetime;
	}

	public Integer getPayside() {
		return payside;
	}

	public void setPayside(Integer payside) {
		this.payside = payside;
	}

	public UserSimpleBean getUser() {
		return user;
	}

	public void setUser(UserSimpleBean user) {
		this.user = user;
	}
}