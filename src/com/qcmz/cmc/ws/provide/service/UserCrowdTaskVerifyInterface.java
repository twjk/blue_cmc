package com.qcmz.cmc.ws.provide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.process.UserCrowdTaskProcess;
import com.qcmz.cmc.service.IUserCrowdTaskService;
import com.qcmz.cmc.ws.provide.vo.UserCrowdTaskDealCountResp;
import com.qcmz.cmc.ws.provide.vo.UserCrowdTaskVerifyCountReq;
import com.qcmz.cmc.ws.provide.vo.UserCrowdTaskVerifyQueryReq;
import com.qcmz.cmc.ws.provide.vo.UserCrowdTaskVerifyQueryResp;
import com.qcmz.cmc.ws.provide.vo.UserCrowdTaskVerifyReq;
import com.qcmz.cmc.ws.provide.vo.UserCrowdTaskVerifyStartReq;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.framework.ws.BaseInterface;
import com.qcmz.framework.ws.vo.Response;
import com.qcmz.srm.client.util.SrmClient;

/**
 * 用户任务审核相关接口
 */
@Component
public class UserCrowdTaskVerifyInterface extends BaseInterface{
	@Autowired
	private IUserCrowdTaskService userCrowdTaskService;
	@Autowired
	private UserCrowdTaskProcess userCrowdTaskProcess;
	
	/**
	 * 获取审核任务数
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public UserCrowdTaskDealCountResp getCount(UserCrowdTaskVerifyCountReq req, String interfaceType, String reqIp){
		UserCrowdTaskDealCountResp resp = new UserCrowdTaskDealCountResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className);
			
			//数据校验
						
			//处理
			if(resp.successed()){
				String serviceType = SrmClient.getServiceType(req.getAuthAccount());
				resp.setResult(userCrowdTaskProcess.getVerifyCount(serviceType, req.getVerifyUid()));
			}
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		return resp;
	}
	
	/**
	 * 分页获取用户任务报名审核列表
	 * @param req
	 * @param page
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public UserCrowdTaskVerifyQueryResp findUserTask(UserCrowdTaskVerifyQueryReq req, String pageSize, String interfaceType, String reqIp){
		UserCrowdTaskVerifyQueryResp resp = new UserCrowdTaskVerifyQueryResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className);
			
			//数据校验			
			
			//处理
			if(resp.successed()){
				PageBean pageBean = new PageBean("1", pageSize);
				String serviceType = SrmClient.getServiceType(req.getAuthAccount());
				resp.setResult(userCrowdTaskService.findUserTask4Verify(req.getBean(), serviceType, pageBean.getPageSize()));
			}
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		return resp;
	}
	
	/**
	 * 开始报名审核
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public Response startVerify(UserCrowdTaskVerifyStartReq req, String interfaceType, String reqIp){
		Response resp = new Response();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getVerifyUid());
			
			//数据校验
			if(resp.successed()){
				if(req.getVerifyUid()==null){
					resp.errorParam("审核人用户编号为空");
				}else if(StringUtil.isBlankOrNull(req.getUtId())){
					resp.errorParam("用户任务编号为空");
				}
			}
			
			//处理
			if(resp.successed()){
				userCrowdTaskProcess.startVerify(req.getUtId(), req.getVerifyUid());
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
	 * 报名审核
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public Response finishVerify(UserCrowdTaskVerifyReq req, String interfaceType, String reqIp){
		Response resp = new Response();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getBean().getVerifyUid());
			
			//数据校验
			if(resp.successed()){
				if(req.getBean().getVerifyUid()==null){
					resp.errorParam("审核人用户编号为空");
				}else if(StringUtil.isBlankOrNull(req.getBean().getUtId())){
					resp.errorParam("用户任务编号为空");
				}else if(req.getBean().getPass()==null){
					resp.errorParam("是否通过审核为空");
				}
			}
			
			//处理
			if(resp.successed()){
				userCrowdTaskProcess.finishVerify(req.getBean());
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
