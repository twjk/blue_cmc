package com.qcmz.cmc.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.qcmz.framework.cache.AbstractCacheMap;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.umc.ws.provide.locator.ActivityWS;
import com.qcmz.umc.ws.provide.vo.ActivityBean;

@Component
public class ActivityMap extends AbstractCacheMap{
	private Map<Long, ActivityBean> map;
	
	@Override
	protected void init() {
		Map<Long, ActivityBean> temp = new HashMap<Long, ActivityBean>();
		List<ActivityBean> list = ActivityWS.findSell(DictConstants.DICT_SERVICETYPE_VOICETRANS);
		if(list==null) return;
		
		for (ActivityBean bean : list) {
			temp.put(bean.getActId(), bean);
		}
		map = temp;
	}

	/**
	 * 获取活动
	 * @param actId
	 */
	public ActivityBean getActivity(Long actId){
		init();
		if(safeInit(map)){
			return map.get(actId);
		}
		return null;
	}
	
	@Override
	public void update(Object obj) {}

	@Override
	public void delete(Object obj) {}

}
