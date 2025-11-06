package com.qcmz.cmc.ws.provide.service;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.bdc.ws.provide.locator.LogInterfaceWS;
import com.qcmz.cmc.service.IAccountService;
import com.qcmz.cmc.service.IProductService;
import com.qcmz.cmc.service.ITradeService;
import com.qcmz.cmc.ws.provide.vo.AccountReq;
import com.qcmz.cmc.ws.provide.vo.AccountResp;
import com.qcmz.cmc.ws.provide.vo.ConsumeReq;
import com.qcmz.cmc.ws.provide.vo.ConsumeResp;
import com.qcmz.cmc.ws.provide.vo.ConsumeRuleResp;
import com.qcmz.cmc.ws.provide.vo.ProductQueryReq;
import com.qcmz.cmc.ws.provide.vo.ProductQueryResp;
import com.qcmz.cmc.ws.provide.vo.RechargeReq;
import com.qcmz.cmc.ws.provide.vo.RechargeResp;
import com.qcmz.cmc.ws.provide.vo.TradeQueryReq;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.framework.ws.vo.PageBeanResponse;
import com.qcmz.framework.ws.vo.Request;
import com.qcmz.srm.client.util.AuthUtil;

/**
 * 类说明：费用接口实现
 * 修改历史：
 */
@Component
public class FeeInterface {
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private IProductService productService;
	@Autowired
	private IAccountService accountService;
	@Autowired
	private ITradeService tradeService;
	
	private String className = this.getClass().getSimpleName();
	
	/**
	 * 获取帐户信息
	 * @param req
	 * @return
	 * 修改历史：
	 */
	public AccountResp getAccount(AccountReq req, String interfaceType, String reqIp){
		AccountResp resp = new AccountResp();
		Date reqTime = new Date();
		Exception exception = null;
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		try {
			//身份验证
			AuthUtil.validAccount(req, resp, className, String.valueOf(req.getUserId()));
			
			//数据验证
			if(resp.successed()){
				if(req.getUserId()==null){
					resp.errorParam("用户编号为空");
				}
			}
			
			//查询
			if(resp.successed()){
				resp.setAccount(accountService.getAccountInfo(req.getUserId()));
			}
		} catch (DataException e) {
			resp.errorData(e.getMessage());
		} catch (Exception e) {
			resp.error();
			exception = e;
			logger.error("访问接口["+methodName+"]失败", e);
		}
		
		//保存接口日志
		if(!resp.successed()){
			LogInterfaceWS.logInterfaceThread(interfaceType, className, methodName, reqTime, reqIp, req, resp, exception);
		}
		
		return resp;
	}
	
	/**
	 * 获取产品列表
	 * @param req
	 * @return
	 * 修改历史：
	 */
	public ProductQueryResp findProduct(ProductQueryReq req, String interfaceType, String reqIp){
		ProductQueryResp resp = new ProductQueryResp();
		Date reqTime = new Date();
		Exception exception = null;
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		try {
			//身份验证
			AuthUtil.validAccount(req, resp, className);
			
			//数据验证
			
			//查询
			if(resp.successed()){
				resp.getProducts().addAll(productService.findSellProduct(req.getUserId()));
			}
		} catch (DataException e) {
			resp.errorData(e.getMessage());
		} catch (Exception e) {
			resp.error();
			exception = e;
			logger.error("访问接口["+methodName+"]失败", e);
		}
		
		//保存接口日志
		if(!resp.successed()){
			LogInterfaceWS.logInterfaceThread(interfaceType, className, methodName, reqTime, reqIp, req, resp, exception);
		}
		
		return resp;
	}
	
	/**
	 * 充值
	 * @param req
	 * @return
	 * 修改历史：
	 */
	public RechargeResp recharge(RechargeReq req, String interfaceType, String reqIp){
		RechargeResp resp = new RechargeResp();
		Date reqTime = new Date();
		Exception exception = null;
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		try {
			//身份验证
			AuthUtil.validAccount(req, resp, className, String.valueOf(req.getRecharge().getUserId()));
			
			//数据验证
			if(resp.successed()){
				if(req.getRecharge().getUserId()==null){
					resp.errorParam("用户编号为空");
				}else if(req.getRecharge().getProductId()==null
						&& StringUtil.isBlankOrNull(req.getRecharge().getProductCode())){
					resp.errorParam("产品编号和产品代码均为空");
				}else if(req.getRecharge().getNum()==null){
					resp.errorParam("产品数量为空");
				}else if(req.getRecharge().getAmount()==null){
					resp.errorParam("充值金额为空");
				}else if(StringUtil.isBlankOrNull(req.getRecharge().getTime())){
					resp.errorParam("充值时间为空");
				}
			}
			
			//充值
			if(resp.successed()){
				resp.setAccount(tradeService.recharge(req.getRecharge()));
			}
		} catch (DataException e) {
			resp.errorData(e.getMessage());
		} catch (Exception e) {
			resp.error();
			exception = e;
			logger.error("访问接口["+methodName+"]失败", e);
		}

		//保存接口日志
		if(!resp.successed()){
			LogInterfaceWS.logInterfaceThread(interfaceType, className, methodName, reqTime, reqIp, req, resp, exception);
		}
		
		return resp;
	}
	
