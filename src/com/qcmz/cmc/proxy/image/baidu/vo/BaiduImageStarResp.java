package com.qcmz.cmc.proxy.image.baidu.vo;

import java.util.ArrayList;
import java.util.List;

public class BaiduImageStarResp extends BaiduImageResp{
	/**
	 * 错误提示码，失败才返回，成功不返回
	 */
	private String error_code;
	/**
	 * 错误提示信息，失败才返回，成功不返回
	 */
	private String error_msg;
	/**
	 * 审核结果，可取值描述：合规、不合规、疑似、审核失败
	 */
	private String conclusion;
	/**
	 * 审核结果类型，1：合规，2：不合规，3：疑似，4：审核失败
	 */
	private Integer conclusionType;
	
	private List<BaiduImageStarBean> data = new ArrayList<BaiduImageStarBean>();

	public String getError_code() {
		return error_code;
	}

	public void setError_code(String error_code) {
		this.error_code = error_code;
	}

	public String getError_msg() {
		return error_msg;
	}

	public void setError_msg(String error_msg) {
		this.error_msg = error_msg;
	}

	public String getConclusion() {
		return conclusion;
	}

	public void setConclusion(String conclusion) {
		this.conclusion = conclusion;
	}

	public Integer getConclusionType() {
		return conclusionType;
	}

	public void setConclusionType(Integer conclusionType) {
		this.conclusionType = conclusionType;
	}

	public List<BaiduImageStarBean> getData() {
		return data;
	}

	public void setData(List<BaiduImageStarBean> data) {
		this.data = data;
	}
}
