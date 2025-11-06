package com.qcmz.cmc.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.entity.CmcRItem;
import com.qcmz.cmc.entity.CmcROrder;
import com.qcmz.cmc.service.IArticleOrderService;
import com.qcmz.cmc.service.IOrderService;
import com.qcmz.cmc.service.IWalletConsumeService;
import com.qcmz.cmc.ws.provide.vo.ArticleSubBean;
import com.qcmz.cms.ws.provide.vo.ArticleItemBean;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.util.DoubleUtil;
import com.qcmz.framework.util.StringUtil;

/**
 * @author Administrator
 *
 */
@Service
public class ArticleOrderServiceImpl implements IArticleOrderService {
	@Autowired
	private IOrderService orderService;
	@Autowired
	private IWalletConsumeService walletConsumeService;
	
	/**
	 * 资讯订阅
	 * @param orderId
	 * @param bean
	 * @param item
	 * @param serviceType
	 * @param platform
	 * @param version
	 * @return
	 */
	public CmcROrder subArticle(String orderId, ArticleSubBean bean, ArticleItemBean item, String serviceType, String subServiceType, Integer orderType, String platform, String version){
		double payableAmount = bean.getAmount();
		
		//优惠券
		Double couponAmount = bean.getCouponAmount();
		if(couponAmount<bean.getAmount()){
			payableAmount = DoubleUtil.subtract(bean.getAmount(), bean.getCouponAmount());	//应付金额
		}else{
			couponAmount = bean.getAmount();
			payableAmount = 0;			
		}
		
		//钱包抵扣
		double walletAmount = bean.getWalletAmount();
		if(payableAmount>0 && walletAmount>0){
			if(walletAmount<payableAmount){
				payableAmount = DoubleUtil.subtract(payableAmount, bean.getWalletAmount());
			}else{
				walletAmount = payableAmount;
				payableAmount = 0;
			}
			
			//钱包抵扣
			walletConsumeService.consume(bean.getUid(), walletAmount, subServiceType, orderId);
		}
		
		String dealStatus = DictConstant.DICT_ORDER_DEALSTATUS_WAITPAY; 
		if(payableAmount==0){
			dealStatus = DictConstant.DICT_ORDER_DEALSTATUS_WAITDEAL;
		}
		
		CmcROrder order = new CmcROrder();
		order.setOrderid(orderId);
		order.setUserid(bean.getUid());
		order.setUsertype(bean.getUserType());
		order.setEmployeeid(bean.getEmployeeId());
		order.setEmployeename(bean.getEmployeeName());
		order.setOrdercat(DictConstant.DICT_ORDERCAT_SUB);
		order.setOrdertype(orderType);
		order.setIapid(item.getIapId());
		order.setAmount(bean.getAmount());
		order.setCouponamount(couponAmount);
		order.setWalletamount(walletAmount);
		order.setPayableamount(payableAmount);
		order.setInvitecode(bean.getInviteCode());
		order.setCreatetime(new Date());
		order.setServicetype(serviceType);
		order.setPlatform(platform);
		order.setVersion(version);
		order.setDealstatus(dealStatus);
		order.setStatus(SystemConstants.STATUS_ON);
		
		if(payableAmount==0){
			order.setDealprogress(DictConstants.DICT_DEALPROGRESS_WAITING);
			order.setWaittime(new Date());
		}
		
		CmcRItem ritem = new CmcRItem();
		ritem.setOrderid(orderId);
		ritem.setItemid(bean.getItemId());
		if(StringUtil.notBlankAndNull(bean.getItemName())){
			ritem.setItemname(bean.getItemName());
		}else{
			ritem.setItemname(item.getItemName());
		}
		if(bean.getArticleId()!=null){
			ritem.setRefid(String.valueOf(bean.getArticleId()));
		}
		ritem.setOriprice(item.getOriPrice());
		ritem.setPrice(item.getPrice());
		ritem.setPic(item.getPic());
		ritem.setItemnum(1);
		
		orderService.saveOrder(order, ritem);
		
		return order;
	}
}
