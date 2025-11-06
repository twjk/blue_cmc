package com.qcmz.cmc.process;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.util.concurrent.AtomicLongMap;
import com.qcmz.cmc.cache.CatMap;
import com.qcmz.cmc.cache.LangMap;
import com.qcmz.cmc.cache.TransLibMap;
import com.qcmz.cmc.cache.TransResultMap;
import com.qcmz.cmc.config.JobConfig;
import com.qcmz.cmc.config.TransConfig;
import com.qcmz.cmc.config.TransLibConfig;
import com.qcmz.cmc.constant.BeanConstant;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.entity.CmcLang;
import com.qcmz.cmc.entity.CmcPkgTheme;
import com.qcmz.cmc.proxy.trans.BaiduLangDetectProxy;
import com.qcmz.cmc.service.ILangService;
import com.qcmz.cmc.service.IThemeService;
import com.qcmz.cmc.service.ITransLibService;
import com.qcmz.cmc.util.CmcUtil;
import com.qcmz.cmc.util.TransUtil;
import com.qcmz.cmc.vo.TransLib4Cache;
import com.qcmz.cmc.vo.TransLibraryEntity;
import com.qcmz.cmc.vo.TransResult4Cache;
import com.qcmz.cmc.ws.provide.vo.LangBean;
import com.qcmz.cmc.ws.provide.vo.TransDstBean;
import com.qcmz.cmc.ws.provide.vo.TransLibAddBean;
import com.qcmz.cmc.ws.provide.vo.TransLibBean;
import com.qcmz.cmc.ws.provide.vo.TransLibDownloadBean;
import com.qcmz.cmc.ws.provide.vo.TransResult;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.exception.DataRepeatException;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.framework.util.log.OperLogUtil;
import com.qcmz.framework.util.log.Operator;

/**
 * 类说明：译库处理
 * 修改历史：
 */
@Component
public class TransLibProcess {
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private ITransLibService transLibService;
	@Autowired
	private ILangService langService;
	@Autowired
	private IThemeService themeService;

	@Autowired
	private LangMap langMap;
	@Autowired
	private TransLibMap transLibMap;
	@Autowired
	private TransResultMap transResultMap;
	@Autowired
	private CatMap catMap;
	
	@Autowired
	private BaiduLangDetectProxy baiduLangDetectProxy;
	@Autowired
	private TransProcess transProcess;
	
	/**
	 * 译库命中次数,libClass|libId
	 */
	private AtomicLongMap<String> hitCount = AtomicLongMap.create();
	
	/**
	 * 待入库译库队列
	 */
	private static ConcurrentLinkedQueue<TransLibraryEntity> libs = new ConcurrentLinkedQueue<TransLibraryEntity>();
	
	/**
	 * 添加译库命中次数
	 * @param libs
	 */
	public void addHitCount(TransLib4Cache lib){
		if(lib!=null){
			StringBuilder sb = new StringBuilder(lib.getLibClass()).append("|").append(lib.getLibId());
			hitCount.incrementAndGet(sb.toString());
		}
	}
	
	/**
	 * 添加译库命中次数
	 * @param libs
	 */
	public void addHitCount(TransLibraryEntity lib){
		if(lib!=null){
			StringBuilder sb = new StringBuilder(lib.getLibClass()).append("|").append(lib.getLibid());
			hitCount.incrementAndGet(sb.toString());
		}
	}
	
	/**
	 * 保存译库命中次数
	 */
	public void saveHitCount(){
		if(hitCount.isEmpty()) return;
		Map<String, Long> countMap = hitCount.asMap();
		hitCount = AtomicLongMap.create();
		
		String[] arr = null;
		String libClass;
		Long libId;
		for (String key : countMap.keySet()) {
			try {
				arr = StringUtil.split(key, "|");
				libClass = arr[0];
				libId = Long.valueOf(arr[1]);
				transLibService.increaseHitCount(libClass, libId, countMap.get(key).intValue());
			} catch (Exception e) {
				logger.error("译库命中次数入库失败："+key, e);
			}
		}
	}
	
