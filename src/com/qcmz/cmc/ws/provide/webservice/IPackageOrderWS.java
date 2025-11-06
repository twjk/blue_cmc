package com.qcmz.cmc.ws.provide.webservice;

import com.qcmz.cmc.ws.provide.vo.PackageOrderExchangedReq;
import com.qcmz.framework.ws.vo.Response;

public interface IPackageOrderWS {
	/**
	 * 保存套餐兑换信息
	 * @param req
	 * @return
	 * 修改历史：
	 */
	public Response saveExchanged(PackageOrderExchangedReq req);
}
