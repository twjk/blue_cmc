package com.qcmz.cmc.ws.provide.webservice.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.ws.provide.service.PackageOrderInterface;
import com.qcmz.cmc.ws.provide.vo.PackageOrderExchangedReq;
import com.qcmz.cmc.ws.provide.webservice.IPackageOrderWS;
import com.qcmz.framework.ws.BaseWS;
import com.qcmz.framework.ws.vo.Response;

@Component
public class PackageOrderWSImpl extends BaseWS implements IPackageOrderWS {
	@Autowired
	private PackageOrderInterface packageOrderInterface;
	
	/**
	 * 保存套餐兑换信息
	 * @param req
	 * @return
	 * 修改历史：
	 */
	public Response saveExchanged(PackageOrderExchangedReq req){
		return packageOrderInterface.saveExchanged(req, interfaceType, getRemoteAddr());
	}
}
