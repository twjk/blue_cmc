package com.qcmz.cmc.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.cache.TransComboMap;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.dao.ITransComboOrderDao;
import com.qcmz.cmc.entity.CmcCombo;
import com.qcmz.cmc.entity.CmcComboPackage;
import com.qcmz.cmc.entity.CmcRCombo;
import com.qcmz.cmc.entity.CmcRItem;
import com.qcmz.cmc.entity.CmcROrder;
import com.qcmz.cmc.service.IOrderLogService;
import com.qcmz.cmc.service.IOrderService;
import com.qcmz.cmc.service.ITransComboOrderService;
import com.qcmz.cmc.service.IUserTransComboService;
import com.qcmz.cmc.service.IWalletConsumeService;
import com.qcmz.cmc.util.OrderUtil;
import com.qcmz.cmc.util.TransComboUtil;
import com.qcmz.cmc.ws.provide.vo.TransComboAddBean;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.DoubleUtil;
import com.qcmz.umc.ws.provide.locator.UserMap;
import com.qcmz.umc.ws.provide.vo.UserSimpleBean;

@Service
public class TransComboOrderServiceImpl implements ITransComboOrderService {
	@Autowired
	private ITransComboOrderDao transComboOrderDao;
	
	@Autowired
	private IUserTransComboService userTransComboService;
	@Autowired
	private IWalletConsumeService walletConsumeService;
	@Autowired
	private IOrderService orderService;
	@Autowired
	private IOrderLogService orderLogService;
	
	@Autowired
	private TransComboMap transComboMap;
	
	/**
	 * 分页获取优惠套餐，含订单信息
	 * @param map
	 * @param pageBean<CmcRCombo>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean){
		transComboOrderDao.queryByMapTerm(map, pageBean);
		
		List<CmcRCombo> comboOrderList = (List<CmcRCombo>) pageBean.getResultList();
		if(comboOrderList.isEmpty()) return;
		
		//获取用户信息
		Set<Long> userIds = new HashSet<Long>();
		for (CmcRCombo rc : comboOrderList) {
			userIds.add(rc.getUserid());				
		}
		Map<Long, UserSimpleBean> userMap = UserMap.findUser(userIds);
		for (CmcRCombo orderCombo : comboOrderList) {
			orderCombo.setUser(userMap.get(orderCombo.getUserid()));
		}
	}
	
	/**
	 * 获取用户已购买套餐订单数
	 * @param userId
	 * @param pkgId
	 * @return
	 */
	public Long getBoughtCount(Long userId, Long pkgId){
		return transComboOrderDao.getBoughtCount(userId, pkgId);
	}
	
	/**
	 * 获取套餐订单信息，含套餐、操作日志、留言
	 * @param orderId
	 * @return
	 * 修改历史：
	 */
	public CmcROrder getOrderJoin(String orderId){
		CmcROrder order = orderService.getOrderJoin(orderId);
		order.setCmcRCombo(getCombo(orderId));
		order.setUser(UserMap.getUser(order.getUserid()));
		return order;
	} 
	
	/**
	 * 获取套餐订单信息
	 * @param orderId
	 * @return
	 */
	public CmcRCombo getCombo(String orderId){
		return transComboOrderDao.getCombo(orderId);
	}
	
