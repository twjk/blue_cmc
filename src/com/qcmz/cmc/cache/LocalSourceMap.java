package com.qcmz.cmc.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.entity.CmcLtSource;
import com.qcmz.cmc.service.ILocalSourceService;
import com.qcmz.framework.cache.AbstractCacheMap;
import com.qcmz.framework.util.StringUtil;

@Component
public class LocalSourceMap extends AbstractCacheMap {
	@Autowired
	private ILocalSourceService localSourceService;
	
	private Map<Long, CmcLtSource> map;
	
	@PostConstruct
	@Override
	protected void init() {
		Map<Long, CmcLtSource> mapTemp = new HashMap<Long, CmcLtSource>();
		List<CmcLtSource> list = localSourceService.findSource();
		for (CmcLtSource bean : list) {
			mapTemp.put(bean.getSourceid(), bean);
		}
		
		map = mapTemp;
	}

	/**
	 * 根据来源名称模糊查询来源列表
	 * @param sourceName
	 * @param defaultIds 默认来源编号，多个用;分隔
	 * @return
	 * 修改历史：
	 */
	public List<CmcLtSource> findSource(String sourceName, List<Long> defaultIds){
		List<CmcLtSource> result = new ArrayList<CmcLtSource>(); 
		List<Long> ids = new ArrayList<Long>();
		if(!safeInit(map)) return result;
		
		//默认返回
		CmcLtSource bean = null;
		int size = 10;
		if(StringUtil.isBlankOrNull(sourceName)){
			if(defaultIds!=null){
				for (Long sourceId : defaultIds) {
					bean = getSource(sourceId);
					if(bean==null) continue;
					result.add(bean);
					ids.add(bean.getSourceid());
					if(--size<=0) break;
				}
			}
			if(size>0){
				for (Long key : map.keySet()) {
					if(ids.contains(key)) continue;
					bean = map.get(key);
					if(bean==null) continue;
					result.add(bean);
					if(--size<=0) break;
				}
			}
			return result;
		}
		
		//完全匹配
		for (Long key : map.keySet()) {
			bean = map.get(key);
			if(bean.getSourcename().equalsIgnoreCase(sourceName)){
				result.add(bean);
				size--;
			}
		}
		
		//模糊匹配
		for (Long key : map.keySet()) {
			bean = map.get(key);
			if(bean.getSourcename().contains(sourceName) && !bean.getSourcename().equalsIgnoreCase(sourceName)){
				result.add(bean);
				if(--size<=0) break;
			}
		}
		
		return result;
	}
	
	public CmcLtSource getSource(Long sourceId){
		if(!safeInit(map)) return null;
		return map.get(sourceId);
	}
	
	public String getSourceName(Long sourceId){
		CmcLtSource source = getSource(sourceId);
		if(source!=null) return source.getSourcename();
		return null;
	}
	
	/**
	 * 根据名称获取来源信息
	 * @param sourceName
	 * @return
	 */
	public CmcLtSource getSource(String sourceName){
		if(!safeInit(map)) return null;
		if(StringUtil.isBlankOrNull(sourceName)) return null;
		
		CmcLtSource source = null;
		for (Long key : map.keySet()) {
			source = map.get(key);
			if(source.getSourcename().equals(sourceName)){
				return source; 
			}
		}
		
		return null;
	}
	
	@Override
	public void update(Object obj) {}

	@Override
	public void delete(Object obj) {}
}
