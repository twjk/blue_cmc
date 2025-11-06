package com.qcmz.cmc.ws.provide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.process.TransPriceProcess;
import com.qcmz.cmc.process.TransVideoProcess;
import com.qcmz.cmc.service.ITransVideoService;
import com.qcmz.cmc.ws.provide.vo.OrderCreateResp;
import com.qcmz.cmc.ws.provide.vo.OrderCreateResult;
import com.qcmz.cmc.ws.provide.vo.OrderGetReq;
import com.qcmz.cmc.ws.provide.vo.TransVideoAddReq;
import com.qcmz.cmc.ws.provide.vo.TransVideoDetailResp;
import com.qcmz.cmc.ws.provide.vo.TransVideoPriceQueryReq;
import com.qcmz.cmc.ws.provide.vo.TransVideoPriceResp;
import com.qcmz.framework.constant.ExceptionConstants;
import com.qcmz.framework.exception.BalanceNotEnoughException;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.exception.ParamException;
import com.qcmz.framework.util.BeanUtil;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.framework.ws.BaseInterface;
import com.qcmz.framework.ws.vo.Response;
import com.qcmz.srm.client.util.SrmClient;

@Component
public class TransVideoInterface extends BaseInterface {
	@Autowired
	private ITransVideoService transVideoService;
	@Autowired
	private TransPriceProcess transPriceProcess;
	@Autowired
	private TransVideoProcess transVideoProcess;
	
	/**
	 * 获取视频口译价格
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public TransVideoPriceResp findPrice(TransVideoPriceQueryReq req, String interfaceType, String reqIp){
		TransVideoPriceResp resp = new TransVideoPriceResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className);
			
			//数据验证
			if(resp.successed()){}
			
			//处理
			if(resp.successed()){
				resp.setResult(transPriceProcess.findTransVideoPrice(req.getFrom(), req.getTo(), req.gotLanguage()));
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
	 * 创建视频口译订单
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public OrderCreateResp addVideo(TransVideoAddReq req, String interfaceType, String reqIp){
		OrderCreateResp resp = new OrderCreateResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getBean().getUid());
			
			//数据校验
			if(resp.successed()){
				if(req.getBean().getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}else if(req.getBean().getUserType()==null){
					resp.errorParam(ExceptionConstants.MSG_USERTYPE_EMPTY);
				}else if(StringUtil.isBlankOrNull(req.getBean().getFrom())){
					resp.errorParam(ExceptionConstants.MSG_FROMLANG_EMPTY);
				}else if(StringUtil.isBlankOrNull(req.getBean().getTo())){
					resp.errorParam(ExceptionConstants.MSG_TOLANG_EMPTY);
				}
			}
			
			//处理
			if(resp.successed()){
				String platform = SrmClient.getPlatform(req.getAuthAccount());
				String serviceType = SrmClient.getServiceType(req.getAuthAccount());
				OrderCreateResult r = transVideoProcess.addVideo(req.getBean(), serviceType, platform, req.getAppVer());
				BeanUtil.copyProperties(resp, r);
			}
		} catch (ParamException e) {
			resp.errorParam(e.getMessage());
		} catch (DataException e) {
			resp.errorData(e.getMessage());
		} catch (BalanceNotEnoughException e) {
			resp.error(Response.RESPCODE_BALANCE_NOTENOUGH, null);
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		
		return resp;
	}
	
	/**
	 * 获取视频口译订单详细信息
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public TransVideoDetailResp getVideo(OrderGetReq req, String interfaceType, String reqIp){
		TransVideoDetailResp resp = new TransVideoDetailResp();
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
				resp.setResult(transVideoService.getVideoDetail(req.getOrderId(), req.getUid()));
			}
		}catch (ParamException e) {
			resp.errorParam(e.getMessage());
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		
		return resp;
	}
}
