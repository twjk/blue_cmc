package com.qcmz.cmc.ws.provide.webservice.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.ws.provide.service.TransConfigInterface;
import com.qcmz.cmc.ws.provide.vo.LangReq;
import com.qcmz.cmc.ws.provide.vo.LangResp;
import com.qcmz.cmc.ws.provide.webservice.ITransConfigWS;
import com.qcmz.framework.ws.BaseWS;

/**
 * 类说明：翻译配置接口
 * 修改历史：
 */
@Component
public class TransConfigWSImpl extends BaseWS implements ITransConfigWS {
	@Autowired
	private TransConfigInterface transConfigInterface;
	
	/**
	 * 获取语言基础信息
	 * @param req
	 * @return
	 * 修改历史：
	 */
	public LangResp findLang(LangReq req){
		return transConfigInterface.findLang(req, interfaceType, getRemoteAddr());
	}
}
