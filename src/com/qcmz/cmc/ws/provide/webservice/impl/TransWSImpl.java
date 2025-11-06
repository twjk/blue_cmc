package com.qcmz.cmc.ws.provide.webservice.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.ws.provide.service.TransInterface;
import com.qcmz.cmc.ws.provide.vo.TransReq;
import com.qcmz.cmc.ws.provide.vo.TransResp;
import com.qcmz.cmc.ws.provide.vo.TransRespV2;
import com.qcmz.cmc.ws.provide.webservice.ITransWS;
import com.qcmz.framework.ws.BaseWS;

/**
 * 类说明：翻译接口
 * 修改历史：
 */
@Component
public class TransWSImpl extends BaseWS implements ITransWS {
	@Autowired
	private TransInterface transInterface;
	
	/**
	 * 翻译
	 * @param req
	 * @return
	 * 修改历史：
	 */
	@Deprecated
	public TransResp translate(TransReq req){
		return transInterface.translate(req, interfaceType, getRemoteAddr());
	}
	
	/**
	 * 翻译
	 * @param req
	 * @return
	 * 修改历史：
	 */
	public TransRespV2 translateV2(TransReq req){
		return transInterface.translateV2(req, interfaceType, getRemoteAddr());
	}
}
