package com.qcmz.cmc.cache;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.qcmz.dmc.ws.provide.vo.AppUserIdentifyBean;
import com.qcmz.framework.cache.AbstractCacheMap;
import com.qcmz.framework.util.StringUtil;

@Component
public class AppUserForbidMap extends AbstractCacheMap {
	private Set<Long> uids;
	private Set<String> uuids;
	
	@Override
	protected void init() {
		/*
		List<AppUserIdentifyBean> list = AppUserWS.findForbidUser();
		if(list==null){
			return;
		}
		*/
		List<AppUserIdentifyBean> list = new ArrayList<AppUserIdentifyBean>();
		Set<Long> uidsTemp = new HashSet<Long>();
		Set<String> uuidsTemp = new HashSet<String>();
		for (AppUserIdentifyBean bean : list) {
			uidsTemp.add(bean.getUid());
			uuidsTemp.add(bean.getUuid());
		}
		
		uids = uidsTemp;
		uuids = uuidsTemp;
	}
	
	/**
	 * 用户是否被禁用
	 * @param uid
	 * @param uuid
	 * @return
	 */
	public boolean isForbid(Long uid, String uuid){
		if(safeInit(uids)){
			if(uids!=null && uid!=null && uids.contains(uid)){
				return true;
			}
			if(uuids!=null && StringUtil.notBlankAndNull(uuid) && uuids.contains(uuid)){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void update(Object obj) {}

	@Override
	public void delete(Object obj) {}

}