	/**
	 * 分页获取译库
	 * @param lastId
	 * @param pageSize
	 * @return
	 */
	public List<TransLibBean> findTransLib4Web(Long lastId, int pageSize){
		List<TransLibBean> result = new ArrayList<TransLibBean>();
		TransLibBean bean;
		
		if(pageSize<=0){
			pageSize = 100;
		}else if(pageSize>1000){
			pageSize = 1000;
		}
		
		List<TransLibraryEntity> list = transLibService.findLib("CmcTransLibraryWeb", SystemConstants.STATUS_ON, lastId, pageSize);
		for (TransLibraryEntity entity : list) {
			bean = new TransLibBean();
			bean.setLibId(entity.getLibid());
			bean.setFrom(entity.getFromlang());
			bean.setFromName(langMap.getLangName4Text(entity.getFromlang()));
			bean.setSrc(entity.getSrc());
			bean.setTo(entity.getTolang());
			bean.setToName(langMap.getLangName4Text(entity.getTolang()));
			bean.setDst(entity.getDst());
			
			result.add(bean);
		}
		
		return result;
		
	}
		
	/**
	 * 从译库缓存、翻译结果缓存、译库中获取翻译结果
	 * @param from
	 * @param to
	 * @param src
	 * @return
	 * 修改历史：
	 */
	public List<TransDstBean> findDst(String from, String to, String src){
		List<TransDstBean> result = new ArrayList<TransDstBean>();
		TransDstBean bean = null;
		
		//译库缓存
		List<TransLib4Cache> libs = transLibMap.findLib(from, to, src);
		if(libs!=null) {
			for (TransLib4Cache lib : libs) {
				bean = new TransDstBean();
				bean.setDst(lib.getDst());
				bean.setSimilar(lib.getSimilar());
				bean.setTtsSrc(lib.getTtsSrc());
				bean.setTtsText(lib.getTtsText());
				result.add(bean);
				addHitCount(lib);
			}
			return result;
		}
		
		//翻译结果缓存
		TransResult4Cache resultCache = transResultMap.getBean(from, to, src);
		if(resultCache!=null){
			bean = new TransDstBean();
			bean.setDst(resultCache.getDst());
			bean.setSimilar(resultCache.getSimilar());
			result.add(bean);
			resultCache.hited();
			//高频翻译结果入库
			if(JobConfig.TRANLIB_SAVE_ISWORK 
					&& !resultCache.isSaved()
					&& resultCache.getTimes()>=TransConfig.TRANS_CACHE_DBTHRESHOLD){
				try {
					TransLibraryEntity entity = new TransLibraryEntity();
					entity.setLibtype(DictConstant.DICT_LIBTYPE_FREQUENT);
					entity.setFromlang(from);
					entity.setSrc(StringUtil.clearPuncLast(src));
					entity.setTolang(to);
					entity.setDst(resultCache.getDst());
					entity.setTwoway(SystemConstants.STATUS_OFF);
					entity.setHitcount(Long.valueOf(resultCache.getTimes()));
					entity.setSimilar(resultCache.getSimilar());
					entity.setStatus(SystemConstants.STATUS_ON);
					entity.setCheckstatus(SystemConstants.STATUS_OFF);
					entity.setSourceid(DictConstant.DICT_LIBSOURCE_SYSTEM);
					
					addTransLib(entity);
					
					resultCache.saved();
				} catch (Exception e) {}
			}
			return result;
		}
		return result;
	}
	
	/**
	 * 获取译库源语言代码列表
	 * @return
	 * 修改历史：
	 */
	public List<CmcLang> findFromLang(){
		return langService.findLangByCode(DictConstants.DICT_LANGTYPE_SPEECH, transLibService.findFromLang());
	}
	
	/**
	 * 获取译库目标语言代码列表
	 * @return
	 * 修改历史：
	 */
	public List<CmcLang> findToLang(){
		return langService.findLangByCode(DictConstants.DICT_LANGTYPE_SPEECH, transLibService.findToLang());
	}
	
