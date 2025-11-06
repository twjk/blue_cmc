package com.qcmz.cmc.proxy.speech.azure;

import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.cache.ProxyAccountMap;
import com.qcmz.cmc.cache.ProxyLangMap;
import com.qcmz.cmc.constant.BusinessConstant;
import com.qcmz.cmc.entity.CmcProxyAccount;
import com.qcmz.cmc.proxy.speech.AbstractSpeechProxy;
import com.qcmz.cmc.proxy.speech.azure.vo.AzureRecongnizeResp;
import com.qcmz.framework.exception.ProxyException;
import com.qcmz.framework.util.FileUtil;
import com.qcmz.framework.util.HttpUtil;
import com.qcmz.framework.util.HttpsSSLFactoryUtil;
import com.qcmz.framework.util.JsonUtil;
import com.qcmz.framework.util.StringUtil;

/**
 * 微软语音
 */
@Component
public class AzureSpeechProxy extends AbstractSpeechProxy {
	@Autowired
	private ProxyLangMap proxyLangMap;
	@Autowired
	private ProxyAccountMap proxyAccountMap;
	
	private String URL_ASR = "https://eastasia.stt.speech.microsoft.com/speech/recognition/conversation/cognitiveservices/v1";
	
	public AzureSpeechProxy() {
		super();
		proxyId = BusinessConstant.PROXYID_AZURESPEECH;
	}
	
	/**
	 * 语音识别 REST API
	 * https://docs.microsoft.com/zh-cn/azure/cognitive-services/speech-service/rest-speech-to-text
	 * 语言：https://docs.microsoft.com/zh-cn/azure/cognitive-services/speech-service/language-support#speech-to-text
	 * 最多只能包含60秒的音频
	 * @param langCode 语言代码
	 * @param file 语音文件
	 */
	@Override
	public String asr(String langCode, File file) {
		HttpsURLConnection conn = null;
		InputStream is = null;
		String azureLangCode = null;
		String result = null;
		try {
			//帐户
			CmcProxyAccount account = proxyAccountMap.getAccount4Java(proxyId);
			azureLangCode = proxyLangMap.getBean(proxyId, langCode).getAsrcode();
			
			//调用
			URL url = new URL(URL_ASR+"?language="+azureLangCode);			
			conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(HttpsSSLFactoryUtil.getSSLFactory());
			conn.setConnectTimeout(HttpUtil.CONN_TIMEOUT_DEFAULT);
			conn.setReadTimeout(HttpUtil.READ_TIMEOUT_DEFAULT);
	        conn.setRequestMethod("POST");
	        conn.setRequestProperty("Content-Type", "audio/wav; codecs=audio/pcm; samplerate=16000");
	        conn.setRequestProperty("Ocp-Apim-Subscription-Key", account.getKey1());
	        conn.setDoOutput(true);

        	DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            byte[] encoded_content = FileUtil.fileToByteArray(file);
            wr.write(encoded_content, 0, encoded_content.length);
            wr.flush();
            wr.close();

            if(conn.getResponseCode()==200){
//            	{"RecognitionStatus":"Success","DisplayText":"Conversation.","Offset":4500000,"Duration":10600000}
//            	{"RecognitionStatus":"InitialSilenceTimeout","Offset":20000000,"Duration":0}
            	is = conn.getInputStream();
            	String json = FileUtil.inputStreamToString(is, "UTF-8");
//            	logger.info("返回：\n"+json);
            	if(StringUtil.notBlankAndNull(json)
            			&& !"null".equalsIgnoreCase(json)){
            		AzureRecongnizeResp resp = JsonUtil.string2Object(json, AzureRecongnizeResp.class);
            		if("Success".equals(resp.getRecognitionStatus())){
            			result = resp.getDisplayText();
            		}else{
            			logger.error("语音识别失败："+json);
            			throw new ProxyException("语音识别失败");
            		}
            	}else{
            		throw new ProxyException("语音识别失败");
            	}
	    	}else{
	    		StringBuilder sbResp = new StringBuilder()
	    			.append("语音识别失败：[")
	    			.append(conn.getResponseCode())
	    			.append("]").append(conn.getResponseMessage());
	    		logger.error(sbResp);
	    		throw new ProxyException("语音识别失败");
	    	}
		} catch (ProxyException e) {
			throw e;
		}catch (Exception e) {
			logger.error("语音识别失败：", e);
			throw new ProxyException("语音识别失败");
		} finally{
			if(is!=null) try {is.close();} catch (Exception e) {};
			if(conn!=null) conn.disconnect();
		}
		return result;
	}
	
	/**
	 * 语音合成
	 * @param langCode 语言代码
	 * @param text 文本
	 * @param langCode 语言代码
	 * @param gender 性别 M男F女
	 * @param speed 语速	100正常
	 * @return
	 */
	public String tts(String langCode, String text, String gender, Integer speed){
		return null;
	}
}
