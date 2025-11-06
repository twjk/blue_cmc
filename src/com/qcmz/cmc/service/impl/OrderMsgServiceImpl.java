package com.qcmz.cmc.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.comparator.OrderMsgComparator;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.dao.IOrderMsgDao;
import com.qcmz.cmc.entity.CmcRMsg;
import com.qcmz.cmc.service.IOrderMsgService;
import com.qcmz.cmc.util.BeanConvertUtil;
import com.qcmz.cmc.ws.provide.vo.OrderMsgBean;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.NumberUtil;
import com.qcmz.framework.util.log.Operator;
import com.qcmz.umc.ws.provide.locator.UserMap;
import com.qcmz.umc.ws.provide.vo.UserSimpleBean;

@Service
public class OrderMsgServiceImpl implements IOrderMsgService {
	@Autowired
	private IOrderMsgDao orderMsgDao;
	
	/**
	 * 分页获取以订单为单位最新留言列表，带订单信息
	 * @param map
	 * @param pageBean<CmcRMsg>
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean){
		String sideType = map.get("sidetype");
		String orderId = map.get("orderid");

		String uid = map.get("userid");
		Long userId = null;
		if(NumberUtil.isNumber(uid)){
			userId = Long.valueOf(uid);
		}
		
		orderMsgDao.findRecentMsg(sideType, userId, orderId, pageBean);
		
		List<CmcRMsg> resultList = (List<CmcRMsg>) pageBean.getResultList();
		if(resultList.isEmpty()) return;
		
		//封装用户信息
		Set<Long> userIds = new HashSet<Long>();
		for (CmcRMsg msg : resultList) {
			userIds.add(msg.getCmcROrder().getUserid());
		}
		Map<Long, UserSimpleBean> userMap = UserMap.findUser(userIds);
		for (CmcRMsg msg : resultList) {
			msg.getCmcROrder().setUser(userMap.get(msg.getCmcROrder().getUserid()));
		}
	}
	
	/**
	 * 以订单为单位分页获取用户最新留言列表
	 * @param userId
	 * @param pageBean
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public List<OrderMsgBean> findRecentMsg4User(Long userId, PageBean pageBean){
		orderMsgDao.findRecentMsg(null, userId, null, pageBean);
		
		return BeanConvertUtil.toOrderMsgBean((List<CmcRMsg>) pageBean.getResultList());
	}
	
	/**
	 * 分页获取留言列表，倒序
	 * @param orderId
	 * @param pageSize
	 * @param lastId
	 * @return
	 * 修改历史：
	 */
	public List<OrderMsgBean> findMsg(String orderId, int pageSize, Long lastId){
		return BeanConvertUtil.toOrderMsgBean(orderMsgDao.findMsg(orderId, null, pageSize, lastId));
	}
	
	/**
	 * 分页获取指定订单的留言列表
	 * 倒序分页获取历史留言列表，当前页结果再按正序排序
	 * @param orderId
	 * @param pageSize
	 * @param lastId
	 * @return
	 * 修改历史：
	 */
	public List<OrderMsgBean> findMsg4User(String orderId, Long userId, int pageSize, Long lastId){
		List<CmcRMsg> msgList = orderMsgDao.findMsg(orderId, userId, pageSize, lastId);
		
		List<OrderMsgBean> result = BeanConvertUtil.toOrderMsgBean(msgList);

		Collections.sort(result, new OrderMsgComparator());
		
		return result;
	}
	
	/**
	 * 分页获取指定订单的新留言列表
	 * 倒序分页获取新的留言列表，当前页结果再按正序排序
	 * @param orderId
	 * @param pageSize
	 * @param lastId
	 * @return
	 * 修改历史：
	 */
	public List<OrderMsgBean> findLastMsg4User(String orderId, Long userId, int pageSize, Long lastId){
		List<CmcRMsg> msgList = orderMsgDao.findLastMsg(orderId, userId, pageSize, lastId);
		
		List<OrderMsgBean> result = BeanConvertUtil.toOrderMsgBean(msgList);

		Collections.sort(result, new OrderMsgComparator());
		
		return result;
	}
	
	/**
	 * 添加用户留言
	 * @param userId
	 * @param orderId
	 * @param msg
	 * 修改历史：
	 */
	public void saveMsgOfUser(String orderId, Long userId, String msg){
		CmcRMsg cmcRMsg = new CmcRMsg();
		cmcRMsg.setOrderid(orderId);
		cmcRMsg.setMsg(msg);
		cmcRMsg.setCreatetime(new Date());
		cmcRMsg.setSidetype(DictConstant.DICT_SIDETYPE_USER);
		cmcRMsg.setMsgcd(String.valueOf(userId));
		cmcRMsg.setMsgname(SystemConstants.OPERATOR_USER);
		orderMsgDao.save(cmcRMsg);
	}
	
	/**
	 * 添加客服留言
	 * @param orderId
	 * @param msg
	 * @param oper
	 * 修改历史：
	 */
	public void saveMsgOfCs(String orderId, String msg, Operator oper){		
		CmcRMsg cmcRMsg = new CmcRMsg();
		cmcRMsg.setOrderid(orderId);
		cmcRMsg.setMsg(msg);
		cmcRMsg.setCreatetime(new Date());
		cmcRMsg.setSidetype(DictConstant.DICT_SIDETYPE_CS);
		cmcRMsg.setMsgcd(oper.getOperCd());
		cmcRMsg.setMsgname(oper.getOperName());
		orderMsgDao.save(cmcRMsg);
	}
}
