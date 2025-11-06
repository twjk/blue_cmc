package com.qcmz.cmc.process;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.cache.DictMap;
import com.qcmz.cmc.cache.LangMap;
import com.qcmz.cmc.cache.TransComboMap;
import com.qcmz.cmc.config.SystemConfig;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.dao.IOrderDao;
import com.qcmz.cmc.dao.IOrderItemDao;
import com.qcmz.cmc.dao.IRedPacketCashDao;
import com.qcmz.cmc.dao.IWalletRechargeDao;
import com.qcmz.cmc.entity.CmcCombo;
import com.qcmz.cmc.entity.CmcRItem;
import com.qcmz.cmc.entity.CmcROrder;
import com.qcmz.cmc.service.ICrowdTaskLibService;
import com.qcmz.cmc.service.IOrderService;
import com.qcmz.cmc.service.IRewardCashService;
import com.qcmz.cmc.service.IUserCrowdTaskQcService;
import com.qcmz.cmc.service.IUserCrowdTaskService;
import com.qcmz.cmc.thrd.OrderAppointSoonNotifyCsThrd;
import com.qcmz.cmc.thrd.OrderMonitorNotifyCsThrd;
import com.qcmz.cmc.util.CmcUtil;
import com.qcmz.cmc.util.CrowdTaskUtil;
import com.qcmz.cmc.vo.CrowdTaskLibLackBean;
import com.qcmz.cmc.vo.OrderCountBean;
import com.qcmz.cmc.vo.OrderDayCountBean;
import com.qcmz.cmc.vo.WalletRechargeCountBean;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.MailUtil;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.umc.ws.provide.locator.UserMsgWS;
import com.qcmz.umc.ws.provide.vo.UserMsgAddBean;

@Component
public class MonitorProcess {
	@Autowired
	private IOrderItemDao orderItemDao;
	@Autowired
	private IOrderDao orderDao;
	@Autowired
	private IRedPacketCashDao redPacketCashDao;
	@Autowired
	private IWalletRechargeDao walletRechargeDao;
	
	@Autowired
	private IOrderService orderService;
	@Autowired
	private IUserCrowdTaskService userCrowdTaskService;
	@Autowired
	private IUserCrowdTaskQcService userCrowdTaskQcService;
	@Autowired
	private IRewardCashService rewardCashService;
	@Autowired
	private ICrowdTaskLibService crowdTaskLibService;
	
	@Autowired
	private LangMap langMap;
	@Autowired
	private TransComboMap transComboMap;
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 每日监控
	 */
	public void monitorPerday(){
		//监控订单统计
		mointorOrder();
		
		//监控奖励金发放
		monitorRewardGrant();
		
		//监控奖励金提现申请
		monitorRewardCash();
		
		//监控题库可用题目数
//		monitorCrowdTaskLib();
		
//		moinitorSubList();
//		monitorRedPacketCash();
	}

	/**
	 * 预约订单提醒
	 */
	public void notifyAppoint(){
		try {
			boolean msgUser = false;
			//视频翻译
			List<CmcROrder> list = orderService.findSoonDeal(DictConstant.DICT_ORDERCAT_TRANS, DictConstant.DICT_ORDERTYPE_TRANSVIDEO);
			for (CmcROrder order : list) {
				logger.info("预约订单提醒："+order.getOrderid());
				
				//通知用户				
				String content = null;
				if(order.getCmcUCombo()!=null){
					CmcCombo combo = transComboMap.getCombo(order.getCmcUCombo().getComboid());
					content = DictMap.getTransComboTypeMean(combo.getCombotype());
				}
				if(StringUtil.isBlankOrNull(content)){
					content = "口译";
				}
				
				List<Long> toUserIds = new ArrayList<Long>();
				toUserIds.add(order.getUserid());
				
				UserMsgAddBean bean = new UserMsgAddBean();
				bean.setMsgType(62L);
				bean.setToUserIds(toUserIds);
				bean.setMsg(content);
				bean.setIdentify(order.getOrderid());
				bean.setExtra(null);				
				msgUser = UserMsgWS.addMsg(bean);
				
				if(msgUser){
					//更新状态
					orderService.updateSoonDealStatus(order.getOrderid(), SystemConstants.STATUS_OPER_ALREADY);
					
					//通知译员
					OrderAppointSoonNotifyCsThrd.notify(order);
				}
			}
		} catch (Exception e) {
			logger.error("预约订单提醒失败", e);
		}
	}
	
