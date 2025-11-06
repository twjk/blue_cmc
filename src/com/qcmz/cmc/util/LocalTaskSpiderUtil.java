package com.qcmz.cmc.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LocalTaskSpiderUtil {
	/**
	 * 抛弃标题包含指定关键词的采集信息
	 */
	private static List<String> excludeKeywords = new ArrayList<String>();
	static{
		excludeKeywords.add("临时管制");
		excludeKeywords.add("放假");
		excludeKeywords.add("指南");
		excludeKeywords.add("反诈");
		excludeKeywords.add("诈骗");
		excludeKeywords.add("防骗");
		excludeKeywords.add("声明");
		excludeKeywords.add("赵公子招");
		excludeKeywords.add("信息发布平台");
		excludeKeywords.add("信息发布微信群");
		excludeKeywords.add("脱单");
		excludeKeywords.add("交友");
		excludeKeywords.add("上岸");
	}
	
	/**
	 * 抛弃符合指定的标题的采集信息
	 */
	private static List<String> excludeTitleList = new ArrayList<String>();
	static{
		excludeTitleList.add("荔湾区公益招聘服务指引");				//荔湾就业
		excludeTitleList.add("求职？招聘？“白云就业”线上来帮忙！");	//广州白云就业
		excludeTitleList.add("普陀企业请关注！失业保险稳岗返还补贴未确认名单在这里！");	//90乐业普陀
		excludeTitleList.add("天津市河西区妇产科医院招聘简章");		//142河西人才
		excludeTitleList.add("天津市河西医院招聘外聘工作人员");		//142河西人才
		excludeTitleList.add("因病提前退休申报说明");					//142河西人才
		excludeTitleList.add("筑梦青春 就业扬帆——致2024届高校毕业生的一封信");				//142河西人才
		excludeTitleList.add("河西区网络招聘会报名方式");				//142河西人才
		excludeTitleList.add("红海直聘邀请企业入驻，期待您的加入！");	//239增城红海直聘
		excludeTitleList.add("【职场热点】灵活就业人员请注意，快来收下这份参保指南");
		excludeTitleList.add("商务合作");	//xxx赵公子
		excludeTitleList.add("信息发布");	//xxx赵公子
		excludeTitleList.add("你的性格，告诉你适合什么职业？");
		excludeTitleList.add("企业/店铺要招人，请看这条→");	//苏州招聘
		excludeTitleList.add("求职者找工作，请看这条→");	//苏州招聘
		//excludeTitleList.add("");	//
	}
	
	/**
	 * 特定来源，由于含有非招聘信息较多，要求其标题含有招聘相关信息
	 */
	private static Set<Long> specSourceSet = new HashSet<Long>();
	static{
		specSourceSet.add(94L);	//乐业闵行
	}
	
	/**
	 * 是否抛弃。指定来源，对于一些高频出现并确认没用的标题，直接抛弃
	 * @param sourceId
	 * @param title
	 * @return
	 */
	public static boolean isExclude(Long sourceId, String title){
		for (String keyword : excludeKeywords) {
			if(title.contains(keyword)) return true;
		}
		
		if(specSourceSet.contains(sourceId)){
			if(!JobArticleUtil.containJob(title)) return true;
		}
		return excludeTitleList.contains(title);
	}

	public static void main(String[] args) {
		System.out.println(isExclude(93L, "放假"));
	}
}
