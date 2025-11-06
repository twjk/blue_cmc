package com.qcmz.cmc.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.cache.EvalMap;
import com.qcmz.cmc.cache.LangMap;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.dao.IOrderDao;
import com.qcmz.cmc.dao.IOrderItemDao;
import com.qcmz.cmc.dao.IOrderLogDao;
import com.qcmz.cmc.dao.IOrderMsgDao;
import com.qcmz.cmc.dao.ITransPicDao;
import com.qcmz.cmc.dao.ITransTextDao;
import com.qcmz.cmc.entity.CmcEval;
import com.qcmz.cmc.entity.CmcPtPic;
import com.qcmz.cmc.entity.CmcPtTrans;
import com.qcmz.cmc.entity.CmcREval;
import com.qcmz.cmc.entity.CmcRItem;
import com.qcmz.cmc.entity.CmcRLog;
import com.qcmz.cmc.entity.CmcROrder;
import com.qcmz.cmc.entity.CmcRTrade;
import com.qcmz.cmc.entity.CmcTtTrans;
import com.qcmz.cmc.entity.CmcUCombo;
import com.qcmz.cmc.service.IOrderLogService;
import com.qcmz.cmc.service.IOrderMsgService;
import com.qcmz.cmc.service.IOrderService;
import com.qcmz.cmc.service.ITransPicService;
import com.qcmz.cmc.service.ITransTextService;
import com.qcmz.cmc.service.ITransVideoService;
import com.qcmz.cmc.service.IUserTransComboService;
import com.qcmz.cmc.service.IWalletConsumeService;
import com.qcmz.cmc.thrd.OrderEvalActivityThrd;
import com.qcmz.cmc.thrd.OrderNewNotifyCsThrd;
import com.qcmz.cmc.thrd.OrderNotifyThrd;
import com.qcmz.cmc.util.BeanConvertUtil;
import com.qcmz.cmc.util.OrderUtil;
import com.qcmz.cmc.ws.provide.vo.OrderDealDetailBean;
import com.qcmz.cmc.ws.provide.vo.OrderDetailBean;
import com.qcmz.cmc.ws.provide.vo.OrderEvalAddBean;
import com.qcmz.cmc.ws.provide.vo.OrderEvalDetailBean;
import com.qcmz.cmc.ws.provide.vo.OrderItemListBean;
import com.qcmz.cmc.ws.provide.vo.OrderListBean;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.constant.ExceptionConstants;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.exception.BusinessException;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.exception.ParamException;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.framework.util.log.OperLogUtil;
import com.qcmz.framework.util.log.Operator;
import com.qcmz.umc.ws.provide.locator.UserMap;
import com.qcmz.umc.ws.provide.vo.UserSimpleBean;

@Service
public class OrderServiceImpl implements IOrderService {
	@Autowired
	private IOrderDao orderDao;
	@Autowired
	private IOrderMsgDao orderMsgDao;
	@Autowired
	private IOrderLogDao orderLogDao;
	@Autowired
	private IOrderItemDao orderItemDao;
	@Autowired
	private ITransPicDao transPicDao;
	@Autowired
	private ITransTextDao transTextDao;	
	
	@Autowired
	private IOrderLogService orderLogService;
	@Autowired
	private IOrderMsgService orderMsgService;
	@Autowired
	private ITransPicService transPicService;
	@Autowired
	private ITransTextService transTextService;
	@Autowired
	private ITransVideoService transVideoService;
	@Autowired
	private IWalletConsumeService walletConsumeService;
	@Autowired
	private IUserTransComboService userTransComboService;
	
	@Autowired
	private LangMap langMap;
	@Autowired
	private EvalMap evalMap;
	