	/**
	 * 监控待处理订单
	 * 修改历史：
	 */
	public void monitorWaitDeal(){
		try {
			String msg = null;
			String lang = null;
			Integer orderType = null;
			String key = null;
			String[] arrKey = null;
			List<Integer> orderTypes = new ArrayList<Integer>();
			List<String> dealProgressList = new ArrayList<String>();

			//翻译订单
			List<CmcROrder> list = orderService.findWaitDeal(DictConstant.DICT_ORDERCAT_TRANS, DictConstant.DICT_ORDERTYPES_TRANS);
			if(!list.isEmpty()){
				//<orderType|lang, List<CmcROrder>>
				Map<String, List<CmcROrder>> map = new HashMap<String, List<CmcROrder>>();
				for (CmcROrder order : list) {
					lang = CmcUtil.getLang(order.getFromlang(), order.getTolang());
					key = new StringBuilder().append(order.getOrdertype()).append("|").append(lang).toString();
					if(map.get(key)==null) map.put(key, new ArrayList<CmcROrder>());
					map.get(key).add(order);
				}
				
				for (String key1 : map.keySet()) {
					list = map.get(key1);
					arrKey = StringUtil.splitPreserveAllTokens(key1,"|");
					orderType = Integer.valueOf(arrKey[0]);
					lang = arrKey[1];
					msg = new StringBuilder()
							.append(list.size()).append("个")
							.append(langMap.getLangName4Text(lang))
							.append(DictMap.getOrderTypeMean(orderType))
							.append("订单待处理")
							.toString()
							;
					MailUtil.sendHtmlMail(SystemConfig.CS_MAILS, null, msg, gentHtml(list));
					OrderMonitorNotifyCsThrd.monitorNotify(orderType, msg, lang);
				}
			}
			
			//真人配音订单
			orderTypes.clear();
			orderTypes.add(DictConstant.DICT_ORDERTYPE_HUMANDUB);
			dealProgressList.clear();
			dealProgressList.add(DictConstants.DICT_DEALPROGRESS_WAITING);
			Long count = orderService.getProcessCount(DictConstant.DICT_ORDERCAT_DUB, orderTypes, dealProgressList);
			if(count>0){
				msg = new StringBuilder()
						.append(count).append("个")
						.append(DictMap.getOrderTypeMean(DictConstant.DICT_ORDERTYPE_HUMANDUB))
						.append("订单待处理")
						.toString()
						;
				MailUtil.sendHtmlMail(SystemConfig.CS_MAILS, msg, msg);
			}
			
		} catch (Exception e) {
			logger.error("监控翻译未完成处理订单失败", e);
		}
	}
	
