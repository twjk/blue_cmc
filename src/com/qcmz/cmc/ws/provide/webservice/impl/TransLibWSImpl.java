package com.qcmz.cmc.ws.provide.webservice.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.ws.provide.service.TransLibInterface;
import com.qcmz.cmc.ws.provide.vo.TransLibAddReq;
import com.qcmz.cmc.ws.provide.webservice.ITransLibWS;
import com.qcmz.framework.ws.BaseWS;
import com.qcmz.framework.ws.vo.Response;

/**
 * 类说明：翻译接口
 * 修改历史：
 */
@Component
public class TransLibWSImpl extends BaseWS implements ITransLibWS {
	@Autowired
	private TransLibInterface transLibInterface;
	
	/**
	 * 添加译库
	 * @param req
	 * @return
	 */
	public Response addTransLib(TransLibAddReq req){
		return transLibInterface.addTransLib(req, interfaceType, getRemoteAddr());
	}
}
