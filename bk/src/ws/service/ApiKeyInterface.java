package com.qcmz.cmc.ws.provide.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.cache.ApiKeyMap;
import com.qcmz.cmc.ws.provide.vo.ApiKeyResponse;
import com.qcmz.framework.util.StringUtil;

/**
 * 类说明：ApiKey接口实现
 * 修改历史：
 */
@Component
public class ApiKeyInterface {
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private ApiKeyMap apiKeyMap;
	
	/**
	 * 根据类型获取key信息
	 * @param apiType
	 * @return
	 * 修改历史：
	 */
	public ApiKeyResponse getApiKey(String apiType){
		ApiKeyResponse resp = new ApiKeyResponse();
		
		if(StringUtil.isBlankOrNull(apiType)){
			resp.errorParam("ApiKey类型为空");
			return resp;
		}
		
		try {
			resp.setAk(apiKeyMap.getApiKey(apiType));
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口[getApiKey]失败", e);
		}
		return resp;
	}
}
