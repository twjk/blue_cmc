package com.qcmz.cmc.process;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.cache.TransComboMap;
import com.qcmz.cmc.config.SystemConfig;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.entity.CmcCombo;
import com.qcmz.cmc.entity.CmcComboPackage;
import com.qcmz.cmc.entity.CmcComboPlatform;
import com.qcmz.cmc.entity.CmcRCombo;
import com.qcmz.cmc.entity.CmcROrder;
import com.qcmz.cmc.entity.CmcUCombo;
import com.qcmz.cmc.entity.CmcWalletAccount;
import com.qcmz.cmc.service.IOrderService;
import com.qcmz.cmc.service.ITransComboOrderService;
import com.qcmz.cmc.service.IUserTransComboService;
import com.qcmz.cmc.service.IWalletAccountService;
import com.qcmz.cmc.thrd.OrderNotifyThrd;
import com.qcmz.cmc.util.BeanConvertUtil;
import com.qcmz.cmc.ws.provide.vo.OrderCreateResult;
import com.qcmz.cmc.ws.provide.vo.OrderPrepayBean;
import com.qcmz.cmc.ws.provide.vo.SceneBean;
import com.qcmz.cmc.ws.provide.vo.TransComboAddBean;
import com.qcmz.cmc.ws.provide.vo.TransComboBean;
import com.qcmz.cmc.ws.provide.vo.TransComboOrderDetailBean;
import com.qcmz.cmc.ws.provide.vo.UserTransComboBean;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.exception.ParamException;
import com.qcmz.framework.util.BeanUtil;
import com.qcmz.framework.util.DoubleUtil;
import com.qcmz.framework.util.IPUtil;
import com.qcmz.framework.util.MailUtil;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.framework.util.log.Operator;
import com.qcmz.umc.ws.provide.locator.UserMsgWS;
import com.qcmz.umc.ws.provide.vo.UserMsgAddBean;

@Component
public class TransComboProcess {
	@Autowired
	private IWalletAccountService walletAccountService;
	@Autowired
	private IOrderService orderService;
	@Autowired
	private ITransComboOrderService transComboOrderService;
	@Autowired
	private IUserTransComboService userTransComboService;

	@Autowired
	private TransComboMap transComboMap;
	@Autowired
	private OrderProcess orderProcess;
	@Autowired
	private OrderMsgProcess orderMsgProcess;
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 获取有效的翻译套餐
	 * @param transType
	 * @return
	 */
	public List<TransComboBean> findCombo(String transType){
		List<TransComboBean> result = new ArrayList<TransComboBean>();
		
		List<TransComboBean> comboValidList = transComboMap.findValidComboInfo();
		Long now = System.currentTimeMillis();
		for (TransComboBean bean : comboValidList) {
			//时间
			if(now<bean.getStartTime() || now>bean.getEndTime()){
				continue;
			}
			
			//翻译类型，01短文快译02图片翻译03语音翻译04真人口译
			if(!transType.equals(bean.getTransType())){
				continue;
			}
			
			//匹配
			result.add(bean);
		}
		
		return result;
	}
	
