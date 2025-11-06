package com.qcmz.cmc.ws.provide.vo;

/**
 * 类说明：代理帐户信息
 * 修改历史：
 */
public class ProxyAccountBean {
	/**
	 * 帐户编号
	 */
	private Long accountId;
	/**
	 * 代理编号
	 */
	private Long proxyId;
	/**
	 * 帐户
	 */
	private String account;
	/**
	 * 密钥
	 */
	private String key;
	/**
	 * 密钥2
	 */
	private String key2;
	/**
	 * 服务地址
	 */
	private String server;
	/**
	 * 服务端口
	 */
	private String port;
	/**
	 * 使用范围，0通用1普通用户2翻译机
	 */
	private int scope;
	
	public ProxyAccountBean() {
		super();
	}
	public ProxyAccountBean(ProxyAccountBean bean, String server) {
		super();
		this.accountId = bean.getAccountId();
		this.proxyId = bean.getProxyId();
		this.account = bean.getAccount();
		this.key = bean.getKey();
		this.key2 = bean.getKey2();
		this.server = server;
		this.port = bean.getPort();
		this.scope = bean.getScope();
	}

	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	public Long getProxyId() {
		return proxyId;
	}
	public void setProxyId(Long proxyId) {
		this.proxyId = proxyId;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getKey2() {
		return key2;
	}
	public void setKey2(String key2) {
		this.key2 = key2;
	}
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public int getScope() {
		return scope;
	}
	public void setScope(int scope) {
		this.scope = scope;
	}
}
