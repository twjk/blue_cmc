package com.qcmz.cmc.proxy.trans;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.qcmz.cmc.cache.LangMap;
import com.qcmz.cmc.config.TransConfig;
import com.qcmz.cmc.constant.BusinessConstant;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.util.HttpUtil;

/**
 * 类说明：百度翻译
 * 修改历史：
 */
@Deprecated
public class BaiduTransProxy extends AbstractTransProxy {
	@Autowired
	private LangMap langMap;

	private static String client_id = "59hFBrqgxbtF2hfAaWzvjzDC";
	private static String url = "http://openapi.baidu.com/public/2.0/bmt/translate";
	
	public Long getProxyId() {
		return BusinessConstant.PROXYID_BAIDU;
	}
	
	/** 
	* 翻译
	 * @param from 源语言
	 * @param to 目标语言
	 * @param src 待翻译内容
	 * @return
	 * 修改历史：
	 */
	@Override @SuppressWarnings("unchecked")
	public String trans(String from, String to, String src){
		StringBuilder sbHint = new StringBuilder().append(from).append("->").append(to).append("[").append(src).append("]");
		try {
			BaiduTransResp resp = baiduTrans(from, to, src);
			if(resp.getTrans_result()!=null && !resp.getTrans_result().isEmpty()){
				return resp.getTrans_result().get(0).getDst();
			}else{
				StringBuilder sbLog = new StringBuilder();
				sbLog.append(sbHint)
					 .append("翻译失败：")
					 .append(resp.getError_code()).append("[").append(resp.getError_msg()).append("]")
				;
				logger.error(sbLog.toString());
			}
		} catch (Exception e) {
			logger.error(new StringBuilder("翻译出错：").append(sbHint), e);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private BaiduTransResp baiduTrans(String from, String to, String src) throws Exception {
		//组装数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("client_id", client_id);
		map.put("from", langMap.getBaiduCode(from));
		map.put("to", langMap.getBaiduCode(to));
		map.put("q", URLEncoder.encode(src, SystemConstants.ENCODING_UTF8));
		
		//发起请求
		//http://openapi.baidu.com/public/2.0/bmt/translate?client_id=59hFBrqgxbtF2hfAaWzvjzDC&q=Oxford%20street.&from=en&to=zh
		String json = HttpUtil.post(url, HttpUtil.map2Params(map), TransConfig.BAIDU_TRANS_TIMEOUT, false);
		
		//结果解析
		return (BaiduTransResp) JSON.parseObject(json, BaiduTransResp.class); 
	}
	
	static class BaiduTransResp {
		/**
		 * 错误码
		 */
		private String error_code;
		/**
		 * 错误信息
		 */
		private String error_msg;
		/**
		 * 源语言
		 */
		private String from;
		/**
		 * 目标语言
		 */
		private String to;
		/**
		 * 母语
		 */
		private String monLang;
		/**
		 * 调用API时的输入参数
		 */
		private String query;
		/**
		 * 翻译结果
		 */
		private List<BaiduTransBean> trans_result;
		
		public String getError_code() {
			return error_code;
		}
		public void setError_code(String error_code) {
			this.error_code = error_code;
		}
		public String getError_msg() {
			return error_msg;
		}
		public void setError_msg(String error_msg) {
			this.error_msg = error_msg;
		}
		public List<BaiduTransBean> getTrans_result() {
			return trans_result;
		}
		public void setTrans_result(List<BaiduTransBean> trans_result) {
			this.trans_result = trans_result;
		}
		public String getFrom() {
			return from;
		}
		public void setFrom(String from) {
			this.from = from;
		}
		public String getTo() {
			return to;
		}
		public void setTo(String to) {
			this.to = to;
		}
		public String getMonLang() {
			return monLang;
		}
		public void setMonLang(String monLang) {
			this.monLang = monLang;
		}
		public String getQuery() {
			return query;
		}
		public void setQuery(String query) {
			this.query = query;
		}
	}
	
	static class BaiduTransBean {
		/**
		 * 原文
		 */
		private String src;
		/**
		 * 译文
		 */
		private String dst;
		/**
		 * 翻译结果
		 */
		private List<Object> align_info;

		public String getSrc() {
			return src;
		}
		public void setSrc(String src) {
			this.src = src;
		}
		public String getDst() {
			return dst;
		}
		public void setDst(String dst) {
			this.dst = dst;
		}
		public List<Object> getAlign_info() {
			return align_info;
		}
		public void setAlign_info(List<Object> align_info) {
			this.align_info = align_info;
		}
	}
}
