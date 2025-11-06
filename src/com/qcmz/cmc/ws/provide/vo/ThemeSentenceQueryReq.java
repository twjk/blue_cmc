package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

/**
 * 类说明：对该类的主要功能进行说明
 * @author 李炳煜
 * 修改历史：
 */
public class ThemeSentenceQueryReq extends Request {
	/**
	 * 主题编号
	 */
	private Long themeId;
	/**
	 * 分类编号
	 */
	private String catId;
	
	public Long getThemeId() {
		return themeId;
	}
	public void setThemeId(Long themeId) {
		this.themeId = themeId;
	}
	public String getCatId() {
		return catId;
	}
	public void setCatId(String catId) {
		this.catId = catId;
	}
}
