package com.qcmz.cmc.ws.provide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.process.RewardProcess;
import com.qcmz.cmc.proxy.pay.alipay.AlipayProxy;
import com.qcmz.cmc.service.IRewardAccountService;
import com.qcmz.cmc.service.IRewardBillService;
import com.qcmz.cmc.ws.provide.vo.AlipayGetOpenidReq;
import com.qcmz.cmc.ws.provide.vo.AlipayGetOpenidResp;
import com.qcmz.cmc.ws.provide.vo.RewardAccountGetReq;
import com.qcmz.cmc.ws.provide.vo.RewardAccountResp;
import com.qcmz.cmc.ws.provide.vo.RewardBestowReq;
import com.qcmz.cmc.ws.provide.vo.RewardBillQueryReq;
import com.qcmz.cmc.ws.provide.vo.RewardBillQueryResp;
import com.qcmz.cmc.ws.provide.vo.RewardCashApplyReq;
import com.qcmz.cmc.ws.provide.vo.RewardInviteReq;
import com.qcmz.cmc.ws.provide.vo.RewardRewardReq;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.constant.ExceptionConstants;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.exception.ParamException;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.SecretUtil;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.framework.ws.BaseInterface;
import com.qcmz.framework.ws.vo.Response;
import com.qcmz.srm.client.util.SrmClient;

/**
 * 奖励金接口
 */
@Component
public class RewardInterface extends BaseInterface {
	@Autowired
	private IRewardAccountService rewardAccountService;
	@Autowired
	private IRewardBillService rewardBillService;
	
	@Autowired
	private AlipayProxy alipayProxy;
	
	@Autowired
	private RewardProcess rewardProcess;
	
	/**
	 * 获取帐户信息
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public RewardAccountResp getAccount(RewardAccountGetReq req, String interfaceType, String reqIp){
		RewardAccountResp resp = new RewardAccountResp();
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
				resp.setResult(rewardAccountService.getAccountInfo(req.getUid()));
			}
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		
		return resp;
	}
	
	/**
	 * 分页查询账单列表
	 * @param req
	 * @param page
	 * @param pageSize
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public RewardBillQueryResp findBill(RewardBillQueryReq req, String interfaceType, String reqIp){
		RewardBillQueryResp resp = new RewardBillQueryResp();
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
				PageBean pageBean = new PageBean(1, req.getBean().getPageSize());
				req.getBean().setPageSize(pageBean.getPageSize());
				resp.setResult(rewardBillService.findBillInfo(req.getBean()));
			}
			
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		
		return resp;
	}
	
	/**
	 * 奖励
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public Response reward(RewardRewardReq req, String interfaceType, String reqIp){
		Response resp = new Response();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getBean().getServiceId());
			
			//数据验证
			if(resp.successed()){
				if(req.getBean().getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}else if(StringUtil.isBlankOrNull(req.getBean().getSubServiceType())){
					resp.errorParam("子业务类型为空");
				}else if(StringUtil.isBlankOrNull(req.getBean().getServiceId())){
					resp.errorParam("业务编号为空");
				}else if(req.getBean().getRewardAmount()==null 
						|| req.getBean().getRewardAmount()<=0){
					resp.errorParam("奖励金额有误");
				}
			}
			
			//处理
			if(resp.successed()){
				rewardProcess.reward(req.getBean());
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
	 * 邀请奖励
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public Response inviteReward(RewardInviteReq req, String interfaceType, String reqIp){
		Response resp = new Response();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getBean().getServiceId());
			
			//数据验证
			if(resp.successed()){
				if(req.getBean().getInviterId()==null || req.getBean().getInviteeId()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}else if(StringUtil.isBlankOrNull(req.getBean().getServiceId())){
					resp.errorParam("业务编号为空");
				}
			}
			
			//处理
			if(resp.successed()){
				rewardProcess.inviteReward(req.getBean());
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
	 * 赠送奖励金
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public Response bestowReward(RewardBestowReq req, String interfaceType, String reqIp){
		Response resp = new Response();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className);
			
			//数据验证
			if(resp.successed()){
				if(req.getBean().getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}else if(req.getBean().getAmount()==null || req.getBean().getAmount()<=0){
					resp.errorData("金额有误");
				}
			}
			
			//处理
			if(resp.successed()){
				rewardProcess.bestowReward(req.getBean().getUid(), req.getBean().getAmount(), req.getBean().getDescription());
			}
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}

		return resp;
	}
	
	/**
	 * 申请提现
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public Response applyCash(RewardCashApplyReq req, String interfaceType, String reqIp){
		Response resp = new Response();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getBean().getUid());
			
			//数据验证
			if(resp.successed()){
				if(req.getBean().getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}else if(!DictConstants.isValidPayAccountType(req.getBean().getAccountType())){
					resp.errorParam("帐户类型有误");
				}else if(StringUtil.isBlankOrNull(req.getBean().getAccount())){
					resp.errorParam("帐户为空");
				}else if(req.getBean().getAmount()==null || req.getBean().getAmount()<=0){
					resp.errorData("提现金额有误");
				}
			}
			
			//处理
			if(resp.successed()){
				rewardProcess.applyCash(req.getBean().getUid(), req.getBean().getAmount(), req.getBean().getAccountType(), req.getBean().getAccount());
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
	 * 获取openid
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public AlipayGetOpenidResp getAlipayOpenid(AlipayGetOpenidReq req, String interfaceType, String reqIp){
		AlipayGetOpenidResp resp = new AlipayGetOpenidResp();
		try {
			//身份验证、数据验证
			if(StringUtil.isBlankOrNull(req.getAlipayAuthCode())){
				resp.errorParam("授权码为空");
			}else{
				SrmClient.validAccount(req, resp, className, SecretUtil.encryptByMd5(req.getAlipayAuthCode()));
			}
			
			if(resp.successed()){
				resp.setResult(alipayProxy.getOpenid(req.getAlipayAuthCode(), SrmClient.getServiceType(req.getAuthAccount())));
			}
		}catch (ParamException e) {
			resp.errorParam(e.getMessage());
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		
		return resp;
	}
}
