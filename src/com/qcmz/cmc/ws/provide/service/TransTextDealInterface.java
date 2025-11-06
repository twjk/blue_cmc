package com.qcmz.cmc.ws.provide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.process.TransTextProcess;
import com.qcmz.cmc.service.ITransTextService;
import com.qcmz.cmc.ws.provide.vo.OrderDealGetReq;
import com.qcmz.cmc.ws.provide.vo.OrderDealQueryReq;
import com.qcmz.cmc.ws.provide.vo.TransTextDealDetailResp;
import com.qcmz.cmc.ws.provide.vo.TransTextDealQueryResp;
import com.qcmz.cmc.ws.provide.vo.TransTextFinishReq;
import com.qcmz.cmc.ws.provide.vo.TransTextTransReq;
import com.qcmz.framework.constant.ExceptionConstants;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.exception.ParamException;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.framework.ws.BaseInterface;
import com.qcmz.framework.ws.vo.Response;
import com.qcmz.srm.client.util.SrmClient;

/**
 * 类说明：短文快译处理接口
 * 修改历史：
 */
@Component
public class TransTextDealInterface extends BaseInterface {
	@Autowired
	private ITransTextService transTextService;
	@Autowired
	private TransTextProcess transTextProcess;
	
	/**
	 * 分页获取短文快译列表
	 * @param req
	 * @param page
	 * @param pageSize
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public TransTextDealQueryResp findText(OrderDealQueryReq req, String pageSize, String interfaceType, String reqIp){
		TransTextDealQueryResp resp = new TransTextDealQueryResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className);
			
			//数据校验
			if(resp.successed()){}
			
			//处理
			if(resp.successed()){
				PageBean pageBean = new PageBean("1", pageSize);
				resp.setResult(transTextProcess.findText4Deal(req.getBean(), pageBean.getPageSize()));
			}
			
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		
		return resp;
	}
	
	/**
	 * 获取短文快译订单详细信息
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public TransTextDealDetailResp getText(OrderDealGetReq req, String interfaceType, String reqIp){
		TransTextDealDetailResp resp = new TransTextDealDetailResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className);
			
			//数据校验
			if(resp.successed()){
				if(StringUtil.isBlankOrNull(req.getOrderId())){
					resp.errorParam(ExceptionConstants.MSG_ORDERID_EMPTY);
				}
			}
			
			//处理
			if(resp.successed()){
				resp.setResult(transTextService.getTextDetail4Deal(req.getOrderId()));
			}
		} catch(ParamException e){
			resp.errorParam(e.getMessage());
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		
		return resp;
	}
	
	/**
	 * 保存翻译结果
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public Response saveTrans(TransTextTransReq req, String interfaceType, String reqIp){
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
				transTextService.saveTrans(req.getBean());
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
	public Response finishTrans(TransTextFinishReq req, String interfaceType, String reqIp){
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
				}else if(StringUtil.isBlankOrNull(req.getBean().getDst())){
					resp.errorData("译文为空");
				}
			}
			
			//处理
			if(resp.successed()){
				transTextProcess.finishTrans(req.getBean());
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
