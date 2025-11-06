package com.qcmz.cmc.ws.provide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.process.TransPriceProcess;
import com.qcmz.cmc.process.TransTextProcess;
import com.qcmz.cmc.service.ITransTextService;
import com.qcmz.cmc.ws.provide.vo.OrderCreateResp;
import com.qcmz.cmc.ws.provide.vo.OrderCreateResult;
import com.qcmz.cmc.ws.provide.vo.OrderGetReq;
import com.qcmz.cmc.ws.provide.vo.TransPriceResp;
import com.qcmz.cmc.ws.provide.vo.TransTextAddReq;
import com.qcmz.cmc.ws.provide.vo.TransTextDetailResp;
import com.qcmz.cmc.ws.provide.vo.TransTextPriceQueryReq;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.constant.ExceptionConstants;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.exception.ParamException;
import com.qcmz.framework.util.BeanUtil;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.framework.ws.BaseInterface;
import com.qcmz.srm.client.util.SrmClient;

/**
 * 短文快译接口
 */
@Component
public class TransTextInterface extends BaseInterface {
	@Autowired
	private ITransTextService transTextService;
	
	@Autowired
	private TransPriceProcess transPriceProcess;
	@Autowired
	private TransTextProcess transTextProcess;
		
	/**
	 * 获取短文快译价格
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public TransPriceResp findPrice(TransTextPriceQueryReq req, String interfaceType, String reqIp){
		TransPriceResp resp = new TransPriceResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className);
			
			//数据验证
			if(resp.successed()){}
			
			//处理
			if(resp.successed()){
				resp.setResult(transPriceProcess.findTransPrice(DictConstants.DICT_TRANSTYPE_TEXT, null
						, req.getBean().getSrc(), req.getBean().getFrom(), req.getBean().getTo(), req.getLanguage()));
			}
		}catch (ParamException e) {
			resp.errorParam(e.getMessage());
		}catch (Exception e) {
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
	public TransTextDetailResp getText(OrderGetReq req, String interfaceType, String reqIp){
		TransTextDetailResp resp = new TransTextDetailResp();
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
				resp.setResult(transTextService.getTextDetail(req.getOrderId(), req.getUid()));
			}
		}catch (ParamException e) {
			resp.errorParam(e.getMessage());
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		
		return resp;
	}
	
	/**
	 * 添加短文快译订单
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public OrderCreateResp addText(TransTextAddReq req, String interfaceType, String reqIp){
		OrderCreateResp resp = new OrderCreateResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getBean().getUid());
			
			//数据验证
			if(resp.successed()){
				if(req.getBean().getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}else if(req.getBean().getUserType()==null){
					resp.errorParam(ExceptionConstants.MSG_USERTYPE_EMPTY);
				}else if(StringUtil.isBlankOrNull(req.getBean().getFrom())){
					resp.errorParam("源语言为空");
				}else if(StringUtil.isBlankOrNull(req.getBean().getTo())){
					resp.errorParam("目标语言为空");
				}else if(StringUtil.isBlankOrNull(req.getBean().getSrc())){
					resp.errorData("原文为空");
				}
			}
			
			//处理
			if(resp.successed()){
				String platform = SrmClient.getPlatform(req.getAuthAccount());
				String serviceType = SrmClient.getServiceType(req.getAuthAccount());
				OrderCreateResult r = transTextProcess.addText(req.getBean(), 0, serviceType, platform, req.getAppVer());
				BeanUtil.copyProperties(resp, r);
			}
		}catch (ParamException e) {
			resp.errorParam(e.getMessage());
		}catch (DataException e) {
			resp.errorParam(e.getMessage());
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		
		return resp;
	}
		
}
