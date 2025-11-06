package com.qcmz.cmc.ws.provide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.service.IThemeSentenceService;
import com.qcmz.cmc.service.IThemeService;
import com.qcmz.cmc.ws.provide.vo.ThemeDownloadReq;
import com.qcmz.cmc.ws.provide.vo.ThemeDownloadResp;
import com.qcmz.cmc.ws.provide.vo.ThemeQueryReq;
import com.qcmz.cmc.ws.provide.vo.ThemeReq;
import com.qcmz.cmc.ws.provide.vo.ThemeResp;
import com.qcmz.cmc.ws.provide.vo.ThemeSentenceQueryReq;
import com.qcmz.cmc.ws.provide.vo.ThemeSentenceQueryResp;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.framework.ws.BaseInterface;
import com.qcmz.framework.ws.vo.PageBeanResponse;
import com.qcmz.srm.client.util.SrmClient;

/**
 * 类说明：日常用语主题接口实现
 * 修改历史：
 */
@Component
public class ThemeInterface extends BaseInterface {
	@Autowired
	private IThemeService themeService;
	@Autowired
	private IThemeSentenceService themeSentenceService;
	
	/**
	 * 分页获取主题列表
	 * @param req
	 * @param page
	 * @param pageSize
	 * @return
	 * 修改历史：
	 */
	public PageBeanResponse findTheme(ThemeQueryReq req, String page, String pageSize, String interfaceType, String reqIp){
		PageBeanResponse resp = new PageBeanResponse();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className);
			
			//数据验证
			
			//查询
			if(resp.successed()){
				PageBean pageBean = new PageBean(page, pageSize);
				themeService.findThemeInfo(pageBean);
				resp.setPageBean(pageBean);
			}
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		
		return resp;
	}
	
	/**
	 * 获取主题信息
	 * @param req
	 * @return
	 * 修改历史：
	 */
	public ThemeResp getTheme(ThemeReq req, String interfaceType, String reqIp){
		ThemeResp resp = new ThemeResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className);
			
			//数据验证
			if(resp.successed()){
				if(req.getThemeId()==null){
					resp.errorParam("主题编号为空");
				}
			}
			
			//获取数据
			if(resp.successed()){
				resp.setDetail(themeService.getDetailInfo(req.getThemeId()));
			}
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		
		return resp;
	}
	
	/**
	 * 分页获取主题下明细列表
	 * @param req
	 * @param page
	 * @param pageSize
	 * @return
	 * 修改历史：
	 */
	public ThemeSentenceQueryResp findSentence(ThemeSentenceQueryReq req, String interfaceType, String reqIp){
		ThemeSentenceQueryResp resp = new ThemeSentenceQueryResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className);
			
			//数据验证
			if(resp.successed()){
				if(req.getThemeId()==null){
					resp.errorParam("主题编号为空");
				}
			}
			
			//查询
			if(resp.successed()){
				resp.getSentences().addAll(themeSentenceService.findSentenceInfo(req.getThemeId(), req.getCatId()));
			}
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}

		return resp;
	}
	
	/**
	 * 获取主题离线包信息
	 * @param req
	 * @return
	 * 修改历史：
	 */
	public ThemeDownloadResp getDownload(ThemeDownloadReq req, String interfaceType, String reqIp){
		ThemeDownloadResp resp = new ThemeDownloadResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className);
			
			//数据验证
			if(resp.successed()){
				if(req.getThemeId()==null && StringUtil.isBlankOrNull(req.getThemeCode())){
					resp.errorParam("主题编号和代码均为空");
				}
			}
			
			//获取数据
			if(resp.successed()){
				resp.setDownload(themeService.getDownloadInfo(req.getThemeId(), req.getThemeCode()));
			}
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		
		return resp;
	}
}
