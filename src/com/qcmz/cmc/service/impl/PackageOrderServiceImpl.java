package com.qcmz.cmc.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.dao.IPackageOrderDao;
import com.qcmz.cmc.entity.CmcRItem;
import com.qcmz.cmc.entity.CmcROrder;
import com.qcmz.cmc.entity.CmcRPackage;
import com.qcmz.cmc.service.IOrderLogService;
import com.qcmz.cmc.service.IOrderService;
import com.qcmz.cmc.service.IPackageOrderService;
import com.qcmz.cmc.service.IWalletConsumeService;
import com.qcmz.cmc.util.OrderUtil;
import com.qcmz.cmc.ws.provide.vo.PackageOrderAddBean;
import com.qcmz.cmc.ws.provide.vo.PackageOrderDetailBean;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.BeanUtil;
import com.qcmz.framework.util.DoubleUtil;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.umc.ws.provide.locator.UserMap;
import com.qcmz.umc.ws.provide.vo.ActivityBean;
import com.qcmz.umc.ws.provide.vo.UserSimpleBean;

@Service
public class PackageOrderServiceImpl implements IPackageOrderService {
	@Autowired
	private IPackageOrderDao packageOrderDao;
	
	@Autowired
	private IWalletConsumeService walletConsumeService;
	@Autowired
	private IOrderService orderService;
	@Autowired
	private IOrderLogService orderLogService;
	
	/**
	 * 分页获取优惠套餐，含订单信息
	 * @param map
	 * @param pageBean<CmcRPackage>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean){
		packageOrderDao.queryByMapTerm(map, pageBean);
		
		List<CmcRPackage> pkgs = (List<CmcRPackage>) pageBean.getResultList();
		if(pkgs.isEmpty()) return;
		
		//获取用户信息
		Set<Long> userIds = new HashSet<Long>();
		for (CmcRPackage pkg : pkgs) {
			userIds.add(pkg.getUserid());
			if(pkg.getExchangeuserid()!=null){
				userIds.add(pkg.getExchangeuserid());
			}
		}
		
		Map<Long, UserSimpleBean> userMap = UserMap.findUser(userIds);
		for (CmcRPackage pkg : pkgs) {
			pkg.setUser(userMap.get(pkg.getUserid()));
			if(pkg.getExchangeuserid()!=null){
				pkg.setExchangeUser(userMap.get(pkg.getExchangeuserid()));
			}
		}
	}
	
	/**
	 * 获取优惠券包订单信息，含券包、操作日志、留言
	 * @param orderId
	 * @return
	 * 修改历史：
	 */
	public CmcROrder getOrderJoin(String orderId){
		CmcROrder order = orderService.getOrderJoin(orderId);
		CmcRPackage pkg = getPackage(orderId);
		order.setCmcRPackage(pkg);
		
		Set<Long> userIds = new HashSet<Long>();
		userIds.add(pkg.getUserid());
		if(pkg.getExchangeuserid()!=null){
			userIds.add(pkg.getExchangeuserid());
		}
		
		//获取用户信息
		Map<Long, UserSimpleBean> userMap = UserMap.findUser(userIds);
		pkg.setUser(userMap.get(pkg.getUserid()));
		if(pkg.getExchangeuserid()!=null){
			pkg.setExchangeUser(userMap.get(pkg.getExchangeuserid()));
		}
				
		return order;
	}
	
	/**
	 * 获取套餐订单详细信息
	 * @param orderId
	 * @param userId
	 * @return
	 */
	public PackageOrderDetailBean getPackageDetail(String orderId, Long userId){
		PackageOrderDetailBean result = new PackageOrderDetailBean();
		BeanUtil.copyProperties(result, orderService.getOrderDetail(orderId, userId));
		
		CmcRPackage pkg = getPackage(orderId);
		result.setActTitle(pkg.getActtitle());
		result.setActDesc(pkg.getActdesc());		
		result.setStartTime(pkg.getStarttime()==null?null:pkg.getStarttime().getTime());
		return result;
	}
	
	/**
	 * 获取套餐信息
	 */
	public CmcRPackage getPackage(String orderId){
		return packageOrderDao.getPackage(orderId);
	}
	
	/**
	 * 获取套餐信息
	 */
	public CmcRPackage getPackage(Long pkgId){
		return (CmcRPackage) packageOrderDao.load(CmcRPackage.class, pkgId);
	}
	
	/**
	 * 创建优惠套餐订单
	 * @param orderId
	 * @param bean
	 * @param activity
	 * @param serviceType
	 * @param platform
	 * @param version
	 * @return
	 */
	public CmcROrder addPackage(String orderId, PackageOrderAddBean bean, ActivityBean activity, String exchangeCode, String serviceType, String platform, String version){
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
			walletConsumeService.consume(bean.getUid(), walletAmount, DictConstants.DICT_SUBSERVICETYPE_ACTIVITYPACKAGE, orderId);
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
		order.setOrdertype(DictConstant.DICT_ORDERTYPE_PACKAGE);
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
		
		CmcRItem ritem = new CmcRItem();
		ritem.setOrderid(orderId);
		ritem.setItemid(activity.getActId());
		ritem.setItemname(activity.getTitle());
		ritem.setOriprice(activity.getOriPrice());
		ritem.setPrice(activity.getPrice());
		
		CmcRPackage pkg = new CmcRPackage();
		pkg.setOrderid(orderId);
		pkg.setUserid(order.getUserid());
		pkg.setActid(activity.getActId());
		pkg.setActtitle(activity.getTitle());
		pkg.setActdesc(activity.getDescription());
		pkg.setExchangecode(exchangeCode);
		if(StringUtil.notBlankAndNull(exchangeCode)){
			pkg.setExchangestatus(SystemConstants.STATUS_OFF);
		}
		if(bean.getStartTime()!=null){
			pkg.setStarttime(new Date(bean.getStartTime()));
		}
		
		//信息入库
		packageOrderDao.save(order);
		packageOrderDao.save(ritem);
		packageOrderDao.save(pkg);
		
		//操作
		orderLogService.saveFlowLog(order.getOrderid(), order.getDealstatus(), String.valueOf(order.getUserid()), SystemConstants.OPERATOR_USER, order.getCreatetime(),null);
		
		return order;
	}
	
	/**
	 * 更新套餐订单
	 * @param entity
	 */
	public void updatePackage(CmcRPackage entity){
		packageOrderDao.update(entity);
	}
	
	/**
	 * 更新兑换码
	 * @param pkgId
	 * @param exchangeCode
	 */
	public void updateExchangeCode(Long pkgId, String exchangeCode){
		CmcRPackage entity = getPackage(pkgId);
		entity.setExchangecode(exchangeCode);
		entity.setExchangestatus(SystemConstants.STATUS_OFF);
		packageOrderDao.update(entity);
	}
	
	/**
	 * 保存兑换结果，并将订单状态置为已完成
	 * @param orderId
	 * @param userId
	 * @param exchangeTime
	 */
	public void saveExchangeSuccess(String orderId, Long userId, Date exchangeTime){
		CmcRPackage pkg = getPackage(orderId);
		pkg.setExchangeuserid(userId);
		pkg.setExchangestatus(SystemConstants.STATUS_ON);
		pkg.setExchangetime(exchangeTime);
		packageOrderDao.update(pkg);
		
		orderService.saveOrderFinish(orderId, null, null, String.valueOf(userId), SystemConstants.OPERATOR_USER);
	}
}
