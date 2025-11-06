package com.qcmz.cmc.process;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.cache.ApiKeyMap;
import com.qcmz.cmc.config.SystemConfig;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.service.IApiKeyService;
import com.qcmz.framework.util.MailUtil;

/**
 * 类说明：ApiKey处理
 * 修改历史：
 */
@Component
public class ApikeyProcess {
	@Autowired
	private IApiKeyService apiKeyService;
	@Autowired
	private ApiKeyMap apiKeyMap;
	
	/**
	 * 启用到期的key
	 * 修改历史：
	 */
	public void restartBingKey(){
		//启用到期的key
		apiKeyService.restartBing();
		//刷新缓存
		apiKeyMap.refresh();
	}
	
	/**
	 * 停用必应的某个key
	 * @param key
	 * 修改历史：
	 */
	public void offBiyingKey(String apiKey){
		Long count = apiKeyService.getValidCount(DictConstant.DICT_APIKEYTYPE_BIYING);
		if(count<50){
			return;	//可用数小于50个时，不再停用，需人工核实
		}
		
		//状态置为停用
		apiKeyService.stopBing(apiKey);
		
		//刷新缓存
		apiKeyMap.delete(DictConstant.DICT_APIKEYTYPE_BIYING, apiKey);
		
		//邮件通知，可用总数小于90时再通知
		if(count<90){
			StringBuilder sbMsg = new StringBuilder();
			sbMsg.append(apiKey).append("流量为0，可用Key数").append(apiKeyMap.getCount(DictConstant.DICT_APIKEYTYPE_BIYING));
			MailUtil.sendSimpleMail(SystemConfig.MAILS, null, sbMsg.toString(), sbMsg.toString());
		}
	}
}
