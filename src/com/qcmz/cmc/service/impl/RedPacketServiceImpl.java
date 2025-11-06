package com.qcmz.cmc.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.config.SystemConfig;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.dao.IRedPacketAccountDao;
import com.qcmz.cmc.dao.IRedPacketDao;
import com.qcmz.cmc.entity.CmcRpAccount;
import com.qcmz.cmc.entity.CmcRpPacket;
import com.qcmz.cmc.entity.CmcRpReceive;
import com.qcmz.cmc.service.IRedPacketBillService;
import com.qcmz.cmc.service.IRedPacketService;
import com.qcmz.cmc.util.RedPacketUtil;
import com.qcmz.cmc.ws.provide.vo.RedPacketCreateBean;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.DoubleUtil;
import com.qcmz.umc.ws.provide.locator.UserMap;
import com.qcmz.umc.ws.provide.vo.UserSimpleBean;

@Service
public class RedPacketServiceImpl implements IRedPacketService {
	@Autowired
	private IRedPacketDao redPacketDao;
	@Autowired
	private IRedPacketAccountDao redPacketAccountDao;
	
	@Autowired
	private IRedPacketBillService redPacketBillService;

	/**
	 * 获取红包领取记录
	 * @param packetId
	 * @return
	 */
	public List<CmcRpReceive> findReceived(String packetId){
		List<CmcRpReceive> receiveList =  redPacketDao.findReceived(packetId);
		
		//获取用户列表信息
		Set<Long> userIds = new HashSet<Long>();
		for (CmcRpReceive receive : receiveList) {
			userIds.add(receive.getReceiverid());
		}
		Map<Long, UserSimpleBean> userMap = UserMap.findUser(userIds);
		for (CmcRpReceive receive : receiveList) {
			receive.setReceiver(userMap.get(receive.getReceiverid()));
		}
		
		return receiveList;
	}
	
	/**
	 * 获取红包未领取记录
	 * @param packetId
	 * @return
	 */
	public List<CmcRpReceive> findUnReceive(String packetId){
		return redPacketDao.findUnReceive(packetId);
	}
	
	/**
	 * 分页获取用户发出的红包列表
	 * @param map
	 * @param pageBean
	 */
	public void findPacket(Map<String, String> map, PageBean pageBean){
		redPacketDao.findPacket(map, pageBean);
	}
	
	/**
	 * 分页获取用户发出的红包列表---带用户信息
	 * @param map
	 * @param pageBean
	 */
	@SuppressWarnings("unchecked")
	public void findPacketJoin(Map<String, String> map, PageBean pageBean){
		redPacketDao.findPacket(map, pageBean);
		List<CmcRpPacket> list = (List<CmcRpPacket>) pageBean.getResultList();
		if(list.isEmpty())return;
		
		//获取用户信息
		Set<Long> userIds = new HashSet<Long>();
		for (CmcRpPacket packet : list) {
			userIds.add(packet.getUserid());
		}
		Map<Long, UserSimpleBean> userMap = UserMap.findUser(userIds);
		for (CmcRpPacket packet : list) {
			packet.setUser(userMap.get(packet.getUserid()));
		}
	}
	
	/**
	 * 分页获取用户发出的红包列表
	 * @param userId
	 * @param pageBean<CmcRpPacket>
	 */
	public void findSent(Long userId, PageBean pageBean){
		redPacketDao.findSent(userId, pageBean);
	}
	
	/**
	 * 分页获取用户领取的红包列表
	 * @param map
	 * @param pageBean
	 */
	@SuppressWarnings("unchecked")
	public void findReceived(Map<String, String> map, PageBean pageBean){
		redPacketDao.findReceived(map, pageBean);
		List<CmcRpReceive> list = (List<CmcRpReceive>) pageBean.getResultList();
		
		if(list.isEmpty())return;
		
		Set<Long> userIds = new HashSet<Long>();
		for (CmcRpReceive receive : list) {
			userIds.add(receive.getUserid());
		}
		
		//获取用户信息
		Map<Long, UserSimpleBean> userMap = UserMap.findUser(userIds);
		for (CmcRpReceive receive : list) {
			receive.setReceiver(userMap.get(receive.getUserid()));
		}
	}
	
	/**
	 * 分页获取用户领取的红包列表
	 * @param userId
	 * @param pageBean
	 */
	public void findReceived(Long receiverId, PageBean pageBean){
		redPacketDao.findReceived(receiverId, pageBean);
	}
	
	/**
	 * 获取需要做过期处理的红包列表
	 * @return
	 */
	public List<String> findPacket4ExpireDeal(){
		return redPacketDao.findPacket4ExpireDeal();
	}
	
	/**
	 * 获取需要做取消处理的红包编号列表
	 * @return
	 */
	public List<String> findPacket4CancelDeal(){
		return redPacketDao.findPacket4CancelDeal();
	}
	
	/**
	 * 获取红包信息
	 * @param packetId
	 * @return
	 */
	public CmcRpPacket getPacket(String packetId){
		return (CmcRpPacket) redPacketDao.load(CmcRpPacket.class, packetId);
	}
	
	/**
	 * 获取用户领取红包信息
	 * @param packetId
	 * @param receiverId
	 * @return
	 */
	public CmcRpReceive getReceive(String packetId, Long receiverId){
		return redPacketDao.getReceive(packetId, receiverId);
	}
	