	/**
	 * 获取译库下载信息
	 * @return
	 */
	public TransLibDownloadBean getDownload(){
		TransLibDownloadBean bean = new TransLibDownloadBean();
		bean.setVer(TransLibConfig.TRANSLIB_DOWNLOAD_VERSION);
		bean.setKey(CmcUtil.encrypt(TransLibConfig.TRANSLIB_DOWNLOAD_KEY));
		bean.setMd5(TransLibConfig.TRANSLIB_DOWNLOAD_MD5);
		bean.setUrl(TransLibConfig.TRANSLIB_DOWNLOAD_URL);
		return bean;
	}
	
	/**
	 * 译库加入待入库队列表
	 * @param lib
	 */
	public void addTransLib(TransLibraryEntity lib){
		if(JobConfig.TRANLIB_SAVE_ISWORK){
			libs.add(lib);
		}
	}

	/**
	 * 保存队列中的译库信息
	 */
	public void saveTransLib(){
		if(libs.isEmpty()) return;
		
		Operator oper = new Operator(SystemConstants.OPERATOR_DEFAULT, null);
		
		int size = libs.size();
		for (int i = 1; i <= size; i++) {
			try {
				saveOrUpdate(libs.poll(), oper);
			} catch (Exception e) {
			}
		}
	}
	
	/**
	 * 添加译库
	 * @param bean
	 */
	public void saveTransLib(TransLibAddBean bean){
		Operator oper = new Operator(bean.getOperCd(), bean.getOperName(), null);
		
		TransLibraryEntity entity = new TransLibraryEntity();
		entity.setLibtype(bean.getLibType());
		entity.setFromlang(bean.getFrom());
		entity.setSrc(bean.getSrc());
		entity.setTolang(bean.getTo());
		entity.setDst(bean.getDst());
		entity.setTwoway(bean.getTwoway());
		entity.setCat(bean.getCat());
		entity.setStatus(SystemConstants.STATUS_ON);
		entity.setCheckstatus(SystemConstants.STATUS_ON);
		entity.setSourceid(DictConstant.DICT_LIBSOURCE_INPUT);
		
		try {
			saveOrUpdate(entity, oper);
		} catch (DataRepeatException e) {
		}
	}
	
	/**
	 * 保存译库并更新缓存
	 * @param bean
	 * @param oper
	 * @exception DataRepeatException
	 */
	public void saveOrUpdate(TransLibraryEntity bean, Operator oper){
		List<TransLibraryEntity> List = transLibService.saveOrUpdate(bean, oper);
		CacheSynProcess.synUpdateCacheThrd(BeanConstant.BEANID_CACHE_TRANSLIB, List);
	}	
	
	/**
	 * 更新状态
	 * @param libClass
	 * @param libId
	 * @param status
	 * 修改历史：
	 */
	public void updateStatus(String libClass, Long libId, Integer status, Operator oper){
		transLibService.updateStatus(libClass, libId, status, oper);
		TransLibraryEntity lib = transLibService.getLib(libClass, libId);
		if(lib!=null){
			if(SystemConstants.STATUS_ON.equals(lib.getStatus())){
				CacheSynProcess.synUpdateCacheThrd(BeanConstant.BEANID_CACHE_TRANSLIB, lib);
			}else{
				CacheSynProcess.synDeleteCacheThrd(BeanConstant.BEANID_CACHE_TRANSLIB, lib);
			}
		}
	}
	
	/**
	 * 删除，同时删除反向翻译的结果
	 * @param libClass
	 * @param libId
	 * @param oper
	 * @return
	 * 修改历史：
	 */
	public void delete(String libClass, Long libId, Operator oper){
		List<TransLibraryEntity> deleteList = transLibService.delete(libClass, libId, oper);
		CacheSynProcess.synDeleteCacheThrd(BeanConstant.BEANID_CACHE_TRANSLIB, deleteList);
	}
	
