package com.qcmz.cmc.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.comparator.TransferProxyComparator;
import com.qcmz.cmc.constant.BusinessConstant;
import com.qcmz.cmc.entity.CmcFuncCap;
import com.qcmz.cmc.service.IFuncCapService;
import com.qcmz.cmc.service.ITransService;
import com.qcmz.cmc.vo.TransProxyBean;
import com.qcmz.cmc.vo.TransferProxyBean;
import com.qcmz.cmc.ws.provide.vo.TransLangBean;
import com.qcmz.framework.cache.AbstractCacheMap;

/**
 * 类说明：翻译能力缓存
 * 修改历史：
 */
@Component
public class TransCapMap extends AbstractCacheMap {
	@Autowired
	private IFuncCapService funcCapService;
	@Autowired
	private ITransService transService;
	
	/**
	 * 直译语言通道缓存，<源语言|目标语言,代理编号列表>
	 */
	private Map<String, List<Long>> map;
	/**
	 * 中转翻译语言通道缓存，<源语言|目标语言,中转代理列表>
	 */
	private Map<String, List<TransferProxyBean>> transferMap;
	/**
	 * 翻译语言列表
	 */
	private List<TransLangBean> list = null;
	private List<String> langs = null;
	
	/** 
	 * 
	 * 修改历史：
	 */
	@Override @PostConstruct
	protected void init() {		
		list = transService.findLang();
		List<String> langsTemp = new ArrayList<String>();
		for (TransLangBean bean : list) {
			langsTemp.add(bean.getLangCode());
		}
		langs = langsTemp;
		
		Map<String, List<Long>> temp = new HashMap<String, List<Long>>();
		Map<String, List<TransferProxyBean>> transferTemp = new HashMap<String, List<TransferProxyBean>>();
		
		String key = null;

		List<CmcFuncCap> list = funcCapService.findCap(BusinessConstant.FUNCID_TRANSLATE);
		//直译通道
		for (CmcFuncCap bean : list) {
			key = getKey(bean.getFromlang(), bean.getTolang());
			if(temp.get(key)==null) temp.put(key, new ArrayList<Long>());
			temp.get(key).add(bean.getProxyid());
		}
		map = temp;
		
		//中转通道
		TransferProxyBean transferBean = null;
		for (CmcFuncCap proxy1 : list) {
			for (CmcFuncCap proxy2 : list) {
				if(proxy2.getFromlang().equals(proxy1.getTolang())
						&& !proxy2.getTolang().equals(proxy1.getFromlang())
						&& findProxy(proxy1.getFromlang(), proxy2.getTolang())==null){
					transferBean = new TransferProxyBean();
					transferBean.getProxys().add(new TransProxyBean(proxy1));
					transferBean.getProxys().add(new TransProxyBean(proxy2));
					
					key = getKey(proxy1.getFromlang(), proxy2.getTolang());
					if(transferTemp.get(key)==null){
						transferTemp.put(key, new ArrayList<TransferProxyBean>());
					}
					transferTemp.get(key).add(transferBean);
				}
			}
		}
		TransferProxyComparator comp = new TransferProxyComparator();
		for (String lang : transferTemp.keySet()) {
			Collections.sort(transferTemp.get(lang), comp);
		}
		transferMap = transferTemp;
	}
	
	/**
	 * 获取翻译语言列表
	 * @return
	 * 修改历史：
	 */
	public List<TransLangBean> findLang(){
		if(safeInit(list)){
			return list;
		}
		return new ArrayList<TransLangBean>();
	}
	
	/**
	 * 翻译列表是否包含指定语言
	 * @param langCode
	 * @return
	 * 修改历史：
	 */
	public boolean contains(String langCode){
		if(safeInit(langs)){
			return langs.contains(langCode);
		}
		return false;
	}
	
	/**
	 * 获取语言可用的通道列表
	 * @param from
	 * @param to
	 * @return
	 * 修改历史：
	 */
	public List<Long> findProxy(String from, String to){
		List<Long> list = null;
		if(safeInit(map)){
			list = map.get(getKey(from, to));
			if(list!=null && list.isEmpty()){
				list = null;
			}
		}
		return list;
	}
	
	/**
	 * 获取语言可用的中转通道列表
	 * @param from
	 * @param to
	 * @return
	 * 修改历史：
	 */
	public List<TransferProxyBean> findTransferProxy(String from, String to){
		List<TransferProxyBean> list = null;
		if(safeInit(transferMap)){
			list = transferMap.get(getKey(from, to));
			if(list!=null && list.isEmpty()){
				list = null;
			}
		}
		return list;
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

	private String getKey(String from, String to){
		return new StringBuilder().append(from).append("|").append(to).toString();
	}
}