	/**
	 * 分页获取订单列表
	 * @param map
	 * @param pageBean<CmcROrder>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean){
		orderDao.queryByMapTerm(map, pageBean);
		
		List<CmcROrder> list = (List<CmcROrder>) pageBean.getResultList();
		if(list.isEmpty()) return;
		
		//加载商品列表
		Map<String, CmcROrder> orderMap = new HashMap<String, CmcROrder>();
		List<String> orderIds = new ArrayList<String>();
		Set<Long> userIds = new HashSet<Long>();
		for (CmcROrder cmcROrder : list) {
			orderIds.add(cmcROrder.getOrderid());
			orderMap.put(cmcROrder.getOrderid(), cmcROrder);
			userIds.add(cmcROrder.getUserid());				
		}
		List<CmcRItem> items = orderItemDao.findItem(orderIds);
		if(!items.isEmpty()){
			for (CmcRItem item : items) {
				orderMap.get(item.getOrderid()).getCmcRItems().add(item);
			}
		}
		
		//获取用户信息
		Map<Long, UserSimpleBean> userMap = UserMap.findUser(userIds);
		for (CmcROrder order : list) {
			order.setUser(userMap.get(order.getUserid()));
		}
	}
	
	/**
	 * 分页获取订单列表
	 * @param map
	 * @param pageBean<CmcROrder>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void findOrder(Map<String, String> map, PageBean pageBean){
		//1.获取翻译处理订单列表
		orderDao.queryByMapTerm(map, pageBean);
		
		//2.组装编号
		List<CmcROrder> list = (List<CmcROrder>) pageBean.getResultList();
		if(list.isEmpty()) return;
		
		Set<Long> userIds = new HashSet<Long>();
		List<String> picOrderIds = new ArrayList<String>();
		List<String> textOrderIds = new ArrayList<String>();
		for (CmcROrder order : list) {
			userIds.add(order.getUserid());
			if(DictConstant.DICT_ORDERTYPE_TRANSPIC.equals(order.getOrdertype())){
				picOrderIds.add(order.getOrderid());
			}else if(DictConstant.DICT_ORDERTYPE_TRANSTEXT.equals(order.getOrdertype())){
				textOrderIds.add(order.getOrderid());
			} 
		}		
		
		//3.获取图片翻译信息
		Map<String, CmcPtPic> picMap = new HashMap<String, CmcPtPic>();
		if(!picOrderIds.isEmpty()){
			List<CmcPtPic> pics = transPicDao.findPic(picOrderIds);
			for (CmcPtPic pic : pics) {				
				picMap.put(pic.getOrderid(), pic);
			}
			
		}
		
		//4.获取短文快译信息
		Map<String, CmcTtTrans> textMap = new HashMap<String, CmcTtTrans>();
		if(!textOrderIds.isEmpty()){
			List<CmcTtTrans> transes = transTextDao.findText(textOrderIds);
			for (CmcTtTrans trans : transes) {
				textMap.put(trans.getOrderid(), trans);
			}
		}
		
		//6.获取用户信息
		Map<Long, UserSimpleBean> userMap = UserMap.findUser(userIds);
		
		//7.组装
		for (CmcROrder order : list) {
			order.setUser(userMap.get(order.getUserid()));
			order.setCmcPtPic(picMap.get(order.getOrderid()));
			order.setCmcTtTrans(textMap.get(order.getOrderid()));
		}
	}
	
	/**
	 * 分页获取翻译处理订单列表，含图片/文本、评价、用户信息、用户套餐
	 * @param map
	 * @param pageBean<CmcROrder>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void findTransDeal(Map<String, String> map, PageBean pageBean){
		//1.获取翻译处理订单列表
		orderDao.findTransDeal(map, pageBean);
		
		//2.组装编号
		List<CmcROrder> list = (List<CmcROrder>) pageBean.getResultList();
		if(list.isEmpty()) return;
		
		Set<Long> userIds = new HashSet<Long>();
		List<String> picOrderIds = new ArrayList<String>();
		List<String> textOrderIds = new ArrayList<String>();
		List<String> evalOrderIds = new ArrayList<String>();
		
		for (CmcROrder order : list) {
			userIds.add(order.getUserid());
			if(DictConstant.DICT_ORDERTYPE_TRANSPIC.equals(order.getOrdertype())){
				picOrderIds.add(order.getOrderid());
			}else if(DictConstant.DICT_ORDERTYPE_TRANSTEXT.equals(order.getOrdertype())){
				textOrderIds.add(order.getOrderid());
			}
			if(SystemConstants.STATUS_OPER_ALREADY.equals(order.getEvalstatus())){
				evalOrderIds.add(order.getOrderid());
			}
		}		
		
		//3.获取图片翻译信息
		Map<String, CmcPtPic> picMap = new HashMap<String, CmcPtPic>();
		if(!picOrderIds.isEmpty()){
			Map<String, StringBuilder> picDstMap = new HashMap<String, StringBuilder>();
			StringBuilder sbDst = null;
			List<CmcPtTrans> transes = transPicDao.findTrans(picOrderIds);
			for (CmcPtTrans trans : transes) {
				sbDst = picDstMap.get(trans.getPicid()); 
				if(sbDst==null){
					sbDst = new StringBuilder(trans.getDst());
				}else{
					sbDst.append("。").append(trans.getDst());
				}
				picDstMap.put(trans.getPicid(), sbDst);
			}
			
			List<CmcPtPic> pics = transPicDao.findPic(picOrderIds);
			for (CmcPtPic pic : pics) {
				sbDst = picDstMap.get(pic.getOrderid()); 
				if(sbDst!=null){
					pic.setDst(sbDst.toString());
				}
				picMap.put(pic.getOrderid(), pic);
			}
			
		}
		
		//4.获取短文快译信息
		Map<String, CmcTtTrans> textMap = new HashMap<String, CmcTtTrans>();
		if(!textOrderIds.isEmpty()){
			List<CmcTtTrans> transes = transTextDao.findText(textOrderIds);
			for (CmcTtTrans trans : transes) {
				textMap.put(trans.getOrderid(), trans);
			}
		}
		
		//5.评价
		Map<String, CmcREval> evalMap = new HashMap<String, CmcREval>();
		if(!evalOrderIds.isEmpty()){
			List<CmcREval> evals = orderDao.findEval(evalOrderIds);
			for (CmcREval eval : evals) {
				evalMap.put(eval.getOrderid(), eval);
			}
		}
		
		//7.获取用户信息
		Map<Long, UserSimpleBean> userMap = UserMap.findUser(userIds);
		
		//8.赋值
		for (CmcROrder order : list) {
			order.setUser(userMap.get(order.getUserid()));
			order.setCmcPtPic(picMap.get(order.getOrderid()));
			order.setCmcTtTrans(textMap.get(order.getOrderid()));
			order.setCmcREval(evalMap.get(order.getOrderid()));
		}
	}
	
	/**
	 * 分页获取订单列表
	 * @param userId
	 * @param pageSize
	 * @param lastId
	 * @return
	 */
	public List<OrderListBean> findOrderInfo(Long userId, String employeeId, Integer orderType, int pageSize, String lastId){
		List<OrderListBean> result = orderDao.findOrderInfo(userId, employeeId, orderType, pageSize, lastId);
		if(result.isEmpty()) return result;
		
		//封装商品信息
		Map<String, OrderListBean> orderMap = new HashMap<String, OrderListBean>();
		List<String> orderIds = new ArrayList<String>();
		for (OrderListBean bean : result) {
			orderIds.add(bean.getOrderId());
			orderMap.put(bean.getOrderId(), bean);
		}
		
		Map<String, OrderItemListBean> textItemMap = new HashMap<String, OrderItemListBean>();
		OrderItemListBean itemBean = null;
		OrderListBean orderBean = null;
		
		List<CmcRItem> items = orderItemDao.findItem(orderIds);	
		for (CmcRItem cmcRItem : items) {
			orderBean = orderMap.get(cmcRItem.getOrderid());
			
			itemBean = BeanConvertUtil.toOrderItemListBean(cmcRItem, orderBean);
			
			orderBean.getItems().add(itemBean);
			
			if(DictConstant.DICT_ORDERTYPE_TRANSTEXT.equals(orderBean.getOrderType())){
				textItemMap.put(cmcRItem.getOrderid(), itemBean);
			}
		}
			
		//补充短文翻译
		if(!textItemMap.isEmpty()){
			List<String> textOrderIds = new ArrayList<String>();
			textOrderIds.addAll(textItemMap.keySet());
			List<CmcTtTrans> texts = transTextService.findText(textOrderIds);
			for (CmcTtTrans tt : texts) {
				itemBean = textItemMap.get(tt.getOrderid());
				itemBean.setSrc(tt.getSrc());
			}
		}
		
		return result;
	}
	
