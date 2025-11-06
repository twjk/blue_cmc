package com.qcmz.cmc.cache;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.service.ICrowdTaskBlacklistService;
import com.qcmz.framework.cache.AbstractCacheMap;

/**
 * 黑名单
 */
@Component
public class CrowdTaskBlacklistMap extends AbstractCacheMap {
	@Autowired
	private ICrowdTaskBlacklistService crowdTaskBlacklistService;
	
	private Set<Long> blacklistSet = null;
	
	@PostConstruct
	@Override
	protected void init() {
		Set<Long> blacklistSetTemp = new HashSet<Long>();
		
		List<Long> userIds = crowdTaskBlacklistService.findUserId();
		blacklistSetTemp.addAll(userIds);
		
		blacklistSet = blacklistSetTemp;		
	}
	
	/**
	 * 用户是否在黑名单中
	 * @param userId
	 * @return
	 */
	public boolean contains(Long userId){
		if(safeInit(blacklistSet)){
			return blacklistSet.contains(userId);
		}
		return false;
	}

	@Override
	public void update(Object obj) {}

	@Override
	public void delete(Object obj) {}
}
