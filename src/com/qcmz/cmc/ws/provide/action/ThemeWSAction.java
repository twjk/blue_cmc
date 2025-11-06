package com.qcmz.cmc.ws.provide.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.ws.provide.service.ThemeInterface;
import com.qcmz.cmc.ws.provide.vo.ThemeDownloadReq;
import com.qcmz.cmc.ws.provide.vo.ThemeQueryReq;
import com.qcmz.cmc.ws.provide.vo.ThemeReq;
import com.qcmz.cmc.ws.provide.vo.ThemeSentenceQueryReq;
import com.qcmz.framework.action.BaseWSAction;

/**
 * 类说明：日常用语主题接口
 * 修改历史：
 */
public class ThemeWSAction extends BaseWSAction {
	
	@Autowired
	private ThemeInterface themeInterface;
	
	/**
	 * 主题编号
	 */
	private Long themeId;
	/**
	 * 主题代码
	 */
	private String themeCode;
	/**
	 * 分类编号
	 */
	private String catId;
	/**
	 * 当前页数
	 */
	private String page;
	/**
	 * 每页记录数
	 */
	private String pageSize;
	
	/**
	 * 分页查询主题
	 * @return
	 * 修改历史：
	 */
	public String findTheme(){
		ThemeQueryReq req = new ThemeQueryReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		jsonObj = themeInterface.findTheme(req, page, pageSize, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 获取主题信息
	 * @return
	 * 修改历史：
	 */
	public String getTheme(){
		ThemeReq req = new ThemeReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		req.setThemeId(themeId);
		
		jsonObj = themeInterface.getTheme(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 获取主题信息
	 * @return
	 * 修改历史：
	 */
	public String getDownload(){
		ThemeDownloadReq req = new ThemeDownloadReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		req.setThemeId(themeId);
		req.setThemeCode(themeCode);
		
		jsonObj = themeInterface.getDownload(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 分页获取主题下语句信息
	 * @return
	 * 修改历史：
	 */
	public String findSentence(){
		ThemeSentenceQueryReq req = new ThemeSentenceQueryReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		req.setThemeId(themeId);
		req.setCatId(catId);
		
		jsonObj = themeInterface.findSentence(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}

	public Long getThemeId() {
		return themeId;
	}

	public void setThemeId(Long themeId) {
		this.themeId = themeId;
	}

	public String getThemeCode() {
		return themeCode;
	}

	public void setThemeCode(String themeCode) {
		this.themeCode = themeCode;
	}

	public String getCatId() {
		return catId;
	}

	public void setCatId(String catId) {
		this.catId = catId;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
}