	/**
	 * 获取需要退款的订单编号
	 * @param orderId
	 * @param userId
	 * @return
	 */
	public List<String> findNeedRefund(){
		return orderDao.findNeedRefund();
	}
	
	/**
	 * 分页获取需取消订单编号
	 * @param pageSize
	 * @param lastId
	 * @return
	 */
	public List<String> findNeedCancel(int pageSize, String lastId){
		return orderDao.findNeedCancel(pageSize, lastId);
	}
	
	/**
	 * 获取待处理的订单
	 * @param orderCat not null
	 * @param orderTypes not null
	 * @return
	 */
	public List<CmcROrder> findWaitDeal(Integer orderCat, List<Integer> orderTypes){
		return orderDao.findWaitDeal(orderCat, orderTypes);
	}
	
	/**
	 * 获取待处理的订单
	 * @param orderCat not null
	 * @param orderType not null
	 * @return
	 */
	public List<CmcROrder> findWaitDeal(Integer orderCat, Integer orderType){
		List<Integer> orderTypes = new ArrayList<Integer>();
		orderTypes.add(orderType);
		return orderDao.findWaitDeal(orderCat, orderTypes);
	}

	/**
	 * 获取即将需要处理的预约订单，带用户套餐
	 * @param orderCat not null
	 * @param orderTypes not null
	 * @return
	 */
	public List<CmcROrder> findSoonDeal(Integer orderCat, List<Integer> orderTypes){
		return orderDao.findSoonDeal(orderCat, orderTypes);
	}
	
	/**
	 * 获取即将需要处理的预约订单 ，带用户套餐
	 * @param orderCat not null
	 * @param orderType not null
	 * @return
	 */
	public List<CmcROrder> findSoonDeal(Integer orderCat, Integer orderType){
		List<Integer> orderTypes = new ArrayList<Integer>();
		orderTypes.add(orderType);
		return orderDao.findSoonDeal(orderCat, orderTypes);
	}	
	
	/**
	 * 获取未完成处理的订单
	 * @param orderCat not null
	 * @param orderTypes not null
	 * @return
	 * 修改历史：
	 */
	public List<CmcROrder> findUndealt(Integer orderCat, List<Integer> orderTypes){
		return orderDao.findUndealt(orderCat, orderTypes);
	}
	
	/**
	 * 获取未完成处理的订单
	 * @param orderCat not null
	 * @param orderType not null
	 * @return
	 * 修改历史：
	 */
	public List<CmcROrder> findUndealt(Integer orderCat, Integer orderType){
		List<Integer> orderTypes = new ArrayList<Integer>();
		orderTypes.add(orderType);
		return orderDao.findUndealt(orderCat, orderTypes);
	}
	
	/**
	 * 获取商品列表
	 * @param orderId
	 * @return
	 */
	public List<CmcRItem> findItem(String orderId){
		return orderItemDao.findItem(orderId);
	}
	
	/**
	 * 获取评价列表
	 * @param orderIds
	 * @return
	 */
	public List<CmcREval> findEval(List<String> orderIds){
		return orderDao.findEval(orderIds);
	}
	
	/**
	 * 是否有已经购买的订单
	 * @param userId
	 * @param orderType
	 * @param itemId
	 * @param refId
	 * @return
	 */
	public boolean hasBought(Long userId, Integer orderType, Long itemId, String refId){
		return orderItemDao.hasBought(userId, orderType, itemId, refId);
	}
	
