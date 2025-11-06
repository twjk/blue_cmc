package com.qcmz.cmc.proxy.trans;

import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.qcmz.cmc.cache.ProxyAccountMap;
import com.qcmz.cmc.cache.ProxyLangMap;
import com.qcmz.cmc.config.TransConfig;
import com.qcmz.cmc.constant.BusinessConstant;
import com.qcmz.cmc.entity.CmcProxyAccount;
import com.qcmz.framework.counter.CounterInteger;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.HttpUtil;
import com.qcmz.framework.util.MailUtil;
import com.qcmz.framework.util.SecretUtil;

/**
 * 类说明：百度翻译
 * http://api.fanyi.baidu.com/api/trans/product/apidoc
 * 修改历史：
 */
@Component
public class BaiduTransProxyV2 extends AbstractTransProxy {
	@Autowired
	private ProxyLangMap proxyLangMap;
	@Autowired
	private ProxyAccountMap proxyAccountMap;

	private CounterInteger counter = new CounterInteger();
	
	public BaiduTransProxyV2() {
		super();
		proxyId = BusinessConstant.PROXYID_BAIDU;
	}

	/**
	 * 获取账户
	 * @return
	 */
	public CmcProxyAccount getAccount(){
		return proxyAccountMap.getAccount4Java(proxyId);
	}
	
	/** 
	* 翻译
	 * @param from 源语言
	 * @param to 目标语言
	 * @param src 待翻译内容
	 * @return
	 * 修改历史：
	 */
	@Override
	public String trans(String from, String to, String src){
		StringBuilder sbHint = new StringBuilder().append(from).append("->").append(to).append("[").append(src).append("]");
		try {
			CmcProxyAccount account = getAccount();
			
			sbHint.append(account.getAccount());
			
			//组装数据
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put("q", URLEncoder.encode(src, "utf-8"));
			paramsMap.put("appid", account.getAccount());
			paramsMap.put("salt", getSalt());
			paramsMap.put("from", proxyLangMap.getBean(proxyId, from).getMtcode());
			paramsMap.put("to", proxyLangMap.getBean(proxyId, to).getMtcode());
			paramsMap.put("sign", getSign(src, paramsMap.get("salt"), account.getAccount(), account.getKey1()));
			
			String json = HttpUtil.post(account.getServerurl(), HttpUtil.map2Params(paramsMap), TransConfig.BAIDU_TRANS_TIMEOUT, null, false);
			BaiduTransResp resp = JSON.parseObject(json, BaiduTransResp.class);
			if(resp==null){
				logger.error("解析失败："+json);
			}else if(resp.getTrans_result()!=null && !resp.getTrans_result().isEmpty()){
				if(counter.value()>0){
					counter = new CounterInteger();
				}
				return resp.getDst();
			}else{
				if("54004".equals(resp.getError_code())){
					needRecharge();
				}
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
		return "";
	}
	
	private Date lastMailTime = null;
	private String[] mails = new String[]{"app@qcmuzhi.com"};
	private void needRecharge(){
		counter.increase();
		Date now = new Date();
		if(counter.value()%500==0
				&& (lastMailTime==null || DateUtil.betweenMinute(lastMailTime, now)>30)){						
			MailUtil.sendSimpleMail(mails, null, "百度翻译余额不足", "百度翻译余额不足");
			lastMailTime = now;
		}
		
	}
	
	/**
	 * 获取sign
	 * @param q
	 * @param salt
	 * @return
	 * 修改历史：
	 */
	private String getSign(String q, String salt, String appid, String secKey){
		StringBuilder sb = new StringBuilder().append(appid).append(q).append(salt).append(secKey);
		return SecretUtil.encryptByMd5(sb.toString()).toLowerCase();
	}
	
	/**
	 * 获取随机数
	 * @return
	 * 修改历史：
	 */
	private String getSalt(){
		return String.valueOf(new Random().nextInt(1000000)); 
	}

	/**
	 * 
	 * 类说明：百度翻译结果
	 * {"from":"zh","to":"en","trans_result":[{"src":"\u96fe\u973e\u8d70\u4e86","dst":"The haze has gone"}]}
	 * 修改历史：
	 */
	static class BaiduTransResp{
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
		public List<BaiduTransBean> getTrans_result() {
			return trans_result;
		}
		public void setTrans_result(List<BaiduTransBean> trans_result) {
			this.trans_result = trans_result;
		}
		public String getDst(){
			StringBuilder sb = new StringBuilder();
			if(trans_result!=null){
				int i = 0;
				for (BaiduTransBean bean : trans_result) {
					i++;
					if(i>1){
						sb.append("\r\n");
					}
					sb.append(bean.getDst());
				}
			}
			return sb.toString();
		}
	}
	
	/**
	 * 类说明：翻译信息
	 * 修改历史：
	 */
	static class BaiduTransBean{
		/**
		 * 原文
		 */
		private String src;
		/**
		 * 译文
		 */
		private String dst;
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
	}
	
	public Long getProxyId() {
		return proxyId;
	}
}
