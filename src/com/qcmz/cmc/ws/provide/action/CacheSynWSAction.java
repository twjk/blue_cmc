package com.qcmz.cmc.ws.provide.action;

import com.qcmz.cmc.process.CacheSynProcess;
import com.qcmz.framework.action.BaseWSAction;
import com.qcmz.framework.util.StringUtil;

public class CacheSynWSAction extends BaseWSAction {
	
	/**
	 * 重载的缓存ID，多个用,分隔
	 */
	private String cacheId;
	
	//重载缓存
	public String refreshCache(){
		try {
			logger.info("重载缓存：" + cacheId);
			String[] cacheIds = StringUtil.split(cacheId, ",");
			CacheSynProcess.refreshCache(cacheIds);
		} catch (Exception e) {
			handleResult = false;
			setResult("重载缓存失败：" + e.getMessage());
			logger.error("重载缓存失败", e);
		}
		
		print();

		return null;
	}
	
	//更新缓存
	public String updateCache(){
		try {
			logger.info("更新缓存：" + cacheId);
			CacheSynProcess.updateCache(cacheId, readObject());
		} catch (Exception e) {
			handleResult = false;
			setResult("更新缓存失败：" + e.getMessage());
			logger.error("更新缓存失败", e);
		}
		
		print();

		return null;
	}
	
	//删除缓存
	public String deleteCache(){
		try {
			logger.info("删除缓存：" + cacheId);
			CacheSynProcess.deleteCache(cacheId, readObject());
		} catch (Exception e) {
			handleResult = false;
			setResult("删除缓存失败：" + e.getMessage());
			logger.error("删除缓存失败", e);
		}
		
		print();

		return null;
	}

	public String getCacheId() {
		return cacheId;
	}
	public void setCacheId(String cacheId) {
		this.cacheId = cacheId;
	}
}
