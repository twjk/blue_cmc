package com.qcmz.cmc.proxy.pay.alipay;

import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.vo.PrepayBean;
import com.qcmz.cmc.vo.TradeResultBean;
import com.qcmz.cmc.ws.provide.vo.PrepayAlipayBean;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.exception.ProxyException;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.HttpUtil;
import com.qcmz.framework.util.NumberUtil;
import com.qcmz.framework.util.StringUtil;

/**
 * 类说明：支付宝工具类
 * 修改历史：
 */
public class AlipayProxy{
	private static Logger logger = Logger.getLogger(AlipayProxy.class);
	
	/**
	 * 验证通知地址
	 */
	private static final String URL_NOTIFYVERIFY = "https://mapi.alipay.com/gateway.do?service=notify_verify";
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
			alipayClient = new DefaultAlipayClient(URL_GATEWAY, account.getAppid(), account.getAppPrivateKey(), "json", "UTF-8", account.getAlipayPublicKey());
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
	public static PrepayAlipayBean prePay1(PrepayBean preBean){
		PrepayAlipayBean result = null;
		String hint = null;
		StringBuilder sbError = new StringBuilder();
		try {
			hint = new StringBuilder(preBean.getOrderId()).append("支付预处理").toString();
			sbError.append(hint).append("失败：");
			
			AlipayAccountBean account = AlipayAccountUtil.getAccount(preBean.getServiceType());
			
			//除sign、sign_type两个参数，使用到的参数都需要签名
			StringBuilder sb = new StringBuilder()
				.append("service=\"mobile.securitypay.pay\"")				//接口名称,固定值
				.append("&partner=\"").append(account.getPartnerid()).append("\"")		//合作者身份ID。支付宝唯一用户号，以2088开头的16位纯数字组成
				.append("&_input_charset=\"utf-8\"")
				.append("&notify_url=\"").append(account.getNotifyUrl()).append("\"")	//服务器异步通知页面路径
				.append("&out_trade_no=\"").append(preBean.getOrderId()).append("\"")	//商户网站唯一订单号
				.append("&subject=\"").append(preBean.getOrderDesc()).append("\"")		//商品名称，最长128汉字
				.append("&payment_type=\"1\"")								//支付类型。默认值为：1（商品购买）
				.append("&seller_id=\"").append(account.getPartnerid()).append("\"")		//卖家支付宝账号或付宝唯一用户号
				.append("&total_fee=\"").append(preBean.getAmount()).append("\"")		//总金额，元，[0.01，100000000.00]，精确到小数点后两位
				.append("&body=\"").append(preBean.getOrderDesc()).append("\"")			//商品详情，最长512汉字
			;
			
			String sign = RSA.sign(sb.toString(), account.getAppPrivateKey(), account.getSignType(), "utf-8");
			if(StringUtil.notBlankAndNull(sign)){
				sb.append("&sign=\"").append(URLEncoder.encode(sign, "utf-8")).append("\"").append("&sign_type=\"").append(account.getSignType()).append("\"");
				result = new PrepayAlipayBean();
				result.setPayInfo(sb.toString());
			}else{
				sbError.append("签名失败");
			}
			
			if(result==null){
				logger.error(sbError);
			}
		} catch (Exception e) {
			logger.error(sbError, e);
		}
		
		if(result==null){
			throw new ProxyException();
		}
		return result;
	}
	
	/**
	 * 查询支付结果
	 * @param orderId
	 * 修改历史：
	 */
	public static TradeResultBean queryPay1(String orderId, String serviceType){
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
	public static TradeResultBean refund1(String orderId, String serviceType, Double refundAmount){
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
	public static TradeResultBean queryRefund1(String orderId, String serviceType){
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
	public static TradeResultBean parseSyn1(Map<String, String> params, String serviceType){
		String sign = params.get("sign");
		//验证签名
		if(!verifySynSign1(params, sign, serviceType)){
			throw new ProxyException("无效签名");
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
			result.setAmount(Double.valueOf(params.get("total_fee")));
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
	
	/**
	 * 获取签名
	 * 参数名ASCII码从小到大排序（字典序）
	 * 如果参数的值为空不参与签名
	 * 参数名区分大小写
	 * @param map
	 * 修改历史：
	 */
	private static boolean verifySynSign1(Map<String, String> params, String sign, String serviceType){
		boolean result = false;
		
		if(StringUtil.isBlankOrNull(sign)
				|| params==null || params.isEmpty()){
			return result;
		}
		
		Map<String, String> map = new TreeMap<String, String>();
		map.putAll(params);
		map.remove("sign");
		map.remove("sign_type");
		
		StringBuilder sb = new StringBuilder();
		String value = null;
		for (String key : map.keySet()) {
			value = map.get(key);
			if(StringUtil.isBlankOrNull(value)) continue;
			sb.append("&").append(key).append("=").append(value);
		}
		sb.deleteCharAt(0);
		
		AlipayAccountBean account = AlipayAccountUtil.getAccount(serviceType);
		
		boolean verify = RSA.verify(sb.toString(), sign, account.getPartnerPublicKey(), account.getSignType(), SystemConstants.ENCODING_UTF8);
		if(verify){
			//验证是否是支付宝发来的通知
			StringBuilder sbUrl = new StringBuilder(URL_NOTIFYVERIFY)
				.append("&partner=").append(account.getPartnerid())
				.append("&notify_id=").append(params.get("notify_id"))
			;
			
			try {
				result = "true".equalsIgnoreCase(HttpUtil.get(sbUrl.toString())); 
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return result;
	}
}
