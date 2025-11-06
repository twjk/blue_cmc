package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

public class CrowdSubjectBean extends CrowdSubjectBaseBean{
	/**
	 * 选项
	 */
	private List<CrowdOptionBean> options = new ArrayList<CrowdOptionBean>();
	
	public List<CrowdOptionBean> getOptions() {
		return options;
	}
	public void setOptions(List<CrowdOptionBean> options) {
		this.options = options;
	}
}
