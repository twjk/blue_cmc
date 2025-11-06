package com.qcmz.cmc.util;

import java.util.LinkedHashMap;
import java.util.Map;

import com.qcmz.framework.util.FilePathUtil;
import com.qcmz.framework.util.StringUtil;

public class JobArticleUtil {
	/**
	 * 就业信息分类-1301全职工作
	 */
	public static final Long JOBARTICLE_CATID_FULLTIME = 1301L;
	/**
	 * 就业信息分类-1302本地兼职
	 */
	public static final Long JOBARTICLE_CATID_PARTTIME_LOCAL = 1302L;
	/**
	 * 就业信息分类-1303网络兼职
	 */
	public static final Long JOBARTICLE_CATID_PARTTIME_ONLINE = 1303L;
	/**
	 * 就业信息分类-1304就业服务
	 */
	public static final Long JOBARTICLE_CATID_JOBSERVICE = 1304L;
	
	/**
	 * 就业信息分类关键词
	 */
	private static Map<Long, String[]> jobArticleCatKeywordMap = new LinkedHashMap<Long, String[]>();
	static{
		//网络兼职关键词
		jobArticleCatKeywordMap.put(JOBARTICLE_CATID_PARTTIME_ONLINE, new String[]{"居家","远程","线上","在线"});
		//本地兼职关键词
		jobArticleCatKeywordMap.put(JOBARTICLE_CATID_PARTTIME_LOCAL, new String[]{"兼职","时薪","零花钱","临时","零工","普工","日结"});
		//就业服务关键词
		jobArticleCatKeywordMap.put(JOBARTICLE_CATID_JOBSERVICE, new String[]{"招聘会","政策","培训","通知","大会","就业","活动"});
		//全职工作关键词
		jobArticleCatKeywordMap.put(JOBARTICLE_CATID_FULLTIME, new String[]{"招聘","急招","岗位","招聘启事","直聘","诚聘","正式工","招工","用工","公司"});
	}

	/**
	 * 解析标题获得分类编号
	 * @param title
	 * @return
	 */
	public static Long getCatId(String title){
		if(StringUtil.isBlankOrNull(title)) return null;
		for (Map.Entry<Long, String[]> entry : jobArticleCatKeywordMap.entrySet()) {
			for (String keyword : entry.getValue()) {
				if(title.contains(keyword)){
					return entry.getKey();
				}
			}
		}
		return null;
	}
	
	/**
	 * 标题是否包含工作信息
	 * @param title
	 * @return
	 */
	public static boolean containJob(String title){
		if(StringUtil.isBlankOrNull(title)) return false;
		for (Map.Entry<Long, String[]> entry : jobArticleCatKeywordMap.entrySet()) {
			for (String keyword : entry.getValue()) {
				if(title.contains(keyword)){
					return true;
				}
			}
		}
		return false;
	}
	
	public static void main(String[] args) {
		System.out.println(containJob("【北京兼职】300元/两天+提成｜中国国际展览中心-发单扫码兼职！"));
	}
	
	/**
	 * 获取就业信息图片目录路径
	 * jobart/images/yyMMdd/HHmm
	 * @return
	 */
	public static String getPicDirPath(){
		return FilePathUtil.newDirPath("jobart", "images");
	}
}