	/**
	 * 获取订单详细信息
	 * @param orderId
	 * @param userId
	 * @return
	 * @exception ParamException
	 */
	public OrderDetailBean getOrderDetail(String orderId, Long userId){
		CmcROrder order = getOrder(orderId, userId);
		if(order==null){
			throw new ParamException(ExceptionConstants.MSG_DATA_NOTEXIST);
		}		
		
		//订单基础信息
		CmcUCombo userCombo = null;
		if(order.getUcid()!=null){
			userCombo = userTransComboService.getUserCombo(order.getUcid());
		}
		OrderDetailBean result = BeanConvertUtil.toOrderDetailBean(order, userCombo);
		
		//商品信息
		List<CmcRItem> items = orderItemDao.findItem(orderId);	
		for (CmcRItem cmcRItem : items) {
			result.getItems().add(BeanConvertUtil.toOrderItemBean(cmcRItem));
		}
		
		//日志
		result.setLogs(orderLogDao.findLogInfo(orderId, DictConstant.DICT_LOGTYPE_FLOW, false));
		
		return result;
	}
	
	/**
	 * 获取处理 订单详细信息
	 * @param orderId
	 * @param userId
	 * @return
	 * @exception ParamException
	 */
	public OrderDealDetailBean getOrderDetail4Deal(String orderId){
		CmcROrder order = getOrder(orderId);
		if(order==null){
			throw new ParamException(ExceptionConstants.MSG_DATA_NOTEXIST);
		}		
		
		OrderDealDetailBean result = new OrderDealDetailBean();
		result.setOrderId(order.getOrderid());
		result.setOrderCat(order.getOrdercat());
		result.setOrderType(order.getOrdertype());
		result.setUid(order.getUserid());
		result.setAmount(order.getAmount());
		result.setCouponAmount(order.getCouponamount());
		result.setWalletAmount(order.getWalletamount());
		result.setPayableAmount(order.getPayableamount());
		result.setAppointFlag(order.getAppointflag());
		result.setCanAccept(OrderUtil.canAccept(order.getOrdercat(), order.getDealprogress(), order.getWaittime()));
		result.setLon(order.getLon());
		result.setLat(order.getLat());
		result.setAddress(order.getAddress());
		result.setRequirement(order.getRequirement());
		result.setWaitTime(order.getWaittime()!=null?order.getWaittime().getTime():null);
		result.setOperTime(order.getOpertime()!=null?order.getOpertime().getTime():null);
		result.setNeedTime(order.getNeedtime());
		result.setFinishTime(order.getFinishtime()!=null?order.getFinishtime().getTime():null);
		result.setOperator(order.getOpercd());
		result.setOperatorName(order.getOpername());
		result.setDealStatus(order.getDealstatus());
		result.setDealProgress(order.getDealprogress());
		
		//用户订单数
		result.setOrderCount(orderDao.getUserProcessCount(order.getUserid(), order.getOrdertype(), null));
		
		//日志
		result.setLogs(orderLogDao.findLogInfo(orderId, null, true));
		
		//留言
		result.setMsgs(orderMsgService.findMsg(orderId, 6, null));
				
		return result;
	}
	
	/**
	 * 获取订单信息
	 * @param orderId
	 * @return
	 */
	public CmcROrder getOrder(String orderId){
		return (CmcROrder) orderDao.load(CmcROrder.class, orderId);
	}
	
	/**
	 * 获取用户订单信息
	 * @param orderId
	 * @param userId
	 * @return
	 */
	public CmcROrder getOrder(String orderId, Long userId){
		return orderDao.getOrder(orderId, userId);
	}
	
	/**
	 * 获取订单信息，带商品、日志、留言
	 * @param orderId
	 * @return
	 */
	public CmcROrder getOrderJoin(String orderId){
		CmcROrder order = getOrder(orderId);
		order.setCmcRItems(orderItemDao.findItem(orderId));
		order.setCmcRMsgs(orderMsgDao.findMsg(orderId));
		order.setCmcRLogs(orderLogDao.findLog(orderId, null));
		if(order.getUcid()!=null){
			order.setCmcUCombo(userTransComboService.getUserCombo(order.getUcid()));
		}
		return order;
	}
	
	/**
	 * 获取订单评价
	 * @param orderId
	 * @return
	 */
	public CmcREval getOrderEval(String orderId){
		return orderDao.getOrderEval(orderId);
	}
	
	/**
	 * 获取订单评价信息
	 * @param orderId
	 * @return
	 * @exception ParamException
	 */
	public OrderEvalDetailBean getOrderEvalInfo(String orderId){
		CmcREval cmcREval = getOrderEval(orderId);
		if(cmcREval==null){
			throw new ParamException(ExceptionConstants.MSG_DATA_NOTEXIST);
		}
		CmcEval eval = evalMap.getEval(cmcREval.getEvalid());
		return BeanConvertUtil.toOrderEvalDetailBean(cmcREval, eval);
	} 
	
	/**
	 * 获取用户指定日期订单数
	 * @param userId
	 * @param date
	 * @param orderType
	 * @return
	 */
	public Long getUserDayCount(Long userId, Date date, Integer orderType){
		return orderDao.getUserDayCount(userId, date, orderType);
	}
	
	/**
	 * 获取处理订单数
	 * @param orderCat not null
	 * @param orderTypes not null
	 * @param dealprogressList not null
	 * @return
	 */
	public Long getProcessCount(Integer orderCat, List<Integer> orderTypes, List<String> dealprogressList){
		return orderDao.getProcessCount(orderCat, orderTypes, dealprogressList);
	}
	
	/**
	 * 获取新的订单编号
	 * @return
	 * 修改历史：
	 */
	public String newOrderId(){
		return orderDao.newOrderId();
	}
	
	/**
	 * 保存订单
	 * @param order
	 * @param ritem
	 */
	public void saveOrder(CmcROrder order, CmcRItem ritem){
		orderDao.save(order);
		orderDao.save(ritem);
		orderLogService.saveFlowLog(order.getOrderid(), order.getDealstatus(), String.valueOf(order.getUserid()), SystemConstants.OPERATOR_USER, order.getCreatetime(),null);
	}
	
