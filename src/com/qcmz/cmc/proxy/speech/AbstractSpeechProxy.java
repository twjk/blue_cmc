package com.qcmz.cmc.proxy.speech;

import java.io.File;

import com.qcmz.cmc.proxy.AbstractProxy;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public abstract class AbstractSpeechProxy extends AbstractProxy {
	/**
	 * 合成标准值
	 */
	protected int TTS_STANDARD = 100;
	
	/**
	 * 语音识别
	 * @param langCode 语言代码
	 * @param file 语音文件
	 * @return
	 * 修改历史：
	 */
	public abstract String asr(String langCode, File file);
	/**
	 * 语音合成
	 * @param langCode 语言代码
	 * @param text 文本
	 * @param langCode 语言代码
	 * @param gender 性别 M男F女
	 * @param speed 语速	100正常
	 * @return base64编码的语音文件
	 */
	public abstract String tts(String langCode, String text, String gender, Integer speed);
}
