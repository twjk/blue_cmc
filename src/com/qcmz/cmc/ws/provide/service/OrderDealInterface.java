package com.qcmz.cmc.ws.provide.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.process.OrderMsgProcess;
import com.qcmz.cmc.process.OrderProcess;
import com.qcmz.cmc.service.IOrderCheckService;
import com.qcmz.cmc.service.IOrderMsgService;
import com.qcmz.cmc.service.IOrderService;
import com.qcmz.cmc.ws.provide.vo.OrderAcceptReq;
import com.qcmz.cmc.ws.provide.vo.OrderAppointTimeUpdateReq;
import com.qcmz.cmc.ws.provide.vo.OrderDealMsgAddReq;
import com.qcmz.cmc.ws.provide.vo.OrderDealMsgQueryReq;
import com.qcmz.cmc.ws.provide.vo.OrderHandoverReq;
import com.qcmz.cmc.ws.provide.vo.OrderMsgQueryResp;
import com.qcmz.cmc.ws.provide.vo.OrderPickReq;
import com.qcmz.cmc.ws.provide.vo.OrderRejectReq;
import com.qcmz.cmc.ws.provide.vo.OrderStartCheckReq;
import com.qcmz.framework.constant.ExceptionConstants;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.exception.ParamException;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.framework.util.log.Operator;
import com.qcmz.framework.ws.BaseInterface;
import com.qcmz.framework.ws.vo.Response;
import com.qcmz.srm.client.util.SrmClient;

/**
 * 订单处理接口
 */
@Component
public class OrderDealInterface extends BaseInterface {
	@Autowired
	private IOrderMsgService orderMsgService;
	@Autowired
	private IOrderService orderService;
	@Autowired
	private IOrderCheckService orderCheckService;
	
	@Autowired
	private OrderProcess orderProcess;
	@Autowired
	private OrderMsgProcess orderMsgProcess;
	
