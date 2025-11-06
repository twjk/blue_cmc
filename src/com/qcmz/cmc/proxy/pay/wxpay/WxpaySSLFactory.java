package com.qcmz.cmc.proxy.pay.wxpay;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.apache.log4j.Logger;

import com.qcmz.cmc.entity.CmcBWxpayaccount;
import com.qcmz.framework.exception.ProxyException;

public class WxpaySSLFactory {
	private static Logger logger = Logger.getLogger(WxpaySSLFactory.class);
	private static Map<String, SSLSocketFactory> sslMap = new HashMap<String, SSLSocketFactory>();
	
	/**
	 * @return
	 * 修改历史：
	 */
	public static SSLSocketFactory getSSLFactory(CmcBWxpayaccount account) {
		SSLSocketFactory sslFactory = sslMap.get(account.getMchid());
		if(sslFactory==null){
			try {
				char[] arrMchid = account.getMchid().toCharArray();
				
				//加载证书
//				InputStream is = WxpayProxy.class.getClassLoader().getResourceAsStream(account.getCert());
				InputStream is = new FileInputStream(account.getCert());
				KeyStore ks = KeyStore.getInstance("PKCS12");
				try {
					ks.load(is, arrMchid);
				} finally{
					is.close();
				}
				KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
				kmf.init(ks, arrMchid);
				
				//设置受信任的服务端证书库
				TrustManager[] tm = { new WxpayTrustManager() };
				
				//初始化安全套接字
				SSLContext sslContext = SSLContext.getInstance("TLSv1");
				sslContext.init(kmf.getKeyManagers(), tm, null);
				
				//构造安全socket工厂
				sslFactory = sslContext.getSocketFactory();
				
				sslMap.put(account.getMchid(), sslFactory);
			} catch (Exception e) {
				logger.error("加载证书失败", e);
				throw new ProxyException("加载证书失败");
			}
		}
		return sslFactory;
	}
}
