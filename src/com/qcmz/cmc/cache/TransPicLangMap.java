package com.qcmz.cmc.cache;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.constant.BusinessConstant;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.entity.CmcFuncCap;
import com.qcmz.cmc.service.IFuncCapService;
import com.qcmz.cmc.ws.provide.vo.TransPicLangBean;
import com.qcmz.framework.cache.AbstractCacheMap;

/**
 * 类说明：图片翻译语言列表缓存
 * 修改历史：
 */
@Component
public class TransPicLangMap extends AbstractCacheMap {
	@Autowired
	private IFuncCapService funcCapService;

	private List<TransPicLangBean> list = null;
	
	/** 
	 * 
	 * 修改历史：
	 */
	@Override @PostConstruct
	protected void init() {
		List<TransPicLangBean> temp = new ArrayList<TransPicLangBean>();
		TransPicLangBean bean = null;

		List<CmcFuncCap> machines = funcCapService.findCap(BusinessConstant.FUNCID_TRANSPIC_MACHINE);
		for (CmcFuncCap cap : machines) {
			bean = new TransPicLangBean();
			bean.setTransWay(DictConstant.DICT_TRANSWAY_MACHINE);
			bean.setFrom(cap.getFromlang());
			bean.setTo(cap.getTolang());
			temp.add(bean);
		}
		
		List<CmcFuncCap> humans = funcCapService.findCap(BusinessConstant.FUNCID_TRANSPIC_HUMAN);
		for (CmcFuncCap cap : humans) {
			bean = new TransPicLangBean();
			bean.setTransWay(DictConstant.DICT_TRANSWAY_OVERVIEW);
			bean.setFrom(cap.getFromlang());
			bean.setTo(cap.getTolang());
			temp.add(bean);
			
			bean = new TransPicLangBean();
			bean.setTransWay(DictConstant.DICT_TRANSWAY_DETAIL);
			bean.setFrom(cap.getFromlang());
			bean.setTo(cap.getTolang());
			temp.add(bean);
		}
		
		list = temp;
	}
	
	public List<TransPicLangBean> findLang(){
		return list;
	}
	
	/**
	 * 指定的翻译途径、源语言、目标语言是否支持图片翻译
	 * @param transWay
	 * @param from
	 * @param to
	 * @return
	 * 修改历史：
	 */
	public boolean contains(String transWay, String from, String to){
		if(safeInit(list)){
			for (TransPicLangBean lang : list) {
				if(lang.getTransWay().equals(transWay)
						&& lang.getFrom().equals(from)
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
