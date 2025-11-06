package com.qcmz.cmc.process;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.entity.CmcPtTrans;
import com.qcmz.cmc.entity.CmcROrder;
import com.qcmz.cmc.entity.CmcTtTrans;
import com.qcmz.cmc.service.IOrderCheckService;
import com.qcmz.cmc.service.IOrderService;
import com.qcmz.cmc.service.ITransPicService;
import com.qcmz.cmc.service.ITransTextService;
import com.qcmz.cmc.util.OrderUtil;
import com.qcmz.cmc.ws.provide.vo.TransPicDealBean;
import com.qcmz.cmc.ws.provide.vo.TransPicTransBean;
import com.qcmz.cmc.ws.provide.vo.TransTextDealBean;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.constant.ExceptionConstants;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.framework.util.log.Operator;

@Component
public class OrderCheckProcess {
	@Autowired
	private IOrderService orderService;
	@Autowired
	private ITransPicService transPicService;
	@Autowired
	private ITransTextService transTextService;	
	@Autowired
	private IOrderCheckService orderCheckService;
	
	
	/**
	 * 保存图片翻译校对结果
	 * @param orderId
	 * @param transResult
	 * @param oper
	 */
	public void saveCheck4Pic(String orderId, List<TransPicTransBean> transResult, String from, String to, Operator oper){
		CmcROrder order = orderService.getOrder(orderId);
		if(order==null){
			throw new DataException(ExceptionConstants.MSG_DATA_NOTEXIST);
		}else if(!DictConstants.DICT_CHECKSTATUS_CHECKING.equals(order.getCheckstatus())){
			throw new DataException(ExceptionConstants.MSG_OPER_CANNOT);
		}
		
		//比较前后翻译结果，看是否做过修订
		boolean thisTimeRevise = false;
		List<CmcPtTrans> transList = transPicService.findTrans(orderId);
		if(transList.size()!=transResult.size()){
			thisTimeRevise = true;
		}else{
			boolean hasMatch = false;
			for (CmcPtTrans trans : transList) {
				hasMatch = false;
				for (TransPicTransBean transBean : transResult) {
					if(trans.getPosx().equals(transBean.getX())
							&& trans.getPosy().equals(transBean.getY())
							&& trans.getDst().equals(transBean.getDst())){
						hasMatch = true;
						break;
					}
				}
				if(!hasMatch){
					thisTimeRevise = true;
					break;
				}
			}
		}

		boolean changeFrom = StringUtil.notBlankAndNull(from) && !from.equals(order.getFromlang());
		boolean changeTo = StringUtil.notBlankAndNull(to) && !to.equals(order.getTolang());
		
		//保存翻译结果
		if(thisTimeRevise || changeFrom || changeTo){
			TransPicDealBean bean = new TransPicDealBean();
			bean.setOrderId(orderId);
			bean.setFrom(from);
			bean.setTo(to);
			bean.setTrans(transResult);
			bean.setOperator(oper.getOperCd());
			bean.setOperatorName(oper.getOperName());
			orderCheckService.saveCheck4Pic(bean, order.getDealstatus());
		}
	}
	
	/**
	 * 完成图片翻译校对
	 * @param orderId
	 * @param transResult
	 * @param oper
	 */
	public void finishCheck4Pic(String orderId, List<TransPicTransBean> transResult, String from, String to, Operator oper){
		CmcROrder order = orderService.getOrder(orderId);
		if(order==null){
			throw new DataException(ExceptionConstants.MSG_DATA_NOTEXIST);
		}else if(!OrderUtil.canFinishCheck(order.getCheckstatus())){
			throw new DataException(ExceptionConstants.MSG_OPER_CANNOT);
		}
		
		Integer orderRevise = order.getRevise();
		
		//比较前后翻译结果，看是否做过修订
		boolean thisTimeRevise = false;
		List<CmcPtTrans> transList = transPicService.findTrans(orderId);
		if(transList.size()!=transResult.size()){
			thisTimeRevise = true;
		}else{
			boolean hasMatch = false;
			for (CmcPtTrans trans : transList) {
				for (TransPicTransBean transBean : transResult) {
					if(trans.getPosx().equals(transBean.getX())
							&& trans.getPosy().equals(transBean.getY())
							&& trans.getDst().equals(transBean.getDst())){
						hasMatch = true;
						break;
					}
				}
				if(!hasMatch){
					thisTimeRevise = true;
					break;
				}
			}
		}

		//翻译结果
		boolean changeFrom = StringUtil.notBlankAndNull(from) && !from.equals(order.getFromlang());
		boolean changeTo = StringUtil.notBlankAndNull(to) && !to.equals(order.getTolang());
		
		TransPicDealBean dealBean = null;
		if(thisTimeRevise || changeFrom || changeTo){
			dealBean = new TransPicDealBean();
			dealBean.setOrderId(orderId);
			dealBean.setFrom(from);
			dealBean.setTo(to);
			dealBean.setTrans(transResult);
			dealBean.setOperator(oper.getOperCd());
			dealBean.setOperatorName(oper.getOperName());
			if(thisTimeRevise){
				orderRevise = SystemConstants.STATUS_ON;
			}
		}
		
		//订单校对信息
		Integer checkStatus = DictConstants.DICT_CHECKSTATUS_CHECKED;		
		if(SystemConstants.STATUS_ON.equals(orderRevise)){
			checkStatus = DictConstants.DICT_CHECKSTATUS_REVISED;
		}
		
		//入库
		orderCheckService.saveFinishCheck4Pic(orderId, order.getDealstatus(), orderRevise, checkStatus, dealBean);
	}
	
