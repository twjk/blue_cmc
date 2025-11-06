package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

public class UserTransComboBean {
	/**
	 * 用户套餐编号
	 */
	private Long ucid;
	/**
	 * 适用翻译类型
	 */
	private String transType;
	/**
	 * 套餐类型
	 */
	private Integer comboType;
	/**
	 * 总数量
	 */
	private Integer num;
	/**
	 * 可用量
	 */
	private Integer usableNum;
	/**
	 * 单位，1次2日
	 */
	private Integer unit;
	/**
	 * 单位名称
	 */
	private String unitName;
	/**
	 * 有效期开始时间
	 */
	private Long startTime;
	/**
	 * 有效期结束时间
	 */
	private Long endTime;
	/**
	 * 套餐编号
	 */
	private Long comboId;
	/**
	 * 套餐名称
	 */
	private String comboTitle;
	/**
	 * 套餐图标
	 */
	private String comboIcon;
	/**
	 * 套餐详述
	 */
	private String comboDesc;
	/**
	 * 服务途径
	 */
	private Integer serviceWay;
	/**
	 * 服务账号
	 */
	private String serviceAccount;
	/**
	 * 限定使用的语言列表，空表示均可使用
	 */
	private List<TransComboLangBean> langs = new ArrayList<TransComboLangBean>();
	/**
	 * 限定使用的场景列表，空表示均可使用
	 */
	private List<SceneBean> scenes = new ArrayList<SceneBean>();
	
	public Long getUcid() {
		return ucid;
	}
	public void setUcid(Long ucid) {
		this.ucid = ucid;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public Integer getComboType() {
		return comboType;
	}
	public void setComboType(Integer comboType) {
		this.comboType = comboType;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Integer getUsableNum() {
		return usableNum;
	}
	public void setUsableNum(Integer usableNum) {
		this.usableNum = usableNum;
	}
	public Integer getUnit() {
		return unit;
	}
	public void setUnit(Integer unit) {
		this.unit = unit;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public Long getStartTime() {
		return startTime;
	}
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	public Long getComboId() {
		return comboId;
	}
	public void setComboId(Long comboId) {
		this.comboId = comboId;
	}
	public String getComboTitle() {
		return comboTitle;
	}
	public void setComboTitle(String comboTitle) {
		this.comboTitle = comboTitle;
	}
	public String getComboIcon() {
		return comboIcon;
	}
	public void setComboIcon(String comboIcon) {
		this.comboIcon = comboIcon;
	}
	public String getComboDesc() {
		return comboDesc;
	}
	public void setComboDesc(String comboDesc) {
		this.comboDesc = comboDesc;
	}
	public Integer getServiceWay() {
		return serviceWay;
	}
	public void setServiceWay(Integer serviceWay) {
		this.serviceWay = serviceWay;
	}
	public String getServiceAccount() {
		return serviceAccount;
	}
	public void setServiceAccount(String serviceAccount) {
		this.serviceAccount = serviceAccount;
	}
	public List<TransComboLangBean> getLangs() {
		return langs;
	}
	public void setLangs(List<TransComboLangBean> langs) {
		this.langs = langs;
	}
	public List<SceneBean> getScenes() {
		return scenes;
	}
	public void setScenes(List<SceneBean> scenes) {
		this.scenes = scenes;
	}
}
