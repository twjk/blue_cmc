package com.qcmz.cmc.web.action;

import com.qcmz.cmc.process.CacheSynProcess;
import com.qcmz.framework.action.BaseAction;
import com.qcmz.framework.exception.SystemException;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class CacheAction extends BaseAction {
	
	/**
	 * 重载系统各节点缓存
	 */
	public String doRefresh(){
		try {
			String[] cacheIds = request.getParameterValues("beanid");
			CacheSynProcess.synCache(cacheIds);
		} catch(SystemException e){
			handleResult = false;
			setResult("重载缓存失败："+e.getMessage());
		} catch (Exception e) {
			logger.error("重载缓存失败", e);
			handleResult = false;
			setResult("重载缓存失败："+e.getMessage());
		}

		print();
		return null;
	}
}
