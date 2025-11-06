package com.qcmz.cmc.proxy.pay.wxpay;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class WxpayTrustManager implements X509TrustManager {

	/** 
	 * @param chain
	 * @param authType
	 * @throws CertificateException
	 * 修改历史：
	 */
	public void checkClientTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
	}

	/** 
	 * @param chain
	 * @param authType
	 * @throws CertificateException
	 * 修改历史：
	 */
	public void checkServerTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
	}

	/** 
	 * @return
	 * 修改历史：
	 */
	public X509Certificate[] getAcceptedIssuers() {
		return new java.security.cert.X509Certificate[0];
	}

}
