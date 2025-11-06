package com.qcmz.cmc.ws.provide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.process.TransLibProcess;
import com.qcmz.cmc.ws.provide.vo.TransLangResp;
import com.qcmz.cmc.ws.provide.vo.TransLibAddReq;
import com.qcmz.cmc.ws.provide.vo.TransLibDownloadResp;
import com.qcmz.cmc.ws.provide.vo.TransLibQueryReq;
import com.qcmz.cmc.ws.provide.vo.TransLibQueryResp;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.framework.ws.BaseInterface;
import com.qcmz.framework.ws.vo.Request;
import com.qcmz.framework.ws.vo.Response;
import com.qcmz.srm.client.util.SrmClient;

/**
 * 类说明：译库接口实现
 * 修改历史：
 */
@Component
public class TransLibInterface extends BaseInterface {
	@Autowired
	private TransLibProcess transLibProcess;
	
	/**
	 * 分页获取译库
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public TransLibQueryResp findTransLib4Web(TransLibQueryReq req, String interfaceType, String reqIp){
		TransLibQueryResp resp = new TransLibQueryResp();
		try {
			//身份验证
//			SrmClient.validAccount(req, resp, className);
			
			//数据校验			
			
			//处理
			if(resp.successed()){
				resp.setResult(transLibProcess.findTransLib4Web(req.getBean().getLastId(), req.getBean().getPageSize()));
			}
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口失败：", e);
		}
		
		return resp;
	}	
	
	/**
	 * 获取译库下载信息
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public TransLibDownloadResp getTransLibDownload(Request req, String interfaceType, String reqIp){
		TransLibDownloadResp resp = new TransLibDownloadResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className);
			
			//数据验证
			
			//获取数据
			if(resp.successed()){
				resp.setResult(transLibProcess.getDownload());
			}
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		
		return resp;
	}
	
	
	/**
	 * 添加译库
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public Response addTransLib(TransLibAddReq req, String interfaceType, String reqIp){
		TransLangResp resp = new TransLangResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className);
			
			//数据校验
			if(resp.successed()){
				if(StringUtil.isBlankOrNull(req.getBean().getLibType())){
					resp.errorParam("译库类型为空");
				}else if(StringUtil.isBlankOrNull(req.getBean().getFrom())){
					resp.errorParam("原文语言为空");
				}else if(StringUtil.isBlankOrNull(req.getBean().getSrc())){
					resp.errorParam("原文为空");
				}else if(StringUtil.isBlankOrNull(req.getBean().getTo())){
					resp.errorParam("译文语言为空");
				}else if(StringUtil.isBlankOrNull(req.getBean().getDst())){
					resp.errorParam("译文为空");
				}else if(req.getBean().getTwoway()==null){
					resp.errorParam("双向互译为空");
				}
			}
			
			//处理
			if(resp.successed()){
				transLibProcess.saveTransLib(req.getBean());
			}
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		
		return resp;
	}
}
