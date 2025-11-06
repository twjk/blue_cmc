package com.qcmz.cmc.proxy.trans;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.cache.ProxyAccountMap;
import com.qcmz.cmc.cache.ProxyLangMap;
import com.qcmz.cmc.constant.BusinessConstant;
import com.qcmz.cmc.entity.CmcProxyAccount;
import com.qcmz.framework.exception.ProxyException;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.FileUtil;
import com.qcmz.framework.util.HttpUtil;
import com.qcmz.framework.util.SecretUtil;
import com.qcmz.framework.util.XmlUtil;

/**
 * 灵云翻译
 */
@Component
public class HcicloudTransProxy extends AbstractTransProxy {
	@Autowired
	private ProxyLangMap proxyLangMap;
	@Autowired
	private ProxyAccountMap proxyAccountMap;
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 翻译服务地址
	 */
	private static String TRANS_SERVER = "http://api.hcicloud.com:8880/mt/Translate";
	
	public HcicloudTransProxy() {
		super();
		proxyId = BusinessConstant.PROXYID_HCICLOUD;
	}
	
	@Override
	public String trans(String from, String to, String src) {
		StringBuilder sbHint = new StringBuilder().append(from).append("->").append(to).append("[").append(src).append("]");
		HttpURLConnection conn = null;
		InputStream is = null;
		try {
			
			CmcProxyAccount account = proxyAccountMap.getAccount4Java(proxyId);
			
			//参数
			String fromLang = proxyLangMap.getBean(proxyId, from).getMtcode();
			String toLang = proxyLangMap.getBean(proxyId, to).getMtcode();
			String date = DateUtil.formatDateTime(new Date());
			
			//调接口
			URL url = new URL(TRANS_SERVER);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(HttpUtil.CONN_TIMEOUT_DEFAULT);   
			conn.setReadTimeout(HttpUtil.READ_TIMEOUT_DEFAULT); 
	        conn.setRequestMethod("POST");
	        conn.setRequestProperty("x-sdk-version", "5.0");
	        conn.setRequestProperty("x-request-date", date);
	        conn.setRequestProperty("x-udid", "101:1234567890");
	        conn.setRequestProperty("x-app-key", account.getAccount());
	        conn.setRequestProperty("x-task-config", "capkey=mt.cloud."+fromLang+"2"+toLang);
	        conn.setRequestProperty("x-session-key", SecretUtil.encryptByMd5(date+account.getKey1()));		//请求数据签名,md5(x-request-date+devkey)，大写
	        conn.setDoOutput(true);

        	DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        	byte[] encoded_content = src.getBytes("UTF-8");
            wr.write(encoded_content, 0, encoded_content.length);
            wr.flush();
            wr.close();

            if(conn.getResponseCode()==200){
            	//<?xml version="1.0" encoding="UTF-8"?><ResponseInfo><ResCode>Success</ResCode><ResMessage>Success</ResMessage><ErrorNo>0</ErrorNo><Result_Token>2_126_50_48610_20190416094012_373878</Result_Token><ResultText>Hello.</ResultText><Score>100</Score></ResponseInfo>
            	//<?xml version="1.0" encoding="utf-8"?><ResponseInfo><ResCode>Failed</ResCode><ErrorNo>20500</ErrorNo><ResMessage>Internal Server Error: 发送/接收数据出错</ResMessage></ResponseInfo>
            	is = conn.getInputStream();
            	String resp = FileUtil.inputStreamToString(is, "UTF-8");
            	//翻译结果xml特殊字符处理
            	resp = resp.replace("<ResultText>", "<ResultText><![CDATA[").replace("</ResultText>", "]]></ResultText>");
            	Document doc = XmlUtil.parse(resp);
            	Element root = doc.getRootElement();
            	String errorNo = root.elementTextTrim("ErrorNo");
            	if("0".equals(errorNo)){
            		return root.elementTextTrim("ResultText");
            	}else{
            		StringBuilder sbResp = new StringBuilder(sbHint)
        	    		.append("翻译失败：[")
        	    		.append(errorNo)
        	    		.append("]").append(root.elementTextTrim("ResMessage"));
        	    	logger.error(sbResp);
            	}
	    	}else{
	    		StringBuilder sbResp = new StringBuilder(sbHint)
	    			.append("翻译失败：[")
	    			.append(conn.getResponseCode())
	    			.append("]").append(conn.getResponseMessage());
	    		logger.error(sbResp);
	    	}
            
		} catch(ProxyException e){
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error(sbHint+"翻译失败：", e);
		} finally{
			if(is!=null) try {is.close();} catch (Exception e) {};
			if(conn!=null) conn.disconnect();
		}
		return null;
	}

}
