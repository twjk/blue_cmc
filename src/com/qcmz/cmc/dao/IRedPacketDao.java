package com.qcmz.cmc.dao;

import java.util.List;
import java.util.Map;

import com.qcmz.cmc.entity.CmcRpReceive;
import com.qcmz.framework.dao.IBaseDAO;
import com.qcmz.framework.page.PageBean;

public interface IRedPacketDao extends IBaseDAO {
	/**
	 * 获取红包领取记录
	 * @param packetId
	 * @return
	 */
	public List<CmcRpReceive> findReceived(String packetId);
	/**
	 * 获取红包未领取记录
	 * @param packetId
	 * @return
	 */
	public List<CmcRpReceive> findUnReceive(String packetId);
	/**
	 * 获取用户发出的红包列表
	 * @param map
	 * @param pageBean<CmcRpPacket>
	 */
	public void findPacket(Map<String, String> map, PageBean pageBean);
	/**
	 * 分页获取用户发出的红包列表
	 * @param userId
	 * @param pageBean<CmcRpPacket>
	 */
	public void findSent(Long userId, PageBean pageBean);
	/**
	 * 分页获取用户领取的红包列表
	 * @param map
	 * @param pageBean<CmcRpReceive>
	 */
	public void findReceived(Map<String, String> map, PageBean pageBean);
	/**
	 * 分页获取用户领取的红包列表
	 * @param userId
	 * @param pageBean<CmcRpReceive>
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
	 * 获取用户领取红包信息
	 * @param packetId
	 * @param receiverId
	 * @return
	 */
	public CmcRpReceive getReceive(String packetId, Long receiverId);
	/**
	 * 获取红包已被领取总额
	 * @param packetId
	 * @return
	 */
	public Double getReceivedAmount(String packetId);
	/**
	 * 更新红包已被领取个数
	 * @param packetId
	 */
	public void updateReceivedNum(String packetId);
	/**
	 * 更新红包状态
	 * @param packetId
	 * @param status
	 */
	public void updateStatus(String packetId, Integer status);
	/**
	 * 删除未领取的过期的红包
	 * @param packetId
	 */
	public void deleteUnReceive(String packetId);
}
