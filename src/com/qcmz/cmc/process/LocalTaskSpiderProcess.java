package com.qcmz.cmc.process;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.cache.LocalSourceMap;
import com.qcmz.cmc.config.SystemConfig;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.entity.CmcJobArticle;
import com.qcmz.cmc.entity.CmcJobArticlePic;
import com.qcmz.cmc.entity.CmcLtCompany;
import com.qcmz.cmc.entity.CmcLtSource;
import com.qcmz.cmc.entity.CmcLtSpd;
import com.qcmz.cmc.entity.CmcLtSpdtask;
import com.qcmz.cmc.entity.CmcLtTask;
import com.qcmz.cmc.proxy.ai.baidu.BaiduAiProxy;
import com.qcmz.cmc.proxy.ai.baidu.BaiduAiUtil;
import com.qcmz.cmc.proxy.spider.weixin.WeixinSpider;
import com.qcmz.cmc.proxy.spider.weixin.vo.ArticleListBean;
import com.qcmz.cmc.proxy.spider.weixin.vo.WeixinLoginResult;
import com.qcmz.cmc.service.ILocalCompanyService;
import com.qcmz.cmc.service.ILocalTaskService;
import com.qcmz.cmc.service.ILocalTaskSpiderService;
import com.qcmz.cmc.service.ILocalTaskSpiderTaskService;
import com.qcmz.cmc.service.ILockService;
import com.qcmz.cmc.thrd.LocalSpiderSortingThrd;
import com.qcmz.cmc.util.JobArticleUtil;
import com.qcmz.cmc.util.LocalTaskSpiderUtil;
import com.qcmz.cmc.util.LocalTaskUtil;
import com.qcmz.cmc.vo.LocalTaskBean;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.constant.ExceptionConstants;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.exception.ProxyException;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.JsonUtil;
import com.qcmz.framework.util.MailUtil;
import com.qcmz.framework.util.NumberUtil;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.framework.util.SystemUtil;
import com.qcmz.framework.util.log.OperLogUtil;
import com.qcmz.framework.util.log.Operator;

@Component
public class LocalTaskSpiderProcess {
	private Logger logger = Logger.getLogger(this.getClass());
	private Logger loggerSorting = Logger.getLogger("LoalTaskSpiderSorting");
	
	@Autowired
	private ILocalTaskSpiderTaskService localTaskSpiderTaskService;
	@Autowired
	private ILocalTaskSpiderService localTaskSpiderService;
	@Autowired
	private ILocalTaskService localTaskService;
	@Autowired
	private ILocalCompanyService localCompanyService;
	@Autowired
	private ILockService lockService;
	@Autowired
	private LockProcess lockProcess;
	@Autowired
	private LocalSourceMap localSourceMap;
	@Autowired
	private LocalCompanyProcess localCompanyProcess;	
	
