package com.qcmz.cmc.ws.provide.locator;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.qcmz.cmc.ws.provide.vo.LangBean;
import com.qcmz.cmc.ws.provide.vo.LangReq;
import com.qcmz.cmc.ws.provide.vo.LangResp;
import com.qcmz.framework.ws.util.AuthUtil;

/**
 * 类说明：翻译配置
 * 修改历史：
 */
public class TransConfigWS {
	private static Logger logger = Logger.getLogger(TransConfigWS.class);
	
	/**
	 * 获取语言基础信息
	 * @param language 空则返回所有
	 * @return
	 * 修改历史：
	 */
	public static List<LangBean> findLang(String langType, String language){
		try {
			LangReq req = new LangReq();
			req.setAuthAccount(AuthUtil.AUTH_ACCOUNT);
			req.setAuthToken(AuthUtil.getAuthToken());
			req.setLanguage(language);
			req.setLangType(langType);
			
			LangResp resp = CmcWSLocator.getTransConfigWS().findLang(req);
			if(resp.successed()){
				return resp.getResult();
			}
			logger.error("获取语言列表失败："+resp.toString());
		} catch (Exception e) {
			logger.error("获取语言列表失败", e);
		}
		return new ArrayList<LangBean>();
	}
}
