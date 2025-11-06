package com.qcmz.cmc.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.qcmz.cmc.cache.LangMap;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.entity.CmcCtOption;
import com.qcmz.cmc.entity.CmcCtSubject;
import com.qcmz.cmc.entity.CmcCtTask;
import com.qcmz.cmc.ws.provide.vo.UserCrowdAnswerBean;
import com.qcmz.cmc.ws.provide.vo.UserCrowdAnswerQcBean;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.media.vo.AudioBean;
import com.qcmz.framework.util.SpringUtil;
import com.qcmz.framework.util.StringUtil;

public class CrowdTaskUtil {
	public static String[] SERVICETYPES_CROWDTASK = new String[]{DictConstants.DICT_SERVICETYPE_VOICETRANS, DictConstants.DICT_SERVICETYPE_HUADUODUO};
	/**
	 * 获取语言名称
	 * "中文->普通话"
	 * @param langMap
	 * @param langCode
	 * @return
	 */
	public static String getLangName(String langCode){
		LangMap langMap = (LangMap) SpringUtil.getBean("langMap");
		String langName = langMap.getLangName4Speech(langCode);
		if("中文".equals(langName)){
			langName = "普通话";
		}
		return langName;
	}
	
	/**
	 * 任务是否有效
	 * @param task
	 * @return
	 */
	public static boolean isValid(CmcCtTask task){
		if(task==null) return false;
		
		if(!SystemConstants.STATUS_ON.equals(task.getStatus())){
			return false;
		}
		
		long now = System.currentTimeMillis();
		if(task.getStarttime().getTime()<=now && task.getEndtime().getTime()>=now){
			return true;
		}
		
		return false;
	}
	
	/**
	 * 任务是否过期
	 * @param task
	 * @return
	 */
	public static boolean isExpired(CmcCtTask task){
		if(task==null) return true;

		long now = System.currentTimeMillis();
		if(task.getStarttime().getTime()<=now && task.getEndtime().getTime()>=now){
			return true;
		}
		
		return false;
	}
	
	/**
	 * 检查音频格式是否是16k16bit单声道
	 * @param af
	 * @return
	 */
	public static boolean checkAudioFormat(AudioBean audioBean){
		if(audioBean==null) return false;
		if(audioBean.getChannels()==1
				&& audioBean.getSampleRate()==16000
				&& audioBean.getSampleSizeInBits()==16){
			return true;
		}
		return false;
	}
	
	/**
	 * 检查用户答案
	 * @param answers
	 * @param subjectMap 带选项的题目
	 * @return
	 */
	public static boolean checkUserAnswers(List<UserCrowdAnswerBean> answers, Map<Long, CmcCtSubject> subjectMap){
		if(answers==null || answers.isEmpty()) return false;
		
		Map<Long, List<UserCrowdAnswerBean>> answerMap = new HashMap<Long, List<UserCrowdAnswerBean>>();
		for (UserCrowdAnswerBean answer : answers) {
			if(answer.getSubjectId()==null) return false;
			
			if(answerMap.get(answer.getSubjectId())==null){
				answerMap.put(answer.getSubjectId(), new ArrayList<UserCrowdAnswerBean>());
			}
			answerMap.get(answer.getSubjectId()).add(answer);
		}
		
		CmcCtSubject subject = null;
		CmcCtOption option = null;
		List<UserCrowdAnswerBean> subjectAnswers = null;
		Map<Long, CmcCtOption> subjectOptionMap = new HashMap<Long, CmcCtOption>();
		
		for (Long subjectId : answerMap.keySet()) {
			subject = subjectMap.get(subjectId);
			subjectAnswers = answerMap.get(subjectId);
			subjectOptionMap.clear();
			for (CmcCtOption o : subject.getOptions()) {
				subjectOptionMap.put(o.getOptionid(), o);
			}
			
			//选项校验
			for (UserCrowdAnswerBean answerBean : subjectAnswers) {
				if(subjectOptionMap.keySet().isEmpty()){
					if(answerBean.getOptionId()!=null) return false;
				}else{
					if(answerBean.getOptionId()==null) return false;
					option = subjectOptionMap.get(answerBean.getOptionId());
					if(option==null) return false;
					//补全
					answerBean.setOptionValue(option.getOptionvalue());
				}
			}
			
			//音频采集校验
			if(DictConstant.DICT_CROWDTASK_SUBJECTCAT_AUDIOCOLLECTION.equals(subject.getSubjectcat())){
				if(subjectAnswers.size()<subject.getRecordtimes()) return false;
				for (UserCrowdAnswerBean answer : subjectAnswers) {
					if(StringUtil.isBlankOrNull(answer.getVoice())) return false;
				}
			}
			//助力截图/添加剂采集校验
			if(DictConstant.DICT_CROWDTASK_SUBJECTCAT_HELP.equals(subject.getSubjectcat())
					|| DictConstant.DICT_CROWDTASK_SUBJECTCAT_ADDITIVE.equals(subject.getSubjectcat())){
				for (UserCrowdAnswerBean answer : subjectAnswers) {
					if(StringUtil.isBlankOrNull(answer.getPic())) return false;
				}
			}
		}
		
		return true;
	}
	
	/**
	 * 计算总得分
	 * 只要有一题的答案分数为null，返回null
	 * @param answers
	 * @return
	 */
	public static Integer calTotalScore(List<UserCrowdAnswerBean> answers){
		if(answers==null || answers.isEmpty()) return null;
		Map<Long, Integer> scoreMap = new HashMap<Long, Integer>();
		for (UserCrowdAnswerBean preAnswer : answers) {
			//有一题的答案分数为null，不计算返回null
			if(preAnswer.getAnswerScore()==null) return null;
			
			if(scoreMap.get(preAnswer.getSubjectId())==null){
				scoreMap.put(preAnswer.getSubjectId(), preAnswer.getAnswerScore());
			}else if(preAnswer.getAnswerScore()>scoreMap.get(preAnswer.getSubjectId())){
				scoreMap.put(preAnswer.getSubjectId(), preAnswer.getAnswerScore());
			}
		}
		Integer totalScore = 0;
		for (Long key : scoreMap.keySet()) {
			totalScore = totalScore + scoreMap.get(key);
		}
		return totalScore;
	}
	
	/**
	 * 检查题目审校结果
	 * @param subjectCat
	 * @param subjectScore
	 * @param answerQcs
	 * @return
	 */
	public static boolean checkQc(CmcCtSubject subject, Integer subjectScore, List<UserCrowdAnswerQcBean> answerQcs){
		if(DictConstant.DICT_CROWDTASK_SUBJECTCAT_AUDIOCOLLECTION.equals(subject.getSubjectcat())){
			if(answerQcs==null || answerQcs.isEmpty()) return false;
			if(answerQcs.size()<subject.getRecordtimes()) return false;
			for (UserCrowdAnswerQcBean bean : answerQcs) {
				if(bean.getAnswerScore()==null) return false;
			}
		}else{
			if(subjectScore==null) return false;
		}
		return true;
	}
	
	/**
	 * 是否可以取消用户任务
	 * @param status
	 * @return
	 */
	public static boolean canCancel(Integer status){
		if(DictConstant.DICT_USERCROWDTASK_STATUS_APPLY.equals(status)){
			return true;
		}else if(DictConstant.DICT_USERCROWDTASK_STATUSES_ING.contains(status)){
			return true;			
		}
		return false;
	}
}
