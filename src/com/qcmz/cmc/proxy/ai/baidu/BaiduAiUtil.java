package com.qcmz.cmc.proxy.ai.baidu;

import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.framework.util.NumberUtil;
import com.qcmz.framework.util.StringUtil;

public class BaiduAiUtil {
	/**
	 * 提示词
	 */
	private static String PROMPT_BAIDU;
	
	/**
	 * 获取提示词
	 * @return
	 */
	public static String getPrompt(){
		if(StringUtil.isBlankOrNull(PROMPT_BAIDU)){
			//提示词
			//bk:从网页中按岗位提取招聘信息，包括公司、岗位职位、人数值、薪资、最低薪资数值、最高薪资数值、工作经验、最低工作年限数值、学历要求、最低学历、岗位职责、任职要求、联系人电话邮箱微信等、工作地址、判断该岗位是全职还是兼职信息；公司名称、岗位职位信息为未知或者空时，丢弃该岗位；薪资只获取工资部分，多个工资用范围表示，其他的写入岗位职责里。如果学历要求和岗位要求不一致时，以岗位要求里的学历要求为准；工作经验和学历要求，未知时空着；保留岗位职责和任职要求里的薪资福利待遇信息；保留任职要求里的工作经验和学历要求；联系人和电话合并成一个字段。结果以json的形式输出，没有岗位职位时返回空串；json参数为companyName、title、peopleNumber、reward、minReward、maxReward、exp、minExp、edu、minEdu、job、jobRequire、contact、address、workTime，job和jobRequire的多条内容用换行。不需要提示、注意等其他信息，只返回json。
			PROMPT_BAIDU = new StringBuilder()
					.append("分析网页从中按岗位提取招聘信息，包括公司名称、岗位职位、人数、薪资、最低薪资、最高薪资、工作经验、最低工作年限数值、学历要求、岗位职责、任职要求、联系人及其电话邮箱微信QQ等、工作地址、判断该岗位是全职还是兼职信息、判断薪资是次薪时薪日薪月薪还是年薪，")
					.append("各项信息如果未知则置空，没有招聘岗位信息时丢弃。")
					.append("薪资只获取工资部分，多个工资用范围表示，最低薪资和最高薪资只保留数值。")
					.append("如果学历要求和岗位要求不一致时，以岗位要求里的学历要求为准。")
					.append("联系人及其电话邮箱微信QQ等合并成一个字段，用,分隔。")
					.append("提取结果按岗位以json的形式输出，json参数为companyName、title、peopleNum、reward、minReward、maxReward、exp、minExp、edu、job、jobRequire、contact、address、workTime、rewardType，")
					.append("job和jobRequire的多条内容用换行。")
					.toString()
					;
		}
			
//		PROMPT_BAIDU = "从网页中按岗位提取招聘信息，包括公司、岗位职位、人数值、薪资、最低薪资数值、最高薪资数值、工作经验、最低工作年限数值、学历要求、最低学历、岗位职责、任职要求、联系人电话邮箱微信等、工作地址、判断该岗位是全职还是兼职信息；公司名称、岗位职位信息为未知或者空时，丢弃该岗位；薪资只获取工资部分，多个工资用范围表示，其他的写入岗位职责里。如果学历要求和岗位要求不一致时，以岗位要求里的学历要求为准；工作经验和学历要求，未知时空着；保留岗位职责和任职要求里的薪资福利待遇信息；保留任职要求里的工作经验和学历要求；联系人和电话合并成一个字段。结果以json的形式输出，没有岗位职位时返回空串；json参数为companyName、title、peopleNumber、reward、minReward、maxReward、exp、minExp、edu、minEdu、job、jobRequire、contact、address、workTime，job和jobRequire的多条内容用换行。不需要提示、注意、示例等其他信息，只返回json。";
		return PROMPT_BAIDU;
	}

	public static void main(String[] args) {
		System.out.println(getPrompt());
	}

	/**
	 * 数据处理
	 * 未知时返回空串
	 * @param data
	 * @return
	 */
	public static String processData(String data){
		if(StringUtil.isBlankOrNull(data)
				|| "未知".equals(data)
				|| "置空".equals(data) 
				|| "空".equals(data)) return "";
		return data;
	}
	
	/**
	 * 薪酬处理
	 * @param reward
	 * @return
	 */
	public static String formatReward(String reward){
		if(StringUtil.isBlankOrNull(reward)) return reward;
		if("范围值".equals(reward)) return "";
		return reward;
	}
	
	/**
	 * 联系人处理
	 * @param contact
	 * @return
	 */
	public static String formatContact(String contact){
		if(StringUtil.notBlankAndNull(contact)){
			if(contact.endsWith(",")){
				contact = contact.substring(0, contact.length()-1);
			}
		}
		return contact;
	}
	
	/**
	 * 地址处理
	 * @param contact
	 * @return
	 */
	public static String formatAddress(String address){
		if(StringUtil.notBlankAndNull(address)){
			if(address.startsWith("[")){
				address = address.replaceAll("\\[\"", "").replaceAll("\"\\]", "").replaceAll("\",\"", "，");
			}
		}
		return address;
	}
	
	/**
	 * 解析薪酬
	 * 去掉元
	 * @param peopleNum
	 * @return
	 */
	public static Integer parseReward(String reward){
		Integer result = null;
		if(StringUtil.notBlankAndNull(reward)){
			reward = reward.replace("元", "");
			if(NumberUtil.isNumber(reward)){
				result = Integer.valueOf(reward);
			}
		}
		return result;
	}

	/**
	 * 解析薪资类型
	 * @param rewardType
	 * @return
	 */
	public static Integer parseReawrdType(String rewardType){
		if(StringUtil.isBlankOrNull(rewardType)) return null;
		if("时薪".equals(rewardType)){
			return DictConstant.DICT_LOCALTASK_REWARDTYPE_HOUR;
		}
		if("日薪".equals(rewardType)){
			return DictConstant.DICT_LOCALTASK_REWARDTYPE_DATE;
		}
		if("月薪".equals(rewardType)){
			return DictConstant.DICT_LOCALTASK_REWARDTYPE_MONTH;
		}
		if("年薪".equals(rewardType)){
			return DictConstant.DICT_LOCALTASK_REWARDTYPE_YEAR;
		}
		if("次薪".equals(rewardType)){
			return DictConstant.DICT_LOCALTASK_REWARDTYPE_TIME;
		}
		return null;
	}
	
	/**
	 * 解析工作时间类型
	 * @param rewardType
	 * @return
	 */
	public static Integer parseWorkTimeType(String workTimeType, String title){
		if("兼职".equals(workTimeType)
				|| title.contains("兼职") || title.contains("临时")){
			return DictConstant.DICT_LOCALTASK_WORKTIMETYPE_PARTTIME; 
		}
		return DictConstant.DICT_LOCALTASK_WORKTIMETYPE_FULLTIME;
	}
}
