package com.qcmz.cmc.service;

import java.util.List;

import com.qcmz.cmc.entity.CmcFuncCap;
import com.qcmz.cmc.ws.provide.vo.CapAsrBean;
import com.qcmz.cmc.ws.provide.vo.CapLangDetectBean;
import com.qcmz.cmc.ws.provide.vo.CapOcrBean;
import com.qcmz.cmc.ws.provide.vo.CapTtsBean;

/**
 * 类说明：功能能力服务
 * 修改历史：
 */
public interface IFuncCapService {
	/**
	 * 获取指定功能支持的语言代码列表
	 * @return
	 * 修改历史：
	 */
	public List<String> findLang(Long funcId);
	/**
	 * 获取指定功能的能力列表
	 * @return
	 * 修改历史：
	 */
	public List<CmcFuncCap> findCap(Long funcId);
	/**
	 * 获取语音识别能力列表
	 * @return
	 * 修改历史：
	 */
	public List<CapAsrBean> findAsrCap();
	/**
	 * 获取语音合成能力列表
	 * @return
	 * 修改历史：
	 */
	public List<CapTtsBean> findTtsCap();
	/**
	 * 获取图像识别能力列表
	 * @return
	 * 修改历史：
	 */
	public List<CapOcrBean> findOcrCap();
	/**
	 * 获取语言检测能力列表
	 * @return
	 * 修改历史：
	 */
	public List<CapLangDetectBean> findLangDetectCap();
	/**
	 * 保存能力
	 * @param funcId
	 * @param list
	 * 修改历史：
	 */
	public void saveCap(Long funcId, List<CmcFuncCap> list);
}
