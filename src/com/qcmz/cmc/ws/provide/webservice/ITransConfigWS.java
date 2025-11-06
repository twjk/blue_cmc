package com.qcmz.cmc.ws.provide.webservice;

import com.qcmz.cmc.ws.provide.vo.LangReq;
import com.qcmz.cmc.ws.provide.vo.LangResp;

public interface ITransConfigWS {
	/**
	 * 获取语言基础信息
	 * @param req
	 * @return
	 * 修改历史：
	 */
	public LangResp findLang(LangReq req);
}
