package com.qcmz.cmc.cache;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.constant.BusinessConstant;
import com.qcmz.cmc.entity.CmcFuncCap;
import com.qcmz.cmc.service.IFuncCapService;
import com.qcmz.cmc.ws.provide.vo.TransVideoLangBean;
import com.qcmz.framework.cache.AbstractCacheMap;

/**
 * 类说明：视频人工翻译语言列表缓存
 * 修改历史：
 */
@Component
public class TransVideoLangMap extends AbstractCacheMap {
	@Autowired
	private IFuncCapService funcCapService;

	private List<TransVideoLangBean> list = null;
	
	/** 
	 * 
	 * 修改历史：
	 */
	@Override @PostConstruct
	protected void init() {
		List<TransVideoLangBean> temp = new ArrayList<TransVideoLangBean>();
		TransVideoLangBean bean = null;

		List<CmcFuncCap> machines = funcCapService.findCap(BusinessConstant.FUNCID_TRANSVIDEO_HUMAN);
		for (CmcFuncCap cap : machines) {
			bean = new TransVideoLangBean();
			bean.setFrom(cap.getFromlang());
			bean.setTo(cap.getTolang());
			temp.add(bean);
		}
		
		list = temp;
	}
	
	public List<TransVideoLangBean> findLang(){
		return list;
	}
	
	/**
	 * 指定的源语言、目标语言是否支持视频人工翻译
	 * @param from
	 * @param to
	 * @return
	 * 修改历史：
	 */
	public boolean contains(String from, String to){
		if(safeInit(list)){
			for (TransVideoLangBean lang : list) {
				if(lang.getFrom().equals(from)
						&& lang.getTo().equals(to)){
					return true;
				}
			}
		}
		return false;
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
