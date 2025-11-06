package com.qcmz.cmc.web.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.jsp.dictionary.bean.DictionaryBean;
import com.qcmz.cmc.cache.DictMap;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.entity.CmcROrder;
import com.qcmz.cmc.process.OrderMsgProcess;
import com.qcmz.cmc.process.OrderProcess;
import com.qcmz.cmc.service.ICrowdTaskOrderService;
import com.qcmz.cmc.service.IDubOrderService;
import com.qcmz.cmc.service.IOrderService;
import com.qcmz.cmc.service.IPackageOrderService;
import com.qcmz.cmc.service.ITransComboOrderService;
import com.qcmz.cmc.service.ITransPicService;
import com.qcmz.cmc.service.ITransTextService;
import com.qcmz.cmc.service.ITransVideoService;
import com.qcmz.cmc.util.BeanConvertUtil;
import com.qcmz.cmc.vo.TransPicIPictureBean;
import com.qcmz.framework.action.BaseAction;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.exception.ProxyException;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.IPUtil;
import com.qcmz.framework.util.JsonUtil;
import com.qcmz.framework.util.log.Operator;
import com.qcmz.srm.vo.UserBean;

/**
 * 类说明：订单管理
 * 修改历史：
 */
public class OrderAction extends BaseAction {
	@Autowired
	private IOrderService orderService;
	@Autowired
	private ITransTextService transTextService;
	@Autowired
	private ITransPicService transPicService;
	@Autowired
	private ITransVideoService transVideoService;
	@Autowired
	private IPackageOrderService packageOrderService;
	@Autowired
	private ITransComboOrderService transComboOrderService;
	@Autowired
	private IDubOrderService dubOrderService;
	@Autowired
	private ICrowdTaskOrderService crowdTaskOrderService;
	
	@Autowired
	private OrderProcess orderProcess;
	@Autowired
	private OrderMsgProcess orderMsgProcess;
	
	/**
	 * 订单编号
	 */
	private String orderId;
	/**
	 * 订单分类
	 */
	private Integer ordercat;
	/**
	 * 订单类型
	 */
	private Integer orderType;
	/**
	 * 订单类型字典
	 */
	private List<DictionaryBean> orderTypeDict;
	/**
	 * 取消原因
	 */
	private String reason;
	/**
	 * 留言
	 */
	private String msg;
	/**
	 * 源语言
	 */
	private String from;
	/**
	 * 目标语言
	 */
	private String to;
	
	/**
	 * 分页
	 */
	private PageBean pageBean;
	/**
	 * 订单
	 */
	private CmcROrder order;
	/**
	 * 标注信息json串
	 */
	private String ipictureJson;
	
	/**
	 * 页面初始化
	 */
	public String doInit(){
		orderTypeDict = new ArrayList<DictionaryBean>();
		if(DictConstant.DICT_ORDERCAT_SUB.equals(ordercat)){
			for (Integer orderType : DictConstant.DICT_ORDERTYPES_SUB) {
				orderTypeDict.add(new DictionaryBean("orderType", String.valueOf(orderType), DictMap.getOrderTypeMean(orderType)));
			}
		}
		return QUERY;
	}
	
	/**
	 * 查询
	 * @return
	 * 修改历史：
	 */
	public String doQuery(){
		pageBean = new PageBean(request.getParameter("page"), request.getParameter("pageSize"));

		orderService.queryByMapTerm(getParameterMap(), pageBean);
		
		return LIST;
	}
	
	/**
	 * 详细
	 */
	public String doDetail(){
		String result = null;
		
		if(DictConstant.DICT_ORDERTYPE_TRANSPIC.equals(orderType)){
			//图片翻译
			order = transPicService.getOrderJoin(orderId);
			
			TransPicIPictureBean ipicture = new TransPicIPictureBean();
			ipicture.setPicture1(BeanConvertUtil.toTransPicIPictureTransBean(order.getCmcPtPic().getTrans()));
			ipictureJson = JsonUtil.object2String(ipicture);
					
			result = "detail_pic";			
		}else if(DictConstant.DICT_ORDERTYPE_TRANSTEXT.equals(orderType)){
			//文本翻译
			order = transTextService.getOrderJoin(orderId);
			result = "detail_text";
		}else if(DictConstant.DICT_ORDERTYPE_TRANSVIDEO.equals(orderType)){
			//真人口译
			order = transVideoService.getOrderJoin(orderId);
			result = "detail_video";
		}else if(DictConstant.DICT_ORDERTYPE_PACKAGE.equals(orderType)){
			//优惠券包
			order = packageOrderService.getOrderJoin(orderId);
			result = "detail_package";
		}else if(DictConstant.DICT_ORDERTYPE_TRANSCOMBO.equals(orderType)){
			//翻译套餐
			order = transComboOrderService.getOrderJoin(orderId);
			result = "detail_transcombo";
		}else if(DictConstant.DICT_ORDERTYPE_DUB.equals(orderType)
				|| DictConstant.DICT_ORDERTYPE_HUMANDUB.equals(orderType)){
			//配音
			order = dubOrderService.getOrderJoin(orderId);
			result = "detail_dub";
		}else if(DictConstant.DICT_ORDERTYPES_CROWDTASK.contains(orderType)){
			//众包任务
			order = crowdTaskOrderService.getOrderJoin(orderId);
			result = "detail_crowdtask";
		}else{
			//其他订单
			order = orderService.getOrderJoin(orderId);
			result = DETAIL;
		}

		return result;
	}
	
	
	/**
	 * 取消订单
	 */
	public String doCancel(){
		UserBean user = (UserBean) request.getSession().getAttribute(SystemConstants.SESSION_USER);	
		Operator oper = new Operator(user.getUsername(), user.getName(), IPUtil.getRemoteAddr(request));
		
		try {
			orderProcess.rejectOrderAndRefund(orderId, reason, oper);
		} catch(DataException e){
			handleResult = false;
			setResult(e.getMessage());
		} catch (Exception e) {
			logger.error("取消失败",e);
			handleResult = false;
			setResult("取消失败");
		}
		
		//返回
		print();
		
		return null;
	}
	
