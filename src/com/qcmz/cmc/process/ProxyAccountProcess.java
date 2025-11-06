package com.qcmz.cmc.process;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.util.concurrent.AtomicLongMap;
import com.qcmz.cmc.cache.ProxyAccountMap;
import com.qcmz.cmc.constant.BusinessConstant;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.util.CmcUtil;
import com.qcmz.cmc.util.VersionUtil;
import com.qcmz.cmc.ws.provide.vo.ProxyAccountBean;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.util.BeanUtil;
import com.qcmz.framework.util.EncodeUtil;

/**
 * 类说明：代理帐户处理
 * 修改历史：
 */
@Component
public class ProxyAccountProcess {
	@Autowired
	private ProxyAccountMap proxyAccountMap;

	/**
	 * 访问次数缓存,accountId
	 */
	public AtomicLongMap<Long> callMap = AtomicLongMap.create();
	
	/**
	 * 获取代理帐户
	 * 版本兼容处理：老版本不返回专用帐户
	 * @param platform
	 * @param version
	 * @return
	 */
	public List<ProxyAccountBean> findProxyAccount(String serviceType, String platform, String version){
		List<ProxyAccountBean> list = proxyAccountMap.findAccount(platform);
		
		//语音聊天变声器
		if(DictConstants.DICT_SERVICETYPE_VOICE.equals(serviceType)){
			for (ProxyAccountBean bean : list) {
				if(BusinessConstant.PROXYID_AZURESPEECH.equals(bean.getProxyId())){
					bean.setKey(CmcUtil.encrypt("1232e5739e9540da98e1133430f283d6"));
					bean.setKey2(CmcUtil.encrypt("56e7cbc380914aea92de8447a3cd0787"));
					bean.setServer("eastasia");
					break;
				}
			}
		}
		
		//老版本一个代理返回一个帐户，删除专用账户
		if(VersionUtil.compat4(serviceType, platform, version)){
			Iterator<ProxyAccountBean> it = list.iterator();
			ProxyAccountBean bean = null;
			while(it.hasNext()){
				bean = it.next();
				if(!DictConstant.DICT_PROXYACCOUNT_USESCOPE_COMMON.equals(bean.getScope())
						&& !DictConstant.DICT_PROXYACCOUNT_USESCOPE_GENERAL.equals(bean.getScope())){
					it.remove();
				}
			}
		}
		
		//老版本补全微软服务地址
		if(VersionUtil.needCompleteMicrosoftServer(serviceType, platform, version)){
			List<ProxyAccountBean> newList = new ArrayList<ProxyAccountBean>();
			ProxyAccountBean bean = null;
			String server = null;
			for (ProxyAccountBean proxyAccountBean : list) {
				if(proxyAccountBean.getProxyId().equals(BusinessConstant.PROXYID_BINGSPEECH)){
					server = new StringBuilder(proxyAccountBean.getServer()).append("/sts/v1.0").toString();
					bean = new ProxyAccountBean(proxyAccountBean, server);
				}else if(proxyAccountBean.getProxyId().equals(BusinessConstant.PROXYID_BINGVISION)){
					server = new StringBuilder(proxyAccountBean.getServer()).append("/vision/v1.0").toString();
					bean = new ProxyAccountBean(proxyAccountBean, server);
				}else{
					bean = proxyAccountBean;
				}
				newList.add(bean);
			}
			list = newList;
		}
		
		return list;
	}
	
	/**
	 * 获取指定平台的帐户，每个Proxy随机返回一个帐户(不包含专用帐户)
	 * 该方法主要用于兼容原安卓接口
	 * @param platform
	 * @return
	 * 修改历史：
	 */
	public List<ProxyAccountBean> findSpeechAccount4AndroidOld(){
		List<ProxyAccountBean> result = new ArrayList<ProxyAccountBean>();
		ProxyAccountBean bean = null;
		
		List<ProxyAccountBean> list = proxyAccountMap.findAccount(DictConstants.DICT_PLATFORM_ANDROID);
		for (ProxyAccountBean account : list) {
			if(DictConstant.DICT_PROXYACCOUNT_USESCOPE_COMMON.equals(account.getScope())
					|| DictConstant.DICT_PROXYACCOUNT_USESCOPE_GENERAL.equals(account.getScope())){
				if(BusinessConstant.PROXYID_HCICLOUD.equals(account.getProxyId())){
					//灵云
					bean = (ProxyAccountBean) BeanUtil.cloneBean(account);
					bean.setProxyId(1L);
					result.add(bean);
				}else if(BusinessConstant.PROXYID_IFLTEK.equals(account.getProxyId())){
					//科大讯飞
					bean = (ProxyAccountBean) BeanUtil.cloneBean(account);
					bean.setProxyId(2L);
					result.add(bean);
				}else if(BusinessConstant.PROXYID_NUANCE.equals(account.getProxyId())){
					//Nuance
					bean = (ProxyAccountBean) BeanUtil.cloneBean(account);
					bean.setKey(CmcUtil.encrypt(EncodeUtil.hexStr2Bytes(CmcUtil.decrypt(bean.getKey()))));
					bean.setProxyId(3L);
					result.add(bean);
				}
			}
		}
		
		return result;
	}
}
