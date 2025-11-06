package com.qcmz.cmc.ws.provide.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.ws.provide.service.ApiKeyInterface;
import com.qcmz.framework.action.BaseWSAction;

/**
 * 类说明：ApiKey接口
 * 修改历史：
 */
public class ApiKeyWSAction extends BaseWSAction{
	@Autowired
	private ApiKeyInterface apiKeyInterface;
	
	/**
	 *  ApiKey类型
		GOOGLEASR  谷歌
		BAIDUTRANS 百度（暂不支持）
		JIETONG     捷通华声（暂不支持）
		KEDA        科大讯飞（暂不支持）
	 */
	private String at;
	
	//获取APIKEY,/ak!getApiKey.do?at=GOOGLEASR
	public String getApiKey(){
		jsonObj = apiKeyInterface.getApiKey(at);
		return JSON;
	}

	public String getAt() {
		return at;
	}

	public void setAt(String at) {
		this.at = at;
	}
	
	
}
