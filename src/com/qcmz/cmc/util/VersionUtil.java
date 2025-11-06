package com.qcmz.cmc.util;

import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.util.StringUtil;

/**
 * 类说明：版本处理
 * 修改历史：
 */
public class VersionUtil {
	public static final String VERSION_IOS_2_3_2 = "2.3.2";
	public static final String VERSION_IOS_2_3_7 = "2.3.7";
	public static final String VERSION_IOS_2_4_0 = "2.4.0";
	public static final String VERSION_IOS_2_4_6 = "2.4.6";
	public static final String VERSION_IOS_2_5_4 = "2.5.4";
	public static final String VERSION_IOS_2_5_7 = "2.5.7";
	
	public static final String VERSION_ANDROID_2_0_2 = "2.0.2";	
	public static final String VERSION_ANDROID_2_1_2 = "2.1.2";
	public static final String VERSION_ANDROID_2_1_6 = "2.1.6";
	public static final String VERSION_ANDROID_2_2_5 = "2.2.5";
	public static final String VERSION_ANDROID_2_4_1 = "2.4.1";
	
	//前3批语言
	public static String[] LANGS = new String[]{"zh-cn","zh-hk","en","fr","de","ko","ja","es","pt","it","ar-eg","ru","th","cs","da","nl","fi","el","he","hi","hu","id","no","pl","sv","tr","vi","uk","ms"};
	//第4批语言
	public static String[] LANGS_4 = new String[]{"ca","ro","sk","hr"};	//加泰隆语、罗马尼亚语、斯洛伐克语 、克罗地亚语
	//第5批语言 20170215
	public static String[] LANGS_5 = new String[]{"zh-tw","bs-lt","bg","et","fj","tl","nww","ht","sw","tlh","lv","lt","mt","mg","fa","otq","sm","sr-cy","sr-lt","ls","ty","to","ur","cy","yua"};	
	
	//IOS暂不支持的语言
	//菲律宾语、保加利亚语、斯洛文尼亚语、高棉语、缅甸语
	public static String[] LANGS_UNSUPPORT_IOS = new String[]{"bg","tl","sl","km","my"};
	
	/**
	 * 兼容处理1
	 * 1.图片翻译状态变动
	 *	 原：01机器翻译02已提交03已支付04翻译中05已完成06已取消07已退款
	 * 	 新：01机器翻译02已提交03已支付04翻译中05已完成06已取消07已退款08已关闭09待支付10待翻译
	 * 2.图片翻译处理进度
	 * 	 原：只显示已处理的进度
	 *   新：显示完整的处理进度，未到的进度日期为空
	 * 3.必应图片识别代理
	 * 	 原：灵云
	 *   新：必应、灵云
	 * 4.要兼容的旧版本
	 *   serviceType:出国翻译官
	 * 	 Android:2.0.0/2.0.1
	 *   IOS：2.3.1
	 * @param platform
	 * @param version
	 * @return
	 * 修改历史：
	 */
	public static boolean compat1(String serviceType, String platform, String version){
		if(!DictConstants.DICT_SERVICETYPE_VOICETRANS.equals(serviceType)){
			return false;
		}
		
		if(DictConstants.DICT_PLATFORM_IOS.equals(platform) 
				&& StringUtil.beforeVersion(version, VERSION_IOS_2_3_2)){
			return true;
		}
		if(DictConstants.DICT_PLATFORM_ANDROID.equals(platform) 
				&& StringUtil.beforeVersion(version, VERSION_ANDROID_2_0_2)){
			return true;
		}
		return false;
	}
	
	/**
	 * 兼容处理2
	 * 1.老版本客户端不支持第4批新增语言：加泰隆语、罗马尼亚语、斯洛伐克语 、克罗地亚语
	 * 2.要兼容的旧版本
	 *   serviceType:出国翻译官
	 * 	 Android:2.1.2以下
	 *   IOS    ：2.3.7以下 
	 * @param platform
	 * @param version
	 * @param authAccount
	 * @return
	 */
	public static boolean compat2(String serviceType, String platform, String version){
		if(!DictConstants.DICT_SERVICETYPE_VOICETRANS.equals(serviceType)){
			return false;
		}
		
		if(DictConstants.DICT_PLATFORM_IOS.equals(platform) 
				&& StringUtil.beforeVersion(version, VERSION_IOS_2_3_7)){
			return true;
		}
		if(DictConstants.DICT_PLATFORM_ANDROID.equals(platform) 
				&& StringUtil.beforeVersion(version, VERSION_ANDROID_2_1_2)){
			return true;
		}
		return false;
	}
	
