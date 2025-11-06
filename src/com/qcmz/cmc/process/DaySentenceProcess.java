package com.qcmz.cmc.process;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.bdc.ws.provide.locator.MsgPushWS;
import com.qcmz.bdc.ws.provide.vo.MsgPushReq;
import com.qcmz.cmc.cache.LangMap;
import com.qcmz.cmc.constant.BeanConstant;
import com.qcmz.cmc.entity.CmcPkgDaysentence;
import com.qcmz.cmc.service.IDaySentenceService;
import com.qcmz.cmc.util.BeanConvertUtil;
import com.qcmz.cmc.ws.provide.vo.DaySentenceBean;
import com.qcmz.cmc.ws.provide.vo.DaySentenceLangBean;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.AzureBlobUtil;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.FilePathUtil;
import com.qcmz.framework.util.HttpUtil;
import com.qcmz.framework.util.IPUtil;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.framework.util.log.Operator;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
@Component
public class DaySentenceProcess {
	@Autowired
	private IDaySentenceService daySentenceService;
	@Autowired
	private LangMap langMap;

	private Logger logger = Logger.getLogger(this.getClass());
	
	private Map<String, List<DaySentenceLangBean>> daySentenceLangMap = new HashMap<String, List<DaySentenceLangBean>>();
	private static List<DaySentenceLangBean> langs = new ArrayList<DaySentenceLangBean>();
	static{
		langs.add(new DaySentenceLangBean("en", "English", "daySentence_en"));
		langs.add(new DaySentenceLangBean("ja", "Japanese", "daySentence_ja"));
		langs.add(new DaySentenceLangBean("ko", "Korean", "daySentence_ko"));
	}
	
	/**
	 * 获取所有的每日一句
	 * @return
	 */
	public List<DaySentenceBean> findAllSentence(){
		List<DaySentenceBean> result = new ArrayList<DaySentenceBean>();
		
		List<CmcPkgDaysentence> list = daySentenceService.findSentence();
		for (CmcPkgDaysentence sentence : list) {
			result.add(BeanConvertUtil.toDaySentenceBean(sentence, langMap));
		}
		
		return result;
	}
	
	/**
	 * 分页获取每日一句列表
	 * @param from
	 * @param pageBean
	 */
	@SuppressWarnings("unchecked")
	public void findSentence(String from, PageBean pageBean){
		daySentenceService.findSentence(from, pageBean);
		List<CmcPkgDaysentence> list = (List<CmcPkgDaysentence>) pageBean.getResultList();
		if(list.isEmpty()) return;
		
		List<DaySentenceBean> result = new ArrayList<DaySentenceBean>();
		for (CmcPkgDaysentence sentence : list) {
			result.add(BeanConvertUtil.toDaySentenceBean(sentence, langMap));
		}
		
		pageBean.setResultList(result);		
	}
		
	/**
	 * 获取每日一句的语言列表
	 * @param language
	 * @return
	 * 修改历史：
	 */
	public List<DaySentenceLangBean> findLang(String language){
		if(StringUtil.isBlankOrNull(language)){
			language = DictConstants.DICT_LANG_ZHCN;
		}
		List<DaySentenceLangBean> result = daySentenceLangMap.get(language);
		if(result!=null){
			return result;
		}
		
		result = new ArrayList<DaySentenceLangBean>();
		DaySentenceLangBean bean = null;
		String langName = null;
		for (DaySentenceLangBean lang : langs) {
			bean = new DaySentenceLangBean(lang.getFrom(), lang.getFromName(), lang.getPushTag());
			langName = langMap.getLangName4Text(lang.getFrom(), language);
			if(StringUtil.notBlankAndNull(langName)){
				bean.setFromName(langName);
			}
			result.add(bean);
			daySentenceLangMap.put(language, result);
		}
		return result;
	}
	
	/**
	 * 获取语言对应的推送标签
	 * @param langCode
	 * @return
	 * 修改历史：
	 */
	public static String getPushTag(String langCode){
		for (DaySentenceLangBean lang : langs) {
			if(lang.getFrom().equals(langCode)){
				return lang.getPushTag();
			}
		}
		return null;
	}
	
	/**
	 * 每日推送 
	 * 修改历史：
	 */
	public void push(){
		//获取待推送列表
		List<CmcPkgDaysentence> list = daySentenceService.findSentence4Push();
		if(list.isEmpty()){
			return;
		}
		
		//推送
		Date now = new Date();
		String pushTag = null;
		for (CmcPkgDaysentence entity : list) {
			try {
				pushTag = getPushTag(entity.getFromlang());
				if(StringUtil.isBlankOrNull(pushTag)) continue;
				
				entity.setPushtime(now);
				
				MsgPushReq req = new MsgPushReq();
				req.setTypeid(MsgPushWS.MSGTYPE_VOICETRANS_DAY);
				req.setToType(MsgPushWS.PUSHTOTYPE_TAG);
				req.setTo(pushTag);
				req.setMsg(entity.getTitle());
				req.setIdentify(String.valueOf(entity.getSentid()));
				req.setTiming(DateUtil.formatDateTime(entity.getReleasetime()));
				MsgPushWS.pushMsg(req);
				
				entity.setPushstatus(SystemConstants.STATUS_SUCCESS);
			} catch (Exception e) {
				logger.error("每日一句推送失败："+entity.getSentid(), e);
			}
		}
		
		//保存推送结果
		daySentenceService.saveOrUpdateAll(list);
	}
	
