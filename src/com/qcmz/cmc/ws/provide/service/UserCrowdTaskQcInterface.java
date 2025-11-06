package com.qcmz.cmc.ws.provide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.process.UserCrowdTaskProcess;
import com.qcmz.cmc.service.IUserCrowdTaskService;
import com.qcmz.cmc.ws.provide.vo.UserCrowdSubjectQcReq;
import com.qcmz.cmc.ws.provide.vo.UserCrowdSubjectQueryReq;
import com.qcmz.cmc.ws.provide.vo.UserCrowdSubjectQueryResp;
import com.qcmz.cmc.ws.provide.vo.UserCrowdTaskDealCountResp;
import com.qcmz.cmc.ws.provide.vo.UserCrowdTaskQcCountReq;
import com.qcmz.cmc.ws.provide.vo.UserCrowdTaskQcQueryReq;
import com.qcmz.cmc.ws.provide.vo.UserCrowdTaskQcQueryResp;
import com.qcmz.cmc.ws.provide.vo.UserCrowdTaskQcReq;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.exception.ParamException;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.framework.ws.BaseInterface;
import com.qcmz.framework.ws.vo.Response;
import com.qcmz.srm.client.util.SrmClient;

/**
 * 用户任务审校相关接口
 */
@Component
public class UserCrowdTaskQcInterface extends BaseInterface{
	@Autowired
	private IUserCrowdTaskService userCrowdTaskService;
	@Autowired
	private UserCrowdTaskProcess userCrowdTaskProcess;
	
	/**
	 * 分页获取用户任务审校列表
	 * @param req
	 * @param page
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public UserCrowdTaskQcQueryResp findUserTask(UserCrowdTaskQcQueryReq req, String pageSize, String interfaceType, String reqIp){
		UserCrowdTaskQcQueryResp resp = new UserCrowdTaskQcQueryResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className);
			
			//数据校验			
			
			//处理
			if(resp.successed()){
				String serviceType = SrmClient.getServiceType(req.getAuthAccount());
				resp.setResult(userCrowdTaskService.findUserTask4Qc(req.getBean(), serviceType, new PageBean("1", pageSize).getPageSize()));
			}
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		return resp;
	}
	
	/**
	 * 获取审校任务数
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public UserCrowdTaskDealCountResp getCount(UserCrowdTaskQcCountReq req, String interfaceType, String reqIp){
		UserCrowdTaskDealCountResp resp = new UserCrowdTaskDealCountResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className);
			
			//数据校验
						
			//处理
			if(resp.successed()){
				String serviceType = SrmClient.getServiceType(req.getAuthAccount());
				resp.setResult(userCrowdTaskProcess.getQcCount(serviceType, req.getQcUid()));
			}
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		return resp;
	}
	
	/**
	 * 开始审校
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public Response startQc(UserCrowdTaskQcReq req, String interfaceType, String reqIp){
		Response resp = new Response();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getQcUid());
			
			//数据校验
			if(resp.successed()){
				if(req.getQcUid()==null){
					resp.errorParam("审校人用户编号为空");
				}else if(StringUtil.isBlankOrNull(req.getUtId())){
					resp.errorParam("用户任务编号为空");
				}
			}
			
			//处理
			if(resp.successed()){
				userCrowdTaskProcess.startQc(req.getUtId(), req.getQcUid());
			}
		} catch (DataException e) {
			resp.errorData(e.getMessage());
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		return resp;
	}
	
	/**
	 * 获取审校题目列表
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
				if(StringUtil.isBlankOrNull(req.getUtId())){
					resp.errorParam("用户任务编号为空");
				}else if(req.getQcId()==null){
					resp.errorParam("审校编号为空");
				}
			}
			
			//处理
			if(resp.successed()){
				resp.setResult(userCrowdTaskProcess.findUserSubject4Qc(req.getUtId(), req.getQcId()));
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
	 * 题目审校
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public Response qcSubject(UserCrowdSubjectQcReq req, String interfaceType, String reqIp){
		Response resp = new Response();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getBean().getQcUid());
			
			//数据校验
			if(resp.successed()){
				if(req.getBean().getQcUid()==null){
					resp.errorParam("审校人用户编号为空");
				}else if(StringUtil.isBlankOrNull(req.getBean().getUtId())){
					resp.errorParam("用户任务编号为空");
				}else if(req.getBean().getSubjectId()==null){
					resp.errorParam("题目编号为空");
				}else if(req.getBean().getSubjectScore()==null && req.getBean().getAnswerQcs().isEmpty()){
					resp.errorParam("审校结果为空");
				}
			}
			
			//处理
			if(resp.successed()){
				userCrowdTaskProcess.qcSubject(req.getBean());
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
	 * 完成审校
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public Response finishQc(UserCrowdTaskQcReq req, String interfaceType, String reqIp){
		Response resp = new Response();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getQcUid());
			
			//数据校验
			if(resp.successed()){
				if(req.getQcUid()==null){
					resp.errorParam("审校人用户编号为空");
				}else if(StringUtil.isBlankOrNull(req.getUtId())){
					resp.errorParam("用户任务编号为空");
				}
			}
			
			//处理
			if(resp.successed()){
				userCrowdTaskProcess.finishQc(req.getUtId(), req.getQcUid());
			}
		} catch (DataException e) {
			resp.errorData(e.getMessage());
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		return resp;
	}
}
