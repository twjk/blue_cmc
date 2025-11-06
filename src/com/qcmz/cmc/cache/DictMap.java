package com.qcmz.cmc.cache;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jsp.dictionary.bean.DictionaryBean;
import com.jsp.dictionary.main.Dictionary;
import com.qcmz.bdc.ws.provide.locator.CatWS;
import com.qcmz.bdc.ws.provide.locator.DictWS;
import com.qcmz.bdc.ws.provide.vo.CatBean;
import com.qcmz.bdc.ws.provide.vo.DictBean;
import com.qcmz.cmc.dao.IFunctionDao;
import com.qcmz.cmc.dao.ILangDao;
import com.qcmz.cmc.entity.CmcFunction;
import com.qcmz.cmc.entity.CmcIrCat;
import com.qcmz.cmc.entity.CmcLang;
import com.qcmz.cmc.service.IImageRecognitionCatService;
import com.qcmz.framework.cache.AbstractCacheMap;
import com.qcmz.framework.constant.DictConstants;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
@Component
public class DictMap extends AbstractCacheMap {
	@Autowired
	private ILangDao langDao;
	@Autowired
	private IFunctionDao functionDao;
	@Autowired
	private IImageRecognitionCatService imageRecognitionCatService;
	
	/** 
	 * 
	 * 修改历史：
	 */
	@Override
	public void init() {
		try {
			List<DictBean> list = DictWS.findDict();
			List<DictionaryBean> dictList = new ArrayList<DictionaryBean>();
			if(list!=null && !list.isEmpty()){
				for (DictBean dict : list) {
					dictList.add(new DictionaryBean(dict.getFieldName(), dict.getFieldValue(), dict.getValueMean()));
				}
			}
			
			//分类加入字典
			List<CatBean> catList = CatWS.findCatAll();
			if(catList!=null && !catList.isEmpty()){
				for (CatBean cat : catList) {
					dictList.add(new DictionaryBean("cat", String.valueOf(cat.getCatId()), cat.getCatName()));
				}
			}
			
			//语言加入字典
			List<CmcLang> langList = langDao.findLang(DictConstants.DICT_LANGTYPE_SPEECH, null);
			for (CmcLang lang : langList) {
				dictList.add(new DictionaryBean("speechLang", String.valueOf(lang.getLangcode()), lang.getLangcn()));
			}
			langList = langDao.findLang(DictConstants.DICT_LANGTYPE_TEXT, null);
			for (CmcLang lang : langList) {
				dictList.add(new DictionaryBean("textLang", String.valueOf(lang.getLangcode()), lang.getLangcn()));
			}
			
			//功能
			List<CmcFunction> funcList = functionDao.findAll();
			for (CmcFunction func : funcList) {
				dictList.add(new DictionaryBean("func", String.valueOf(func.getFuncid()), func.getFuncname()));
			}
			
			//图像识别分类
			List<CmcIrCat> ircatList = imageRecognitionCatService.findCat();
			for (CmcIrCat ircat : ircatList) {
				dictList.add(new DictionaryBean("ircat", String.valueOf(ircat.getCatid()), ircat.getCatname()));
			}
			
			Dictionary.getInstance().putToDbMap(dictList);
		} catch (Exception e) {
			logger.error("初始化字典缓存失败", e);
		}
	}
	
	public List<DictionaryBean> findPlatform(){
		return Dictionary.getInstance().getFieldDict(null, DictConstants.DICTNAME_PLATFORM, Dictionary.DATA_SOURCE_DB);
	}
	
	/**
	 * 获取自定值获得对应的含义
	 * @param fieldValue
	 * @return
	 * 修改历史：
	 */
	public static String getValueMean(String fieldName, String fieldValue){
		return Dictionary.getInstance().getInitvalue(fieldValue, null, fieldName, Dictionary.DATA_SOURCE_DB);
	}
	
	public static String getServiceTypeMean(String fieldValue){
		return getValueMean("serviceType", fieldValue);
	}
	
	public static String getSubServiceTypeMean(String fieldValue){
		return getValueMean("subServiceType", fieldValue);
	}
	
	public static String getTradeStatusMean(String fieldValue){
		return getValueMean("tradeStatus", fieldValue);
	}

	public static String getTransWayMean(String fieldValue){
		return getValueMean("transWay", fieldValue);
	}
	
	public static String getOrderCatMean(Integer fieldValue){
		return getValueMean("orderCat", String.valueOf(fieldValue));
	}
	
	public static String getOrderTypeMean(Integer fieldValue){
		return getValueMean("orderType", String.valueOf(fieldValue));
	}
	
	public static String getOrderDealStatusMean(String dealStatus){
		return getValueMean("orderDealStatus", dealStatus);
	}
	
	public static String getTransComboUnitMean(Integer unit){
		return getValueMean("transComboUnit", String.valueOf(unit));
	}
	
	public static String getTransComboTypeMean(Integer comboType){
		return getValueMean("transComboType", String.valueOf(comboType));
	}
	
	public static String getWalletBillTypeMean(Integer walletBillType){
		return getValueMean("walletBillType", String.valueOf(walletBillType));
	}
	public static String getWalletRechargeStatusMean(Integer walletRechargeStatus){
		return getValueMean("walletRechargeStatus", String.valueOf(walletRechargeStatus));
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
