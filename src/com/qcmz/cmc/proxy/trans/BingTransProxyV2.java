package com.qcmz.cmc.proxy.trans;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.qcmz.cmc.cache.ProxyAccountMap;
import com.qcmz.cmc.cache.ProxyLangMap;
import com.qcmz.cmc.config.TransConfig;
import com.qcmz.cmc.constant.BusinessConstant;
import com.qcmz.cmc.entity.CmcProxyAccount;
import com.qcmz.framework.exception.ProxyException;
import com.qcmz.framework.util.FileUtil;
import com.qcmz.framework.util.HttpsSSLFactoryUtil;
import com.qcmz.framework.util.StringUtil;

/**
 * bing翻译收费版v3.0
 * https://docs.microsoft.com/en-us/azure/cognitive-services/translator/quickstart-java-translate
 * https://docs.microsoft.com/en-us/azure/cognitive-services/translator/reference/v3-0-reference
 */
@Component
public class BingTransProxyV2 extends AbstractTransProxy {
	@Autowired
	private ProxyLangMap proxyLangMap;
	@Autowired
	private ProxyAccountMap proxyAccountMap;
	
	public BingTransProxyV2() {
		super();
		proxyId = BusinessConstant.PROXYID_BIYING;
	}

	/**
	 * 翻译
	 * @param from 源语言
	 * @param to 目标语言
	 * @param src 待翻译内容
	 * @return
	 * 修改历史：
	 */
	public String trans(String from, String to, String src) {
		StringBuilder sbHint = new StringBuilder().append(from).append("->").append(to).append("[").append(src).append("]");
		HttpsURLConnection conn = null;
		InputStream is = null;
		try {
			CmcProxyAccount account = proxyAccountMap.getAccount4Java(proxyId);
			
			//参数
			String fromLang = proxyLangMap.getBean(proxyId, from).getMtcode();
			String toLang = proxyLangMap.getBean(proxyId, to).getMtcode();
			//[{"Text":"你好"}]
			//利用JSON做特殊字符的转义
			String content = JSON.toJSONString(new BingText[]{new BingText(src)});
			
			//调接口
			String _url = new StringBuilder(account.getServerurl())
					.append("/translate?api-version=3.0")
					.append("&from=").append(fromLang).append("&to=").append(toLang)
					.toString();
			URL url = new URL(_url);
			conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(HttpsSSLFactoryUtil.getSSLFactory());
			conn.setConnectTimeout(TransConfig.BING_TRANS_TIMEOUT);
			conn.setReadTimeout(TransConfig.BING_TRANS_TIMEOUT);
	        conn.setRequestMethod("POST");
	        conn.setRequestProperty("Content-Type", "application/json");
	        conn.setRequestProperty("Content-Length", content.length() + "");
	        conn.setRequestProperty("Ocp-Apim-Subscription-Key", account.getKey1());
	        if(StringUtil.notBlankAndNull(account.getRegion())){
	        	conn.setRequestProperty("Ocp-Apim-Subscription-Region", account.getRegion());
	        }
	        conn.setRequestProperty("X-ClientTraceId", java.util.UUID.randomUUID().toString());
	        conn.setDoOutput(true);

        	DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            byte[] encoded_content = content.getBytes("UTF-8");
            wr.write(encoded_content, 0, encoded_content.length);
            wr.flush();
            wr.close();

            if(conn.getResponseCode()==200){
            	//[{"translations":[{"text":"How are you doing","to":"en"}]}]
            	is = conn.getInputStream();
            	String result = FileUtil.inputStreamToString(is, "UTF-8");
            	List<BingTransResp> resp = JSON.parseArray(result, BingTransResp.class);    		
            	return resp.get(0).getTranslations().get(0).getText();
	    	}else{
	    		StringBuilder sbResp = new StringBuilder(sbHint)
	    			.append("翻译失败：[")
	    			.append(conn.getResponseCode())
	    			.append("]").append(conn.getResponseMessage());
	    		logger.error(sbResp);
	    	}
            
		} catch(ProxyException e){
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error(sbHint+"翻译失败：", e);
		} finally{
			if(is!=null) try {is.close();} catch (Exception e) {};
			if(conn!=null) conn.disconnect();
		}
		return null;
	}

	
	public Long getProxyId() {
		return proxyId;
	}
	
	static class BingText{
		private String Text;
		public BingText() {
			super();
		}
		public BingText(String text) {
			super();
			Text = text;
		}
		public String getText() {
			return Text;
		}
		public void setText(String text) {
			Text = text;
		}
	}
	
	static class BingTransResp{
		private List<Translation> translations;		
		public List<Translation> getTranslations() {
			return translations;
		}
		public void setTranslations(List<Translation> translations) {
			this.translations = translations;
		}
	}
	
	static class Translation{
		private String text;
		private String to;
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
		public String getTo() {
			return to;
		}
		public void setTo(String to) {
			this.to = to;
		}
	}
}
