package com.qcmz.cmc.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.qcmz.cmc.entity.CmcApikey;
import com.qcmz.cmc.vo.ApiKeyBean;
import com.qcmz.framework.dao.IBaseDAO;

/**
 * 类说明：对该类的主要功能进行说明
 * @author 李炳煜
 * @version 1.0
 * 创建日期：Jun 4, 2014 3:01:45 PM
 * 修改历史：
 */
public interface IApiKeyDao extends IBaseDAO {
	/**
	 * 获取所有有效的key
	 * @return
	 * @author 李炳煜
	 * @version 1.0
	 * 创建日期：Jun 4, 2014 3:03:35 PM
	 * 修改历史：
	 */
	public List<ApiKeyBean> findValid();
	/**
	 * 获取所有key
	 * @return
	 * @author 李炳煜
	 * @version 1.0
	 * 创建日期：Jun 4, 2014 3:03:35 PM
	 * 修改历史：
	 */
	public List<ApiKeyBean> findAll();
	/**
	 * 获取配置当天访问量,keyId为key,当天访问量为value
	 * @return
	 * @author 李炳煜
	 * @version 1.0
	 * 创建日期：Jun 10, 2014 11:01:55 AM
	 * 修改历史：
	 */
	public Map<Long, Long> findTodayCallCount();
	/**
	 * type每天访问量
	 * @return
	 * @author 李炳煜
	 * @version 1.0
	 * 创建日期：Jun 10, 2014 2:19:53 PM
	 * 修改历史：
	 */
	public Map<String, Map<String, Long>> findTypeCallCount(Date beginDate, Date endDate);
	/**
	 * 获取有效key总数
	 * @param apiType
	 * @return
	 * 修改历史：
	 */
	public Long getValidCount(String apiType);
	/**
	 * 获取key信息
	 * @param apiType
	 * @param apiKey
	 * @return
	 * 修改历史：
	 */
	public CmcApikey loadKey(String apiType, String apiKey);
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
