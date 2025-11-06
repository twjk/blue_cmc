package com.qcmz.cmc.vo;

import com.qcmz.cmc.entity.CmcFuncCap;

/**
 * 类说明：翻译通道信息
 * @author 李炳煜
 * 修改历史：
 */
public class TransProxyBean {
	/**
	 * 源语言
	 */
	private String from;
	/**
	 * 目标语言
	 */
	private String to;
	/**
	 * 代理编号
	 */
	private Long proxyId;
	
	public TransProxyBean() {
		super();
	}
	
	public TransProxyBean(CmcFuncCap cmcLangProxy) {
		super();
		this.from = cmcLangProxy.getFromlang();
		this.to = cmcLangProxy.getTolang();
		this.proxyId = cmcLangProxy.getProxyid();
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
	public Long getProxyId() {
		return proxyId;
	}
	public void setProxyId(Long proxyId) {
		this.proxyId = proxyId;
	}
}
