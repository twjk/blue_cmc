package com.qcmz.cmc.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.entity.CmcProxyAccount;
import com.qcmz.cmc.service.IProxyAccountService;
import com.qcmz.cmc.util.CmcUtil;
import com.qcmz.cmc.ws.provide.vo.ProxyAccountBean;
import com.qcmz.framework.cache.AbstractCacheMap;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.util.StringUtil;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
@Component
public class ProxyAccountMap extends AbstractCacheMap {
	@Autowired
	private IProxyAccountService proxyAccountService;
	/**
	 * <proxyId, list>
	 */
	private Map<Long, List<CmcProxyAccount>> javaMap = null;
	/**
	 * 帐户和key加密
	 * <platform,<proxyId|scope, list>>
	 */
	private Map<String, Map<String, List<ProxyAccountBean>>> beanMap = null;
	
	/** 
	 * 
	 * 修改历史：
	 */
	@Override @PostConstruct
	protected void init() {
		Map<Long, List<CmcProxyAccount>> tempJavaMap = new HashMap<Long, List<CmcProxyAccount>>();
		Map<String, Map<String, List<ProxyAccountBean>>> tempBeanMap = new HashMap<String, Map<String, List<ProxyAccountBean>>>();
		
		List<CmcProxyAccount> list = proxyAccountService.findValidAccount();
		
		ProxyAccountBean bean = null;
		List<String> platforms = null;
		String key2 = null;
		for (CmcProxyAccount account : list) {
			//java平台
			if(StringUtil.isBlankOrNull(account.getPlatform())
					|| DictConstants.DICT_PLATFORM_JAVA.equals(account.getPlatform())){
				if(tempJavaMap.get(account.getProxyid())==null){
					tempJavaMap.put(account.getProxyid(), new ArrayList<CmcProxyAccount>());
				}
				tempJavaMap.get(account.getProxyid()).add(account);
			}
			
			//非java平台
			if(StringUtil.isBlankOrNull(account.getPlatform())){
				platforms = DictConstants.DICT_PLATFORMS_DEVICE;
			}else if(DictConstants.DICT_PLATFORM_JAVA.equals(account.getPlatform())){
				continue;
			}else{
				platforms = new ArrayList<String>();
				platforms.add(account.getPlatform());
			}
			for (String platform : platforms) {
				key2 = getKey2(account);
				if(tempBeanMap.get(platform)==null){
					tempBeanMap.put(platform, new HashMap<String, List<ProxyAccountBean>>());
				}
				if(tempBeanMap.get(platform).get(key2)==null){
					tempBeanMap.get(platform).put(key2, new ArrayList<ProxyAccountBean>());
				}
				
				bean = new ProxyAccountBean();
				bean.setAccountId(account.getAccountid());
				bean.setProxyId(account.getProxyid());
				bean.setAccount(CmcUtil.encrypt(account.getAccount()));
				bean.setKey(CmcUtil.encrypt(account.getKey1()));
				bean.setKey2(CmcUtil.encrypt(account.getKey2()));
				bean.setServer(account.getServerurl());
				bean.setPort(account.getServerport());
				bean.setScope(account.getScope());
				
				tempBeanMap.get(platform).get(key2).add(bean);
			}
		}
		
		javaMap = tempJavaMap;
		beanMap = tempBeanMap;
	}
	
	private String getKey2(CmcProxyAccount account){
		return new StringBuilder().append(account.getProxyid()).append("|").append(account.getScope()).toString();
	}
	
	/**
	 * 获取指定平台的帐户，每个Proxy|scope随机返回一个帐户
	 * @param platform
	 * @return
	 * 修改历史：
	 */
	public List<ProxyAccountBean> findAccount(String platform){
		List<ProxyAccountBean> result = new ArrayList<ProxyAccountBean>();
		Map<String, List<ProxyAccountBean>> map = beanMap.get(platform);
		if(map!=null){
			List<ProxyAccountBean> list = null;
			for (String key : map.keySet()) {
				list = map.get(key);
				result.add(list.get(new Random().nextInt(list.size())));
			}
		}
		return result;
	}
	
	/**
	 * 获取Java平台帐户
	 * @param proxyId
	 * @return
	 * 修改历史：
	 */
	public CmcProxyAccount getAccount4Java(Long proxyId){
		List<CmcProxyAccount> accounts = javaMap.get(proxyId);
		if(accounts==null || accounts.isEmpty()) return null;
		return accounts.get(new Random().nextInt(accounts.size()));
	}
	
	/**
	 * 获取帐户数
	 * @param proxyId
	 * @return
	 * 修改历史：
	 */
	public int getCount4Java(Long proxyId){
		List<CmcProxyAccount> accounts = javaMap.get(proxyId);
		if(accounts==null || accounts.isEmpty()) return 0;
		return accounts.size();
	}
	
	/** 
	 * @param obj
	 * 修改历史：
	 */
	@Override
	public void delete(Object obj) {
		
	}
	
	/** 
	 * @param obj
	 * 修改历史：
	 */
	@Override
	public void update(Object obj) {
	}
}
