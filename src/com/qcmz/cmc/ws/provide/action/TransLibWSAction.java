package com.qcmz.cmc.ws.provide.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.ws.provide.service.TransLibInterface;
import com.qcmz.cmc.ws.provide.vo.TransLibQueryReq;
import com.qcmz.framework.action.BaseWSAction;
import com.qcmz.framework.ws.vo.Request;

/**
 * 译库
 */
public class TransLibWSAction extends BaseWSAction {
	@Autowired
	private TransLibInterface transLibInterface;
	
	/**
	 * 最后一条记录编号
	 */
	private Long lastId;
	/**
	 * 每页记录数
	 */
	private int pageSize;
	
	/**
	 * 获取译库下载信息
	 * @return
	 */
	public String getDownload(){
		Request req = new Request();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		jsonObj = transLibInterface.getTransLibDownload(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 分页获取译库
	 * @return
	 */
	public String findTransLib4Web(){
		TransLibQueryReq req = new TransLibQueryReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.getBean().setLastId(lastId);
		req.getBean().setPageSize(pageSize);
		
		jsonObj = transLibInterface.findTransLib4Web(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}

	public Long getLastId() {
		return lastId;
	}

	public void setLastId(Long lastId) {
		this.lastId = lastId;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
}