	/**
	 * 添加套餐订单
	 * @param orderId
	 * @param bean
	 * @param combo
	 * @param pkg
	 * @param serviceType
	 * @param platform
	 * @param version
	 * @return
	 */
	public CmcROrder addCombo(String orderId, TransComboAddBean bean, CmcCombo combo, CmcComboPackage pkg, String serviceType, String platform, String version){
		Date now = new Date();
		Date finishTime = null;
		String dealStatus = null;
		
		double payableAmount = bean.getAmount();
		double walletAmount = bean.getWalletAmount();
		if(payableAmount>0 && walletAmount>0){
			if(walletAmount<payableAmount){
				payableAmount = DoubleUtil.subtract(payableAmount, bean.getWalletAmount());
			}else{
				walletAmount = payableAmount;
				payableAmount = 0;
			}
			
			//钱包抵扣
			walletConsumeService.consume(bean.getUid(), walletAmount, DictConstants.DICT_SUBSERVICETYPE_TRANSCOMBO, orderId);
		}
		
		if(DictConstants.DICT_TRADEWAY_POSTPAY.equals(bean.getTradeWay())){
			dealStatus = DictConstant.DICT_ORDER_DEALSTATUS_FINISH;
			finishTime = now;
		}else if(payableAmount==0){
			dealStatus = DictConstant.DICT_ORDER_DEALSTATUS_PAID;
		}else{
			dealStatus = DictConstant.DICT_ORDER_DEALSTATUS_WAITPAY;
		}
		
		//组装数据
		CmcROrder order = new CmcROrder();
		order.setOrderid(orderId);
		order.setUserid(bean.getUid());
		order.setUsertype(bean.getUserType());
		order.setEmployeeid(bean.getEmployeeId());
		order.setEmployeename(bean.getEmployeeName());
		order.setOrdercat(DictConstant.DICT_ORDERCAT_PACKAGE);
		order.setOrdertype(DictConstant.DICT_ORDERTYPE_TRANSCOMBO);
		order.setRequirement(null);
		order.setAmount(bean.getAmount());
		order.setCouponamount(0.0);
		order.setWalletamount(walletAmount);
		order.setPayableamount(payableAmount);
		order.setTradeway(bean.getTradeWay());
		order.setCreatetime(now);
		order.setWaittime(now);
		order.setFinishtime(finishTime);
		order.setServicetype(serviceType);
		order.setPlatform(platform);
		order.setVersion(version);
		order.setDealstatus(dealStatus);
		order.setDealprogress(OrderUtil.dealStatus2ProgressStatus(order.getDealstatus()));
		order.setStatus(SystemConstants.STATUS_ON);
		
		String title = null;
		if(pkg!=null){
			title = TransComboUtil.getComboTitle(combo.getTitle(), pkg.getPkgtitle());
		}else{
			title = TransComboUtil.getComboTitle(combo.getTitle(), bean.getNum(), combo.getUnitname());
		}
		
		CmcRItem ritem = new CmcRItem();
		ritem.setOrderid(orderId);
		ritem.setPic(combo.getIcon());
		ritem.setItemid(combo.getComboid());
		if(pkg==null){
			ritem.setItemname(combo.getTitle());
			ritem.setPrice(combo.getUnitprice());
			ritem.setOriprice(combo.getOriunitprice());
		}else{
			ritem.setItemname(title);
			ritem.setOriprice(DoubleUtil.multiply(combo.getOriunitprice(), Double.valueOf(bean.getNum())));
			ritem.setPrice(pkg.getPrice());
		}
		
		CmcRCombo rc = new CmcRCombo();
		rc.setOrderid(orderId);
		rc.setUserid(order.getUserid());
		rc.setComboid(combo.getComboid());
		if(pkg!=null){
			rc.setPkgid(pkg.getPkgid());
		}
		rc.setTitle(title);
		rc.setNum(bean.getNum());
		rc.setUnit(combo.getPriceunit());
		if(bean.getStartTime()!=null){
			rc.setStarttime(DateUtil.setMinTime(new Date(bean.getStartTime())));
		}
		
		//信息入库
		transComboOrderDao.save(order);
		transComboOrderDao.save(ritem);
		transComboOrderDao.save(rc);
		
		//操作
		orderLogService.saveFlowLog(order.getOrderid(), order.getDealstatus(), String.valueOf(order.getUserid()), SystemConstants.OPERATOR_USER, order.getCreatetime(),null);
		
		return order;
	}
	
	/**
	 * 处理套餐订单，生成用户翻译套餐
	 * @param orderId
	 */
	public CmcRCombo dealCombo(String orderId){
		CmcRCombo orderCombo = transComboOrderDao.getCombo(orderId);
		CmcCombo combo = transComboMap.getCombo(orderCombo.getComboid());
		CmcComboPackage pkg = transComboMap.getPackage(orderCombo.getPkgid());
		userTransComboService.addUserCombo(combo, pkg, orderCombo.getUserid(), orderCombo.getNum(), orderCombo.getStarttime(), orderId, DictConstant.DICT_PAYSIDE_USER, 1);
		
		orderService.saveOrderFinish(orderId, null, SystemConstants.OPERATOR_DEFAULT, SystemConstants.OPERATOR_DEFAULT);
		
		return orderCombo;
	}
}
