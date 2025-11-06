package com.qcmz.cmc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.dao.IProxyDao;
import com.qcmz.cmc.entity.CmcProxy;
import com.qcmz.cmc.entity.CmcProxyFunc;
import com.qcmz.cmc.service.IProxyService;
import com.qcmz.framework.constant.SystemConstants;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
@Service
public class ProxyServiceImpl implements IProxyService {
	@Autowired
	private IProxyDao proxyDao;
	
	/**
	 * 获取所有代理列表
	 * @return
	 * 修改历史：
	 */
	public List<CmcProxy> findAll(){
		return proxyDao.findProxy(null, null);
	}
	
	/**
	 * 获取可用代理列表
	 * @param funcId
	 * @return
	 * 修改历史：
	 */
	public List<CmcProxy> findValid(Long funcId){
		return proxyDao.findProxy(funcId, SystemConstants.STATUS_ON);
	}
	
	/**
	 * 获取代理功能列表
	 * @return
	 * 修改历史：
	 */
	public List<CmcProxyFunc> findProxyFunc(){
		return proxyDao.findProxyFunc();
	}
	
	/**
	 * 保存代理功能列表
	 * @param list
	 * 修改历史：
	 */
	public void saveProxyFunc(List<CmcProxyFunc> list){
		proxyDao.clearProxyFunc();
		if(list!=null && !list.isEmpty()){
			proxyDao.saveOrUpdateAll(list);
		}
	}
}
