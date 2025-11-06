package com.qcmz.cmc.ws.provide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.cache.CatMap;
import com.qcmz.cmc.cache.ProductMap;
import com.qcmz.cmc.process.DubOrderProcess;
import com.qcmz.cmc.service.IDubOrderService;
import com.qcmz.cmc.ws.provide.vo.CatQueryResp;
import com.qcmz.cmc.ws.provide.vo.DubAddReq;
import com.qcmz.cmc.ws.provide.vo.DubOrderDetailResp;
import com.qcmz.cmc.ws.provide.vo.DubberQueryReq;
import com.qcmz.cmc.ws.provide.vo.DubberQueryResp;
import com.qcmz.cmc.ws.provide.vo.OrderCreateResp;
import com.qcmz.cmc.ws.provide.vo.OrderGetReq;
import com.qcmz.cmc.ws.provide.vo.ProductResp;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.constant.ExceptionConstants;
import com.qcmz.framework.exception.ParamException;
import com.qcmz.framework.util.BeanUtil;
import com.qcmz.framework.util.SecretUtil;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.framework.ws.BaseInterface;
import com.qcmz.framework.ws.vo.Request;
import com.qcmz.srm.client.util.SrmClient;

/**
 * 配音接口
 */
@Component
public class DubOrderInterface extends BaseInterface{
	@Autowired
	private IDubOrderService dubOrderService;
	@Autowired
	private ProductMap productMap;
	@Autowired
	private CatMap catMap;
	@Autowired
	private DubOrderProcess dubOrderProcess;
	
	/**
	 * 获取产品信息
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public ProductResp getProduct(Request req, String interfaceType, String reqIp){
		ProductResp resp = new ProductResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className);
			
			//数据验证
			if(resp.successed()){}
			
			//处理
			if(resp.successed()){
				resp.setResult(productMap.getProductInfo4Dub());
			}
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		
		return resp;
	}
	
	/**
	 * 创建机器配音订单
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public OrderCreateResp addDub(DubAddReq req, String interfaceType, String reqIp){
		OrderCreateResp resp = new OrderCreateResp();
		try {
			//身份验证
			if(StringUtil.isBlankOrNull(req.getBean().getTxt())){
				resp.errorData("配音文本为空");
			}else{
				SrmClient.validAccount(req, resp, className, SecretUtil.encryptByMd5(req.getBean().getTxt()));
			}
			
			//数据校验
			if(resp.successed()){
				if(req.getBean().getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}else if(StringUtil.isBlankOrNull(req.getBean().getTitle())){
					resp.errorData("作品名称为空");
				}else if(req.getBean().getAmount()<=0){
					resp.errorParam("订单金额有误");
				}
			}
			
			//处理
			if(resp.successed()){
				String platform = SrmClient.getPlatform(req.getAuthAccount());
				String serviceType = SrmClient.getServiceType(req.getAuthAccount());
				BeanUtil.copyProperties(resp, dubOrderProcess.addDub(req.getBean(), serviceType, platform, req.getAppVer()));
			}
		} catch (ParamException e) {
			resp.errorParam(e.getMessage());
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}

		return resp;
	}
	
	/**
	 * 获取配音员分类列表
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public CatQueryResp findDubberCat(Request req, String interfaceType, String reqIp){
		CatQueryResp resp = new CatQueryResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className);
			
			//数据验证
			if(resp.successed()){}
			
			//处理
			if(resp.successed()){
				resp.setResult(catMap.findCat(DictConstants.CATTYPE_DUBBER));
			}
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		
		return resp;
	}
	
	/**
	 * 获取配音员列表
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public DubberQueryResp findDubber(DubberQueryReq req, String interfaceType, String reqIp){
		DubberQueryResp resp = new DubberQueryResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className);
			
			//数据验证
			if(resp.successed()){}
			
			//处理
			if(resp.successed()){
				resp.setResult(dubOrderProcess.findDubber(req.getCatId()));
			}
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		
		return resp;
	}
	
	/**
	 * 创建真人配音订单
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public OrderCreateResp addHumanDub(DubAddReq req, String interfaceType, String reqIp){
		OrderCreateResp resp = new OrderCreateResp();
		try {
			//身份验证
			if(StringUtil.isBlankOrNull(req.getBean().getTxt())){
				resp.errorData("配音文本为空");
			}else{
				SrmClient.validAccount(req, resp, className, SecretUtil.encryptByMd5(req.getBean().getTxt()));
			}
			
			//数据校验
			if(resp.successed()){
				if(req.getBean().getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}else if(StringUtil.isBlankOrNull(req.getBean().getMobile())){
					resp.errorData("手机号码为空");
				}else if(req.getBean().getAmount()<=0){
					resp.errorParam("订单金额有误");
				}else if(req.getBean().getDubberId()==null){
					resp.errorParam("配音员编号为空");
				}
			}
			
			//处理
			if(resp.successed()){
				String platform = SrmClient.getPlatform(req.getAuthAccount());
				String serviceType = SrmClient.getServiceType(req.getAuthAccount());
				BeanUtil.copyProperties(resp, dubOrderProcess.addHumanDub(req.getBean(), serviceType, platform, req.getAppVer()));
			}
		} catch (ParamException e) {
			resp.errorParam(e.getMessage());
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}

		return resp;
	}
	
	/**
	 * 获取配音订单详细信息
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public DubOrderDetailResp getDub(OrderGetReq req, String interfaceType, String reqIp){
		DubOrderDetailResp resp = new DubOrderDetailResp();
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
				resp.setResult(dubOrderService.getDetail(req.getOrderId(), req.getUid()));
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