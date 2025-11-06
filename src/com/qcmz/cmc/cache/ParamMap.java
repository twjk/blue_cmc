package com.qcmz.cmc.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.qcmz.bdc.ws.provide.locator.ParamWS;
import com.qcmz.bdc.ws.provide.vo.ParamBean;
import com.qcmz.framework.cache.AbstractCacheMap;
import com.qcmz.framework.util.StringUtil;

/**
 * 在线参数缓存
 */
@Component
public class ParamMap extends AbstractCacheMap {
	private Map<String, String> map = null;
	
	@Override
	protected void init() {
		Map<String, String> mapTemp = new HashMap<String, String>();
		List<ParamBean> list = ParamWS.findParam(null);
		for (ParamBean bean : list) {
			mapTemp.put(getKey(bean.getServiceType(), bean.getParamName()), bean.getParamValue());
		}
		map = mapTemp;
	}

	private String getKey(String serviceType, String paramName){
		return new StringBuilder(serviceType).append("|").append(paramName).toString();
	}
	
	/**
	 * 获取参数值
	 * @param paramName
	 * @return
	 */
	public String getParam(String serviceType, String paramName){
		if(safeInit(map)){
			return map.get(paramName);
		}
		return null;
	}
	
	/**
	 * 众包任务审核人员编号
	 * @return
	 */
	public List<Long> findCrowdTaskVerifyUserId(String serviceType){
		String userIds = getParam(serviceType, "TRANS_CROWDTASK_VERIFYUSER");
		if(StringUtil.notBlankAndNull(userIds)){
			return StringUtil.split2LongList(userIds, ",");
		}
		return new ArrayList<Long>();
	}
	
	/**
	 * 众包任务审校人员编号
	 * @return
	 */
	public List<Long> findCrowdTaskQcUserId(String serviceType){
		String userIds = getParam(serviceType, "TRANS_CROWDTASK_QCUSER");
		if(StringUtil.notBlankAndNull(userIds)){
			return StringUtil.split2LongList(userIds, ",");
		}
		return new ArrayList<Long>();
	}	
	
	@Override
	public void update(Object obj) {}

	@Override
	public void delete(Object obj) {}

}
