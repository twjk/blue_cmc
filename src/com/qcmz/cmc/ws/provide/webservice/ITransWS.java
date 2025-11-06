package com.qcmz.cmc.ws.provide.webservice;

import com.qcmz.cmc.ws.provide.vo.TransReq;
import com.qcmz.cmc.ws.provide.vo.TransResp;
import com.qcmz.cmc.ws.provide.vo.TransRespV2;

public interface ITransWS {
	/**
	 * 翻译
	 * @param req
	 * @return
	 * 修改历史：
	 */
	@Deprecated
	public TransResp translate(TransReq req);
	/**
	 * 翻译
	 * @param req
	 * @return
	 * 修改历史：
	 */
	public TransRespV2 translateV2(TransReq req);
}
