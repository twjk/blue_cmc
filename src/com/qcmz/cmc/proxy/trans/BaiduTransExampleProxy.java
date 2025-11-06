package com.qcmz.cmc.proxy.trans;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.qcmz.cmc.cache.ProxyLangMap;
import com.qcmz.cmc.config.TransConfig;
import com.qcmz.cmc.constant.BusinessConstant;
import com.qcmz.cmc.entity.CmcTransExample;
import com.qcmz.framework.util.HttpUtil;
import com.qcmz.framework.util.StringUtil;

/**
 * 类说明：百度翻译例句，从网页上抓取，带例句等
 * http://fanyi.baidu.com/v2transapi?from=zh&query=%E5%AF%BF%E5%8F%B8&simple_means_flag=3&to=en&transtype=realtime
 * 从http://fanyi.baidu.com/#zh/en/%E8%8B%B9%E6%9E%9C采集到以上地址
 * 修改历史：
 */
@Component
public class BaiduTransExampleProxy {
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private ProxyLangMap proxyLangMap;
	
	private static String BASEURL = "http://fanyi.baidu.com/v2transapi?simple_means_flag=3&transtype=realtime";
	
	/** 
	 * @param from
	 * @param to
	 * @param src
	 * @return
	 * 修改历史：
	 */
	public List<CmcTransExample> spiderExample(String from, String to, String src) {
		List<CmcTransExample> result = new ArrayList<CmcTransExample>();
		CmcTransExample bean = null;
		try {
			//组装数据
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put("query", URLEncoder.encode(src, "utf-8"));
			paramsMap.put("from", proxyLangMap.getBean(BusinessConstant.PROXYID_BAIDU, from).getMtcode());
			paramsMap.put("to", proxyLangMap.getBean(BusinessConstant.PROXYID_BAIDU, to).getMtcode());
			
			
			String json = HttpUtil.post(BASEURL, HttpUtil.map2Params(paramsMap), TransConfig.BAIDU_TRANS_TIMEOUT, null, false);
			
			if(!json.contains("\"double\"")){
				return result;
			}
			
			json = json.replace("\"double\"", "\"liju_double\"");
			
			BaiduTransResp resp = JSON.parseObject(json, BaiduTransResp.class);

			if(resp.liju_result==null
					|| StringUtil.isBlankOrNull(resp.liju_result.liju_double)){
				return result;
			}
			
			JSONArray arrLiju = null;
			JSONArray arrJuzi = null;
			JSONArray arrSrc = null;
			JSONArray arrDst = null;
			JSONArray arrWord = null;
			StringBuilder sb = null;

			arrLiju = JSON.parseArray(resp.liju_result.liju_double);
			for (int i = 0; i < arrLiju.size(); i++) {
				bean = new CmcTransExample();
				bean.setTranssrc(src);
				bean.setFromlang(from);
				bean.setTolang(to);
				arrJuzi = arrLiju.getJSONArray(i);
				arrSrc = arrJuzi.getJSONArray(0);
				arrDst = arrJuzi.getJSONArray(1);
				
				sb = new StringBuilder();
				for (int j = 0; j < arrSrc.size(); j++) {
					arrWord = arrSrc.getJSONArray(j);
					sb.append(arrWord.get(0));
					if(arrWord.size()>4){
						sb.append(arrWord.get(4));
					}
				}
				bean.setSrc(sb.toString());
				
				sb = new StringBuilder();
				for (int j = 0; j < arrDst.size(); j++) {
					arrWord = arrDst.getJSONArray(j);
					sb.append(arrWord.get(0));
					if(arrWord.size()>4){
						sb.append(arrWord.get(4));
					}
				}
				bean.setDst(sb.toString());
				result.add(bean);
			}
		
		} catch (Exception e) {
			logger.error("获取例句出错：", e);
		}
		return result;
	}
	
	static class BaiduTransResp{
		LijuResult liju_result;
		public LijuResult getLiju_result() {
			return liju_result;
		}
		public void setLiju_result(LijuResult liju_result) {
			this.liju_result = liju_result;
		}
	}
	
	static class LijuResult{
		String liju_double;
		public String getLiju_double() {
			return liju_double;
		}
		public void setLiju_double(String liju_double) {
			this.liju_double = liju_double;
		}
	}
}