	/**
	 * 采集
	 */
	public void spiderLocalTask(Map<String, String> params){
		if(!SystemConfig.isDebug()){
			logger.warn("非本地环境不能采集同城信息");
			return;
		}
		
		List<ArticleListBean> articleList = null;
		List<CmcLtSpd> spdList = new ArrayList<CmcLtSpd>();
		CmcLtSpd cmcLtSpd = null;
		Date spiderTime = null;
		Long maxMsgId = null;
		Long lastMsgId = null;
		String hint = null;
		StringBuilder sbMail = new StringBuilder();
		
		Date now = new Date();
		
		Date maxTime =  DateUtil.addDay(now, -3);
		List<CmcLtSpdtask> taskList = localTaskSpiderTaskService.findValidTaskJoin(maxTime);
		if(taskList.isEmpty()){
			logger.info("没有采集任务");
			return;
		}
		
		WeixinLoginResult login = null;
		String sourceName = null;
		int count = 0;	//总数
		int taskSeq = 0;
		try {
			login = WeixinSpider.openAndLogin();
			if(StringUtil.isBlankOrNull(login.getToken())){
				throw new ProxyException("登录失败");
			}
			
			for (CmcLtSpdtask spdTask : taskList) {
				taskSeq++;
				sourceName = spdTask.getCmcLtSource().getSourcename();
				hint = new StringBuilder("【").append(taskSeq).append("/").append(taskList.size()).append(sourceName).append("】").toString();
				logger.info(hint+"开始采集");
				
				if(NumberUtil.isNumber(spdTask.getLastidentify())){
					lastMsgId = Long.valueOf(spdTask.getLastidentify());
				}else{
					lastMsgId = null;
				}
				
				articleList = WeixinSpider.spiderArticleList(login, sourceName, lastMsgId);
				
				spiderTime = new Date();
				spdList.clear();

				if(StringUtil.notBlankAndNull(spdTask.getLastidentify())){
					maxMsgId = Long.valueOf(spdTask.getLastidentify());
				}else{
					maxMsgId = null;
				}
				for (ArticleListBean articleBean : articleList) {
					if(maxMsgId==null || articleBean.getAppmsgid()>maxMsgId){
						maxMsgId = articleBean.getAppmsgid();
					}
					
					//抛弃特定标题
					if(articleBean.getTitle().length()>200) continue;
					if(LocalTaskSpiderUtil.isExclude(spdTask.getSourceid(), articleBean.getTitle())) continue;
					//部分公众号标题一样内容不一样，所以不进行标题查重，例如房山就业的"最新岗位信息~"
					
					cmcLtSpd = new CmcLtSpd();
					cmcLtSpd.setSpdtaskid(spdTask.getSpdtaskid());
					cmcLtSpd.setSourceid(spdTask.getSourceid());
					cmcLtSpd.setTitle(articleBean.getTitle());
					cmcLtSpd.setPic(articleBean.getCover());
					cmcLtSpd.setLink(articleBean.getLink());
					cmcLtSpd.setPublishtime(new Date(articleBean.getUpdateTime()*1000));
					cmcLtSpd.setSpdtime(spiderTime);
					cmcLtSpd.setDealprogress(DictConstants.DICT_DEALPROGRESS_WAITING);
					spdList.add(cmcLtSpd);
				}
				//采集结果为空时，正常入库更新采集时间
				String lastIdentify = "";
				if(maxMsgId!=null){
					lastIdentify = String.valueOf(maxMsgId);
				}
				localTaskSpiderTaskService.saveSpiderResult(spdTask.getSpdtaskid(), lastIdentify, spdList);
				
				count = count + spdList.size();				
				logger.info(hint+"完成采集，入库数【"+spdList.size()+"】。合计入库数【"+count+"】");
				
				sbMail.append(sourceName).append("：").append(spdList.size()).append("\r\n");
				if(count>=1000 || taskSeq>=100){
					MailUtil.sendSimpleMail(SystemConfig.MAILS, "更换同城任务采集帐户", "更换同城任务采集帐户");
					break;
				}
				
				if(taskSeq<taskList.size()){
					if(spdList.size()>0){
						SystemUtil.sleep(NumberUtil.random(180000, 120000));
					}else{
						SystemUtil.sleep(30000);
					}
				}
			}
			logger.info("完成同城任务数据采集");
			MailUtil.sendSimpleMail(SystemConfig.MAILS, "完成同城任务采集"+count, sbMail.toString());
		} finally {
			if(login!=null){
				WeixinSpider.quitAndClose(login.getDriver());
			}
		}
	}
	
	/**
	 * 操作员开始处理
	 * @param spdId
	 * @param oper
	 * @return
	 * @exception DataException
	 */
	public CmcLtSpd startDeal(Long spdId, Operator oper){
		Long lockId = lockProcess.addLock4Unique(DictConstant.DICT_LOCKTYPE_LOCALTASK_SPDDEAL, String.valueOf(spdId));
		if(lockId==null){
			throw new DataException(ExceptionConstants.MSG_OPER_CANNOT);
		}
		
		try {
			CmcLtSpd spd = localTaskSpiderService.getSpd(spdId);
			if(!DictConstants.DICT_DEALPROGRESS_WAITING.equals(spd.getDealprogress())){
				throw new DataException(ExceptionConstants.MSG_OPER_CANNOT);
			}
			
			spd.setDealprogress(DictConstants.DICT_DEALPROGRESS_PROCESSING);
			spd.setOpercd(oper.getOperCd());
			spd.setOpername(oper.getOperName());
			spd.setOperstarttime(new Date());
			localTaskSpiderService.saveOrUpdate(spd);
			return spd;
		} finally {
			lockService.deleteLock(lockId);
		}
	}
	
