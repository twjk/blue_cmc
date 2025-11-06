package com.qcmz.cmc.ws.provide.webservice.impl;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.bdc.ws.provide.locator.LogInterfaceWS;
import com.qcmz.cmc.service.IDaySentenceService;
import com.qcmz.cmc.ws.provide.vo.DaySentenceReq;
import com.qcmz.cmc.ws.provide.vo.DaySentenceResp;
import com.qcmz.cmc.ws.provide.webservice.IDaySentenceWS;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.ws.vo.PageBeanResponse;
import com.qcmz.framework.ws.vo.Request;
import com.qcmz.srm.client.util.AuthUtil;

/**
 * 类说明：对该类的主要功能进行说明
 * @author 李炳煜
 * 修改历史：
 */
@Component
public class DaySentenceWSImpl implements IDaySentenceWS {
	private Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private IDaySentenceService daySentenceService;
	
	private String className = this.getClass().getSimpleName();
	
	/**
	 * 分页获取有效的每日一句信息
	 * @param req
	 * @param page
	 * @param pageSize
	 * @return
	 * 修改历史：
	 */
	public PageBeanResponse findSentence(Request req, String page, String pageSize){
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
				daySentenceService.findSentenceInfo(pageBean);
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
	 * 获取指定句子信息
	 * @param req
	 * @return
	 * 修改历史：
	 */
	public DaySentenceResp getSentence(DaySentenceReq req){
		DaySentenceResp resp = new DaySentenceResp();
		Date reqTime = new Date();
		Exception exception = null;
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		try {
			//身份验证
			AuthUtil.validAccount(req, resp, className);
			
			//数据验证.
			if(resp.successed()){
				if(req.getSentenceId()==null){
					resp.errorParam("编号为空");
				}
			}
			
			//查询
			if(resp.successed()){
				resp.setBean(daySentenceService.getSentenceInfo(req.getSentenceId()));
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
	 * 获取下一条句子信息
	 * @param req
	 * @return
	 * 修改历史：
	 */
	public DaySentenceResp nextSentence(DaySentenceReq req){
		DaySentenceResp resp = new DaySentenceResp();
		Date reqTime = new Date();
		Exception exception = null;
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		try {
			//身份验证
			AuthUtil.validAccount(req, resp, className);
			
			//数据验证
			
			//查询
			if(resp.successed()){
				resp.setBean(daySentenceService.getNextSentenceInfo(req.getSentenceId()));
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
	 * 获取上一条句子信息
	 * @param req
	 * @return
	 * 修改历史：
	 */
	public DaySentenceResp preSentence(DaySentenceReq req){
		DaySentenceResp resp = new DaySentenceResp();
		Date reqTime = new Date();
		Exception exception = null;
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		try {
			//身份验证
			AuthUtil.validAccount(req, resp, className);
			
			//数据验证
			
			//查询
			if(resp.successed()){
				resp.setBean(daySentenceService.getPreSentenceInfo(req.getSentenceId()));
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
}