	/**
	 * 保存文本翻译校对
	 * @param orderId
	 * @param transResult
	 * @param oper
	 */
	public void saveCheck4Text(String orderId, String transResult, String from, String to, Operator oper){
		CmcROrder order = orderService.getOrder(orderId);
		if(order==null){
			throw new DataException(ExceptionConstants.MSG_DATA_NOTEXIST);
		}else if(!DictConstants.DICT_CHECKSTATUS_CHECKING.equals(order.getCheckstatus())){
			throw new DataException(ExceptionConstants.MSG_OPER_CANNOT);
		}
		
		CmcTtTrans trans = transTextService.getTrans(orderId);
		
		//没有改动，直接返回
		boolean isModifyDst = !trans.getDst().equals(transResult);
		boolean changeFrom = StringUtil.notBlankAndNull(from) && !from.equals(order.getFromlang());
		boolean changeTo = StringUtil.notBlankAndNull(to) && !to.equals(order.getTolang());
		if(!isModifyDst && !changeFrom && !changeTo) return ;
		
		TransTextDealBean dealBean = new TransTextDealBean();
		dealBean.setOrderId(orderId);
		dealBean.setFrom(from);
		dealBean.setTo(to);
		dealBean.setDst(transResult);
		dealBean.setOperator(oper.getOperCd());
		dealBean.setOperatorName(oper.getOperName());		
		orderCheckService.saveCheck4Text(dealBean, order.getDealstatus());
	}
	
	/**
	 * 完成文本翻译校对
	 * @param orderId
	 * @param transResult
	 * @param oper
	 */
	public void finishCheck4Text(String orderId, String transResult, String from, String to, Operator oper){
		CmcROrder order = orderService.getOrder(orderId);
		if(order==null){
			throw new DataException(ExceptionConstants.MSG_DATA_NOTEXIST);
		}else if(!OrderUtil.canFinishCheck(order.getCheckstatus())){
			throw new DataException(ExceptionConstants.MSG_OPER_CANNOT);
		}
		
		Integer orderRevise = order.getRevise();
		
		CmcTtTrans trans = transTextService.getTrans(orderId);
		TransTextDealBean dealBean = null;
		
		boolean isModifyDst = !trans.getDst().equals(transResult);
		boolean changeFrom = StringUtil.notBlankAndNull(from) && !from.equals(order.getFromlang());
		boolean changeTo = StringUtil.notBlankAndNull(to) && !to.equals(order.getTolang());
		if(isModifyDst || changeFrom || changeTo){
			dealBean = new TransTextDealBean();
			dealBean.setOrderId(orderId);
			dealBean.setFrom(from);
			dealBean.setTo(to);
			dealBean.setDst(transResult);
			dealBean.setOperator(oper.getOperCd());
			dealBean.setOperatorName(oper.getOperName());
			if(isModifyDst){
				orderRevise = SystemConstants.STATUS_ON;
			}
		}
		
		Integer checkStatus = DictConstants.DICT_CHECKSTATUS_CHECKED;		
		if(SystemConstants.STATUS_ON.equals(orderRevise)){
			checkStatus = DictConstants.DICT_CHECKSTATUS_REVISED;
		}
		
		orderCheckService.saveFinishCheck4Text(orderId, order.getDealstatus(), orderRevise, checkStatus, dealBean);
	}
}
