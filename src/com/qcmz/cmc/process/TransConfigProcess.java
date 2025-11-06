package com.qcmz.cmc.process;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.cache.FuncCapMap;
import com.qcmz.cmc.cache.LangMap;
import com.qcmz.cmc.cache.TransPicLangMap;
import com.qcmz.cmc.cache.TransTextLangMap;
import com.qcmz.cmc.config.TransConfig;
import com.qcmz.cmc.ws.provide.vo.TransConfigsBean;

@Component
public class TransConfigProcess {
	@Autowired
	private LangMap langMap;
	@Autowired
	private TransPicLangMap transPicLangMap;
	@Autowired
	private TransTextLangMap transTextLangMap;
	@Autowired
	private FuncCapMap funcCapMap;
	
	@Autowired
	private ProxyAccountProcess proxyAccountProcess;
	
	/**
	 * 获取翻译配置
	 * @param channel
	 * @return
	 */
	public TransConfigsBean findConfig(String serviceType, String platform, String version, String language){
		TransConfigsBean result = new TransConfigsBean();

		//代理账户
		result.setProxyAccounts(proxyAccountProcess.findProxyAccount(serviceType, platform, version));
		
		//代理能力
		result.setFuncCapCfg(funcCapMap.findCaps(serviceType, platform, version));
		
		//语音语言
		result.setSpeechLangs(langMap.findLang4Speech(language));
		
		//图片翻译
		result.getTransPicCfg().setLangs(transPicLangMap.findLang());

		//短文快译
		result.getTransTextCfg().setOn(TransConfig.TRANSTEXT_ON);
		result.getTransTextCfg().setServiceTime(TransConfig.TRANSTEXT_WORKTIME);
		result.getTransTextCfg().setServiceTimeDesc(TransConfig.TRANSTEXT_WORKTIMEDESC);
		result.getTransTextCfg().setTimeout(TransConfig.TRANSTEXT_TIMEOUT);
		result.getTransTextCfg().setMaxWordNum(TransConfig.TRANSTEXT_MAXWORDNUM);
		result.getTransTextCfg().setLangs(transTextLangMap.findLang());
		result.getTransTextCfg().setChannels(TransConfig.TRANSTEXT_CHANNELS);
		
		return result;
	}
}
