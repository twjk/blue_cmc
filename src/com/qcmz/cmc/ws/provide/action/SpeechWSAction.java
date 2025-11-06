package com.qcmz.cmc.ws.provide.action;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.ws.provide.service.SpeechInterface;
import com.qcmz.cmc.ws.provide.vo.SpeechAsrReq;
import com.qcmz.cmc.ws.provide.vo.SpeechTtsReq;
import com.qcmz.framework.action.BaseWSAction;

public class SpeechWSAction extends BaseWSAction {
	@Autowired
	private SpeechInterface speechInterface;
	
	/**
	 * 语言代码
	 */
	private String langCode;
	/**
	 * 语音文件
	 */
	private File file;
	/**
	 * 合成文本
	 */
	private String text;
	/**
	 * 性别，M男F女
	 */
	private String gender;
	/**
	 * 语速，100为正常
	 */
	private Integer speed;
	/**
	 * 代理编号
	 */
	private Long proxyId;
	
	/**
	 * 语音识别
	 */
	public String asr(){
		SpeechAsrReq req = new SpeechAsrReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.getBean().setLangCode(langCode);
		req.getBean().setFile(file);
		req.getBean().setProxyId(proxyId);
		jsonObj = speechInterface.asr(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}

	public String tts(){
		SpeechTtsReq req = new SpeechTtsReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.getBean().setLangCode(langCode);
		req.getBean().setText(text);
		req.getBean().setGender(gender);
		req.getBean().setSpeed(speed);
		req.getBean().setProxyId(proxyId);
		
		jsonObj = speechInterface.tts(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	public String getLangCode() {
		return langCode;
	}

	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Integer getSpeed() {
		return speed;
	}

	public void setSpeed(Integer speed) {
		this.speed = speed;
	}

	public Long getProxyId() {
		return proxyId;
	}

	public void setProxyId(Long proxyId) {
		this.proxyId = proxyId;
	}
}