	/**
	 * 获取消费规则
	 * @param req
	 * @return
	 * 修改历史：
	 */
	public ConsumeRuleResp getConsumeRule(Request req, String interfaceType, String reqIp){
		ConsumeRuleResp resp = new ConsumeRuleResp();
		Date reqTime = new Date();
		Exception exception = null;
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		try {
			//身份验证
			AuthUtil.validAccount(req, resp, className);

			//数据验证
			
			//查询
			if(resp.successed()){
				resp.setRule(tradeService.getConsumeRule());
			}
		} catch (Exception e) {
			resp.error();
			exception = e;
			logger.error("访问接口["+methodName+"]失败", e);
		}
		
		//保存接口日志
		if(!resp.successed()){
			LogInterfaceWS.logInterfaceThread(interfaceType, className, methodName, reqTime, reqIp, req, resp, exception);
		}
		
		return resp;
	}
	
	/**
	 * 消费
	 * @param req
	 * @return
	 * 修改历史：
	 */
	public ConsumeResp consume(ConsumeReq req, String interfaceType, String reqIp){
		ConsumeResp resp = new ConsumeResp();
		Date reqTime = new Date();
		Exception exception = null;
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		try {
			//身份验证
			AuthUtil.validAccount(req, resp, className, String.valueOf(req.getConsume().getUserId()));

			//数据验证
			if(resp.successed()){
				if(req.getConsume().getUserId()==null){
					resp.errorParam("用户编号为空");
				}else if(StringUtil.isBlankOrNull(req.getConsume().getItem())){
					resp.errorParam("消费项目为空");
				}else if(StringUtil.isBlankOrNull(req.getConsume().getLangCode())){
					resp.errorParam("语言代码为空");
				}
			}
			
			//消费
			if(resp.successed()){
				resp.setAccount(tradeService.consume(req.getConsume()));
			}
		} catch (DataException e) {
			resp.errorData(e.getMessage());
		} catch (Exception e) {
			resp.error();
			exception = e;
			logger.error("访问接口["+methodName+"]失败", e);
		}
		
		//保存接口日志
		if(!resp.successed()){
			LogInterfaceWS.logInterfaceThread(interfaceType, className, methodName, reqTime, reqIp, req, resp, exception);
		}
		
		return resp;
	}
	
	/**
	 * 分页获取用户交易明细
	 * @param req
	 * @param page
	 * @param pageSize
	 * @return
	 * 修改历史：
	 */
	public PageBeanResponse findTrade(TradeQueryReq req, String page, String pageSize, String interfaceType, String reqIp){
		PageBeanResponse resp = new PageBeanResponse();
		Date reqTime = new Date();
		Exception exception = null;
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		try {
			//身份验证
			AuthUtil.validAccount(req, resp, className, String.valueOf(req.getSearch().getUserId()));
			
			//数据验证
			if(resp.successed()){
				if(req.getSearch().getUserId()==null){
					resp.errorData("用户编号为空");
				}
			}
			
			//查询
			if(resp.successed()){
				PageBean pageBean = new PageBean(page, pageSize);
				tradeService.findTradeInfo(req.getSearch(), pageBean);
				resp.setPageBean(pageBean);
			}
		} catch (DataException e) {
			resp.errorData(e.getMessage());
		} catch (Exception e) {
			resp.error();
			exception = e;
			logger.error("访问接口["+methodName+"]失败", e);
		}
		
		//保存接口日志
		if(!resp.successed()){
			LogInterfaceWS.logInterfaceThread(interfaceType, className, methodName, reqTime, reqIp, req, resp, exception);
		}
		
		return resp;
	}
}