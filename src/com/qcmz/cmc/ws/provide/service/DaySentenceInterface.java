package com.qcmz.cmc.ws.provide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.process.DaySentenceProcess;
import com.qcmz.cmc.service.IDaySentenceService;
import com.qcmz.cmc.ws.provide.vo.DaySentenceLangResp;
import com.qcmz.cmc.ws.provide.vo.DaySentenceQueryReq;
import com.qcmz.cmc.ws.provide.vo.DaySentenceQueryResp;
import com.qcmz.cmc.ws.provide.vo.DaySentenceReq;
import com.qcmz.cmc.ws.provide.vo.DaySentenceResp;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.ws.BaseInterface;
import com.qcmz.framework.ws.vo.PageBeanResponse;
import com.qcmz.framework.ws.vo.Request;
import com.qcmz.srm.client.util.SrmClient;

/**
 * 类说明：每日一句接口实现
 * 修改历史：
 */
@Component
public class DaySentenceInterface extends BaseInterface {
	@Autowired
	private IDaySentenceService daySentenceService;
	@Autowired
	private DaySentenceProcess daySentenceProcess;
	
	/**
	 * 获取每日一句语言列表
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public DaySentenceLangResp findLang(Request req, String interfaceType, String reqIp){
		DaySentenceLangResp resp = new DaySentenceLangResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className);
			
			//数据校验
			
			//处理
			if(resp.successed()){
				resp.getResult().addAll(daySentenceProcess.findLang(req.getLanguage()));
			}
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口[findLang]失败", e);
		}
		
		return resp;
	}
	
	/**
	 * 获取所有每日一句信息
	 * @param req
	 * @param page
	 * @param pageSize
	 * @return
	 * 修改历史：
	 */
	public DaySentenceQueryResp findAllSentence(Request req, String interfaceType, String reqIp){
		DaySentenceQueryResp resp = new DaySentenceQueryResp();
		try {
			//身份验证
//			SrmClient.validAccount(req, resp, className);
			
			//数据验证
			
			//查询
			if(resp.successed()){
				resp.setResult(daySentenceProcess.findAllSentence());
			}
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口[findAllSentence]失败", e);
		}
		
		return resp;
	}
	
	/**
	 * 分页获取有效的每日一句信息
	 * @param req
	 * @param page
	 * @param pageSize
	 * @return
	 * 修改历史：
	 */
	public PageBeanResponse findSentence(DaySentenceQueryReq req, String page, String pageSize, String interfaceType, String reqIp){
		PageBeanResponse resp = new PageBeanResponse();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className);
			
			//数据验证
			
			//查询
			if(resp.successed()){
				PageBean pageBean = new PageBean(page, pageSize);
				daySentenceProcess.findSentence(req.getFrom(), pageBean);
				resp.setPageBean(pageBean);
			}
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口[findSentence]失败", e);
		}
		
		return resp;
	}
	
	/**
	 * 获取指定句子信息
	 * @param req
	 * @return
	 * 修改历史：
	 */
	public DaySentenceResp getSentence(DaySentenceReq req, String interfaceType, String reqIp){
		DaySentenceResp resp = new DaySentenceResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className);
			
			//数据验证.
			if(resp.successed()){
				if(req.getSentenceId()==null){
					resp.errorParam("语句编号为空");
				}
			}
			
			//查询
			if(resp.successed()){
				resp.setBean(daySentenceService.getSentenceInfo(req.getSentenceId()));
			}
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口[getSentence]失败", e);
		}
		
		return resp;
	}
	
	/**
	 * 获取下一条句子信息
	 * @param req
	 * @return
	 * 修改历史：
	 */
	public DaySentenceResp nextSentence(DaySentenceReq req, String interfaceType, String reqIp){
		DaySentenceResp resp = new DaySentenceResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className);
			
			//数据验证
			
			//查询
			if(resp.successed()){
				resp.setBean(daySentenceService.getNextSentenceInfo(req.getSentenceId(), req.getFrom()));
			}
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口[nextSentence]失败", e);
		}
		
		return resp;
	}
	
	/**
	 * 获取上一条句子信息
	 * @param req
	 * @return
	 * 修改历史：
	 */
	public DaySentenceResp preSentence(DaySentenceReq req, String interfaceType, String reqIp){
		DaySentenceResp resp = new DaySentenceResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className);
			
			//数据验证
			if(resp.successed()){
				if(req.getSentenceId()==null){
					resp.errorParam("当前语句编号为空");
				}
			}
			
			//查询
			if(resp.successed()){
				resp.setBean(daySentenceService.getPreSentenceInfo(req.getSentenceId(), req.getFrom()));
			}
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口[preSentence]失败", e);
		}

		return resp;
	}
}
