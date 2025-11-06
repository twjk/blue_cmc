package com.qcmz.cmc.ws.provide.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.cache.EvalMap;
import com.qcmz.cmc.cache.FuncCapMap;
import com.qcmz.cmc.cache.LangMap;
import com.qcmz.cmc.cache.SceneMap;
import com.qcmz.cmc.process.ProxyAccountProcess;
import com.qcmz.cmc.process.TransConfigProcess;
import com.qcmz.cmc.util.VersionUtil;
import com.qcmz.cmc.ws.provide.vo.CapOcrBean;
import com.qcmz.cmc.ws.provide.vo.CapQueryResp;
import com.qcmz.cmc.ws.provide.vo.CapsBean;
import com.qcmz.cmc.ws.provide.vo.EvalQueryReq;
import com.qcmz.cmc.ws.provide.vo.EvalQueryResp;
import com.qcmz.cmc.ws.provide.vo.Lang4SpeechBean;
import com.qcmz.cmc.ws.provide.vo.Lang4SpeechResp;
import com.qcmz.cmc.ws.provide.vo.LangReq;
import com.qcmz.cmc.ws.provide.vo.LangResp;
import com.qcmz.cmc.ws.provide.vo.ProxyAccountQueryResp;
import com.qcmz.cmc.ws.provide.vo.SceneQueryReq;
import com.qcmz.cmc.ws.provide.vo.SceneQueryResp;
import com.qcmz.cmc.ws.provide.vo.SpeechAccountQueryResp;
import com.qcmz.cmc.ws.provide.vo.TransConfigQueryReq;
import com.qcmz.cmc.ws.provide.vo.TransConfigQueryResp;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.util.ArrayUtil;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.framework.ws.BaseInterface;
import com.qcmz.framework.ws.vo.Request;
import com.qcmz.srm.client.util.SrmClient;

/**
 * 类说明：翻译配置接口实现
 * 修改历史：
 */
@Component
public class TransConfigInterface extends BaseInterface {
	@Autowired
	private LangMap langMap;
	@Autowired
	private FuncCapMap funcCapMap;
	@Autowired
	private EvalMap evalMap;
	@Autowired
	private SceneMap sceneMap;
	
	@Autowired
	private ProxyAccountProcess proxyAccountProcess;
	@Autowired
	private TransConfigProcess transConfigProcess;
	
