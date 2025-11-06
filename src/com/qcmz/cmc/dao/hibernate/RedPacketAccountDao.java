package com.qcmz.cmc.dao.hibernate;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.dao.IRedPacketAccountDao;
import com.qcmz.cmc.entity.CmcRpAccount;
import com.qcmz.framework.dao.impl.BaseDAO;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.NumberUtil;

@Repository
public class RedPacketAccountDao extends BaseDAO implements IRedPacketAccountDao {

	/**
	 * 分页获取红包账户列表
	 * @param map
	 * @param pageBean<CmcRpAccount>
	 * 修改历史：
	 */
	public void findAccount(Map<String, String> map, PageBean pageBean){
		StringBuilder sbHql = new StringBuilder("from CmcRpAccount t");
		StringBuilder sbCountHql = new StringBuilder("select count(t.accountid) from CmcRpAccount t");
		Map<String, Object> params = new HashMap<String, Object>();

		if(map!=null && !map.isEmpty()){
			StringBuilder sbCond = new StringBuilder();
			sbCond.append(" WHERE 1=1");
			
			//查询条件
			//用户编号
			String userid = map.get("userid");
			if(NumberUtil.isNumber(userid)){
				sbCond.append(" and t.userid=:userid");
				params.put("userid", Long.valueOf(userid));
			}
			sbHql.append(sbCond);
			sbCountHql.append(sbCond);
		}

		sbHql.append(" order by t.accountid desc");
		
		getQueryPageBean(sbHql.toString(), sbCountHql.toString(), params, pageBean);
	}
	
	/**
	 * 获取红包帐户信息
	 * @param userId
	 * @return
	 */
	public CmcRpAccount getAccount(Long userId){
		return (CmcRpAccount) load("from CmcRpAccount where userid=?", userId);
	}
	
	/**
	 * 更新用户帐户信息
	 * @param userId
	 */
	public void updateAccount(Long userId){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("update CmcRpAccount t set")
			 .append(" t.applyamount=(select coalesce(sum(cashamount),0) from CmcRpCash c where c.userid=t.userid and c.status=:st1)")
			 .append(" ,t.cashamount=(select coalesce(sum(cashamount),0) from CmcRpCash c where c.userid=t.userid and c.status=:st2)")
			 .append(" ,t.receiveamount=(select coalesce(sum(amount),0) from CmcRpReceive re where re.receiverid=t.userid )")
			 .append(" ,t.receivenum=(select count(receiveid) from CmcRpReceive re where re.receiverid=t.userid)")
			 .append(" ,t.sendamount=(select coalesce(sum(packetamount),0) from CmcRpPacket p where p.userid=t.userid)")
			 .append(" ,t.sendnum=(select count(packetid) from CmcRpPacket p where p.userid=t.userid)")
			 .append(" ,t.balance=(select coalesce(sum(amount),0) from CmcRpBill b where b.userid=t.userid)")
			 .append(" where t.userid=:userid")
			 ;
		params.put("userid", userId);
		params.put("st1", DictConstant.DICT_REDPACKETCASH_STATUS_APPLY);
		params.put("st2", DictConstant.DICT_REDPACKETCASH_STATUS_CASHED);
		
		updateBatch(sbHql.toString(), params);
	}
	
	/**
	 * 更新用户微信openid
	 * @param userId
	 * @param wxopenid
	 */
	public void updateWxopenid(Long userId, String wxopenid){
		String hql = "update CmcRpAccount set wxopenid=:wxopenid where userid=:userid";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("wxopenid", wxopenid);
		params.put("userid", userId);
		updateBatch(hql, params);
	}
	
}
