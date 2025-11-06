package com.qcmz.cmc.proxy.trans;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.cache.ProxyAccountMap;
import com.qcmz.cmc.cache.ProxyLangMap;
import com.qcmz.cmc.config.JobConfig;
import com.qcmz.cmc.config.SystemConfig;
import com.qcmz.cmc.config.TransConfig;
import com.qcmz.cmc.constant.BusinessConstant;
import com.qcmz.cmc.entity.CmcProxyAccount;
import com.qcmz.cmc.process.ProxyAccountProcess;
import com.qcmz.cmc.thrd.BingKeyStopThrd;
import com.qcmz.cmc.thrd.SimpleMailThrd;
import com.qcmz.cmc.vo.BingTokenBean;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.exception.ProxyException;
import com.qcmz.framework.util.FileUtil;
import com.qcmz.framework.util.HttpUtil;
import com.qcmz.framework.util.StringUtil;


/**
 * 类说明：bing翻译
 * 修改历史：
 */
@Component
public class BingTransProxy extends AbstractTransProxy{
	@Autowired
	private ProxyAccountMap proxyAccountMap;
	@Autowired
	private ProxyLangMap proxyLangMap;
	@Autowired
	private ProxyAccountProcess proxyAccountProcess;
	
	/**
	 * 流量用尽次数
	 */
	private Map<String, Integer> zeroMap = new HashMap<String, Integer>();
	/**
	 * token池
	 */
	private List<BingTokenBean> tokens = Collections.synchronizedList(new ArrayList<BingTokenBean>());
	/**
	 * 上次Token异常邮件时间
	 */
	private Long lastTokenMailTime = null;

	public BingTransProxy() {
		super();
		proxyId = BusinessConstant.PROXYID_BIYING;
	}

	@Override
	public String trans(String from, String to, String src) {
		return transAjax(from, to, src);
	}

	/** 
	* 翻译
	 * @param from 源语言
	 * @param to 目标语言
	 * @param src 待翻译内容
	 * @return
	 * 修改历史：
	 */
	public String transAjax(String from, String to, String src) {
		StringBuilder sbHint = new StringBuilder().append(from).append("->").append(to).append("[").append(src).append("]");
		HttpURLConnection conn = null;
		try {
			//鉴权获取token
			BingTokenBean token = getToken();
			if(token==null){
				throw new ProxyException("获取BingToken失败");
			}

			//翻译
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put("from", proxyLangMap.getBean(proxyId, from).getProxylangcode());
			paramsMap.put("to", proxyLangMap.getBean(proxyId, to).getProxylangcode());
			paramsMap.put("text", URLEncoder.encode(src, SystemConstants.ENCODING_UTF8));
			
			String params = HttpUtil.map2Params(paramsMap);
			
			URL url = new URL(TransConfig.BING_TRANSURL_AJAX+"?"+params);
			
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(TransConfig.BING_TRANS_TIMEOUT);   
			conn.setRequestProperty("Content-Type","text/plain; charset=" + SystemConstants.ENCODING_UTF8);
	        conn.setRequestProperty("Accept-Charset",SystemConstants.ENCODING_UTF8);
            conn.setRequestProperty("Authorization",token.getAccessToken());
	        conn.setRequestMethod("GET");
	        conn.setDoOutput(true);
			
	        if(conn.getResponseCode()==200){
	        	String resp = FileUtil.inputStreamToString(conn.getInputStream(), SystemConstants.ENCODING_UTF8);
	        	if(StringUtil.notBlankAndNull(resp)){
	        		resp = resp.replaceAll("\uFEFF", "");
	        	}
	        	//result = "\"TranslateApiException: credentials has zero balance";
	        	if(!isException(resp)){
	        		return jsonToString(resp);
	        	}else{
	        		if(resp.indexOf("credentials has zero balance")>-1){
						zero(token);//流量用完停用key
					}else if(resp.indexOf("token has expired")>-1){
						token.setTokenExpiration(System.currentTimeMillis()-1000);
						logger.error(new StringBuilder(sbHint).append("token has expired：").append(token.getTokenExpiration()).append(" now:"+System.currentTimeMillis()));
					}else{
						logger.error(new StringBuilder(sbHint).append("翻译失败：").append(resp).append(" apiKey:"+token.getApiKey()));
					}
	        	}
	        }else{
	        	logger.error(new StringBuilder(sbHint).append("翻译失败：").append(conn));
	        }
		} catch (SocketTimeoutException e) {
			return transHttp(from, to, src);
		} catch (Exception e) {
			logger.error(new StringBuilder("翻译出错：").append(sbHint), e);
		} finally{
			if(conn!=null){
				conn.disconnect();
			}
		}
		return null;
	}
	
	
	
