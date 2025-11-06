package com.qcmz.cmc.process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.cache.TransPriceMap;
import com.qcmz.cmc.cache.TransVideoLangMap;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.entity.CmcTpIntl;
import com.qcmz.cmc.entity.CmcTpPrice;
import com.qcmz.cmc.ws.provide.vo.TransPicPriceBean;
import com.qcmz.cmc.ws.provide.vo.TransPriceBean;
import com.qcmz.cmc.ws.provide.vo.TransVideoLangBean;
import com.qcmz.cmc.ws.provide.vo.TransVideoPriceBean;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.DoubleUtil;
import com.qcmz.framework.util.StringUtil;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
@Component
public class TransPriceProcess {
	@Autowired
	private TransPriceMap transPriceMap;
	@Autowired
	private TransVideoLangMap transVideoLangMap;
	
	/**
	 * 获取翻译价格
	 * @param transType
	 * @param transWay
	 * @param src
	 * @param from
	 * @param to
	 * @param language
	 * @return
	 * 修改历史：
	 */
	public TransPriceBean getTransPrice(String transType, String transWay, String src, String from, String to, String language){
		List<TransPriceBean> prices = findTransPrice(transType, transWay, src, from, to, language);
		TransPriceBean result = null;
		if(!prices.isEmpty()){
			result = prices.get(0);
		}
		return result;
	}
	
	/**
	 * 获取图片翻译价格
	 * @param search
	 * @param language
	 * @return
	 * 修改历史：
	 */
	public TransPriceBean getTransPicPrice(String transWay, String src, String from, String to, String language){
		return getTransPrice(DictConstants.DICT_TRANSTYPE_PIC, transWay, src, from, to, language);
	}
	
	/**
	 * 获取图片概要翻译价格，用于旧版兼容
	 * @param language
	 * @return
	 * 修改历史：
	 */
	@Deprecated
	public TransPicPriceBean getTransPicPrice(String language){
		TransPriceBean price = getTransPrice(DictConstants.DICT_TRANSTYPE_PIC, DictConstant.DICT_TRANSWAY_OVERVIEW, null, null, null, language);
		
		TransPicPriceBean result = null;
		if(price!=null){
			result = new TransPicPriceBean();
			result.setService(price.getService());
			result.setOriprice(price.getTotalOriPrice());
			result.setPrice(price.getTotalPrice());
			result.setMaxLength(50);
			result.setOpen(true);
			result.setOpenTime(price.getWorkTime());
		}
		
		return result;
	}
	
	/**
	 * 获取翻译价格列表
	 * @param transType
	 * @param transWay
	 * @param src
	 * @param from
	 * @param to
	 * @param language
	 * @return
	 */
	public List<TransPriceBean> findTransPrice(String transType, String transWay, String src, String from, String to, String language){
		List<TransPriceBean> result = new ArrayList<TransPriceBean>();		
		List<CmcTpPrice> transTypePrices = transPriceMap.findPrice(transType);
		if(transTypePrices==null || transTypePrices.isEmpty()){
			return result;
		}
		
		Map<String, CmcTpPrice> priceMap = new HashMap<String, CmcTpPrice>();
		for (CmcTpPrice price : transTypePrices) {
			if(StringUtil.notBlankAndNull(transWay)	&& !price.getTransway().equals(transWay)) continue;
			if(!transPriceMap.isSupport(price.getPriceid(), from, to)) continue;
			
			priceMap.put(price.getTranstype()+price.getTransway(), price);
		}
		
		
		if(StringUtil.isBlankOrNull(language)){
			language = DictConstants.DICT_LANG_ZHCN;
		}
		
		TransPriceBean priceBean = null;
		CmcTpIntl intl = null;
		Double totalOriPrice = null;
		Double totalPrice = null;
		
		int wordCount = StringUtil.countWord(src);
		if(DictConstants.DICT_TRANSTYPE_PIC.equals(transType)){
			if(wordCount==0){
				wordCount = 150;
			}else{
				int charCount = src.length()/6;
				if(charCount>wordCount){
					wordCount = charCount;
				}
			}
		}
		for (String key2 : priceMap.keySet()) {
			CmcTpPrice price = priceMap.get(key2);
			
			intl = transPriceMap.getPriceIntl(price.getPriceid(), language);
			if(intl==null)	continue;
			
			priceBean = new TransPriceBean();
			priceBean.setPriceId(price.getPriceid());
			priceBean.setTransType(price.getTranstype());
			priceBean.setTransWay(price.getTransway());
			if(DictConstant.DICT_PRICEUNIT_ORDER.equals(price.getPriceunit())){
				totalOriPrice = price.getOriprice();
				totalPrice = price.getPrice();
			}else if(DictConstant.DICT_PRICEUNIT_WORD.equals(price.getPriceunit())){
				if(wordCount>price.getStartnum()){
					totalOriPrice = price.getOristartprice()+price.getOriprice()*(wordCount-price.getStartnum());
					totalOriPrice = DoubleUtil.floor(totalOriPrice, 2);
					
					totalPrice = price.getStartprice()+price.getPrice()*(wordCount-price.getStartnum());
					totalPrice = DoubleUtil.floor(totalPrice, 2);
				}else{
					totalOriPrice = price.getOristartprice();
					totalPrice = price.getStartprice();
				}
			}
			priceBean.setOriPrice(price.getOriprice());
			priceBean.setPrice(price.getPrice());
			priceBean.setStartPrice(price.getStartprice());
			priceBean.setWordCount(wordCount);
			priceBean.setTotalOriPrice(totalOriPrice);
			priceBean.setTotalPrice(totalPrice);
			
			String workTime = price.getStarttime()+"-"+price.getEndtime();
			priceBean.setInWorkTime(DateUtil.inTime4(workTime.replaceAll(":", "")));
			priceBean.setWorkTime(workTime);
			priceBean.setWorkTimeDesc(intl.getWorktimedesc());
			priceBean.setOverview(intl.getOverview());
			priceBean.setService(intl.getService());
			result.add(priceBean);
		}
		
		return result;
	}
	
