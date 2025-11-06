package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class EvalQueryReq extends Request {
	/**
	 * 评价类型
	 */
	private Integer evalType;

	public Integer getEvalType() {
		return evalType;
	}

	public void setEvalType(Integer evalType) {
		this.evalType = evalType;
	}
}
