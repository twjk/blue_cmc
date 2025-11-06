package com.qcmz.cmc.util;

import java.util.Date;

import com.qcmz.cmc.cache.LangMap;
import com.qcmz.cmc.constant.BusinessConstant;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.SecretUtil;
import com.qcmz.framework.util.SpringUtil;
import com.qcmz.framework.util.StringUtil;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class CmcUtil {
	public static String KEY = "02xbhCUce1z2V384e9u6E52D";
	public static String IV = "fhhbyO33";
	
	/**
	 * 信息加密
	 * @param data
	 * @return
	 * 修改历史：
	 */
	public static String encrypt(String data){
		if(StringUtil.isBlankOrNull(data)) return "";
		return encrypt(data.getBytes());
	}
	
	/**
	 * 信息加密
	 * @param data
	 * @return
	 * 修改历史：
	 */
	public static String encrypt(byte[] data){
		return SecretUtil.base643DES(KEY.getBytes(), IV.getBytes(), data);
	}
	
	/**
	 * 信息解密
	 * @param data
	 * @return
	 * 修改历史：
	 */
	public static String decrypt(String data){
		if(StringUtil.isBlankOrNull(data)) return "";
		return SecretUtil.deBase643DES(KEY, IV, data);
	}
	
	/**
	 * 格式化每日一句发布时间，格式 09 Jul.2015
	 * @param d
	 * @return
	 * 修改历史：
	 */
	public static String formatReleaseTime(Date d){
		if(d==null) return "";
		return formatReleaseTime(DateUtil.formatDate(d));
	}
	
	/**
	 * 格式化每日一句发布时间，格式 09 Jul.2015
	 * @param date
	 * @return
	 * 修改历史：
	 */
	public static String formatReleaseTime(String date){
		if(date==null || "".equals(date)) return "";
		
		return new StringBuilder()
			.append(date.substring(8))
			.append(" ")
			.append(DateUtil.getMonthShort(date))
			.append(".")
			.append(date.substring(0,4))
			.toString()
		;
	}
	
	/**
	 * 根据功能编号获取语言类型
	 * @param funcId
	 * @return
	 * 修改历史：
	 */
	public static String getLangTypeByFuncId(Long funcId){
		if(BusinessConstant.FUNCID_TRANSLATE.equals(funcId)
				|| BusinessConstant.FUNCID_OCR.equals(funcId)
				|| BusinessConstant.FUNCID_TRANSPIC_MACHINE.equals(funcId)
				|| BusinessConstant.FUNCID_TRANSPIC_HUMAN.equals(funcId)
				|| BusinessConstant.FUNCID_LANGDETECT.equals(funcId)){
			return DictConstants.DICT_LANGTYPE_TEXT;
		}else{
			return DictConstants.DICT_LANGTYPE_SPEECH;
		}
	}
	
	/**
	 * 获取语言描述
	 * @param from
	 * @param to
	 * @return
	 */
	public static String getLangDesc(String from, String to){
		LangMap langMap = (LangMap) SpringUtil.getBean("langMap");
		return new StringBuilder()
					.append(langMap.getLangName4Text(from))
					.append("⇌")
					.append(langMap.getLangName4Text(to))
					.toString();
	}
	
	public static String getLang(String from, String to){
		if(!DictConstants.DICT_LANG_ZHCN.equals(from)){
			return from;
		}
		if(!DictConstants.DICT_LANG_ZHCN.equals(to)){
			return to;
		}
		return from;
	}
	
	/**
	 * 生成版本号
	 * @return
	 */
	public static String getTransVerCode(Date transDate){
		return new StringBuilder()
			.append(DateUtil.format(transDate, "yyMMddHHmmss"))
			.append(DateUtil.format(new Date(), "SSS"))
			.toString();
	}
	
	/**
	 * 生成查询排序，正序
	 * @param sortIndex 正序
	 * @param id 倒序
	 * @param time 倒序
	 * @return
	 */
	public static String getSortQuery(Integer sortIndex, Long id, Date time){
		StringBuilder sb = new StringBuilder("1").append(StringUtil.fillZero(sortIndex,5));
		if(time!=null){
			sb.append(Math.abs(time.getTime()/1000-10000000000L));
		}
		sb.append(Math.abs(id-10000000));
		return sb.toString();
	}
	
	/**
	 * 生成查询排序，正序
	 * @param sortIndex 正序
	 * @param id 倒序
	 * @return
	 */
	public static String getSortQuery(Integer sortIndex, Long id){
		return getSortQuery(sortIndex, id, null);
	}
	
	public static void main(String[] args) {
		System.out.println(getSortQuery(999, 5L));
		String data = "BqAQ245guWFUF9UIaDeqTj/qo5v8GmcB";
		System.out.println(decrypt(data));
	}
}