	/**
	 * 创建红包
	 * @param bean
	 * @return
	 * @exception DataException
	 */
	public CmcRpPacket createRedPacket(RedPacketCreateBean bean, String platform, String version){
		Date now = new Date();
		
		CmcRpAccount account = redPacketAccountDao.getAccount(bean.getUid());
		if(account==null){
			account = new CmcRpAccount();
			account.setUserid(bean.getUid());
			account.setCreatetime(now);
			account.setWxopenid(bean.getOpenid());
			redPacketAccountDao.save(account);
		}
		
		if(bean.getDeductAmount()>0
				&& account.getBalance()<bean.getDeductAmount()){
			throw new DataException("余额不足");
		}
		
		Double amount = DoubleUtil.add(bean.getPacketAmount(), bean.getServiceAmount());
		double payableAmount = DoubleUtil.subtract(amount, bean.getDeductAmount());
		Date startTime = null;
		Date endTime = null;
		Integer status = DictConstant.DICT_REDPACKET_STATUS_WAITPAY;
		if(payableAmount==0){
			status = DictConstant.DICT_REDPACKET_STATUS_VALID;
			startTime = now;
			endTime = DateUtil.addDay(now, SystemConfig.REDPACKET_VALIDDAYS);
		}
		
		//红包信息
		CmcRpPacket packet = new CmcRpPacket();
		packet.setPacketid(RedPacketUtil.newPacketId(bean.getUid()));
		packet.setUserid(bean.getUid());
		packet.setLangcode(bean.getLangCode());
		packet.setTitle(bean.getTitle());
		packet.setTitlecn(bean.getTitleCn());
		packet.setPacketnum(bean.getPacketNum());
		packet.setReceivednum(0);
		packet.setPacketamount(bean.getPacketAmount());
		packet.setServiceamount(bean.getServiceAmount());
		packet.setDeductamount(bean.getDeductAmount());
		packet.setPayableamount(payableAmount);
		packet.setPayamount(0D);
		packet.setCreatetime(now);
		packet.setStarttime(startTime);
		packet.setEndtime(endTime);
		packet.setPlatform(platform);
		packet.setVersion(version);
		packet.setStatus(status);
		packet.setAmount(amount);
		
		redPacketDao.save(packet);
		
		//待领红包项目
		if(DictConstant.DICT_REDPACKET_STATUS_VALID.equals(status)){
			redPacketDao.saveOrUpdateAll(RedPacketUtil.genReceives(packet));
		}
		
		//账单,更新可用余额
		if(bean.getDeductAmount()>0){
			redPacketBillService.saveSendBill(packet.getUserid(), bean.getDeductAmount(), packet.getPacketid());
		}
		
		//更新计数
		redPacketAccountDao.updateAccount(packet.getUserid());

		return packet;
	}
	
	/**
	 * 更新红包信息
	 * @param entity
	 * @return
	 */
	public void updatePacket(CmcRpPacket entity){
		redPacketDao.update(entity);
	}
	
	/**
	 * 支付成功处理
	 * @param packet
	 */
	public void paySuccess(CmcRpPacket packet){
		updatePacket(packet);
		redPacketDao.saveOrUpdateAll(RedPacketUtil.genReceives(packet));
	}
	
	/**
	 * 领红包，并更新领红包用户的帐户信息
	 * @param entity
	 * @return
	 */
	public void receiveRedPacket(CmcRpReceive entity){
		CmcRpAccount account = redPacketAccountDao.getAccount(entity.getReceiverid());
		if(account==null){
			account = new CmcRpAccount();
			account.setUserid(entity.getReceiverid());
			account.setCreatetime(new Date());
			redPacketDao.save(account);
		}
		
		redPacketDao.update(entity);
		redPacketDao.updateReceivedNum(entity.getPacketid());
		
		redPacketBillService.saveReceiveBill(entity.getReceiverid(), entity.getAmount(), entity.getPacketid());
//		redPacketAccountDao.updateAccount(entity.getUserid());
		redPacketAccountDao.updateAccount(entity.getReceiverid());
	}
	
	/**
	 * 取消红包
	 * @param packetId
	 * @param userId
	 * @param deductAmount
	 */
	public void cancelPacket(String packetId, Long userId, Double deductAmount){
		if(deductAmount>0){
			redPacketBillService.saveUnReceiveBill(userId, deductAmount, packetId);
			redPacketAccountDao.updateAccount(userId);
		}
		
		redPacketDao.updateStatus(packetId, DictConstant.DICT_REDPACKET_STATUS_CANCEL);
	}
	
	/**
	 * 过期红包处理
	 * @param packetId
	 */
	public void expireRedPacket(String packetId){
		CmcRpPacket packet = getPacket(packetId);
		if(packet.getEndtime().getTime()<System.currentTimeMillis()){
			Double receivedAmount = redPacketDao.getReceivedAmount(packetId);
			Double balance = DoubleUtil.subtract(packet.getPacketamount(), receivedAmount);
			if(balance>0){
				redPacketBillService.saveUnReceiveBill(packet.getUserid(), balance, packetId);
				redPacketDao.deleteUnReceive(packetId);
				redPacketAccountDao.updateAccount(packet.getUserid());
			}
			
			redPacketDao.updateStatus(packetId, DictConstant.DICT_REDPACKET_STATUS_EXPIRED);
		}
	}
}
