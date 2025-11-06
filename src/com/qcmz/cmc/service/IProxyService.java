package com.qcmz.cmc.service;

import java.util.List;

import com.qcmz.cmc.entity.CmcProxy;
import com.qcmz.cmc.entity.CmcProxyFunc;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public interface IProxyService {
	/**
	 * 获取所有代理列表
	 * @return
	 * 修改历史：
	 */
	public List<CmcProxy> findAll();
	/**
	 * 获取可用代理列表
	 * @param funcId
	 * @return
	 * 修改历史：
	 */
	public List<CmcProxy> findValid(Long funcId);
	/**
	 * 获取代理功能列表
	 * @return
	 * 修改历史：
	 */
	public List<CmcProxyFunc> findProxyFunc();
	/**
	 * 保存代理功能列表
	 * @param list
	 * 修改历史：
	 */
	public void saveProxyFunc(List<CmcProxyFunc> list);
}
