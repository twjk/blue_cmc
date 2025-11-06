package com.qcmz.cmc.service.impl;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Locale;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.vo.TradeResultBean;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.exception.ProxyException;
import com.qcmz.framework.util.SecretUtil;
import com.qcmz.framework.util.StringUtil;

/**
 * 苹果支付
 */
public class ApplepayProxy {
	/**
	 * 验证服务器地址
	 */
	private static final String URL_VERIFY = "https://buy.itunes.apple.com/verifyReceipt";  
	/**
	 * 验证服务器地址-测试
	 */
	private static final String URL_VERIFY_SANDBOX = "https://sandbox.itunes.apple.com/verifyReceipt";
	
	private static Logger logger = Logger.getLogger(ApplepayProxy.class);
	private static Logger loggerSyn = Logger.getLogger("PaysynApple"); 
	
		
	/**
	 * 解析异步通知结果
	 * @param orderId
	 * @param userId
	 * @param receipt
	 * @return
	 * @exception ProxyException
	 */
	public static TradeResultBean parseSyn(String orderId, Long userId, String receipt){
		String verifyResult = verify(receipt, orderId);
		if(StringUtil.isBlankOrNull(verifyResult)){
			throw new ProxyException("二次验证失败");
		}
		
		VerifyResult vr = JSONObject.parseObject(verifyResult, VerifyResult.class);
		if(vr.getStatus()!=0){
			throw new ProxyException("二次验证失败");
		}
		
		TradeResultBean result = new TradeResultBean();
		result.setOrderId(orderId);
		result.setOutTradeId(vr.getReceipt().getTransaction_id());
		result.setIapId(vr.getReceipt().getProduct_id());
		result.setTradeWay(DictConstants.DICT_TRADEWAY_APPLEPAY);
		result.setTradeType(DictConstants.DICT_TRADETYPE_PAY);
		result.setTradeStatus(DictConstant.DICT_TRADESTATUS_SUCCESS);
		result.setTradeTime(new Date(Long.valueOf(vr.getReceipt().purchase_date_ms)));
		return result;
	}
	
	/**
	 * 向苹果服务器做二次验证
	 * {"receipt":{"original_purchase_date_pst":"2017-11-27 22:50:39 America/Los_Angeles", "purchase_date_ms":"1511851839025", "unique_identifier":"59d47741da6121408b8739c14215897e007c0d4e", "original_transaction_id":"1000000355357085", "bvrs":"1", "transaction_id":"1000000355357085", "quantity":"1", "unique_vendor_identifier":"12172F1C-5296-447B-B5F0-71D996B5281B", "item_id":"1318049258", "original_purchase_date":"2017-11-28 06:50:39 Etc/GMT", "product_id":"MeiSubscribeYearly", "purchase_date":"2017-11-28 06:50:39 Etc/GMT", "is_trial_period":"false", "purchase_date_pst":"2017-11-27 22:50:39 America/Los_Angeles", "bid":"cn.smart.MUZHI", "original_purchase_date_ms":"1511851839025"}, "status":0}
	 * @return
	 */
	private static String verify(String receipt, String orderId){
		try{
			SSLContext sc = SSLContext.getInstance("SSL");  
			sc.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new java.security.SecureRandom());
           
			URL console = new URL(getVerifyUrl(receipt));  
			HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();  
			conn.setSSLSocketFactory(sc.getSocketFactory());  
			conn.setHostnameVerifier(new TrustAnyHostnameVerifier());  
			conn.setRequestMethod("POST");  
			conn.setRequestProperty("content-type", "text/json");  
			conn.setRequestProperty("Proxy-Connection", "Keep-Alive");  
			conn.setDoInput(true);  
			conn.setDoOutput(true);  
			BufferedOutputStream hurlBufOus=new BufferedOutputStream(conn.getOutputStream());  
			
			String str= String.format(Locale.CHINA,"{\"receipt-data\":\"" + SecretUtil.encodeByBase64(receipt)+"\"}");  
			hurlBufOus.write(str.getBytes());  
			hurlBufOus.flush();  
                     
			InputStream is = conn.getInputStream();  
            BufferedReader reader=new BufferedReader(new InputStreamReader(is));  
            StringBuffer sb = new StringBuffer();
            String line = null;  
            while((line = reader.readLine()) != null){  
              sb.append(line);  
            }
            loggerSyn.info(new StringBuilder("SUCCESS|").append(orderId).append("|").append(receipt).append("|").append(sb));
            return sb.toString();
		}catch(Exception e){  
			loggerSyn.error(new StringBuilder("ERROR|").append(orderId).append("|").append(receipt).append("|").append(e.getMessage()));
			logger.error("二次验证失败：", e);
		}
		return null;
	}
	
	/**
	 * 根据原始收据返回苹果的验证地址
	 * Sandbox 测试单   Real 正式单
	 * @param receipt
	 * @return
	 */
	private static String getVerifyUrl(String receipt){
		String environment = "Real";
		try{  
            JSONObject obj = JSONObject.parseObject(receipt.replaceAll("\n\t", "").replaceAll("=", ":").replaceAll(";", ","));
            if(obj.containsKey("environment")){
            	environment=obj.getString("environment");  
            }  
        }catch(Exception e){
        	logger.error("解析Receipt失败", e);
        }  
		
		 String url = URL_VERIFY;
         if("Sandbox".equals(environment)){
      	   url = URL_VERIFY_SANDBOX;
         }
		
        return url;
	}
	
	private static class TrustAnyTrustManager implements X509TrustManager {
		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};  
        }  
    }
      
    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }  
    }
    
    protected static class VerifyResult{
    	private int status;
    	private Receipt receipt;
		public int getStatus() {
			return status;
		}
		public void setStatus(int status) {
			this.status = status;
		}
		public Receipt getReceipt() {
			return receipt;
		}
		public void setReceipt(Receipt receipt) {
			this.receipt = receipt;
		}
    }
    
    protected static class Receipt{
    	/**
    	 * 商品标识
    	 */
    	private String product_id;
    	/**
    	 * 交易标识
    	 */
    	private String transaction_id;
    	/**
    	 * 购买时间毫秒
    	 */
    	private String purchase_date_ms;
		public String getProduct_id() {
			return product_id;
		}
		public void setProduct_id(String product_id) {
			this.product_id = product_id;
		}
		public String getTransaction_id() {
			return transaction_id;
		}
		public void setTransaction_id(String transaction_id) {
			this.transaction_id = transaction_id;
		}
		public String getPurchase_date_ms() {
			return purchase_date_ms;
		}
		public void setPurchase_date_ms(String purchase_date_ms) {
			this.purchase_date_ms = purchase_date_ms;
		}
    }
}