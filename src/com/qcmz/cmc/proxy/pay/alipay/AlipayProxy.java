package com.qcmz.cmc.proxy.pay.alipay;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayFundTransUniTransferModel;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.Participant;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayFundTransUniTransferRequest;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayFundTransUniTransferResponse;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.qcmz.cmc.cache.AlipayAccountMap;
import com.qcmz.cmc.config.SystemConfig;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.entity.CmcBAlipayaccount;
import com.qcmz.cmc.vo.PrepayBean;
import com.qcmz.cmc.vo.TradeResultBean;
import com.qcmz.cmc.vo.TransferBean;
import com.qcmz.cmc.ws.provide.vo.AlipayOpenidBean;
import com.qcmz.cmc.ws.provide.vo.PrepayAlipayBean;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.exception.ProxyException;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.NumberUtil;
import com.qcmz.framework.util.StringUtil;

/**
 * 类说明：支付宝工具类
 * 修改历史：
 */
@Component
public class AlipayProxy{
	@Autowired
	private AlipayAccountMap alipayAccountMap;
	
	private Logger logger = Logger.getLogger(this.getClass());
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
	
	/**
	 * 返回代码-成功
	 */
	private static final String CODE_SUCCESS = "10000";
	private static final String FORMAT = "json";
	
	/**
	 * <appid, AlipayClient>
	 */
	private static Map<String, AlipayClient> clientMap = new HashMap<String, AlipayClient>();
	private AlipayClient getAlipayClient(String serviceType) throws AlipayApiException{
		CmcBAlipayaccount account = getAccount(serviceType);
		if(account==null) return null;
		
		AlipayClient alipayClient = clientMap.get(account.getAppid());
		if(alipayClient==null){
			CertAlipayRequest alipayConfig = new CertAlipayRequest();
	        alipayConfig.setPrivateKey(account.getPrivatekey());
	        alipayConfig.setServerUrl(URL_GATEWAY);
	        alipayConfig.setAppId(account.getAppid());
	        alipayConfig.setCharset(SystemConstants.ENCODING_UTF8);
	        alipayConfig.setSignType(account.getSigntype());
	        alipayConfig.setEncryptor("");
	        alipayConfig.setFormat(FORMAT);
	        alipayConfig.setCertPath(account.getApppubliccert());		//应用公钥证书文件路径，例如：/foo/appCertPublicKey_2019051064521003.crt
	        alipayConfig.setAlipayPublicCertPath(account.getAlipaypubliccert());	//支付宝公钥证书文件路径，例如：/foo/alipayCertPublicKey_RSA2.crt
	        alipayConfig.setRootCertPath(account.getRootcert());		//支付宝根证书文件路径，例如：/foo/alipayRootCert.crt

	        alipayClient = new DefaultAlipayClient(alipayConfig);
//	        alipayClient = new DefaultAlipayClient(URL_GATEWAY, account.getAppid(), account.getPrivatekey(), FORMAT, SystemConstants.ENCODING_UTF8, account.getPublickey(), account.getSigntype());
	        
	        clientMap.put(account.getAppid(), alipayClient);
		}

		return alipayClient;
	}
	
	public static void init(){
		clientMap.clear();
	}
	
	/**
	 * 获取支付宝账户，重新封装一下用于处理本地证书路径
	 * @param serviceType
	 * @return
	 */
	private CmcBAlipayaccount getAccount(String serviceType){
		CmcBAlipayaccount account = alipayAccountMap.getAccount(serviceType);
		if(SystemConfig.isDebug()){
			//本地测试
			if(StringUtil.notBlankAndNull(account.getApppubliccert())){
				account.setApppubliccert(account.getApppubliccert().replaceFirst("/mnt/cert/", "C:/test/cert/"));
			}
			if(StringUtil.notBlankAndNull(account.getAlipaypubliccert())){
				account.setAlipaypubliccert(account.getAlipaypubliccert().replaceFirst("/mnt/cert/", "C:/test/cert/"));
			}
			if(StringUtil.notBlankAndNull(account.getRootcert())){
				account.setRootcert(account.getRootcert().replaceFirst("/mnt/cert/", "C:/test/cert/"));
			}
		}
		return account;
	}
	
