package com.qcmz.cmc.ws.provide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.process.WalletRechargeProcess;
import com.qcmz.cmc.service.IWalletAccountService;
import com.qcmz.cmc.service.IWalletBillService;
import com.qcmz.cmc.ws.provide.vo.WalletAccountGetReq;
import com.qcmz.cmc.ws.provide.vo.WalletAccountResp;
import com.qcmz.cmc.ws.provide.vo.WalletBillQueryReq;
import com.qcmz.cmc.ws.provide.vo.WalletBillQueryResp;
import com.qcmz.cmc.ws.provide.vo.WalletEncourageReq;
import com.qcmz.cmc.ws.provide.vo.WalletEncourageResp;
import com.qcmz.framework.constant.ExceptionConstants;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.exception.ParamException;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.ws.BaseInterface;
import com.qcmz.srm.client.util.SrmClient;

/**
 * 钱包接口
 */
@Component
public class WalletInterface extends BaseInterface {
	@Autowired
	private IWalletAccountService walletAccountService;
	@Autowired
	private IWalletBillService walletBillService;
	
	@Autowired
	private WalletRechargeProcess walletRechargeProcess;
	
	/**
	 * 获取帐户信息
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public WalletAccountResp getAccount(WalletAccountGetReq req, String interfaceType, String reqIp){
		WalletAccountResp resp = new WalletAccountResp();
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
				resp.setResult(walletAccountService.getAccountInfo(req.getUid()));
			}
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		
		return resp;
	}
	
	/**
	 * 分页查询账单列表
	 * @param req
	 * @param page
	 * @param pageSize
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public WalletBillQueryResp findBill(WalletBillQueryReq req, String interfaceType, String reqIp){
		WalletBillQueryResp resp = new WalletBillQueryResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getBean().getUid());
			
			//数据校验
			if(resp.successed()){
				if(req.getBean().getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}
			}
			
			//处理
			if(resp.successed()){
				PageBean pageBean = new PageBean(1, req.getBean().getPageSize());
				req.getBean().setPageSize(pageBean.getPageSize());
				resp.setResult(walletBillService.findBillInfo(req.getBean()));
			}
			
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		
		return resp;
	}
	
	/**
	 * 激励
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public WalletEncourageResp encourage(WalletEncourageReq req, String interfaceType, String reqIp){
		WalletEncourageResp resp = new WalletEncourageResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getBean().getUid());
			
			//数据校验
			if(resp.successed()){
				if(req.getBean().getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}else if(req.getBean().getEncourageType()==null){
					resp.errorParam("激励类型为空");
				}
			}
			
			//处理
			if(resp.successed()){
				resp.setResult(walletRechargeProcess.encourage(req.getBean().getUid(), req.getBean().getEncourageType(), req.getBean().getOrderId()));
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
}
