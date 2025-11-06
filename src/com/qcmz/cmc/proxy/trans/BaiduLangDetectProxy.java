package com.qcmz.cmc.proxy.trans;

import java.net.URLEncoder;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.qcmz.cmc.cache.ProxyLangMap;
import com.qcmz.cmc.constant.BusinessConstant;
import com.qcmz.framework.util.ArrayUtil;
import com.qcmz.framework.util.HttpUtil;
import com.qcmz.framework.util.StringUtil;

/**
 * 类说明：百度语言自动检测
 * 修改历史：
 */
@Component
public class BaiduLangDetectProxy {
	private Logger logger = Logger.getLogger(this.getClass());
	
	private static String url = "http://fanyi.baidu.com/langdetect";
	
	//检测功能不太准，非指定语种认为检测失败
	private static String[] langs = new String[]{"zh-cn", "ko", "ja", "ar-eg"};
	
	@Autowired
	private ProxyLangMap proxyLangMap;
	
	/**
	 * 检测语言，失败返回null
	 * http://fanyi.baidu.com/langdetect?query=Four%20Points%20by%20Sheraton%20Hangzhou%2C%20Binjiang
	 * @param txt
	 * @return
	 * 修改历史：
	 */
	public String detectLang(String txt){
		String lang = null;
		if(StringUtil.isBlankOrNull(txt)) return lang;
		try {
			String json = HttpUtil.post(url, "query="+URLEncoder.encode(txt, "utf-8"));
			BaiduDetectResp resp = JSON.parseObject(json, BaiduDetectResp.class);
			if(resp!=null 
					&& resp.error!=null && resp.error==0
					&& StringUtil.notBlankAndNull(resp.lan)){
				lang = proxyLangMap.getLangCodeByProxyLangCode(BusinessConstant.PROXYID_BAIDU, resp.lan);
				if(StringUtil.isBlankOrNull(lang)){
//					logger.error(txt+"检测语言失败："+json);
				}else if(!ArrayUtil.contain(langs, lang)){
					lang = null;
				}
				return lang;
			}
		} catch (Exception e) {
			logger.error("检测语言失败："+txt, e);
		}
		return lang;
	}
	
	static class BaiduDetectResp{
		Integer error;
		String msg;
		String lan;
		public Integer getError() {
			return error;
		}
		public void setError(Integer error) {
			this.error = error;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
		public String getLan() {
			return lan;
		}
		public void setLan(String lan) {
			this.lan = lan;
		}
	}
	
}