	/**
	 * 支付前处理，统一下单
	 * https://docs.open.alipay.com/api_1/alipay.trade.app.pay
	 * @param preBean
	 * @return 失败抛出ProxyException
	 * 修改历史：
	 */
	public PrepayAlipayBean prePay(PrepayBean preBean){
		PrepayAlipayBean result = null;
		try {
			
			CmcBAlipayaccount account = getAccount(preBean.getServiceType());
			
			//实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
			//SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
			AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
			model.setBody(preBean.getOrderDesc());		//商品详情
			model.setSubject(preBean.getOrderDesc());	//商品名称
			model.setOutTradeNo(preBean.getOrderId());	//商户网站唯一订单号
			model.setTimeoutExpress("30m");
			model.setTotalAmount(String.valueOf(preBean.getAmount()));	//总金额，元，[0.01，100000000.00]，精确到小数点后两位
			model.setProductCode("QUICK_MSECURITY_PAY");
			model.setPassbackParams(preBean.getSubServiceType());		//回传参数,会在异步通知时将该参数原样返回			
			
			AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
			request.setBizModel(model);
			request.setNotifyUrl(account.getNotifyurl());	//服务器异步通知页面路径
			
	        AlipayTradeAppPayResponse response = getAlipayClient(preBean.getServiceType()).sdkExecute(request);
	        
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
	 * https://opendocs.alipay.com/open/02e7gm
	 * @param orderId
	 * 修改历史：
	 */
	public TradeResultBean queryPay(String orderId, String serviceType){
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
			
			AlipayTradeQueryResponse resp = getAlipayClient(serviceType).certificateExecute(request);
			logger.info(new StringBuilder(hint).append("返回：").append(resp.getBody()));
			
			if(CODE_SUCCESS.equals(resp.getCode()) 
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
	public TradeResultBean refund(String orderId, String serviceType, Double refundAmount){
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
			AlipayTradeRefundResponse resp = getAlipayClient(serviceType).certificateExecute(request);
			logger.info(new StringBuilder(hint).append("返回：").append(resp.getBody()));
			
			if(CODE_SUCCESS.equals(resp.getCode())){
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
	public TradeResultBean queryRefund(String orderId, String serviceType){
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
			AlipayTradeFastpayRefundQueryResponse resp = getAlipayClient(serviceType).certificateExecute(request);
			logger.info(new StringBuilder(hint).append("返回：").append(resp.getBody()));
			
			if(CODE_SUCCESS.equals(resp.getCode())
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
	 * 转账到支付宝
	 * 接口：https://opendocs.alipay.com/open/62987723_alipay.fund.trans.uni.transfer
	 * 产品首页：https://open.alipay.com/api/detail?code=I1080300001000046143
	 * 开发首页：https://opendocs.alipay.com/open/repo-0038si
	 * @param TransferBean
	 * @return
	 * 修改历史：
	 */
	public TradeResultBean transfer(TransferBean transferBean){
		TradeResultBean result = new TradeResultBean();
		String hint = null;
		StringBuilder sbError = new StringBuilder();
		
		try {
			hint = new StringBuilder(transferBean.getOrderId()).append("转账到支付宝").toString();
			
	        Participant payeeInfo = new Participant();
	        payeeInfo.setIdentityType("ALIPAY_OPEN_ID");		//必填，收款方标识类型，ALIPAY_USER_ID 用户ID、ALIPAY_LOGON_ID 邮箱或手机号、ALIPAY_OPEN_ID openid
	        payeeInfo.setIdentity(transferBean.getOpenid());	//必填，收款方标识
//	        payeeInfo.setName("");			//可选，收款方真实姓名，当identity_type=ALIPAY_LOGON_ID必填
	        
	        AlipayFundTransUniTransferModel model = new AlipayFundTransUniTransferModel();
	        model.setOutBizNo(transferBean.getOrderId());		//必填，商家侧唯一订单号
	        model.setRemark(transferBean.getOrderDesc());		//可选，业务备注
	        model.setBusinessParams("{\"payer_show_name_use_alias\":\"true\"}");	//可选，是否在收款方账单中展示付款方别名
	        model.setBizScene("DIRECT_TRANSFER");				//固定值
	        model.setTransAmount(String.valueOf(transferBean.getAmount()));			//金额，元，范围[0.1,100000000]
	        model.setProductCode("TRANS_ACCOUNT_NO_PWD");		//销售产品码，固定值
	        model.setOrderTitle(transferBean.getOrderDesc());	//转账业务的标题，在支付宝账单里显示
	        model.setPayeeInfo(payeeInfo);

	        AlipayFundTransUniTransferRequest req = new AlipayFundTransUniTransferRequest();
	        req.setBizModel(model);
	        
	        logger.info(new StringBuilder(hint).append("请求：").append(req.toString()));
	        AlipayFundTransUniTransferResponse resp = getAlipayClient(transferBean.getServiceType()).certificateExecute(req);
	        logger.info(new StringBuilder(hint).append("返回：").append(resp.getBody()));
	        
	        if (resp.isSuccess()) {
	            result.setTradeWay(DictConstants.DICT_TRADEWAY_ALIPAY);
				result.setTradeType(DictConstants.DICT_TRADETYPE_TRANSFER);
				result.setTradeStatus(DictConstant.DICT_TRADESTATUS_SUCCESS);
				result.setOrderId(resp.getOutBizNo());
				result.setOutTradeId(resp.getOrderId());
				result.setTradeTime(DateUtil.parseDateTime(resp.getTransDate()));
	        } else {
	        	sbError.append(resp.getCode()).append("-").append(resp.getMsg());
	            result.setTradeStatus(DictConstant.DICT_TRADESTATUS_FAIL);
				result.setTradeResult(resp.getCode()+resp.getMsg()+"["+resp.getSubCode()+resp.getSubMsg()+"]");
	        }
		} catch (Exception e) {
			result.setTradeStatus(DictConstant.DICT_TRADESTATUS_FAIL);
			result.setTradeResult("系统错误");
			logger.error(sbError, e);
		}
		return result;
	}
	
	/**
	 * 获取openid
	 * https://opendocs.alipay.com/open/02ailc
	 *  使用openid开发接入指南：https://opendocs.alipay.com/pre-open/06z4jd
	 *  服务端接入（获取用户信息）：https://opendocs.alipay.com/open/218/01epuj
	 * 	方式1：通过用户授权获取 openid。
	 * 		     开发者调用 my.getAuthCode（获取用户授权码）获取授权码，
	 * 		     再调用 alipay.system.oauth.token（换取授权访问令牌接口），支付宝会在返回 access_token 的同时，返回 openid。
	 *  方式2：用户支付完成后获取 openid。用户支付完成，开发者可以从支付响应结果及回调通知中获取 openid。
	 *  方式3：通过付款码解码查询获取 openid。商家使用设备扫描用户的支付宝付款码获取付款码信息后，可以传入付款码码值获取 openid。
	 * @param authCode 授权码
	 * @param serviceType
	 * @return
	 * @exception ProxyException
	 */
	public AlipayOpenidBean getOpenid(String authCode, String serviceType){
		AlipayOpenidBean result = new AlipayOpenidBean();
		String hint = null;
		
		try {
			hint = new StringBuilder(serviceType).append(",").append(authCode).append("获取openid").toString();
			
			//alipay.system.oauth.token	换取授权访问令牌接口
			//https://opendocs.alipay.com/open/02ailc
			//{"alipay_system_oauth_token_response":{"access_token":"kuaijieB92961e0ba073412e81f5a83bae9c3D07","alipay_user_id":"20880042337779769247695240710307","auth_start":"2023-09-02 17:19:51","expires_in":1209600,"re_expires_in":15552000,"refresh_token":"kuaijieB12472bb0606049ceb0d678c4fed05X07","user_id":"2088302480631072"},"alipay_cert_sn":"4f16c8c920a6a0328f8e0850c2699d3e","sign":"DqbfTLxQTpxt4owUGPVLEdxBNjT1Y3ZNQGlsV3HlTLMJGkxe3DlMBw+lGyI9Y5L1uUcHlUhjW3Cq+17RcVIIx5/tp466u+/TdO+zP78reu2K8RWcK7KE656ESiO5qbvhDzrl1UucWn9RiyTZV5SENLx9qZbI3RGbYrwys9YC0KX3IIzyCQlLk5QqqwL28GWZBORpH5zBJ29EXXZpPnBj/qiY7Vq/zOFp5/DSyYjAr57Uu13rLKEVkv8KueDWBECnjVEUcYMZDY74A0+B4eLYOmC62N5IWTsncjNTdz9WhBBgea0S/oZkKZFUOj7Q8vJABMTsAP8ZKU+be1rtcet1ew=="}
			//带openid{"alipay_system_oauth_token_response":{"access_token":"kuaijieB92961e0ba073412e81f5a83bae9c3D07","alipay_user_id":"20880042337779769247695240710307","auth_start":"2023-09-02 17:19:51","expires_in":1209600,"re_expires_in":15552000,"refresh_token":"kuaijieB12472bb0606049ceb0d678c4fed05X07","user_id":"2088302480631072","open_id":"007VwJuT4M_k-WvFjHrrTFDw7rCN6cGLIwePkHo0lUGKWgd"},"alipay_cert_sn":"4f16c8c920a6a0328f8e0850c2699d3e","sign":"EEQfTyXa8s8AHJxsq4fMPAXRMDMsGZ4CcvcIhzECTilW6fzQsEbWbrODltacWPsgjxdtRs11d1fiDqZ11SI/ctdu2dc7c2p3XK8UO0C7tkWwWuH7hcmpRYhENpMeCnmwcpa3dVobP3zbrhjEnaoF8uxIQTyeLE2jhCQoIddswZI+zE91bNf3fYNCdyl9XKjaTxHDLq8isYzZClGEdTWx8dfxxKkKCxCV4kJsy7qBXQ3QED7wsKzcSYNiRxB7y3ntUouBCtpT+zb14LdYYuWefWMRr8ab8pWTEBGaZ4lv5y6ZnFcmZRfCIc6sc3t4VqftLLBr5W8YGeK6sieZPRXhMg=="}
			AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
			request.setGrantType("authorization_code");
			request.setCode(authCode);
			
	        logger.info(hint);
	        AlipaySystemOauthTokenResponse resp = getAlipayClient(serviceType).certificateExecute(request);
	        logger.info(new StringBuilder(hint).append("返回：").append(resp.getBody()));
	        
	        if (resp.isSuccess()) {
				result.setOpenid(resp.getOpenId());
	        } else {
	        	logger.info(new StringBuilder(hint).append("失败：").append(resp.getCode()).append(resp.getMsg()));
	        	throw new ProxyException();
	        }
		} catch (ProxyException e) {
			throw e;
		} catch (Exception e) {
			logger.error(new StringBuilder(hint).append("失败：").toString(), e);
			throw new ProxyException();
		}
		return result;
	}
	
	/**
	 * 解析异步通知结果
	 * https://docs.open.alipay.com/204/105301/
	 * @param xml
	 * @return
	 * 修改历史：
	 */
	public TradeResultBean parseSyn(Map<String, String> params, String serviceType){
		//验证签名
		try {
			CmcBAlipayaccount account = getAccount(serviceType);
//			if(!AlipaySignature.rsaCheckV1(params, account.getPublickey(), SystemConstants.ENCODING_UTF8, account.getSigntype())){
			if(!AlipaySignature.rsaCertCheckV1(params, account.getAlipaypubliccert(), SystemConstants.ENCODING_UTF8, account.getSigntype())){
				throw new ProxyException("无效签名");
			}
		} catch(ProxyException e){
			throw e;
		} catch (Exception e) {
			logger.error("验证签名失败：", e);
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
		}

		return result;
	}
	
	//alipay.user.info.share	支付宝会员授权信息查询接口
	//https://opendocs.alipay.com/open/02ailds
	//{"alipay_user_info_share_response":{"code":"10000","msg":"Success","avatar":"https:\/\/tfs.alipayobjects.com\/images\/partner\/AT8ymRRoZ3w0EAAAAAAAAAAAAADtl2AA","nick_name":"王东海","user_id":"2088302480631072"},"alipay_cert_sn":"4f16c8c920a6a0328f8e0850c2699d3e","sign":"VwTrDSXcLGg/j5zF/h6fjKBaYBOYBr+Z688sFN7RcFSkirziuJOIv7SA/Fde25QqHoNgY2t9474YJ98oNYitjC6oTn9xNOK1kvUsQ+ofuzKawu3Xhdqugc9L5xo38TeD3+sUvBULWv4Az1P0z7SjTgaQJL27RsGI/DXtx830wNLiVRPD1zLdcY/4lvDXcu5pkydlfjzTdFV8WzG7tgeQx20022YQHqKRP8tk1IXYT/5gkGKEb8b+UxLKGrPDxXnHc/WXWWqZZ4spDswy34FUEj30c66J5sbaKIxdsUNPXG/RTf/MQ8rXIKFteTOh8vczJ1MXe1vzzcoQVEFNUeATPA=="}
	//{"alipay_user_info_share_response":{"code":"10000","msg":"Success","avatar":"https:\/\/tfs.alipayobjects.com\/images\/partner\/AT8ymRRoZ3w0EAAAAAAAAAAAAADtl2AA","nick_name":"王东海","user_id":"2088302480631072","open_id":"007VwJuT4M_k-WvFjHrrTFDw7rCN6cGLIwePkHo0lUGKWgd"},"alipay_cert_sn":"4f16c8c920a6a0328f8e0850c2699d3e","sign":"Wz1XkAT5EjWdBh4FyNGihOlh7hy0iWNqipiMpBwbssjyFI/rt27GXfP2qSe4l/3Qvgkgk9IEloNWnSW4S3zvGNQ2Z6cR8NLhDkp0jFFffRtS33D5N+FTZwzYyqk0P7SbMI0BXL3GVV8pHOAwxaKPtV+kFkGKByyp1d47yOALGhN+mq9/QMiOaI7caM5BomdRNJnP4r22vP0EgFiXbX1JS4JWohKKK3jng0Yfw1LcDn6i+uKZy/lJZU6WtGMgKIxRKEuOLrphzCwHVlRsUv6OqADDCsX5jd5R1jtcimXsSg5JjQ5FJO4TumeqSPBFI1S5JkMnnLGOlccqrI39cElOrg=="}
}