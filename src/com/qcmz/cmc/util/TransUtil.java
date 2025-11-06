package com.qcmz.cmc.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.qcmz.cmc.config.JobConfig;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.vo.LibClassBean;
import com.qcmz.cmc.vo.TransLibraryEntity;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.util.BeanUtil;
import com.qcmz.framework.util.SecretUtil;
import com.qcmz.framework.util.StringUtil;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class TransUtil {
	
	private static Logger logger = Logger.getLogger(TransUtil.class);
	
	private static List<LibClassBean> libClasses = null;
	
	public final static String LIBCLASS_DEFAULT = "CmcTransLibrary";
	public final static String LIBCLASS_TEMP = "CmcTransLibraryTemp";
	
	/**
	 * 获取译库列表
	 * @return
	 * 修改历史：
	 */
	public static List<LibClassBean> findLibClassBean(){
		if(libClasses==null){
			libClasses = new ArrayList<LibClassBean>();
			libClasses.add(new LibClassBean("zh-cn", "en", "CmcTransLibraryCn2en", "中英"));
			libClasses.add(new LibClassBean("en", "zh-cn", "CmcTransLibraryEn2cn", "英中"));
			libClasses.add(new LibClassBean("zh-cn", "ja", "CmcTransLibraryCn2ja", "中日"));
			libClasses.add(new LibClassBean("ja", "zh-cn", "CmcTransLibraryJa2cn", "日中"));
			libClasses.add(new LibClassBean("zh-cn", "ko", "CmcTransLibraryCn2ko", "中韩"));
			libClasses.add(new LibClassBean("ko", "zh-cn", "CmcTransLibraryKo2cn", "韩中"));
			libClasses.add(new LibClassBean("", "", "CmcTransLibrary", "其他"));
		}
		return libClasses;
	}

	/**
	 * 获取所有的译库实体类，不包含临时库
	 * @return
	 * 修改历史：
	 */
	public static List<String> findAllLibClassWithoutTemp(){
		List<String> result = new ArrayList<String>();
		List<LibClassBean> list = findLibClassBean();
		for (LibClassBean bean : list) {
			result.add(bean.getLibClass());
		}
		return result;
	}
	
	/**
	 * 获取所有的译库实体类
	 * @return
	 * 修改历史：
	 */
	public static List<String> findAllLibClass(){
		List<String> result = findAllLibClassWithoutTemp();
		result.add(LIBCLASS_TEMP);
		return result;
	}
	
	/**
	 * 获取译库实体类
	 * @param from
	 * @param to
	 * @return
	 * 修改历史：
	 */
	public static String getLibClass(String from, String to){
		List<LibClassBean> list = findLibClassBean();
		for (LibClassBean bean : list) {
			if(from.equals(bean.getFrom())
					&& to.equals(bean.getTo())){
				return bean.getLibClass();
			}
		}
		return LIBCLASS_DEFAULT;
	}
	
	/**
	 * 译库记录转为入库所需的Entity
	 * @param bean
	 * @return
	 * 修改历史：
	 */
	public static Object getLibEntity(TransLibraryEntity bean){
		Object result = null;
		String libClass = getLibClass(bean.getFromlang(), bean.getTolang());
		try {
			if(!DictConstant.DICT_TTSMETHOD_DSTTEXT.equals(bean.getTtssrc())){
				bean.setTtstext(null);
			}
			if(SystemConstants.STATUS_ON.equals(bean.getCheckstatus())){
				bean.setSimilar(100);
			}
			result = Class.forName("com.qcmz.cmc.entity."+libClass).newInstance();
			BeanUtil.copyProperties(result, bean);
		} catch (Exception e) {
			logger.error("转入库实体失败："+libClass, e);
		}
		return result;
	}
	
	/**
	 * 判断文本是否匹配
	 * @param str
	 * 修改历史：
	 */
	public static int calSimilarity(String str1, String str2){
		if(str1==null || str2==null) return 0;
		
		int max = 100;

		str1 = StringUtil.clearPunc(str1);
		str2 = StringUtil.clearPunc(str2);

		if(str1.equalsIgnoreCase(str2)) return max;
		
		float similar = StringUtil.calSimilarity(str1, str2); 
		return (int)(similar*max);
	}
	
	/**
	 * 获取主题句子音频文件标识
	 * @param themeId
	 * @param sentId
	 * @return
	 * 修改历史：
	 */
	public static String getThemeSentenceVoice(Long themeId, Long sentId){
		return SecretUtil.encryptByMd5(new StringBuilder("theme_").append(themeId).append("_").append(sentId).toString());
	}
	
	/**
	 * 是否保存翻译差异
	 * @param dst
	 * @param similar
	 * @param sessionId
	 * @param pushRegid
	 * @param platform
	 * @param version
	 * @param serviceType
	 * @return
	 */
	public static boolean isSaveTransDiff(String src, int similar, String sessionId, String pushRegid
			, String platform, String version, String serviceType, Integer transModel){
		if(!JobConfig.TRANSDIFF_SAVE_ISWORK) return false;
		
		if(similar>=80) return false;
		if(StringUtil.countWord(src)>50) return false;
		if(StringUtil.isBlankOrNull(sessionId) || StringUtil.isBlankOrNull(pushRegid)) return false;
		if(!DictConstants.DICT_SERVICETYPE_VOICETRANS.equals(serviceType)) return false;
		if(transModel!=null && !DictConstant.DICT_TRANSMODEL_FREE.equals(transModel)){
			return false;
		}
		
		if(DictConstants.DICT_PLATFORM_IOS.equals(platform)){
			if(StringUtil.afterVersion(version, "2.6.3")){
				return true;
			}
		}else if(DictConstants.DICT_PLATFORM_ANDROID.equals(platform)){
			if(StringUtil.afterVersion(version, "2.5.6")){
				return true;
			}
		}
				
		return false;
	}
	
}
