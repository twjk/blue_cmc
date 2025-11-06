package com.qcmz.cmc.proxy.speech.google;

import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.cache.ProxyAccountMap;
import com.qcmz.cmc.cache.ProxyLangMap;
import com.qcmz.cmc.constant.BusinessConstant;
import com.qcmz.cmc.entity.CmcProxyAccount;
import com.qcmz.cmc.entity.CmcProxyLang;
import com.qcmz.cmc.proxy.speech.AbstractSpeechProxy;
import com.qcmz.cmc.proxy.speech.google.vo.GoogleRecognizeAlternative;
import com.qcmz.cmc.proxy.speech.google.vo.GoogleRecognizeResp;
import com.qcmz.cmc.proxy.speech.google.vo.GoogleRecognizeResult;
import com.qcmz.cmc.proxy.speech.google.vo.GoogleRecongnizeReq;
import com.qcmz.cmc.proxy.speech.google.vo.GoogleSynthesizeReq;
import com.qcmz.cmc.proxy.speech.google.vo.GoogleSynthesizeResp;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.exception.ProxyException;
import com.qcmz.framework.util.DoubleUtil;
import com.qcmz.framework.util.FileUtil;
import com.qcmz.framework.util.HttpUtil;
import com.qcmz.framework.util.JsonUtil;
import com.qcmz.framework.util.NumberUtil;
import com.qcmz.framework.util.StringUtil;

/**
 * 谷歌语音技术，需要翻墙
 */
@Component
public class GoogleSpeechProxy extends AbstractSpeechProxy{
	@Autowired
	private ProxyLangMap proxyLangMap;
	@Autowired
	private ProxyAccountMap proxyAccountMap;
	
	/**
	 * ASR服务地址
	 */
//	private static String URL_ASR = "https://speech.googleapis.com/v1p1beta1/speech:recognize";
	//香港中转服务地址
	private static String URL_ASR = "http://47.91.190.222/wall/services/speech!asrByGoogle.do";
	/**
	 * TTS服务地址
	 */
//	private static String URL_TTS = "https://texttospeech.googleapis.com/v1beta1/text:synthesize";
	//香港中转服务地址
	private static String URL_TTS = "http://47.91.190.222/wall/services/speech!ttsByGoogle.do";
	
	public GoogleSpeechProxy() {
		super();
		proxyId = BusinessConstant.PROXYID_GOOGLE;
	}

