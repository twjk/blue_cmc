package com.qcmz.cmc.dao;

import java.util.List;

import com.qcmz.cmc.entity.CmcFuncCap;
import com.qcmz.cmc.ws.provide.vo.CapAsrBean;
import com.qcmz.cmc.ws.provide.vo.CapLangDetectBean;
import com.qcmz.cmc.ws.provide.vo.CapOcrBean;
import com.qcmz.cmc.ws.provide.vo.CapTtsBean;
import com.qcmz.framework.dao.IBaseDAO;

/**
 * 类说明：功能能力
 * 修改历史：
 */
public interface IFuncCapDao extends IBaseDAO {
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
	 * 获取语音识别支持的语言列表
	 * @return
	 * 修改历史：
	 */
	public List<String> findAsrLang();
	/**
	 * 获取语音合成能力列表
	 * @return
	 * 修改历史：
	 */
	public List<CapTtsBean> findTtsCap();
	/**
	 * 获取语音合成支持的语言列表
	 * @return
	 * 修改历史：
	 */
	public List<String> findTtsLang();
	/**
	 * 获取图像识别能力列表
	 * @return
	 * 修改历史：
	 */
	public List<CapOcrBean> findOcrCap();
	/**
	 * 获取图像识别支持的语言列表
	 * @return
	 * 修改历史：
	 */
	public List<String> findOcrLang();
	/**
	 * 获取语言检测能力列表
	 * @return
	 * 修改历史：
	 */
	public List<CapLangDetectBean> findLangDetectCap();
	/**
	 * 清除指定功能的能力
	 * 修改历史：
	 */
	public void clearCap(Long funcId);
}
