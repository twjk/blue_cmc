package com.qcmz.cmc.process;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.cache.DictMap;
import com.qcmz.cmc.cache.RewardActivityMap;
import com.qcmz.cmc.config.SystemConfig;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.entity.CmcCtTask;
import com.qcmz.cmc.entity.CmcJobArticle;
import com.qcmz.cmc.entity.CmcLtTask;
import com.qcmz.cmc.entity.CmcRewardAccount;
import com.qcmz.cmc.entity.CmcRewardActivity;
import com.qcmz.cmc.entity.CmcRewardCash;
import com.qcmz.cmc.proxy.pay.alipay.AlipayProxy;
import com.qcmz.cmc.proxy.pay.wxpay.WxpayProxy;
import com.qcmz.cmc.service.ICrowdTaskService;
import com.qcmz.cmc.service.IJobArticleService;
import com.qcmz.cmc.service.ILocalTaskService;
import com.qcmz.cmc.service.ILockService;
import com.qcmz.cmc.service.IRewardAccountService;
import com.qcmz.cmc.service.IRewardActivityPartService;
import com.qcmz.cmc.service.IRewardBillService;
import com.qcmz.cmc.service.IRewardCashService;
import com.qcmz.cmc.service.IRewardService;
import com.qcmz.cmc.util.RewardUtil;
import com.qcmz.cmc.vo.RewardOfflineBean;
import com.qcmz.cmc.vo.TradeResultBean;
import com.qcmz.cmc.vo.TransferBean;
import com.qcmz.cmc.ws.provide.vo.RewardInviteBean;
import com.qcmz.cmc.ws.provide.vo.RewardRewardBean;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.constant.ExceptionConstants;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.exception.ParamException;
import com.qcmz.framework.exception.ProxyException;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.MailUtil;
import com.qcmz.framework.util.NumberUtil;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.framework.util.log.Operator;
import com.qcmz.umc.ws.provide.locator.UserMap;
import com.qcmz.umc.ws.provide.locator.UserMsgWS;
import com.qcmz.umc.ws.provide.vo.UserMsgAddBean;
import com.qcmz.umc.ws.provide.vo.UserSimpleBean;

/**
 * 奖励金处理
 */
@Component
public class RewardProcess {
	@Autowired
	private IRewardAccountService rewardAccountService;
	@Autowired
	private IRewardCashService rewardCashService;
	@Autowired
	private IRewardService rewardService;
	@Autowired
	private IRewardActivityPartService rewardActivityPartService;
	@Autowired
	private IRewardBillService rewardBillService;
	@Autowired
	private ILocalTaskService localTaskService;
	@Autowired
	private IJobArticleService jobArticleService;
	@Autowired
	private ICrowdTaskService crowdTaskService;
	
	@Autowired
	private RewardActivityMap rewardActivityMap;
	
	@Autowired
	private WxpayProxy wxpayProxy;
	@Autowired
	private AlipayProxy alipayProxy;
	
	@Autowired
	private LockProcess lockProcess;
	@Autowired
	private ILockService lockService;
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 奖励
	 * @param bean
	 * @exception ParamException、DataException
	 */
	public void reward(RewardRewardBean bean){
		//开关
		if(!SystemConfig.REWARD_REWARD_SWITCH){
			throw new DataException("系统繁忙，请稍后再试");
		}
		
		//金额
		if(bean.getRewardAmount()>SystemConfig.REWARD_REWARD_MAXAMOUNT){
			throw new ParamException("奖励金额有误");
		}
		
		//获取用户信息
		UserSimpleBean user = UserMap.getUser(bean.getUid());
		if(user==null){
			throw new ParamException(ExceptionConstants.MSG_USER_NOTEXIST);
		}
		
		Integer billType = DictConstant.DICT_REWARD_BILLTYPE_RECEIVE;
		
		//重复
		if(rewardBillService.isExist(bean.getUid(), billType, bean.getSubServiceType(), bean.getServiceId())){
			throw new DataException(ExceptionConstants.MSG_OPER_REPEAT);
		}
		
		//次数限制
		if(SystemConfig.REWARD_REWARD_DATETIMES!=null && SystemConfig.REWARD_REWARD_DATETIMES>0){
			Long count = rewardBillService.getCount(bean.getUid(), billType, bean.getSubServiceType(), DateUtil.getSysDateOnly());
			if(count>=SystemConfig.REWARD_REWARD_DATETIMES){
				throw new DataException("休息一下，明天再战");
			}
		}
		
		//奖励
		rewardService.grantReward(user.getServiceType(), bean.getUid(), bean.getRewardAmount(), bean.getDescription(), bean.getSubServiceType(), bean.getServiceId());
				
		//通知用户		
		notifyUser(bean.getUid(), bean.getRewardAmount());
	}
	
