package com.qcmz.cmc.ws.provide.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.ws.provide.service.DaySentenceInterface;
import com.qcmz.cmc.ws.provide.vo.DaySentenceQueryReq;
import com.qcmz.cmc.ws.provide.vo.DaySentenceReq;
import com.qcmz.framework.action.BaseWSAction;
import com.qcmz.framework.ws.vo.Request;

/**
 * 类说明：每日一句接口
 * 修改历史：
 */
public class DayWSAction extends BaseWSAction {
	@Autowired
	private DaySentenceInterface daySentenceInterface;
	
	/**
	 * 当前页数
	 */
	private String page;
	/**
	 * 每页记录数
	 */
	private String pageSize;
	/**
	 * 语句编号
	 */
	private Long sentenceId;
	/**
	 * 源语言
	 */
	private String from;
	
	/**
	 * 分页获取每日一句列表
	 * @return
	 * 修改历史：
	 */
	public String findLang(){
		Request req = new Request();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		req.setLanguage(language);
		
		jsonObj = daySentenceInterface.findLang(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 分页获取每日一句列表
	 * @return
	 * 修改历史：
	 */
	public String findAllSentence(){
		Request req = new Request();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		jsonObj = daySentenceInterface.findAllSentence(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 分页获取每日一句列表
	 * @return
	 * 修改历史：
	 */
	public String findSentence(){
		DaySentenceQueryReq req = new DaySentenceQueryReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setFrom(from);
		
		jsonObj = daySentenceInterface.findSentence(req, page, pageSize, interfaceType, getRemoteAddr());
		
		return JSON;
	}

	/**
	 * 获取每日一句信息
	 * @return
	 * 修改历史：
	 */
	public String getSentence(){
		DaySentenceReq req = new DaySentenceReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		req.setSentenceId(sentenceId);
		
		jsonObj = daySentenceInterface.getSentence(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 获取下一条每日一句信息
	 * @return
	 * 修改历史：
	 */
	public String nextSentence(){
		DaySentenceReq req = new DaySentenceReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setSentenceId(sentenceId);
		req.setFrom(from);
		
		jsonObj = daySentenceInterface.nextSentence(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 获取下一条每日一句信息
	 * @return
	 * 修改历史：
	 */
	public String preSentence(){
		DaySentenceReq req = new DaySentenceReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setSentenceId(sentenceId);
		req.setFrom(from);
		
		jsonObj = daySentenceInterface.preSentence(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	public Long getSentenceId() {
		return sentenceId;
	}

	public void setSentenceId(Long sentenceId) {
		this.sentenceId = sentenceId;
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

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}
}