	/**
	 * 兼容处理3
	 * 1.老版本客户端不支持第5批新增语言
	 * 2.要兼容的旧版本
	 *   serviceType:出国翻译官
	 *   Android: 2.1.6以下
	 *   IOS    ：2.4.0以下 
	 * @param platform
	 * @param version
	 * @param authAccount
	 * @return
	 */
	public static boolean compat3(String serviceType, String platform, String version){
		if(!DictConstants.DICT_SERVICETYPE_VOICETRANS.equals(serviceType)){
			return false;
		}
		
		if(DictConstants.DICT_PLATFORM_IOS.equals(platform) 
				&& StringUtil.beforeVersion(version, VERSION_IOS_2_4_0)){
			return true;
		}
		if(DictConstants.DICT_PLATFORM_ANDROID.equals(platform) 
				&& StringUtil.beforeVersion(version, VERSION_ANDROID_2_1_6)){
			return true;
		}
		
		return false;
	}
	
	/**
	 * 兼容处理4
	 * 1.获取代理账户接口，老版本一个代理返回一个帐户，新版本一个代理返回多个帐户；兼容处理：删掉专用帐户
	 * 2.要兼容的旧版本
	 * serviceType:出国翻译官
	 * Android: 2.2.5以下
	 * IOS    ：2.4.6以下 
	 * @param platform
	 * @param version
	 * @param authAccount
	 * @return
	 */
	public static boolean compat4(String serviceType, String platform, String version){
		if(!DictConstants.DICT_SERVICETYPE_VOICETRANS.equals(serviceType)){
			return false;
		}
		
		if(DictConstants.DICT_PLATFORM_IOS.equals(platform) 
				&& StringUtil.beforeVersion(version, VERSION_IOS_2_4_6)){
			return true;
		}
		if(DictConstants.DICT_PLATFORM_ANDROID.equals(platform) 
				&& StringUtil.beforeVersion(version, VERSION_ANDROID_2_2_5)){
			return true;
		}
		
		return false;
	}
	
	/**
	 * 是否补全微软服务地址（OCR、语音和翻译）
	 * 要兼容的旧版本
	 * serviceType:出国翻译官
	 * Android:2.8.9以下
	 * IOS    ：2.9.6以下 
	 * @param platform
	 * @param version
	 * @param authAccount
	 * @return
	 */
	public static boolean needCompleteMicrosoftServer(String serviceType, String platform, String version){
		if(!DictConstants.DICT_SERVICETYPE_VOICETRANS.equals(serviceType)){
			return false;
		}
		
		if(DictConstants.DICT_PLATFORM_IOS.equals(platform)
				&& StringUtil.beforeVersion(version, "2.9.6")){
			return true;
		}
		if(DictConstants.DICT_PLATFORM_ANDROID.equals(platform)
				&& StringUtil.beforeVersion(version, "2.8.9")){
			return true;
		}
		
		return false;
	}
	
	/**
	 * 由于客户端bug，指定版本停用微软语音识别服务
	 * 停用的版本
	 * serviceType:出国翻译官
	 * Android:2.8.9和2.9.0
	 * IOS    ：2.9.6和2.9.7 
	 * @param serviceType
	 * @param platform
	 * @param version
	 * @return
	 */
	public static boolean disableMicrosoftAsr(String serviceType, String platform, String version){
		if(!DictConstants.DICT_SERVICETYPE_VOICETRANS.equals(serviceType)){
			return false;
		}
		if(DictConstants.DICT_PLATFORM_IOS.equals(platform)
				&& StringUtil.betweenVersion(version, "2.9.6", "2.9.7")){
			return true;
		}
		if(DictConstants.DICT_PLATFORM_ANDROID.equals(platform)
				&& StringUtil.betweenVersion(version, "2.8.9", "2.9.0")){
			return true;
		}
		return false;
	}

