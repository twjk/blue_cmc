package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

import com.qcmz.framework.ws.vo.Response;

/**
 * 类说明：语音帐户查询结果
 * 修改历史：
 */
public class SpeechAccountQueryResp extends Response {
	/**
	 * 帐户列表
	 */
	List<ProxyAccountBean> results = new ArrayList<ProxyAccountBean>();

	public List<ProxyAccountBean> getResults() {
		return results;
	}

	public void setResults(List<ProxyAccountBean> results) {
		this.results = results;
	}
}
