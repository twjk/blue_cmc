package com.qcmz.cmc.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.framework.util.CollectionUtil;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.DoubleUtil;
import com.qcmz.framework.util.NumberUtil;
import com.qcmz.framework.util.StringUtil;

public class LocalTaskUtil {
	
	/**
	 * 用户是否可以重新编辑岗位
	 * @param status
	 * @return
	 */
	public static boolean canReEdit(Integer status){
		if(DictConstant.DICT_LOCALTASK_STATUS_OFF.equals(status)) return true;
		if(DictConstant.DICT_LOCALTASK_STATUS_ON.equals(status)) return true;
		if(DictConstant.DICT_LOCALTASK_STATUS_VERIFYREFUSE.equals(status)) return true;
		return false;
	}
	
	/**
	 * 用户是否可以发布岗位
	 * @param status
	 * @return
	 */
	public static boolean canPublish(Integer status){
		if(DictConstant.DICT_LOCALTASK_STATUS_OFF.equals(status)) return true;
		if(DictConstant.DICT_LOCALTASK_STATUS_EDITING.equals(status)) return true;
		return false;
	}
	
	/**
	 * 用户是否可以下架岗位
	 * @param status
	 * @return
	 */
	public static boolean canTakeDown(Integer status){
		if(DictConstant.DICT_LOCALTASK_STATUS_OFF.equals(status)) return false;
		return true;
	}
	
	/**
	 * 格式化薪酬
	 * @param reward
	 * @param rewardType
	 * @return
	 */
	public static String formatReward(String reward, Integer rewardType){
		if(StringUtil.isBlankOrNull(reward)) return "面议";
		if(DictConstant.DICT_LOCALTASK_REWARDTYPE_HOUR.equals(rewardType) && !reward.contains("时")){
			reward = reward + "/时";
		}else if(DictConstant.DICT_LOCALTASK_REWARDTYPE_DATE.equals(rewardType) && !reward.contains("日")){
			reward = reward + "/日";
		}else if(DictConstant.DICT_LOCALTASK_REWARDTYPE_MONTH.equals(rewardType) && !reward.contains("月")){
			reward = reward + "/月";
		}else if(DictConstant.DICT_LOCALTASK_REWARDTYPE_YEAR.equals(rewardType) && !reward.contains("年")){
			reward = reward + "/年";
		}else if(DictConstant.DICT_LOCALTASK_REWARDTYPE_TIME.equals(rewardType) && !reward.contains("次")){
			reward = reward + "/次";
		}
		return reward;
	}
	
	/**
	 * 解析薪酬
	 * @param reward
	 * @return [minReward, maxReward]
	 */
	public static List<Integer> parseReward(String reward){
		List<Integer> result = new ArrayList<Integer>();

		if(StringUtil.isBlankOrNull(reward)){
			result.add(null);
			result.add(null);
			return result;
		}
		
		//时换成+，方便后续去掉其之后的内容
		reward = reward.replace("——", "-").replace("－", "-").replace("—", "-")
				.replace("～", "-").replace("~", "-").replace("至", "-").replace("到", "-")
				.replace("加", "+").replace("时", "+").replace("H", "+").replace("h", "+")
				.replace("，", "+").replace(",", "+");
		
		int index = reward.indexOf("+");	//去掉+后面内容，如"21元/小时+餐补15元/天"、"27元/小时（底薪25元+2元绩效考核）加班1.5倍加"
		if(index>-1){
			reward = reward.substring(0, index);
		}
		
		Double val = null;
		String valStr = null;
		String[] arr = StringUtil.split(reward, "-");
		for (int i = 0; i < arr.length; i++) {
			if(i>1) break;
			valStr = StringUtil.clearNotNumber(arr[i]);
			if(!NumberUtil.isNumber(valStr) || StringUtil.isBlankOrNull(valStr)) break;
			
			val = DoubleUtil.valueOf(valStr);
			if(val<100){
				if(reward.contains("万") || reward.contains("W") || reward.contains("w")){
					val = val * 10000;
				}else if(reward.contains("K") || reward.contains("k")){
					val = val * 1000;
				}
			}
			
			if(i==1 && val.intValue()<result.get(0)){
				result.add(result.get(0));
			}else{
				result.add(val.intValue());
			}
			
		}
		
		if(result.isEmpty()){
			result.add(null);
			result.add(null);
		}else if(result.size()==1){
			result.add(result.get(0));
		}
		return result;
	}	
	