	/**
	 * 监控未完成处理订单
	 * 修改历史：
	 */
	public void monitorUndealt(){
		try {
			List<CmcROrder> list = null;
			String msg = null;
			String lang = null;
			Integer orderType = null;
			String key = null;
			String[] arrKey = null;
			//<orderType|lang, List<CmcROrder>>
			Map<String, List<CmcROrder>> map = new HashMap<String, List<CmcROrder>>();
			
			//翻译类
			list = orderService.findUndealt(DictConstant.DICT_ORDERCAT_TRANS, DictConstant.DICT_ORDERTYPES_TRANS);
			if(!list.isEmpty()){
				for (CmcROrder order : list) {
					lang = CmcUtil.getLang(order.getFromlang(), order.getTolang());
					key = new StringBuilder().append(order.getOrdertype()).append("|").append(lang).toString();
					if(map.get(key)==null) map.put(key, new ArrayList<CmcROrder>());
					map.get(key).add(order);
				}
				
				for (String key1 : map.keySet()) {
					list = map.get(key1);
					arrKey = StringUtil.splitPreserveAllTokens(key1,"|");
					orderType = Integer.valueOf(arrKey[0]);
					lang = arrKey[1];
					msg = new StringBuilder()
							.append(list.size()).append("个")
							.append(langMap.getLangName4Text(lang))
							.append(DictMap.getOrderTypeMean(orderType))
							.append("订单未完成处理")
							.toString()
							;
					MailUtil.sendHtmlMail(SystemConfig.CS_MAILS, null, msg, gentHtml(list));
					OrderMonitorNotifyCsThrd.monitorNotify(orderType, msg, lang);
				}			
			}
			
			//订阅类
			list = orderService.findUndealt(DictConstant.DICT_ORDERCAT_SUB, DictConstant.DICT_ORDERTYPES_SUB);
			if(!list.isEmpty()){
				msg = new StringBuilder().append(list.size()).append("个订阅订单未完成处理").toString();
				MailUtil.sendHtmlMail(SystemConfig.MAILS, null, msg, gentHtml(list));
			}
			
			//套餐类
			list = orderService.findUndealt(DictConstant.DICT_ORDERCAT_PACKAGE, DictConstant.DICT_ORDERTYPES_PACKAGE);
			if(!list.isEmpty()){
				msg = new StringBuilder().append(list.size()).append("个套餐订单未完成处理").toString();
				MailUtil.sendHtmlMail(SystemConfig.MAILS, null, msg, gentHtml(list));
			}
			
			//配音
			list = orderService.findUndealt(DictConstant.DICT_ORDERCAT_DUB, DictConstant.DICT_ORDERTYPES_DUB);
			if(!list.isEmpty()){
				msg = new StringBuilder().append(list.size()).append("个配音订单未完成处理").toString();
				MailUtil.sendHtmlMail(SystemConfig.MAILS, null, msg, gentHtml(list));
			}
		} catch (Exception e) {
			logger.error("监控翻译未完成处理订单失败", e);
		}
	}
	
	/**
	 * 生成html内容
	 * @param orders
	 * @return
	 */
	private String gentHtml(List<CmcROrder> orders){
		if(orders==null || orders.isEmpty()) return "";
		
		StringBuilder sbHtml = new StringBuilder();
		sbHtml.append("<html>")
			  .append("<head><style>")
			  .append("table{width:370px;font-size: 12px; border-left:solid 1px #000000; border-right:solid 1px #000000; border-bottom:solid 1px #000000; border-collapse:collapse;}")
			  .append("th,td{border:solid 1px #000000; line-height:24px; background-color:#ffffff; height:24px;border-collapse:collapse;}")
			  .append("</style></head>")
			  .append("<body>")			  
			  ;
		
		sbHtml.append("<table>")
			  .append("<tr>")
			  .append("<th width='100px'>类型</th>")
			  .append("<th width='80px'>状态</th>")
			  .append("<th width='190px'>订单号</th>")
			  .append("</tr>")
			  ;
		
		for (CmcROrder bean : orders) {
			sbHtml.append("<tr>")
				  .append("<td>").append(DictMap.getOrderTypeMean(bean.getOrdertype())).append("</td>")
				  .append("<td>").append(DictMap.getOrderDealStatusMean(bean.getDealstatus())).append("</td>")
				  .append("<td>").append(bean.getOrderid()).append("</td>")
				  .append("</tr>")
				  ;
		}
		
		sbHtml.append("</table>");
		
		sbHtml.append("</body></html>");
		
		return sbHtml.toString();
	}
	