	/**
	 * 获取有效的翻译套餐
	 * @param transType
	 * @param comboType
	 * @param sceneId
	 * @param platform
	 * @param version
	 * @return
	 */
	public List<TransComboBean> findCombo(String transType, Integer comboType, Long sceneId, String platform, String version){
		List<TransComboBean> result = new ArrayList<TransComboBean>();
		
		List<TransComboBean> comboValidList = transComboMap.findValidComboInfo();
		
		Long now = System.currentTimeMillis();
		List<CmcComboPlatform> platformList = null;
		boolean matchPlatform = false;
		
		for (TransComboBean bean : comboValidList) {
			//时间
			if(now<bean.getStartTime() || now>bean.getEndTime()){
				continue;
			}
			
			//翻译类型，01短文快译02图片翻译03语音翻译04真人口译
			if(StringUtil.notBlankAndNull(transType)){
				if(!transType.equals(bean.getTransType())){
					continue;
				}
			}
			
			//套餐类型，1口译2课程
			if(comboType!=null){
				if(!comboType.equals(bean.getComboType())){
					continue;
				}
			}
			
			//适用平台
			platformList = transComboMap.findPlatform(bean.getComboId());
			if(platformList!=null && !platformList.isEmpty()){
				matchPlatform = false;
				for (CmcComboPlatform pf : platformList) {
					if((StringUtil.isBlankOrNull(pf.getPlatform()) || pf.getPlatform().equals(platform))
							&& (StringUtil.isBlankOrNull(pf.getMinversion()) || StringUtil.afterOrEqualVersion(version, pf.getMinversion()))
							&& (StringUtil.isBlankOrNull(pf.getMaxversion()) || StringUtil.beforeOrEqualVersion(version, pf.getMaxversion()))){
						matchPlatform = true;
						break;
					}
				}
				if(!matchPlatform) continue;
			}
			
			//场景
			if(sceneId!=null){
				if(bean.getScenes()!=null && !bean.getScenes().isEmpty()){
					boolean matchScene = false;
					for (SceneBean sceneBean : bean.getScenes()) {
						matchScene = sceneBean.getSceneId().equals(sceneId);
						if(matchScene) break;
					}
					if(!matchScene){
						continue;
					}
				}
			}
			
			//匹配
			result.add(bean);
		}
		
		return result;
	}
	

	/**
	 * 获取用户有效的翻译套餐
	 * @param userId
	 * @param transType
	 * @param comboType
	 * @return
	 */
	public List<UserTransComboBean> findUserValidCombo(Long userId, String transType, Integer comboType){
		List<UserTransComboBean> result = new ArrayList<UserTransComboBean>();
		CmcCombo combo = null;
		
		List<CmcUCombo> list = userTransComboService.findUserValidCombo(userId, transType, comboType);
		for (CmcUCombo userCombo : list) {
			combo = transComboMap.getCombo(userCombo.getComboid());
			result.add(BeanConvertUtil.toUserTransComboBean(userCombo, combo));			
		}
		
		return result;
	}
	
	/**
	 * 获取用户翻译套餐
	 * @param userId
	 * @param transType
	 * @param comboType
	 * @return
	 */
	public List<UserTransComboBean> findUserCombo(Long userId, String transType, Integer comboType){
		List<UserTransComboBean> result = new ArrayList<UserTransComboBean>();
		CmcCombo combo = null;
		
		List<CmcUCombo> list = userTransComboService.findUserCombo(userId, transType, comboType);
		for (CmcUCombo userCombo : list) {
			combo = transComboMap.getCombo(userCombo.getComboid());
			result.add(BeanConvertUtil.toUserTransComboBean(userCombo, combo));			
		}
		
		return result;
	}
	
	/**
	 * 获取翻译套餐订单详细
	 * @param orderId
	 * @param userId
	 * @return
	 */
	public TransComboOrderDetailBean getTransComboDetail(String orderId, Long userId){
		TransComboOrderDetailBean result = new TransComboOrderDetailBean();
		BeanUtil.copyProperties(result, orderService.getOrderDetail(orderId, userId));
		
		CmcRCombo orderCombo = transComboOrderService.getCombo(orderId);
		result.setComboTitle(orderCombo.getTitle());
		result.setComboDesc(transComboMap.getCombo(orderCombo.getComboid()).getDescription());		
		result.setStartTime(orderCombo.getStarttime()==null?null:orderCombo.getStarttime().getTime());
		
		CmcUCombo userCombo = userTransComboService.getUserCombo(orderId);
		if(userCombo!=null && userCombo.getUserid().equals(userId)){
			CmcCombo combo = transComboMap.getCombo(userCombo.getComboid());
			result.setUserCombo(BeanConvertUtil.toUserTransComboBean(userCombo, combo));
		}
		
		return result;
	}
	
