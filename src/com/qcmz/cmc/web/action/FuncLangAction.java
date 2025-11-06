package com.qcmz.cmc.web.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.constant.BeanConstant;
import com.qcmz.cmc.constant.BusinessConstant;
import com.qcmz.cmc.entity.CmcFuncCap;
import com.qcmz.cmc.entity.CmcLang;
import com.qcmz.cmc.process.CacheSynProcess;
import com.qcmz.cmc.service.IFuncCapService;
import com.qcmz.cmc.service.ILangService;
import com.qcmz.cmc.util.CmcUtil;
import com.qcmz.framework.action.BaseAction;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.exception.SystemException;
import com.qcmz.framework.util.StringUtil;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class FuncLangAction extends BaseAction {
	@Autowired
	private ILangService langService;
	@Autowired
	private IFuncCapService funcCapService;
	
	private Long funcId;
	private List<CmcLang> langs;
	private List<String> caps = new ArrayList<String>();
	
	public String doEdit(){
		langs = langService.findValid(CmcUtil.getLangTypeByFuncId(funcId));
		List<CmcFuncCap> list = funcCapService.findCap(funcId);
		for (CmcFuncCap bean : list) {
			caps.add(new StringBuilder(bean.getFromlang()).append("|").append(bean.getTolang()).toString());
		}
		return EDIT;
	}
	
	public String doSave(){
		try {
			String[] arr = request.getParameterValues("funclang");
			List<CmcFuncCap> list = new ArrayList<CmcFuncCap>();
			
			String[] arrfuncLang = null;
			CmcFuncCap bean = null;
			for (String string : arr) {
				arrfuncLang = StringUtil.split(string, "|");
				bean = new CmcFuncCap();
				bean.setFuncid(funcId);
				bean.setFromlang(arrfuncLang[0]);
				bean.setTolang(arrfuncLang[1]);
				bean.setPriority(SystemConstants.SORTINDEX_DEFAULT);
				list.add(bean);
			}
			funcCapService.saveCap(funcId, list);
			
			if(BusinessConstant.FUNCID_TRANSPIC_MACHINE.equals(funcId)
					|| BusinessConstant.FUNCID_TRANSPIC_HUMAN.equals(funcId)){
				CacheSynProcess.synCache(BeanConstant.BEANID_CACHE_TRANSPICLANG);
			}else if(BusinessConstant.FUNCID_TRANSTEXT_HUMAN.equals(funcId)){
				CacheSynProcess.synCache(BeanConstant.BEANID_CACHE_TRANSTEXTLANG);
			}else if(BusinessConstant.FUNCID_TRANSVIDEO_HUMAN.equals(funcId)){
				CacheSynProcess.synCache(BeanConstant.BEANID_CACHE_TRANSVIDEOLANG);
			}
		} catch (SystemException e) {
			handleResult = false;
			setResult(e.getMessage());
		} catch (Exception e) {
			handleResult = false;
			setResult("保存失败");
			logger.error("保存失败", e);
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

	public List<CmcLang> getLangs() {
		return langs;
	}

	public void setLangs(List<CmcLang> langs) {
		this.langs = langs;
	}

	public List<String> getCaps() {
		return caps;
	}

	public void setCaps(List<String> caps) {
		this.caps = caps;
	}
}
