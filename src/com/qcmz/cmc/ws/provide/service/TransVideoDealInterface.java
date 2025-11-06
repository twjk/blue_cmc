package com.qcmz.cmc.ws.provide.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.process.TransVideoProcess;
import com.qcmz.cmc.service.ITransVideoService;
import com.qcmz.cmc.ws.provide.vo.OrderDealQueryReq;
import com.qcmz.cmc.ws.provide.vo.TransVideoConnectedReq;
import com.qcmz.cmc.ws.provide.vo.TransVideoDealQueryResp;
import com.qcmz.cmc.ws.provide.vo.TransVideoFinishReq;
import com.qcmz.cmc.ws.provide.vo.TransVideoStartBillingReq;
import com.qcmz.framework.constant.ExceptionConstants;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.framework.ws.BaseInterface;
import com.qcmz.framework.ws.vo.Response;
import com.qcmz.srm.client.util.SrmClient;

/**
 * 类说明：视频口译处理接口
 * 修改历史：
 */
@Component
public class TransVideoDealInterface extends BaseInterface {
	@Autowired
	private ITransVideoService transVideoService;
	
	@Autowired
	private TransVideoProcess transVideoProcess;
	
	private Logger logger = Logger.getLogger(this.getClass());
	private String className = this.getClass().getSimpleName();
	
	/**
	 * 分页获取视频口译列表
	 * @param req
	 * @param page
	 * @param pageSize
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public TransVideoDealQueryResp findVideo(OrderDealQueryReq req, String pageSize, String interfaceType, String reqIp){
		TransVideoDealQueryResp resp = new TransVideoDealQueryResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className);
			
			//数据校验
			if(resp.successed()){}
			
			//处理
			if(resp.successed()){
				PageBean pageBean = new PageBean("1", pageSize);
				resp.setResult(transVideoProcess.findVideo4Deal(req.getBean(), pageBean.getPageSize()));
			}
			
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		
		return resp;
	}
	
	/**
	 * 接通用户
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public Response connected(TransVideoConnectedReq req, String interfaceType, String reqIp){
		Response resp = new Response();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getBean().getOperator());
			
			//数据校验
			if(resp.successed()){
				if(StringUtil.isBlankOrNull(req.getBean().getOperator())
						|| StringUtil.isBlankOrNull(req.getBean().getOperatorName())){
					resp.errorParam(ExceptionConstants.MSG_OPERATOR_EMPTY);
				}else if(StringUtil.isBlankOrNull(req.getBean().getOrderId())){
					resp.errorParam(ExceptionConstants.MSG_ORDERID_EMPTY);
				}
			}
			
			//处理
			if(resp.successed()){
				transVideoService.connected(req.getBean());
			}
		}catch (DataException e) {
			resp.errorData(e.getMessage());
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		
		return resp;
	}
	
	/**
	 * 开始计费
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public Response startBilling(TransVideoStartBillingReq req, String interfaceType, String reqIp){
		Response resp = new Response();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getBean().getOperator());
			
			//数据校验
			if(resp.successed()){
				if(StringUtil.isBlankOrNull(req.getBean().getOperator())
						|| StringUtil.isBlankOrNull(req.getBean().getOperatorName())){
					resp.errorParam(ExceptionConstants.MSG_OPERATOR_EMPTY);
				}else if(StringUtil.isBlankOrNull(req.getBean().getOrderId())){
					resp.errorParam(ExceptionConstants.MSG_ORDERID_EMPTY);
				}
			}
			
			//处理
			if(resp.successed()){
				transVideoService.startBilling(req.getBean());
			}
		}catch (DataException e) {
			resp.errorData(e.getMessage());
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		
		return resp;
	}
	
	/**
	 * 接通并开始计费
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public Response connectedAndBilling(TransVideoStartBillingReq req, String interfaceType, String reqIp){
		Response resp = new Response();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getBean().getOperator());
			
			//数据校验
			if(resp.successed()){
				if(StringUtil.isBlankOrNull(req.getBean().getOperator())
						|| StringUtil.isBlankOrNull(req.getBean().getOperatorName())){
					resp.errorParam(ExceptionConstants.MSG_OPERATOR_EMPTY);
				}else if(StringUtil.isBlankOrNull(req.getBean().getOrderId())){
					resp.errorParam(ExceptionConstants.MSG_ORDERID_EMPTY);
				}
			}
			
			//处理
			if(resp.successed()){
				transVideoService.connectedAndBilling(req.getBean());
			}
		}catch (DataException e) {
			resp.errorData(e.getMessage());
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		
		return resp;
	}
	
	/**
	 * 完成翻译
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public Response finishTrans(TransVideoFinishReq req, String interfaceType, String reqIp){
		Response resp = new Response();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getBean().getOperator());
			
			//数据校验
			if(resp.successed()){
				if(StringUtil.isBlankOrNull(req.getBean().getOperator())
						|| StringUtil.isBlankOrNull(req.getBean().getOperatorName())){
					resp.errorParam(ExceptionConstants.MSG_OPERATOR_EMPTY);
				}else if(StringUtil.isBlankOrNull(req.getBean().getOrderId())){
					resp.errorParam(ExceptionConstants.MSG_ORDERID_EMPTY);
				}
			}
			
			//处理
			if(resp.successed()){
				transVideoProcess.finishTrans(req.getBean());
			}
		}catch (DataException e) {
			resp.errorData(e.getMessage());
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		
		return resp;
	}
}
