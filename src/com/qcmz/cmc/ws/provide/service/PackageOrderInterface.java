package com.qcmz.cmc.ws.provide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.process.PackageOrderProcess;
import com.qcmz.cmc.service.IPackageOrderService;
import com.qcmz.cmc.ws.provide.vo.OrderCreateResp;
import com.qcmz.cmc.ws.provide.vo.OrderCreateResult;
import com.qcmz.cmc.ws.provide.vo.OrderGetReq;
import com.qcmz.cmc.ws.provide.vo.PackageOrderAddReq;
import com.qcmz.cmc.ws.provide.vo.PackageOrderDetailResp;
import com.qcmz.cmc.ws.provide.vo.PackageOrderExchangedReq;
import com.qcmz.framework.constant.ExceptionConstants;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.exception.ParamException;
import com.qcmz.framework.util.BeanUtil;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.framework.ws.BaseInterface;
import com.qcmz.framework.ws.vo.Response;
import com.qcmz.srm.client.util.SrmClient;

@Component
public class PackageOrderInterface extends BaseInterface{
	@Autowired
	private IPackageOrderService packageOrderService;
	@Autowired
	private PackageOrderProcess packageOrderProcess;
	
	/**
	 * 创建优惠券包订单
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public OrderCreateResp addPackage(PackageOrderAddReq req, String interfaceType, String reqIp){
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
				}else if(req.getBean().getActId()==null){
					resp.errorParam("活动编号为空");
				}
			}
			
			//处理
			if(resp.successed()){
				String platform = SrmClient.getPlatform(req.getAuthAccount());
				String serviceType = SrmClient.getServiceType(req.getAuthAccount());
				OrderCreateResult r = packageOrderProcess.addPackage(req.getBean(), serviceType, platform, req.getAppVer());
				BeanUtil.copyProperties(resp, r);
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
	 * 获取优惠券包订单详细信息
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public PackageOrderDetailResp getPackage(OrderGetReq req, String interfaceType, String reqIp){
		PackageOrderDetailResp resp = new PackageOrderDetailResp();
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
				resp.setResult(packageOrderService.getPackageDetail(req.getOrderId(), req.getUid()));
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
	 * 保存优惠券包兑换信息
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public Response saveExchanged(PackageOrderExchangedReq req, String interfaceType, String reqIp){
		Response resp = new Response();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className);
			
			//数据验证
			if(resp.successed()){
				if(req.getBean().getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}else if(StringUtil.isBlankOrNull(req.getBean().getOrderId())){
					resp.errorParam("订单编号为空");
				}else if(StringUtil.isBlankOrNull(req.getBean().getExchangeCode())){
					resp.errorParam("兑换码为空");
				}
			}
			
			//处理
			if(resp.successed()){
				packageOrderProcess.saveExchanged(req.getBean());
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
