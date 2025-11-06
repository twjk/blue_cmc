package com.qcmz.cmc.proxy;

import org.apache.log4j.Logger;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public abstract class AbstractProxy {
	
	protected Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 代理编号
	 */
	protected Long proxyId = 0L;

	public Long getProxyId() {
		return proxyId;
	}

	public void setProxyId(Long proxyId) {
		this.proxyId = proxyId;
	}
	
}
