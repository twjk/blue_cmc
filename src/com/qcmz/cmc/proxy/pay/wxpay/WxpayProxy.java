package com.qcmz.cmc.proxy.pay.wxpay;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import javax.net.ssl.HttpsURLConnection;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.cache.WxpayAccountMap;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.entity.CmcBWxpayaccount;
import com.qcmz.cmc.vo.PrepayBean;
import com.qcmz.cmc.vo.TradeResultBean;
import com.qcmz.cmc.vo.TransferBean;
import com.qcmz.cmc.ws.provide.vo.PrepayWxpayBean;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.exception.ProxyException;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.DoubleUtil;
import com.qcmz.framework.util.IPUtil;
import com.qcmz.framework.util.SecretUtil;
import com.qcmz.framework.util.StringUtil;

/**
 * 类说明：微信支付
 * 修改历史：
 */
@Component
public class WxpayProxy{
	@Autowired
	private WxpayAccountMap wxpayAccountMap;
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 统一下单地址
	 */
	private static String URL_UNIFIEDORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	/**
	 * 查询订单地址
	 */
	private static String URL_QUERYORDER = "https://api.mch.weixin.qq.com/pay/orderquery";
	/**
	 * 退款地址
	 */
	private static String URL_REFUND = "https://api.mch.weixin.qq.com/secapi/pay/refund";
	/**
	 * 查询退款地址
	 */
	private static String URL_QUERYREFUND = "https://api.mch.weixin.qq.com/pay/refundquery";
	/**
	 * 企业付款
	 */
	private static String URL_TRANSFERS = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
	/**
	 * 沙箱环境API接口验证
	 */
	private static String URL_GETSIGNKEY = "https://apitest.mch.weixin.qq.com/sandboxnew/pay/getsignkey";

	/**
	 * 返回代码-成功
	 */
	private static final String CODE_SUCCESS = "SUCCESS";
	/**
	 * 扩展字段默认值
	 */
	private static final String VALUE_PKG = "Sign=WXPay";
	
