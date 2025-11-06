package com.qcmz.cmc.ws.provide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.process.WalletRechargeProcess;
import com.qcmz.cmc.ws.provide.vo.ApplepaySynReq;
import com.qcmz.cmc.ws.provide.vo.OrderCancelReq;
import com.qcmz.cmc.ws.provide.vo.OrderPrepayReq;
import com.qcmz.cmc.ws.provide.vo.PrepayResp;
import com.qcmz.cmc.ws.provide.vo.WalletRechargeCreateReq;
import com.qcmz.cmc.ws.provide.vo.WalletRechargeCreateResp;
import com.qcmz.cmc.ws.provide.vo.WalletRechargeCreateResult;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.constant.ExceptionConstants;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.exception.ParamException;
import com.qcmz.framework.exception.ProxyException;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.framework.ws.BaseInterface;
import com.qcmz.framework.ws.vo.Response;
import com.qcmz.srm.client.util.SrmClient;

/**
 * 钱包充值接口
 */
@Component
public class WalletRechargeInterface extends BaseInterface {
	@Autowired
	private WalletRechargeProcess walletProcess;
	
	/**
	 * 创建充值订单
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 */
	public WalletRechargeCreateResp create(WalletRechargeCreateReq req, String interfaceType, String reqIp){
		WalletRechargeCreateResp resp = new WalletRechargeCreateResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getBean().getUid());
			
			String platform = SrmClient.getPlatform(req.getAuthAccount());
			//数据校验
			if(resp.successed()){
				if(req.getBean().getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}else if(req.getBean().getAmount()<=0){
					resp.errorParam("金额有误");
				}else if(DictConstants.DICT_PLATFORM_MINIPROGRAM.equals(platform)
						&& StringUtil.isBlankOrNull(req.getBean().getOpenid())){
					resp.errorParam("openid为空");
				}
			}
			
			//处理
			if(resp.successed()){
				String serviceType = SrmClient.getServiceType(req.getAuthAccount());
				WalletRechargeCreateResult r = walletProcess.create(req.getBean(), serviceType, platform, req.getAppVer());
				resp.setOrderId(r.getOrderId());
				resp.setPrepayResult(r.getPrepayResult());
			}
		} catch (ParamException e) {
			resp.errorParam(e.getMessage());
		} catch (DataException e) {
			resp.errorData(e.getMessage());
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		
		return resp;
	}
	
	/**
	 * 订单支付预处理
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public PrepayResp prepay(OrderPrepayReq req, String interfaceType, String reqIp){
		PrepayResp resp = new PrepayResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getBean().getUid());
			
			//数据验证
			String platform = SrmClient.getPlatform(req.getAuthAccount());
			if(resp.successed()){
				if(req.getBean().getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}else if(StringUtil.isBlankOrNull(req.getBean().getTradeWay())){
					resp.errorParam("交易途径为空");
				}else if(StringUtil.isBlankOrNull(req.getBean().getOrderId())){
					resp.errorParam(ExceptionConstants.MSG_ORDERID_EMPTY);
				}else if(req.getBean().getAmount()==null){
					resp.errorParam("金额有误");
				}else if(DictConstants.DICT_PLATFORM_MINIPROGRAM.equals(platform)
						&& StringUtil.isBlankOrNull(req.getBean().getOpenid())){
					resp.errorParam("openid为空");
				}
			}
			
			//处理
			if(resp.successed()){
				resp.setResult(walletProcess.prepay(req.getBean(), platform));
			}
		}catch (ParamException e) {
			resp.errorParam(e.getMessage());
		}catch (DataException e) {
			resp.errorData(e.getMessage());
		}catch (ProxyException e) {
			resp.error();
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		
		return resp;
	}
	
	/**
	 * 处理苹果支付通知
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public Response synApplepay(ApplepaySynReq req, String interfaceType, String reqIp){
		Response resp = new Response();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getUid());
			
			//数据校验
			if(resp.successed()){
				if(req.getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}else if(StringUtil.isBlankOrNull(req.getOrderId())){
					resp.errorParam(ExceptionConstants.MSG_ORDERID_EMPTY);
				}else if(StringUtil.isBlankOrNull(req.getReceipt())){
					resp.errorParam("支付结果为空");
				}
			}
			
			//处理
			if(resp.successed()){
				boolean syn = walletProcess.synApplepay(req.getOrderId(), req.getUid(), req.getReceipt());
				if(!syn){
					resp.errorParam("数据有误");
				}
			}
		} catch (ProxyException e){
			resp.errorParam("验证失败");
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		
		return resp;
	}
	
	/**
	 * 取消充值
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public Response cancel(OrderCancelReq req, String interfaceType, String reqIp){
		Response resp = new Response();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getUid());
			
			//数据验证
			if(resp.successed()){
				if(req.getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}else if(StringUtil.isBlankOrNull(req.getOrderId())){
					resp.errorParam(ExceptionConstants.MSG_ORDERID_EMPTY);
				}
			}
			
			//处理
			if(resp.successed()){
				walletProcess.cancel(req.getOrderId(), req.getUid());
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