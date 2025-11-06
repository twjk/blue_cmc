package com.qcmz.cmc.ws.provide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.cache.TransComboMap;
import com.qcmz.cmc.process.TransComboProcess;
import com.qcmz.cmc.ws.provide.vo.OrderCreateResp;
import com.qcmz.cmc.ws.provide.vo.OrderCreateResult;
import com.qcmz.cmc.ws.provide.vo.OrderGetReq;
import com.qcmz.cmc.ws.provide.vo.TransComboAddReq;
import com.qcmz.cmc.ws.provide.vo.TransComboBean;
import com.qcmz.cmc.ws.provide.vo.TransComboGetReq;
import com.qcmz.cmc.ws.provide.vo.TransComboGetResp;
import com.qcmz.cmc.ws.provide.vo.TransComboOrderDetailResp;
import com.qcmz.cmc.ws.provide.vo.TransComboQueryReq;
import com.qcmz.cmc.ws.provide.vo.TransComboQueryResp;
import com.qcmz.cmc.ws.provide.vo.UserTransComboExchangeReq;
import com.qcmz.cmc.ws.provide.vo.UserTransComboQueryReq;
import com.qcmz.cmc.ws.provide.vo.UserTransComboQueryResp;
import com.qcmz.framework.constant.ExceptionConstants;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.exception.ParamException;
import com.qcmz.framework.util.BeanUtil;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.framework.ws.BaseInterface;
import com.qcmz.framework.ws.vo.Response;
import com.qcmz.srm.client.util.SrmClient;

/**
 * 翻译套餐接口
 */
@Component
public class TransComboInterface extends BaseInterface {
	@Autowired
	private TransComboMap transComboMap;
	@Autowired
	private TransComboProcess transComboProcess;
		
	/**
	 * 获取可用翻译套餐列表
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public TransComboQueryResp findCombo(TransComboQueryReq req, String interfaceType, String reqIp){
		TransComboQueryResp resp = new TransComboQueryResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className);
			
			//数据校验
			
			//处理
			if(resp.successed()){
				String platform = SrmClient.getPlatform(req.getAuthAccount());
				resp.setResult(transComboProcess.findCombo(req.getTransType(), req.getComboType(), req.getSceneId(), platform, req.getAppVer()));
			}
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		
		return resp;
	}
	
	/**
	 * 获取可用翻译套餐信息
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public TransComboGetResp getCombo(TransComboGetReq req, String interfaceType, String reqIp){
		TransComboGetResp resp = new TransComboGetResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className);
			
			//数据校验
			if(resp.successed()){
				if(req.getComboId()==null){
					resp.errorParam("套餐编号为空");
				}
			}
			
			//处理
			if(resp.successed()){
				TransComboBean result = transComboMap.getValidComboInfo(req.getComboId());
				if(result!=null){
					resp.setResult(result);
				}else{
					resp.errorParam("套餐不存在");
				}
			}
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}

		return resp;
	}
	
	/**
	 * 提交翻译套餐订单
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public OrderCreateResp addComboOrder(TransComboAddReq req, String interfaceType, String reqIp){
		OrderCreateResp resp = new OrderCreateResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getBean().getUid());
			
			//数据验证
			if(resp.successed()){
				if(req.getBean().getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}else if(req.getBean().getUserType()==null){
					resp.errorParam(ExceptionConstants.MSG_USERTYPE_EMPTY);
				}else if(req.getBean().getComboId()==null){
					resp.errorParam("套餐编号为空");
				}else if(req.getBean().getPkgId()==null){
					if(req.getBean().getNum()==null || req.getBean().getNum()<1){
						resp.errorParam("数量为空");
					}
				}else if(req.getBean().getAmount()<0){
					resp.errorParam("订单总金额有误");
				}
			}
			
			//处理
			if(resp.successed()){
				String platform = SrmClient.getPlatform(req.getAuthAccount());
				String serviceType = SrmClient.getServiceType(req.getAuthAccount());
				OrderCreateResult r = transComboProcess.addCombo(req.getBean(), serviceType, platform, req.getAppVer());
				BeanUtil.copyProperties(resp, r);
			}
		}catch (ParamException e) {
			resp.errorParam(e.getMessage());
		}catch (DataException e) {
			resp.errorParam(e.getMessage());
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		
		return resp;
	}
	
	/**
	 * 获取翻译套餐订单详细信息
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public TransComboOrderDetailResp getComboOrder(OrderGetReq req, String interfaceType, String reqIp){
		TransComboOrderDetailResp resp = new TransComboOrderDetailResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getUid());
			
			//数据校验
			if(resp.successed()){
				if(req.getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}else if(StringUtil.isBlankOrNull(req.getOrderId())){
					resp.errorParam(ExceptionConstants.MSG_ORDERID_EMPTY);
				}
			}
			
			//处理
			if(resp.successed()){
				resp.setResult(transComboProcess.getTransComboDetail(req.getOrderId(), req.getUid()));
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
	 * 获取用户可用翻译套餐列表
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public UserTransComboQueryResp findUserValidCombo(UserTransComboQueryReq req, String interfaceType, String reqIp){
		UserTransComboQueryResp resp = new UserTransComboQueryResp();
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
				resp.setResult(transComboProcess.findUserValidCombo(req.getUid(), req.getTransType(), req.getComboType()));
			}
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}

		return resp;
	}
	
	/**
	 * 获取用户翻译套餐列表
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public UserTransComboQueryResp findUserCombo(UserTransComboQueryReq req, String interfaceType, String reqIp){
		UserTransComboQueryResp resp = new UserTransComboQueryResp();
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
				resp.setResult(transComboProcess.findUserCombo(req.getUid(), req.getTransType(), req.getComboType()));
			}
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}

		return resp;
	}
	
	/**
	 * 兑换套餐
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public Response exchangeCombo(UserTransComboExchangeReq req, String interfaceType, String reqIp){
		Response resp = new Response();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getUid());
			
			//数据验证
			if(resp.successed()){
				if(req.getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}else if(StringUtil.isBlankOrNull(req.getExchangeCode())){
					resp.errorParam("激活码为空");
				}
			}
			
			if(resp.successed()){
				transComboProcess.exchangeUserCombo(req.getUid(), req.getExchangeCode());
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
}
