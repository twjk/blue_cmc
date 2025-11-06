package com.qcmz.cmc.ws.provide.webservice.impl;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.bdc.ws.provide.locator.LogInterfaceWS;
import com.qcmz.cmc.service.IThemeSentenceService;
import com.qcmz.cmc.service.IThemeService;
import com.qcmz.cmc.ws.provide.vo.ThemeDownloadReq;
import com.qcmz.cmc.ws.provide.vo.ThemeDownloadResp;
import com.qcmz.cmc.ws.provide.vo.ThemeQueryReq;
import com.qcmz.cmc.ws.provide.vo.ThemeReq;
import com.qcmz.cmc.ws.provide.vo.ThemeResp;
import com.qcmz.cmc.ws.provide.vo.ThemeSentenceQueryReq;
import com.qcmz.cmc.ws.provide.vo.ThemeSentenceQueryResp;
import com.qcmz.cmc.ws.provide.webservice.IThemeWS;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.framework.ws.vo.PageBeanResponse;
import com.qcmz.srm.client.util.AuthUtil;

/**
 * 类说明：对该类的主要功能进行说明
 * @author 李炳煜
 * 修改历史：
 */
@Service
public class ThemeWSImpl implements IThemeWS {
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private IThemeService themeService;
	@Autowired
	private IThemeSentenceService themeSentenceService;
	
	private String className = this.getClass().getSimpleName();
	
	/**
	 * 分页获取主题列表
	 * @param req
	 * @param page
	 * @param pageSize
	 * @return
	 * 修改历史：
	 */
	public PageBeanResponse findTheme(ThemeQueryReq req, String page, String pageSize){
		PageBeanResponse resp = new PageBeanResponse();
		Date reqTime = new Date();
		Exception exception = null;
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		try {
			//身份验证
			AuthUtil.validAccount(req, resp, className);
			
			//数据验证
			
			//查询
			if(resp.successed()){
				PageBean pageBean = new PageBean(page, pageSize, null);
				themeService.findThemeInfo(pageBean);
				resp.setPageBean(pageBean);
			}
		} catch (Exception e) {
			resp.error();
			exception = e;
			logger.error("访问接口["+methodName+"]失败", e);
		}
		
		//保存接口日志
		if(!resp.successed()){
			LogInterfaceWS.logInterfaceThread(className, methodName, reqTime, req, resp, exception);
		}
		
		return resp;
	}
	
	/**
	 * 获取主题信息
	 * @param req
	 * @return
	 * 修改历史：
	 */
	public ThemeResp getTheme(ThemeReq req){
		ThemeResp resp = new ThemeResp();
		Date reqTime = new Date();
		Exception exception = null;
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		try {
			//身份验证
			AuthUtil.validAccount(req, resp, className);
			
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
			exception = e;
			logger.error("访问接口["+methodName+"]失败", e);
		}
		
		//保存接口日志
		if(!resp.successed()){
			LogInterfaceWS.logInterfaceThread(className, methodName, reqTime, req, resp, exception);
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
	public ThemeSentenceQueryResp findSentence(ThemeSentenceQueryReq req){
		ThemeSentenceQueryResp resp = new ThemeSentenceQueryResp();
		Date reqTime = new Date();
		Exception exception = null;
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		try {
			//身份验证
			AuthUtil.validAccount(req, resp, className);
			
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
			exception = e;
			logger.error("访问接口["+methodName+"]失败", e);
		}
		
		//保存接口日志
		if(!resp.successed()){
			LogInterfaceWS.logInterfaceThread(className, methodName, reqTime, req, resp, exception);
		}
		
		return resp;
	}
	
	/**
	 * 获取主题离线包信息
	 * @param req
	 * @return
	 * 修改历史：
	 */
	public ThemeDownloadResp getDownload(ThemeDownloadReq req){
		ThemeDownloadResp resp = new ThemeDownloadResp();
		Date reqTime = new Date();
		Exception exception = null;
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		try {
			//身份验证
			AuthUtil.validAccount(req, resp, className);
			
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
			exception = e;
			logger.error("访问接口["+methodName+"]失败", e);
		}
		
		//保存接口日志
		if(!resp.successed()){
			LogInterfaceWS.logInterfaceThread(className, methodName, reqTime, req, resp, exception);
		}
		
		return resp;
	}
}