	/**
	 * 批量创建静态页
	 * @param params
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public void createHtml(Map<String, String> params){
		int page = 1;
		int pageSize = 100;
		
		PageBean pageBean = null;
		List<Long> ids = null;
		StringBuilder sbLog = null;
		
		do{
			pageBean = new PageBean(page, pageSize);
			daySentenceService.findId(pageBean);
			ids = (List<Long>) pageBean.getResultList();
			if(ids.isEmpty()) break;
			
			sbLog = new StringBuilder()
				.append("创建静态页，第").append(page).append("页")
				.append("，共").append(pageBean.getPageCount()).append("页，")
				.append(pageBean.getTotal()).append("条");
			logger.info(sbLog.toString());

			for (Long id : ids) {
				daySentenceService.createHtml(id);
			}
			
			page++;
		}while(page<=pageBean.getPageCount());
		logger.info("完成创建每日一句静态页");		
	}
	
	/**
	 * 生产补充缺失的每日一句
	 * 定时执行，每次往前校验31天
	 * 如果不存在，则取历史中同一天的数据
	 * 如果同一天也不存在，则同近一年中的数据中选一条
	 * 入库并重新生成静态页
	 */
	public void produceDaySentence(){
		String[] arrLang = new String[]{"en","ja","ko"};
		Date today = DateUtil.getSysDateOnly();
		Date yesterday = DateUtil.addDay(today, -1);
		Date end = DateUtil.addDay(today, 31);
		Date yearAgo = DateUtil.addYear(today, -1);
		Date day = null;
		String dayStr = null;
		int yearIndex = -1;
		Operator oper = new Operator(SystemConstants.OPERATOR_DEFAULT, IPUtil.LOCAL_IP);
		CmcPkgDaysentence optionBean = null;
		CmcPkgDaysentence newBean = null;
		
		List<CmcPkgDaysentence> list = null;
		List<CmcPkgDaysentence> yearList = null;
		Map<Date, CmcPkgDaysentence> map = new HashMap<Date, CmcPkgDaysentence>();
		for (String from : arrLang) {
			list = daySentenceService.findSentence(from, today, end);
			for (CmcPkgDaysentence bean : list) {
				map.put(DateUtil.setMinTime(bean.getReleasetime()), bean);
			}
			
			day = yesterday;
			while(true){
				day = DateUtil.addDay(day, 1);
				if(day.after(end)) break;
				
				//存在，下一天
				if(map.get(day)!=null) continue;
				
				dayStr = DateUtil.format(day, "MMdd");
				//获取同一天的数据
				list = daySentenceService.findSentence(from, dayStr);
				if(!list.isEmpty()){
					optionBean = list.get(0);
				}else{
					if(yearList==null){
						//获取一年内的数据
						yearList = daySentenceService.findSentence(from, yearAgo, yesterday);
					}
					if(yearList.isEmpty()) continue;
					if(yearIndex==-1){
						yearIndex = yearList.size();
					}
					yearIndex--;
					optionBean = yearList.get(yearIndex);
				}
				if(optionBean!=null){
					newBean = new CmcPkgDaysentence();
					newBean.setTitle(optionBean.getTitle());
					newBean.setFromlang(optionBean.getFromlang());
					newBean.setSrc(optionBean.getSrc());
					newBean.setTolang(optionBean.getTolang());
					newBean.setDst(optionBean.getDst());
					newBean.setSmallpic(optionBean.getSmallpic());
					newBean.setPic(optionBean.getPic());
					newBean.setSource(optionBean.getSource());
					newBean.setReleasetime(DateUtil.setTime(day, 8, 0, 0));
					newBean.setStatus(SystemConstants.STATUS_ON);
					daySentenceService.saveOrUpdate(newBean, oper);
					logger.info("生产每日一句："+from+","+dayStr);
				}
			}
			
			map.clear();
			day = today;
			list.clear();
			yearList=null;
			yearIndex = -1;
		}
		
		CacheSynProcess.synCache(BeanConstant.BEANID_CACHE_DAYSENTENCE);
	}
	
	/**
	 * 本地图片文件迁移到AzureBlob
	 */
	public void migrateFileLocal2AzureBlob(){
		String destServer = "https://f2.qcmuzhi.com";
		File file = null;
		String tempDirPath = FilePathUtil.getAbsolutePath4Temp();
		String picUrl = null;
		boolean migrate = false;
		
		logger.info("开始图片迁移");
		List<CmcPkgDaysentence> list = daySentenceService.findSentence();
		for (CmcPkgDaysentence entity : list) {
			migrate = false;
			
			//迁移小图
			if(!entity.getSmallpic().startsWith(destServer)){
				file = HttpUtil.download(entity.getSmallpic(), tempDirPath);
				picUrl = AzureBlobUtil.uploadFile("cmc", "daysentence/images/"+file.getName(), file);
				if(StringUtil.notBlankAndNull(picUrl)){
					daySentenceService.updateSmallPic(entity.getSentid(), picUrl);
					migrate = true;
				}
			}
			
			//迁移大图
			if(!entity.getPic().startsWith(destServer)){
				file = HttpUtil.download(entity.getPic(), tempDirPath);
				picUrl = AzureBlobUtil.uploadFile("cmc", "daysentence/images/"+file.getName(), file);
				if(StringUtil.notBlankAndNull(picUrl)){
					daySentenceService.updatePic(entity.getSentid(), picUrl);
					migrate = true;
				}
			}
			
			
			//重新生成静态页
			if(migrate){
				daySentenceService.createHtml(entity.getSentid());
				logger.info("完成图片迁移："+entity.getSentid());
			}
		}
		
		logger.info("完成图片迁移");
	}
}