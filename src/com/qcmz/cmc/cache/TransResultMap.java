package com.qcmz.cmc.cache;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.qcmz.cmc.config.JobConfig;
import com.qcmz.cmc.vo.TransResult4Cache;
import com.qcmz.framework.cache.AbstractCacheMap;
import com.qcmz.framework.util.StringUtil;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
@Component
public class TransResultMap extends AbstractCacheMap {
	/**
	 * 翻译结果缓存 <from+to,<src.trim.clearPuncLast,bean>>
	 */
	private Map<String, Map<String, TransResult4Cache>> transMap = new HashMap<String, Map<String,TransResult4Cache>>();
	
	/**
	 * 获取缓存的翻译结果
	 * @param from
	 * @param to
	 * @param src
	 * @return
	 * 修改历史：
	 */	
	public TransResult4Cache getBean(String from, String to, String src){
		if(!JobConfig.TRANSCACHE_CLEAREXPIRATION_ISWORK) return null;
		
		Map<String, TransResult4Cache> langMap = transMap.get(from+to);
		if(langMap!=null){
			String key = StringUtil.clearPuncLast(src.trim().toLowerCase());
			TransResult4Cache cache = langMap.get(key);
			if(cache!=null && cache.isValid()){
				return cache;
			}
		}
		
		return null;
	}
	
	/**
	 * 放入缓存
	 * @param from
	 * @param to
	 * @param src
	 * @param dst
	 * 修改历史：
	 */
	public void putDst(String from, String to, String src, String dst, int similar){
		if(!JobConfig.TRANSCACHE_CLEAREXPIRATION_ISWORK) return;
		
		String lang = from+to;
		Map<String, TransResult4Cache> langMap = transMap.get(lang);
		if(langMap==null){
			langMap = new HashMap<String, TransResult4Cache>();
			transMap.put(lang, langMap);
		}
		langMap.put(StringUtil.clearPuncLast(src.toLowerCase()), new TransResult4Cache(dst, similar));
	}
	
	/**
	 * 清除过期
	 * 修改历史：
	 */
	public void clearExpiration(){
		Map<String, TransResult4Cache> langMap = null;
		TransResult4Cache cache = null;
		
		long count = 0;
		for (String key : transMap.keySet()) {
			langMap = transMap.get(key);
			Iterator<Map.Entry<String, TransResult4Cache>> entries = langMap.entrySet().iterator();
			while (entries.hasNext()) {
				cache = entries.next().getValue();
				if(!cache.isValid()){
					entries.remove();
				}
			}
			count = count+langMap.size();
		}
	}
	
	/** 
	 * 
	 * 修改历史：
	 */
	@Override
	protected void init() {
		transMap.clear();
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
