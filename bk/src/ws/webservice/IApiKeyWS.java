package com.qcmz.cmc.ws.provide.webservice;

import com.qcmz.cmc.ws.provide.vo.ApiKeyResponse;

/**
 * 类说明：对该类的主要功能进行说明
 * @author 李炳煜
 * @version 1.0
 * 创建日期：Jun 4, 2014 5:28:13 PM
 * 修改历史：
 */
public interface IApiKeyWS {
	/**
	 * 根据类型获取key信息
	 * @param apiType
	 * @return
	 * @author 李炳煜
	 * @version 1.0
	 * 创建日期：Jun 4, 2014 5:32:21 PM
	 * 修改历史：
	 */
	public ApiKeyResponse getApiKey(String apiType);
}