	/**
	 * 订单监控
	 */
	public void mointorOrder(){
		try {
			Date day = DateUtil.addDay(new Date(), -1);
			Date start = DateUtil.setMinTime(day);
			Date end = DateUtil.setMaxTime(day);
			String dayStr = DateUtil.formatDate5(day);
			
			StringBuilder sbHtml = new StringBuilder();
			sbHtml.append("<html>")
				  .append("<head><style>")
				  .append("table{width:350px;font-size: 12px; border:solid 1px #cccccc; border-collapse:collapse;}")
				  .append("th,td{border:solid 1px #000000; line-height:24px; background-color:#ffffff; height:24px;border-collapse:collapse;}")
				  .append("</style></head>")
				  .append("<body>")			  
				  ;
			
			StringBuilder sbTableTitle = new StringBuilder();
			sbTableTitle.append("<table>")
				  .append("<tr>")
				  .append("<th width='80px'>类型</th>")
				  .append("<th width='50px'>状态</th>")
				  .append("<th width='50px'>订单数</th>")
				  .append("<th width='60px'>总金额</th>")
				  .append("<th width='50px'>优惠券</th>")
				  .append("<th width='60px'>钱包支付</th>")
				  .append("<th width='60px'>收入</th>")
				  .append("</tr>")
				  ;
			
			sbHtml.append("<p>").append(dayStr).append("已完成订单统计（金额取整）：</p>").append(sbTableTitle);
			StringBuilder sbDetail = new StringBuilder(sbTableTitle);
			StringBuilder sbTr = new StringBuilder();
			OrderCountBean finishCountBean = new OrderCountBean();
			
			List<OrderCountBean> list = orderDao.findOrderCount(start, end);
			List<WalletRechargeCountBean> rechargeList = walletRechargeDao.findRechargeCount(start, end);
			if(!list.isEmpty() || !rechargeList.isEmpty()){
				List<String> finishStatusList = new ArrayList<String>();
				finishStatusList.add(DictConstant.DICT_ORDER_DEALSTATUS_FINISH);
				finishStatusList.add(DictConstant.DICT_ORDER_DEALSTATUS_SUB);
				for (OrderCountBean bean : list) {
					sbTr = new StringBuilder();
					sbTr.append("<tr>")
						.append("<td>").append(DictMap.getOrderTypeMean(bean.getOrderType())).append("</td>")
						.append("<td>").append(DictMap.getOrderDealStatusMean(bean.getDealStatus())).append("</td>")
						.append("<td>").append(bean.getCount()).append("</td>")
						.append("<td>").append(bean.getAmount().intValue()).append("</td>")
						.append("<td>").append(bean.getCouponAmount().intValue()).append("</td>")
						.append("<td>").append(bean.getWalletAmount().intValue()).append("</td>")
						.append("<td>").append(bean.getPayAmount().intValue()).append("</td>")
						.append("</tr>")
						;
					if(finishStatusList.contains(bean.getDealStatus())){
						sbHtml.append(sbTr);
						finishCountBean.increase(bean);
					}
					sbDetail.append(sbTr);
				}
				
				for (WalletRechargeCountBean rechargeBean : rechargeList) {
					sbTr = new StringBuilder();
					sbTr.append("<tr>")
						.append("<td>钱包充值</td>")
						.append("<td>").append(DictMap.getWalletRechargeStatusMean(rechargeBean.getStatus())).append("</td>")
						.append("<td>").append(rechargeBean.getCount()).append("</td>")
						.append("<td>").append(rechargeBean.getAmount().intValue()).append("</td>")
						.append("<td>0</td>")
						.append("<td>0</td>")
						.append("<td>").append(rechargeBean.getPayAmount().intValue()).append("</td>")
						.append("</tr>")
						;
					if(DictConstant.DICT_WALLET_RECHARGESTATUS_PAID.equals(rechargeBean.getStatus())){
						sbHtml.append(sbTr);
						finishCountBean.increase(rechargeBean);
					}
					sbDetail.append(sbTr);
				}
				
				
				//已完成合计
				sbHtml.append("<tr>")
					  .append("<td>合计</td>")
					  .append("<td></td>")
					  .append("<td>").append(finishCountBean.getCount()).append("</td>")
					  .append("<td>").append(finishCountBean.getAmount().intValue()).append("</td>")
					  .append("<td>").append(finishCountBean.getCouponAmount().intValue()).append("</td>")
					  .append("<td>").append(finishCountBean.getWalletAmount().intValue()).append("</td>")
					  .append("<td>").append(finishCountBean.getPayAmount().intValue()).append("</td>")
					  .append("</tr>")
					  ;				
			}else{
				sbHtml.append("<tr><td colspan='5'>无数据</td></tr>");
				sbDetail.append("<tr><td colspan='5'>无数据</td></tr>");
			}
			sbHtml.append("</table>");
			sbDetail.append("</table>");
			
			sbHtml.append("<br/><p>").append(dayStr).append("订单各状态统计：</p>");
			sbHtml.append(sbDetail);
			
			sbHtml.append("</body></html>");
			
			MailUtil.sendHtmlMail(SystemConfig.MANAGER_MAILS, null, "订单统计"+dayStr, sbHtml.toString());
		} catch (Exception e) {
			logger.error("订单统计失败", e);
		}
	}
	
