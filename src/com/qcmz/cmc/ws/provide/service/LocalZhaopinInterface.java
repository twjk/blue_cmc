package com.qcmz.cmc.ws.provide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.process.LocalTaskZhaopinProcess;
import com.qcmz.cmc.ws.provide.vo.LocalZhaopinJobDelReq;
import com.qcmz.cmc.ws.provide.vo.LocalZhaopinJobPublishReq;
import com.qcmz.cmc.ws.provide.vo.LocalZhaopinJobReEditReq;
import com.qcmz.cmc.ws.provide.vo.LocalZhaopinJobTakeDownReq;
import com.qcmz.cmc.ws.provide.vo.LocalZhaopinJobAddReq;
import com.qcmz.cmc.ws.provide.vo.LocalZhaopinJobAddResp;
import com.qcmz.cmc.ws.provide.vo.LocalZhaopinJobAddressUpdateReq;
import com.qcmz.cmc.ws.provide.vo.LocalZhaopinJobBasicUpdateReq;
import com.qcmz.cmc.ws.provide.vo.LocalZhaopinJobDutyUpdateReq;
import com.qcmz.cmc.ws.provide.vo.LocalZhaopinJobEduUpdateReq;
import com.qcmz.cmc.ws.provide.vo.LocalZhaopinJobExpUpdateReq;
import com.qcmz.cmc.ws.provide.vo.LocalZhaopinJobGetReq;
import com.qcmz.cmc.ws.provide.vo.LocalZhaopinJobGetResp;
import com.qcmz.cmc.ws.provide.vo.LocalZhaopinJobQueryReq;
import com.qcmz.cmc.ws.provide.vo.LocalZhaopinJobQueryResp;
import com.qcmz.cmc.ws.provide.vo.LocalZhaopinJobRequireUpdateReq;
import com.qcmz.framework.constant.ExceptionConstants;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.framework.ws.BaseInterface;
import com.qcmz.framework.ws.vo.Response;
import com.qcmz.srm.client.util.SrmClient;

/**
 * 招聘相关接口
 */
@Component
public class LocalZhaopinInterface extends BaseInterface {
	@Autowired
	private LocalTaskZhaopinProcess localZhaopinProcess;
	