	/**
	 * 捡单
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public Response pickOrder(OrderPickReq req, String interfaceType, String reqIp){
		Response resp = new Response();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getOperator());
			
			//数据校验
			if(resp.successed()){
				if(StringUtil.isBlankOrNull(req.getOperator())
						|| StringUtil.isBlankOrNull(req.getOperatorName())){
					resp.errorParam(ExceptionConstants.MSG_OPERATOR_EMPTY);
				}else if(StringUtil.isBlankOrNull(req.getOrderId())){
					resp.errorParam(ExceptionConstants.MSG_ORDERID_EMPTY);
				}
			}
			
			//处理
			if(resp.successed()){
				orderService.pickOrder(req.getOrderId(), new Operator(req.getOperator(), req.getOperatorName(), reqIp));
			}
		}catch (DataException e) {
			resp.errorData(e.getMessage());
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口[pickOrder]失败", e);
		}
		
		return resp;
	}
	
	/**
	 * 修改预约时间
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public Response modAppointTime(OrderAppointTimeUpdateReq req, String interfaceType, String reqIp){
		Response resp = new Response();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getOperator());
			
			//数据校验
			if(resp.successed()){
				if(StringUtil.isBlankOrNull(req.getOperator())
						|| StringUtil.isBlankOrNull(req.getOperatorName())){
					resp.errorParam(ExceptionConstants.MSG_OPERATOR_EMPTY);
				}else if(StringUtil.isBlankOrNull(req.getOrderId())){
					resp.errorParam(ExceptionConstants.MSG_ORDERID_EMPTY);
				}else if(req.getAppointTime()==null){
					resp.errorParam("预约时间为空");
				}
			}
			
			//处理
			if(resp.successed()){
				orderProcess.modAppointTime(req.getOrderId(), new Date(req.getAppointTime())
						, new Operator(req.getOperator(), req.getOperatorName(), reqIp));
			}
		}catch (DataException e) {
			resp.errorData(e.getMessage());
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口[modAppointTime]失败", e);
		}
		
		return resp;
	}
	
	/**
	 * 接单
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public Response acceptOrder(OrderAcceptReq req, String interfaceType, String reqIp){
		Response resp = new Response();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getOperator());
			
			//数据校验
			if(resp.successed()){
				if(StringUtil.isBlankOrNull(req.getOperator())
						|| StringUtil.isBlankOrNull(req.getOperatorName())){
					resp.errorParam(ExceptionConstants.MSG_OPERATOR_EMPTY);
				}else if(StringUtil.isBlankOrNull(req.getOrderId())){
					resp.errorParam(ExceptionConstants.MSG_ORDERID_EMPTY);
				}
			}
			
			//处理
			if(resp.successed()){
				orderProcess.acceptOrder(req.getOrderId(), new Operator(req.getOperator(), req.getOperatorName(), reqIp));
			}
		}catch (DataException e) {
			resp.errorData(e.getMessage());
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口[acceptOrder]失败", e);
		}
		
		return resp;
	}

	/**
	 * 交单
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public Response handoverOrder(OrderHandoverReq req, String interfaceType, String reqIp){
		Response resp = new Response();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getOperator());
			
			//数据校验
			if(resp.successed()){
				if(StringUtil.isBlankOrNull(req.getOperator())
						|| StringUtil.isBlankOrNull(req.getOperatorName())){
					resp.errorParam(ExceptionConstants.MSG_OPERATOR_EMPTY);
				}else if(StringUtil.isBlankOrNull(req.getOrderId())){
					resp.errorParam(ExceptionConstants.MSG_ORDERID_EMPTY);
				}
			}
			
			//处理
			if(resp.successed()){
				orderService.handoverOrder(req.getOrderId(), req.getFrom(), req.getTo(), new Operator(req.getOperator(), req.getOperatorName(), reqIp));
			}
		}catch (DataException e) {
			resp.errorData(e.getMessage());
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口[handoverOrder]失败", e);
		}
		
		return resp;
	}
	
	/**
	 * 拒单
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public Response rejectOrder(OrderRejectReq req, String interfaceType, String reqIp){
		Response resp = new Response();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getOperator());
			
			//数据校验
			if(resp.successed()){
				if(StringUtil.isBlankOrNull(req.getOperator())
						|| StringUtil.isBlankOrNull(req.getOperatorName())){
					resp.errorParam(ExceptionConstants.MSG_OPERATOR_EMPTY);
				}else if(StringUtil.isBlankOrNull(req.getOrderId())){
					resp.errorParam(ExceptionConstants.MSG_ORDERID_EMPTY);
				}else if(StringUtil.isBlankOrNull(req.getReason())){
					resp.errorData("取消原因为空");
				}
			}
			
			//处理
			if(resp.successed()){
				orderProcess.rejectOrderAndRefund(req.getOrderId(), req.getReason(), new Operator(req.getOperator(), req.getOperatorName(), reqIp));
			}
		}catch (DataException e) {
			resp.errorData(e.getMessage());
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口[rejectOrder]失败", e);
		}
		
		return resp;
	}
	
	/**
	 * 拒单
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public Response startCheck(OrderStartCheckReq req, String interfaceType, String reqIp){
		Response resp = new Response();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getOperator());
			
			//数据校验
			if(resp.successed()){
				if(StringUtil.isBlankOrNull(req.getOperator())
						|| StringUtil.isBlankOrNull(req.getOperatorName())){
					resp.errorParam(ExceptionConstants.MSG_OPERATOR_EMPTY);
				}else if(StringUtil.isBlankOrNull(req.getOrderId())){
					resp.errorParam(ExceptionConstants.MSG_ORDERID_EMPTY);
				}
			}
			
			//处理
			if(resp.successed()){
				orderCheckService.startCheck(req.getOrderId(), new Operator(req.getOperator(), req.getOperatorName(), reqIp));
			}
		}catch (DataException e) {
			resp.errorData(e.getMessage());
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口[startCheck]失败", e);
		}
		
		return resp;
	}
	
	/**
	 * 添加客服留言
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public Response addMsg(OrderDealMsgAddReq req, String interfaceType, String reqIp){
		Response resp = new Response();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getOperator());
			
			//数据校验
			if(resp.successed()){
				if(StringUtil.isBlankOrNull(req.getOperator())
						|| StringUtil.isBlankOrNull(req.getOperatorName())){
					resp.errorParam(ExceptionConstants.MSG_OPERATOR_EMPTY);
				}else if(StringUtil.isBlankOrNull(req.getOrderId())){
					resp.errorParam(ExceptionConstants.MSG_ORDERID_EMPTY);
				}else if(StringUtil.isBlankOrNull(req.getMsg())){
					resp.errorParam("留言为空");
				}else if(req.getMsg().length()>500){
					resp.errorParam("留言超长");
				}
			}
			
			//处理
			if(resp.successed()){
				orderMsgProcess.addCsMsg(req.getOrderId(), req.getMsg(), new Operator(req.getOperator(), req.getOperatorName(), reqIp));
			}
		} catch (ParamException e) {
			resp.errorParam(e.getMessage());
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口[addMsg]失败", e);
		}
		
		return resp;
	} 
	
	/**
	 * 分页查询留言列表
	 * @param req
	 * @param page
	 * @param pageSize
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public OrderMsgQueryResp findMsg(OrderDealMsgQueryReq req, String pageSize, String interfaceType, String reqIp){
		OrderMsgQueryResp resp = new OrderMsgQueryResp();
		try {
			//身份验证
			if(StringUtil.notBlankAndNull(req.getOperator())){
				SrmClient.validAccount(req, resp, className, req.getOperator());
			}else{
				SrmClient.validAccount(req, resp, className);
			}
			
			//数据校验
			if(resp.successed()){
				if(StringUtil.isBlankOrNull(req.getOrderId())){
					resp.errorParam("订单编号为空");
				}
			}
			
			//处理
			if(resp.successed()){
				PageBean pageBean = new PageBean("1", pageSize);
				resp.setResultList(orderMsgService.findMsg(req.getOrderId(), pageBean.getPageSize(), req.getLastMsgId()));
			}
			
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口[findMsg]失败", e);
		}
		
		return resp;
	}
}