	/**
	 * 监控订阅明细
	 */
	public void moinitorSubList(){
		try {
			Integer orderCat = DictConstant.DICT_ORDERCAT_SUB;
			String dealStatus = DictConstant.DICT_ORDER_DEALSTATUS_SUB;
			Date today = new Date();
			Date yesterday = DateUtil.addDay(today, -1);
			Date start = null;
			Date end = null;
			String yesterdayStr = DateUtil.formatDate5(yesterday);
			
			StringBuilder sbHtml = new StringBuilder();
			sbHtml.append("<html>")
				  .append("<head><style>")
				  .append("table{width:380px;font-size: 12px; border-left:solid 1px #000000; border-right:solid 1px #000000; border-bottom:solid 1px #000000; border-collapse:collapse;}")
				  .append("th,td{border:solid 1px #000000; line-height:24px; background-color:#ffffff; height:24px;border-collapse:collapse;}")
				  .append("</style></head>")
				  .append("<body>")			  
				  ;
			
			sbHtml.append("<p>每日订阅统计：</p>");
			sbHtml.append("<table>")
				  .append("<tr>")
				  .append("<th width='50px'>序号</th>")
				  .append("<th width='130px'>日期</th>")
				  .append("<th width='100px'>下单数</th>")
				  .append("<th width='100px'>订阅数</th>")
				  .append("</tr>")
				  ;
			
			start = DateUtil.setMinTime(DateUtil.addDay(today, -7));
			end = DateUtil.setMaxTime(today);
			//下单数
			Map<String, Long[]> countMap = new LinkedHashMap<String, Long[]>();
			Long[] arr = null;
			List<OrderDayCountBean> createCountList = orderDao.findOrderDayCount(orderCat, null, start, end);
			for (OrderDayCountBean bean : createCountList) {
				arr = countMap.get(bean.getDate());
				if(arr==null){arr = new Long[]{0L, 0L};}
				arr[0] = bean.getCount();
				countMap.put(bean.getDate(), arr);
			}
			//订阅数
			List<OrderDayCountBean> subCountList = orderDao.findOrderDayCount(orderCat, dealStatus, start, end);
			for (OrderDayCountBean bean : subCountList) {
				arr = countMap.get(bean.getDate());
				if(arr==null){arr = new Long[]{0L, 0L};}
				arr[1] = bean.getCount();
				countMap.put(bean.getDate(), arr);
			}
			if(!countMap.isEmpty()){
				int i = 0;
				for (String key: countMap.keySet()) {
					arr = countMap.get(key);
					sbHtml.append("<tr>")
						  .append("<td>").append(++i).append("</td>")
						  .append("<td>").append(key).append("</td>")
						  .append("<td>").append(arr[0]).append("</td>")
						  .append("<td>").append(arr[1]).append("</td>")
						  .append("</tr>")
						  ;
				}
			}else{
				sbHtml.append("<tr><td colspan='3'>无数据</td></tr>");
			}
			sbHtml.append("</table>");
			
			start = DateUtil.setMinTime(yesterday);
			end = DateUtil.setMaxTime(yesterday);
			sbHtml.append("<br/><p>").append(yesterdayStr).append("订阅明细：</p>");
			sbHtml.append("<table>")
				  .append("<tr>")
				  .append("<th width='50px'>序号</th>")
				  .append("<th width='100px'>名称</th>")
				  .append("<th width='100px'>金额</th>")
				  .append("<th width='130px'>平台</th>")
				  .append("</tr>")
				  ;
			List<CmcRItem> list = orderItemDao.findItemJoinOrder(orderCat, dealStatus, start, end);
			if(!list.isEmpty()){
				int i = 0;
				for (CmcRItem bean : list) {
					sbHtml.append("<tr>")
						  .append("<td>").append(++i).append("</td>")
						  .append("<td>").append(bean.getItemname()).append("</td>")
						  .append("<td>").append(bean.getCmcROrder().getPayamount()).append("</td>")
						  .append("<td>").append(bean.getCmcROrder().getPlatform()).append("</td>")
						  .append("</tr>")
						  ;
				}
			}else{
				sbHtml.append("<tr><td colspan='4'>无数据</td></tr>");
			}		
			sbHtml.append("</table>");
			
			sbHtml.append("</body></html>");
			
			MailUtil.sendHtmlMail(SystemConfig.MANAGER_MAILS, null, "订阅统计"+yesterdayStr, sbHtml.toString());
		} catch (Exception e) {
			logger.error("订阅统计失败", e);
		}
	}
	
