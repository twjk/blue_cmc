package com.qcmz.cmc.proxy.trans;

import java.net.URLEncoder;
import java.util.List;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.qcmz.cmc.constant.BusinessConstant;
import com.qcmz.framework.util.HttpUtil;

/**
 * 类说明：有道翻译
 * 费用问题，停用
 * 修改历史：
 */
@Component
public class YoudaoTransProxy extends AbstractTransProxy {
	private static String url = "http://fanyi.youdao.com/openapi.do?keyfrom=VoiceTrans&key=468380854&type=data&doctype=json&version=1.1&only=translate&q=";
	
	public Long getProxyId() {
		return BusinessConstant.PROXYID_YOUDAO;
	}
	
	/** 
	 * 翻译
	 * @param from
	 * @param to
	 * @param src
	 * @return
	 * 修改历史：
	 */
	@Override
	public String trans(String from, String to, String src) {
		StringBuilder sbHint = new StringBuilder().append(from).append("->").append(to).append("[").append(src).append("]");
		try {
			//组装数据
			String getUrl = url + URLEncoder.encode(src, "utf-8");
			
			String json = HttpUtil.get(getUrl, false);
			
			if(json.startsWith("{")){
				YoudaoTransResp resp = JSON.parseObject(json, YoudaoTransResp.class);
				if("0".equals(resp.errorCode) 
						&& resp.translation!=null 
						&& !resp.translation.isEmpty()){
					return resp.translation.get(0);
				}else{
					logger.error(new StringBuilder(sbHint).append("翻译失败：").append(json));
				}
			}else{
				logger.error(new StringBuilder(sbHint).append("翻译失败：").append(json));
			}
		} catch (Exception e) {
			logger.error(new StringBuilder("翻译出错：").append(sbHint), e);
		}

		return null;
	}

	static class YoudaoTransResp{
		private String errorCode;
		private List<String> translation;
		public String getErrorCode() {
			return errorCode;
		}
		public void setErrorCode(String errorCode) {
			this.errorCode = errorCode;
		}
		public List<String> getTranslation() {
			return translation;
		}
		public void setTranslation(List<String> translation) {
			this.translation = translation;
		}
	}
	
}
