package com.qcmz.cmc.process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.entity.CmcTransLog;
import com.qcmz.cmc.entity.CmcTransWord;
import com.qcmz.cmc.proxy.word.ifltek.IfltekWordAnalysisProxy;
import com.qcmz.cmc.service.ITransLogService;
import com.qcmz.cmc.service.ITransWordService;
import com.qcmz.framework.config.SharePreferences;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.util.StringUtil;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
@Component
public class TransWordProcess {
	@Autowired
	private ITransWordService transWordService;
	@Autowired
	private ITransLogService transLogService;
	
	private Logger logger = Logger.getLogger(this.getClass());
	private SharePreferences preferences = null;
	private String PREFERENCES_KEY_LASTLOGID = "wordAnalysis_lastLogId";
	
	/**
	 * 构造函数
	 * 修改历史：
	 */
	public TransWordProcess() {
		try {
			preferences = new SharePreferences("wordAnalysis");
		} catch (Exception e) {
			logger.error("初始化个性配置失败", e);
		}
	}
	
	/**
	 * 语义分析 
	 * 修改历史：
	 */
	public void analysisWordFromLog(){
		int pageSize = 100;
		int maxLength = 200;
		String from = DictConstants.DICT_LANG_ZHCN;
		String src = null;
		String key = null;
		String[] arrKey = null;
		List<String> words = null;
		Map<String, Long> map = new HashMap<String, Long>();
		List<CmcTransWord> srcs = new ArrayList<CmcTransWord>();
		List<CmcTransWord> wordList = new ArrayList<CmcTransWord>();
		CmcTransWord bean = null;
		StringBuilder sbSrc = null;
		String country = null;
		
		Long lastLogId = preferences.getLong(PREFERENCES_KEY_LASTLOGID, 0L);
		logger.info("开始翻译日志分词："+lastLogId);
		List<CmcTransLog> logs = transLogService.findLog(from, lastLogId, pageSize);
		while(!logs.isEmpty()){
			//清除临时量
			srcs.clear();
			map.clear();
			wordList.clear();
			words = null;
			country = null;
			
			//将日志拼接组装，以降低分词频率
			sbSrc = new StringBuilder();
			for (CmcTransLog log : logs) {
				lastLogId = log.getLogid();
				src = StringUtil.clearByte4(log.getSrc());
				if(StringUtil.isBlankOrNull(src)) continue;
				if(StringUtil.isBlankOrNull(log.getReqcountry())) continue;
				
				if(country==null){
					country = log.getReqcountry();
				}
				if(!log.getReqcountry().equals(country)
						|| src.length()+sbSrc.length()>maxLength){
					bean = new CmcTransWord();
					bean.setCountry(country);
					bean.setWord(sbSrc.toString());
					srcs.add(bean);
					country = log.getReqcountry();
					sbSrc = new StringBuilder(src);
				}else if(sbSrc.length()==0){
					sbSrc.append(src);
				}else{
					sbSrc.append("\n").append(src);
				}
			}
			
			if(sbSrc.length()>0){
				bean = new CmcTransWord();
				bean.setCountry(country);
				bean.setWord(sbSrc.toString());
				srcs.add(bean);
			}
			
			//分词并计数
			for (CmcTransWord s : srcs) {
				words = IfltekWordAnalysisProxy.analysis(s.getWord());
				if(words.isEmpty()){
					continue;
				}
				
				for (String word : words) {
					if(word.length()>100){
						logger.info("词语太长抛弃："+word);
						continue;
					}
					key = new StringBuilder(s.getCountry()).append("|").append(word).toString();
					if(map.get(key)==null){
						map.put(key, 1L);
					}else{
						map.put(key, map.get(key)+1);
					}
				}
			}
			
			//入库
			for (String key2 : map.keySet()) {
				arrKey = key2.split("\\|",2);
				bean = new CmcTransWord();
				bean.setLangcode(from);
				bean.setCountry(arrKey[0]);
				bean.setWord(arrKey[1]);
				bean.setTranscount(map.get(key2));
				wordList.add(bean);
			}
			transWordService.saveOrUpdateAll(wordList);
			
			preferences.saveProperty(PREFERENCES_KEY_LASTLOGID, lastLogId);

			//继续下一次
			logs = transLogService.findLog(from, lastLogId, pageSize);
		}
		logger.info("完成翻译日志分词："+lastLogId);
	}
}
