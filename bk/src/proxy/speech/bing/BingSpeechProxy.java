package com.qcmz.cmc.proxy.speech.bing;

import java.io.DataOutputStream;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.qcmz.cmc.constant.BusinessConstant;
import com.qcmz.cmc.proxy.speech.bing.vo.AudioOutputFormat;
import com.qcmz.cmc.vo.BingQuotaBean;
import com.qcmz.cmc.vo.BingTokenBean;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.HttpUtil;
import com.qcmz.framework.util.StringUtil;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class BingSpeechProxy {
	private static Logger logger = Logger.getLogger(BingSpeechProxy.class);
	/**
	 * 获取Token服务地址
	 */
	private static String URL_TOKEN = "https://oxford-speech.cloudapp.net/token/issueToken";
	/**
	 * tts服务地址
	 */
	private static String URL_TTS = "https://speech.platform.bing.com/synthesize";
	/**
	 * 获取额度服务地址
	 */
	private static String URL_GETQUOTA = "https://www.microsoft.com/cognitive-services/Subscription/GetQuotaInfoFromCallResult";
	
	
	/**
	 * 获取语音配额
	 * @param subscriptionId
	 * @param regTime
	 * @return
	 */
	public static BingQuotaBean getSpeechQuota(String subscriptionId, Date regTime){
		return getQuota("GlobalApim", "/products/Bing.Speech.Preview", subscriptionId, regTime);
	}
	
	/**
	 * 获取识别配额
	 * @param subscriptionId
	 * @param regTime
	 * @return
	 */
	public static BingQuotaBean getVisionQuota(String subscriptionId, Date regTime){
		return getQuota("WestUSApim", "/products/54d873dd5eefd00dc474a0f4", subscriptionId, regTime);
	}
	
	/**
	 * 获取配额
	 * @param proxyId
	 * @param subscriptionId
	 * @param regTime
	 * @return
	 */
	public static BingQuotaBean getQuota(Long proxyId, String subscriptionId, Date regTime){
		if(BusinessConstant.PROXYID_BINGSPEECH.equals(proxyId)){
			return BingSpeechProxy.getSpeechQuota(subscriptionId, regTime);
		}else if(BusinessConstant.PROXYID_BINGVISION.equals(proxyId)){
			return BingSpeechProxy.getVisionQuota(subscriptionId, regTime);
		}
		return null;
	}
	
	/**
	 * 获取配额
	 * {"TotalCalls":5000,"CallCountSuccess":"56","RemainingQuota":4944,"ExpiredDate":"10/06/2016 00:00:00"}
	 */
	public static BingQuotaBean getQuota(String apimId, String productId, String subscriptionId, Date regTime){
		try {
			StringBuilder sbParam = new StringBuilder()
				.append("?apimFriendlyName=").append(apimId)
				.append("&productId=").append(productId)
				.append("&subscriptionId=").append(subscriptionId)
				.append("&subscriptionCreatedDate=").append(DateUtil.format(regTime, "MM/dd/yyyy"))
				.append("&_=").append(System.currentTimeMillis())
				;
			
			String resp = HttpUtil.get(URL_GETQUOTA+sbParam.toString());
       
			return JSON.parseObject(resp, BingQuotaBean.class);
		} catch (Exception e) {
			logger.error("获取配额失败："+subscriptionId, e);
		}
		return null;
	}
	
	/**
	 * 使用量
	 */
	public static void use(String accessToken, String text){
		try {
			if(StringUtil.isBlankOrNull(text)){
				text = "This is a demo to call microsoft text to speach service in java.";
			}
			
			String outputFormat = AudioOutputFormat.Riff16Khz16BitMonoPcm;
	        String deviceLanguage = "en-US";
	        String genderName = "Female";
	        String voiceName = "Microsoft Server Speech Text to Speech Voice (en-US, ZiraRUS)";

            HttpsURLConnection webRequest = (HttpsURLConnection) new URL(URL_TTS).openConnection();
            webRequest.setDoInput(true);
            webRequest.setDoOutput(true);
            webRequest.setConnectTimeout(5000);
            webRequest.setReadTimeout(15000);
            webRequest.setRequestMethod("POST");

            webRequest.setRequestProperty("Content-Type", "application/ssml+xml");
            webRequest.setRequestProperty("X-Microsoft-OutputFormat", outputFormat);
            webRequest.setRequestProperty("Authorization", "Bearer " + accessToken);
            webRequest.setRequestProperty("User-Agent", "TTSAndroid");
            webRequest.setRequestProperty("Accept", "*/*");

            String SsmlTemplate = "<speak version='1.0' xml:lang='en-us'><voice xml:lang='%s' xml:gender='%s' name='%s'>%s</voice></speak>";
            String body = String.format(SsmlTemplate, deviceLanguage, genderName, voiceName, text);
            byte[] bytes = body.getBytes();
            webRequest.setRequestProperty("content-length", String.valueOf(bytes.length));
            webRequest.connect();

            DataOutputStream dop = new DataOutputStream(webRequest.getOutputStream());
            dop.write(bytes);
            dop.flush();
            dop.close();
            
            StringBuilder sbLog = new StringBuilder()
            	.append("使用配额：")
            	.append("[").append(webRequest.getResponseCode()).append("]")
            	.append(webRequest.getResponseMessage())
            ;
            logger.info(sbLog.toString());
            webRequest.disconnect();

            /*
            InputStream inSt = webRequest.getInputStream();
            ByteArray ba = new ByteArray();
            
            int rn2 = 0;
            int bufferLength = 4096;
            byte[] buf2 = new byte[bufferLength];
            while ((rn2 = inSt.read(buf2, 0, bufferLength)) > 0) {
                ba.cat(buf2, 0, rn2);
            }

            inSt.close();
            webRequest.disconnect();

            byte[] audioBuffer = ba.getArray();
        	
        	// write the pcm data to the file
        	String outputWave = "C:\\test\\"+System.currentTimeMillis()+".pcm";
        	File outputAudio = new File(outputWave);
        	FileOutputStream fstream = new FileOutputStream(outputAudio);
            fstream.write(audioBuffer);
            fstream.flush();
            fstream.close();
            */
		} catch (Exception e) {
			logger.error("使用key失败", e);
		}
	}
	
	/**
	 * 获取Token
	 * @param clientId
	 * @param clientSecret
	 * @return
	 * {"access_token":"eyJ0eXAiOiJKV1QiLCJhbGci...5mAE44g-SbRaQ3_AQwSmgk","token_type":"jwt","expires_in":"600","scope":"https://speech.platform.bing.com"}
	 * 修改历史：
	 */
	public static BingTokenBean getToken(String clientId, String clientSecret){
		Map<String, String> map = new HashMap<String, String>();
		map.put("grant_type", "client_credentials");
		map.put("client_id", clientId);
		map.put("client_secret", clientSecret);
		map.put("scope", "https://speech.platform.bing.com");
		String params = HttpUtil.map2Params(map);
		
		try {
			String resp = HttpUtil.post(URL_TOKEN, params);
			OxfordAccessToken accessToken = JSON.parseObject(resp, OxfordAccessToken.class);
			
			BingTokenBean token = new BingTokenBean();
			token.setApiKey(clientId);
			token.setAccessToken(accessToken.access_token);
			token.setTokenExpiration(System.currentTimeMillis()+(Integer.valueOf(accessToken.expires_in)-2)*1000);
			return token;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	static class OxfordAccessToken {
	    String access_token;
	    String token_type;
	    String expires_in;
	    String scope;
		public String getAccess_token() {
			return access_token;
		}
		public void setAccess_token(String access_token) {
			this.access_token = access_token;
		}
		public String getToken_type() {
			return token_type;
		}
		public void setToken_type(String token_type) {
			this.token_type = token_type;
		}
		public String getExpires_in() {
			return expires_in;
		}
		public void setExpires_in(String expires_in) {
			this.expires_in = expires_in;
		}
		public String getScope() {
			return scope;
		}
		public void setScope(String scope) {
			this.scope = scope;
		}
	}
}