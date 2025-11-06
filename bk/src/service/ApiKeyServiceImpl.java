package com.qcmz.cmc.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.dao.IApiKeyDao;
import com.qcmz.cmc.entity.CmcApikeyCalllog;
import com.qcmz.cmc.service.IApiKeyService;
import com.qcmz.framework.service.impl.BaseService;

/**
 * 类说明：对该类的主要功能进行说明
 * @author 李炳煜
 * @version 1.0
 * 创建日期：Apr 8, 2014 3:52:17 PM
 * 修改历史：
 */
@Service
public class ApiKeyServiceImpl extends BaseService implements IApiKeyService {

	@Autowired
	private IApiKeyDao apiKeyDao;

	/**
	 * 获取有效key总数
	 * @param apiType
	 * @return
	 * 修改历史：
	 */
	public Long getValidCount(String apiType){
		return apiKeyDao.getValidCount(apiType);
	}
	
	/**
	 * 记录key使用日志
	 * @param keyId
	 * @author 李炳煜
	 * @version 1.0
	 * 创建日期：Jun 4, 2014 4:14:49 PM
	 * 修改历史：
	 */
	public void saveLog(Long keyId){
		CmcApikeyCalllog log = new CmcApikeyCalllog();
		log.setKeyid(keyId);
		log.setCalltime(new Date());
		apiKeyDao.save(log);
	}

	/**
	 * 停用必应key
	 * @param apiKey
	 * 修改历史：
	 */
	public void stopBing(String apiKey){
		apiKeyDao.stopBing(apiKey);
	}
	
	/**
	 * 启用到期的必应 
	 * 修改历史：
	 */
	public void restartBing(){
		apiKeyDao.restartBing();
	}
}