	/**
	 * 监控红包提现申请，通知客服
	 */
	public void monitorRedPacketCash(){
		try {
			if(SystemConfig.CS_MAILS!=null && SystemConfig.CS_MAILS.length>0){
				Long count = redPacketCashDao.getCashCount(DictConstant.DICT_REDPACKETCASH_STATUS_APPLY);
				if(count>0){
					String subject = count+"个红包提现申请待处理";
					MailUtil.sendSimpleMail(SystemConfig.CS_MAILS, null, subject, subject);
				}
			}
		} catch (Exception e) {
			logger.error("监控红包提现申请失败", e);
		}
	}
	
	/**
	 * 监控奖励金提现申请，通知客服
	 */
	public void monitorRewardCash(){
		try {
			if(SystemConfig.CS_MAILS!=null && SystemConfig.CS_MAILS.length>0){
				Long count = rewardCashService.getCashCount(DictConstants.DICT_CASHSTATUS_APPLY);
				if(count>0){
					String subject = count+"个奖励金提现申请待处理";
					MailUtil.sendSimpleMail(SystemConfig.CS_MAILS, null, subject, subject);
				}
			}
		} catch (Exception e) {
			logger.error("监控奖励金提现申请失败", e);
		}
	}
	
	/**
	 * 监控奖励金发放，通知系统管理员
	 */
	public void monitorRewardGrant(){
		try {
			Long taskCount = userCrowdTaskService.getCount4UngrantTaskReward();
			Long verifyCount = userCrowdTaskService.getCount4UngrantVerifyReward();
			Long qcCount = userCrowdTaskQcService.getCount4UngrantQcReward();
			if(taskCount>0 || verifyCount>0 || qcCount>0){
				String subject = "奖励金超时未发放";
				StringBuilder sbContent = new StringBuilder();
				if(taskCount>0){
					sbContent.append(taskCount).append("个任务奖励金超时未发放").append("\r\n");
				}
				if(verifyCount>0){
					sbContent.append(verifyCount).append("个审核奖励金超时未发放").append("\r\n");
				}
				if(qcCount>0){
					sbContent.append(qcCount).append("个审校奖励金超时未发放").append("\r\n");
				}
				MailUtil.sendSimpleMail(SystemConfig.MAILS, null, subject, sbContent.toString());
			}
		} catch (Exception e) {
			logger.error("监控奖励金发放失败", e);
		}
	}
	
