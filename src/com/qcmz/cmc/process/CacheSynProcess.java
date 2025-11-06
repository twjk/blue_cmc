package com.qcmz.cmc.process;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.qcmz.cmc.config.SystemConfig;
import com.qcmz.cmc.constant.BeanConstant;
import com.qcmz.framework.action.BaseAction;
import com.qcmz.framework.cache.AbstractCacheMap;
import com.qcmz.framework.exception.SystemException;
import com.qcmz.framework.util.ArrayUtil;
import com.qcmz.framework.util.CollectionUtil;
import com.qcmz.framework.util.HttpUtil;
import com.qcmz.framework.util.SpringUtil;
import com.qcmz.framework.util.StaticUtil;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.srm.client.cache.GroupUserCache;
import com.qcmz.srm.client.cache.UserCache;
import com.qcmz.srm.client.util.SrmClient;
import com.qcmz.umc.ws.provide.locator.UserMap;

/**
 * 缓存同步
 *
 */
public class CacheSynProcess {
	private static Logger logger = Logger.getLogger(CacheSynProcess.class); 
	
	/**
	 * 重载本机缓存
	 * @param cacheIds
	 */
	public static void refreshCache(String[] cacheIds){
		if(cacheIds!=null && cacheIds.length>0){
			for (String cacheId : cacheIds) {
				if(BeanConstant.BEANID_CACHE_STATICTEMPLATE.equals(cacheId)){
					StaticUtil.reInit();
				}else if(GroupUserCache.BEANID_CACHE_GROUPUSERCACHE.equals(cacheId)){
					GroupUserCache.refresh();
				}else if(UserCache.BEANID_CACHE_USERCACHE.equals(cacheId)){
					UserCache.refresh();
				}else if(SrmClient.BEANID_CACHE_SRMCLIENT.equals(cacheId)){
					SrmClient.refresh();
				}else if(UserMap.BEANID_CACHE_USERMAP.equals(cacheId)){
					UserMap.refresh();
				}else{
					((AbstractCacheMap)SpringUtil.getBean(cacheId)).refresh();
				}
			}
		}
	}
	
	/**
	 * 更新本机缓存
	 * @param cacheId
	 * @param cacheData
	 */
	public static void updateCache(String cacheId, Object cacheData){
		((AbstractCacheMap)SpringUtil.getBean(cacheId)).update(cacheData);
	}
	
	/**
	 * 删除本机缓存
	 * @param cacheId
	 * @param cacheData
	 */
	public static void deleteCache(String cacheId, Object cacheData){
		((AbstractCacheMap)SpringUtil.getBean(cacheId)).delete(cacheData);
	}
	
	/**
	 * 起线程同步缓存
	 * @param cacheId
	 */
	public static void synCacheThrd(final String cacheId){
		new Thread(new Runnable() {
			@Override
			public void run() {
				synCache(cacheId);
			}
		}).start();
	}
	
	/**
	 * 起线程同步更新缓存
	 * @param cacheId
	 */
	public static void synUpdateCacheThrd(final String cacheId, final Object cacheData){
		new Thread(new Runnable() {
			@Override
			public void run() {
				synUpdateCache(cacheId, cacheData);
			}
		}).start();
	}
	
	/**
	 * 起线程同步更新缓存
	 * @param cacheId
	 */
	public static void synDeleteCacheThrd(final String cacheId, final Object cacheData){
		new Thread(new Runnable() {
			@Override
			public void run() {
				synDeleteCache(cacheId, cacheData);
			}
		}).start();
	}
	
	/**
	 * 同步各分发服务器缓存
	 * @param cacheIds
	 * throws SystemException
	 */
	public static void synCache(String cacheId){
		if(StringUtil.notBlankAndNull(cacheId)){
			synCache(new String[]{cacheId});
		}
	}
	
