package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

import com.qcmz.framework.ws.vo.Response;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class ProxyAccountQueryResp extends Response {
	private List<ProxyAccountBean> resultList = new ArrayList<ProxyAccountBean>();

	public List<ProxyAccountBean> getResultList() {
		return resultList;
	}

	public void setResultList(List<ProxyAccountBean> resultList) {
		this.resultList = resultList;
	}
}