	/**
	 * 译库数据分拣 
	 * 修改历史：
	 */
	public void sortingData(String libClass, Long themeId){
		logger.info("开始译库分拣");
		
		if(themeId!=null){
			CmcPkgTheme theme = themeService.load(themeId);
			if(theme==null){
				throw new DataException("主题不存在");
			}
		}
		
		String toLibClass = null;
		List<TransLibraryEntity> libs = null;
		StringBuilder sbLog = null;
		String from = null;
		String to = null;
		Long catId = null;
		
		Operator oper = OperLogUtil.getSystemOperator();
		List<LangBean> langs = langMap.findLang(DictConstants.DICT_LANGTYPE_SPEECH, DictConstants.DICT_LANG_ZHCN);
		
		List<String> libClasses = TransUtil.findAllLibClass();
		for (String libCls : libClasses) {
			if(StringUtil.notBlankAndNull(libClass)
					&& !libClass.equals(libCls)){
				continue;
			}
			for (LangBean fromLang : langs) {
				for (LangBean toLang : langs) {
					from = fromLang.getLangCode();
					to = toLang.getLangCode();
					if(from.equals(to)) continue;
					
					toLibClass = TransUtil.getLibClass(from, to);
					if(libCls.equals(toLibClass)) continue;
					
					libs = transLibService.findLib(libCls, from, to);
					if(libs.isEmpty()) continue;
					
					sbLog = new StringBuilder("译库迁移：")
						.append(from).append("|").append(to)
						.append(",").append(libCls).append("->").append(toLibClass)
						.append(",数量：").append(libs.size())
						;
					logger.info(sbLog);
					
					for (TransLibraryEntity lib : libs) {
						if(StringUtil.isBlankOrNull(lib.getCat()) && StringUtil.notBlankAndNull(lib.getCatname())){
							catId = catMap.getCatIdByName(DictConstants.CATTYPE_TRANSLIB, lib.getCatname());
							if(catId!=null){
								lib.setCat(catMap.getFullCat(String.valueOf(catId)));
							}
						}
						transLibService.migration(lib, toLibClass, themeId, oper);
					}
				}
			}
		}
		
		CacheSynProcess.synCacheThrd(BeanConstant.BEANID_CACHE_TRANSLIB);
		
		logger.info("完成译库分拣");
	}
	
	/**
	 * 自动校验译库已停用数据
	 * 1.删除文本和语言匹配的记录
	 * 2.机器翻译结果和译文相比较，一致的直接启用 
	 * 修改历史：
	 */
	public void doAutoCheck(){
		logger.info("开始校验译库已停用数据");
		
		int pageSize = 100;
		List<TransLibraryEntity> libs = null;
		String detectFrom = null;
		String detectTo = null;
		TransResult transBean = null;
		
		Operator oper = OperLogUtil.getSystemOperator();
		
		List<String> libClasses = TransUtil.findAllLibClass();
		Long lastId = null;
		for (String libClass : libClasses) {
			do {
				libs = transLibService.findLib(libClass, SystemConstants.STATUS_OFF, lastId, pageSize);
				for (TransLibraryEntity lib : libs) {
					lastId = lib.getLibid();
					detectFrom = baiduLangDetectProxy.detectLang(lib.getSrc());
					if(detectFrom!=null && !detectFrom.equals(lib.getFromlang())){
						transLibService.delete(libClass, lib.getLibid(), oper);
						continue;
					}
					
					detectTo = baiduLangDetectProxy.detectLang(lib.getDst());
					if(detectTo!=null && !detectTo.equals(lib.getTolang())){
						transLibService.delete(libClass, lib.getLibid(), oper);
					}
					
					transBean = transProcess.trans(lib.getFromlang(), lib.getTolang(), lib.getSrc());
					if(transBean!=null && lib.getDst().equalsIgnoreCase(transBean.getDst())){
						transLibService.updateStatus(libClass, lib.getLibid(), SystemConstants.STATUS_ON, oper);
						logger.info("采集译文与翻译结果一致："+lib.getSrc());
					}
				}
				
			} while (!libs.isEmpty());
		}
		
		CacheSynProcess.synCacheThrd(BeanConstant.BEANID_CACHE_TRANSLIB);
		
		logger.info("完成校验译库已停用数据");
	}
}