	/**
	 * 题库可用题目监控
	 */
	public void monitorCrowdTaskLib(){
		try {
			List<CrowdTaskLibLackBean> list = crowdTaskLibService.findLib4Lack();
			if(list.isEmpty()) return;
			
			StringBuilder sbHtml = new StringBuilder();
			sbHtml.append("<html>")
			.append("<head><style>")
			.append("table{width:370px;font-size: 12px; border-left:solid 1px #000000; border-right:solid 1px #000000; border-bottom:solid 1px #000000; border-collapse:collapse;}")
			.append("th,td{border:solid 1px #000000; line-height:24px; background-color:#ffffff; height:24px;border-collapse:collapse;}")
			.append("</style></head>")
			.append("<body>")			  
			;
			
			sbHtml.append("<table>")
			.append("<tr>")
			.append("<th width='80px'>编号</th>")
			.append("<th width='190px'>名称</th>")
			.append("<th width='100px'>剩余题目</th>")
			.append("</tr>")
			;
			
			for (CrowdTaskLibLackBean bean : list) {
				sbHtml.append("<tr>")
				.append("<td>").append(bean.getLibId()).append("</td>")
				.append("<td>").append(bean.getLibName()).append("</td>")
				.append("<td>").append(bean.getIdleSubjectNum()).append("</td>")
				.append("</tr>")
				;
			}
			
			sbHtml.append("</table>");
			
			sbHtml.append("</body></html>");
			
			String subject = new StringBuilder().append(list.size()).append("个题库的可用题目数不足").toString();
			MailUtil.sendHtmlMail(SystemConfig.MAILS, null, subject, sbHtml.toString());
		} catch (Exception e) {
			logger.error("监控题库可用题目失败", e);
		}
	}
	
	public void monitorUserCrowdTaskVerifyOrQcUndealt(){
		for (String serviceType : CrowdTaskUtil.SERVICETYPES_CROWDTASK) {
			monitorUserCrowdTaskVerifyOrQcUndealt(serviceType);
		}
	}
	
	/**
	 * 用户任务未完成报名审核与未完成审校数
	 */
	public void monitorUserCrowdTaskVerifyOrQcUndealt(String serviceType){
		try {
			if(SystemConfig.CS_MAILS!=null && SystemConfig.CS_MAILS.length>0){
				Long waitVerifyCount = userCrowdTaskService.getVerifyCount(serviceType, DictConstant.DICT_USERCROWDTASK_VERIFYSTATUS_WAITING, null);
				Long processingVerifyCount = userCrowdTaskService.getVerifyCount(serviceType, DictConstant.DICT_USERCROWDTASK_VERIFYSTATUS_PROCESSING, null);
				
				Long waitQcCount = userCrowdTaskService.getQcCount(serviceType, DictConstant.DICT_USERCROWDTASK_QCSTATUS_WAITING, null);
				Long processingQcCount = userCrowdTaskService.getQcCount(serviceType, DictConstant.DICT_USERCROWDTASK_QCSTATUS_PROCESSING, null);
				
				Long verifyCount = waitVerifyCount + processingVerifyCount;
				Long qcCount = waitQcCount + processingQcCount;
				
				StringBuilder sbSubject = new StringBuilder();
				if(verifyCount>0){
					sbSubject.append(verifyCount+"个众包任务未完成审核");
				}
				if(qcCount>0){
					if(sbSubject.length()>0) sbSubject.append("，");
					sbSubject.append(qcCount+"个众包任务未完成审校");
				}
				
				StringBuilder sbMsg = new StringBuilder();
				if(waitVerifyCount>0){
					sbMsg.append("待审核任务数："+waitVerifyCount).append("\r\n");
				}
				if(processingVerifyCount>0){
					sbMsg.append("审核中任务数："+processingVerifyCount).append("\r\n");
				}
				if(waitQcCount>0){
					sbMsg.append("待审校任务数："+waitQcCount).append("\r\n");
				}
				if(processingQcCount>0){
					sbMsg.append("审校中任务数："+processingQcCount).append("\r\n");
				}
				
				if(sbSubject.length()>0){
					sbSubject.insert(0, "有").insert(0, DictMap.getServiceTypeMean(serviceType));
					MailUtil.sendSimpleMail(SystemConfig.CS_MAILS, null, sbSubject.toString(), sbMsg.toString());
				}				
			}
		} catch (Exception e) {
			logger.error("监控用户任务审核审校", e);
		}
	}
}

