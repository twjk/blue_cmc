package com.qcmz.cmc.ws.provide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.process.LocalTaskProcess;
import com.qcmz.cmc.util.VersionUtil;
import com.qcmz.cmc.ws.provide.vo.LocalTaskGetReq;
import com.qcmz.cmc.ws.provide.vo.LocalTaskGetResp;
import com.qcmz.cmc.ws.provide.vo.LocalTaskQueryReq;
import com.qcmz.cmc.ws.provide.vo.LocalTaskQueryResp;
import com.qcmz.cmc.ws.provide.vo.LocalTaskSubCancelReq;
import com.qcmz.cmc.ws.provide.vo.LocalTaskSubReq;
import com.qcmz.cmc.ws.provide.vo.LocalTaskUserSubGetReq;
import com.qcmz.cmc.ws.provide.vo.LocalTaskUserSubGetResp;
import com.qcmz.cmc.ws.provide.vo.LocalTaskUserSubQueryReq;
import com.qcmz.cmc.ws.provide.vo.UserCrowdTaskApplyResp;
import com.qcmz.framework.constant.ExceptionConstants;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.ws.BaseInterface;
import com.qcmz.framework.ws.vo.Response;
import com.qcmz.srm.client.util.SrmClient;

/**
 * 同城任务相关接口
 */
@Component
public class LocalTaskInterface extends BaseInterface {
	@Autowired
	private LocalTaskProcess localTaskProcess;
	
	/**
	 * 分页获取同城任务列表
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public LocalTaskQueryResp findTask(LocalTaskQueryReq req, String pageSize, String interfaceType, String reqIp){
		LocalTaskQueryResp resp = new LocalTaskQueryResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className);
			
			//数据校验
//			if(resp.successed()){		
//			}
			
			//处理
			if(resp.successed()){
				String serviceType = SrmClient.getServiceType(req.getAuthAccount());
				String platform = SrmClient.getPlatform(req.getAuthAccount());
				boolean encrypt = VersionUtil.isEncryptJob(serviceType, platform, req.getAppVer());
				
				PageBean pageBean = new PageBean("1", pageSize);
				resp.setResult(localTaskProcess.findTask(req.getBean(), pageBean.getPageSize(), encrypt));
			}
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		return resp;
	}
	
	/**
	 * 分页获取同城任务列表
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public LocalTaskQueryResp findSubTask(LocalTaskUserSubQueryReq req, String pageSize, String interfaceType, String reqIp){
		LocalTaskQueryResp resp = new LocalTaskQueryResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getBean().getUid());
			
			//数据校验
			if(resp.successed()){
				if(req.getBean().getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}
			}
			
			//处理
			if(resp.successed()){
				PageBean pageBean = new PageBean("1", pageSize);
				resp.setResult(localTaskProcess.findSubTask(req.getBean(), pageBean.getPageSize()));
			}
		}catch (Exception e) {
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
	public LocalTaskGetResp getTask(LocalTaskGetReq req, String interfaceType, String reqIp){
		LocalTaskGetResp resp = new LocalTaskGetResp();
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
				String serviceType = SrmClient.getServiceType(req.getAuthAccount());
				String platform = SrmClient.getPlatform(req.getAuthAccount());
				boolean encrypt = VersionUtil.isEncryptJob(serviceType, platform, req.getAppVer());
				
				resp.setResult(localTaskProcess.getTask(req.getTaskId(), encrypt));
			}
		}catch (DataException e) {
			resp.errorData(e.getMessage());
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		return resp;
	}
	
	/**
	 * 订阅
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public Response sub(LocalTaskSubReq req, String interfaceType, String reqIp){
		UserCrowdTaskApplyResp resp = new UserCrowdTaskApplyResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getBean().getUid());
			
			//数据校验
			if(resp.successed()){
				if(req.getBean().getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}
			}
			
			//处理
			if(resp.successed()){
				localTaskProcess.sub(req.getBean());
			}
		}catch (DataException e) {
			resp.errorData(e.getMessage());
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		return resp;
	}
	
	/**
	 * 取消订阅
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public Response cancelSub(LocalTaskSubCancelReq req, String interfaceType, String reqIp){
		Response resp = new Response();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getUid());
			
			//数据校验
			if(resp.successed()){
				if(req.getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}
			}
			
			//处理
			if(resp.successed()){
				localTaskProcess.cancelSub(req.getUid());
			}
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		return resp;
	}
	
	/**
	 * 获取用户订阅信息
	 * @param req
	 * @param page
	 * @param interfaceType
	 * @param reqIp
	 * 修改历史：
	 */
	public LocalTaskUserSubGetResp getSub(LocalTaskUserSubGetReq req, String interfaceType, String reqIp){
		LocalTaskUserSubGetResp resp = new LocalTaskUserSubGetResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getUid());
			
			//数据校验
			if(resp.successed()){
				if(req.getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}
			}
			
			//处理
			if(resp.successed()){
				resp.setResult(localTaskProcess.getUserSub(req.getUid()));
			}
		}catch (DataException e) {
			resp.errorData(e.getMessage());
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		return resp;
	}
}