	/**
	 * 修改预约时间
	 * @param order
	 * @param appointTime
	 * @param oper
	 */
	public void updateAppointTime(CmcROrder order, Date appointTime, Operator oper){
		Date now = new Date();
		Date waitTime = null;
		Integer soonDealStatus = order.getSoondealstatus();
		Integer appointFlag = order.getAppointflag();
		String oriAppointTime = DateUtil.formatDateTime(order.getAppointtime());
		if(appointTime.after(now)){
			waitTime = appointTime;
			appointFlag = SystemConstants.STATUS_ON;
			soonDealStatus = SystemConstants.STATUS_OPER_ENABLED;
		}else{
			waitTime = now;
			soonDealStatus = null;
		}
		
		order.setAppointflag(appointFlag);
		order.setAppointtime(appointTime);
		order.setWaittime(waitTime);
		order.setSoondealstatus(soonDealStatus);
		orderDao.update(order);
		
		//日志
		StringBuilder sbLog = new StringBuilder()
				.append("预约时间【").append(oriAppointTime).append("】")
				.append("修改为【").append(DateUtil.formatDateTime(order.getAppointtime())).append("】")
				;
		orderLogService.saveOperLog(order.getOrderid(), order.getDealstatus(), oper.getOperCd(), oper.getOperName(), sbLog.toString());
	}
	
	/**
	 * 操作员接单
	 * 不校验订单是否存在以及能否接单，在外部处理
	 * @param order
	 * @param oper
	 * @return
	 */
	public void acceptOrder(CmcROrder order, Operator oper){
		//是否交单后二次接单
		boolean handover = DictConstant.DICT_ORDER_DEALSTATUS_PROCESSING.equals(order.getDealstatus()); 
		
		if(DictConstant.DICT_ORDERCAT_TRANS.equals(order.getOrdercat())){
			order.setDealstatus(DictConstant.DICT_ORDER_DEALSTATUS_PROCESSING);
		}else if(DictConstant.DICT_ORDERCAT_DUB.equals(order.getOrdercat())){
			order.setDealstatus(DictConstant.DICT_ORDER_DEALSTATUS_DUBING);
		}else{
			order.setDealstatus(DictConstant.DICT_ORDER_DEALSTATUS_DEALING);
		}
		order.setDealprogress(OrderUtil.dealStatus2ProgressStatus(order.getDealstatus()));
		order.setOpercd(oper.getOperCd());
		order.setOpername(oper.getOperName());
		order.setOpertime(new Date());
		orderDao.update(order);
		
		//日志
		if(!handover){
			orderLogService.saveFlowLog(order.getOrderid(), order.getDealstatus(), oper.getOperCd(), oper.getOperName(), new Date());
		}else{
			orderLogService.saveOperLog(order.getOrderid(), order.getDealstatus(), oper.getOperCd(), oper.getOperName(), "接单");
		}
		
		//通知用户
		if(!handover){
			OrderNotifyThrd.notifyStatus(order);
		}
	}
	
	/**
	 * 交单
	 * @param orderId
	 * @param from
	 * @param to
	 * @param oper
	 */
	public void handoverOrder(String orderId, String from, String to, Operator oper){
		//1.校验
		CmcROrder order = getOrder(orderId);
		if(order==null){
			throw new DataException(ExceptionConstants.MSG_DATA_NOTEXIST);
		}
		if(!OrderUtil.canHandover(order.getOrdercat(), order.getDealprogress())){
			throw new DataException(ExceptionConstants.MSG_OPER_CANNOT);
		}
		
		//2.修改订单信息：操作员置空，修改订单语言
		boolean changeFrom = StringUtil.notBlankAndNull(from) && !from.equals(order.getFromlang());
		boolean changeTo = StringUtil.notBlankAndNull(to) && !to.equals(order.getTolang());
		StringBuilder sbLangLog = new StringBuilder();
		if(changeFrom || changeTo){
			sbLangLog.append("语言【").append(langMap.getLangName4Text(order.getFromlang())).append("->").append(langMap.getLangName4Text(order.getTolang())).append("】");
			
			if(changeFrom)	order.setFromlang(from);
			if(changeTo)	order.setTolang(to);
			sbLangLog.append("修改为【").append(langMap.getLangName4Text(order.getFromlang())).append("->").append(langMap.getLangName4Text(order.getTolang())).append("】");
		}
		order.setDealprogress(DictConstants.DICT_DEALPROGRESS_WAITING);
		order.setOpercd(null);
		order.setOpername(null);
		order.setOpertime(null);
		orderDao.update(order);
		
		//3.修改翻译语言
		if(changeFrom || changeTo){
			if(DictConstant.DICT_ORDERTYPE_TRANSTEXT.equals(order.getOrdertype())){
				CmcTtTrans trans = transTextDao.getTrans(orderId);
				if(changeFrom)	trans.setFromlang(from);
				if(changeTo)	trans.setTolang(to);
				transTextDao.saveOrUpdate(trans);
			}else if(DictConstant.DICT_ORDERTYPE_TRANSPIC.equals(order.getOrdertype())){
				CmcPtPic pic = transPicService.getPic(orderId);
				if(changeFrom)	pic.setFromlang(from);
				if(changeTo)	pic.setTolang(to);
				transPicDao.saveOrUpdate(pic);
			}
		}
		
		//4.日志
		if(changeFrom || changeTo){
			orderLogService.saveOperLog(orderId, order.getDealstatus(), oper.getOperCd(), oper.getOperName(), sbLangLog.toString());
		}
		orderLogService.saveOperLog(orderId, order.getDealstatus(), oper.getOperCd(), oper.getOperName(), "交单");
		
		//5.通知客服
		OrderNewNotifyCsThrd.newOrderNotify(order);
	}
	
