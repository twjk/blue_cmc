package com.qcmz.cmc.cache;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.service.ITransLibService;
import com.qcmz.cmc.util.TransUtil;
import com.qcmz.cmc.vo.TransLib4Cache;
import com.qcmz.cmc.vo.TransLibraryEntity;
import com.qcmz.framework.cache.AbstractCacheMap;
import com.qcmz.framework.util.StringUtil;

/**
 * 译库缓存
 * 修改历史：
 */
@Component
public class TransLibMap extends AbstractCacheMap {
	@Autowired
	private ITransLibService transLibService;
	
	/**
	 * 译库缓存
	 * <from+to, <src.trim.toLowerCase.clearPuncLast, List<TransLib4Cache>>>
	 */
	private Map<String, Map<String, List<TransLib4Cache>>> map;
	
	/** 
	 * 
	 * 修改历史：
	 */
	@Override
	protected void init() {
		Date d1 = new Date();
		Map<String, Map<String, List<TransLib4Cache>>> mapTemp = new HashMap<String, Map<String,List<TransLib4Cache>>>();
		Map<String, List<TransLib4Cache>> mapTemp2 = null;
		List<TransLibraryEntity> list = null;
		String key = null;
		String key2 = null;
		long count = 0;
		for (String libClass : TransUtil.findAllLibClassWithoutTemp()) {
			list = transLibService.findLib4Cache(libClass);
			count = count + list.size();
			
			for (TransLibraryEntity entity : list) {
				key = getKey(entity.getFromlang(), entity.getTolang());
				key2 = getKey2(entity.getSrc());
				
				mapTemp2 = mapTemp.get(key);
				if(mapTemp2==null){
					mapTemp2 = new HashMap<String, List<TransLib4Cache>>();
					mapTemp.put(key, mapTemp2);
				}
				if(mapTemp2.get(key2)==null){
					mapTemp2.put(key2, new ArrayList<TransLib4Cache>());
				}
				mapTemp2.get(key2).add(new TransLib4Cache(entity));
			}
		}
		
		if(map!=null) map.clear();
		map = mapTemp;
		
		StringBuilder sb = new StringBuilder("译库缓存重载完成，条数[").append(count)
				.append("]耗时[").append(new Date().getTime()-d1.getTime()).append("]毫秒")
				;
		logger.info(sb.toString());
	}
	
	/**
	 * 获取译库信息
	 * @param from
	 * @param to
	 * @param src
	 * @return
	 * 修改历史：
	 */
	public List<TransLib4Cache> findLib(String from, String to, String src){
		if(safeInit(map)){
			Map<String, List<TransLib4Cache>> langMap = map.get(from+to);
			if(langMap!=null){
				return langMap.get(StringUtil.clearPuncLast(src.trim().toLowerCase()));
			}
		}
		return null;
	}
	
	/** 
	 * 删除
	 * @param obj
	 * 修改历史：
	 */
	@Override @SuppressWarnings("unchecked")
	public void delete(Object obj) {
		if(obj==null || map==null || map.isEmpty()) return;
		
		List<TransLibraryEntity> list = new ArrayList<TransLibraryEntity>();
		if(obj instanceof List){
			list = (List<TransLibraryEntity>) obj;
		}else if(obj instanceof TransLibraryEntity){
			list.add((TransLibraryEntity)obj);
		}
		
		Long d1 = System.currentTimeMillis();
		Map<String, List<TransLib4Cache>> langMap = null;
		Iterator<Map.Entry<String, List<TransLib4Cache>>> matchLangIterator;
		Map.Entry<String, List<TransLib4Cache>> temp;
		Iterator<TransLib4Cache> matchSrcIterator;
		for (TransLibraryEntity o : list) {
			String key = getKey(o.getFromlang(), o.getTolang());
			langMap = map.get(key);
			if(langMap!=null){
				matchLangIterator = langMap.entrySet().iterator();
				while (matchLangIterator.hasNext()) {
					temp = matchLangIterator.next();
					if(temp.getKey().equals(getKey2(o.getSrc()))){
						if(temp.getValue().size()<2){
							matchLangIterator.remove();
						}else{
							matchSrcIterator = temp.getValue().iterator();
							while(matchSrcIterator.hasNext()){
								if(matchSrcIterator.next().getDst().equals(o.getDst())){
									matchSrcIterator.remove();
								}
							}
						}
					}
				}
			}
		}

		StringBuilder sb = new StringBuilder("译库缓存删除完成，耗时[").append(System.currentTimeMillis()-d1).append("]毫秒");
		logger.info(sb.toString());
	}

	/** 
	 * 更新（只更新新增的）
	 * @param obj
	 * 修改历史：
	 */
	@Override @SuppressWarnings("unchecked")
	public void update(Object obj) {
		if(obj==null || map==null || map.isEmpty()) return;
		
		List<TransLibraryEntity> list = new ArrayList<TransLibraryEntity>();
		if(obj instanceof List){
			list = (List<TransLibraryEntity>) obj;
		}else if(obj instanceof TransLibraryEntity){
			list.add((TransLibraryEntity)obj);
		}
		
		Date d1 = new Date();
		String key;
		String key2;
		Map<String, List<TransLib4Cache>> matchLangMap = null;
		List<TransLib4Cache> matchSrcList = null;
		boolean exist = false;
		for (TransLibraryEntity entity : list) {
			key = getKey(entity.getFromlang(), entity.getTolang());
			key2 = getKey2(entity.getSrc());
			
			matchLangMap = map.get(key);
			if(matchLangMap==null){
				matchLangMap = new HashMap<String, List<TransLib4Cache>>();
				map.put(key, matchLangMap);
			}
			matchSrcList = matchLangMap.get(key2);
			if(matchSrcList==null){
				matchSrcList = new ArrayList<TransLib4Cache>();
				matchLangMap.put(key2, matchSrcList);
			}                        
			if(matchSrcList.isEmpty()){
				matchSrcList.add(new TransLib4Cache(entity));
			}else{
				exist = false;
				for (TransLib4Cache cache : matchSrcList) {
					if(cache.getLibId().equals(entity.getLibid())){
						cache.update(entity);
						exist = true;
						break;
					}
				}
				if(!exist){
					matchSrcList.add(new TransLib4Cache(entity));
				}
			}
		}
		StringBuilder sb = new StringBuilder("译库缓存更新完成，耗时[").append(new Date().getTime()-d1.getTime()).append("]毫秒");
		logger.info(sb.toString());
	}

	private String getKey(String from, String to){
		return new StringBuilder().append(from).append(to).toString();
	}
	
	private String getKey2(String content){
		return StringUtil.clearPuncLast(content.trim().toLowerCase());
	}
}
