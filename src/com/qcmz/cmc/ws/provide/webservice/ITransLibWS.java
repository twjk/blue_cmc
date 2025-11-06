package com.qcmz.cmc.ws.provide.webservice;

import com.qcmz.cmc.ws.provide.vo.TransLibAddReq;
import com.qcmz.framework.ws.vo.Response;

public interface ITransLibWS {
	/**
	 * 添加译库
	 * @param req
	 * @return
	 */
	public Response addTransLib(TransLibAddReq req);
}
