package com.qcmz.cmc.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.dao.IDubOrderDao;
import com.qcmz.cmc.entity.CmcRDub;
import com.qcmz.cmc.entity.CmcRItem;
import com.qcmz.cmc.entity.CmcROrder;
import com.qcmz.cmc.service.IDubOrderService;
import com.qcmz.cmc.service.IDubberService;
import com.qcmz.cmc.service.IOrderService;
import com.qcmz.cmc.ws.provide.vo.DubAddBean;
import com.qcmz.cmc.ws.provide.vo.DubOrderDetailBean;
import com.qcmz.cmc.ws.provide.vo.ProductItemBean;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.exception.ParamException;
import com.qcmz.framework.page.PageBean;
import com.qcmz.umc.ws.provide.locator.UserMap;
import com.qcmz.umc.ws.provide.vo.UserSimpleBean;

@Service
public class DubOrderServiceImpl implements IDubOrderService {
	@Autowired
	private IDubOrderDao dubOrderDao;
	@Autowired
	private IOrderService orderService;
	@Autowired
	private IDubberService dubberService;
	
	/**
	 * 分页获取配音订单，含订单信息、配音员
	 * @param map
	 * @param pageBean<CmcRDub>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean){
		dubOrderDao.queryByMapTerm(map, pageBean);
		
		List<CmcRDub> dubList = (List<CmcRDub>) pageBean.getResultList();
		if(dubList.isEmpty()) return;
		
		//获取用户信息
		Set<Long> userIds = new HashSet<Long>();
		for (CmcRDub dub : dubList) {
			userIds.add(dub.getUserid());				
		}
		Map<Long, UserSimpleBean> userMap = UserMap.findUser(userIds);
		for (CmcRDub dub : dubList) {
			dub.setUser(userMap.get(dub.getUserid()));
		}
	}
	
	/**
	 * 分页获取处理配音订单列表，含订单、配音员
	 * @param map
	 * @param pageBean<CmcRDub>
	 */
	@SuppressWarnings("unchecked")
	public void findDeal(Map<String, String> map, PageBean pageBean){
		dubOrderDao.findDeal(map, pageBean);
		
		List<CmcRDub> dubList = (List<CmcRDub>) pageBean.getResultList();
		if(dubList.isEmpty()) return;
		
		//获取用户信息
		Set<Long> userIds = new HashSet<Long>();
		for (CmcRDub dub : dubList) {
			userIds.add(dub.getUserid());				
		}
		Map<Long, UserSimpleBean> userMap = UserMap.findUser(userIds);
		for (CmcRDub dub : dubList) {
			dub.setUser(userMap.get(dub.getUserid()));
		}
	}
	
	/**
	 * 获取配音订单信息，含配音、配音员、操作日志、留言
	 * @param orderId
	 * @return
	 * 修改历史：
	 */
	public CmcROrder getOrderJoin(String orderId){
		CmcROrder order = orderService.getOrderJoin(orderId);
		order.setCmcRDub(getDub(orderId));
		if(order.getCmcRDub().getDubberid()!=null){
			dubberService.getDubber(order.getCmcRDub().getDubberid());
		}
		order.setUser(UserMap.getUser(order.getUserid()));
		return order;
	} 
	
	/**
	 * 获取配音订单配音信息
	 * @param orderId
	 * @return
	 */
	public CmcRDub getDub(String orderId){
		return dubOrderDao.getDub(orderId);
	}
	
	/**
	 * 获取配音订单详细信息
	 * @param orderId
	 * @param userId
	 * @return
	 * @exception ParamException
	 */
	public DubOrderDetailBean getDetail(String orderId, Long userId){
		//订单信息
		DubOrderDetailBean result = new DubOrderDetailBean(orderService.getOrderDetail(orderId, userId));
		
		//配音信息
		CmcRDub cmcRDub = dubOrderDao.getDubJoin(orderId);
		result.setTxt(cmcRDub.getTxt());
		result.setTitle(cmcRDub.getTitle());
		result.setDubberTitle(cmcRDub.getCmcDubber().getTitle());
		
		return result;
	}
	
	/**
	 * 创建配音订单
	 * @param orderId
	 * @param bean
	 * @param item
	 * @param serviceType
	 * @param platform
	 * @param version
	 * @return
	 */
	public CmcROrder addDub(String orderId, DubAddBean bean, ProductItemBean item, String serviceType, String platform, String version){
		CmcROrder order = new CmcROrder();
		order.setOrderid(orderId);
		order.setUserid(bean.getUid());
		order.setUsertype(bean.getUserType());
		order.setEmployeeid(bean.getEmployeeId());
		order.setEmployeename(bean.getEmployeeName());
		order.setOrdercat(DictConstant.DICT_ORDERCAT_DUB);
		if(bean.getDubberId()!=null){
			order.setOrdertype(DictConstant.DICT_ORDERTYPE_HUMANDUB);
		}else{
			order.setOrdertype(DictConstant.DICT_ORDERTYPE_DUB);
		}
		order.setAmount(bean.getAmount());
		order.setCouponamount(0.0);
		order.setWalletamount(0.0);
		order.setPayableamount(bean.getAmount());
		order.setTradeway(bean.getTradeWay());
		order.setRequirement(bean.getRequirement());
		order.setMobile(bean.getMobile());
		order.setCreatetime(new Date());
		order.setServicetype(serviceType);
		order.setPlatform(platform);
		order.setVersion(version);
		order.setDealstatus(DictConstant.DICT_ORDER_DEALSTATUS_WAITPAY);
		order.setStatus(SystemConstants.STATUS_ON);
		
		CmcRItem ritem = new CmcRItem();
		ritem.setOrderid(orderId);
		ritem.setItemid(item.getItemId());
		ritem.setItemname(item.getItemName());
		ritem.setOriprice(item.getOriPrice());
		ritem.setPrice(item.getPrice());
		
		CmcRDub cmcRDub = new CmcRDub();
		cmcRDub.setOrderid(orderId);
		cmcRDub.setUserid(bean.getUid());
		cmcRDub.setTitle(bean.getTitle());
		cmcRDub.setTxt(bean.getTxt());
		cmcRDub.setDubberid(bean.getDubberId());
		
		//信息入库
		orderService.saveOrder(order, ritem);
		
		dubOrderDao.save(cmcRDub);
		
		return order;
	}
}
