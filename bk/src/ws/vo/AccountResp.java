package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Response;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class AccountResp extends Response {
	/**
	 * 帐户信息
	 */
	private AccountBean account;
	
	public AccountBean getAccount() {
		return account;
	}

	public void setAccount(AccountBean account) {
		this.account = account;
	}
}
