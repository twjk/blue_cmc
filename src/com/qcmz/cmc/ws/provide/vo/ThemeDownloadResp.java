package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Response;

/**
 * 类说明：对该类的主要功能进行说明
 * @author 李炳煜
 * 修改历史：
 */
public class ThemeDownloadResp extends Response {
	/**
	 * 离线包信息
	 */
	private ThemeDownloadBean download;

	public ThemeDownloadBean getDownload() {
		return download;
	}

	public void setDownload(ThemeDownloadBean download) {
		this.download = download;
	}
}
