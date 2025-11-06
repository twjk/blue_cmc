package com.qcmz.cmc.ws.provide.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.ws.provide.service.DialogInterface;
import com.qcmz.cmc.ws.provide.vo.DialogMsgAddReq;
import com.qcmz.cmc.ws.provide.vo.DialogMsgQueryReq;
import com.qcmz.framework.action.BaseWSAction;

/**
 * 对话相关接口
 */
public class DialogWSAction extends BaseWSAction {
	@Autowired
	private DialogInterface dialogInterface;
	
	/**
	 * 用户编号
	 */
	private Long uid;
	/**
	 * 消息
	 */
	private String msg;
	/**
	 * 更多基准标识
	 */
	private String moreBaseId;
	
	private String pageSize;
	
	/**
	 * 用户添加消息
	 */
	public String addMsg(){
		DialogMsgAddReq req = new DialogMsgAddReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.getBean().setUid(uid);
		req.getBean().setMsg(msg);
		
		jsonObj = dialogInterface.addMsg(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 分页获取用户消息
	 */
	public String findMsg(){
		DialogMsgQueryReq req = new DialogMsgQueryReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setUid(uid);
		req.setMoreBaseId(moreBaseId);
		
		jsonObj = dialogInterface.findMsg(req, pageSize, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 分页获取用户新的消息
	 */
	public String findNewMsg(){
		DialogMsgQueryReq req = new DialogMsgQueryReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setUid(uid);
		req.setMoreBaseId(moreBaseId);
		
		jsonObj = dialogInterface.findNewMsg(req, pageSize, interfaceType, getRemoteAddr());
		
		return JSON;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
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
