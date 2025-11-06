package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

public class ProxyAccountsBean {
	private List<ProxyAccountBean> accounts = new ArrayList<ProxyAccountBean>();

	public List<ProxyAccountBean> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<ProxyAccountBean> accounts) {
		this.accounts = accounts;
	}
}