	/**
	 * 恢复为待处理
	 * @param spd
	 * @return
	 */
	public void resetDeal(CmcLtSpd spd){
		spd.setDealprogress(DictConstants.DICT_DEALPROGRESS_WAITING);
		spd.setOpercd(null);
		spd.setOpername(null);
		spd.setOperstarttime(null);
		localTaskSpiderService.saveOrUpdate(spd);
	}
	
	/**
	 * 操作员完成处理
	 * @param spdId
	 * @param oper
	 * @return
	 * @exception DataException
	 */
	public void finishDeal(Long spdId, Operator oper){
		CmcLtSpd spd = localTaskSpiderService.getSpd(spdId);
		if(!DictConstants.DICT_DEALPROGRESS_PROCESSING.equals(spd.getDealprogress())
				|| !oper.getOperCd().equals(spd.getOpercd())){
			throw new DataException(ExceptionConstants.MSG_OPER_CANNOT);
		}
		spd.setDealprogress(DictConstants.DICT_DEALPROGRESS_DEALT);
		spd.setOperendtime(new Date());
		localTaskSpiderService.saveOrUpdate(spd);
	}
	
	/**
	 * 转就业信息
	 * @param spdId
	 * @param catId
	 * @param oper
	 */
	public void toArticle(Long spdId, String fullCatId, Operator oper){
		CmcLtSpd spd = localTaskSpiderService.getSpdJoin(spdId);
		if(!DictConstants.DICT_DEALPROGRESS_PROCESSING.equals(spd.getDealprogress())
				|| !oper.getOperCd().equals(spd.getOpercd())){
			throw new DataException(ExceptionConstants.MSG_OPER_CANNOT);
		}
		
		spd.setDealprogress(DictConstants.DICT_DEALPROGRESS_DEALT);
		spd.setOperendtime(new Date());
		
		CmcJobArticle article = new CmcJobArticle();
		article.setFullcatid(fullCatId);
		article.setSourceid(spd.getSourceid());
		article.setTitle(spd.getTitle());
		article.setCityid(spd.getCmcLtSource().getCityid());
		article.setCityname(spd.getCmcLtSource().getCityname());
		article.setLink(spd.getLink());
		article.setPublishtime(spd.getPublishtime());
		article.setSpdid(spd.getSpdid());
		
		if(StringUtil.notBlankAndNull(spd.getPic())){
			article.getPics().add(new CmcJobArticlePic(spd.getPic()));
		}
		
		localTaskSpiderService.saveOrUpdate(spd, article);
	}
	
	/**
	 * 转就业信息
	 */
	public void toArticleAuto(Map<String, String> params){
		List<CmcLtSpd> list = null;
		Date spiderTimeS = null;
		Date spiderTimeE = null;
		
		String dealProgress = DictConstants.DICT_DEALPROGRESS_WAITING;
		String spdDate = params.get("spdDate");
		if(StringUtil.notBlankAndNull(spdDate)){
			spiderTimeS = DateUtil.parseDate(spdDate);
			spiderTimeE = DateUtil.setMaxTime(spiderTimeS);
		}
		
		int pageSize = 100;
		Long lastSpdId = null;
		String fullCatId = null;
		Long catId = null;
		int count = 0;
		int toCount = 0;
		
		logger.info("开始转就业信息");
		do{
			list = localTaskSpiderService.findSpd(dealProgress,  spiderTimeS, spiderTimeE, pageSize, lastSpdId);
			if(list.isEmpty()) break;
			for (CmcLtSpd spd : list) {
				count++;
				lastSpdId = spd.getSpdid();
				catId = JobArticleUtil.getCatId(spd.getTitle());
				if(catId==null) continue;
				
				fullCatId = String.valueOf(catId);
				startDeal(spd.getSpdid(), OperLogUtil.OPERATOR_SYSTEM);
				toArticle(spd.getSpdid(), fullCatId, OperLogUtil.OPERATOR_SYSTEM);
				toCount++;
			}
			logger.info("转就业信息"+toCount+"/"+count);
		}while(true);
		logger.info("完成转就业信息"+toCount+"/"+count);
	}
	
