package com.qcmz.cmc.ws.provide.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.ws.provide.service.JobArticleInterface;
import com.qcmz.cmc.ws.provide.vo.JobArticleQueryReq;
import com.qcmz.framework.action.BaseWSAction;
import com.qcmz.framework.ws.vo.Request;

public class JobArticleWSAction extends BaseWSAction {
	@Autowired
	private JobArticleInterface jobArticleInterface;
	
	/**
	 * 分类编号
	 */
	private Long catId;
	/**
	 * 任务名称
	 */
	private String title;
	/**
	 * 城市名称
	 */
	private String cityName;
	/**
	 * 更多基准标识
	 */
	private String moreBaseId;
	/**
	 * 每页记录数
	 */
	private String pageSize;
	
	/**
	 * 获取分类列表
	 */
	public String findCat(){
		Request req = new Request();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		jsonObj = jobArticleInterface.findCat(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 分页获取就业信息
	 */
	public String findArticle(){
		JobArticleQueryReq req = new JobArticleQueryReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.getBean().setCatId(catId);
		req.getBean().setTitle(title);
		req.getBean().setCityName(cityName);
		req.getBean().setMoreBaseId(moreBaseId);
		
		jsonObj = jobArticleInterface.findArticle(req, pageSize, interfaceType, getRemoteAddr());
		
		return JSON;
	}

	public Long getCatId() {
		return catId;
	}

	public void setCatId(Long catId) {
		this.catId = catId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getMoreBaseId() {
		return moreBaseId;
	}

	public void setMoreBaseId(String moreBaseId) {
		this.moreBaseId = moreBaseId;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
}