	/**
	 * 操作员捡单
	 * @param orderId
	 * @param oper
	 * @return
	 * @exception DataException
	 */
	public CmcROrder pickOrder(String orderId, Operator oper){
		CmcROrder order = getOrder(orderId);
		if(order==null){
			throw new DataException(ExceptionConstants.MSG_DATA_NOTEXIST);
		}
		if(!SystemConstants.STATUS_OPER_ENABLED.equals(order.getStatus())){
			throw new DataException(ExceptionConstants.MSG_OPER_CANNOT);
		}

		order.setPickstatus(SystemConstants.STATUS_OPER_ALREADY);
		order.setOpercd(oper.getOperCd());
		order.setOpername(oper.getOperName());
		order.setOpertime(new Date());
		orderDao.update(order);
		
		return order;
	}
	
	/**
	 * 操作员拒单
	 * @param orderId
	 * @param reason
	 * @param oper
	 * @return
	 * @exception DataException
	 */
	public CmcROrder rejectOrder(String orderId, String reason, Operator oper){
		//校验
		CmcROrder order = getOrder(orderId);
		if(!OrderUtil.canReject(order.getOrdercat(), order.getDealstatus())){
			throw new DataException(ExceptionConstants.MSG_OPER_CANNOT);
		}
		
		//退款
		if(order.getWalletamount()>0){
			boolean refund = walletConsumeService.refund(order.getUserid(), order.getWalletamount(), orderId);
			if(!refund){
				throw new DataException("退回钱包失败");
			}
		}
		//套餐使用回滚
		if(order.getUcid()!=null){
			userTransComboService.rollbackUserCombo(order.getUcid(), orderId);
		}
		
		//取消处理
		Date now = new Date();
		String dealStatus = DictConstant.DICT_ORDER_DEALSTATUS_CANCEL;
		if(StringUtil.isBlankOrNull(order.getOpercd())){
			order.setOpercd(oper.getOperCd());
			order.setOpername(oper.getOperName());
			order.setOpertime(now);
		}
		order.setWalletamount(0.0);
		order.setDealstatus(dealStatus);
		order.setDealprogress(OrderUtil.dealStatus2ProgressStatus(order.getDealstatus()));
		order.setCheckstatus(null);
		order.setFinishtime(now);
		
		orderDao.saveOrUpdate(order);
		
		//日志
		orderLogService.saveFlowLog(order.getOrderid(), dealStatus, oper.getOperCd(), oper.getOperName(), now, reason);
		
		if(DictConstant.DICT_ORDERTYPE_TRANSVIDEO.equals(order.getOrdertype())){
			transVideoService.rejectOrder(orderId, now);
		}
		
		return order;
	}
	
	/**
	 * 退回钱包抵扣金额
	 * @param orderId
	 * @return
	 */
	public boolean refundWallet(String orderId){
		CmcROrder order = getOrder(orderId);
		boolean refund = walletConsumeService.refund(order.getUserid(), order.getWalletamount(), orderId);
		if(refund){
			order.setWalletamount(0.0);
			order.setDealprogress(DictConstants.DICT_DEALPROGRESS_DEALT);
			order.setDealstatus(DictConstant.DICT_ORDER_DEALSTATUS_REFUNDED);
			orderDao.saveOrUpdate(order);
			if(order.getPayamount()==0){
				CmcRLog log = new CmcRLog();
				log.setOrderid(order.getOrderid());
				log.setLogtype(DictConstant.DICT_LOGTYPE_FLOW);
				log.setDealstatus(order.getDealstatus());
				log.setOpercd(SystemConstants.OPERATOR_DEFAULT);
				log.setOpername(SystemConstants.OPERATOR_DEFAULT);
				log.setOpertime(new Date());
				orderDao.save(log);
			}
		}
		return refund;
	}
	
	/**
	 * 取消订单并退钱包抵扣款
	 * @param orderId
	 * @param userId 可能为null
	 * @param reason
	 * @param oper
	 * @return
	 * @exception DataException
	 */
	public CmcROrder cancelOrder(String orderId, Long userId, String reason, Operator operator){
		//校验
		CmcROrder order = null;
		if(userId!=null){
			order = getOrder(orderId, userId);
		}else{
			order = getOrder(orderId);
		}
		if(!OrderUtil.canCancel(order.getOrdercat(), order.getOrdertype(), order.getDealstatus())){
			throw new DataException(ExceptionConstants.MSG_OPER_CANNOT);
		}
		
		//退款
		if(order.getWalletamount()>0){
			boolean refund = walletConsumeService.refund(order.getUserid(), order.getWalletamount(), orderId);
			if(!refund){
				throw new BusinessException("退回钱包失败");
			}
		}
		//回滚套餐使用
		if(order.getUcid()!=null && order.getUcid()>0){
			userTransComboService.rollbackUserCombo(order.getUcid(), order.getOrderid());
		}
		
		Date now = new Date();
		
		//取消处理
		if(DictConstant.DICT_ORDERTYPE_TRANSVIDEO.equals(order.getOrdertype())){
			//视频翻译用户取消的订单，需要出现在已处理列表
			order.setDealprogress(DictConstants.DICT_DEALPROGRESS_DEALT);
		}else{
			order.setDealprogress(null);
		}
		order.setOpercd(operator.getOperCd());
		order.setOpername(operator.getOperName());
		order.setDealstatus(DictConstant.DICT_ORDER_DEALSTATUS_CANCEL);
		order.setFinishtime(now);
		order.setWalletamount(0.0);
		
		orderDao.saveOrUpdate(order);
		
		//日志
		orderLogService.saveFlowLog(order.getOrderid(), order.getDealstatus(), operator.getOperCd(), operator.getOperName(), now, reason);
		
		return order;
	}
	