	/**
	 * 邀请奖励
	 * @param inviteBean
	 * @exception ParamException
	 */
	public void inviteReward(RewardInviteBean inviteBean){
		Long actId = null;
		CmcRewardActivity act;
		
		Long inviterId = inviteBean.getInviterId();
		Long inviteeId = inviteBean.getInviteeId();
		String subServiceType = inviteBean.getSubServiceType();
		if(StringUtil.isBlankOrNull(subServiceType)){
			subServiceType = DictConstants.DICT_SUBSERVICETYPE_NONE;
		}
		Long serviceId = null;
		if(NumberUtil.isNumber(inviteBean.getServiceId())){
			serviceId = Long.valueOf(inviteBean.getServiceId());
		}
		
		//自己邀请自己无奖励
		if(inviterId.equals(inviteeId)) return;
		
		//获取用户信息
		List<Long> userIds = new ArrayList<Long>();
		userIds.add(inviterId);
		userIds.add(inviteeId);
		Map<Long, UserSimpleBean> userMap = UserMap.findUser(userIds);
		UserSimpleBean inviter = userMap.get(inviterId);
		UserSimpleBean invitee = userMap.get(inviteeId);
		if(inviter==null || invitee==null){
			throw new ParamException(ExceptionConstants.MSG_USER_NOTEXIST);
		}
		String serviceType = inviter.getServiceType();
		
		//a.特殊奖励
		if(DictConstants.DICT_SUBSERVICETYPE_GOODJOB.equals(subServiceType)){
			CmcLtTask task = localTaskService.getTask(serviceId);
			if(task==null)	throw new ParamException(ExceptionConstants.MSG_DATA_NOTEXIST);
			actId = task.getActid();
		}else if(DictConstants.DICT_SUBSERVICETYPE_JOBARTICLE.equals(subServiceType)){
			CmcJobArticle article = jobArticleService.getArticle(serviceId);
			if(article==null)	throw new ParamException(ExceptionConstants.MSG_DATA_NOTEXIST);
			actId = article.getActid();
		}else if(DictConstants.DICT_SUBSERVICETYPE_CROWDTASK.equals(subServiceType)){
			CmcCtTask task = crowdTaskService.getTask(serviceId);
			if(task==null)	throw new ParamException(ExceptionConstants.MSG_DATA_NOTEXIST);
			actId = task.getActid();
		}else if(!DictConstants.DICT_SUBSERVICETYPE_NONE.equals(subServiceType)){
			return;
		}
		if(actId!=null){
			act = rewardActivityMap.getValidActivity(actId);
			//活动不存在或已停用，无奖励
			if(act==null) return;
			//重复无奖励
			if(rewardActivityPartService.getPart(actId, inviteBean)!=null) return;
			//发放特殊奖励，超出限制次数走通用奖励
			if(act.getParttimes()==-1
					|| rewardActivityPartService.getPartTimes(inviterId, act.getActid(), act.getPartfreq())<act.getParttimes()){
				rewardService.grantActivityReward(act, inviteBean, serviceType);
				return;
			}
		}
		
		//b.通用奖励
		act = rewardActivityMap.getCommonActivity();
		if(act!=null){
			//被邀请人为当天注册的新用户
			if(DateUtil.betweenDay(new Date(), new Date(invitee.getRegTime()))==0){
				//重复无奖励
				if(rewardActivityPartService.getPart(act.getActid(), inviteBean)!=null) return;
				//发放通用奖励，超出限制次数走安慰奖
				if(act.getParttimes()==-1
						|| rewardActivityPartService.getPartTimes(inviterId, act.getActid(), act.getPartfreq())<act.getParttimes()){
					rewardService.grantActivityReward(act, inviteBean, serviceType);
					return;
				}
			}
		}
		
		//c.安慰奖
		act = rewardActivityMap.getEncouragementActivity();
		if(act!=null){
			//重复无奖励
			if(rewardActivityPartService.getPart(act.getActid(), inviteBean)!=null) return;
			//发放安慰奖
			if(act.getParttimes()==-1
					|| rewardActivityPartService.getPartTimes(inviterId, act.getActid(), act.getPartfreq())<act.getParttimes()){
				rewardService.grantActivityReward(act, inviteBean, serviceType);
				return;
			}
		}
		
	}
	
