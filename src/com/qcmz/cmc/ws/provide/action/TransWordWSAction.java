package com.qcmz.cmc.ws.provide.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.ws.provide.service.TransWordInterface;
import com.qcmz.cmc.ws.provide.vo.TransWordCountryQueryReq;
import com.qcmz.cmc.ws.provide.vo.TransWordQueryReq;
import com.qcmz.framework.action.BaseWSAction;

/**
 * 类说明：分词接口
 * 修改历史：
 */
public class TransWordWSAction extends BaseWSAction {
	@Autowired
	private TransWordInterface transWordInterface;
	
	/**
	 * 语言代码
	 */
	private String langCode;
	/**
	 * 国家代码
	 */
	private String countryName;
	/**
	 * 次数下限（含）
	 */
	private Long countFloor;
	/**
	 * 次数上限（含）
	 */
	private Long countCeiling;
	/**
	 * 当前页数
	 */
	private String page;
	/**
	 * 每页记录数
	 */
	private String pageSize;
	
	/**
	 * 获取国家列表
	 * @return
	 * 修改历史：
	 */
	public String findCountry(){
		TransWordCountryQueryReq req = new TransWordCountryQueryReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		req.setLangCode(langCode);
		
		jsonObj = transWordInterface.findCountry(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 获取次数排前几的词语
	 * @return
	 * 修改历史：
	 */
	public String findWord(){
		TransWordQueryReq req = new TransWordQueryReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.getBean().setLangCode(langCode);
		req.getBean().setCountryName(countryName);
		req.getBean().setCountFloor(countFloor);
		req.getBean().setCountCeiling(countCeiling);		
		
		jsonObj = transWordInterface.findWord(req, page, pageSize, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	public String getLangCode() {
		return langCode;
	}
	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public Long getCountFloor() {
		return countFloor;
	}
	public void setCountFloor(Long countFloor) {
		this.countFloor = countFloor;
	}
	public Long getCountCeiling() {
		return countCeiling;
	}
	public void setCountCeiling(Long countCeiling) {
		this.countCeiling = countCeiling;
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