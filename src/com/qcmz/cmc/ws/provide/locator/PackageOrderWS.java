package com.qcmz.cmc.ws.provide.locator;

import org.apache.log4j.Logger;

import com.qcmz.cmc.ws.provide.vo.PackageOrderExchangedReq;
import com.qcmz.framework.ws.util.AuthUtil;
import com.qcmz.framework.ws.vo.Response;

public class PackageOrderWS {
	private static Logger logger = Logger.getLogger(PackageOrderWS.class);

	/**
	 * 保存套餐兑换信息
	 * @param orderId
	 * @param exchangeCode
	 * @param uid
	 * @param exchangeTime
	 * @return
	 */
	public static Response saveExchanged(String orderId, String exchangeCode, Long uid, Long exchangeTime){
		PackageOrderExchangedReq req = new PackageOrderExchangedReq();
		req.setAuthAccount(AuthUtil.AUTH_ACCOUNT);
		req.setAuthToken(AuthUtil.getAuthToken());
		req.getBean().setOrderId(orderId);
		req.getBean().setExchangeCode(exchangeCode);
		req.getBean().setUid(uid);
		req.getBean().setExchangeTime(exchangeTime);
		
		Response resp = null;
		try {
			resp = CmcWSLocator.getPackageOrderWS().saveExchanged(req);
		} catch (Exception e) {
			logger.error("保存套餐兑换结果失败", e);
			resp = new Response();
			resp.error(e.getMessage());
		}
		return resp;
	}
}