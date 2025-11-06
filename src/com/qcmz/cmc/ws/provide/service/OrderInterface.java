package com.qcmz.cmc.ws.provide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.process.OrderMsgProcess;
import com.qcmz.cmc.process.OrderProcess;
import com.qcmz.cmc.service.IOrderMsgService;
import com.qcmz.cmc.service.IOrderService;
import com.qcmz.cmc.ws.provide.vo.ApplepaySynReq;
import com.qcmz.cmc.ws.provide.vo.OrderCancelReq;
import com.qcmz.cmc.ws.provide.vo.OrderDelReq;
import com.qcmz.cmc.ws.provide.vo.OrderDetailResp;
import com.qcmz.cmc.ws.provide.vo.OrderEvalDetailResp;
import com.qcmz.cmc.ws.provide.vo.OrderEvalGetReq;
import com.qcmz.cmc.ws.provide.vo.OrderEvalReq;
import com.qcmz.cmc.ws.provide.vo.OrderGetReq;
import com.qcmz.cmc.ws.provide.vo.OrderMsgAddReq;
import com.qcmz.cmc.ws.provide.vo.OrderMsgQueryReq;
import com.qcmz.cmc.ws.provide.vo.OrderMsgQueryResp;
import com.qcmz.cmc.ws.provide.vo.OrderPrepayReq;
import com.qcmz.cmc.ws.provide.vo.OrderQueryReq;
import com.qcmz.cmc.ws.provide.vo.OrderQueryResp;
import com.qcmz.cmc.ws.provide.vo.PrepayResp;
import com.qcmz.framework.constant.ExceptionConstants;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.exception.ParamException;
import com.qcmz.framework.exception.ProxyException;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.framework.ws.BaseInterface;
import com.qcmz.framework.ws.vo.Response;
import com.qcmz.srm.client.util.SrmClient;

/**
 * 订单相关接口
 */
@Component
public class OrderInterface extends BaseInterface {
	@Autowired
	private IOrderMsgService orderMsgService;
	@Autowired
	private IOrderService orderService;

	@Autowired
	private OrderProcess orderProcess;
	@Autowired
	private OrderMsgProcess orderMsgProcess;
	