	/**
	 * 获取岗位列表
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public LocalZhaopinJobQueryResp findJob(LocalZhaopinJobQueryReq req, String interfaceType, String reqIp){
		LocalZhaopinJobQueryResp resp = new LocalZhaopinJobQueryResp();
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
				resp.setResult(localZhaopinProcess.findJob(req.getBean()));
			}
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		return resp;
	}
	
	/**
	 * 获取岗位详情
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public LocalZhaopinJobGetResp getJobDetail(LocalZhaopinJobGetReq req, String interfaceType, String reqIp){
		LocalZhaopinJobGetResp resp = new LocalZhaopinJobGetResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getUid());
			
			//数据校验
			if(resp.successed()){
				if(req.getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}else if(req.getTaskId()==null){
					resp.errorParam(ExceptionConstants.MSG_DATAID_EMPTY);
				}
			}
			
			//处理
			if(resp.successed()){
				resp.setResult(localZhaopinProcess.getJob(req.getTaskId(), req.getUid()));
			}
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		return resp;
	}
	
	/**
	 * 添加新岗
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public LocalZhaopinJobAddResp addJob(LocalZhaopinJobAddReq req, String interfaceType, String reqIp){
		LocalZhaopinJobAddResp resp = new LocalZhaopinJobAddResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getBean().getUid());
			
			//数据校验
			if(resp.successed()){
				if(req.getBean().getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}else if(StringUtil.isBlankOrNull(req.getBean().getTitle())){
					resp.errorData("岗位为空");
				}
			}
			
			//处理
			if(resp.successed()){
				resp.setResult(localZhaopinProcess.addJob(req.getBean()));
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
	 * 更新基本信息
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public Response updateJobBasic(LocalZhaopinJobBasicUpdateReq req, String interfaceType, String reqIp){
		Response resp = new Response();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getBean().getUid());
			
			//数据校验
			if(resp.successed()){
				if(req.getBean().getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}else if(req.getBean().getTaskId()==null){
					resp.errorParam(ExceptionConstants.MSG_DATAID_EMPTY);
				}else if(StringUtil.isBlankOrNull(req.getBean().getTitle())){
					resp.errorParam("岗位名称为空");
				}else if(req.getBean().getRewardType()==null){
					resp.errorParam("报酬类型为空");
				}else if(StringUtil.isBlankOrNull(req.getBean().getReward())){
					resp.errorParam("报酬描述为空");
				}
			}
			
			//处理
			if(resp.successed()){
				localZhaopinProcess.updateJobBasic(req.getBean());
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
	 * 更新工作地址
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public Response updateJobAddress(LocalZhaopinJobAddressUpdateReq req, String interfaceType, String reqIp){
		Response resp = new Response();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getBean().getUid());
			
			//数据校验
			if(resp.successed()){
				if(req.getBean().getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}else if(req.getBean().getTaskId()==null){
					resp.errorParam(ExceptionConstants.MSG_DATAID_EMPTY);
				}
			}
			
			//处理
			if(resp.successed()){
				localZhaopinProcess.updateJobAddress(req.getBean());
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
	 * 更新学历要求
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public Response updateJobEdu(LocalZhaopinJobEduUpdateReq req, String interfaceType, String reqIp){
		Response resp = new Response();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getBean().getUid());
			
			//数据校验
			if(resp.successed()){
				if(req.getBean().getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}else if(req.getBean().getTaskId()==null){
					resp.errorParam(ExceptionConstants.MSG_DATAID_EMPTY);
				}
			}
			
			//处理
			if(resp.successed()){
				localZhaopinProcess.updateJobEdu(req.getBean());
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
	 * 更新工作经验要求
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public Response updateJobExp(LocalZhaopinJobExpUpdateReq req, String interfaceType, String reqIp){
		Response resp = new Response();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getBean().getUid());
			
			//数据校验
			if(resp.successed()){
				if(req.getBean().getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}else if(req.getBean().getTaskId()==null){
					resp.errorParam(ExceptionConstants.MSG_DATAID_EMPTY);
				}
			}
			
			//处理
			if(resp.successed()){
				localZhaopinProcess.updateJobExp(req.getBean());
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
	 * 更新工作职责
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public Response updateJobDuty(LocalZhaopinJobDutyUpdateReq req, String interfaceType, String reqIp){
		Response resp = new Response();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getBean().getUid());
			
			//数据校验
			if(resp.successed()){
				if(req.getBean().getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}else if(req.getBean().getTaskId()==null){
					resp.errorParam(ExceptionConstants.MSG_DATAID_EMPTY);
				}
			}
			
			//处理
			if(resp.successed()){
				localZhaopinProcess.updateJobDuty(req.getBean());
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
	 * 更新任职要求
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public Response updateJobRequire(LocalZhaopinJobRequireUpdateReq req, String interfaceType, String reqIp){
		Response resp = new Response();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getBean().getUid());
			
			//数据校验
			if(resp.successed()){
				if(req.getBean().getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}else if(req.getBean().getTaskId()==null){
					resp.errorParam(ExceptionConstants.MSG_DATAID_EMPTY);
				}
			}
			
			//处理
			if(resp.successed()){
				localZhaopinProcess.updateJobRequire(req.getBean());
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
	 * 用户重新编辑岗位
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public Response reEditJob(LocalZhaopinJobReEditReq req, String interfaceType, String reqIp){
		Response resp = new Response();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getUid());
			
			//数据校验
			if(resp.successed()){
				if(req.getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}else if(req.getTaskId()==null){
					resp.errorParam(ExceptionConstants.MSG_DATAID_EMPTY);
				}
			}
			
			//处理
			if(resp.successed()){
				localZhaopinProcess.reEditJob(req.getTaskId(), req.getUid());
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
	 * 用户发布岗位
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public Response publishJob(LocalZhaopinJobPublishReq req, String interfaceType, String reqIp){
		Response resp = new Response();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getUid());
			
			//数据校验
			if(resp.successed()){
				if(req.getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}else if(req.getTaskId()==null){
					resp.errorParam(ExceptionConstants.MSG_DATAID_EMPTY);
				}
			}
			
			//处理
			if(resp.successed()){
				localZhaopinProcess.publishJob(req.getTaskId(), req.getUid());
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
	 * 用户下降岗位
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public Response takeDownJob(LocalZhaopinJobTakeDownReq req, String interfaceType, String reqIp){
		Response resp = new Response();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getUid());
			
			//数据校验
			if(resp.successed()){
				if(req.getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}else if(req.getTaskId()==null){
					resp.errorParam(ExceptionConstants.MSG_DATAID_EMPTY);
				}
			}
			
			//处理
			if(resp.successed()){
				localZhaopinProcess.takeDownJob(req.getTaskId(), req.getUid());
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
	 * 删除岗位
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public Response delJob(LocalZhaopinJobDelReq req, String interfaceType, String reqIp){
		Response resp = new Response();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getUid());
			
			//数据校验
			if(resp.successed()){
				if(req.getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}else if(req.getTaskId()==null){
					resp.errorParam(ExceptionConstants.MSG_DATAID_EMPTY);
				}
			}
			
			//处理
			if(resp.successed()){
				localZhaopinProcess.delJob(req.getTaskId(), req.getUid());
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
