package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class PackageOrderAddReq extends Request {
	private PackageOrderAddBean bean = new PackageOrderAddBean();

	public PackageOrderAddBean getBean() {
		return bean;
	}

	public void setBean(PackageOrderAddBean bean) {
		this.bean = bean;
	}
}
