package com.qcmz.cmc.vo;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class BingTokenBean {
	/**
	 * token有效时长，10分钟600秒，为了衔接采用590秒
	 */
	private final long tokenExpire = 590000;
	
	/**
	 * key
	 */
	private String apiKey;
	/**
	 * token
	 */
	private String accessToken;
	/**
	 * token过期时间
	 */
	private long tokenExpiration;
	
	public BingTokenBean() {
		super();
	}
	
	public BingTokenBean(String apiKey, String accessToken) {
		super();
		this.apiKey = apiKey;
		this.accessToken = accessToken;
		this.tokenExpiration = System.currentTimeMillis()+tokenExpire;
	}

	/**
	 * 是否有效
	 * @return
	 */
	public boolean isValid(){
		return System.currentTimeMillis()<tokenExpiration;
	}
	
	/**
	 * 是否即将失效
	 * @return
	 */
	public boolean isInvalidSoon(){
		return System.currentTimeMillis()+10000>tokenExpiration;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((accessToken == null) ? 0 : accessToken.hashCode());
		result = prime * result + ((apiKey == null) ? 0 : apiKey.hashCode());
		result = prime * result
				+ (int) (tokenExpiration ^ (tokenExpiration >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof BingTokenBean))
			return false;
		final BingTokenBean other = (BingTokenBean) obj;
		if (accessToken == null) {
			if (other.accessToken != null)
				return false;
		} else if (!accessToken.equals(other.accessToken))
			return false;
		if (apiKey == null) {
			if (other.apiKey != null)
				return false;
		} else if (!apiKey.equals(other.apiKey))
			return false;
		if (tokenExpiration != other.tokenExpiration)
			return false;
		return true;
	}

	public String getApiKey() {
		return apiKey;
	}
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public long getTokenExpiration() {
		return tokenExpiration;
	}
	public void setTokenExpiration(long tokenExpiration) {
		this.tokenExpiration = tokenExpiration;
	}
}
