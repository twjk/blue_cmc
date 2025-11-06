package com.qcmz.cmc.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.constant.BeanConstant;
import com.qcmz.cmc.constant.BusinessConstant;
import com.qcmz.cmc.entity.CmcFuncCap;
import com.qcmz.cmc.entity.CmcLang;
import com.qcmz.cmc.entity.CmcProxy;
import com.qcmz.cmc.process.CacheSynProcess;
import com.qcmz.cmc.service.IFuncCapService;
import com.qcmz.cmc.service.ILangService;
import com.qcmz.cmc.service.IProxyService;
import com.qcmz.cmc.util.CmcUtil;
import com.qcmz.framework.action.BaseAction;
import com.qcmz.framework.exception.SystemException;
import com.qcmz.framework.util.StringUtil;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class FuncCapAction extends BaseAction {
	@Autowired
	private IProxyService proxyService;
	@Autowired
	private ILangService langService;
	@Autowired
	private IFuncCapService funcCapService;
	
	private Long funcId;
	private List<CmcProxy> proxys;
	private List<CmcLang> langs;
	private Map<String, CmcFuncCap> map = new HashMap<String, CmcFuncCap>();
	
	//初始化
	public String doEdit(){
		proxys = proxyService.findValid(funcId);
		langs = langService.findValid(CmcUtil.getLangTypeByFuncId(funcId));
		List<CmcFuncCap> list = funcCapService.findCap(funcId);
		StringBuilder sbKey = null;
		for (CmcFuncCap bean : list) {
			sbKey = new StringBuilder()
				.append(bean.getFromlang())
				.append("|").append(bean.getTolang())
				.append("|").append(bean.getProxyid())
			;
			map.put(sbKey.toString(), bean);
		}
		
		if(BusinessConstant.FUNCID_TRANSLATE.equals(funcId)){
			return "langlang";
		}else{
			return "lang";
		}
	}

	//保存
	public String doSave(){
		try {
			List<CmcFuncCap> list = new ArrayList<CmcFuncCap>();
			if(map!=null && !map.isEmpty()){
				String[] arrKey = null;
				CmcFuncCap entity = null;
				for (String key : map.keySet()) {
					arrKey = StringUtil.splitPreserveAllTokens(key, "|");
					entity = map.get(key);
					entity.setFuncid(funcId);
					entity.setFromlang(arrKey[0]);
					entity.setTolang(arrKey[1]);
					entity.setProxyid(Long.valueOf(arrKey[2]));
					list.add(entity);
				}
			}
			funcCapService.saveCap(funcId, list);
			if(BusinessConstant.FUNCID_TRANSLATE.equals(funcId)){
				CacheSynProcess.synCache(BeanConstant.BEANID_CACHE_TRANSCAP);
			}else if(BusinessConstant.FUNCID_ASR.equals(funcId)
					|| BusinessConstant.FUNCID_TTS.equals(funcId)
					|| BusinessConstant.FUNCID_OCR.equals(funcId)){
				CacheSynProcess.synCache(new String[]{BeanConstant.BEANID_CACHE_LANG, BeanConstant.BEANID_CACHE_FUNCCAP});
			}
		} catch (SystemException e) {
			handleResult = false;
			setResult(e.getMessage());
		} catch (Exception e) {
			logger.error("保存失败", e);
			handleResult = false;
			setResult("保存失败");
		}

		print();
		return null;
	}
	
	public Long getFuncId() {
		return funcId;
	}

	public void setFuncId(Long funcId) {
		this.funcId = funcId;
	}

	public List<CmcProxy> getProxys() {
		return proxys;
	}

	public void setProxys(List<CmcProxy> proxys) {
		this.proxys = proxys;
	}

	public List<CmcLang> getLangs() {
		return langs;
	}

	public void setLangs(List<CmcLang> langs) {
		this.langs = langs;
	}

	public Map<String, CmcFuncCap> getMap() {
		return map;
	}

	public void setMap(Map<String, CmcFuncCap> map) {
		this.map = map;
	}
}
