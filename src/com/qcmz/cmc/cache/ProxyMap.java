package com.qcmz.cmc.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.entity.CmcProxy;
import com.qcmz.cmc.proxy.ProxyMgr;
import com.qcmz.cmc.service.IProxyService;
import com.qcmz.framework.cache.AbstractCacheMap;
import com.qcmz.framework.constant.SystemConstants;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
@Component
public class ProxyMap extends AbstractCacheMap {
	@Autowired
	private IProxyService proxyService;
	
	private Map<Long, CmcProxy> map;
	
	/** 
	 * 初始化
	 * 修改历史：
	 */
	@Override @PostConstruct
	protected void init() {
		Map<Long, CmcProxy> temp = new HashMap<Long, CmcProxy>();
		List<CmcProxy> list = proxyService.findAll();
		for (CmcProxy proxy : list) {
			temp.put(proxy.getProxyid(), proxy);
		}
		
		map = temp;
		ProxyMgr.clear();
	}
	
	/**
	 * 根据ID获取代理信息
	 * @param proxyId
	 * @return
	 * 修改历史：
	 */
	public CmcProxy getBean(Long proxyId){
		if(map==null) init();
		return map.get(proxyId);
	}
	
	/**
	 * 根据ID获取有效的代理信息
	 * @param proxyId
	 * @return
	 * 修改历史：
	 */
	public CmcProxy getValidBean(Long proxyId){
		if(map==null) init();
		CmcProxy proxy = map.get(proxyId);
		if(proxy!=null && SystemConstants.STATUS_ON.equals(proxy.getStatus())){
			return proxy;
		}
		return null;
	}
	
	/**
	 * 获取代理名称
	 * @param proxyId
	 * @return
	 * 修改历史：
	 */
	public String getProxyName(Long proxyId){
		if(map==null) init();
		CmcProxy proxy = map.get(proxyId);
		if(proxy!=null){
			return proxy.getProxyname();			
		}
		return String.valueOf(proxyId);
	}
	
	/** 
	 * @param obj
	 * 修改历史：
	 */
	@Override
	public void delete(Object obj) {
	}

	/** 
	 * @param obj
	 * 修改历史：
	 */
	@Override
	public void update(Object obj) {
	}

}