	/**
	 * 分页查询订单列表
	 * @param req
	 * @param page
	 * @param pageSize
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public OrderQueryResp findOrder(OrderQueryReq req, String pageSize, String interfaceType, String reqIp){
		OrderQueryResp resp = new OrderQueryResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getUid());
			
			//数据校验
			if(resp.successed()){
				if(req.getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}
			}
			
			//处理
			if(resp.successed()){
				PageBean pageBean = new PageBean("1", pageSize);
				resp.setResult(orderService.findOrderInfo(req.getUid(), req.getEmployeeId(), req.getOrderType(), pageBean.getPageSize(), req.getLastOrderId()));
			}
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		return resp;
	}
	
	/**
	 * 获取订单详细信息
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public OrderDetailResp getOrder(OrderGetReq req, String interfaceType, String reqIp){
		OrderDetailResp resp = new OrderDetailResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getUid());
			
			//数据校验
			if(resp.successed()){
				if(req.getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}else if(StringUtil.isBlankOrNull(req.getOrderId())){
					resp.errorParam(ExceptionConstants.MSG_ORDERID_EMPTY);
				}
			}
			
			//处理
			if(resp.successed()){
				resp.setResult(orderService.getOrderDetail(req.getOrderId(), req.getUid()));
			}
		} catch (ParamException e) {
			resp.errorData(e.getMessage());
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		
		return resp;
	}
	
	/**
	 * 订单支付预处理
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public PrepayResp prepay(OrderPrepayReq req, String interfaceType, String reqIp){
		PrepayResp resp = new PrepayResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getBean().getUid());
			
			//数据验证
			if(resp.successed()){
				if(req.getBean().getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}else if(StringUtil.isBlankOrNull(req.getBean().getTradeWay())){
					resp.errorParam("交易途径为空");
				}else if(StringUtil.isBlankOrNull(req.getBean().getOrderId())){
					resp.errorParam(ExceptionConstants.MSG_ORDERID_EMPTY);
				}else if(req.getBean().getAmount()==null){
					resp.errorParam("金额有误");
				}
			}
			
			//处理
			if(resp.successed()){
				String platform = SrmClient.getPlatform(req.getAuthAccount());
				resp.setResult(orderProcess.prepay(req.getBean(), platform));
			}
		}catch (ParamException e) {
			resp.errorParam(e.getMessage());
		}catch (DataException e) {
			resp.errorData(e.getMessage());
		}catch (ProxyException e) {
			resp.error();
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}

		return resp;
	}
	
	/**
	 * 处理苹果支付通知
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public Response synApplepay(ApplepaySynReq req, String interfaceType, String reqIp){
		Response resp = new Response();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getUid());
			
			//数据校验
			if(resp.successed()){
				if(req.getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}else if(StringUtil.isBlankOrNull(req.getOrderId())){
					resp.errorParam(ExceptionConstants.MSG_ORDERID_EMPTY);
				}else if(StringUtil.isBlankOrNull(req.getReceipt())){
					resp.errorParam("支付结果为空");
				}
			}
			
			//处理
			if(resp.successed()){
				boolean syn = orderProcess.synApplepay(req.getOrderId(), req.getUid(), req.getReceipt());
				if(!syn){
					resp.errorParam("数据有误");
				}
			}
		} catch (ProxyException e){
			resp.errorParam("验证失败");
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}

		return resp;
	}
	
	/**
	 * 取消订单
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public Response cancelOrder(OrderCancelReq req, String interfaceType, String reqIp){
		Response resp = new Response();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getUid());
			
			//数据校验
			if(resp.successed()){
				if(req.getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}else if(StringUtil.isBlankOrNull(req.getOrderId())){
					resp.errorParam(ExceptionConstants.MSG_ORDERID_EMPTY);
				}
			}
			
			//处理
			if(resp.successed()){
				orderProcess.cancelOrder(req.getOrderId(), req.getUid(), req.getReason());
			}
		}catch(DataException e){
			resp.errorData(e.getMessage());
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		
		return resp;
	}
	
	/**
	 * 删除订单
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public Response delOrder(OrderDelReq req, String interfaceType, String reqIp){
		Response resp = new Response();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getUid());
			
			//数据校验
			if(resp.successed()){
				if(req.getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}else if(StringUtil.isBlankOrNull(req.getOrderId())){
					resp.errorParam(ExceptionConstants.MSG_ORDERID_EMPTY);
				}
			}
			
			//处理
			if(resp.successed()){
				orderProcess.delOrder(req.getOrderId(), req.getUid());
			}
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		
		return resp;
	}
	
	/**
	 * 添加留言
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public Response addMsg(OrderMsgAddReq req, String interfaceType, String reqIp){
		Response resp = new Response();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getBean().getUid());
			
			//数据校验
			if(resp.successed()){
				if(req.getBean().getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}else if(req.getBean().getOrderId()==null){
					resp.errorParam(ExceptionConstants.MSG_ORDERID_EMPTY);
				}else if(StringUtil.isBlankOrNull(req.getBean().getMsg())){
					resp.errorParam("留言为空");
				}else if(req.getBean().getMsg().length()>500){
					resp.errorParam("留言超长");
				}
			}
			
			//处理
			if(resp.successed()){
				orderMsgProcess.addMsgOfUser(req.getBean());
			}
		} catch (ParamException e) {
			resp.errorParam(e.getMessage());
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		
		return resp;
	}
	
	/**
	 * 以订单为单位分页获取用户最新留言列表
	 * @param req
	 * @param page
	 * @param pageSize
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public OrderMsgQueryResp findRecentMsg(OrderMsgQueryReq req, String page, String pageSize, String interfaceType, String reqIp){
		OrderMsgQueryResp resp = new OrderMsgQueryResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getUid());
			
			//数据校验
			if(resp.successed()){
				if(req.getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}
			}
			
			//处理
			if(resp.successed()){
				PageBean pageBean = new PageBean(page, pageSize);
				resp.setResultList(orderMsgService.findRecentMsg4User(req.getUid(), pageBean));
			}
			
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		
		return resp;
	}
	
	/**
	 * 分页获取指定订单的留言列表
	 * @param req
	 * @param page
	 * @param pageSize
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public OrderMsgQueryResp findMsg(OrderMsgQueryReq req, String pageSize, String interfaceType, String reqIp){
		OrderMsgQueryResp resp = new OrderMsgQueryResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getUid());
			
			//数据校验
			if(resp.successed()){
				if(req.getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}else if(req.getOrderId()==null){
					resp.errorParam(ExceptionConstants.MSG_ORDERID_EMPTY);
				}
			}
			
			//处理
			if(resp.successed()){
				PageBean pageBean = new PageBean("1", pageSize);
				resp.setResultList(orderMsgService.findMsg4User(req.getOrderId(), req.getUid(), pageBean.getPageSize(), req.getLastMsgId()));
			}
			
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		
		return resp;
	}
	
	/**
	 * 分页获取指定订单的新留言列表
	 * @param req
	 * @param page
	 * @param pageSize
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public OrderMsgQueryResp findLastMsg(OrderMsgQueryReq req, String pageSize, String interfaceType, String reqIp){
		OrderMsgQueryResp resp = new OrderMsgQueryResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getUid());
			
			//数据校验
			if(resp.successed()){
				if(req.getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}else if(req.getOrderId()==null){
					resp.errorParam(ExceptionConstants.MSG_ORDERID_EMPTY);
				}
			}
			
			//处理
			if(resp.successed()){
				PageBean pageBean = new PageBean("1", pageSize);
				resp.setResultList(orderMsgService.findLastMsg4User(req.getOrderId(), req.getUid(), pageBean.getPageSize(), req.getLastMsgId()));
			}
			
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		
		return resp;
	}
	
	/**
	 * 评价订单
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public Response evalOrder(OrderEvalReq req, String interfaceType, String reqIp){
		Response resp = new Response();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getBean().getUid());
			
			//数据校验
			if(resp.successed()){
				if(req.getBean().getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}else if(req.getBean().getOrderId()==null){
					resp.errorParam(ExceptionConstants.MSG_ORDERID_EMPTY);
				}else if(req.getBean().getEvalId()==null){
					resp.errorParam("评价编号为空");
				}
			}
			
			//处理
			if(resp.successed()){
				orderService.evalOrder(req.getBean());
			}
		} catch (ParamException e) {
			resp.errorParam(e.getMessage());
		} catch (DataException e) {
			resp.errorData(e.getMessage());
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		
		return resp;
	}
	
	/**
	 * 获取订单评价信息
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public OrderEvalDetailResp getOrderEval(OrderEvalGetReq req, String interfaceType, String reqIp){
		OrderEvalDetailResp resp = new OrderEvalDetailResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getUid());
			
			//数据校验
			if(resp.successed()){
				if(req.getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}else if(req.getOrderId()==null){
					resp.errorParam(ExceptionConstants.MSG_ORDERID_EMPTY);
				}
			}
			
			//处理
			if(resp.successed()){
				resp.setResult(orderService.getOrderEvalInfo(req.getOrderId()));
			}
		} catch (ParamException e) {
			resp.errorParam(e.getMessage());
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
			
		return resp;
	}
}
