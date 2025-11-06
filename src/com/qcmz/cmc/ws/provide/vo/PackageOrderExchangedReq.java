package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class PackageOrderExchangedReq extends Request {
	private PackageOrderExchangedBean bean = new PackageOrderExchangedBean();

	public PackageOrderExchangedBean getBean() {
		return bean;
	}

	public void setBean(PackageOrderExchangedBean bean) {
		this.bean = bean;
	}
}
