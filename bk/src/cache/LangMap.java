package com.qcmz.cmc.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.dao.ILangDao;
import com.qcmz.cmc.entity.CmcLang;
import com.qcmz.framework.cache.AbstractCacheMap;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
@Component
public class LangMap extends AbstractCacheMap {
	@Autowired
	private ILangDao langDao;
	/**
	 * 语言缓存<code,实体>
	 */
	private Map<String, CmcLang> map;
	
	/** 
	 * 
	 * 修改历史：
	 */
	@Override @PostConstruct
	protected void init() {
		Map<String, CmcLang> temp = new HashMap<String, CmcLang>();
		List<CmcLang> list = langDao.findAll();
		for (CmcLang lang : list) {
			temp.put(lang.getLangcode(), lang);
		}
		map = temp;
	}
	
	/**
	 * 根据语言代码获取语言信息
	 * @param langCode
	 * @return
	 * 修改历史：
	 */
	public CmcLang getBean(String langCode){
		if(map==null) init();
		return map.get(langCode);
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
