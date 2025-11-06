package com.qcmz.cmc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.dao.IFuncCapDao;
import com.qcmz.cmc.entity.CmcFuncCap;
import com.qcmz.cmc.service.IFuncCapService;
import com.qcmz.cmc.ws.provide.vo.CapAsrBean;
import com.qcmz.cmc.ws.provide.vo.CapLangDetectBean;
import com.qcmz.cmc.ws.provide.vo.CapOcrBean;
import com.qcmz.cmc.ws.provide.vo.CapTtsBean;

/**
 * 类说明：功能能力服务
 * 修改历史：
 */
@Service
public class FuncCapServiceImpl implements IFuncCapService {
	@Autowired
	private IFuncCapDao funcCapDao;
	
	/**
	 * 获取语言列表
	 * @return
	 * 修改历史：
	 */
	public List<String> findLang(Long funcId){
		return funcCapDao.findLang(funcId);
	}
	
	/**
	 * 获取指定功能的能力列表
	 * @return
	 * 修改历史：
	 */
	public List<CmcFuncCap> findCap(Long funcId){
		return funcCapDao.findCap(funcId);
	}
	
	/**
	 * 获取语音识别能力列表
	 * @return
	 * 修改历史：
	 */
	public List<CapAsrBean> findAsrCap(){
		return funcCapDao.findAsrCap();
	}
	
	/**
	 * 获取语音合成能力列表
	 * @return
	 * 修改历史：
	 */
	public List<CapTtsBean> findTtsCap(){
		return funcCapDao.findTtsCap();
	}
	
	/**
	 * 获取图像识别能力列表
	 * @return
	 * 修改历史：
	 */
	public List<CapOcrBean> findOcrCap(){
		return funcCapDao.findOcrCap();
	}
	
	/**
	 * 获取语言检测能力列表
	 * @return
	 * 修改历史：
	 */
	public List<CapLangDetectBean> findLangDetectCap(){
		return funcCapDao.findLangDetectCap();
	}
	
	/**
	 * 保存能力
	 * @param funcId
	 * @param list
	 * 修改历史：
	 */
	public void saveCap(Long funcId, List<CmcFuncCap> list){
		funcCapDao.clearCap(funcId);
		if(list!=null && !list.isEmpty()){
			funcCapDao.saveOrUpdateAll(list);
		}
	}
}