	/**
	 * 同步重载各分发服务器缓存
	 * @param cacheIds
	 * throws SystemException
	 */
	public static void synCache(String[] cacheIds){
		if(SystemConfig.CMC_DISTS==null
				|| cacheIds==null || cacheIds.length==0){
			return;
		}
		
		String cacheId = ArrayUtil.toString(cacheIds);
		
		Set<String> errServer = new HashSet<String>();
		StringBuilder sbLog = null;
		String url = null;
		for (String server : SystemConfig.CMC_DISTS) {
			try {
				if(StringUtil.isBlankOrNull(server)) continue;
				url = server + "/services/cacheSyn!refreshCache.do";
				String resp = HttpUtil.post(url, "cacheId="+cacheId);
				if(BaseAction.HANDLE_SUCCESS.equals(resp)){
					sbLog = new StringBuilder()
						.append("成功重载[").append(server)
						.append("]的缓存[").append(cacheId).append("]")
					;
					logger.info(sbLog);
				}else{
					errServer.add(server);
					sbLog = new StringBuilder()
						.append("重载[").append(server)
						.append("]的缓存[").append(cacheId)
						.append("]失败，返回：").append(resp)
					;
					logger.error(sbLog);
				}
			} catch (Exception e) {
				errServer.add(server);
				logger.error("重载缓存失败："+url, e);
			}
			
		}
		
		if(!errServer.isEmpty()){
			throw new SystemException(CollectionUtil.toString(errServer));
		}
	}
	
	/**
	 * 同步更新各分发服务器缓存
	 * @param cacheId
	 * @param cacheData
	 */
	public static void synUpdateCache(String cacheId, Object cacheData){
		if(SystemConfig.CMC_DISTS==null
				|| StringUtil.isBlankOrNull(cacheId)){
			return;
		}
		
		Set<String> errServer = new HashSet<String>();
		StringBuilder sbLog = null;
		String url = null;
		for (String server : SystemConfig.CMC_DISTS) {
			try {
				if(StringUtil.isBlankOrNull(server)) continue;
				url = server + "/services/cacheSyn!updateCache.do?cacheId="+cacheId;
				String resp = HttpUtil.sendObject(url, cacheData);
				if(BaseAction.HANDLE_SUCCESS.equals(resp)){
					sbLog = new StringBuilder()
						.append("成功更新[").append(server)
						.append("]的缓存[").append(cacheId).append("]")
					;
					logger.info(sbLog);
				}else{
					errServer.add(server);
					sbLog = new StringBuilder()
						.append("更新[").append(server)
						.append("]的缓存[").append(cacheId)
						.append("]失败，返回：").append(resp)
					;
					logger.error(sbLog);
				}
			} catch (Exception e) {
				errServer.add(server);
				logger.error("更新缓存失败："+url, e);
			}
			
		}
		
		if(!errServer.isEmpty()){
			throw new SystemException(CollectionUtil.toString(errServer));
		}
	}
	
	/**
	 * 同步删除各分发服务器缓存
	 * @param cacheId
	 * @param cacheData
	 */
	public static void synDeleteCache(String cacheId, Object cacheData){
		if(SystemConfig.CMC_DISTS==null
				|| StringUtil.isBlankOrNull(cacheId)){
			return;
		}
		
		Set<String> errServer = new HashSet<String>();
		StringBuilder sbLog = null;
		String url = null;
		for (String server : SystemConfig.CMC_DISTS) {
			try {
				if(StringUtil.isBlankOrNull(server)) continue;
				url = server + "/services/cacheSyn!deleteCache.do?cacheId="+cacheId;
				String resp = HttpUtil.sendObject(url, cacheData);
				if(BaseAction.HANDLE_SUCCESS.equals(resp)){
					sbLog = new StringBuilder()
						.append("成功删除[").append(server)
						.append("]的缓存[").append(cacheId).append("]")
					;
					logger.info(sbLog);
				}else{
					errServer.add(server);
					sbLog = new StringBuilder()
						.append("删除[").append(server)
						.append("]的缓存[").append(cacheId)
						.append("]失败，返回：").append(resp)
					;
					logger.error(sbLog);
				}
			} catch (Exception e) {
				errServer.add(server);
				logger.error("删除缓存失败："+url, e);
			}
			
		}
		
		if(!errServer.isEmpty()){
			throw new SystemException(CollectionUtil.toString(errServer));
		}
	}
	
}
