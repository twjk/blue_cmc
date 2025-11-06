package com.qcmz.cmc.thrd;

import java.util.Date;

import org.apache.log4j.Logger;

import com.qcmz.cmc.config.TransConfig;
import com.qcmz.cmc.entity.CmcTransLog;
import com.qcmz.cmc.service.ITransLogService;
import com.qcmz.dmc.ws.provide.locator.TransLogWS;
import com.qcmz.dmc.ws.provide.vo.TransLogAddBean;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.thrd.AbstractThrd;
import com.qcmz.framework.util.IPUtil;
import com.qcmz.framework.util.SpringUtil;
import com.qcmz.framework.util.StringUtil;

/**
 * 类说明：保存翻译日志线程
 * 修改历史：
 */
public class TransLogThrd extends AbstractThrd {
	private static Logger logger = Logger.getLogger(TransLogThrd.class);
	
	private static final String TEST_SENTENCE = "出国翻译官";
	
	private TransLogAddBean wsbean;
	private CmcTransLog log;
	
	public TransLogThrd() {
		super();
	}

	public TransLogThrd(TransLogAddBean log) {
		super();
		this.wsbean = log;
	}
	
	public TransLogThrd(CmcTransLog log) {
		super();
		this.log = log;
	}

	/** 
	 * 
	 * 修改历史：
	 */
	@Override
	protected void work() {
		if(wsbean!=null){
			TransLogWS.saveTransLog(wsbean);
		}else if(log!=null){
			ITransLogService transLogService = (ITransLogService) SpringUtil.getBean("transLogServiceImpl");
			transLogService.addLogCache(log);
		}
	}
	
	public static void start(Long userId, String pushRegid, String uuid, String tmcode, String transType, String sessionId, Long proxyId
			, String from, String src, String to, String dst, int similar
			, boolean success, boolean hitCache, String result, Date begin, Date end
			, String ip, String lon, String lat, String platform, String platformVer, String serviceType){
		try {
			if(TransConfig.TRANS_LOG_ON==0) return;
			
			if(TEST_SENTENCE.equals(src)) return;	//检测用的不记录日志
			
			if(TransConfig.TRANS_LOG_ON==2){
				TransLogAddBean bean = new TransLogAddBean();
				bean.setUid(userId);
				bean.setPushRegid(pushRegid);
				bean.setUuid(uuid);
				bean.setTmcode(tmcode);
				bean.setTransType(transType);
				bean.setSid(sessionId);
				bean.setFrom(from);
				bean.setSrc(StringUtil.clearInvalidXmlChar(src));
				bean.setTo(to);
				bean.setDst(StringUtil.clearInvalidXmlChar(dst));
				bean.setSimilar(similar);
				bean.setStartTime(begin);
				bean.setEndTime(end);
				bean.setSuccess(success?SystemConstants.VALUE_1:SystemConstants.VALUE_0);
				bean.setHitCache(hitCache);
				bean.setResult(result);
				bean.setProxyId(proxyId);
				bean.setIp(ip);
				bean.setTransIp(IPUtil.LOCAL_IP);
				bean.setLon(lon);
				bean.setLat(lat);
				bean.setPlatform(platform);
				bean.setPlatformVersion(platformVer);
				bean.setServiceType(serviceType);
				
				new Thread(new TransLogThrd(bean)).start();
			}else if(TransConfig.TRANS_LOG_ON==1){
				CmcTransLog log = new CmcTransLog();
				log.setProxyid(proxyId);
				log.setToken(uuid);
				log.setFromlang(from);
				log.setSrc(src);
				log.setTolang(to);
				log.setDst(dst);
				log.setSimilar(similar);
				log.setStarttime(begin);
				log.setEndtime(end);
				log.setSucc(success?SystemConstants.VALUE_1:SystemConstants.VALUE_0);
				log.setTransresult(result);
				log.setReqip(ip);
				log.setReqcountry(null);
				log.setReqcity(null);
				log.setLon(lon);
				log.setLat(lat);
				log.setPlatform(platform);
				log.setAppversion(platformVer);
				
				new Thread(new TransLogThrd(log)).start();
			}
			
		} catch (Exception e) {
			logger.error("保存翻译日志异常", e);
		}
	}
}
