package com.qcmz.cmc.proxy.spider.weixin.vo;

public class WeixinResp {
	private BaseResp baseResp;

	@Override
	public String toString() {
		return new StringBuilder("[").append(baseResp.getRet()).append("]").append(baseResp.getErrMsg()).toString();
	}
	
	public BaseResp getBaseResp() {
		return baseResp;
	}

	public void setBaseResp(BaseResp baseResp) {
		this.baseResp = baseResp;
	}
	
	public boolean success(){
		return baseResp.getRet()==0;
	}
	public boolean freqControl(){
		//[200013]freq control
		return baseResp.getRet()==200013;
	}
}
