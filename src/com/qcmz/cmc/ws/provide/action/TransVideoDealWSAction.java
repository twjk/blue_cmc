package com.qcmz.cmc.ws.provide.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.ws.provide.service.TransVideoDealInterface;
import com.qcmz.cmc.ws.provide.vo.OrderDealQueryReq;
import com.qcmz.cmc.ws.provide.vo.TransVideoConnectedReq;
import com.qcmz.cmc.ws.provide.vo.TransVideoFinishReq;
import com.qcmz.cmc.ws.provide.vo.TransVideoStartBillingReq;
import com.qcmz.framework.action.BaseWSAction;
import com.qcmz.framework.util.StringUtil;

/**
 * 类说明：视频口译处理
 * 修改历史：
 */
public class TransVideoDealWSAction extends BaseWSAction {
	@Autowired
	private TransVideoDealInterface transVideoDealInterface;
	
	/**
	 * 操作员用户名
	 */
	private String operator;
	/**
	 * 操作员姓名
	 */
	private String operatorName;
	/**
	 * 订单编号
	 */
	private String orderId;
	/**
	 * 排序标识
	 */
	private String moreBaseId;
	/**
	 * 用户编号
	 */
	private Long uid;
	/**
	 * 译文
	 */
	private String dst;
	/**
	 * 处理进度
	 */
	private String dealProgress;
	/**
	 * 语言代码，多个用;分隔
	 */
	private String langs;
	/**
	 * 每页记录数
	 */
	private String pageSize;
	
	/**
	 * 分页获取视频口译列表
	 * @return
	 * 修改历史：
	 */
	public String findVideo(){
		OrderDealQueryReq req = new OrderDealQueryReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.getBean().setDealProgress(dealProgress);
		req.getBean().setUid(uid);
		req.getBean().setOperator(operator);
		req.getBean().setMoreBaseId(moreBaseId);
		req.getBean().setLangs(StringUtil.split2List(langs, ";"));
		
		jsonObj = transVideoDealInterface.findVideo(req, pageSize, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 接通用户
	 * @return
	 * 修改历史：
	 */
	public String connected(){
		TransVideoConnectedReq req = new TransVideoConnectedReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.getBean().setOrderId(orderId);
		req.getBean().setOperator(operator);
		req.getBean().setOperatorName(operatorName);
		
		jsonObj = transVideoDealInterface.connected(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 开始计费
	 * @return
	 * 修改历史：
	 */
	public String startBilling(){
		TransVideoStartBillingReq req = new TransVideoStartBillingReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.getBean().setOrderId(orderId);
		req.getBean().setOperator(operator);
		req.getBean().setOperatorName(operatorName);
		
		jsonObj = transVideoDealInterface.startBilling(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 接通用户并开始计费
	 * @return
	 * 修改历史：
	 */
	public String connectedAndBilling(){
		TransVideoStartBillingReq req = new TransVideoStartBillingReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.getBean().setOrderId(orderId);
		req.getBean().setOperator(operator);
		req.getBean().setOperatorName(operatorName);
		
		jsonObj = transVideoDealInterface.connectedAndBilling(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 完成翻译
	 * @return
	 * 修改历史：
	 */
	public String finishTrans(){
		TransVideoFinishReq req = new TransVideoFinishReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.getBean().setOrderId(orderId);
		req.getBean().setOperator(operator);
		req.getBean().setOperatorName(operatorName);
		
		jsonObj = transVideoDealInterface.finishTrans(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getMoreBaseId() {
		return moreBaseId;
	}

	public void setMoreBaseId(String moreBaseId) {
		this.moreBaseId = moreBaseId;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getDst() {
		return dst;
	}

	public void setDst(String dst) {
		this.dst = dst;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getDealProgress() {
		return dealProgress;
	}

	public void setDealProgress(String dealProgress) {
		this.dealProgress = dealProgress;
	}

	public String getLangs() {
		return langs;
	}

	public void setLangs(String langs) {
		this.langs = langs;
	}
}