	/**
	 * 分拣采集信息
	 * 1.利用阅读分析得到招聘信息，转入就业精选
	 * 2.无招聘信息，转入就业信息
	 * @param params spdDate,
	 */
	public void sorting(Map<String, String> params){
		String dealProgress = DictConstants.DICT_DEALPROGRESS_WAITING;

		List<CmcLtSpd> list = null;
		Date spiderTimeS = null;
		Date spiderTimeE = null;
		
		String spdDate = params.get("spdDate");
		String spdDateS = params.get("spdDateS");
		String spdDateE = params.get("spdDateE");
		if(StringUtil.notBlankAndNull(spdDate)){
			spiderTimeS = DateUtil.parseDate(spdDate);
			spiderTimeE = DateUtil.setMaxTime(spiderTimeS);
		}else{
			if(StringUtil.notBlankAndNull(spdDateS)){
				spiderTimeS = DateUtil.parseDate(spdDateS);
			}
			if(StringUtil.notBlankAndNull(spdDateE)){
				spiderTimeE = DateUtil.setMaxTime(DateUtil.parseDate(spdDateE));
			}
		}
		
		int pageSize = 8;
		Long lastSpdId = null;
		int count = 0;
		int toLocalTaskCount = 0;
		int toJobArticleCount = 0;
		int discardCount = 0;
		StringBuilder sbLog = null;
		LocalSpiderSortingThrd sortingThrd = null;
		Thread thrd = null;
		List<LocalSpiderSortingThrd> sortingThrds = new ArrayList<LocalSpiderSortingThrd>();
		List<Thread> thrds = new ArrayList<Thread>();
		Integer sortResult = null;
		
		loggerSorting.info("开始分拣采集信息："+spdDate+","+spdDateS+","+spdDateE);
		Long waitCount = localTaskSpiderService.getSpdCount(dealProgress, spiderTimeS, spiderTimeE, lastSpdId);
		if(waitCount==0) {
			loggerSorting.info("待分拣数为0");
			return;
		}
		BaiduAiProxy.resetAccountStatus();
		do{
			list = localTaskSpiderService.findSpd(dealProgress, spiderTimeS, spiderTimeE, pageSize, lastSpdId);
			if(list.isEmpty()) break;
			sortingThrds.clear();
			thrds.clear();
			
			if(!BaiduAiProxy.ACCOUNT_STATUS) {
				loggerSorting.error("百度账户异常，退出分拣");
				break;
			}
			
			for (CmcLtSpd spd : list) {
				count++;
				lastSpdId = spd.getSpdid();
				//丢弃,250610由于丢弃数少，为避免漏掉，全部解析分拣
//				if(JobArticleUtil.getCatId(spd.getTitle())==null) {
//					discardCount++;
//					continue;
//				}
				
				sortingThrd = new LocalSpiderSortingThrd(this, spd);
				thrd = new Thread(sortingThrd);
				thrd.start();
				sortingThrds.add(sortingThrd);
				thrds.add(thrd);
			}
			//多线程处理
			for (int i = 0; i < thrds.size(); i++) {
				try {
					thrds.get(i).join();
					sortResult = (Integer)sortingThrds.get(i).bean;
					if(sortResult!=null){
						if(sortResult==2){
							toLocalTaskCount++;
						}else if(sortResult==3){
							toJobArticleCount++;
						}else if(sortResult==1){
							discardCount++;
						}
					}
				} catch (Exception e) {
					logger.error("等待分拣线程完成失败：" + e.getMessage());
				}
			}
			
			waitCount = localTaskSpiderService.getSpdCount(dealProgress, spiderTimeS, spiderTimeE, lastSpdId);
			sbLog = new StringBuilder()
					.append("++++++++++分拣采集信息：待分拣总数").append(waitCount)
					.append("；已分拣").append(count)
					.append("，丢弃").append(discardCount)
					.append("，就业精选").append(toLocalTaskCount)
					.append("，就业信息").append(toJobArticleCount)
					;
			logger.info(sbLog);
		}while(true);
		sbLog = new StringBuilder()
				.append("完成分拣采集信息（").append(spdDate).append(",").append(spdDateS).append(",").append(spdDateE)
				.append("）：总数").append(count)
				.append("，丢弃").append(discardCount)
				.append("，就业精选").append(toLocalTaskCount)
				.append("，就业信息").append(toJobArticleCount)
				;
		loggerSorting.info(sbLog);
		if(toLocalTaskCount>0 || toJobArticleCount>0){
			MailUtil.sendSimpleMail(SystemConfig.MAILS, "完成同城采集结果分拣", sbLog.toString());
		}
	}
	
