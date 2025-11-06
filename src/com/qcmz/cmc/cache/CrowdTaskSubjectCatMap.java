package com.qcmz.cmc.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.dao.ICrowdTaskSubjectCatDao;
import com.qcmz.cmc.entity.CmcCtSubjectcat;
import com.qcmz.framework.cache.AbstractCacheMap;

@Component
public class CrowdTaskSubjectCatMap extends AbstractCacheMap {
	@Autowired
	private ICrowdTaskSubjectCatDao  crowdTaskSubjectCatDao;
	
	private Map<Long, CmcCtSubjectcat> map;
	
	@Override @PostConstruct
	protected void init() {
		Map<Long, CmcCtSubjectcat> mapTemp = new HashMap<Long, CmcCtSubjectcat>();
		List<CmcCtSubjectcat> list = crowdTaskSubjectCatDao.findAll();
		for (CmcCtSubjectcat cat : list) {
			mapTemp.put(cat.getCatid(), cat);
		}
		
		map = mapTemp;
	}

	public CmcCtSubjectcat getCat(Long catId){
		if(safeInit(map)){
			return map.get(catId);
		}
		return null;
	}
	
	@Override
	public void update(Object obj) {
	}

	@Override
	public void delete(Object obj) {
	}

}