	/**
	 * 获取配置
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public TransConfigQueryResp findConfig(TransConfigQueryReq req, String interfaceType, String reqIp){
		TransConfigQueryResp resp = new TransConfigQueryResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className);
			
			//数据校验
			
			//处理
			if(resp.successed()){
				String platform = SrmClient.getPlatform(req.getAuthAccount());
				String serviceType = SrmClient.getServiceType(req.getAuthAccount());
				resp.setResult(transConfigProcess.findConfig(serviceType, platform, req.getAppVer(), req.getLanguage()));
			}
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		
		return resp;
	} 
	
	/**
	 * 获取语言基础信息
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public LangResp findLang(LangReq req, String interfaceType, String reqIp){
		LangResp resp = new LangResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className);
			
			//数据校验
			if(resp.successed()){
				if(StringUtil.isBlankOrNull(req.getLangType())){
					resp.errorParam("语言类型为空");
				}
			}
			
			//处理
			if(resp.successed()){
				resp.getResult().addAll(langMap.findLang(req.getLangType(), req.getLanguage()));
			}
		} catch (DataException e) {
			resp.errorData(e.getMessage());
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}

		return resp;
	}
	
	/**
	 * 获取语音语言基础信息
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public Lang4SpeechResp findLang4Speech(Request req, String interfaceType, String reqIp){
		Lang4SpeechResp resp = new Lang4SpeechResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className);
			
			//数据校验

			//处理
			if(resp.successed()){
				String platform = SrmClient.getPlatform(req.getAuthAccount());
				String serviceType = SrmClient.getServiceType(req.getAuthAccount());
				List<Lang4SpeechBean> list = langMap.findLang4Speech(req.getLanguage());
				if(VersionUtil.compat2(serviceType, platform, req.getAppVer())){
					for (Lang4SpeechBean bean: list) {
						if(ArrayUtil.contain(VersionUtil.LANGS, bean.getLangCode())){
							resp.getResultList().add(bean);
						}
					}
				}else if(VersionUtil.compat3(serviceType, platform, req.getAppVer())){
					for (Lang4SpeechBean bean: list) {
						if(ArrayUtil.contain(VersionUtil.LANGS, bean.getLangCode())
								|| ArrayUtil.contain(VersionUtil.LANGS_4, bean.getLangCode())){
							resp.getResultList().add(bean);
						}
					}
				}else if(DictConstants.DICT_PLATFORM_IOS.equals(platform)){
					for (Lang4SpeechBean bean : list) {
						if(!ArrayUtil.contain(VersionUtil.LANGS_UNSUPPORT_IOS, bean.getLangCode())){
							resp.getResultList().add(bean);
						}
					}
				}else{
					resp.getResultList().addAll(list);
				}
			}
		} catch (DataException e) {
			resp.errorData(e.getMessage());
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		
		return resp;
	}
	
	/**
	 * 获取代理帐户
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public ProxyAccountQueryResp findProxyAccount(Request req, String interfaceType, String reqIp){
		ProxyAccountQueryResp resp = new ProxyAccountQueryResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className);
			
			//数据校验
			
			//处理
			if(resp.successed()){
				resp.setResultList(proxyAccountProcess.findProxyAccount(
						SrmClient.getServiceType(req.getAuthAccount())
						, SrmClient.getPlatform(req.getAuthAccount())
						, req.getAppVer()));
			}
		} catch (DataException e) {
			resp.errorData(e.getMessage());
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}

		return resp;
	}
	
	/**
	 * 获取代理帐户
	 * 用于兼容原接口
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	@Deprecated
	public SpeechAccountQueryResp findSpeechAccount(Request req, String interfaceType, String reqIp){
		SpeechAccountQueryResp resp = new SpeechAccountQueryResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className);
			
			//数据校验
			
			//处理
			if(resp.successed()){
				resp.setResults(proxyAccountProcess.findSpeechAccount4AndroidOld());
			}
		} catch (DataException e) {
			resp.errorData(e.getMessage());
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		
		return resp;
	}
	
	/**
	 * 获取功能能力
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public CapQueryResp findCap(Request req, String interfaceType, String reqIp){
		CapQueryResp resp = new CapQueryResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className);
			
			//数据校验
			
			//处理
			if(resp.successed()){
				String serviceType = SrmClient.getServiceType(req.getAuthAccount());
				String platform = SrmClient.getPlatform(req.getAuthAccount());
				resp.setResult(funcCapMap.findCaps(serviceType, platform, req.getAppVer()));
			}
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}

		return resp;
	} 
	
	/**
	 * 获取语音识别能力
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public CapQueryResp findAsrCap(Request req, String interfaceType, String reqIp){
		CapQueryResp resp = new CapQueryResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className);
			
			//数据校验
			
			//处理
			if(resp.successed()){
				String serviceType = SrmClient.getServiceType(req.getAuthAccount());
				String platform = SrmClient.getPlatform(req.getAuthAccount());
				
				CapsBean capsBean = new CapsBean();
				capsBean.setAsrCaps(funcCapMap.findAsrCap(serviceType, platform, req.getAppVer()));
				resp.setResult(capsBean);
			}
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}

		return resp;
	}
	
	/**
	 * 获取语音合成能力
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public CapQueryResp findTtsCap(Request req, String interfaceType, String reqIp){
		CapQueryResp resp = new CapQueryResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className);
			
			//数据校验
			
			//处理
			if(resp.successed()){				
				String serviceType = SrmClient.getServiceType(req.getAuthAccount());
				String platform = SrmClient.getPlatform(req.getAuthAccount());
				
				CapsBean capsBean = new CapsBean();
				capsBean.setTtsCaps(funcCapMap.findTtsCap(serviceType, platform, req.getAppVer()));
				resp.setResult(capsBean);
			}
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		
		return resp;
	}
	
	/**
	 * 获取图像识别能力
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public CapQueryResp findOcrCap(Request req, String interfaceType, String reqIp){
		CapQueryResp resp = new CapQueryResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className);
			
			//数据校验
			
			//处理
			if(resp.successed()){
				List<CapOcrBean> list = null;
				if(VersionUtil.compat1(SrmClient.getServiceType(req.getAuthAccount())
						, SrmClient.getPlatform(req.getAuthAccount())
						,req.getAppVer())){
					list = funcCapMap.findOcrCap4Compat1();
				}else{
					list = funcCapMap.findOcrCap();
				}
				
				CapsBean capsBean = new CapsBean();
				capsBean.setOcrCap(list);
				resp.setResult(capsBean);
				
			}
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}

		return resp;
	}
	
	/**
	 * 获取图像识别能力
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public CapQueryResp findLangDetectCap(Request req, String interfaceType, String reqIp){
		CapQueryResp resp = new CapQueryResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className);
			
			//数据校验
			
			//处理
			if(resp.successed()){
				CapsBean capsBean = new CapsBean();
				capsBean.setLangDetectCaps(funcCapMap.findLangDetectCap());
				resp.setResult(capsBean);
			}
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		
		return resp;
	}
	
	/**
	 * 获取评价字典
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public EvalQueryResp findEval(EvalQueryReq req, String interfaceType, String reqIp){
		EvalQueryResp resp = new EvalQueryResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className);
			
			//数据校验
			
			//处理
			if(resp.successed()){
				resp.setResult(evalMap.findValidEval(req.getEvalType()));
			}
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}

		return resp;
	}
	
	/**
	 * 获取场景字典
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public SceneQueryResp findScene(SceneQueryReq req, String interfaceType, String reqIp){
		SceneQueryResp resp = new SceneQueryResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className);
			
			//数据校验
			
			//处理
			if(resp.successed()){
				resp.setResult(sceneMap.findValidScene(req.getTransType()));
			}
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		
		return resp;
	}
}
