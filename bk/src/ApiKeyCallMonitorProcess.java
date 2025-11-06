package com.qcmz.cmc.process;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.cache.ApiKeyMap;
import com.qcmz.cmc.config.TransConfig;
import com.qcmz.cmc.dao.IApiKeyDao;
import com.qcmz.cmc.vo.ApiKeyBean;
import com.qcmz.framework.util.MailUtil;

/**
 * 类说明：对该类的主要功能进行说明
 * @author 李炳煜
 * @version 1.0
 * 创建日期：Jun 5, 2014 3:26:54 PM
 * 修改历史：
 */
@Component
public class ApiKeyCallMonitorProcess {
	@Autowired
	private IApiKeyDao apiKeyDao;
	@Autowired
	private ApiKeyMap apiKeyMap;
	
	private static List<String> mails = new ArrayList<String>();
	static{
		mails.add("abing139@139.com");
		mails.add("19977396@qq.com");
	}
	
	/**
	 * 监控apikey当天访问量
	 * 
	 * @author 李炳煜
	 * @version 1.0
	 * 创建日期：Jun 5, 2014 3:28:37 PM
	 * 修改历史：
	 */
	public void monitorApiKeyTodayCall(){
		Map<Long, Long> countMap = apiKeyDao.findTodayCallCount();

		StringBuilder sbMail = new StringBuilder();
		ApiKeyBean bean = null;
		for (Long keyId : countMap.keySet()) {
			bean = apiKeyMap.getBean(keyId);
			if(countMap.get(keyId)>TransConfig.GOOGLE_APIKEY_DAYMAX){
				sbMail.append("Type[").append(bean.getApitype()).append("]")
					  .append(" Key[").append(bean.getApikey()).append("]")
					  .append(" Count[").append(countMap.get(keyId)).append("]<br/>");
			}
		}
		if(sbMail.length()>0){
			MailUtil.sendHtmlMail(mails, null, "ApiKey日访问量超限", sbMail.toString());
		}
	
	}
}
