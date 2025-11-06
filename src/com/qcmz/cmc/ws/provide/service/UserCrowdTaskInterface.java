package com.qcmz.cmc.ws.provide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.process.UserCrowdTaskProcess;
import com.qcmz.cmc.service.IUserCrowdTaskService;
import com.qcmz.cmc.ws.provide.vo.UserCrowdNewSubjectQueryReq;
import com.qcmz.cmc.ws.provide.vo.UserCrowdNewSubjectQueryResp;
import com.qcmz.cmc.ws.provide.vo.UserCrowdSubjectAnswerReq;
import com.qcmz.cmc.ws.provide.vo.UserCrowdSubjectQueryReq;
import com.qcmz.cmc.ws.provide.vo.UserCrowdSubjectQueryResp;
import com.qcmz.cmc.ws.provide.vo.UserCrowdTaskApplyReq;
import com.qcmz.cmc.ws.provide.vo.UserCrowdTaskApplyResp;
import com.qcmz.cmc.ws.provide.vo.UserCrowdTaskCancelReq;
import com.qcmz.cmc.ws.provide.vo.UserCrowdTaskCountReq;
import com.qcmz.cmc.ws.provide.vo.UserCrowdTaskCountResp;
import com.qcmz.cmc.ws.provide.vo.UserCrowdTaskFinishReq;
import com.qcmz.cmc.ws.provide.vo.UserCrowdTaskGetReq;
import com.qcmz.cmc.ws.provide.vo.UserCrowdTaskGetResp;
import com.qcmz.cmc.ws.provide.vo.UserCrowdTaskQueryReq;
import com.qcmz.cmc.ws.provide.vo.UserCrowdTaskQueryResp;
import com.qcmz.cmc.ws.provide.vo.UserCrowdTaskStartReq;
import com.qcmz.cmc.ws.provide.vo.UserCrowdTaskUploadReq;
import com.qcmz.cmc.ws.provide.vo.UserCrowdTaskUploadResp;
import com.qcmz.framework.constant.ExceptionConstants;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.exception.ParamException;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.framework.ws.BaseInterface;
import com.qcmz.framework.ws.vo.Response;
import com.qcmz.srm.client.util.SrmClient;

/**
 * 用户众包任务相关接口
 */
@Component
public class UserCrowdTaskInterface extends BaseInterface {
	@Autowired
	private IUserCrowdTaskService userCrowdTaskService;
	
	@Autowired
	private UserCrowdTaskProcess userCrowdTaskProcess;
	