	/**
	 * 获取视频口译价格列表
	 * @param from
	 * @param to
	 * @return
	 */
	public CmcTpPrice getTransVideoPrice(String from, String to){
		//价格列表
		List<CmcTpPrice> transTypePrices = transPriceMap.findPrice(DictConstants.DICT_TRANSTYPE_VIDEO);
		if(transTypePrices==null || transTypePrices.isEmpty()) return null;
		
		//语言
		for (CmcTpPrice price : transTypePrices) {
			if(transPriceMap.isSupport(price.getPriceid(), from, to)){
				return price;
			}
		}
		
		return null;
	}
	
	/**
	 * 获取视频口译价格列表
	 * @param from
	 * @param to
	 * @param language
	 * @return
	 */
	public List<TransVideoPriceBean> findTransVideoPrice(String from, String to, String language){
		List<TransVideoPriceBean> result = new ArrayList<TransVideoPriceBean>();
		
		//价格列表
		List<CmcTpPrice> transTypePrices = transPriceMap.findPrice(DictConstants.DICT_TRANSTYPE_VIDEO);
		if(transTypePrices==null || transTypePrices.isEmpty()) return result;
		
		//语言列表
		List<TransVideoLangBean> langs = transVideoLangMap.findLang();
		if(langs.isEmpty()) return result;
		if(StringUtil.notBlankAndNull(from) && StringUtil.notBlankAndNull(to)){
			langs.clear();
			langs.add(new TransVideoLangBean(from, to));
		}
		
		//语言价格
		CmcTpIntl intl = null;
		TransVideoPriceBean priceBean = null;
		for (TransVideoLangBean lang : langs) {
			for (CmcTpPrice price : transTypePrices) {
				if(transPriceMap.isSupport(price.getPriceid(), lang.getFrom(), lang.getTo())){
					intl = transPriceMap.getPriceIntl(price.getPriceid(), language);
					if(intl!=null){
						priceBean = new TransVideoPriceBean();
						priceBean.setPriceId(price.getPriceid());
						priceBean.setFrom(lang.getFrom());
						priceBean.setTo(lang.getTo());
						priceBean.setOriPrice(price.getOriprice());
						priceBean.setPrice(price.getPrice());
						priceBean.setStartPrice(price.getStartprice());
						
						String workTime = price.getStarttime()+"-"+price.getEndtime();
						priceBean.setInWorkTime(DateUtil.inTime4(workTime.replaceAll(":", "")));
						priceBean.setWorkTime(workTime);
						priceBean.setWorkTimeDesc(intl.getWorktimedesc());
						priceBean.setOverview(intl.getOverview());
						priceBean.setService(intl.getService());
						result.add(priceBean);
						break;
					}
				}
				
			}
		}
		
		return result;
	}
}