	/**
	 * 接单，开始处理订单
	 */
	public String doAccept(){
		UserBean user = (UserBean) request.getSession().getAttribute(SystemConstants.SESSION_USER);	
		Operator oper = new Operator(user.getUsername(), user.getName(), IPUtil.getRemoteAddr(request));
		
		try {
			orderProcess.acceptOrder(orderId, oper);
		} catch(DataException ae){
			handleResult = false;
			setResult(ae.getMessage());
		} catch (Exception e) {
			logger.error("接单失败",e);
			handleResult = false;
			setResult("接单失败");
		}
		
		//返回
		print();
		
		return null;
	}
	
	/**
	 * 交单
	 */
	public String doHandover(){
		UserBean user = (UserBean) request.getSession().getAttribute(SystemConstants.SESSION_USER);	
		Operator oper = new Operator(user.getUsername(), user.getName(), IPUtil.getRemoteAddr(request));
		
		try {
			orderService.handoverOrder(orderId, from, to, oper);
		} catch(DataException ae){
			handleResult = false;
			setResult(ae.getMessage());
		} catch (Exception e) {
			logger.error("交单失败",e);
			handleResult = false;
			setResult("交单失败");
		}
		
		//返回
		print();
		
		return null;
	}
	
	/**
	 * 同步支付结果
	 */
	public String doSynPay(){
		try {
			orderProcess.synPay(orderId);
		} catch(ProxyException ae){
			handleResult = false;
			setResult(ae.getMessage());
		} catch (Exception e) {
			logger.error("同步支付结果失败",e);
			handleResult = false;
			setResult("同步支付结果失败");
		}
		
		//返回
		print();
		
		return null;
	} 
	
	/**
	 * 同步退款结果
	 */
	public String doSynRefund(){
		try {
			orderProcess.synRefund(orderId);
		} catch(ProxyException ae){
			handleResult = false;
			setResult(ae.getMessage());
		} catch (Exception e) {
			logger.error("同步退款结果失败",e);
			handleResult = false;
			setResult("同步退款结果失败");
		}
		
		//返回
		print();
		
		return null;
	} 
	
	/**
	 * 退款
	 */
	public String doRefund(){
		boolean refund = false;
		try {
			UserBean user = (UserBean) request.getSession().getAttribute(SystemConstants.SESSION_USER);	
			Operator oper = new Operator(user.getUsername(), IPUtil.getRemoteAddr(request));
			
			refund = orderProcess.refund(orderId, oper);
		} catch (Exception e) {
			logger.error("退款失败",e);
		}
		
		if(!refund){
			handleResult = false;
			setResult("退款失败");
		}
		
		//返回
		print();
		
		return null;
	}
	
	/**
	 * 完成订单
	 */
	public String doFinish(){
		UserBean user = (UserBean) request.getSession().getAttribute(SystemConstants.SESSION_USER);	
		Operator oper = new Operator(user.getUsername(), user.getName(), IPUtil.getRemoteAddr(request));
		
		try {
			orderProcess.finishOrder(orderId, oper);
		} catch(DataException ae){
			handleResult = false;
			setResult(ae.getMessage());
		} catch (Exception e) {
			logger.error("完成订单失败",e);
			handleResult = false;
			setResult("完成订单失败");
		}
		
		//返回
		print();
		
		return null;
	}
	
	/**
	 * 添加留言
	 * @return
	 * 修改历史：
	 */
	public String doAddMsg(){
		UserBean user = (UserBean) request.getSession().getAttribute(SystemConstants.SESSION_USER);	
		Operator oper = new Operator(user.getUsername(), user.getName(), IPUtil.getRemoteAddr(request));
		
		try {
			orderMsgProcess.addCsMsg(orderId, msg, oper);
		} catch (Exception e) {
			logger.error("保存信息失败",e);
			handleResult = false;
			setResult("保存信息失败");
		}
		
		print();

		return null;
	}
	
	public PageBean getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}

	public String getPicId() {
		return orderId;
	}

	public void setPicId(String picId) {
		this.orderId = picId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	public Integer getOrdercat() {
		return ordercat;
	}

	public void setOrdercat(Integer ordercat) {
		this.ordercat = ordercat;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public List<DictionaryBean> getOrderTypeDict() {
		return orderTypeDict;
	}

	public void setOrderTypeDict(List<DictionaryBean> orderTypeDict) {
		this.orderTypeDict = orderTypeDict;
	}

	public CmcROrder getOrder() {
		return order;
	}

	public void setOrder(CmcROrder order) {
		this.order = order;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getIpictureJson() {
		return ipictureJson;
	}

	public void setIpictureJson(String ipictureJson) {
		this.ipictureJson = ipictureJson;
	}
}