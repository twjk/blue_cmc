package com.qcmz.cmc.proxy;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.cache.ProxyMap;
import com.qcmz.cmc.proxy.speech.AbstractSpeechProxy;
import com.qcmz.cmc.proxy.speech.azure.AzureSpeechProxy;
import com.qcmz.cmc.proxy.speech.baidu.BaiduSpeechProxy;
import com.qcmz.cmc.proxy.speech.google.GoogleSpeechProxy;
import com.qcmz.cmc.proxy.trans.AbstractTransProxy;
import com.qcmz.cmc.proxy.trans.BaiduTransProxyV2;
import com.qcmz.cmc.proxy.trans.BingTransProxyV2;
import com.qcmz.cmc.proxy.trans.GoogleTransProxy;
import com.qcmz.cmc.proxy.trans.HcicloudTransProxy;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
@Component
public class ProxyMgr {
	@Autowired
	private BaiduTransProxyV2 baiduTransProxyV2;
	@Autowired
	private BingTransProxyV2 bingTransProxyV2;
	@Autowired
	private GoogleTransProxy googleTransProxy;
	@Autowired
	private HcicloudTransProxy hcicloudTransProxy;
	
	@Autowired
	private BaiduSpeechProxy baiduSpeechProxy;
	@Autowired
	private GoogleSpeechProxy googleSpeechProxy;
	@Autowired
	private AzureSpeechProxy azureSpeechProxy;
	
	@Autowired
	private ProxyMap proxyMap;
	
	private static Map<Long, AbstractTransProxy> transProxyMap;
	private static Map<Long, AbstractSpeechProxy> speechProxyMap;
	
	/**
	 * 获取翻译代理实例
	 * @param proxyId
	 * @return
	 * 修改历史：
	 */
	public AbstractTransProxy getTransProxy(Long proxyId){
		if(transProxyMap==null){
			initProxyMap();
		}
		return transProxyMap.get(proxyId);
	}
	
	/**
	 * 获取语音代理实例
	 * @param proxyId
	 * @return
	 * 修改历史：
	 */
	public AbstractSpeechProxy getSpeechProxy(Long proxyId){
		if(speechProxyMap==null){
			initProxyMap();
		}
		return speechProxyMap.get(proxyId);
	}
	
	public static void clear(){
		transProxyMap = null;
		speechProxyMap = null;
	}
	
	public void initProxyMap(){
		//翻译
		transProxyMap = new HashMap<Long, AbstractTransProxy>();
		if(proxyMap.getValidBean(baiduTransProxyV2.getProxyId())!=null){
			transProxyMap.put(baiduTransProxyV2.getProxyId(), baiduTransProxyV2);
		}
		if(proxyMap.getValidBean(bingTransProxyV2.getProxyId())!=null){
			transProxyMap.put(bingTransProxyV2.getProxyId(), bingTransProxyV2);
		}
		if(proxyMap.getValidBean(googleTransProxy.getProxyId())!=null){
			transProxyMap.put(googleTransProxy.getProxyId(), googleTransProxy);
		}
		if(proxyMap.getValidBean(hcicloudTransProxy.getProxyId())!=null){
			transProxyMap.put(hcicloudTransProxy.getProxyId(), hcicloudTransProxy);
		}
		
		//语音
		speechProxyMap = new HashMap<Long, AbstractSpeechProxy>();
		if(proxyMap.getValidBean(baiduSpeechProxy.getProxyId())!=null){
			speechProxyMap.put(baiduSpeechProxy.getProxyId(), baiduSpeechProxy);
		}
		if(proxyMap.getValidBean(googleSpeechProxy.getProxyId())!=null){
			speechProxyMap.put(googleSpeechProxy.getProxyId(), googleSpeechProxy);
		}
		if(proxyMap.getValidBean(azureSpeechProxy.getProxyId())!=null){
			speechProxyMap.put(azureSpeechProxy.getProxyId(), azureSpeechProxy);
		}
	}
}