	/**
	 * 分页获取用户任务列表
	 * @param req
	 * @param page
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public UserCrowdTaskQueryResp findUserTask(UserCrowdTaskQueryReq req, String pageSize, String interfaceType, String reqIp){
		UserCrowdTaskQueryResp resp = new UserCrowdTaskQueryResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getUid());
			
			//数据校验
			if(req.getUid()==null){
				resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
			}
			
			//处理
			if(resp.successed()){
				PageBean pageBean = new PageBean("1", pageSize);
				resp.setResult(userCrowdTaskService.findUserTask(req.getUid(), pageBean.getPageSize(), req.getMoreBaseId()));
			}
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		return resp;
	}
	
	/**
	 * 获取用户进行中的任务数
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public UserCrowdTaskCountResp getCount(UserCrowdTaskCountReq req, String interfaceType, String reqIp){
		UserCrowdTaskCountResp resp = new UserCrowdTaskCountResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getUid());
			
			//数据校验
			if(req.getUid()==null){
				resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
			}
			
			//处理
			if(resp.successed()){
				resp.setResult(userCrowdTaskProcess.getCount(req.getUid()));
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
	public UserCrowdTaskGetResp getUserTask(UserCrowdTaskGetReq req, String interfaceType, String reqIp){
		UserCrowdTaskGetResp resp = new UserCrowdTaskGetResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className);
			
			//数据校验
			if(resp.successed()){
				if(StringUtil.isBlankOrNull(req.getUtId())){
					resp.errorParam("用户任务编号为空");
				}
			}
			
			//处理
			if(resp.successed()){
				resp.setResult(userCrowdTaskProcess.getUserTask(req.getUtId()));
			}
		}catch (DataException e) {
			resp.errorParam(e.getMessage());
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		return resp;
	}
	
	/**
	 * 获取用户任务新的题目列表
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public UserCrowdNewSubjectQueryResp findNewSubject(UserCrowdNewSubjectQueryReq req, String pageSize, String interfaceType, String reqIp){
		UserCrowdNewSubjectQueryResp resp = new UserCrowdNewSubjectQueryResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getUid());
			
			//数据校验
			if(resp.successed()){
				if(StringUtil.isBlankOrNull(req.getUtId())){
					resp.errorParam("用户任务编号为空");
				}else if(req.getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}
			}
			
			//处理
			if(resp.successed()){
				PageBean pageBean = new PageBean("1", pageSize);
				resp.setResult(userCrowdTaskProcess.findNewSubject(req.getUtId(), req.getUid(), pageBean.getPageSize(), req.getLastSubjectId()));
			}
		}catch (ParamException e) {
			resp.errorParam(e.getMessage());
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		return resp;
	}
	
	/**
	 * 获取任务题目列表
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public UserCrowdSubjectQueryResp findUserSubject(UserCrowdSubjectQueryReq req, String interfaceType, String reqIp){
		UserCrowdSubjectQueryResp resp = new UserCrowdSubjectQueryResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className);
			
			//数据校验
			if(resp.successed()){
				if(req.getUtId()==null){
					resp.errorParam("用户任务编号为空");
				}
			}
			
			//处理
			if(resp.successed()){
				resp.setResult(userCrowdTaskProcess.findUserSubject(req.getUtId()));
			}
		}catch (ParamException e) {
			resp.errorParam(e.getMessage());
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		return resp;
	}
	
	/**
	 * 报名
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public UserCrowdTaskApplyResp applyTask(UserCrowdTaskApplyReq req, String interfaceType, String reqIp){
		UserCrowdTaskApplyResp resp = new UserCrowdTaskApplyResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getBean().getUid());
			
			//数据校验
			if(resp.successed()){
				if(req.getBean().getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}else if(req.getBean().getTaskId()==null){
					resp.errorParam("任务编号为空");
				}
			}
			
			//处理
			if(resp.successed()){
				String platform = SrmClient.getPlatform(req.getAuthAccount());
				String serviceType = SrmClient.getServiceType(req.getAuthAccount());
				resp.setResult(userCrowdTaskProcess.applyTask(req.getBean(), serviceType, platform, req.getAppVer()));
			}
		}catch (ParamException e) {
			resp.errorParam(e.getMessage());
		}catch (DataException e) {
			resp.errorData(e.getMessage());
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		return resp;
	}
	
	/**
	 * 取消任务
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public Response cancelTask(UserCrowdTaskCancelReq req, String interfaceType, String reqIp){
		Response resp = new Response();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getUid());
			
			//数据校验
			if(resp.successed()){
				if(req.getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}else if(StringUtil.isBlankOrNull(req.getUtId())){
					resp.errorParam("用户任务编号为空");
				}
			}
			
			//处理
			if(resp.successed()){
				userCrowdTaskProcess.cancelTask(req.getUid(), req.getUtId(), req.getReason());
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
	 * 开始任务
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public Response startTask(UserCrowdTaskStartReq req, String interfaceType, String reqIp){
		Response resp = new Response();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getUid());
			
			//数据校验
			if(resp.successed()){
				if(req.getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}else if(StringUtil.isBlankOrNull(req.getUtId())){
					resp.errorParam("用户任务编号为空");
				}
			}
			
			//处理
			if(resp.successed()){
				userCrowdTaskProcess.startTask(req.getUid(), req.getUtId());
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
	 * 上传前置任务文件
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public UserCrowdTaskUploadResp uploadPreTask(UserCrowdTaskUploadReq req, String interfaceType, String reqIp){
		UserCrowdTaskUploadResp resp = new UserCrowdTaskUploadResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getBean().getUid());
			
			//数据校验
			if(resp.successed()){
				if(req.getBean().getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}else if(req.getBean().getTaskId()==null){
					resp.errorParam("任务编号为空");
				}else if(req.getBean().getSubjectId()==null){
					resp.errorParam("题目编号为空");
				}else if(req.getBean().getFile()==null){
					resp.errorParam(ExceptionConstants.MSG_FILE_EMPTY);
				}
			}
			
			//处理
			if(resp.successed()){
				resp.setResult(userCrowdTaskProcess.uploadPreTask(req.getBean()));
			}
		}catch (ParamException e) {
			resp.errorParam(e.getMessage());
		}catch (DataException e) {
			resp.errorData(e.getMessage());
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		return resp;
	}
	
	/**
	 * 上传任务文件
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public UserCrowdTaskUploadResp uploadTask(UserCrowdTaskUploadReq req, String interfaceType, String reqIp){
		UserCrowdTaskUploadResp resp = new UserCrowdTaskUploadResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getBean().getUid());
			
			//数据校验
			if(resp.successed()){
				if(req.getBean().getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}else if(StringUtil.isBlankOrNull(req.getBean().getUtId())){
					resp.errorParam("用户任务编号为空");
				}else if(req.getBean().getSubjectId()==null){
					resp.errorParam("题目编号为空");
				}else if(req.getBean().getFile()==null){
					resp.errorParam(ExceptionConstants.MSG_FILE_EMPTY);
				}
			}
			
			//处理
			if(resp.successed()){
				resp.setResult(userCrowdTaskProcess.uploadTask(req.getBean()));
			}
		}catch (ParamException e) {
			resp.errorParam(e.getMessage());
		}catch (DataException e) {
			resp.errorData(e.getMessage());
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		return resp;
	}
	
	/**
	 * 答题
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public Response answerSubject(UserCrowdSubjectAnswerReq req, String interfaceType, String reqIp){
		Response resp = new Response();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getBean().getUid());
			
			//数据校验
			if(resp.successed()){
				if(req.getBean().getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}else if(StringUtil.isBlankOrNull(req.getBean().getUtId())){
					resp.errorParam("用户任务编号为空");
				}else if(req.getBean().getAnswers().isEmpty()){
					resp.errorParam("作答信息为空");
				}
			}
			
			//处理
			if(resp.successed()){
				userCrowdTaskProcess.answerSubject(req.getBean());
			}
		}catch (ParamException e) {
			resp.errorParam(e.getMessage());
		}catch (DataException e) {
			resp.errorData(e.getMessage());
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		return resp;
	}
	
	/**
	 * 完成任务
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public Response finishTask(UserCrowdTaskFinishReq req, String interfaceType, String reqIp){
		Response resp = new Response();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getUid());
			
			//数据校验
			if(resp.successed()){
				if(req.getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}else if(StringUtil.isBlankOrNull(req.getUtId())){
					resp.errorParam("用户任务编号为空");
				}
			}
			
			//处理
			if(resp.successed()){
				userCrowdTaskProcess.finishTask(req.getUid(), req.getUtId());
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
