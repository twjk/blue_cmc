package com.qcmz.cmc.vo;

/**
 * 类说明：对该类的主要功能进行说明
 * @author 李炳煜
 * @version 1.0
 * 创建日期：Jun 4, 2014 2:56:53 PM
 * 修改历史：
 */
public class ApiKeyBean{
	private Long keyid;
	private String apitype;
	private String apikey;
	private Integer status;

	public ApiKeyBean() {
		super();
	}
	
	public ApiKeyBean(Long keyid, String apitype, String apikey, Integer status) {
		super();
		this.keyid = keyid;
		this.apitype = apitype;
		this.apikey = apikey;
		this.status = status;
	}

	public Long getKeyid() {
		return keyid;
	}
	public void setKeyid(Long keyid) {
		this.keyid = keyid;
	}
	public String getApitype() {
		return apitype;
	}
	public void setApitype(String apitype) {
		this.apitype = apitype;
	}
	public String getApikey() {
		return apikey;
	}
	public void setApikey(String apikey) {
		this.apikey = apikey;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
