package com.qcmz.cmc.service;

import com.qcmz.framework.service.IBaseService;

/**
 * 类说明：对该类的主要功能进行说明
 * @author 李炳煜
 * @version 1.0
 * 创建日期：Nov 7, 2013 3:14:46 PM
 * 修改历史：
 */
public interface IApiKeyService extends IBaseService {
	/**
	 * 获取有效key总数
	 * @param apiType
	 * @return
	 * 修改历史：
	 */
	public Long getValidCount(String apiType);
	/**
	 * 记录key使用日志
	 * @param keyId
	 * @author 李炳煜
	 * @version 1.0
	 * 创建日期：Jun 4, 2014 4:14:49 PM
	 * 修改历史：
	 */
	public void saveLog(Long keyId);
	/**
	 * 停用必应key
	 * @param apiKey
	 * 修改历史：
	 */
	public void stopBing(String apiKey);
	/**
	 * 启用到期的必应 
	 * 修改历史：
	 */
	public void restartBing();
}
