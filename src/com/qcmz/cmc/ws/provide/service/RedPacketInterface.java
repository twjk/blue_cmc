package com.qcmz.cmc.ws.provide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.process.RedPacketProcess;
import com.qcmz.cmc.service.IRedPacketAccountService;
import com.qcmz.cmc.util.RedPacketUtil;
import com.qcmz.cmc.ws.provide.vo.PrepayResp;
import com.qcmz.cmc.ws.provide.vo.RedPacketAccountGetReq;
import com.qcmz.cmc.ws.provide.vo.RedPacketAccountResp;
import com.qcmz.cmc.ws.provide.vo.RedPacketCancelReq;
import com.qcmz.cmc.ws.provide.vo.RedPacketCashApplyReq;
import com.qcmz.cmc.ws.provide.vo.RedPacketCreateReq;
import com.qcmz.cmc.ws.provide.vo.RedPacketCreateResp;
import com.qcmz.cmc.ws.provide.vo.RedPacketGetReq;
import com.qcmz.cmc.ws.provide.vo.RedPacketGetResp;
import com.qcmz.cmc.ws.provide.vo.RedPacketOfUserQueryResp;
import com.qcmz.cmc.ws.provide.vo.RedPacketPrepayReq;
import com.qcmz.cmc.ws.provide.vo.RedPacketReceiveReq;
import com.qcmz.cmc.ws.provide.vo.RedPacketReceiveResp;
import com.qcmz.cmc.ws.provide.vo.RedPacketReceivedQueryReq;
import com.qcmz.cmc.ws.provide.vo.RedPacketSentQueryReq;
import com.qcmz.framework.constant.DictConstants;
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
 * 红包接口
 */
@Component
public class RedPacketInterface extends BaseInterface {
	@Autowired
	private IRedPacketAccountService redPacketAccountService;
	@Autowired
	private RedPacketProcess redPacketProcess;
	
