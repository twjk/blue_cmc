package com.qcmz.cmc.cache;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.constant.BusinessConstant;
import com.qcmz.cmc.service.IFuncCapService;
import com.qcmz.cmc.util.VersionUtil;
import com.qcmz.cmc.ws.provide.vo.CapAsrBean;
import com.qcmz.cmc.ws.provide.vo.CapLangDetectBean;
import com.qcmz.cmc.ws.provide.vo.CapOcrBean;
import com.qcmz.cmc.ws.provide.vo.CapTtsBean;
import com.qcmz.cmc.ws.provide.vo.CapsBean;
import com.qcmz.framework.cache.AbstractCacheMap;
import com.qcmz.framework.util.BeanUtil;

/**
 * 类说明：能力缓存
 * 修改历史：
 */
@Component
public class FuncCapMap extends AbstractCacheMap {
	@Autowired
	private IFuncCapService funcCapService;

	private CapsBean bean = null;
	/**
	 * OCR能力只留下灵云代理
	 */
	private CapsBean bean4Compat1 = null;
	
	/** 
	 * 
	 * 修改历史：
	 */
	@Override @PostConstruct
	protected void init() {
		CapsBean beanTemp = new CapsBean();
		beanTemp.setAsrCaps(funcCapService.findAsrCap());
		beanTemp.setTtsCaps(funcCapService.findTtsCap());
		beanTemp.setOcrCap(funcCapService.findOcrCap());
		beanTemp.setLangDetectCaps(funcCapService.findLangDetectCap());
		
		bean = beanTemp;
		
		//兼容处理：OCR能力只留下灵云代理
		CapsBean bean4Compat1Temp = new CapsBean();
		bean4Compat1Temp.setAsrCaps(bean.getAsrCaps());
		bean4Compat1Temp.setTtsCaps(bean.getTtsCaps());
		bean4Compat1Temp.setLangDetectCaps(bean.getLangDetectCaps());
		List<CapOcrBean> list = new ArrayList<CapOcrBean>();
		for (CapOcrBean ocr : bean.getOcrCap()) {
			if(BusinessConstant.PROXYID_HCICLOUD.equals(ocr.getProxyId())){
				list.add(ocr);
			}
		}
		bean4Compat1Temp.setOcrCap(list);
		bean4Compat1 = bean4Compat1Temp;
	}
	
	/**
	 * 获取能力列表
	 * @return
	 * 修改历史：
	 */
	public CapsBean findCaps(){
		if(safeInit(bean)){
			return bean;
		}
		return new CapsBean();
	}
	
	/**
	 * 获取能力列表
	 * @return
	 * 修改历史：
	 */
	public CapsBean findCaps(String serviceType, String platform, String version){
		if(safeInit(bean)){
			if(VersionUtil.compat1(serviceType, platform, version)){
				return bean4Compat1;
			}else if(!VersionUtil.enableIosSpeech(serviceType, platform, version)
					|| VersionUtil.disableMicrosoftAsr(serviceType, platform, version)
					|| !VersionUtil.enableBaiduSpeech(serviceType, platform, version)){
				CapsBean result = new CapsBean();
				List<CapAsrBean> asrList = new ArrayList<CapAsrBean>();
				List<CapTtsBean> ttsList = new ArrayList<CapTtsBean>();
				Iterator<CapAsrBean> asrIt = null;
				Iterator<CapTtsBean> ttsIt = null;
				CapAsrBean asr = null;
				CapTtsBean tts = null;
				
				BeanUtil.cloneList(asrList, bean.getAsrCaps());
				BeanUtil.cloneList(ttsList, bean.getTtsCaps());				
				
				asrIt = asrList.iterator();
				while (asrIt.hasNext()) {
					asr = asrIt.next();
					if(BusinessConstant.PROXYID_BINGSPEECH.equals(asr.getProxyId())
							|| BusinessConstant.PROXYID_AZURESPEECH.equals(asr.getProxyId())){
						if(VersionUtil.disableMicrosoftAsr(serviceType, platform, version)){
							asrIt.remove();
						}
					}else if(BusinessConstant.PROXYID_IOS.equals(asr.getProxyId())){
						if(!VersionUtil.enableIosSpeech(serviceType, platform, version)){
							asrIt.remove();
						}
					}else if(BusinessConstant.PROXYID_BAIDUSPEECH.equals(asr.getProxyId())){
						if(!VersionUtil.enableBaiduSpeech(serviceType, platform, version)){
							asrIt.remove();
						}
					}
				}
				
				ttsIt = ttsList.iterator();
				while (ttsIt.hasNext()) {
					tts = ttsIt.next();
					if(BusinessConstant.PROXYID_IOS.equals(tts.getProxyId())){
						if(!VersionUtil.enableIosSpeech(serviceType, platform, version)){
							ttsIt.remove();
						}
					}else if(BusinessConstant.PROXYID_BAIDUSPEECH.equals(tts.getProxyId())){
						if(!VersionUtil.enableBaiduSpeech(serviceType, platform, version)){
							ttsIt.remove();
						}
					}
				}
				
				result.setAsrCaps(asrList);
				result.setTtsCaps(ttsList);
				result.setLangDetectCaps(bean.getLangDetectCaps());
				result.setOcrCap(bean.getOcrCap());
				return result;
			}else{
				return bean;
			}
		}
		return new CapsBean();
	}
	
	/**
	 * 获取语音识别能力列表
	 * @return
	 * 修改历史：
	 */
	public List<CapAsrBean> findAsrCap(){
		if(safeInit(bean)){
			return bean.getAsrCaps();
		}
		return new ArrayList<CapAsrBean>();
	}	

	/**
	 * 获取语音识别能力列表
	 * @return
	 * 修改历史：
	 */
	public List<CapAsrBean> findAsrCap(String serviceType, String platform, String version){
		CapsBean bean = findCaps(serviceType, platform, version);
		return bean.getAsrCaps();
	}
		
	/**
	 * 获取语音合成能力列表
	 * @return
	 * 修改历史：
	 */
	public List<CapTtsBean> findTtsCap(){
		if(safeInit(bean)){
			return bean.getTtsCaps();
		}
		return new ArrayList<CapTtsBean>(); 
	}
	
	/**
	 * 获取语音合成能力列表
	 * @return
	 * 修改历史：
	 */
	public List<CapTtsBean> findTtsCap(String serviceType, String platform, String version){
		CapsBean bean = findCaps(serviceType, platform, version);
		return bean.getTtsCaps();
	}
	
	/**
	 * 获取图像识别能力列表
	 * @return
	 * 修改历史：
	 */
	public List<CapOcrBean> findOcrCap(){
		if(safeInit(bean)){
			return bean.getOcrCap();
		}
		return new ArrayList<CapOcrBean>();
	}
	
	/**
	 * 获取图像识别能力列表，只留灵云
	 * @return
	 * 修改历史：
	 */
	public List<CapOcrBean> findOcrCap4Compat1(){
		if(safeInit(bean4Compat1)){
			return bean4Compat1.getOcrCap();
		}
		return new ArrayList<CapOcrBean>();
	}
	
	/**
	 * 获取语言检测能力列表
	 * @return
	 * 修改历史：
	 */
	public List<CapLangDetectBean> findLangDetectCap(){
		if(safeInit(bean)){
			return bean.getLangDetectCaps();
		}
		return new ArrayList<CapLangDetectBean>();
	}
	
	/** 
	 * @param obj
	 * 修改历史：
	 */
	@Override
	public void delete(Object obj) {
	}

	/** 
	 * @param obj
	 * 修改历史：
	 */
	@Override
	public void update(Object obj) {
	}

}
