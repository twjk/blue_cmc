package com.qcmz.cmc.ws.provide.webservice;

import com.qcmz.cmc.ws.provide.vo.ThemeDownloadReq;
import com.qcmz.cmc.ws.provide.vo.ThemeDownloadResp;
import com.qcmz.cmc.ws.provide.vo.ThemeQueryReq;
import com.qcmz.cmc.ws.provide.vo.ThemeReq;
import com.qcmz.cmc.ws.provide.vo.ThemeResp;
import com.qcmz.cmc.ws.provide.vo.ThemeSentenceQueryReq;
import com.qcmz.cmc.ws.provide.vo.ThemeSentenceQueryResp;
import com.qcmz.framework.ws.vo.PageBeanResponse;

/**
 * 类说明：对该类的主要功能进行说明
 * @author 李炳煜
 * 修改历史：
 */
public interface IThemeWS {
	/**
	 * 分页获取主题列表
	 * @param req
	 * @param page
	 * @param pageSize
	 * @return
	 * @author 李炳煜
	 * 修改历史：
	 */
	public PageBeanResponse findTheme(ThemeQueryReq req, String page, String pageSize);
	/**
	 * 获取主题详细信息
	 * @param req
	 * @return
	 * @author 李炳煜
	 * 修改历史：
	 */
	public ThemeResp getTheme(ThemeReq req);
	/**
	 * 分页获取主题下明细列表
	 * @param req
	 * @param page
	 * @param pageSize
	 * @return
	 * @author 李炳煜
	 * 修改历史：
	 */
	public ThemeSentenceQueryResp findSentence(ThemeSentenceQueryReq req);
	/**
	 * 获取主题离线包信息
	 * @param req
	 * @return
	 * @author 李炳煜
	 * 修改历史：
	 */
	public ThemeDownloadResp getDownload(ThemeDownloadReq req);
}
