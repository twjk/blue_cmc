package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class CapsBean {
	private List<CapAsrBean> asrCaps = new ArrayList<CapAsrBean>();
	private List<CapTtsBean> ttsCaps = new ArrayList<CapTtsBean>();
	private List<CapOcrBean> ocrCap = new ArrayList<CapOcrBean>();
	private List<CapLangDetectBean> langDetectCaps = new ArrayList<CapLangDetectBean>();
	public List<CapAsrBean> getAsrCaps() {
		return asrCaps;
	}
	public void setAsrCaps(List<CapAsrBean> asrCaps) {
		this.asrCaps = asrCaps;
	}
	public List<CapTtsBean> getTtsCaps() {
		return ttsCaps;
	}
	public void setTtsCaps(List<CapTtsBean> ttsCaps) {
		this.ttsCaps = ttsCaps;
	}
	public List<CapOcrBean> getOcrCap() {
		return ocrCap;
	}
	public void setOcrCap(List<CapOcrBean> ocrCap) {
		this.ocrCap = ocrCap;
	}
	public List<CapLangDetectBean> getLangDetectCaps() {
		return langDetectCaps;
	}
	public void setLangDetectCaps(List<CapLangDetectBean> langDetectCaps) {
		this.langDetectCaps = langDetectCaps;
	}
}