	/**
	 * 可以使用IOS自带的语音服务，proxyid=12
	 * 可以用的版本：IOS,出国翻译官>=2.9.8
	 * @param serviceType
	 * @param platform
	 * @param version
	 * @return
	 */
	public static boolean enableIosSpeech(String serviceType, String platform, String version){
		if(DictConstants.DICT_PLATFORM_IOS.equals(platform)){
			if(DictConstants.DICT_SERVICETYPE_VOICETRANS.equals(serviceType)){
				return StringUtil.afterOrEqualVersion(version, "2.9.8");
			}else{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 可以使用百度语音，proxyid=13
	 * 出国翻译官 IOS>=3.0.5  安卓>=3.0.3
	 * @param serviceType
	 * @param platform
	 * @param version
	 * @return
	 */
	public static boolean enableBaiduSpeech(String serviceType, String platform, String version){
		if(DictConstants.DICT_SERVICETYPE_VOICETRANS.equals(serviceType)){
			if(DictConstants.DICT_PLATFORM_IOS.equals(platform)){
				return StringUtil.afterOrEqualVersion(version, "3.0.4");
			}else if(DictConstants.DICT_PLATFORM_ANDROID.equals(platform)){
				return StringUtil.afterOrEqualVersion(version, "3.0.3");
			}
		}
		return true;
	}
	
	/**
	 * 兼容处理5
	 * 1.ios图片识别英文未加空格，导致价格无法计算，停用该版本
	 * 2.要兼容的旧版本
	 * serviceType:出国翻译官
	 * IOS    ：2.5.4 
	 * @param serviceType
	 * @param platform
	 * @param version
	 * @param langCode
	 * @return
	 */
	public static boolean compat5(String serviceType, String platform, String version, String langCode){
		if(DictConstants.DICT_LANG_EN.equals(langCode)
				&& DictConstants.DICT_SERVICETYPE_VOICETRANS.equals(serviceType)
				&& DictConstants.DICT_PLATFORM_IOS.equals(platform) 
				&& VERSION_IOS_2_5_4.equals(version)){
			return true;
		}
		
		return false;
	}
	
	/**
	 * 兼容处理6
	 * 1.翻译接口AuthToken增加扩展认证内容
	 * 2.要兼容的旧版本
	 * serviceType:出国翻译官
	 * Android: 2.4.1以下
	 * IOS    ：2.5.6以下 
	 * @param platform
	 * @param version
	 * @param authAccount
	 * @return
	 */
	public static boolean compat6(String serviceType, String platform, String version){
		if(DictConstants.DICT_PLATFORM_JAVA.equals(platform)){
			return true;
		}
		
		if(!DictConstants.DICT_SERVICETYPE_VOICETRANS.equals(serviceType)){
			return false;
		}
		
		if(DictConstants.DICT_PLATFORM_IOS.equals(platform) 
				&& StringUtil.beforeVersion(version, VERSION_IOS_2_5_7)){
			return true;
		}
		if(DictConstants.DICT_PLATFORM_ANDROID.equals(platform) 
				&& StringUtil.beforeVersion(version, VERSION_ANDROID_2_4_1)){
			return true;
		}
		
		return false;
	}
	
	/**
	 * 客服版是否禁用（老版本停用）
	 * @param platform
	 * @param version
	 * @return
	 */
	public static boolean isForbidden4Cs(String platform, String version){
		if(DictConstants.DICT_PLATFORM_IOS.equals(platform)){
			if("5".equals(version)
					|| StringUtil.beforeVersion(version, "1.0.1")){
				return true;
			}
		}else if(DictConstants.DICT_PLATFORM_ANDROID.equals(platform)){
			if(StringUtil.beforeVersion(version, "1.0.1")){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 图片翻译处理是否校验操作员
	 * 老版本校验，新版本不校验	 * 
	 * Android:1.1.7及以下
	 * IOS    ：1.1.2及以下 
	 * @param platform
	 * @param version
	 * @return
	 */
	public static boolean isVerifyOperator4TransPicDeal(String platform, String version){
		if(DictConstants.DICT_PLATFORM_IOS.equals(platform) 
				&& StringUtil.beforeOrEqualVersion(version, "1.1.2")){
			return true;
		}
		if(DictConstants.DICT_PLATFORM_ANDROID.equals(platform) 
				&& StringUtil.beforeOrEqualVersion(version, "1.1.7")){
			return true;
		}
		
		return false;
	}
	
	/**
	 * 是否加密重要的工作信息
	 * 老版本不加密，其他加密 
	 * @param serviceType
	 * @param platform
	 * @param version
	 * @return
	 */
	public static boolean isEncryptJob(String serviceType, String platform, String version){
		if(StringUtil.isBlankOrNull(version)) return true;
		
		//蓝猫星球+ios+2.1
		if(DictConstants.DICT_SERVICETYPE_BLUECAT.equals(serviceType)
				&& DictConstants.DICT_PLATFORM_IOS.equals(platform)
				&& StringUtil.beforeOrEqualVersion(version, "2.1")){
			return false;
		}
		
		return true;
	}
	
}
