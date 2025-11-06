package com.qcmz.cmc.ws.provide.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.ws.provide.service.TransConfigInterface;
import com.qcmz.cmc.ws.provide.vo.EvalQueryReq;
import com.qcmz.cmc.ws.provide.vo.LangReq;
import com.qcmz.cmc.ws.provide.vo.SceneQueryReq;
import com.qcmz.cmc.ws.provide.vo.TransConfigQueryReq;
import com.qcmz.framework.action.BaseWSAction;
import com.qcmz.framework.ws.vo.Request;
import com.qcmz.framework.ws.vo.Response;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class TransConfigWSAction extends BaseWSAction {
	@Autowired
	private TransConfigInterface transConfigInterface;

	/**
	 * 语言类型，01通用02语音03翻译
	 */
	private String langType;
	/**
	 * 翻译类型
	 */
	private String transType;
	/**
	 * 评价类型
	 */
	private Integer evalType;
	/**
	 * 帐户编号
	 */
	private Long accountId;
	/**
	 * 访问次数
	 */
	private Long callCount;
	
	/**
	 * 获取配置
	 * @return
	 */
	public String findConfig(){
		TransConfigQueryReq req = new TransConfigQueryReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		req.setLanguage(language);
		
		try {
			jsonObj = transConfigInterface.findConfig(req, interfaceType, getRemoteAddr());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return JSON;
	}
	
	/**
	 * 获取基础语言列表
	 * @return
	 */
	public String findBaseLang(){
		LangReq req = new LangReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		req.setLanguage(language);
		req.setLangType(langType);
		
		jsonObj = transConfigInterface.findLang(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 获取语音语言列表 
	 */
	public String findLang(){
		Request req = new Request();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		req.setLanguage(language);
		
		jsonObj = transConfigInterface.findLang4Speech(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 获取代理帐户列表
	 */
	public String findProxyAccount(){
		Request req = new Request();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		jsonObj = transConfigInterface.findProxyAccount(req, interfaceType, getRemoteAddr());
		return JSON;
	}
	
	/**
	 * 添加访问次数
	 */
	@Deprecated
	public String addCallCount(){
		jsonObj = new Response();
		return JSON;
	}
	
	
	/**
	 * 获取语音识别能力
	 */
	public String findCap(){
		Request req = new Request();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		jsonObj = transConfigInterface.findCap(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 获取语音识别能力
	 */
	public String findAsrCap(){
		Request req = new Request();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		jsonObj = transConfigInterface.findAsrCap(req, interfaceType, getRemoteAddr());
		return JSON;
	}
	
	/**
	 * 获取语音合成能力
	 */
	public String findTtsCap(){
		Request req = new Request();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		jsonObj = transConfigInterface.findTtsCap(req, interfaceType, getRemoteAddr());
		return JSON;
	}
	
	/**
	 * 获取图像识别能力
	 */
	public String findOcrCap(){
		Request req = new Request();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		jsonObj = transConfigInterface.findOcrCap(req, interfaceType, getRemoteAddr());
		return JSON;
	}
	
	/**
	 * 获取语言检测能力
	 */
	public String findLangDetectCap(){
		Request req = new Request();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		jsonObj = transConfigInterface.findLangDetectCap(req, interfaceType, getRemoteAddr());
		return JSON;
	}
	
	/**
	 * 获取评价字典
	 */
	public String findEval(){
		EvalQueryReq req = new EvalQueryReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		req.setEvalType(evalType);
		
		jsonObj = transConfigInterface.findEval(req, interfaceType, getRemoteAddr());
		return JSON;
	}
	
	/**
	 * 获取场景字典
	 */
	public String findScene(){
		SceneQueryReq req = new SceneQueryReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		req.setTransType(transType);
		
		jsonObj = transConfigInterface.findScene(req, interfaceType, getRemoteAddr());
		return JSON;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Long getCallCount() {
		return callCount;
	}

	public void setCallCount(Long callCount) {
		this.callCount = callCount;
	}

	public String getLangType() {
		return langType;
	}

	public void setLangType(String langType) {
		this.langType = langType;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public Integer getEvalType() {
		return evalType;
	}

	public void setEvalType(Integer evalType) {
		this.evalType = evalType;
	}
}
