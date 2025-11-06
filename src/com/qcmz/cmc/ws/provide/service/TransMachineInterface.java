package com.qcmz.cmc.ws.provide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.process.TransMachineProcess;
import com.qcmz.cmc.ws.provide.vo.TmGetReq;
import com.qcmz.cmc.ws.provide.vo.TmGetResp;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.framework.ws.BaseInterface;
import com.qcmz.srm.client.util.SrmClient;

@Component
public class TransMachineInterface extends BaseInterface {
	@Autowired
	private TransMachineProcess transMachineProcess;
	
	/**
	 * 获取翻译机信息
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public TmGetResp getTm(TmGetReq req, String interfaceType, String reqIp){
		TmGetResp resp = new TmGetResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getTmcode());
			
			//数据校验
			if(resp.successed()){
				if(StringUtil.isBlankOrNull(req.getTmcode())){
					resp.errorParam("翻译机编号为空");
				}
			}
			
			//处理
			if(resp.successed()){
				resp.setResult(transMachineProcess.getTm(req.getTmcode()));
			}
		} catch (DataException e) {
			resp.errorData(e.getMessage());
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}

		return resp;
	}
}