	/**
	 * 添加翻译套餐订单
	 * @param bean
	 * @param serviceType
	 * @param platform
	 * @param version
	 * @return
	 * @exception ParamException、DataException
	 */
	public OrderCreateResult addCombo(TransComboAddBean bean, String serviceType, String platform, String version){
		//1.校验
		CmcCombo combo = transComboMap.getCombo(bean.getComboId());
		if(combo==null || combo.getStatus().equals(SystemConstants.STATUS_OFF)){
			throw new ParamException("套餐不存在");
		}
		
		//校验金额
		CmcComboPackage pkg = null;
		if(bean.getPkgId()!=null){
			pkg = transComboMap.getPackage(bean.getPkgId());
			if(pkg==null || pkg.getStatus().equals(SystemConstants.STATUS_OFF)){
				throw new ParamException("套餐包不存在");
			}
			if(!pkg.getPrice().equals(bean.getAmount())){
				throw new ParamException("订单总金额有误");
			}
			//购买次数
			if(pkg.getPerbuytimes()!=null){
				Long count = transComboOrderService.getBoughtCount(bean.getUid(), bean.getPkgId());
				if(count>=pkg.getPerbuytimes()){
					throw new DataException("超出可购买数量");
				}
			}
			bean.setNum(pkg.getNum());
		}else{
			if(!DoubleUtil.multiply(combo.getUnitprice(), Double.valueOf(bean.getNum())).equals(bean.getAmount())){
				throw new ParamException("订单总金额有误");
			}
		}
		
		//校验抵扣金额
		if(bean.getWalletAmount()>0){
			CmcWalletAccount account = walletAccountService.getAccount(bean.getUid());
			if(account==null || bean.getWalletAmount()>account.getBalance()){
				throw new DataException("钱包余额不足");
			}
		}
		
		
		//2.创建订单
		String orderId = orderService.newOrderId();
		CmcROrder order = transComboOrderService.addCombo(orderId, bean, combo, pkg, serviceType, platform, version);
		OrderCreateResult result = new OrderCreateResult();
		result.setOrderId(order.getOrderid());
		result.setDealStatus(order.getDealstatus());
		
		//3.创建订单后，预支付处理，失败返回空，客户端重新发起预支付
		if(DictConstant.DICT_ORDER_DEALSTATUS_WAITPAY.equals(order.getDealstatus())
				&& StringUtil.notBlankAndNull(bean.getTradeWay())){
			try {
				OrderPrepayBean prepayBean = new OrderPrepayBean();
				prepayBean.setOrderId(order.getOrderid());
				prepayBean.setUid(order.getUserid());
				prepayBean.setAmount(order.getPayableamount());
				prepayBean.setTradeWay(bean.getTradeWay());
				result.setPrepayResult(orderProcess.prepay(prepayBean, platform));
			} catch (Exception e) {
				logger.error("预支付处理失败："+orderId, e);
			}
		}
		
		return result;
	}
	
	/**
	 * 手工添加用户翻译套餐
	 * @param bean
	 * @param copies 份数
	 * @param oper
	 * @return
	 */
	public String handAddUserCombo(CmcUCombo bean, int copies, Operator oper){
		CmcCombo combo = transComboMap.getCombo(bean.getComboid());
		if(combo==null || combo.getStatus().equals(SystemConstants.STATUS_OFF)){
			throw new ParamException("套餐不存在");
		}
		CmcComboPackage pkg = null;
		if(bean.getPkgid()!=null){
			pkg = transComboMap.getPackage(bean.getPkgid());
			if(pkg==null || pkg.getStatus().equals(SystemConstants.STATUS_OFF)){
				throw new ParamException("套餐包不存在");
			}
			bean.setNum(pkg.getNum());
		}
	
		List<CmcUCombo> list = userTransComboService.addUserCombo(combo, pkg, bean.getUserid(), bean.getNum(), bean.getStarttime(), null, bean.getPayside(), copies);
		
		int size = list.size();
		String comboTitle = list.get(0).getCombotitle();
		
		StringBuilder sb = new StringBuilder();
		if(bean.getUserid()!=null){
			sb.append("给用户【").append(bean.getUserid()).append("】成功添加【").append(size).append("】份【").append(comboTitle).append("】");
			
			//用户通知
			UserMsgAddBean msgBean = new UserMsgAddBean();
			msgBean.setMsgType(61L);	//新套餐
			msgBean.getToUserIds().add(bean.getUserid());
			msgBean.setMsg(comboTitle);			
			UserMsgWS.addMsg(msgBean);
		}else{
			sb.append("成功添加【").append(size).append("】份【").append(comboTitle).append("】，兑换码：");
			StringBuilder sbCode = new StringBuilder();
			for (CmcUCombo userCombo : list) {
				sbCode.append(",").append(userCombo.getExchangecode());
			}
			if(sbCode.length()>0){
				sbCode.deleteCharAt(0);
			}
			sb.append(sbCode);
		}
		return sb.toString();		
	}
	
