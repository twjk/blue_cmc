package com.qcmz.cmc.proxy.speech.baidu;

import java.io.File;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baidu.aip.speech.AipSpeech;
import com.qcmz.cmc.cache.ProxyAccountMap;
import com.qcmz.cmc.cache.ProxyLangMap;
import com.qcmz.cmc.constant.BusinessConstant;
import com.qcmz.cmc.entity.CmcProxyAccount;
import com.qcmz.cmc.proxy.speech.AbstractSpeechProxy;
import com.qcmz.framework.exception.ProxyException;
import com.qcmz.framework.util.StringUtil;

/**
 * 百度语音
 * 依赖：baidu-aip-4.1.1.jar
 * https://ai.baidu.com/ai-doc/SPEECH/Rk38lxdmh
 */
@Component
public class BaiduSpeechProxy extends AbstractSpeechProxy{
	@Autowired
	private ProxyLangMap proxyLangMap;
	@Autowired
	private ProxyAccountMap proxyAccountMap;
	
	public BaiduSpeechProxy() {
		super();
		proxyId = BusinessConstant.PROXYID_BAIDUSPEECH;
	}
	
	private static AipSpeech client = null;
	private AipSpeech getClient(){
		if(client==null){
			CmcProxyAccount account = proxyAccountMap.getAccount4Java(proxyId);
			client = new AipSpeech(account.getAccount(), account.getKey1(), account.getKey2());
			client.setConnectionTimeoutInMillis(2000);
	        client.setSocketTimeoutInMillis(60000);
		}
		return client;
	}
	
	/**
	 * 短语音识别
	 * 2种普通话及英语、粤语、四川话
	 * 支持的格式有：pcm（不压缩）、wav（不压缩，pcm编码）、amr（压缩格式）
	 * 原始 pcm的录音参数必须符合 16k 采样率、16bit 位深、单声道
	 * 百度服务端会将非pcm格式，转为pcm格式，因此使用wav、amr、m4a会有额外的转换耗时
	 * @param langCode 语言代码
	 * @param file 语音文件
	 * @return
	 * @exception ProxyException
	 */
	public String asr(String langCode, File file){
		//{"result": ["这个文件能不能行，"],"err_msg": "success.","sn": "289883751881516348310", "err_no": 0, "corpus_no": "6512666402157296457"}
		//{"error_code": "SDK102", "error_msg": "read image file error"}
		//{"err_msg": "speech quality error.", "sn": "653297976171516348407", "err_no": 3301}
		
		try {
			HashMap<String, Object> options = new HashMap<String, Object>();
			options.put("dev_pid", proxyLangMap.getBean(proxyId, langCode).getAsrcode());
			
			JSONObject resp = getClient().asr(file.getPath(), "pcm", 16000, options);
			
			String err_no = resp.optString("err_no");
			if("0".equals(err_no)){
				JSONArray arr = resp.getJSONArray("result");
				return StringUtil.clearPuncLast(arr.getString(0));
			}else{
				logger.error("语音识别失败："+resp.toString());
				throw new ProxyException("语音识别失败");
			}
		} catch (ProxyException e) {
			throw e;
		}catch (Exception e) {
			logger.error("语音识别失败", e);
			throw new ProxyException("语音识别失败");
		}
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
