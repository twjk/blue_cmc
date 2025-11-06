package com.qcmz.cmc.web.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.process.TransProcess;
import com.qcmz.framework.action.BaseAction;

public class TransAction extends BaseAction {
	@Autowired
	private TransProcess transProcess;
	
	private String from;
	private String to;
	private String src;
	private Long proxyId;	
	
	public String trans(){
		
		jsonObj = transProcess.transByProxy(from, to, src, proxyId);
		
		return JSON;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public Long getProxyId() {
		return proxyId;
	}

	public void setProxyId(Long proxyId) {
		this.proxyId = proxyId;
	}
}
