package com.qcmz.cmc.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.entity.CmcProxyLang;
import com.qcmz.cmc.service.IProxyLangService;
import com.qcmz.framework.cache.AbstractCacheMap;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
@Component
public class ProxyLangMap extends AbstractCacheMap {
	@Autowired
	private IProxyLangService proxyLangService;
	
	/**
	 * key:proxyId|langCode
	 */
	private Map<String, CmcProxyLang> map = null;
	
	/** 
	 * 
	 * 修改历史：
	 */
	@Override @PostConstruct
	protected void init() {
		Map<String, CmcProxyLang> tempMap = new HashMap<String, CmcProxyLang>();
		List<CmcProxyLang> list = proxyLangService.findAll();
		for (CmcProxyLang cmcProxyLang : list) {
			tempMap.put(getKey(cmcProxyLang.getProxyid(), cmcProxyLang.getLangcode()), cmcProxyLang);
		}
		map = tempMap;
	}

	/**
	 * 获取代理某语言的语言信息
	 * @param proxyId
	 * @param langCode
	 * @return
	 * 修改历史：
	 */
	public CmcProxyLang getBean(Long proxyId, String langCode){
		if(!safeInit(map)) return null;
		return map.get(getKey(proxyId, langCode));
	}
	
	/**
	 * 根据代理语言代码获取系统语言代码
	 * @param proxyId
	 * @param mtCode
	 * @return
	 * 修改历史：
	 */
	public String getLangCodeByProxyLangCode(Long proxyId, String mtCode){
		if(!safeInit(map)) return null;
		CmcProxyLang bean = null;
		for (String key : map.keySet()) {
			bean = map.get(key);
			if(bean.getProxyid().equals(proxyId)
					&& mtCode.equals(bean.getMtcode())){
				return bean.getLangcode();
			}
		}
		return null;
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

	private String getKey(Long proxyId, String langCode){
		return new StringBuilder().append(proxyId).append("|").append(langCode).toString();
	}
}
