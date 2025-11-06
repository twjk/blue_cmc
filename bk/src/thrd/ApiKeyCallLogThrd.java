package com.qcmz.cmc.thrd;

import com.qcmz.cmc.service.IApiKeyService;
import com.qcmz.framework.thrd.AbstractThrd;

/**
 * 类说明：对该类的主要功能进行说明
 * @author 李炳煜
 * @version 1.0
 * 创建日期：Jun 5, 2014 10:09:13 AM
 * 修改历史：
 */
public class ApiKeyCallLogThrd extends AbstractThrd {
	private IApiKeyService apiKeyService;
	private Long apiKeyId;
	
	public ApiKeyCallLogThrd() {
		super();
	}

	public ApiKeyCallLogThrd(IApiKeyService apiKeyService, Long apiKeyId) {
		super();
		this.apiKeyService = apiKeyService;
		this.apiKeyId = apiKeyId;
	}

	/** 
	 * 
	 * @author 李炳煜
	 * @version 1.0
	 * 创建日期：Jun 5, 2014 10:09:13 AM
	 * 修改历史：
	 */
	@Override
	protected void work() {
		try {
			apiKeyService.saveLog(apiKeyId);
		} catch (Exception e) {
			logger.error("保存api key 使用日志出错", e);
		}
	}

}
