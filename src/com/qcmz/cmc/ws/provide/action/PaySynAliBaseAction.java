package com.qcmz.cmc.ws.provide.action;

import java.util.Map;

import org.apache.log4j.Logger;

import com.qcmz.cmc.proxy.pay.alipay.AlipayProxy;
import com.qcmz.framework.action.BaseAction;
import com.qcmz.framework.exception.ProxyException;

/**
 * 类说明：接收支付宝支付异步通知
 * 修改历史：
 */
public abstract class PaySynAliBaseAction extends BaseAction{	
	protected Logger logger = Logger.getLogger(this.getClass());
	
	protected String alipaySuccess = "success";
	protected String alipayFail = "fail";
	
	/**
	 * 支付宝异步通知（扣款、退款）
	 * 通知触发条件：交易成功（TRADE_FINISHED）支付成功（TRADE_SUCCESS）交易创建（WAIT_BUYER_PAY）
	 * 交易关闭（TRADE_CLOSED）不触发通知
	 * Post方式发送
	 * https://doc.open.alipay.com/doc2/detail?treeId=59&articleId=103666&docType=1
	 * @return
	 * 修改历史：
	 */
	@Override
	public String execute() throws Exception {
		StringBuilder sbLog = new StringBuilder();
		String queryString = null;
		try {
			//接收参数
			queryString = getQueryString(); 
			Map<String, String> params = getParameterMap();
			
			//处理
			boolean syn = false;
			String tradeStatus = params.get("trade_status");
			String subServiceType = params.get("passback_params");
			
			if(!params.isEmpty() && !AlipayProxy.WAIT_BUYER_PAY.equals(tradeStatus)){
				syn = synAlipay(params, subServiceType);
			}
			
			//日志
			if(syn){
				logger.info(sbLog.append("SUCCESS").append("|").append(queryString));
			}else{
				logger.info(sbLog.append("IGNORE").append("|").append(queryString));
			}
			response.getWriter().print(alipaySuccess);
		} catch (ProxyException e) {
			logger.info(sbLog.append("FAIL[").append(e.getMessage()).append("]|").append(queryString));
			response.getWriter().print(alipayFail);
		} catch (Exception e) {
			logger.error(sbLog.append("ERROR").append("|").append(queryString), e);
		}
		return null;
	}
	
	public abstract boolean synAlipay(Map<String, String> params, String subServieType);
}
