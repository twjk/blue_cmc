package com.qcmz.cmc.ws.provide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.process.ArticleOrderProcess;
import com.qcmz.cmc.ws.provide.vo.ArticleSubReq;
import com.qcmz.cmc.ws.provide.vo.OrderCreateResp;
import com.qcmz.cmc.ws.provide.vo.OrderCreateResult;
import com.qcmz.framework.constant.ExceptionConstants;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.exception.ParamException;
import com.qcmz.framework.util.BeanUtil;
import com.qcmz.framework.ws.BaseInterface;
import com.qcmz.srm.client.util.SrmClient;

/**
 * 资讯订单接口
 */
@Component
public class ArticleOrderInterface extends BaseInterface{
	@Autowired
	private ArticleOrderProcess articleOrderProcess;
	
	/**
	 * 资讯订阅
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public OrderCreateResp subArticle(ArticleSubReq req, String interfaceType, String reqIp){
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
				}else if(req.getBean().getItemId()==null){
					resp.errorParam("商品编号为空");
				}else if(req.getBean().getItemPrice()==null){
					resp.errorParam("商品售价为空");
				}
			}
			
			//处理
			if(resp.successed()){
				String platform = SrmClient.getPlatform(req.getAuthAccount());
				String serviceType = SrmClient.getServiceType(req.getAuthAccount());
				OrderCreateResult r = articleOrderProcess.subArticle(req.getBean(), serviceType, platform, req.getAppVer());
				BeanUtil.copyProperties(resp, r);
			}
		} catch (DataException e) {
			resp.errorData(e.getMessage());
		} catch (ParamException e) {
			resp.errorParam(e.getMessage());
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		
		return resp;
	}
}
