package com.qcmz.cmc.proxy.trans;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.qcmz.cmc.constant.BusinessConstant;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.util.HttpUtil;
import com.qcmz.framework.util.StringUtil;

/**
 * 类说明：灵云翻译
 * 接口不成熟，大量异常，停用
 * 修改历史：
 */
@Component
public class HcicloudTransProxy extends AbstractTransProxy {
	private static String url = "http://www.hcicloud.com/experimental/translate";
	
	public Long getProxyId() {
		return BusinessConstant.PROXYID_HCICLOUD;
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
		String capkey_type = null;
		if(DictConstants.DICT_LANG_ZHCN.equals(from) && DictConstants.DICT_LANG_EN.equals(to)){
			capkey_type = "0";	//中英
		}else if(DictConstants.DICT_LANG_EN.equals(from) && DictConstants.DICT_LANG_ZHCN.equals(to)){
			capkey_type = "1";	//英中
		}else{
			logger.info("不支持："+from+"->"+to);
			return null;
		}
		StringBuilder sbHint = new StringBuilder().append(from).append("->").append(to).append("[").append(src).append("]");
		try {
			//组装数据
			Map<String, String> params = new HashMap<String, String>();
			params.put("capkey_type", capkey_type);
			params.put("content", URLEncoder.encode(src, "utf-8"));
			
			String json = HttpUtil.post(url, HttpUtil.map2Params(params), false);
			
			if(json.startsWith("{")){
				HcicloudTransResp resp = JSON.parseObject(json, HcicloudTransResp.class);
				if(resp.status!=null && resp.status==0 
						&& StringUtil.notBlankAndNull(resp.result)){
					return resp.result;
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

	static class HcicloudTransResp{
		private Integer status;
		private String result;
		public Integer getStatus() {
			return status;
		}
		public void setStatus(Integer status) {
			this.status = status;
		}
		public String getResult() {
			return result;
		}
		public void setResult(String result) {
			this.result = result;
		}
	}
	
}
