package com.qcmz.cmc.ws.provide.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.ws.provide.service.TransTextDealInterface;
import com.qcmz.cmc.ws.provide.vo.OrderDealGetReq;
import com.qcmz.cmc.ws.provide.vo.OrderDealQueryReq;
import com.qcmz.cmc.ws.provide.vo.TransTextFinishReq;
import com.qcmz.cmc.ws.provide.vo.TransTextTransReq;
import com.qcmz.framework.action.BaseWSAction;
import com.qcmz.framework.util.StringUtil;

/**
 * 类说明：短文快译处理
 * 修改历史：
 */
public class TransTextDealWSAction extends BaseWSAction {
	@Autowired
	private TransTextDealInterface transTextDealInterface;
	
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
	 * 源语言
	 */
	private String from;
	/**
	 * 目标语言
	 */
	private String to;
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
	 * 预计处理时长，秒
	 */
	private Long needTime;
	
	/**
	 * 分页获取短文快译列表
	 * @return
	 * 修改历史：
	 */
	public String findText(){
		OrderDealQueryReq req = new OrderDealQueryReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.getBean().setUid(uid);
		req.getBean().setDealProgress(dealProgress);
		req.getBean().setLangs(StringUtil.split2List(langs, ";"));
		req.getBean().setOperator(operator);
		req.getBean().setMoreBaseId(moreBaseId);
		
		jsonObj = transTextDealInterface.findText(req, pageSize, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 获取短文快译详细信息
	 * @return
	 * 修改历史：
	 */
	public String getText(){
		OrderDealGetReq req = new OrderDealGetReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setOrderId(orderId);
		
		jsonObj = transTextDealInterface.getText(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 保存翻译结果
	 * @return
	 * 修改历史：
	 */
	public String saveTrans(){
		TransTextTransReq req = new TransTextTransReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.getBean().setOrderId(orderId);
		req.getBean().setFrom(from);
		req.getBean().setTo(to);
		req.getBean().setDst(dst);
		req.getBean().setNeedTime(needTime);
		req.getBean().setOperator(operator);
		req.getBean().setOperatorName(operatorName);
		
		jsonObj = transTextDealInterface.saveTrans(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 完成翻译
	 * @return
	 * 修改历史：
	 */
	public String finishTrans(){
		TransTextFinishReq req = new TransTextFinishReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.getBean().setOrderId(orderId);
		req.getBean().setFrom(from);
		req.getBean().setTo(to);
		req.getBean().setDst(dst);
		req.getBean().setOperator(operator);
		req.getBean().setOperatorName(operatorName);
		
		jsonObj = transTextDealInterface.finishTrans(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}

	public Long getNeedTime() {
		return needTime;
	}

	public void setNeedTime(Long needTime) {
		this.needTime = needTime;
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

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}
}