	/**
	 * 支付前处理，统一下单
	 * https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=9_1
	 * @param preBean
	 * @return 失败抛出ProxyException
	 * 修改历史：
	 */
	public PrepayWxpayBean prePay(PrepayBean preBean){
		PrepayWxpayBean result = null;
		String prepayId = "";
		String hint = null;
		StringBuilder sbError = new StringBuilder();
		
		try {
			hint = new StringBuilder(preBean.getOrderId()).append("统一下单").toString();
			sbError.append(hint).append("失败：");
			
			CmcBWxpayaccount account = wxpayAccountMap.getAccount(preBean.getServiceType(), preBean.getPlatform(), preBean.getSubServiceType());
			String appid = account.getAppid();
			String mchid = account.getMchid();
			String apikey = account.getApikey();
			String noncestr = getNoncestr();
			
			Map<String, String> map = new TreeMap<String, String>();
			map.put("appid", appid);
			map.put("mch_id", mchid);
			map.put("nonce_str", noncestr);						//随机字符串，不长于32位
			map.put("body", preBean.getOrderDesc());			//商品描述
			map.put("out_trade_no", preBean.getOrderId());		//商户系统内部的订单号,32个字符内、可包含字母
			map.put("total_fee", toFen(preBean.getAmount()));	//订单总金额，单位为分
			map.put("spbill_create_ip", IPUtil.getLocalIp());	//用户端实际ip
			map.put("notify_url", account.getNotifyurl());		//接收微信支付异步通知回调地址
			map.put("trade_type", account.getTradetype());		//支付类型
			if(StringUtil.notBlankAndNull(preBean.getSubServiceType())){
				map.put("attach", preBean.getSubServiceType());		//附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
			}
			if(StringUtil.notBlankAndNull(preBean.getOpenid())){
				map.put("openid", preBean.getOpenid());			//trade_type=JSAPI，此参数必传
			}
			map.put("sign", getSign(map, apikey));
			
			Document doc = DocumentHelper.createDocument();
			Element root = doc.addElement("xml");
			for (String key : map.keySet()) {
				root.addElement(key).addText(map.get(key));
			}			
			
			String req = doc.getRootElement().asXML();
			
			logger.info(new StringBuilder(hint).append("请求：").append(req.replace("\n", "")));
			String resp = post(URL_UNIFIEDORDER, req);
			logger.info(new StringBuilder(hint).append("返回：").append(resp.replace("\n", "")));
			
			doc = DocumentHelper.parseText(resp);
			root = doc.getRootElement();
			
			String returnCode = root.elementText("return_code");
			if(!CODE_SUCCESS.equals(returnCode)){
				sbError.append(returnCode).append("-").append(root.elementText("return_msg"));
			}else {
				String resultCode = root.elementText("result_code");
				if(!CODE_SUCCESS.equals(resultCode)){
					sbError.append(root.elementText("err_code")).append("-").append(root.elementText("err_code_des"));
				}else{
					prepayId = root.elementText("prepay_id");	//预支付交易会话标识，有效期为2小时
					result = new PrepayWxpayBean();
					result.setAppid(appid);
					result.setPartnerid(mchid);
					result.setPrepayid(prepayId);
					result.setPkg(VALUE_PKG);
					result.setNoncestr(noncestr);
					result.setTimestamp(String.valueOf(new Date().getTime()/1000));
					
					Map<String, String> nmap = new TreeMap<String, String>();
					if("JSAPI".equals(account.getTradetype())){
						nmap.put("appId", result.getAppid());
						nmap.put("nonceStr", result.getNoncestr());
						nmap.put("package", "prepay_id="+result.getPrepayid());
						nmap.put("signType", "MD5");
						nmap.put("timeStamp", result.getTimestamp());
					}else{
						nmap.put("appid", result.getAppid());
						nmap.put("partnerid", result.getPartnerid());
						nmap.put("prepayid", result.getPrepayid());
						nmap.put("package", result.getPkg());
						nmap.put("noncestr", result.getNoncestr());
						nmap.put("timestamp", result.getTimestamp());
					}
					
					result.setSign(getSign(nmap, apikey));
				}
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
	 * @return 
	 * 修改历史：
	 */
	public TradeResultBean queryPay(String orderId, String serviceType, String subServiceType, String platform){
		TradeResultBean result = null;
		String hint = null;
		StringBuilder sbError = new StringBuilder();
		try {
			
			hint = new StringBuilder(orderId).append("查询支付结果").toString();
			sbError.append(hint).append("失败：");
			
			CmcBWxpayaccount account = wxpayAccountMap.getAccount(serviceType, platform, subServiceType);
			String appid = account.getAppid();
			String mchid = account.getMchid();
			String apikey = account.getApikey();
			String noncestr = getNoncestr();
			
			Map<String, String> map = new TreeMap<String, String>();
			map.put("appid", appid);
			map.put("mch_id", mchid);
			map.put("nonce_str", noncestr);			//随机字符串，不长于32位
			map.put("out_trade_no",orderId);		//商户系统内部的订单号,32个字符内、可包含字母
			map.put("sign", getSign(map, apikey));
			
			Document doc = DocumentHelper.createDocument();
			Element root = doc.addElement("xml");
			for (String key : map.keySet()) {
				root.addElement(key).addText(map.get(key));
			}			
			String req = doc.getRootElement().asXML();
			
			logger.info(new StringBuilder(hint).append("请求：").append(req.replace("\n", "")));
			String resp = post(URL_QUERYORDER, req);
			logger.info(new StringBuilder(hint).append("返回：").append(resp.replace("\n", "")));
			
			doc = DocumentHelper.parseText(resp);
			root = doc.getRootElement();
			
			if(CODE_SUCCESS.equals(root.elementText("return_code"))
					&& CODE_SUCCESS.equals(root.elementText("result_code"))
					&& CODE_SUCCESS.equals(root.elementText("trade_state"))){
				result = new TradeResultBean();
				result.setOrderId(root.elementTextTrim("out_trade_no"));
				result.setOutTradeId(root.elementTextTrim("transaction_id"));
				result.setAmount(DoubleUtil.movePointLeft(root.elementTextTrim("total_fee"), 2));
				result.setTradeWay(DictConstants.DICT_TRADEWAY_WXPAY);
				result.setTradeType(DictConstants.DICT_TRADETYPE_PAY);
				result.setTradeStatus(DictConstant.DICT_TRADESTATUS_SUCCESS);
				result.setTradeTime(DateUtil.parseDateTime2(root.elementTextTrim("time_end")));
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
	 * 修改历史：
	 */
	public TradeResultBean refund(String orderId, Double refundAmount, String serviceType, String subServiceType, String platform){
		TradeResultBean result = new TradeResultBean();
		result.setOrderId(orderId);
		result.setAmount(refundAmount);
		result.setTradeWay(DictConstants.DICT_TRADEWAY_WXPAY);
		result.setTradeType(DictConstants.DICT_TRADETYPE_REFUND);
		result.setTradeStatus(DictConstant.DICT_TRADESTATUS_FAIL);
		result.setTradeTime(new Date());
		
		String hint = null;
		StringBuilder sbError = new StringBuilder();
		try {
			
			hint = new StringBuilder(orderId).append("退款").toString();
			sbError.append(hint).append("失败：");
			
			CmcBWxpayaccount account = wxpayAccountMap.getAccount(serviceType, platform, subServiceType);
			String appid = account.getAppid();
			String mchid = account.getMchid();
			String apikey = account.getApikey();
			String noncestr = getNoncestr();
			
			Map<String, String> map = new TreeMap<String, String>();
			map.put("appid", appid);
			map.put("mch_id", mchid);
			map.put("nonce_str", noncestr);
			map.put("out_trade_no", orderId);		//商户系统内部的订单号,32个字符内、可包含字母
			map.put("out_refund_no", orderId);		//退款单号，商户系统内部唯一，同一退款单号多次请求只退一笔
			map.put("total_fee", toFen(refundAmount));		//订单总金额，单位为分
			map.put("refund_fee",toFen(refundAmount));		//退款总金额
			map.put("op_user_id", mchid);		//操作员帐号, 默认为商户号
			map.put("sign", getSign(map, apikey));
			
			Document doc = DocumentHelper.createDocument();
			Element root = doc.addElement("xml");
			for (String key : map.keySet()) {
				root.addElement(key).addText(map.get(key));
			}			
			String req = doc.getRootElement().asXML();
			
			logger.info(new StringBuilder(hint).append("请求：").append(req.replace("\n", "")));
			String resp = postSSL(URL_REFUND, req, account);
			logger.info(new StringBuilder(hint).append("返回：").append(resp.replace("\n", "")));
			
			doc = DocumentHelper.parseText(resp);
			root = doc.getRootElement();
			
			String returnCode = root.elementTextTrim("return_code");
			if(!CODE_SUCCESS.equals(returnCode)){
				result.setTradeStatus(DictConstant.DICT_TRADESTATUS_FAIL);
				result.setTradeResult(returnCode+root.elementTextTrim("return_msg"));
			}else if(!CODE_SUCCESS.equals(root.elementTextTrim("result_code"))){
				result.setTradeStatus(DictConstant.DICT_TRADESTATUS_FAIL);
				result.setTradeResult(root.elementTextTrim("err_code")+root.elementTextTrim("err_code_des"));
			}else{
				result.setOutTradeId(root.elementTextTrim("refund_id"));
				result.setTradeStatus(DictConstant.DICT_TRADESTATUS_SUCCESS);
				result.setAmount(DoubleUtil.movePointLeft(root.elementTextTrim("refund_fee"), 2));
			}
		} catch (Exception e) {
			logger.error(sbError, e);
			result.setTradeResult(e.getMessage());
		}
		
		return result;
	}
	
	/**
	 * 查询退款结果
	 * @param orderId
	 * @return 
	 * 修改历史：
	 */
	public TradeResultBean queryRefund(String orderId, String serviceType, String subServiceType, String platform){
		TradeResultBean result = null;
		String hint = null;
		StringBuilder sbError = new StringBuilder();
		
		try {
			
			hint = new StringBuilder(orderId).append("查询退款结果").toString();
			sbError.append(hint).append("失败：");
			
			CmcBWxpayaccount account = wxpayAccountMap.getAccount(serviceType, platform, subServiceType);
			String appid = account.getAppid();
			String mchid = account.getMchid();
			String apikey = account.getApikey();
			String noncestr = getNoncestr();
			
			Map<String, String> map = new TreeMap<String, String>();
			map.put("appid", appid);
			map.put("mch_id", mchid);
			map.put("nonce_str", noncestr);			//随机字符串，不长于32位
			map.put("out_refund_no",orderId);		//商户侧传给微信的退款单号
			map.put("sign", getSign(map, apikey));
			
			Document doc = DocumentHelper.createDocument();
			Element root = doc.addElement("xml");
			for (String key : map.keySet()) {
				root.addElement(key).addText(map.get(key));
			}			
			String req = doc.getRootElement().asXML();
			
			logger.info(new StringBuilder(hint).append("请求：").append(req.replace("\n", "")));
			String resp = post(URL_QUERYREFUND, req);
			logger.info(new StringBuilder(hint).append("返回：").append(resp.replace("\n", "")));
			
			doc = DocumentHelper.parseText(resp);
			root = doc.getRootElement();
			
			if(CODE_SUCCESS.equals(root.elementText("return_code"))
					&& CODE_SUCCESS.equals(root.elementText("result_code"))
					&& CODE_SUCCESS.equals(root.elementText("refund_status_$n"))){
				result = new TradeResultBean();
				result.setOrderId(root.elementTextTrim("out_trade_no"));
				result.setOutTradeId(root.elementTextTrim("refund_id_$n"));
				result.setAmount(Double.valueOf(root.elementTextTrim("refund_fee_$n")));
				result.setTradeWay(DictConstants.DICT_TRADEWAY_WXPAY);
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
	 * 企业付款
	 * https://pay.weixin.qq.com/wiki/doc/api/tools/mch_pay.php?chapter=14_1
	 * @param TransferBean
	 * @return
	 * 修改历史：
	 */
	public TradeResultBean transfer(TransferBean transferBean){
		TradeResultBean result = new TradeResultBean();
		String hint = null;
		StringBuilder sbError = new StringBuilder();
		String errCode = null;
		String errDesc = null;
		
		try {
			hint = new StringBuilder(transferBean.getOrderId()).append("企业付款").toString();
			sbError.append(hint).append("失败：");
			
			CmcBWxpayaccount account = wxpayAccountMap.getAccount(transferBean.getServiceType(),  transferBean.getPlatform(), transferBean.getSubServiceType());
			String appid = account.getAppid();
			String mchid = account.getMchid();
			String apikey = account.getApikey();
			String noncestr = getNoncestr();
			
			Map<String, String> map = new TreeMap<String, String>();
			map.put("mch_appid", appid);
			map.put("mchid", mchid);
			map.put("nonce_str", noncestr);						//随机字符串，不长于32位
			map.put("partner_trade_no", transferBean.getOrderId());		//商户系统内部的订单号,32个字符内、可包含字母
			map.put("openid", transferBean.getOpenid());				//商户appid下，某用户的openid
			map.put("check_name", "NO_CHECK");				//校验用户姓名选项,NO_CHECK/FORCE_CHECK
//			map.put("re_user_name", "NO_CHECK");			//收款用户真实姓名，配合check_name使用
			
			map.put("amount", toFen(transferBean.getAmount()));	//企业付款金额，单位为分
			map.put("desc", transferBean.getOrderDesc());		//企业付款操作说明信息。必填
			map.put("spbill_create_ip", IPUtil.getLocalIp());	//用户端实际ip
			map.put("sign", getSign(map, apikey));
			
			
			Document doc = DocumentHelper.createDocument();
			Element root = doc.addElement("xml");
			for (String key : map.keySet()) {
				root.addElement(key).addText(map.get(key));
			}			
			
			String req = doc.getRootElement().asXML();
			
			logger.info(new StringBuilder(hint).append("请求：").append(req.replace("\n", "")));
			String resp = postSSL(URL_TRANSFERS, req, account);
			logger.info(new StringBuilder(hint).append("返回：").append(resp.replace("\n", "")));
			
			doc = DocumentHelper.parseText(resp);
			root = doc.getRootElement();
			
			String returnCode = root.elementText("return_code");
			if(!CODE_SUCCESS.equals(returnCode)){
				errDesc = root.elementTextTrim("return_msg");
				sbError.append(returnCode).append("-").append(errDesc);
				
				result.setTradeStatus(DictConstant.DICT_TRADESTATUS_FAIL);
				result.setTradeResult(returnCode+errDesc);
			}else {
				String resultCode = root.elementText("result_code");
				if(!CODE_SUCCESS.equals(resultCode)){
					errCode = root.elementTextTrim("err_code");
					errDesc = root.elementTextTrim("err_code_des");
					sbError.append(errCode).append("-").append(errDesc);
					
					result.setTradeStatus(DictConstant.DICT_TRADESTATUS_FAIL);
					result.setTradeResult(errCode+errDesc);
				}else{
					result.setTradeWay(DictConstants.DICT_TRADEWAY_WXPAY);
					result.setTradeType(DictConstants.DICT_TRADETYPE_TRANSFER);
					result.setTradeStatus(DictConstant.DICT_TRADESTATUS_SUCCESS);
					result.setOrderId(root.elementTextTrim("partner_trade_no"));
					result.setOutTradeId(root.elementTextTrim("payment_no"));
					result.setTradeTime(DateUtil.parseDateTime(root.elementTextTrim("payment_time")));
				}
			}
			if(DictConstant.DICT_TRADESTATUS_FAIL.equals(result.getTradeStatus())){
				logger.error(sbError);
			}
		} catch (Exception e) {
			result.setTradeStatus(DictConstant.DICT_TRADESTATUS_FAIL);
			result.setTradeResult("系统错误");
			logger.error(sbError, e);
		}
		
		return result;
	}
	
	/**
	 * 沙箱环境API接口验证
	 * @return 
	 * 修改历史：
	 */
	public void getSignKey(){
		String hint = "API接口验证";
		StringBuilder sbError = new StringBuilder();
		try {
			CmcBWxpayaccount account = wxpayAccountMap.getAccount(DictConstants.DICT_SERVICETYPE_VOICETRANS, DictConstants.DICT_PLATFORM_IOS, DictConstants.DICT_SUBSERVICETYPE_TRANSPIC);
			String mchid = account.getMchid();
			String apikey = account.getApikey();
			String noncestr = getNoncestr();
			
			Map<String, String> map = new TreeMap<String, String>();
			map.put("mch_id", mchid);
			map.put("nonce_str", noncestr);			//随机字符串，不长于32位
			map.put("sign", getSign(map, apikey));
			
			Document doc = DocumentHelper.createDocument();
			Element root = doc.addElement("xml");
			for (String key : map.keySet()) {
				root.addElement(key).addText(map.get(key));
			}			
			String req = doc.getRootElement().asXML();
			
			logger.info(new StringBuilder(hint).append("请求：").append(req.replace("\n", "")));
			String resp = post(URL_GETSIGNKEY, req);
			logger.info(new StringBuilder(hint).append("返回：").append(resp.replace("\n", "")));
			
		} catch (Exception e) {
			logger.error(sbError, e);
		}
	}
	
	/**
	 * 解析异步通知结果
	 * @param xml
	 * @return
	 * 修改历史：
	 */
	public TradeResultBean parseSyn(String xml, String serviceType, String subServiceType, String platform){
		TradeResultBean result = null;
		try {
			Document doc = DocumentHelper.parseText(xml);
			Element root = doc.getRootElement();
			if(CODE_SUCCESS.equals(root.elementText("return_code"))){
				String apikey = wxpayAccountMap.getAccount(serviceType, platform, subServiceType).getApikey();
				if(!verifySynSign(root, apikey)){
					throw new ProxyException("无效签名");
				}
				
				result = new TradeResultBean();
				result.setOrderId(root.elementTextTrim("out_trade_no"));
				result.setTradeWay(DictConstants.DICT_TRADEWAY_WXPAY);
				result.setTradeType(DictConstants.DICT_TRADETYPE_PAY);
				result.setOutTradeId(root.elementTextTrim("transaction_id"));
				result.setAmount(DoubleUtil.movePointLeft(root.elementTextTrim("total_fee"), 2));
				result.setTradeTime(DateUtil.parseDateTime2(root.elementTextTrim("time_end")));
				
				if(CODE_SUCCESS.equals(root.elementText("result_code"))){
					result.setTradeStatus(DictConstant.DICT_TRADESTATUS_SUCCESS);
				}else{
					result.setTradeStatus(DictConstant.DICT_TRADESTATUS_FAIL);
					result.setTradeResult(root.elementTextTrim("err_code")+root.elementTextTrim("err_code_des"));
				}
			}
		} catch (ProxyException e) {
			throw e;
		}catch (Exception e) {
			logger.error("解析异步通知失败："+xml, e);
			throw new ProxyException("XML解析失败");
		}
		return result;
	}
	
	/**
	 * 获取签名
	 * 参数名ASCII码从小到大排序（字典序）
	 * 如果参数的值为空不参与签名
	 * 参数名区分大小写
	 * 签名验证：https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=20_1
	 * @param map
	 * 修改历史：
	 */
	private static String getSign(Map<String, String> map, String apikey){
		StringBuilder sb = new StringBuilder();
		String value = null;
		
		if(map.containsKey("sign")){
			map.remove("sign");
		}
		
		for (String key : map.keySet()) {
			value = map.get(key);
			if(StringUtil.isBlankOrNull(value)) continue;
			sb.append("&").append(key).append("=").append(value);
		}
		sb.deleteCharAt(0);
		sb.append("&key=").append(apikey);
		return SecretUtil.encryptByMd5(sb.toString());
	}
	
	/**
	 * 签名验证
	 * @param root
	 * @param proxyBean
	 * @return
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	private static boolean verifySynSign(Element root, String apikey){
		Map<String, String> map = new TreeMap<String, String>();
		
		Iterator<Element> it = root.elementIterator();
		Element element = null;
		while(it.hasNext()){
			element = it.next();
			map.put(element.getName(), element.getTextTrim());
		}
		String sign = map.get("sign");
		
		String newSign = getSign(map, apikey);
		
		return newSign.equals(sign);
	}
	
	/**
	 * 获取随机字符串
	 * @return
	 * 修改历史：
	 */
	private static String getNoncestr(){
		return SecretUtil.encryptByMd5(String.valueOf(new Random().nextInt(1000)));
	}
	
	private static String toFen(Double amount){
		Double fen = DoubleUtil.multiply(amount, 100D);
		return String.valueOf(fen.intValue());
	}
	
	private static String post(String url, String xml) throws Exception{
		URL destURL = new URL(url);	
		HttpURLConnection urlConn = (HttpURLConnection) destURL.openConnection();
	    urlConn.setDoOutput(true);
	    urlConn.setDoInput(true);
	    urlConn.setAllowUserInteraction(false);
	    urlConn.setUseCaches(false);
	    urlConn.setRequestMethod("POST");
	    OutputStream os = urlConn.getOutputStream(); 
    	os.write(xml.getBytes("utf-8"));
    	os.flush();
    	os.close();
	    
	    InputStreamReader reader = new InputStreamReader(urlConn.getInputStream(), "utf-8"); 
		StringBuilder sb = new StringBuilder(); 
		char[] buf = new char[1024]; 
		int count = 0; 
		try { 
		   while ((count = reader.read(buf)) != -1) { 
		   	sb.append(buf, 0, count); 
		   } 
		} finally { 
		   reader.close(); 
		} 
		return sb.toString();
	}
	
	private static String postSSL(String url, String xml, CmcBWxpayaccount account) throws Exception{
		URL destURL = new URL(url);	
		HttpsURLConnection urlConn = (HttpsURLConnection) destURL.openConnection();
		urlConn.setSSLSocketFactory(WxpaySSLFactory.getSSLFactory(account));
	    urlConn.setDoOutput(true);
	    urlConn.setDoInput(true);
	    urlConn.setAllowUserInteraction(false);
	    urlConn.setUseCaches(false);
	    urlConn.setRequestMethod("POST");
	    OutputStream os = urlConn.getOutputStream(); 
    	os.write(xml.getBytes("utf-8"));
    	os.flush();
    	os.close();
	    
	    InputStreamReader reader = new InputStreamReader(urlConn.getInputStream(), "utf-8"); 
		StringBuilder sb = new StringBuilder(); 
		char[] buf = new char[1024]; 
		int count = 0; 
		try { 
		   while ((count = reader.read(buf)) != -1) { 
		   	sb.append(buf, 0, count); 
		   } 
		} finally { 
		   reader.close(); 
		} 
		return sb.toString();
	}
}
