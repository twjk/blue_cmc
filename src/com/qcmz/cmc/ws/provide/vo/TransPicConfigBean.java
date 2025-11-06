package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

public class TransPicConfigBean {
	private List<TransPicLangBean> langs = new ArrayList<TransPicLangBean>();

	public List<TransPicLangBean> getLangs() {
		return langs;
	}

	public void setLangs(List<TransPicLangBean> langs) {
		this.langs = langs;
	}
	
}