	/** 
	*  翻译
	 * @param from 源语言
	 * @param to 目标语言
	 * @param src 待翻译内容
	 * @return
	 * 修改历史：
	 */
	public String transHttp(String from, String to, String src) {
		StringBuilder sbHint = new StringBuilder().append(from).append("->").append(to).append("[").append(src).append("]");
		HttpURLConnection conn = null;
		try {
			//鉴权获取token
			BingTokenBean token = getToken();

			//翻译
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put("from", proxyLangMap.getBean(proxyId, from).getProxylangcode());
			paramsMap.put("to", proxyLangMap.getBean(proxyId, to).getProxylangcode());
			paramsMap.put("text", URLEncoder.encode(src, SystemConstants.ENCODING_UTF8));
			
			String params = HttpUtil.map2Params(paramsMap);
			
			URL url = new URL(TransConfig.BING_TRANSURL_HTTP+"?"+params);
			
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(TransConfig.BING_TRANS_TIMEOUT);
			conn.setRequestProperty("Content-Type","text/plain; charset=" + SystemConstants.ENCODING_UTF8);
	        conn.setRequestProperty("Accept-Charset",SystemConstants.ENCODING_UTF8);
            conn.setRequestProperty("Authorization",token.getAccessToken());
	        conn.setRequestMethod("GET");
	        conn.setDoOutput(true);
			
			if(conn.getResponseCode()==200){
				//<string xmlns="http://schemas.microsoft.com/2003/10/Serialization/">Where are you going</string>
				String result = FileUtil.inputStreamToString(conn.getInputStream(), SystemConstants.ENCODING_UTF8);
				Element root = DocumentHelper.parseText(result).getRootElement();
				return root.getTextTrim();
			}else{
				logger.error(new StringBuilder(sbHint).append("翻译失败：").append(conn));
			}
			
		} catch (Exception e) {
			logger.error(new StringBuilder("翻译出错：").append(sbHint), e);
		} finally{
			if(conn!=null){
				conn.disconnect();
			}
		}
		return null;
	}
	
	/**
	 * 流量用完一定次数之后，停用并邮件通知
	 * @param apikey
	 * 修改历史：
	 */
	private void zero(BingTokenBean token){
		tokens.remove(token);
		
		String apikey = token.getApiKey();
		Integer count = zeroMap.get(apikey);
		if(count==null){
			count = 1;
		}else{
			count++;
		}
		if(count>=2){
			zeroMap.remove(apikey);
			String log = new StringBuilder(apikey).append("流量为0").toString(); 
			logger.error(log);
			//TransMonitorThrd.start(transProcess, log);
			BingKeyStopThrd.start(proxyAccountProcess, apikey);
		}else{
			zeroMap.put(apikey, count);
		}
	}
	