	/**
	 * 计算基准薪酬
	 * @param minReward
	 * @param maxReward
	 * @param rewardType
	 * @return
	 */
	public static Integer calRefReward(Integer minReward, Integer maxReward, Integer rewardType){
		Integer refReward = maxReward;
		if(refReward==null){
			refReward = minReward;
		}
		if(refReward==null){
			refReward = 0;
		}
		if(DictConstant.DICT_LOCALTASK_REWARDTYPE_MONTH.equals(rewardType)){
			refReward = refReward * 12;
		}
		return refReward;
	}
	
	/**
	 * 解析学历
	 * 0不限1小学2初中3高中职高4专科5本科学士6研究生硕士7博士，主要用于查询比较
	 * @param edu
	 * @return
	 */
	public static Integer parseEdu(String edu){
		if(StringUtil.isBlankOrNull(edu)){
			return DictConstant.DICT_LOCALTASK_EDU_UNLIMITED;
		}
		if(edu.contains("小学")){
			return DictConstant.DICT_LOCALTASK_EDU_PRIMARY; 
		}
		if(edu.contains("初中")){
			return DictConstant.DICT_LOCALTASK_EDU_JUNIORHIGH; 
		}
		if(edu.contains("高中") || edu.contains("职高") || edu.contains("中专")){
			return DictConstant.DICT_LOCALTASK_EDU_HIGH; 
		}
		if(edu.contains("大专") || edu.contains("专科")){
			return DictConstant.DICT_LOCALTASK_EDU_JUNIORCOLLEGE; 
		}
		if(edu.contains("本科") || edu.contains("学士") || edu.contains("大学")){
			return DictConstant.DICT_LOCALTASK_EDU_BACHELOR; 
		}
		if(edu.contains("硕士") || edu.contains("研究生")){
			return DictConstant.DICT_LOCALTASK_EDU_GRADUATE; 
		}
		if(edu.contains("博士")){
			return DictConstant.DICT_LOCALTASK_EDU_DOCTORAL; 
		}
		
		if(edu.contains("不限")){
			return DictConstant.DICT_LOCALTASK_EDU_UNLIMITED;
		}
		
		System.out.println("解析学历失败："+edu);
		return DictConstant.DICT_LOCALTASK_EDU_UNLIMITED;
	}
	
	/**
	 * 解析工作经验
	 * 去掉年
	 * @param peopleNum
	 * @return
	 */
	public static Integer parseExp(String exp){
		if(StringUtil.isBlankOrNull(exp)
				|| exp.contains("无经验")
				|| exp.contains("无需经验") 
				|| exp.contains("应届")) return 0;
		if(exp.contains("月") || exp.contains("半年")) return 1;
		
		String expN = StringUtil.clearNotNumber(exp);
		if(NumberUtil.isNumber(expN)){
			return DoubleUtil.round(DoubleUtil.valueOf(expN), 0).intValue();
		}
		System.out.println("解析工作经验失败："+exp);
		
		return 0;
	}
	
	/**
	 * 公司文件目录路径
	 * @return
	 */
	public static String getCompanyBasePath(){
		String date = DateUtil.formatDate7(new Date());
		return new StringBuilder().append("localcompany").append("/").append(date).toString();
	}
	
	public static void main(String[] args) {
		List<Integer> list = null;
//		Integer rewardType = DictConstant.DICT_LOCALTASK_REWARDTYPE_MONTH;
		
		List<String> rewards = new ArrayList<String>();
		rewards.add("税前全年收入6.5万—8.5万");
		rewards.add("5111元—6839元/月");
		rewards.add("4K+");
		rewards.add("底薪4000元+提成");
		rewards.add("20～450元/天");
		rewards.add("6000～1万以上不封顶");
		rewards.add("2～3万以上不封顶");
		rewards.add("20元/小时");
		rewards.add("17元/小时+150全勤");
		rewards.add("27元/小时（底薪25元+2元绩效考核）加班1.5倍加");
		rewards.add("20元/H，夜班补10元/晚");
		rewards.add("19元/H，三天后每天先发150元");
		rewards.add("27元/小时（底薪25元+2元绩效考核）加班1.5倍加");
		rewards.add("面议");
		rewards.add("3000－5000");
		rewards.add("26.27. 29 300包餐，28号400包餐");

		for (String reward : rewards) {
			list = parseReward(reward);
//			list = calMonthReward(reward, rewardType);
			System.out.println(CollectionUtil.toString(list)+ " <<< " + reward);
		}
	}
}
