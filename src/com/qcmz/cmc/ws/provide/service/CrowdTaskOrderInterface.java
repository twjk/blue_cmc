package com.qcmz.cmc.ws.provide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.process.CrowdTaskOrderProcess;
import com.qcmz.cmc.service.ICrowdTaskOrderService;
import com.qcmz.cmc.ws.provide.vo.CrowdTaskOrderAddReq;
import com.qcmz.cmc.ws.provide.vo.CrowdTaskOrderDetailResp;
import com.qcmz.cmc.ws.provide.vo.CrowdTaskOrderQueryReq;
import com.qcmz.cmc.ws.provide.vo.CrowdTaskOrderQueryResp;
import com.qcmz.cmc.ws.provide.vo.OrderCreateResp;
import com.qcmz.cmc.ws.provide.vo.OrderGetReq;
import com.qcmz.framework.constant.ExceptionConstants;
import com.qcmz.framework.exception.ParamException;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.BeanUtil;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.framework.ws.BaseInterface;
import com.qcmz.srm.client.util.SrmClient;

/**
 * 众包任务订单
 */
@Component
public class CrowdTaskOrderInterface extends BaseInterface {
	@Autowired
	private CrowdTaskOrderProcess crowdTaskOrderProcess;
	@Autowired
	private ICrowdTaskOrderService crowdTaskOrderService;
	
	/**
	 * 创建机器配音订单
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public OrderCreateResp addCrowdTask(CrowdTaskOrderAddReq req, String interfaceType, String reqIp){
		OrderCreateResp resp = new OrderCreateResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getBean().getUid());			
			
			//数据校验
			if(resp.successed()){
				if(req.getBean().getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}else if(StringUtil.isBlankOrNull(req.getBean().getContent())){
					resp.errorData("任务说明为空");
				}else if(req.getBean().getPrice()<=0){
					resp.errorParam("任务单价有误");
				}else if(req.getBean().getPeopleNum()<=0){
					resp.errorParam("参与人数有误");
				}
			}
			
			//处理
			if(resp.successed()){
				String platform = SrmClient.getPlatform(req.getAuthAccount());
				String serviceType = SrmClient.getServiceType(req.getAuthAccount());
				BeanUtil.copyProperties(resp, crowdTaskOrderProcess.addCrowdTask(req.getBean(), serviceType, platform, req.getAppVer()));
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
	 * 获取众包任务订单详细信息
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public CrowdTaskOrderDetailResp getCrowdTask(OrderGetReq req, String interfaceType, String reqIp){
		CrowdTaskOrderDetailResp resp = new CrowdTaskOrderDetailResp();
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
				resp.setResult(crowdTaskOrderService.getDetail(req.getOrderId(), req.getUid()));
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
	 * 分页用户众包任务订单列表
	 * @param req
	 * @param page
	 * @param pageSize
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public CrowdTaskOrderQueryResp findCrowdTask(CrowdTaskOrderQueryReq req, String pageSize, String interfaceType, String reqIp){
		CrowdTaskOrderQueryResp resp = new CrowdTaskOrderQueryResp();
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
				resp.setResult(crowdTaskOrderService.findCrowdTaskInfo(req.getUid(), req.getOrderType(), pageBean.getPageSize(), req.getLastOrderId()));
			}
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		return resp;
	}
}
