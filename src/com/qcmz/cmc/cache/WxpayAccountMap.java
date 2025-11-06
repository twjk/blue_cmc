package com.qcmz.cmc.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.dao.IWxpayAccountDao;
import com.qcmz.cmc.entity.CmcBWxpayaccount;
import com.qcmz.framework.cache.AbstractCacheMap;

@Component
public class WxpayAccountMap extends AbstractCacheMap {
	@Autowired
	private IWxpayAccountDao wxpayAccountDao;
	
	private Logger mointorLogger = Logger.getLogger("Monitor");
	
	private Map<String, CmcBWxpayaccount> map = null;
	
	
	@Override @PostConstruct
	protected void init() {
		Map<String, CmcBWxpayaccount> mapTemp = new HashMap<String, CmcBWxpayaccount>();
		
		List<CmcBWxpayaccount> list = wxpayAccountDao.findAccount();
		for (CmcBWxpayaccount account : list) {
			mapTemp.put(getKey(account.getServicetype(), account.getPlatform(), account.getSubservicetype()), account);
		}
		
		map = mapTemp;
	}
	
	public CmcBWxpayaccount getAccount(String serviceType, String platform, String subServiceType){
		if(!safeInit(map)) return null;
		
		CmcBWxpayaccount result = null;
		String key = null;
		
		//完全匹配
		key = getKey(serviceType, platform, subServiceType);
		result = map.get(key);
		//匹配未限定子业务类型的账号
		if(result==null){
			key = getKey(serviceType, platform, "");
			result = map.get(key);
		}
		//匹配未限定平台的账号
		if(result==null){
			key = getKey(serviceType, "", subServiceType);
			result = map.get(key);
		}
		//匹配未限定子业务类型和平台的账号
		if(result==null){
			key = getKey(serviceType, "", "");
			result = map.get(key);
		}
		
		StringBuilder sbLog = new StringBuilder("获取微信支付账号：");
		sbLog.append(getKey(serviceType, platform, subServiceType)).append("  >>> ");
		if(result==null){
			sbLog.append("null");
		}else{
			sbLog.append(getKey(result.getServicetype(), result.getPlatform(), result.getSubservicetype()))
				 .append(",").append(result.getAccountid()).append(",").append(result.getAppid())
				 ;
		}
		mointorLogger.info(sbLog);
		
		return result;
	}

	private String getKey(String serviceType, String platform, String subServiceType){
		StringBuilder sb = new StringBuilder(serviceType);
		sb.append("|");
		if(platform!=null){
			sb.append(platform);
		}
		sb.append("|");
		if(subServiceType!=null){
			sb.append(subServiceType);
		}
		return sb.toString();
	}
	
	@Override
	public void update(Object obj) {
	}

	@Override
	public void delete(Object obj) {
	}

}
