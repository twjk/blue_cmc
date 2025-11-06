package com.qcmz.cmc.ws.provide.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.ws.provide.service.TransMachineInterface;
import com.qcmz.cmc.ws.provide.vo.TmGetReq;
import com.qcmz.framework.action.BaseWSAction;

public class TransMachineWSAction extends BaseWSAction {
	@Autowired
	private TransMachineInterface transMachineInterface;
	
	/**
	 * 翻译机编号
	 */
	private String tmcode;
	
	/**
	 * 获取翻译机信息
	 * @return
	 */
	public String getTm(){
		TmGetReq req = new TmGetReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		req.setTmcode(tmcode);
		
		jsonObj = transMachineInterface.getTm(req, interfaceType, getRemoteAddr());
		return JSON;
	}
	
	public String getTmcode() {
		return tmcode;
	}

	public void setTmcode(String tmcode) {
		this.tmcode = tmcode;
	}
}
