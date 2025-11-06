package com.qcmz.cmc.dao;

import java.util.List;

import com.qcmz.cmc.entity.CmcProxy;
import com.qcmz.cmc.entity.CmcProxyFunc;
import com.qcmz.framework.dao.IBaseDAO;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public interface IProxyDao extends IBaseDAO {
	/**
	 * 获取代理列表
	 * @param funcId
	 * @param status
	 * @return
	 * 修改历史：
	 */
	public List<CmcProxy> findProxy(Long funcId, Integer status);
	/**
	 * 获取代理功能列表
	 * @return
	 * 修改历史：
	 */
	public List<CmcProxyFunc> findProxyFunc();
	/**
	 * 清除代理功能 
	 * 修改历史：
	 */
	public void clearProxyFunc();
}
