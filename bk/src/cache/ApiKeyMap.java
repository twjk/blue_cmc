package com.qcmz.cmc.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.dao.IApiKeyDao;
import com.qcmz.cmc.vo.ApiKeyBean;
import com.qcmz.framework.cache.AbstractCacheMap;

/**
 * 类说明：ApiKey缓存
 * 修改历史：
 */
@Service
public class ApiKeyMap extends AbstractCacheMap {
	
	/**
	 * 分类的有效ApiKey
	 */
	private Map<String, List<String>> typeMap;
	
	@Autowired
	private IApiKeyDao apiKeyDao;
	
	/** 
	 * 
	 * 修改历史：
	 */
	@Override @PostConstruct
	protected void init() {
		logger.info("初始化ApiKey缓存开始");
		Map<String, List<String>> tempMap = new HashMap<String, List<String>>();
		List<ApiKeyBean> list = apiKeyDao.findValid();
		for (ApiKeyBean bean : list) {
			if(tempMap.get(bean.getApitype())==null) tempMap.put(bean.getApitype(), new ArrayList<String>());
			tempMap.get(bean.getApitype()).add(bean.getApikey());
		}
		typeMap = tempMap;
		
		logger.info("初始化ApiKey缓存完成");
	}
	
	
	
	/**
	 * 根据类型获取合适的key信息
	 * @param apiType
	 * @return
	 * 修改历史：
	 */
	public String getApiKey(String apiType){
		String key = "";
		List<String> list = typeMap.get(apiType);
		if(list!=null && !list.isEmpty()){
			key = list.get(new Random().nextInt(list.size()));
		}
		return key;
	}
	
	/**
	 * 获取可用的key数
	 * @param apiType
	 * @return
	 * 修改历史：
	 */
	public int getCount(String apiType){
		int count = 0;
		List<String> list = typeMap.get(apiType);
		if(list!=null){
			count = list.size();
		}
		return count;
	}
	
	/** 
	 * @param obj
	 * 修改历史：
	 */
	@Override
	public void delete(Object obj) {
	}

	public void delete(String apiType, String apiKey){
		if(typeMap==null) return;
		List<String> list = typeMap.get(apiType);
		if(list!=null && !list.isEmpty()){
			list.remove(apiKey);
		}
	}
	

	/** 
	 * @param obj
	 * 修改历史：
	 */
	@Override
	public void update(Object obj) {
	}

}
