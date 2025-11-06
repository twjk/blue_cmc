package com.qcmz.cmc.proxy.pay.alipay;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.vo.PrepayBean;
import com.qcmz.cmc.vo.TradeResultBean;
import com.qcmz.cmc.ws.provide.vo.PrepayAlipayBean;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.exception.ProxyException;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.NumberUtil;
import com.qcmz.framework.util.StringUtil;

/**
 * 类说明：支付宝工具类
 * 修改历史：
 */
public class AlipayProxyV2{
	private static Logger logger = Logger.getLogger(AlipayProxyV2.class);
	/**
	 * 支付宝网关
	 */
	private static final String URL_GATEWAY = "https://openapi.alipay.com/gateway.do";
	/**
	 * 交易创建，等待买家付款
	 */
	public static final String WAIT_BUYER_PAY = "WAIT_BUYER_PAY";
	/**
	 * 交易成功
	 */
	public static final String TRADE_SUCCESS = "TRADE_SUCCESS";
	/**
	 * 交易成功且结束
	 */
	public static final String TRADE_FINISHED = "TRADE_FINISHED";
	/**
	 * 在指定时间段内未支付时关闭的交易；在交易完成全额退款成功时关闭的交易。
	 */
	public static final String TRADE_CLOSED = "TRADE_CLOSED";
	/**
	 * 退款成功
	 * 全额退款  trade_status = TRADE_CLOSED，refund_status=REFUND_SUCCESS
	 * 非全额退款trade_status = TRADE_SUCCESS，refund_status=REFUND_SUCCESS
	 */
	public static final String REFUND_SUCCESS = "REFUND_SUCCESS";
	
	
	private static Map<String, AlipayClient> clientMap = new HashMap<String, AlipayClient>();
	public static AlipayClient getAlipayClient(AlipayAccountBean account){
		AlipayClient alipayClient = clientMap.get(account.getAppid());
		if(alipayClient==null){
			alipayClient = new DefaultAlipayClient(URL_GATEWAY, account.getAppid(), account.getAppPrivateKey(), "json", "UTF-8", account.getAlipayPublicKey(), account.getSignType());
			clientMap.put(account.getAppid(), alipayClient);
		}
		return alipayClient;
	}
	
