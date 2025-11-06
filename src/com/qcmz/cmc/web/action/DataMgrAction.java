package com.qcmz.cmc.web.action;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.process.DaySentenceProcess;
import com.qcmz.cmc.process.JobArticleProcess;
import com.qcmz.cmc.process.LocalTaskProcess;
import com.qcmz.cmc.process.LocalTaskSpiderProcess;
import com.qcmz.cmc.process.OrderCommissionProcess;
import com.qcmz.cmc.process.RewardProcess;
import com.qcmz.cmc.process.SpeechDataProcess;
import com.qcmz.cmc.process.UserCrowdTaskExtractProcess;
import com.qcmz.framework.action.BaseAction;
import com.qcmz.framework.exception.ParamException;
import com.qcmz.framework.exception.ProxyException;
import com.qcmz.framework.util.DateUtil;

/**
 * 类说明：数据维护
 * 修改历史：
 */
public class DataMgrAction extends BaseAction {
	@Autowired
	private DaySentenceProcess daySentenceProcess;
	@Autowired
	private OrderCommissionProcess orderCommissionProcess;
	@Autowired
	private UserCrowdTaskExtractProcess userCrowdTaskExtractProcess;
	@Autowired
	private SpeechDataProcess speechDataProcess;
	@Autowired
	private RewardProcess rewardProcess;
	@Autowired
	private LocalTaskSpiderProcess localTaskSpiderProcess;
	@Autowired
	private LocalTaskProcess localTaskProcess;
	@Autowired
	private JobArticleProcess jobArticleProcess;
	
	/**
	 * 测试
	 */
	public String doTest(){
		
		return null;
	}
	
	/**
	 * 采集同城任务
	 */
	public String doSpiderLocalTask(){
		try {
			localTaskSpiderProcess.spiderLocalTask(getParameterMap());
		} catch (ProxyException e) {
			handleResult = false;
			setResult(e.getMessage());
		}catch (Exception e) {
			logger.error("采集同城任务失败",e);
			handleResult = false;
			setResult("采集同城任务失败");
		}
		
		print();
		
		return null;
	}
	
	/**
	 * 同城信息采集结果分拣
	 */
	public String doSpiderSorting(){
		try {
			localTaskSpiderProcess.sorting(getParameterMap());
		} catch (Exception e) {
			logger.error("分拣失败",e);
			handleResult = false;
			setResult("分拣失败");
		}
		
		print();
		
		return null;
	}
	
	/**
	 * 同城信息采集结果转就业信息
	 */
	public String doToJobArticle(){
		try {
			localTaskSpiderProcess.toArticleAuto(getParameterMap());
		} catch (Exception e) {
			logger.error("转就业信息失败",e);
			handleResult = false;
			setResult("转就业信息失败");
		}
		
		print();
		
		return null;
	}
	
	/**
	 * 下载就业信息图片
	 */
	public String doDownloadJobArticlePic(){
		try {
			jobArticleProcess.downloadPic();
		} catch (Exception e) {
			logger.error("下载就业信息图片失败",e);
			handleResult = false;
			setResult("下载就业信息图片失败");
		}
		
		print();
		
		return null;
	}
	
	/**
	 * 完善就业精选数据
	 */
	public String doRefineLocalTask(){
		try {
			localTaskProcess.refineData(getParameterMap());
		} catch (Exception e) {
			logger.error("完善就业精选数据失败",e);
			handleResult = false;
			setResult("完善就业精选数据失败");
		}
		
		print();
		
		return null;
	}
	
	/**
	 * 重新生成每日一句静态页 
	 */
	public String doHtmlDaySentence(){
		try {
			daySentenceProcess.createHtml(getParameterMap());
		} catch (Exception e) {
			logger.error("重新生成每日一句静态页失败",e);
			handleResult = false;
			setResult("重新生成每日一句静态页失败");
		}
		
		print();
		
		return null;
	}
	
	/**
	 * 订单佣金结算
	 * @return
	 */
	public String doOrderCommission(){
		try {
			Date start = DateUtil.parseDate(request.getParameter("start"));
			Date end = DateUtil.parseDateTime(request.getParameter("end")+" 23:59:59");
			String operCd = request.getParameter("operCd");
			
			orderCommissionProcess.commission(start, end, operCd);
		} catch (Exception e) {
			logger.error("佣金结算失败",e);
			handleResult = false;
			setResult("佣金结算失败");
		}
		
		print();
		
		return null;
	}
	
	/**
	 * 订单佣金重新结算
	 * @return
	 */
	public String doOrderReCommission(){
		try {
			Date start = DateUtil.parseDate(request.getParameter("start"));
			Date end = DateUtil.parseDateTime(request.getParameter("end")+" 23:59:59");
			String operCd = request.getParameter("operCd");
			
			orderCommissionProcess.reCommission(start, end, operCd);
		} catch (Exception e) {
			logger.error("佣金重新结算失败",e);
			handleResult = false;
			setResult("佣金重新结算失败");
		}
		
		print();
		
		return null;
	}
	
	/**
	 * 提取众包任务数据
	 * @return
	 */
	public String doExtractCrowdTask(){
		try {
			userCrowdTaskExtractProcess.extract(getParameterMap());
		} catch(ParamException e){
			handleResult = false;
			setResult(e.getMessage());
		}catch (Exception e) {
			logger.error("提取众包任务数据失败",e);
			handleResult = false;
			setResult("提取众包任务数据失败");
		}
		
		print();
		
		return null;
	}
	
	/**
	 * 奖励金提现向用户打款
	 * @return
	 */
	public String doRewardCashTransfer(){
		try {
			rewardProcess.autoTransfer(getParameterMap());
		} catch(ParamException e){
			handleResult = false;
			setResult(e.getMessage());
		}catch (Exception e) {
			logger.error("奖励金提现向用户打款失败",e);
			handleResult = false;
			setResult("奖励金提现向用户打款失败");
		}
		
		print();
		
		return null;
	}
	
	/**
	 * 语音识别
	 * @return
	 */
	public String doAsr(){
		try {
			speechDataProcess.asr(getParameterMap());
		} catch(ParamException e){
			handleResult = false;
			setResult(e.getMessage());
		}catch (Exception e) {
			logger.error("语音识别失败",e);
			handleResult = false;
			setResult("语音识别失败");
		}
		
		print();
		
		return null;
	}
	
	
}