	/**
	 * 删除订单
	 * 有交易记录的订单不能删除
	 * @param orderId
	 * @param oper
	 * 修改历史：
	 */
	public void deleteOrder(String orderId, Operator oper){
		//清除子表信息
		orderMsgDao.clearMsg(orderId);
		orderLogDao.clearLog(orderId);
		orderItemDao.clearItem(orderId);
		
		//有交易记录的订单不能删除，所以不清除交易信息
		
		//删除订单
		CmcROrder order = getOrder(orderId);
		orderDao.delete(CmcROrder.class, orderId);
		
		OperLogUtil.saveDelLog(orderId, oper, order);
	}
	
	/**
	 * 隐藏订单
	 * @param orderId
	 * 修改历史：
	 */
	public void hideOrder(String orderId){
		//更新状态
		orderDao.updateStatus(orderId, SystemConstants.STATUS_OFF);
		
		//操作日志
		CmcROrder order = getOrder(orderId);
		orderLogService.saveOperLog(orderId, order.getDealstatus(), String.valueOf(order.getUserid()), SystemConstants.OPERATOR_USER, "删除");
	}
	
	/**
	 * 更新处理进度
	 * @param orderId
	 * @param dealProgress
	 * 修改历史：
	 */
	public void updateDealProgress(String orderId, String dealProgress){
		orderDao.updateDealProgress(orderId, dealProgress);
	}
	
	/**
	 * 保存交易结果
	 * @param trade
	 * @param order
	 * @param log
	 */
	public void saveTradeReslt(CmcRTrade trade, CmcROrder order, CmcRLog log){
		orderDao.saveOrUpdate(order);
		orderDao.save(trade);
		orderDao.save(log);
	}
	
	/**
	 * 保存交易信息
	 * @param trade
	 */
	public void saveTrade(CmcRTrade trade){
		orderDao.save(trade);
	}
	
	/**
	 * 保存订单处理结果
	 * @param orderId
	 * @param needTime 预计处理时长
	 * @param from 源语言
	 * @param to 目标语言
	 * @param operator 操作员帐户
	 * @param operatorName 操作员姓名
	 */
	public void saveOrderDeal(String orderId, Long needTime, String from, String to, String operator, String operatorName){
		CmcROrder order = getOrder(orderId);
		
		//预计翻译时长
		boolean changeNeedTime = needTime!=null && !needTime.equals(order.getNeedtime());
		StringBuilder sbNeedTimeLog = new StringBuilder();
		if(changeNeedTime){
			sbNeedTimeLog.append("预计翻译时长【").append(order.getNeedTimeStr()).append("】");
			
			order.setNeedtime(needTime);
			
			sbNeedTimeLog.append("修改为【").append(order.getNeedTimeStr()).append("】");
		}

		//换语言
		boolean changeFrom = StringUtil.notBlankAndNull(from) && !from.equals(order.getFromlang());
		boolean changeTo = StringUtil.notBlankAndNull(to) && !to.equals(order.getTolang());
		StringBuilder sbLangLog = new StringBuilder();
		if(changeFrom || changeTo){
			sbLangLog.append("语言【").append(langMap.getLangName4Text(order.getFromlang())).append("->").append(langMap.getLangName4Text(order.getTolang())).append("】");
			
			if(changeFrom)	order.setFromlang(from);
			if(changeTo)	order.setTolang(to);
			
			sbLangLog.append("修改为【").append(langMap.getLangName4Text(order.getFromlang())).append("->").append(langMap.getLangName4Text(order.getTolang())).append("】");
		}
		
		//订单信息入库
		if(changeNeedTime || changeFrom || changeTo){
			transTextDao.update(order);
		}
		
		//日志
		if(changeFrom || changeTo){
			orderLogService.saveOperLog(order.getOrderid(), order.getDealstatus(), operator, operatorName, sbLangLog.toString());
		}
		if(changeNeedTime){
			orderLogService.saveOperLog(order.getOrderid(), order.getDealstatus(), operator, operatorName, sbNeedTimeLog.toString());
		}
	}
	
