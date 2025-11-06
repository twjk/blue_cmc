package com.qcmz.cmc.ws.provide.locator;

import org.apache.log4j.Logger;

import com.qcmz.cmc.ws.provide.vo.TransLibAddBean;
import com.qcmz.cmc.ws.provide.vo.TransLibAddReq;
import com.qcmz.framework.ws.util.AuthUtil;
import com.qcmz.framework.ws.vo.Response;

/**
 * 译库接口 
 */
public class TransLibWS {
	private static Logger logger = Logger.getLogger(TransLibWS.class);
	
	/**
	 * 译库类型-01译库
	 */
	public static final String DICT_LIBTYPE_LIB = "01";
	/**
	 * 译库类型-02日常用语
	 */
	public static final String DICT_LIBTYPE_DAILY = "02";
	/**
	 * 译库类型-03高频用语
	 */
	public static final String DICT_LIBTYPE_FREQUENT = "03";
	
	/**
	 * 添加译库
	 * @param bean
	 * @return
	 */
	public static boolean addTransLib(TransLibAddBean bean){
		try {
			TransLibAddReq req = new TransLibAddReq();
			req.setAuthAccount(AuthUtil.AUTH_ACCOUNT);
			req.setAuthToken(AuthUtil.getAuthToken());
			req.setBean(bean);
			
			Response resp = CmcWSLocator.getTransLibWS().addTransLib(req);
			if(!resp.successed()){
				logger.error("添加译库失败："+resp.toString());
			}
			return resp.successed();
		} catch (Exception e) {
			logger.error("添加译库失败", e);
		}
		return false;
	}
	
}
