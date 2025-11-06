package com.qcmz.cmc.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.dao.IRedPacketDao;
import com.qcmz.cmc.entity.CmcRpReceive;
import com.qcmz.framework.dao.impl.BaseDAO;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.NumberUtil;
import com.qcmz.framework.util.StringUtil;

@Repository
public class RedPacketDao extends BaseDAO implements IRedPacketDao {
	/**
	 * 获取红包领取记录
	 * @param packetId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcRpReceive> findReceived(String packetId){
		String hql = "from CmcRpReceive where packetid=? and receiverid>0";
		return (List<CmcRpReceive>) qryList(hql, packetId);
	}
	
	/**
	 * 获取红包未领取记录
	 * @param packetId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcRpReceive> findUnReceive(String packetId){
		String hql = "from CmcRpReceive where packetid=? and receiverid is null";
		return (List<CmcRpReceive>) qryList(hql, packetId);
	}
	
	/**
	 * 获取用户发出的红包列表
	 * @param userId
	 * @param pageBean<CmcRpPacket>
	 */
	public void findPacket(Map<String, String> map, PageBean pageBean){
		StringBuilder sbHql = new StringBuilder("from CmcRpPacket t");
		StringBuilder sbCountHql = new StringBuilder("select count(t.packetid) from CmcRpPacket t");
		Map<String, Object> params = new HashMap<String, Object>();

		if(map!=null && !map.isEmpty()){
			StringBuilder sbCond = new StringBuilder();
			
			//查询条件
			//用户编号
			String userid = map.get("userid");
			if(NumberUtil.isNumber(userid)){
				sbCond.append(" and t.userid=:userid");
				params.put("userid", Long.valueOf(userid));
			}
			
			//暗语
			String title = map.get("title");
			if(StringUtil.notBlankAndNull(title)){
				sbCond.append(" and t.title like :title");
				params.put("title", "%"+title+"%");
			}
			
			//状态
			String status = map.get("status");
			if(NumberUtil.isNumber(status)){
				sbCond.append(" and t.status=:status");
				params.put("status", Integer.valueOf(status));
			}
						
			//红包编号
			String packetid = map.get("packetid");
			if(StringUtil.notBlankAndNull(packetid)){
				sbCond.append(" and t.packetid like :packetid");
				params.put("packetid", packetid+"%");
			}
			
			if(sbCond.length()>4){
				sbCond.replace(1, 4, "where");
				
				sbHql.append(sbCond);
				sbCountHql.append(sbCond);
			}
		}
		
		sbHql.append(" order by t.packetid desc");
		
		getQueryPageBean(sbHql.toString(), sbCountHql.toString(), params, pageBean);
	}
	
	/**
	 * 获取用户发出的红包列表
	 * @param userId
	 * @param pageBean<CmcRpPacket>
	 */
	public void findSent(Long userId, PageBean pageBean){
		StringBuilder sbHql = new StringBuilder();
		StringBuilder sbCountHql = new StringBuilder("select count(packetid)");
		
		StringBuilder sbCond = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbCond.append("from CmcRpPacket where userid=:userid");
		params.put("userid", userId);
		
		sbHql.append(sbCond);
		sbCountHql.append(sbCond);
		
		sbHql.append(" order by packetid desc");
		
		getQueryPageBean(sbHql.toString(), sbCountHql.toString(), params, pageBean);
	}
	
	/**
	 * 获取用户领取的红包列表
	 * @param map
	 * @param pageBean<CmcRpReceive>
	 */
	public void findReceived(Map<String, String> map, PageBean pageBean){
		StringBuilder sbHql = new StringBuilder("from CmcRpReceive t");
		StringBuilder sbCountHql = new StringBuilder("select count(t.receiveid) from CmcRpReceive t");
		Map<String, Object> params = new HashMap<String, Object>();

		StringBuilder sbCond = new StringBuilder();
		sbCond.append(" where receiverid>0");
		if(map!=null && !map.isEmpty()){
			//查询条件
			//用户编号
			String userid = map.get("userid");
			if(NumberUtil.isNumber(userid)){
				sbCond.append(" and t.userid=:userid");
				params.put("userid", Long.valueOf(userid));
			}
		}
		sbCountHql.append(sbCond);
		sbHql.append(sbCond).append(" order by receivetime desc");
		
		getQueryPageBean(sbHql.toString(), sbCountHql.toString(), params, pageBean);
	}
	
	
	/**
	 * 获取用户领取的红包列表
	 * @param userId
	 * @param pageBean<CmcRpReceive>
	 */
	public void findReceived(Long receiverId, PageBean pageBean){
		StringBuilder sbHql = new StringBuilder();
		StringBuilder sbCountHql = new StringBuilder("select count(receiveid)");
		
		StringBuilder sbCond = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbCond.append("from CmcRpReceive where receiverid=:receiverid");
		params.put("receiverid", receiverId);
		
		sbHql.append(sbCond);
		sbCountHql.append(sbCond);
		
		sbHql.append(" order by receivetime desc");
		
		getQueryPageBean(sbHql.toString(), sbCountHql.toString(), params, pageBean);
	}
	
	/**
	 * 获取需要做过期处理的红包编号列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> findPacket4ExpireDeal(){
		String hql = "select packetid from CmcRpPacket where status=? and endtime<now()";
		return (List<String>) qryList(hql, DictConstant.DICT_REDPACKET_STATUS_VALID);
	}
	
	/**
	 * 获取需要做取消处理的红包编号列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> findPacket4CancelDeal(){
		String hql = "select packetid from CmcRpPacket where status=:status and TIMESTAMPDIFF(minute, createtime, now())>:minute";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", DictConstant.DICT_REDPACKET_STATUS_WAITPAY);
		params.put("minute", 60);
		
		return (List<String>) qryListByMap(hql, params);
	}
	
	
	/**
	 * 获取用户领取红包信息
	 * @param packetId
	 * @param receiverId
	 * @return
	 */
	public CmcRpReceive getReceive(String packetId, Long receiverId){
		String hql = "from CmcRpReceive where packetid=:packetid and receiverid=:receiverid";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("packetid", packetId);
		params.put("receiverid", receiverId);
		return (CmcRpReceive) load(hql, params);
	}
	
	/**
	 * 获取红包已被领取总额
	 * @param packetId
	 * @return
	 */
	public Double getReceivedAmount(String packetId){
		String hql = "select coalesce(sum(amount),0) from CmcRpReceive where packetid=? and receiverid>0";
		return (Double) load(hql, packetId);
	}
	
	/**
	 * 更新红包已被领取个数
	 * @param packetId
	 */
	public void updateReceivedNum(String packetId){
		StringBuilder sbHql = new StringBuilder();
		sbHql.append("update CmcRpPacket t set")
			 .append(" t.receivednum=(select count(receiveid) from CmcRpReceive r where r.packetid=t.packetid and r.receiverid>0)")
			 .append(" where t.packetid=?")
			 ;
		update(sbHql.toString(), packetId);
	}
	
	/**
	 * 更新红包状态
	 * @param packetId
	 * @param status
	 */
	public void updateStatus(String packetId, Integer status){
		String hql = "update CmcRpPacket set status=? where packetid=?";
		update(hql, new Object[]{status, packetId});
	}
	
	/**
	 * 删除未领取的过期的红包
	 * @param packetId
	 */
	public void deleteUnReceive(String packetId){
		String hql = "delete from CmcRpReceive where packetid=? and receiverid is null";
		delete(hql, packetId);
	}
}