	/**
	 * 保存订单完成信息
	 * 状态变更、翻译语言变更、日志
	 * @param orderId
	 * @param from 源语言
	 * @param to 目标语言
	 * @param operator 操作员帐户
	 * @param operatorName 操作员姓名
	 */
	public void saveOrderFinish(String orderId, String from, String to, String operator, String operatorName){
		Date now = new Date();
		
		CmcROrder order = getOrder(orderId);
				
		//变更状态和语言
		boolean changeFrom = StringUtil.notBlankAndNull(from) && !from.equals(order.getFromlang());
		boolean changeTo = StringUtil.notBlankAndNull(to) && !to.equals(order.getTolang());
		StringBuilder sbLangLog = new StringBuilder();
		if(changeFrom || changeTo){
			sbLangLog.append("语言【").append(langMap.getLangName4Text(order.getFromlang())).append("->").append(langMap.getLangName4Text(order.getTolang())).append("】");
			
			if(changeFrom)	order.setFromlang(from);
			if(changeTo)	order.setTolang(to);
			
			sbLangLog.append("修改为【").append(langMap.getLangName4Text(order.getFromlang())).append("->").append(langMap.getLangName4Text(order.getTolang())).append("】");
		}
		order.setDealstatus(DictConstant.DICT_ORDER_DEALSTATUS_FINISH);
		order.setDealprogress(DictConstants.DICT_DEALPROGRESS_DEALT);
		if(DictConstant.DICT_ORDERCAT_TRANS.equals(order.getOrdercat())){
			order.setCheckstatus(DictConstants.DICT_CHECKSTATUS_UNCHECK);
			order.setEvalstatus(SystemConstants.STATUS_OPER_ENABLED);
		}
		order.setFinishtime(now);
		order.setCommissionstatus(SystemConstants.STATUS_OPER_ENABLED);
		orderDao.update(order);
		
		//日志
		if(changeFrom || changeTo){
			orderLogService.saveOperLog(order.getOrderid(), order.getDealstatus(), operator, operatorName, sbLangLog.toString());
		}
		orderLogService.saveFlowLog(order.getOrderid(), order.getDealstatus(), operator, operatorName, now);
	}
	
	/**
	 * 保存订单完成信息
	 * 状态变更、订单商品变更、日志
	 * @param orderId
	 * @param items
	 * @param operator
	 * @param operatorName
	 */
	public void saveOrderFinish(String orderId, List<CmcRItem> items, String operator, String operatorName){
		Date now = new Date();

		CmcROrder order = getOrder(orderId);
		
		//商品
		if(items!=null && !items.isEmpty()){
			orderItemDao.saveOrUpdateAll(items);
		}
		
		//订单
		if(DictConstant.DICT_ORDERCAT_SUB.equals(order.getOrdercat())){
			order.setDealstatus(DictConstant.DICT_ORDER_DEALSTATUS_SUB);
		}else{
			order.setDealstatus(DictConstant.DICT_ORDER_DEALSTATUS_FINISH);
		}
		order.setDealprogress(DictConstants.DICT_DEALPROGRESS_DEALT);
		order.setFinishtime(now);
		orderDao.update(order);
		
		//日志
		orderLogService.saveFlowLog(order.getOrderid(), order.getDealstatus(), operator, operatorName, now);
	}
	
	/**
	 * 保存订单完成信息
	 * 状态变更、日志
	 * @param orderId
	 * @param operator
	 * @param operatorName
	 */
	public void saveOrderFinish(String orderId, String operator, String operatorName){
		Date now = new Date();
		//订单
		CmcROrder order = getOrder(orderId);
		order.setDealstatus(DictConstant.DICT_ORDER_DEALSTATUS_FINISH);
		order.setDealprogress(DictConstants.DICT_DEALPROGRESS_DEALT);
		order.setFinishtime(now);
		orderDao.update(order);
		
		//日志
		orderLogService.saveFlowLog(order.getOrderid(), order.getDealstatus(), operator, operatorName, now);
	}
	
	/**
	 * 更新地址
	 * @param orderId
	 * @param address
	 */
	public void updateAddress(String orderId, String address){
		orderDao.updateAddress(orderId, address);
	}
	
	/**
	 * 评价订单
	 * @param entity
	 * @exception ParamException,DataException
	 */
	public void evalOrder(OrderEvalAddBean bean){
		//校验
		CmcEval eval = evalMap.getEval(bean.getEvalId());
		if(eval==null){
			throw new ParamException("评价编号有误");
		}
		
		CmcROrder order = getOrder(bean.getOrderId(), bean.getUid());
		if(order==null){
			throw new ParamException(ExceptionConstants.MSG_DATA_NOTEXIST);
		}
		if(SystemConstants.STATUS_OPER_ALREADY.equals(order.getEvalstatus())){
			throw new DataException("订单已经评价");
		}
		if(!SystemConstants.STATUS_OPER_ENABLED.equals(order.getEvalstatus())){
			throw new ParamException(ExceptionConstants.MSG_OPER_CANNOT);
		}
		
		//入库
		CmcREval orderEval = new CmcREval();
		orderEval.setOrderid(order.getOrderid());
		orderEval.setEvalid(eval.getEvalid());
		orderEval.setEvallevel(eval.getEvallevel());
		orderEval.setLevelname(eval.getLevelname());
		orderEval.setEvaltag(bean.getEvalTag());
		orderEval.setEvaltime(new Date());
		orderDao.save(orderEval);
		
		orderDao.updateEvalStatus(order.getOrderid(), SystemConstants.STATUS_OPER_ALREADY);
		
		//参加活动
		OrderEvalActivityThrd.start(order);
	}
	
	/**
	 * 更新佣金结算结果
	 * @param orderId
	 * @param commissionStatus
	 * @param commissionAmount
	 * @param commissionDate
	 */
	public void updateCommission(String orderId, Integer commissionStatus, Double commissionAmount, Date commissionDate){
		orderDao.updateCommission(orderId, commissionStatus, commissionAmount, commissionDate);
	}
	
	/**
	 * 更新即将开始处理状态
	 * @param orderId
	 * @param soonDealStatus
	 */
	public void updateSoonDealStatus(String orderId, Integer soonDealStatus){
		orderDao.updateSoonDealStatus(orderId, soonDealStatus);
	}
}
