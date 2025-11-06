package com.qcmz.cmc.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.dao.IAlipayAccountDao;
import com.qcmz.cmc.entity.CmcBAlipayaccount;
import com.qcmz.cmc.proxy.pay.alipay.AlipayProxy;
import com.qcmz.framework.cache.AbstractCacheMap;

@Component
public class AlipayAccountMap extends AbstractCacheMap {
	@Autowired
	private IAlipayAccountDao alipayAccountDao;
	
	private Logger mointorLogger = Logger.getLogger("Monitor");
	
	private Map<String, CmcBAlipayaccount> map = null;	
	
	@Override @PostConstruct
	protected void init() {
		Map<String, CmcBAlipayaccount> mapTemp = new HashMap<String, CmcBAlipayaccount>();
		
		List<CmcBAlipayaccount> list = alipayAccountDao.findAccount();
		for (CmcBAlipayaccount account : list) {
			mapTemp.put(account.getServicetype(), account);
		}
		
		map = mapTemp;
		
		AlipayProxy.init();
	}
	
	/**
	 * 获取支付宝账号
	 */
	public CmcBAlipayaccount getAccount(String serviceType){
		CmcBAlipayaccount result = null;
		if(safeInit(map)){
			result = map.get(serviceType);
		}		
		
		StringBuilder sbLog = new StringBuilder("获取支付宝账号：");
		sbLog.append(serviceType).append("  >>> ");
		if(result==null){
			sbLog.append("null");
		}else{
			sbLog.append(result.getAccountid()).append(",").append(result.getAppid());
		}
		mointorLogger.info(sbLog);
		
		return result;
	}
	
	@Override
	public void update(Object obj) {
	}

	@Override
	public void delete(Object obj) {
	}

}
