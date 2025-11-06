package com.qcmz.cmc.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.constant.BusinessConstant;
import com.qcmz.cmc.dao.IFuncCapDao;
import com.qcmz.cmc.entity.CmcFuncCap;
import com.qcmz.cmc.ws.provide.vo.CapAsrBean;
import com.qcmz.cmc.ws.provide.vo.CapLangDetectBean;
import com.qcmz.cmc.ws.provide.vo.CapOcrBean;
import com.qcmz.cmc.ws.provide.vo.CapTtsBean;
import com.qcmz.framework.dao.impl.BaseDAO;

/**
 * 类说明：功能能力
 * 修改历史：
 */
@Repository
public class FuncCapDao extends BaseDAO implements IFuncCapDao {
	/**
	 * 获取指定功能支持的语言代码列表
	 * @return
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public List<String> findLang(Long funcId){
		return (List<String>) qryList("select distinct t.fromlang from CmcFuncCap t, CmcLang l where t.fromlang=l.langcode and l.status=1 and t.funcid=? order by l.sortindex", funcId);
	}
	/**
	 * 获取指定功能的能力列表
	 * @return
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public List<CmcFuncCap> findCap(Long funcId){
		return (List<CmcFuncCap>) qryList("from CmcFuncCap where funcid=? order by priority, capid", funcId);
	}

	/**
	 * 获取语音识别能力列表
	 * @return
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public List<CapAsrBean> findAsrCap(){
		StringBuilder sbHql = new StringBuilder();
		sbHql.append("select new com.qcmz.cmc.ws.provide.vo.CapAsrBean(")
			 .append("t.fromlang, t.proxyid, pl.asrcode, t.charge")
			 .append(")from CmcFuncCap t, CmcProxyLang pl")
			 .append(" where t.proxyid=pl.proxyid and t.fromlang=pl.langcode")
			 .append(" and t.funcid=?")
			 .append(" order by t.fromlang, t.priority, t.capid")
		;
		return (List<CapAsrBean>) qryList(sbHql.toString(), BusinessConstant.FUNCID_ASR);
	}
	
	/**
	 * 获取语音识别支持的语言列表
	 * @return
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public List<String> findAsrLang(){
		return (List<String>) qryList("select distinct fromlang from CmcFuncCap where funcid=?", BusinessConstant.FUNCID_ASR);
	}
	
	/**
	 * 获取语音合成能力列表
	 * @return
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public List<CapTtsBean> findTtsCap(){
		StringBuilder sbHql = new StringBuilder();
		sbHql.append("select new com.qcmz.cmc.ws.provide.vo.CapTtsBean(")
			 .append("t.fromlang, t.proxyid, pl.ttscode, pl.ttsmalevoice, pl.ttsfemalevoice, t.charge")
			 .append(")from CmcFuncCap t, CmcProxyLang pl")
			 .append(" where t.proxyid=pl.proxyid and t.fromlang=pl.langcode")
			 .append(" and t.funcid=?")
			 .append(" order by t.fromlang, t.priority, t.capid")
		;
		return (List<CapTtsBean>) qryList(sbHql.toString(), BusinessConstant.FUNCID_TTS);
	}
	
	/**
	 * 获取语音合成支持的语言列表
	 * @return
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public List<String> findTtsLang(){
		return (List<String>) qryList("select distinct fromlang from CmcFuncCap where funcid=?", BusinessConstant.FUNCID_TTS);
	}
	
	/**
	 * 获取图像识别能力列表
	 * @return
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public List<CapOcrBean> findOcrCap(){
		StringBuilder sbHql = new StringBuilder();
		sbHql.append("select new com.qcmz.cmc.ws.provide.vo.CapOcrBean(")
			 .append("t.fromlang, t.proxyid, pl.ocrcode")
			 .append(")from CmcFuncCap t, CmcProxyLang pl")
			 .append(" where t.proxyid=pl.proxyid and t.fromlang=pl.langcode")
			 .append(" and t.funcid=?")
			 .append(" order by t.fromlang, t.priority, t.capid")
		;
		return (List<CapOcrBean>) qryList(sbHql.toString(), BusinessConstant.FUNCID_OCR);
	}
	
	/**
	 * 获取图像识别支持的语言列表
	 * @return
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public List<String> findOcrLang(){
		return (List<String>) qryList("select distinct fromlang from CmcFuncCap where funcid=?", BusinessConstant.FUNCID_OCR);
	}
	
	/**
	 * 获取语言检测能力列表
	 * @return
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public List<CapLangDetectBean> findLangDetectCap(){
		StringBuilder sbHql = new StringBuilder();
		sbHql.append("select new com.qcmz.cmc.ws.provide.vo.CapLangDetectBean(")
			 .append("t.proxyid")
			 .append(")from CmcProxyFunc t where t.cmcProxy.status=1")
			 .append(" and t.funcid=?")
			 .append(" order by t.cmcProxy.priority, t.cmcProxy.proxyid")
		;
		return (List<CapLangDetectBean>) qryList(sbHql.toString(), BusinessConstant.FUNCID_LANGDETECT);
	}
	
	/**
	 * 清除指定功能的能力
	 * 修改历史：
	 */
	public void clearCap(Long funcId){
		delete("delete from CmcFuncCap where funcid=?", funcId);
	}
}