	private BingTokenBean getToken(){
		//不定时获取token，则直接从必应获取
		if(!JobConfig.BINGTOKEN_GET_ISWORK){
			return getTokenFromBing();
		}
		
		//从缓存获取token
		BingTokenBean result = null;
		BingTokenBean temp = null;
		int random = 0;
		try {
			while(!tokens.isEmpty()){
				random = new Random().nextInt(tokens.size());
				temp = tokens.get(random);
				if(System.currentTimeMillis()<temp.getTokenExpiration()){
					result = temp;
					break;
				}else{
					tokens.remove(temp);
					logger.info("被动删除过期Token，余"+tokens.size());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(result==null){
			if(lastTokenMailTime==null || lastTokenMailTime+30000<System.currentTimeMillis()){
				lastTokenMailTime = System.currentTimeMillis();
				SimpleMailThrd.start(SystemConfig.MAILS, "没有可用BingToken");
			}
			throw new ProxyException("获取BingToken失败");
		}else if(tokens.size()<5){
			if(lastTokenMailTime==null || lastTokenMailTime+30000<System.currentTimeMillis()){
				lastTokenMailTime = System.currentTimeMillis();
				SimpleMailThrd.start(SystemConfig.MAILS, "可用BingToken仅剩"+tokens.size());
			}
		}
		return result;
	}
	
	/**
	 * 从必应获取token并缓存
	 * 系统启动后调用一次，而后定时调用
	 * 修改历史：
	 */
	public void getTokenAndCache(){
		BingTokenBean temp = null;
		Iterator<BingTokenBean> its = tokens.iterator();
		while(its.hasNext()){
			temp = its.next();
			if(System.currentTimeMillis()>=temp.getTokenExpiration()){
				its.remove();
				logger.info("主动删除过期Token，余"+tokens.size());
			}
		}
		
		int keyCount = proxyAccountMap.getCount4Java(proxyId);
		int poolsize = TransConfig.BING_TRANS_TOKENCOUNT;
		logger.info(new StringBuilder("keyCount：").append(keyCount).append("，poolsize：").append(poolsize).append("，BingToken Size：").append(tokens.size()));
		if(poolsize>keyCount){
			poolsize = keyCount;
		}
		if(tokens.size()<poolsize){
			BingTokenBean token = getTokenFromBing();
			if(token!=null){
				tokens.add(token);
			}
		}
	}
	
	/**
	 * 从必应获取token
	 * @return
	 * 修改历史：
	 */
	private BingTokenBean getTokenFromBing(){
		CmcProxyAccount account = proxyAccountMap.getAccount4Java(proxyId);
		if(account==null) return null;
		
		BingTokenBean result = null;
		String accessToken = null;
		HttpURLConnection conn = null;
		try {
			String clientId = URLEncoder.encode(account.getAccount(), SystemConstants.ENCODING_UTF8);
			String clientSecret = URLEncoder.encode(account.getKey1(), SystemConstants.ENCODING_UTF8);
			StringBuilder sbParams = new StringBuilder();
			sbParams.append("grant_type=client_credentials&scope=http://api.microsofttranslator.com")
					.append("&client_id=").append(clientId)
					.append("&client_secret=").append(clientSecret)
			;
			URL url = new URL(TransConfig.BING_AUTH_URL);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(TransConfig.BING_TRANS_TIMEOUT);
			conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded; charset=" + SystemConstants.ENCODING_UTF8);
			conn.setRequestProperty("Accept-Charset",SystemConstants.ENCODING_UTF8);
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(sbParams.toString());
			wr.flush();
			
			String tokenJson = FileUtil.inputStreamToString(conn.getInputStream(), SystemConstants.ENCODING_UTF8);
			if(StringUtil.notBlankAndNull(tokenJson)){
				tokenJson.replaceAll("\uFEFF", "");
			}
			if(conn.getResponseCode()==200){
				accessToken = "Bearer " + (String)((JSONObject)JSONValue.parse(tokenJson)).get("access_token");
			}else{
				logger.error("获取accessToken失败："+tokenJson);
			}
		} catch (Exception e) {
			logger.error("获取accessToken失败："+account.getAccount(), e);
		} finally{
			if(conn!=null){
				conn.disconnect();
			}
		}
		if(StringUtil.notBlankAndNull(accessToken)){
			result = new BingTokenBean(account.getAccount(), accessToken);
		}
		return result;
	}
	
	/**
	 * 清除token缓存
	 * 只有在手工重载apikey缓存时加载
	 * 修改历史：
	 */
	public void clearTokens(){
		logger.info("清除BingToken缓存");
		tokens.clear();
		getTokenAndCache();
	}
	
	private static boolean isException(String resp){
		return resp.startsWith("\"TranslateApiException:")
			|| resp.startsWith("\"ArgumentOutOfRangeException")
			|| resp.startsWith("\"ArgumentException")
		;
	}
	
	private static String jsonToString(String inputString){
        String json = (String)JSONValue.parse(inputString);
        return json.toString();
    }
	
	public Long getProxyId() {
		return proxyId;
	}
}
