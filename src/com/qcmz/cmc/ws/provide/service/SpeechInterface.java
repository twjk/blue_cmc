package com.qcmz.cmc.ws.provide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.process.SpeechProcess;
import com.qcmz.cmc.ws.provide.vo.SpeechAsrReq;
import com.qcmz.cmc.ws.provide.vo.SpeechAsrResp;
import com.qcmz.cmc.ws.provide.vo.SpeechTtsReq;
import com.qcmz.cmc.ws.provide.vo.SpeechTtsResp;
import com.qcmz.framework.exception.ParamException;
import com.qcmz.framework.exception.ProxyException;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.framework.ws.BaseInterface;
import com.qcmz.srm.client.util.SrmClient;

@Component
public class SpeechInterface extends BaseInterface {
	@Autowired
	private SpeechProcess speechProcess;
	
	/**
	 * 语音识别
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public SpeechAsrResp asr(SpeechAsrReq req, String interfaceType, String reqIp){
		SpeechAsrResp resp = new SpeechAsrResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className);
			
			//数据校验
			if(resp.successed()){
				if(StringUtil.isBlankOrNull(req.getBean().getLangCode())){
					resp.errorParam("语言代码为空");
				}else if(req.getBean().getFile()==null){
					resp.errorParam("语音文件为空");
				}
			}
			
			//处理
			if(resp.successed()){
				resp.setResult(speechProcess.asr(req.getBean()));
			}
		} catch (ParamException e) {
			resp.errorParam(e.getMessage());
		} catch (ProxyException e) {
			resp.error(e.getMessage());
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		
		return resp;
	}
	
	/**
	 * 语音合成
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public SpeechTtsResp tts(SpeechTtsReq req, String interfaceType, String reqIp){
		SpeechTtsResp resp = new SpeechTtsResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className);
			
			//数据校验
			if(resp.successed()){
				if(StringUtil.isBlankOrNull(req.getBean().getLangCode())){
					resp.errorParam("语言代码为空");
				}else if(StringUtil.isBlankOrNull(req.getBean().getText())){
					resp.errorParam("合成文本为空");
				}
			}
			
			//处理
			if(resp.successed()){
				resp.setResult(speechProcess.tts(req.getBean()));
			}
		} catch (ParamException e) {
			resp.errorParam(e.getMessage());
		} catch (ProxyException e) {
			resp.error(e.getMessage());
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		
		return resp;
	}
}
