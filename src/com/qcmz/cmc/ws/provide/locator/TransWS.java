package com.qcmz.cmc.ws.provide.locator;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.qcmz.cmc.ws.provide.vo.TransResult;
import com.qcmz.cmc.ws.provide.vo.TransReq;
import com.qcmz.cmc.ws.provide.vo.TransResp;
import com.qcmz.framework.ws.util.AuthUtil;

/**
 * 类说明：翻译
 * 修改历史：
 */
public class TransWS {
	private static Logger logger = Logger.getLogger(TransWS.class);
	
	/**
	 * 翻译
	 * @param from 源语言
	 * @param to 目标语言
	 * @param src 原文
	 * @return 翻译结果明细
	 * 修改历史：
	 */
	public static List<TransResult> translate(String from, String to, String src){
		try {
			TransReq req = new TransReq();
			req.setAuthAccount(AuthUtil.AUTH_ACCOUNT);
			req.setAuthToken(AuthUtil.getAuthToken());
			req.getBean().setFrom(from);
			req.getBean().setTo(to);
			req.getBean().setSrc(src);
			
			TransResp resp = CmcWSLocator.getTransWS().translate(req);
			if(!resp.successed()){
				logger.error("翻译失败："+resp.toString());
			}
			return resp.getTrans_result();
		} catch (Exception e) {
			logger.error("翻译失败", e);
		}
		return new ArrayList<TransResult>();
	}
	
	/**
	 * 翻译
	 * @param from 源语言
	 * @param to 目标语言
	 * @param src 原文
	 * @return 翻译结果
	 * 修改历史：
	 */
	public static String trans(String from, String to, String src){
		try {
			List<TransResult> list = translate(from, to, src);
			if(list==null || list.isEmpty()){
				return src;
			}
			
			StringBuilder sb = new StringBuilder();
			int i = 0;
			for (TransResult bean : list) {
				i++;
				if(i>1){
					sb.append("\r\n");
				}
				sb.append(bean.getDst());
			}
			
			return sb.toString();
		} catch (Exception e) {
			logger.error("翻译失败", e);
		}
		return src;
	}
}