	/**
	 * 分拣
	 * @param spd
	 * @return 0异常 1丢弃 2转就业精选 3转就业信息
	 */
	public Integer sorting(CmcLtSpd spd){
		StringBuilder sbHint = new StringBuilder().append(spd.getSpdid()).append(",").append(spd.getTitle()).append(",").append(spd.getLink());
		try {
			spd = startDeal(spd.getSpdid(), OperLogUtil.OPERATOR_SYSTEM);
			
			boolean toLocalTask = toLocalTask(spd);
			if(toLocalTask){
				sbHint.insert(0, "转就业精选：");
				loggerSorting.info(sbHint);
				return 2;
			}else{
				Long catId = JobArticleUtil.getCatId(spd.getTitle());
				if(catId!=null) {
					toArticle(spd.getSpdid(), String.valueOf(catId), OperLogUtil.OPERATOR_SYSTEM);
					sbHint.insert(0, "转就业信息：");
					loggerSorting.info(sbHint);
					return 3;
				}else{
					discard(spd);
					sbHint.insert(0, "catId空丢弃：");
					loggerSorting.info(sbHint);
					return 1;
				}
			}
		} catch (Exception e) {
			resetDeal(spd);
			sbHint.insert(0, "分拣失败：");
			logger.error(sbHint, e);
			return 0;
		}
	}
	