	/**
	 * 获取用户红包帐户信息
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public RedPacketAccountResp getAccount(RedPacketAccountGetReq req, String interfaceType, String reqIp){
		RedPacketAccountResp resp = new RedPacketAccountResp();
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
				resp.setResult(redPacketAccountService.getAccountInfo(req.getUid()));
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
	 * 创建红包
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public RedPacketCreateResp create(RedPacketCreateReq req, String interfaceType, String reqIp){
		RedPacketCreateResp resp = new RedPacketCreateResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getBean().getUid());
			
			String platform = SrmClient.getPlatform(req.getAuthAccount());
			
			//数据校验
			if(resp.successed()){
				if(req.getBean().getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}else if(StringUtil.isBlankOrNull(req.getBean().getLangCode())){
					resp.errorParam("语言代码为空");
				}else if(StringUtil.isBlankOrNull(req.getBean().getTitle())){
					resp.errorParam("红包暗语为空");
				}else if(DictConstants.DICT_TRADEWAY_WXPAY.equals(req.getBean().getTradeWay())
						&& DictConstants.DICT_PLATFORM_MINIPROGRAM.equals(platform)
						&& StringUtil.isBlankOrNull(req.getBean().getOpenid())){
					resp.errorParam("openid为空");
				}
			}
			
			//处理
			if(resp.successed()){
				resp.setResult(redPacketProcess.create(req.getBean(), platform, req.getAppVer()));
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
	 * 取消红包
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public Response cancel(RedPacketCancelReq req, String interfaceType, String reqIp){
		Response resp = new Response();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getPacketId());
			
			//数据验证
			if(resp.successed()){
				if(req.getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}else if(StringUtil.isBlankOrNull(req.getPacketId())){
					resp.errorParam("红包编号为空");
				}
			}
			
			//处理
			if(resp.successed()){
				redPacketProcess.cancel(req.getPacketId(), req.getUid());
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
	 * 支付预处理
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public PrepayResp prepay(RedPacketPrepayReq req, String interfaceType, String reqIp){
		PrepayResp resp = new PrepayResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getBean().getPacketId());
			
			//数据验证
			if(resp.successed()){
				if(req.getBean().getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}else if(StringUtil.isBlankOrNull(req.getBean().getTradeWay())
						|| !RedPacketUtil.TRADEWAYS.contains(req.getBean().getTradeWay())){
					resp.errorParam("交易途径有误");
				}else if(StringUtil.isBlankOrNull(req.getBean().getPacketId())){
					resp.errorParam("红包编号为空");
				}else if(req.getBean().getAmount()==null){
					resp.errorParam("金额有误");
				}
			}
			
			//处理
			if(resp.successed()){
				String platform = SrmClient.getPlatform(req.getAuthAccount());
				resp.setResult(redPacketProcess.prepay(req.getBean(), platform));
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
	 * 获取红包信息
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public RedPacketGetResp getPacket(RedPacketGetReq req, String interfaceType, String reqIp){
		RedPacketGetResp resp = new RedPacketGetResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getPacketId());
			
			//数据校验
			if(resp.successed()){
				if(StringUtil.isBlankOrNull(req.getPacketId())){
					resp.errorParam("红包编号为空");
				}
			}
			
			//处理
			if(resp.successed()){
				resp.setResult(redPacketProcess.getRedPacketInfo(req.getPacketId(), req.getReceiverId()));
			}
		} catch (DataException e) {
			resp.errorData(e.getMessage());
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}

		return resp;
	}
	
	/**
	 * 领红包
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public RedPacketReceiveResp receive(RedPacketReceiveReq req, String interfaceType, String reqIp){
		RedPacketReceiveResp resp = new RedPacketReceiveResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getPacketId());
			
			//数据校验
			if(resp.successed()){
				if(req.getReceiverId()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}else if(StringUtil.isBlankOrNull(req.getPacketId())){
					resp.errorParam("红包编号为空");
				}else if(req.getFile()==null){
					resp.errorParam("语音为空");
				}
			}
			
			//处理
			if(resp.successed()){
				resp.setResult(redPacketProcess.receive(req.getPacketId(), req.getReceiverId(), req.getFile()));
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
	 * 申请提现
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public Response applyCash(RedPacketCashApplyReq req, String interfaceType, String reqIp){
		Response resp = new Response();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getUid()+req.getOpenid());
			
			//数据验证
			if(resp.successed()){
				if(req.getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}else if(StringUtil.isBlankOrNull(req.getOpenid())){
					resp.errorParam("openid为空");
				}else if(req.getAmount()<=0){
					resp.errorData("提现金额有误");
				}
			}
			
			//获取用户信息
			if(resp.successed()){
				redPacketProcess.applyCash(req.getUid(), req.getOpenid(), req.getAmount());
			}
		}catch (ParamException e) {
			resp.errorParam(e.getMessage());
		}catch (DataException e) {
			resp.errorData(e.getMessage());
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}

		return resp;
	}
	
	/**
	 * 分页获取用户发出的红包列表
	 * @param req
	 * @param page
	 * @param pageSize
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public RedPacketOfUserQueryResp findUserSent(RedPacketSentQueryReq req, String page, String pageSize, String interfaceType, String reqIp){
		RedPacketOfUserQueryResp resp = new RedPacketOfUserQueryResp();
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
				resp.setResult(redPacketProcess.findSent(req.getUid(), pageBean));
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
	 * 分页获取用户领取的红包列表
	 * @param req
	 * @param page
	 * @param pageSize
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public RedPacketOfUserQueryResp findUserReceived(RedPacketReceivedQueryReq req, String page, String pageSize, String interfaceType, String reqIp){
		RedPacketOfUserQueryResp resp = new RedPacketOfUserQueryResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getReceiverId());
			
			//数据校验
			if(resp.successed()){
				if(req.getReceiverId()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}
			}
			
			//处理
			if(resp.successed()){
				PageBean pageBean = new PageBean(page, pageSize);
				resp.setResult(redPacketProcess.findReceived(req.getReceiverId(), pageBean));
			}
		} catch (ParamException e) {
			resp.errorData(e.getMessage());
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		
		return resp;
	}
}