	/**
	 * 赠送奖励金
	 * @param userId
	 * @param amount
	 * @param billDesc
	 */
	public void bestowReward(Long userId, Double amount, String billDesc){
		//获取用户信息
		UserSimpleBean user = UserMap.getUser(userId);
		
		//赠送
		rewardService.bestowReward(user.getServiceType(), userId, amount, billDesc);
		
		//通知用户		
		notifyUser(userId, amount);
	}
	
	/**
	 * 手工发放奖励金
	 * @param bean
	 * @param operator
	 */
	public void offlineReward(RewardOfflineBean bean, Operator operator){
		//获取用户信息
		UserSimpleBean user = null;
		if(bean.getUserId()!=null){
			user = UserMap.getUser(bean.getUserId());
		}else if(StringUtil.notBlankAndNull(bean.getServiceType()) 
				&& StringUtil.notBlankAndNull(bean.getMobile())){
			user = UserMap.getUser(bean.getServiceType(), bean.getMobile());
		}
		if(user==null){
			throw new DataException(ExceptionConstants.MSG_USER_NOTEXIST);
		}else if(!user.isValid()){
			throw new DataException(ExceptionConstants.MSG_USER_INVALID);
		}
		bean.setUserId(user.getUid());
		
		//赠送奖励金
		if(DictConstant.DICT_REWARD_BILLTYPE_BESTOW.equals(bean.getBillType())){
			//入库
			rewardService.bestowReward(user.getServiceType(), user.getUid(), bean.getAmount(),  bean.getDescription());
			//通知用户		
			notifyUser(user.getUid(), bean.getAmount());
		}
		
	}
	
	/**
	 * 奖励金到账提醒
	 * @param userId
	 * @param amount
	 */
	public void notifyUser(Long userId, Double amount){
		List<Long> toUserIds = new ArrayList<Long>();
		toUserIds.add(userId);
		String msg = new StringBuilder().append(amount).append("奖励金(元)").toString();
		
		UserMsgAddBean bean = new UserMsgAddBean();
		bean.setMsgType(70L);
		bean.setToUserIds(toUserIds);
		bean.setMsg(msg);
		
		UserMsgWS.addMsg(bean);
	}
	
	
	/**
	 * 申请提现
	 * @param userId
	 * @param cashAmount
	 * @exception ParamException,DataException
	 */
	public void applyCash(Long userId, Double cashAmount, String cashAccountType, String cashAccount){
		//1.上锁
		Long lockId = lockProcess.addLock4Unique(DictConstant.DICT_LOCKTYPE_REWARD_APPLYCASH, String.valueOf(userId));
		if(lockId==null){
			throw new DataException(ExceptionConstants.MSG_OPER_OVERCLOCKING);
		}
		
		//2.申请提现
		try {
			//校验
			if(cashAmount<0.3){
				throw new DataException("单次提现不能少于0.3元");
			}
			CmcRewardAccount account = rewardAccountService.getAccount(userId);
			if(account==null || cashAmount>account.getBalance()){
				throw new DataException("余额不足");
			}else if(SystemConstants.STATUS_OFF.equals(account.getStatus())){
				throw new DataException("帐户异常");
			}
			
			//提现申请
			Long cashId = rewardCashService.applyCash(userId, cashAmount, cashAccountType, cashAccount);
			
			//打款并通知客服
			autoTransfer(cashId);
		} finally {
			lockService.deleteLock(lockId);	//解锁
		}
	}
	
	/**
	 * 批量自动打款
	 * @param params
	 */
	public void autoTransfer(Map<String, String> params){
		String srtMaxId = params.get("maxId");
		if(StringUtil.isBlankOrNull(srtMaxId)
				|| !NumberUtil.isNumber(srtMaxId)){
			throw new ParamException("最大提现编号有误");
		}
		
		Long maxId = Long.valueOf(srtMaxId);
		
		Long lastId = null; 
		String strLastId = params.get("lastId");
		if(NumberUtil.isNumber(strLastId)){
			lastId = Long.valueOf(strLastId);
		}
		
		logger.info("开始自动打款：["+strLastId+","+srtMaxId+"]");		
		int pageSize = 100;
		int seq = 0;		
		List<Long> cashIds = null;		
		do{
			cashIds = rewardCashService.findCashId4Transfer(pageSize, maxId, lastId);
			if(cashIds.isEmpty()) break;
			
			for (Long cashId : cashIds) {
				lastId = cashId;
				autoTransfer(cashId);
				seq++;
			}
			logger.info("打款已处理："+seq+"，最后处理提现编号："+lastId);
		}while(true);
	}
	
