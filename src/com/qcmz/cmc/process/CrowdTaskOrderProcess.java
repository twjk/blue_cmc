package com.qcmz.cmc.process;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.entity.CmcROrder;
import com.qcmz.cmc.service.ICrowdTaskOrderService;
import com.qcmz.cmc.service.IOrderService;
import com.qcmz.cmc.util.UserUtil;
import com.qcmz.cmc.ws.provide.vo.CrowdTaskOrderAddBean;
import com.qcmz.cmc.ws.provide.vo.OrderCreateResult;
import com.qcmz.cmc.ws.provide.vo.OrderPrepayBean;
import com.qcmz.framework.exception.ParamException;
import com.qcmz.framework.exception.SystemException;
import com.qcmz.framework.util.DoubleUtil;
import com.qcmz.framework.util.FilePathUtil;
import com.qcmz.framework.util.FileServiceUtil;
import com.qcmz.framework.util.FileTypeUtil;
import com.qcmz.framework.util.StringUtil;

/**
 * 众包任务订单处理
 */
@Component
public class CrowdTaskOrderProcess {
	@Autowired
	private IOrderService orderService;
	@Autowired
	private ICrowdTaskOrderService crowdTaskOrderService;
	
	@Autowired
	private OrderProcess orderProcess;
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 创建众包任务订单
	 * @param bean
	 * @param serviceType
	 * @param platform
	 * @param version
	 * @return
	 * @exception ParamException、SystemException
	 */
	public OrderCreateResult addCrowdTask(CrowdTaskOrderAddBean bean, String serviceType, String platform, String version){
		//1.校验
		//订单类型
		if(!DictConstant.DICT_ORDERTYPES_CROWDTASK.contains(bean.getOrderType())){
			throw new ParamException("订单类型有误");
		}
		//订单金额
		double amount = DoubleUtil.multiply(bean.getPrice(), (double)bean.getPeopleNum());
		if(bean.getAmount()==0){
			bean.setAmount(amount);
		}else if(bean.getAmount()!=amount){
			throw new ParamException("订单金额有误");
		}
		
		String orderId = orderService.newOrderId();
		
		//图片文件存储
		String picUrl = null;
		if(bean.getFile()!=null){
			String fileType = FileTypeUtil.getImageType(bean.getFile());
			if(!FileTypeUtil.isImageType(fileType)){
				throw new ParamException("图片格式有误");
			}
			String dirPath = UserUtil.getCrowdTaskOrderBasePath(orderId);
			picUrl = FileServiceUtil.saveOrUploadFile(bean.getFile(), dirPath, FilePathUtil.newFileName(fileType));
			if(StringUtil.isBlankOrNull(picUrl)){
				throw new SystemException("文件保存失败");
			}
		}
		
		//2.创建订单
		CmcROrder order = crowdTaskOrderService.addCrowdTask(orderId, bean, picUrl, serviceType, platform, version);
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
}
