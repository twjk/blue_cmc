package com.qcmz.cmc.process;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.cache.FuncCapMap;
import com.qcmz.cmc.proxy.ProxyMgr;
import com.qcmz.cmc.proxy.speech.AbstractSpeechProxy;
import com.qcmz.cmc.proxy.speech.baidu.BaiduSpeechProxy;
import com.qcmz.cmc.ws.provide.vo.CapAsrBean;
import com.qcmz.cmc.ws.provide.vo.CapTtsBean;
import com.qcmz.cmc.ws.provide.vo.SpeechAsrBean;
import com.qcmz.cmc.ws.provide.vo.SpeechAsrResult;
import com.qcmz.cmc.ws.provide.vo.SpeechTtsBean;
import com.qcmz.cmc.ws.provide.vo.SpeechTtsResult;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.exception.ParamException;
import com.qcmz.framework.exception.SystemException;
import com.qcmz.framework.media.JaveUtil;
import com.qcmz.framework.util.FilePathUtil;
import com.qcmz.framework.util.FileTypeUtil;
import com.qcmz.framework.util.FileUtil;
import com.qcmz.framework.util.StringUtil;

@Component
public class SpeechProcess {
	@Autowired
	private FuncCapMap funcCapMap;
	@Autowired
	private ProxyMgr proxyMgr;
	@Autowired
	private BaiduSpeechProxy baiduSpeechProxy;
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	public static List<String> asrFileTypes = new ArrayList<String>();
	static{
		asrFileTypes.add("mp3");
		asrFileTypes.add("m4a");
		asrFileTypes.add("wav");
	}
	
	/**
	 * 语音识别
	 * 微信mp3，后续考虑并入asr(SpeechAsrBean bean)实现 
	 * @param file
	 * @return
	 * @exception ParamException,SystemException,ProxyException
	 */
	public SpeechAsrResult asr(String langCode, File file){
		//1.校验
		String fileType = FileTypeUtil.getMultimediaType(file);
		if(StringUtil.isBlankOrNull(fileType)
				|| !asrFileTypes.contains(fileType)){
			throw new ParamException("语音类型有误");
		}
		
		//2.转换
		StringBuilder sbDestPath = new StringBuilder();
		sbDestPath.append(FilePathUtil.getAbsolutePath4Temp())
				  .append("/").append(file.getName())
				  ;
		File destFile = new File(sbDestPath.toString());
		boolean convert = JaveUtil.mp3_pcm(file, destFile, 192000, 16000, 1);
		if(!convert){
			throw new SystemException("语音转换失败");
		}
		
		//3.识别
		String text = baiduSpeechProxy.asr(langCode, destFile);
		
		//删除识别文件
		FileUtil.deleteQuietly(destFile);
		
		//结果
		SpeechAsrResult result = new SpeechAsrResult();
		result.setText(text);
		
		return result;
	}
	
	/**
	 * 语音识别
	 * @param langCode 语言代码
	 * @param file 音频文件
	 * @param proxyId 指定代理编号
	 * @return
	 * @exception ParamException、ProxyException
	 */
	public SpeechAsrResult asr(SpeechAsrBean bean){
		//校验
		String fileType = FileTypeUtil.getMultimediaType(bean.getFile());
		if(StringUtil.isBlankOrNull(fileType)
				|| !asrFileTypes.contains(fileType)){
			throw new ParamException("语音类型有误");
		}
		
		//通道
		AbstractSpeechProxy proxy = null;
		if(bean.getProxyId()!=null){
			proxy = proxyMgr.getSpeechProxy(bean.getProxyId());
		}else{
			List<CapAsrBean> asrCaps = funcCapMap.findAsrCap();
			for (CapAsrBean capAsrBean : asrCaps) {
				if(capAsrBean.getLangCode().equals(bean.getLangCode())){
					proxy = proxyMgr.getSpeechProxy(capAsrBean.getProxyId());
					if(proxy!=null) break;
				}
			}
		}
		if(proxy==null){
			logger.error("语音识别没有可用通道："+bean.getLangCode());
			throw new ParamException("该语言不支持");
		}
		
		//语音识别
		String text = proxy.asr(bean.getLangCode(), bean.getFile());
		
		//结果
		SpeechAsrResult result = new SpeechAsrResult();
		result.setText(text);
		return result;
	}
	
	/**
	 * 语音合成
	 * @param langCode
	 * @param text
	 * @param gender
	 * @param speed
	 * @param proxyId
	 * @return
	 * @exception ParamException、ProxyException
	 */
	public SpeechTtsResult tts(SpeechTtsBean bean){
		SpeechTtsResult result = null;
		
		//默认值处理
		if(StringUtil.isBlankOrNull(bean.getGender())){
			bean.setGender(DictConstants.DICT_SEX_F);
		}
		if(bean.getSpeed()==null || bean.getSpeed()<=0){
			bean.setSpeed(100);
		}
		
		//通道
		AbstractSpeechProxy proxy = null;
		if(bean.getProxyId()!=null){
			proxy = proxyMgr.getSpeechProxy(bean.getProxyId());
		}else{
			List<CapTtsBean> ttsCaps = funcCapMap.findTtsCap();
			for (CapTtsBean ttsCap : ttsCaps) {
				if(ttsCap.getLangCode().equals(bean.getLangCode())){
					proxy = proxyMgr.getSpeechProxy(ttsCap.getProxyId());
					if(proxy!=null) break;
				}
			}
		}
		if(proxy==null){
			logger.error("语音合成没有可用通道："+bean.getLangCode());
			throw new ParamException("该语言不支持");
		}
		
		//语音合成
		String audioContent = proxy.tts(bean.getLangCode(), bean.getText(), bean.getGender(), bean.getSpeed());
		
		//结果
		result = new SpeechTtsResult();
		result.setAudioContent(audioContent);
		return result;
	}
}
