package com.qcmz.cmc.service;

import java.util.List;
import java.util.Map;

import com.qcmz.cmc.entity.CmcRpPacket;
import com.qcmz.cmc.entity.CmcRpReceive;
import com.qcmz.cmc.ws.provide.vo.RedPacketCreateBean;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.page.PageBean;

public interface IRedPacketService {
	/**
	 * 获取红包未领取记录
	 * @param packetId
	 * @return
	 */
	public List<CmcRpReceive> findUnReceive(String packetId);
	/**
	 * 获取红包领取记录
	 * @param packetId
	 * @return
	 */
	public List<CmcRpReceive> findReceived(String packetId);
	/**
	 * 分页获取用户发出的红包列表
	 * @param map
	 * @param pageBean
	 */
	public void findPacket(Map<String, String> map, PageBean pageBean);
	/**
	 * 分页获取用户发出的红包列表---带用户信息
	 * @param map
	 * @param pageBean
	 */
	public void findPacketJoin(Map<String, String> map, PageBean pageBean);
	/**
	 * 分页获取用户发出的红包列表
	 * @param userId
	 * @param pageBean<CmcRpPacket>
	 */
	public void findSent(Long userId, PageBean pageBean);
	/**
	 * 分页获取用户领取的红包列表
	 * @param map
	 * @param pageBean
	 */
	public void findReceived(Map<String, String> map, PageBean pageBean);
	/**
	 * 分页获取用户领取的红包列表
	 * @param userId
	 * @param pageBean
	 */
	public void findReceived(Long receiverId, PageBean pageBean);
	/**
	 * 获取需要做过期处理的红包列表
	 * @return
	 */
	public List<String> findPacket4ExpireDeal();
	/**
	 * 获取需要做取消处理的红包编号列表
	 * @return
	 */
	public List<String> findPacket4CancelDeal();
	/**
	 * 获取红包信息
	 * @param packetId
	 * @return
	 */
	public CmcRpPacket getPacket(String packetId);
	/**
	 * 获取用户领取红包信息
	 * @param packetId
	 * @param receiverId
	 * @return
	 */
	public CmcRpReceive getReceive(String packetId, Long receiverId);
	/**
	 * 创建红包
	 * @param bean
	 * @return
	 * @exception DataException
	 */
	public CmcRpPacket createRedPacket(RedPacketCreateBean bean, String platform, String version);
	/**
	 * 取消红包
	 * @param packetId
	 * @param userId
	 * @param deductAmount
	 */
	public void cancelPacket(String packetId, Long userId, Double deductAmount);
	/**
	 * 支付成功处理
	 * @param packet
	 */
	public void paySuccess(CmcRpPacket packet);
	/**
	 * 更新红包信息
	 * @param entity
	 * @return
	 */
	public void updatePacket(CmcRpPacket entity);
	/**
	 * 领红包
	 * @param entity
	 * @return
	 */
	public void receiveRedPacket(CmcRpReceive entity);
	/**
	 * 过期红包处理
	 * @param packetId
	 */
	public void expireRedPacket(String packetId);
}