	/**
	 * 转就业精选
	 * @param spd
	 * @return
	 */
	public boolean toLocalTask(CmcLtSpd spd){
		try {
			String content = BaiduAiProxy.analysisWeb(BaiduAiUtil.getPrompt(), spd.getLink());
			if(StringUtil.isBlankOrNull(content)) {
				loggerSorting.info(spd.getSpdid()+"提取结果为空，抛弃");
				return false;
			}
			
			//数据不完整处理
			int lastIndex = content.lastIndexOf("```");
			if(lastIndex<7){
				if(content.startsWith("```json\n[")){
					//多条时，尽量保留有效记录
					content = content.substring(0, content.lastIndexOf("},")) + "}]```";
				}else{
					loggerSorting.info(spd.getSpdid()+"提取结果数据不完整，抛弃");
					return false;
				}
//				System.out.println("数据不完整处理后："+content);
				lastIndex = content.lastIndexOf("```");
			}
			
			if(content.indexOf("示例", lastIndex)>-1 || content.indexOf("生成", lastIndex)>-1){
				loggerSorting.info(spd.getSpdid()+"提取结果为示例数据，抛弃");
				return false;
			}
			
			content = content.substring("```json".length(), lastIndex);
			
			List<LocalTaskBean> list = new ArrayList<LocalTaskBean>();
			if(content.startsWith("\n{")){
				list.add(JsonUtil.string2Object(content, LocalTaskBean.class));
			}else{
				list = JsonUtil.string2List(content, LocalTaskBean.class);
			}
			Iterator<LocalTaskBean> it = list.iterator();
			LocalTaskBean tb = null;
			while(it.hasNext()){
				tb = it.next();
				
				tb.setTitle(BaiduAiUtil.processData(tb.getTitle()));
				tb.setCompanyName(BaiduAiUtil.processData(tb.getCompanyName()));
				tb.setContact(BaiduAiUtil.formatContact(BaiduAiUtil.processData(tb.getContact())));
				
				if(StringUtil.isBlankOrNull(tb.getTitle()) || tb.getTitle().contains("未知") || tb.getTitle().startsWith("[")		//数组
						|| StringUtil.isBlankOrNull(tb.getCompanyName()) || tb.getCompanyName().contains("未知") //|| tb.getCompanyName().equals("个人")
						|| StringUtil.isBlankOrNull(tb.getContact()) || tb.getContact().contains("未知") || tb.getContact().contains("***") 
						|| tb.getContact().contains("扫码") || tb.getContact().contains("二维码")
						){
					it.remove();
				}
			}
			if(list.isEmpty()) {
				loggerSorting.info(spd.getSpdid()+"过滤后提取结果为空，抛弃");
				return false;
			}
			
			List<CmcLtTask> tasks = new ArrayList<CmcLtTask>();
			CmcLtTask task = null;
			CmcLtCompany company = null;
			CmcLtSource source = null;
			boolean repeat = false;
			Date now = new Date();
			for (LocalTaskBean bean : list) {
				company = localCompanyService.getSystemCompanyByName(bean.getCompanyName());
				if(company==null){
					company = localCompanyProcess.addCompany4Spider(bean.getCompanyName());
				}
				
				repeat = localTaskService.isRepeat(null, company.getCompanyid(), bean.getTitle());
				if(repeat) continue;
				for (CmcLtTask t : tasks) {
					if(t.getCompanyid().equals(company.getCompanyid()) && t.getTitle().equals(bean.getTitle())){
						repeat = true;
						break;
					}
				}
				if(repeat) continue;
				
				source = localSourceMap.getSource(spd.getSourceid());
				
				task = new CmcLtTask();
				task.setCompanyid(company.getCompanyid());
				task.setSourceid(spd.getSourceid());
				task.setTitle(bean.getTitle());
				task.setPeoplenum(bean.getPeopleNum());
				task.setWorktimetype(BaiduAiUtil.parseWorkTimeType(bean.getWorkTime(), task.getTitle()));
				
				task.setRewardtype(BaiduAiUtil.parseReawrdType(bean.getRewardType()));
				task.setReward(LocalTaskUtil.formatReward(BaiduAiUtil.formatReward(bean.getReward()), task.getRewardtype()));
				task.setMinreward(BaiduAiUtil.parseReward(bean.getMinReward()));
				task.setMaxreward(BaiduAiUtil.parseReward(bean.getMaxReward()));
				if(task.getMaxreward()==null && task.getMinreward()!=null){
					task.setMaxreward(task.getMinreward());
				}
				//7k得到的minReward可能为7，处理一下
				if(task.getMinreward()==null 
						|| (!DictConstant.DICT_LOCALTASK_REWARDTYPE_HOUR.equals(task.getRewardtype()) && task.getMinreward()<100)){
					List<Integer> rewards = LocalTaskUtil.parseReward(task.getReward());
					task.setMinreward(rewards.get(0));
					task.setMaxreward(rewards.get(1));
				}
				task.setRefreward(LocalTaskUtil.calRefReward(task.getMinreward(), task.getMaxreward(), task.getRewardtype()));
				task.setContact(bean.getContact());
				task.setCityid(source.getCityid());
				task.setCityname(source.getCityname());
				task.setAddress(BaiduAiUtil.processData(BaiduAiUtil.formatAddress(bean.getAddress())));
				task.setExp(BaiduAiUtil.processData(bean.getExp()));
				task.setMinexp(LocalTaskUtil.parseExp(bean.getMinExp()));
				task.setEdu(bean.getEdu());
				task.setMinedu(LocalTaskUtil.parseEdu(bean.getEdu()));
				task.setJob(bean.getJob());
				task.setJobrequire(bean.getJobRequire());
				task.setPublishtime(spd.getPublishtime());
				task.setStatus(DictConstant.DICT_LOCALTASK_STATUS_ON);
				task.setCreatetime(now);
				task.setCreateway(DictConstant.DICT_LOCALTASK_CREATEWAY_SPD);
				task.setSpdid(spd.getSpdid());
				task.setLink(spd.getLink());
				
				tasks.add(task);
			}
			
			spd.setDealprogress(DictConstants.DICT_DEALPROGRESS_DEALT);
			spd.setOperendtime(now);
			localTaskSpiderService.saveOrUpdate(spd, tasks);
			return true;
		} catch (Exception e) {
			loggerSorting.error(spd.getSpdid()+"转就业精选失败："+e.getMessage());
			logger.error("转就业精选失败："+spd.getSpdid(), e);
			return false;
		}
	}
	
	/**
	 * 丢弃
	 * @param spd
	 */
	public void discard(CmcLtSpd spd){
		spd.setDealprogress(DictConstants.DICT_DEALPROGRESS_DEALT);
		spd.setOperendtime(new Date());
		localTaskSpiderService.saveOrUpdate(spd);
	}
}
