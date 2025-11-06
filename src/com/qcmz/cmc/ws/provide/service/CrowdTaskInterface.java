package com.qcmz.cmc.ws.provide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.process.CrowdTaskProcess;
import com.qcmz.cmc.ws.provide.vo.CrowdTaskCompletionQueryReq;
import com.qcmz.cmc.ws.provide.vo.CrowdTaskCompletionQueryResp;
import com.qcmz.cmc.ws.provide.vo.CrowdTaskGetReq;
import com.qcmz.cmc.ws.provide.vo.CrowdTaskGetResp;
import com.qcmz.cmc.ws.provide.vo.CrowdTaskQueryReq;
import com.qcmz.cmc.ws.provide.vo.CrowdTaskQueryResp;
import com.qcmz.framework.ws.BaseInterface;
import com.qcmz.srm.client.util.SrmClient;

@Component
public class CrowdTaskInterface extends BaseInterface {
	@Autowired
	private CrowdTaskProcess crowdTaskProcess;
	
	/**
	 * 获取有效任务列表
	 * @param req
	 * @param page
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public CrowdTaskQueryResp findTask(CrowdTaskQueryReq req, String interfaceType, String reqIp){
		CrowdTaskQueryResp resp = new CrowdTaskQueryResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className);
			
			//数据校验			
			
			//处理
			if(resp.successed()){
				String platform = SrmClient.getPlatform(req.getAuthAccount());
				String serviceType = SrmClient.getServiceType(req.getAuthAccount());
				resp.setResult(crowdTaskProcess.findTask(req.getUid(), serviceType, platform, req.getAppVer()));
			}
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		return resp;
	}
	
	/**
	 * 获取任务详情
	 * @param req
	 * @param page
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public CrowdTaskGetResp getTask(CrowdTaskGetReq req, String interfaceType, String reqIp){
		CrowdTaskGetResp resp = new CrowdTaskGetResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className);
			
			//数据校验
			if(resp.successed()){
				if(req.getTaskId()==null){
					resp.errorParam("任务编号为空");
				}
			}
			
			//处理
			if(resp.successed()){
				resp.setResult(crowdTaskProcess.getTask(req.getTaskId(), req.getUid()));
			}
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		return resp;
	}	
	
	/**
	 * 获取任务完成明细
	 * @param req
	 * @param page
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public CrowdTaskCompletionQueryResp findCompletion(CrowdTaskCompletionQueryReq req, String interfaceType, String reqIp){
		CrowdTaskCompletionQueryResp resp = new CrowdTaskCompletionQueryResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className);
			
			//数据校验
			if(resp.successed()){
				if(req.getTaskId()==null){
					resp.errorParam("任务编号为空");
				}
			}
			
			//处理
			if(resp.successed()){
				resp.setResult(crowdTaskProcess.findCompletion(req.getTaskId()));
			}
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		return resp;
	}
}
