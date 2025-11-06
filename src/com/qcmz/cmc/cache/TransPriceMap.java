package com.qcmz.cmc.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.entity.CmcTpIntl;
import com.qcmz.cmc.entity.CmcTpLang;
import com.qcmz.cmc.entity.CmcTpPrice;
import com.qcmz.cmc.service.ITransPriceService;
import com.qcmz.framework.cache.AbstractCacheMap;
import com.qcmz.framework.constant.DictConstants;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
@Component
public class TransPriceMap extends AbstractCacheMap {
	@Autowired
	private ITransPriceService transPriceService;
	
	/**
	 * <transType, List<CmcTpPrice>>
	 */
	private Map<String, List<CmcTpPrice>> map = null;
	/**
	 * <priceid|langcode, CmcTpIntl>
	 */
	private Map<String, CmcTpIntl> intlMap = null;
	/**
	 * <priceid, List<from+to>>
	 */
	private Map<Long, List<String>> langMap = null;
	
	/** 
	 * 
	 * 修改历史：
	 */
	@Override @PostConstruct
	protected void init() {
		Map<String, List<CmcTpPrice>> temp = new HashMap<String, List<CmcTpPrice>>();
		String key = null;
		
		List<CmcTpPrice> prices = transPriceService.findPrice();
		for (CmcTpPrice price : prices) {
			key = price.getTranstype();
			if(temp.get(key)==null){
				temp.put(key, new ArrayList<CmcTpPrice>());
			}
			temp.get(key).add(price);
		}
		map = temp;
		
		Map<String, CmcTpIntl> intlTemp = new HashMap<String, CmcTpIntl>();
		List<CmcTpIntl> intls = transPriceService.findPriceIntl();
		for (CmcTpIntl intl : intls) {
			key = getIntlKey(intl.getPriceid(), intl.getLanguage());
			intlTemp.put(key, intl);
		}
		intlMap = intlTemp;
		
		Map<Long, List<String>> langTemp = new HashMap<Long, List<String>>();
		List<CmcTpLang> langs = transPriceService.findPriceLang();
		for (CmcTpLang lang : langs) {
			if(langTemp.get(lang.getPriceid())==null){
				langTemp.put(lang.getPriceid(), new ArrayList<String>());
			}
			langTemp.get(lang.getPriceid()).add(lang.getFromlang()+lang.getTolang());
		}
		langMap = langTemp;
	}

	/**
	 * 获取指定翻译类型的价格列表
	 * @param transType
	 * @return
	 * 修改历史：
	 */
	public List<CmcTpPrice> findPrice(String transType){
		if(safeInit(map)){
			return map.get(transType);
		}
		return null;
	}
	
	public boolean isSupport(Long priceId, String from, String to){
		if(!safeInit(langMap)) return false;
		String fromTo = from+to;
		List<String> langs = langMap.get(priceId);
		if(langs!=null && !langs.isEmpty()){
			for (String lang : langs) {
				if("ALLALL".equals(lang) || fromTo.equals(lang)){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 获取价格描述
	 * @param priceId
	 * @param language
	 * @return
	 * 修改历史：
	 */
	public CmcTpIntl getPriceIntl(Long priceId, String language){
		if(!safeInit(intlMap)) return null;
		
		CmcTpIntl intl = intlMap.get(getIntlKey(priceId, language));
		if(intl==null){
			intl = intlMap.get(getIntlKey(priceId, DictConstants.DICT_LANG_EN));
		}
		if(intl==null){
			intl = intlMap.get(getIntlKey(priceId, DictConstants.DICT_LANG_ZHCN));
		}
		return intl;
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

	private String getIntlKey(Long priceId, String language){
		return new StringBuilder().append(priceId).append("|").append(language).toString();
	}
}
