package com.qcmz.cmc.util;

import java.util.Date;

import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.entity.CmcUCombo;
import com.qcmz.framework.util.CaesarUtil;

public class TransComboUtil {
	
	/**
	 * 用户套餐是否有效
	 * @param uc
	 * @param userId
	 * @param transType
	 * @return
	 */
	public static boolean isValid(CmcUCombo uc, Long userId, String transType){
		if(uc==null) return false;
		if(!uc.getUserid().equals(userId)) return false;
		if(!uc.getTranstype().equals(transType)) return false;
		if(DictConstant.DICT_TRANSCOMBO_UNIT_TIMES.equals(uc.getUnit())){
			if(uc.getUsablenum()==null || uc.getUsablenum()<1) return false;
		}
		Date now = new Date();
		if(uc.getStarttime()!=null && now.before(uc.getStarttime())) return false;
		if(uc.getEndtime()!=null && now.after(uc.getEndtime())) return false;
		return true;
	}
	
	/**
	 * 生成兑换码
	 * @param seq
	 * @return
	 */
	public static String getExchangeCode(Long seq){
		return CaesarUtil.getCodeByCaesa("TCC-", seq, 10);
	}
	
	/**
	 * 获取翻译套餐名称
	 * @param combotitle
	 * @param pkgItitle
	 * @return
	 */
	public static String getComboTitle(String combotitle, String pkgItitle){
		return new StringBuilder(combotitle).append("（").append(pkgItitle).append("）").toString();
	}
	
	/**
	 * 获取翻译套餐名称
	 * @param combotitle
	 * @param num
	 * @param unitName
	 * @return
	 */
	public static String getComboTitle(String combotitle, int num, String unitName){
		return new StringBuilder(combotitle).append("（").append("自选").append(num).append(unitName).append("）").toString();
	}
}
