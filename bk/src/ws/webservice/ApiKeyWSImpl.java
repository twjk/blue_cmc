package com.qcmz.cmc.ws.provide.webservice.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.cache.ApiKeyMap;
import com.qcmz.cmc.ws.provide.vo.ApiKeyResponse;
import com.qcmz.cmc.ws.provide.webservice.IApiKeyWS;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.framework.ws.vo.RespConstants;

/**
 * 类说明：对该类的主要功能进行说明
 * @author 李炳煜
 * @version 1.0
 * 创建日期：Jun 4, 2014 5:28:50 PM
 * 修改历史：
 */
@Service
public class ApiKeyWSImpl implements IApiKeyWS {
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private ApiKeyMap apiKeyMap;
	
	/**
	 * 根据类型获取key信息
	 * @param apiType
	 * @return
	 * @author 李炳煜
	 * @version 1.0
	 * 创建日期：Jun 4, 2014 5:32:21 PM
	 * 修改历史：
	 */
	public ApiKeyResponse getApiKey(String apiType){
		ApiKeyResponse resp = new ApiKeyResponse();
		
		if(StringUtil.isBlankOrNull(apiType)){
			resp.error(RespConstants.RESPCODE_INVALID, "ApiKey类型为空");
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