	/**
	 * 兑换用户套餐
	 * @param uesrId
	 * @param exchangeCode
	 */
	public void exchangeUserCombo(Long uesrId, String exchangeCode){
		//1.校验
		CmcUCombo userCombo = userTransComboService.getUserComboByCode(exchangeCode);
		if(userCombo==null){
			throw new DataException("激活码有误");
		}else if(SystemConstants.STATUS_ON.equals(userCombo.getExchangestatus())){
			throw new DataException("已经激活");
		}
		
		//2.兑换
		userCombo.setUserid(uesrId);
		userCombo.setExchangetime(new Date());
		userCombo.setExchangestatus(SystemConstants.STATUS_ON);
		userTransComboService.saveOrUpdateUserCombo(userCombo);

		//3.用户通知
		UserMsgAddBean msgBean = new UserMsgAddBean();
		msgBean.setMsgType(61L);	//新套餐
		msgBean.getToUserIds().add(uesrId);
		msgBean.setMsg(userCombo.getCombotitle());			
		UserMsgWS.addMsg(msgBean);
	}
	
	/**
	 * 处理翻译套餐订单
	 */
	public void dealComboOrder(){
		List<CmcROrder> orders = orderService.findWaitDeal(DictConstant.DICT_ORDERCAT_PACKAGE, DictConstant.DICT_ORDERTYPE_TRANSCOMBO);
		if(orders.isEmpty()) return;
		
		CmcRCombo orderCombo = null;
		CmcCombo combo = null;
		Operator oper = new Operator(SystemConstants.OPERATOR_DEFAULT, SystemConstants.OPERATOR_DEFAULT, IPUtil.LOCAL_IP);
		String msg = null;
		String mailSubject = null;
		String mailContent = null;
		
		for (CmcROrder order : orders) {
			try {
				orderCombo = transComboOrderService.dealCombo(order.getOrderid());
				
				//获取最新的订单信息
				order = orderService.getOrder(order.getOrderid());
				
				//通知用户
				OrderNotifyThrd.notifyStatus(order, orderCombo.getTitle(), null);
				
				//微信上课套餐
				combo = transComboMap.getCombo(orderCombo.getComboid());
				if(DictConstant.DICT_TRANSCOMBO_SERVICEWAY_WX.equals(combo.getServiceway())){
					//自动添加留言
					if(StringUtil.notBlankAndNull(combo.getServiceaccount())){
						msg = "您好，请添加微信号："+combo.getServiceaccount()+" 预约上课。";
						orderMsgProcess.addCsMsg(order.getOrderid(), msg, oper);
					}
					//通知管理员
					mailSubject = new StringBuilder("新【").append(combo.getTitle()).append("】订单").toString();
					mailContent = new StringBuilder(mailSubject).append("：").append(order.getOrderid()).toString();
					MailUtil.sendSimpleMail(SystemConfig.MANAGER_MAILS, null, mailSubject, mailContent);
				}
			}catch (Exception e) {
				logger.error("处理翻译套餐订单失败："+order.getOrderid(), e);
			}
		}
	}
}
