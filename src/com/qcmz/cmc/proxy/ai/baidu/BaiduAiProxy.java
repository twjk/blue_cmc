package com.qcmz.cmc.proxy.ai.baidu;

import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

import org.apache.log4j.Logger;

import com.qcmz.cmc.config.SystemConfig;
import com.qcmz.cmc.proxy.ai.baidu.vo.BaiduAiChatFileReq;
import com.qcmz.cmc.proxy.ai.baidu.vo.BaiduAiChatFileResp;
import com.qcmz.framework.exception.ProxyException;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.FileUtil;
import com.qcmz.framework.util.HttpUtil;
import com.qcmz.framework.util.HttpsSSLFactoryUtil;
import com.qcmz.framework.util.JsonUtil;
import com.qcmz.framework.util.MailUtil;
import com.qcmz.framework.util.StringUtil;

/**
 * API列表：https://cloud.baidu.com/doc/WENXINWORKSHOP/s/Nlks5zkzu
 * Github：https://github.com/baidubce/bce-qianfan-sdk
 * 大模型价格：https://cloud.baidu.com/doc/qianfan-docs/s/Jm8r1826a
 */
public class BaiduAiProxy {
	/**
	 * API Key
	 * 控制台-安全认证-API Key
	 * https://cloud.baidu.com/doc/WENXINWORKSHOP/s/Um2wxbaps
	 */
//	private static String API_KEY = "bce-v3/ALTAK-VwbXdlgOlOtfNN9V2U28U/ed05e4adaef72cee3616bcd010bfbb3260299e26";
	private static String API_KEY = "bce-v3/ALTAK-VCJgWbbUh94gX5SIudmLL/7d12be54ef86d5ecd11cc43b2d0be4ac5b9fcb94";
	/**
	 * API Url
	 */
	private static String API_URL = "https://qianfan.baidubce.com/v2/chat/completions";
	/**
	 * 模型ID，支持ernie-3.5-8k、ernie-4.0-turbo-8k、ernie-4.5-turbo-32k，越新越贵
	 */
	private static String MODEL = "ernie-3.5-8k";
	
	private static Logger logger = Logger.getLogger(BaiduAiProxy.class);
	private static Logger loggerSorting = Logger.getLogger("LoalTaskSpiderSorting");
	
	/**
	 * 网页分析
	 * 阅读助手插件：https://cloud.baidu.com/doc/WENXINWORKSHOP/s/Um2wxbaps
	 * @param prompt 提示语
	 * @param url
	 * @return
	 */
	public static String analysisWeb(String prompt, String url){
		try {
			BaiduAiChatFileReq req = new BaiduAiChatFileReq(MODEL, prompt, url);
			
			String json = JsonUtil.object2String(req);
			loggerSorting.info("请求："+json);
			
			HttpsURLConnection conn = (HttpsURLConnection) new URL(API_URL).openConnection();
			conn.setSSLSocketFactory(HttpsSSLFactoryUtil.getSSLFactory());
			conn.setConnectTimeout(HttpUtil.CONN_TIMEOUT_DEFAULT);
			conn.setReadTimeout(300000);
	        conn.setRequestMethod("POST");
	        conn.setRequestProperty("Content-Type", "application/json");
	        conn.setRequestProperty("Authorization", "Bearer " + API_KEY);
	        conn.setDoOutput(true);

	    	OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream(), FileUtil.ENCODING_UTF8);
	    	osw.write(json);
	    	osw.flush();
	    	osw.close();
	    	
	    	if(conn.getResponseCode()!=200){
	        	if(conn.getResponseCode()==429){
	        		needRecharge();
	        		logger.error("余额不足："+conn.getResponseCode()+conn.getResponseMessage());
	        	}else{
	        		logger.error("网页分析失败："+conn.getResponseCode()+conn.getResponseMessage()+"，"+url);
	        	}
	        	throw new ProxyException("网页分析失败");
	        }
	        
	        InputStream is = conn.getInputStream();
	        String resultJson = FileUtil.inputStreamToString(is, FileUtil.ENCODING_UTF8);
	        loggerSorting.info("返回："+resultJson);
	        BaiduAiChatFileResp resp = JsonUtil.string2Object(resultJson, BaiduAiChatFileResp.class);
	        if(resp.getChoices()!=null && !resp.getChoices().isEmpty()){
	        	String result = resp.getChoices().get(0).getMessage().getContent();
	        	return result;
	        }
	        return "";
		} catch (ProxyException e) {
			throw e;
		} catch (Exception e) {
			logger.error("网页分析失败：", e);
			throw new ProxyException("网页分析失败");
		}
	}
	
	public static boolean ACCOUNT_STATUS = true;
	public static void resetAccountStatus(){
		ACCOUNT_STATUS = true;
	}
	
	private static Date lastMailTime = null;
	private static void needRecharge(){
		ACCOUNT_STATUS = false;
		Date now = new Date();
		if(lastMailTime==null || DateUtil.betweenMinute(lastMailTime, now)>30){						
			MailUtil.sendSimpleMail(SystemConfig.CS_MAILS, "百度大模型余额不足", "百度大模型余额不足");
			lastMailTime = now;
		}
	}
	
	public static void main(String[] args) {
		String prompt = BaiduAiUtil.getPrompt();
		String url = null;
		url = "https://mp.weixin.qq.com/s?__biz=Mzg2MjkyMTAxOA==&mid=2247489690&idx=1&sn=5f8cfd3328403ed93574d195b8ee5b98&chksm=ce01d0cff97659d9a3e335c4cf17b4f6a352cf4526bb814c6dd89d0394518faef5d6e233bc1c#rd";
		
		String content = analysisWeb(prompt, url);
		
		if(StringUtil.isBlankOrNull(content)) {
			System.out.println("提取结果为空，抛弃");
		}
	}
}
