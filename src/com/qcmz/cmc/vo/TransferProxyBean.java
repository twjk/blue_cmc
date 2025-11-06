package com.qcmz.cmc.vo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 类说明：对该类的主要功能进行说明
 * @author 李炳煜
 * 修改历史：
 */
public class TransferProxyBean {
	/**
	 * 代理列表
	 */
	private List<TransProxyBean> proxys = new ArrayList<TransProxyBean>();;

	public List<TransProxyBean> getProxys() {
		return proxys;
	}

	public void setProxys(List<TransProxyBean> proxys) {
		this.proxys = proxys;
	}
	
	public Integer getProxyIdCount(){
		Set<Long> ids = new HashSet<Long>();
		for (TransProxyBean proxy : proxys) {
			ids.add(proxy.getProxyId());
		}
		return ids.size();
	}
	
}