	/**
	 * 系统自动打款
	 * 自动打款条件：1.当次提现金额不超过20元 2.总获得奖励金额不超过20元
	 * @param cashId
	 */
	public void autoTransfer(Long cashId){
		boolean transfer = false;
		boolean singleOut,totalOut;
		String outDesc = "";
		try {
			CmcRewardCash cash = rewardCashService.getCash(cashId);
			CmcRewardAccount account = rewardAccountService.getAccount(cash.getUserid());
			
			singleOut = cash.getCashamount()>20;
			totalOut = account.getRewardtotal()>20;
			if(!singleOut && !totalOut){
				//打款
				Operator operator = new Operator();
				operator.setOperCd(SystemConstants.OPERATOR_DEFAULT);
				operator.setOperName(SystemConstants.OPERATOR_DEFAULT);
				cash = transfer(cashId, operator);
				if(DictConstants.DICT_CASHSTATUS_SUCCESS.equals(cash.getStatus())){
					transfer = true;
				}else{
					outDesc = cash.getTraderesult();
				}
			}else{
				if(singleOut){
					outDesc = "单次取现金额超限";
				}else if(totalOut){
					outDesc = "总取现金额超限";
				}
				cash.setTraderesult(outDesc);
				rewardCashService.update(cash);
			}
			
			//失败通知管理员
			if(!transfer){
				UserSimpleBean user = UserMap.getUser(cash.getUserid());
				StringBuilder sbSubject = new StringBuilder(outDesc).append("待处理");
				sbSubject.append("【").append(cash.getCashamount()).append("】元，").append("【").append(user.getNick()).append("】申请奖励金提现");
				String msg = new StringBuilder(sbSubject).append("。用户编号：").append(cash.getUserid()).toString();
				MailUtil.sendSimpleMail(SystemConfig.CS_MAILS, null, sbSubject.toString(), msg);
			}
		} catch (Exception e) {
			logger.error("打款失败："+cashId, e);
		}
	}
	
	/**
	 * 转账付款
	 * @param cashId
	 * @param oper
	 * @exception ProxyException
	 */
	public CmcRewardCash transfer(Long cashId, Operator oper){
		//转账
		CmcRewardCash cash = rewardCashService.getCashJoinAccount(cashId);
		if(DictConstants.DICT_CASHSTATUS_SUCCESS.equals(cash.getStatus())){
			return cash;
		}

		TradeResultBean tradeResult = null;
		String serviceTypeMean = DictMap.getServiceTypeMean(cash.getCmcRewardAccount().getServicetype());
		TransferBean transferBean = new TransferBean();
		transferBean.setOrderId(RewardUtil.getTransferOrderId(cash.getUserid(), cash.getCashid()));
		transferBean.setOrderDesc(RewardUtil.getTransferOrderDesc(serviceTypeMean, transferBean.getOrderId()));
		transferBean.setAmount(cash.getCashamount());
		transferBean.setOpenid(cash.getAccount());
		transferBean.setServiceType(cash.getCmcRewardAccount().getServicetype());
		if(DictConstants.DICT_USER_PAYACCOUNT_WXPAY.equals(cash.getAccounttype())){
			tradeResult = wxpayProxy.transfer(transferBean);
		}else if(DictConstants.DICT_USER_PAYACCOUNT_ALIPAY.equals(cash.getAccounttype())){
			tradeResult = alipayProxy.transfer(transferBean);
		}
		
		cash.setDealcd(oper.getOperCd());
		cash.setDealname(oper.getOperName());
		cash.setDealtime(new Date());
		if(tradeResult==null){
			//失败
			cash.setTraderesult("提现方式有误");
		}else{
			if(DictConstant.DICT_TRADESTATUS_SUCCESS.equals(tradeResult.getTradeStatus())){
				//成功
				cash.setStatus(DictConstants.DICT_CASHSTATUS_SUCCESS);
				cash.setOuttradeid(tradeResult.getOutTradeId());
				cash.setTraderesult(null);
				cash.setCashtime(tradeResult.getTradeTime());
			}else{
				//失败
				cash.setTraderesult(tradeResult.getTradeResult());
			}
		}
		rewardCashService.update(cash);
		
		return cash;
	}
}