	/**
	 * 支付前处理，统一下单
	 * @param preBean
	 * @return 失败抛出ProxyException
	 * 修改历史：
	 */
	public static PrepayAlipayBean prePay(PrepayBean preBean){
		PrepayAlipayBean result = null;
		try {
			AlipayAccountBean account = AlipayAccountUtil.getAccount(preBean.getServiceType());
			
			//实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
			//SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
			AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
			model.setBody(preBean.getOrderDesc());		//商品详情
			model.setSubject(preBean.getOrderDesc());	//商品名称
			model.setOutTradeNo(preBean.getOrderId());	//商户网站唯一订单号
			model.setTimeoutExpress("30m");
			model.setTotalAmount(String.valueOf(preBean.getAmount()));	//总金额，元，[0.01，100000000.00]，精确到小数点后两位
			model.setProductCode("QUICK_MSECURITY_PAY");
			
			AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
			request.setBizModel(model);
			request.setNotifyUrl(account.getNotifyUrl());	//服务器异步通知页面路径
			
	        AlipayTradeAppPayResponse response = getAlipayClient(AlipayAccountUtil.getAccount(preBean.getServiceType())).sdkExecute(request);
	        
	        result = new PrepayAlipayBean();
	        result.setPayInfo(response.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。
			return result;
		} catch (Exception e) {
			StringBuilder sbError = new StringBuilder();
			sbError.append(preBean.getOrderId()).append("支付预处理失败：");
			logger.error(sbError, e);
			throw new ProxyException();
		}
	}
	
	/**
	 * 查询支付结果
	 * @param orderId
	 * 修改历史：
	 */
	public static TradeResultBean queryPay(String orderId, String serviceType){
		TradeResultBean result = null;
		String hint = null;
		StringBuilder sbError = new StringBuilder();
		try {
			hint = new StringBuilder(orderId).append("查询支付结果").toString();
			sbError.append(hint).append("失败：");
			StringBuilder sb = new StringBuilder();
			sb.append("{")
			  .append("\"out_trade_no\":\"").append(orderId).append("\"")
			  .append("}")
			  ;
			
			logger.info(new StringBuilder(hint).append("请求：").append(sb));
			AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
			request.setBizContent(sb.toString());
			AlipayTradeQueryResponse resp = getAlipayClient(AlipayAccountUtil.getAccount(serviceType)).execute(request);
			getAlipayClient(AlipayAccountUtil.getAccount(serviceType)).sdkExecute(request);
			logger.info(new StringBuilder(hint).append("返回：").append(resp.getBody()));
			
			if("10000".equals(resp.getCode()) 
					&& (TRADE_FINISHED.equals(resp.getTradeStatus()) 
							|| TRADE_SUCCESS.equals(resp.getTradeStatus()))){
				result = new TradeResultBean();
				result.setOrderId(orderId);
				result.setOutTradeId(resp.getTradeNo());
				result.setAmount(Double.valueOf(resp.getTotalAmount()));
				result.setTradeWay(DictConstants.DICT_TRADEWAY_ALIPAY);
				result.setTradeType(DictConstants.DICT_TRADETYPE_PAY);
				result.setTradeStatus(DictConstant.DICT_TRADESTATUS_SUCCESS);
				result.setTradeTime(resp.getSendPayDate());
			}
		} catch (Exception e) {
			logger.error(sbError, e);
			throw new ProxyException("查询支付结果失败");
		}
		return result;
	}

	/**
	 * 退款
	 * @param orderId
	 * @param refundAmount
	 * @param reason
	 * 修改历史：
	 */
	public static TradeResultBean refund(String orderId, String serviceType, Double refundAmount){
		TradeResultBean result = new TradeResultBean();
		result.setOrderId(orderId);
		result.setAmount(refundAmount);
		result.setTradeWay(DictConstants.DICT_TRADEWAY_ALIPAY);
		result.setTradeType(DictConstants.DICT_TRADETYPE_REFUND);
		result.setTradeStatus(DictConstant.DICT_TRADESTATUS_FAIL);
		result.setTradeTime(new Date());
		
		String hint = null;
		StringBuilder sbError = new StringBuilder();
		try {
			hint = new StringBuilder(orderId).append("退款").toString();
			sbError.append(hint).append("失败：");
			
			StringBuilder sb = new StringBuilder();
			sb.append("{")
			  .append("\"out_trade_no\":\"").append(orderId).append("\"")
			  .append(",\"refund_amount\":").append(refundAmount)
			  .append("}")
			  ;
			
			logger.info(new StringBuilder(hint).append("请求：").append(sb));
			AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
			request.setBizContent(sb.toString());
			AlipayTradeRefundResponse resp = getAlipayClient(AlipayAccountUtil.getAccount(serviceType)).execute(request);
			logger.info(new StringBuilder(hint).append("返回：").append(resp.getBody()));
			
			if("10000".equals(resp.getCode())){
				result.setOutTradeId(resp.getTradeNo());
				result.setTradeStatus(DictConstant.DICT_TRADESTATUS_SUCCESS);
				result.setAmount(Double.valueOf(resp.getRefundFee()));
				result.setTradeTime(resp.getGmtRefundPay());
			}else{
				result.setTradeResult(resp.getSubCode()+resp.getSubMsg());	
			}
		} catch (AlipayApiException e) {
			logger.error(sbError+e.getMessage());
			result.setTradeResult(e.getErrCode()+e.getErrMsg());	
		} catch (Exception e) {
			logger.error(sbError, e);
			result.setTradeResult(e.getMessage());
		}
		return result;
	}
	
	/**
	 * 查询退款结果
	 * @param orderId
	 * 修改历史：
	 */
	public static TradeResultBean queryRefund(String orderId, String serviceType){
		TradeResultBean result = null;
		String hint = null;
		StringBuilder sbError = new StringBuilder();
		
		try {
			hint = new StringBuilder(orderId).append("查询退款结果").toString();
			sbError.append(hint).append("失败：");
			
			StringBuilder sb = new StringBuilder();
			sb.append("{")
			  .append("\"out_trade_no\":\"").append(orderId).append("\"")
			  .append(", \"out_request_no\":\"").append(orderId).append("\"")
			  .append("}")
			  ;
			
			logger.info(new StringBuilder(hint).append("请求：").append(sb));
			AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();
			request.setBizContent(sb.toString());
			AlipayTradeFastpayRefundQueryResponse resp = getAlipayClient(AlipayAccountUtil.getAccount(serviceType)).execute(request);
			logger.info(new StringBuilder(hint).append("返回：").append(resp.getBody()));
			
			if("10000".equals(resp.getCode())
					&& NumberUtil.isNumber(resp.getRefundAmount())){
				result = new TradeResultBean();
				result.setOrderId(resp.getOutTradeNo());
				result.setOutTradeId(resp.getTradeNo());
				result.setAmount(Double.valueOf(resp.getRefundAmount()));
				result.setTradeWay(DictConstants.DICT_TRADEWAY_ALIPAY);
				result.setTradeType(DictConstants.DICT_TRADETYPE_REFUND);
				result.setTradeStatus(DictConstant.DICT_TRADESTATUS_SUCCESS);
				result.setTradeTime(new Date());
			}
		} catch (Exception e) {
			logger.error(sbError, e);
			throw new ProxyException("查询退款结果失败");
		}
		return result;
	}
	
	/**
	 * 解析异步通知结果
	 * @param xml
	 * @return
	 * 修改历史：
	 */
	public static TradeResultBean parseSyn(Map<String, String> params, String serviceType){
		//验证签名
		try {
			AlipayAccountBean account = AlipayAccountUtil.getAccount(serviceType);
			if(!AlipaySignature.rsaCheckV1(params, account.getAlipayPublicKey(), "UTF-8",account.getSignType())){
				throw new ProxyException("无效签名");
			}
		} catch (Exception e) {
			throw new ProxyException("验证签名失败");
		}

		//封装
		TradeResultBean result = null;
		String tradeStatus = params.get("trade_status");
		String refundStatus = params.get("refund_status");
		
		if(StringUtil.isBlankOrNull(refundStatus)
				&& (TRADE_FINISHED.equals(tradeStatus) 
						|| TRADE_SUCCESS.equals(tradeStatus))){
			//支付成功
			result = new TradeResultBean();
			result.setOrderId(params.get("out_trade_no"));
			result.setOutTradeId(params.get("trade_no"));
			result.setAmount(Double.valueOf(params.get("total_amount")));
			result.setTradeWay(DictConstants.DICT_TRADEWAY_ALIPAY);
			result.setTradeType(DictConstants.DICT_TRADETYPE_PAY);
			result.setTradeStatus(DictConstant.DICT_TRADESTATUS_SUCCESS);
			result.setTradeTime(DateUtil.parseDateTime(params.get("gmt_payment")));
		}else if(REFUND_SUCCESS.equals(refundStatus)){
			//退款成功
			result = new TradeResultBean();
			result.setOrderId(params.get("out_trade_no"));
			result.setOutTradeId(params.get("trade_no"));
			result.setAmount(Double.valueOf(params.get("total_fee")));
			result.setTradeWay(DictConstants.DICT_TRADEWAY_ALIPAY);
			result.setTradeType(DictConstants.DICT_TRADETYPE_REFUND);
			result.setTradeStatus(DictConstant.DICT_TRADESTATUS_SUCCESS);
			result.setTradeTime(DateUtil.parseDateTime(params.get("gmt_refund")));
		}

		return result;
	}
}