	/**
	 * 语音识别REST API
	 * https://cloud.google.com/speech-to-text/docs/reference/rest/v1p1beta1/speech
	 * 语言： https://cloud.google.com/speech-to-text/docs/languages
	 * 音频编码： https://cloud.google.com/speech-to-text/docs/encoding
	 * @param langCode 语言代码
	 * @param file 语音文件
	 * @return
	 * @exception ProxyException
	 * 修改历史：
	 */
	@Override
	public String asr(String langCode, File file){
		long s = System.currentTimeMillis();
//		HttpsURLConnection conn = null;
		HttpURLConnection conn = null;
		InputStream is = null;
		String googleLangCode = null;
		String result = "";
		try {
			//帐户
			CmcProxyAccount account = proxyAccountMap.getAccount4Java(proxyId);
			googleLangCode = proxyLangMap.getBean(proxyId, langCode).getAsrcode();
			
			//请求
			GoogleRecongnizeReq req = new GoogleRecongnizeReq();
			req.getConfig().setSampleRateHertz(16000);			
			
			//示例
//			req.getConfig().setEncoding("FLAC");
//			req.getConfig().setLanguageCode("en-US");
//			req.getAudio().setUri("gs://cloud-samples-tests/speech/brooklyn.flac");
			
			//FLAC(2秒) LINEAR16(4秒) MP3(8秒)
			//快慢跟语言也有关系
			req.getConfig().setEncoding("LINEAR16");
			req.getConfig().setLanguageCode(googleLangCode);
			req.getAudio().setContent(Base64.encodeBase64String(FileUtil.fileToByteArray(file)));
//			req.getAudio().setUri("gs://gs_speech/bb.wav");

			String content = JsonUtil.object2String(req);
//			logger.info("请求：\n"+content);
			
			//调用
			URL url = new URL(URL_ASR+"?key="+account.getAccount());
			
//			conn = (HttpsURLConnection) url.openConnection();
			conn = (HttpURLConnection) url.openConnection();
			
			conn.setConnectTimeout(HttpUtil.CONN_TIMEOUT_DEFAULT);
			conn.setReadTimeout(HttpUtil.READ_TIMEOUT_DEFAULT);
	        conn.setRequestMethod("POST");
	        conn.setRequestProperty("Content-Type", "application/json");
	        conn.setRequestProperty("Content-Length", content.length() + "");
	        conn.setDoOutput(true);

        	DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            byte[] encoded_content = content.getBytes("UTF-8");
            wr.write(encoded_content, 0, encoded_content.length);
            wr.flush();
            wr.close();

            if(conn.getResponseCode()==200){
            	//{"results": [{"alternatives": [{"transcript": "爸爸","confidence": 0.9520726}],"languageCode": "cmn-hans-cn"}]}
            	is = conn.getInputStream();
            	String json = FileUtil.inputStreamToString(is, "UTF-8");
//            	logger.info("返回：\n"+json);
            	if(StringUtil.notBlankAndNull(json)
            			&& !"null".equalsIgnoreCase(json)){
            		GoogleRecognizeResp resp = JsonUtil.string2Object(json, GoogleRecognizeResp.class);
            		if(resp.getResults()!=null && !resp.getResults().isEmpty()){
            			GoogleRecognizeResult r1 = resp.getResults().get(0);
            			if(r1.getAlternatives()!=null && !r1.getAlternatives().isEmpty()){
            				GoogleRecognizeAlternative r = r1.getAlternatives().get(0);
            				if(r.getConfidence()!=null){
            					result = r.getTranscript();
            				}
            			}
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
			logger.error("语音识别失败失败：", e);
			throw new ProxyException("语音识别失败");
		} finally{
			if(is!=null) try {is.close();} catch (Exception e) {};
			if(conn!=null) conn.disconnect();
			
			StringBuilder sbLog = new StringBuilder();
			sbLog.append("谷歌语音识别耗时【").append(System.currentTimeMillis()-s)
				 .append("毫秒】语言【").append(langCode).append("->").append(googleLangCode)
				 .append("】识别结果：").append(result);
			logger.info(sbLog);
		}
		return result;
	}
	
	/**
	 * 语音合成
	 * https://cloud.google.com/text-to-speech/docs/reference/rest/v1beta1/text/synthesize
	 * 语言和发音人：https://cloud.google.com/text-to-speech/docs/voices 不全
	 * 可以通过直接访问https://texttospeech.googleapis.com/v1/voices?key=xxx获取
	 * 也可以在页面https://cloud.google.com/text-to-speech/docs/reference/rest/v1/voices/list通过try it获取
	 * @param langCode 语言代码
	 * @param text 文本
	 * @param filePath 文件路径
	 * @param gender 性别 M男F女
	 * @param speed 语速	100正常
	 * @return
	 */
	public String tts(String langCode, String text, String gender, Integer speed){
		long s = System.currentTimeMillis();
//		HttpsURLConnection conn = null;
		HttpURLConnection conn = null;
		InputStream is = null;
		CmcProxyLang langBean = null;
		
		try {
			//帐户
			CmcProxyAccount account = proxyAccountMap.getAccount4Java(proxyId);
			langBean = proxyLangMap.getBean(proxyId, langCode);
			
			//请求
			GoogleSynthesizeReq req = new GoogleSynthesizeReq();
			
			req.getInput().setText(text);
			
			//语言代码
			req.getVoice().setLanguageCode(langBean.getTtscode());
			//男音女音
			if(DictConstants.DICT_SEX_M.equals(gender)){
				req.getVoice().setName(langBean.getTtsmalevoice());
			}else{
				req.getVoice().setName(langBean.getTtsfemalevoice());
			}
			
			//音频格式，LINEAR16、MP3、OGG_OPUS
			req.getAudioConfig().setAudioEncoding("LINEAR16");
			//采样率
			req.getAudioConfig().setSampleRateHertz(16000.0);
			//音量增益，db， 值范围[-96.0, 16.0]
			req.getAudioConfig().setVolumeGainDb(0.0);
			//音调调节，值范围[-20.0, 20.0]，由轻声到尖锐，0为正常
			req.getAudioConfig().setPitch(0.0);
			
			//语速，值范围[0.25, 4.0]，1.0为正常语速
			double googleSpeed = 1.0;
			if(speed!=null){
				googleSpeed = DoubleUtil.divide(speed, TTS_STANDARD);
				googleSpeed = NumberUtil.limit(googleSpeed, 0.25, 4.0);
			}
			req.getAudioConfig().setSpeakingRate(googleSpeed);
			
			String content = JsonUtil.object2String(req);
//			logger.info("请求：\n"+content);
			
			//调用
			URL url = new URL(URL_TTS+"?key="+account.getAccount());
			
//			conn = (HttpsURLConnection) url.openConnection();
			conn = (HttpURLConnection) url.openConnection();
			
			conn.setConnectTimeout(HttpUtil.CONN_TIMEOUT_DEFAULT);
			conn.setReadTimeout(HttpUtil.READ_TIMEOUT_DEFAULT);
	        conn.setRequestMethod("POST");
	        conn.setRequestProperty("Content-Type", "application/json");
	        conn.setRequestProperty("Content-Length", content.length() + "");
	        conn.setDoOutput(true);

        	DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            byte[] encoded_content = content.getBytes("UTF-8");
            wr.write(encoded_content, 0, encoded_content.length);
            wr.flush();
            wr.close();

            if(conn.getResponseCode()==200){
            	is = conn.getInputStream();
            	String json = FileUtil.inputStreamToString(is, "UTF-8");
//            	logger.info("返回：\n"+json);
            	if(StringUtil.notBlankAndNull(json)
            			&& !"null".equalsIgnoreCase(json)){
            		GoogleSynthesizeResp resp = JsonUtil.string2Object(json, GoogleSynthesizeResp.class);
//            		String filePath = "c:/test/google_"+System.currentTimeMillis()+".wav";
//            		FileUtil.writeToFile(Base64.decodeBase64(resp.getAudioContent()), filePath);
            		return resp.getAudioContent();
            	}else{
            		throw new ProxyException("语音合成失败");
            	}
	    	}else{
	    		StringBuilder sbResp = new StringBuilder()
	    			.append("语音合成失败：[")
	    			.append(conn.getResponseCode())
	    			.append("]").append(conn.getResponseMessage());
	    		logger.error(sbResp);
	    		throw new ProxyException("语音识别失败");
	    	}
		} catch (ProxyException e) {
			throw e;
		}catch (Exception e) {
			logger.error("语音合成失败失败：", e);
			throw new ProxyException("语音合成失败");
		} finally{
			if(is!=null) try {is.close();} catch (Exception e) {};
			if(conn!=null) conn.disconnect();
			
			StringBuilder sbLog = new StringBuilder();
			sbLog.append("谷歌语音合成识别【").append(System.currentTimeMillis()-s)
				 .append("毫秒】语言【").append(langCode).append("】");
			logger.info(sbLog);
		}
	}
}
