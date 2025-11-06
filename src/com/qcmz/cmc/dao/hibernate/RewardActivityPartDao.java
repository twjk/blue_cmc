package com.qcmz.cmc.dao.hibernate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.dao.IRewardActivityPartDao;
import com.qcmz.cmc.entity.CmcRewardPart;
import com.qcmz.cmc.ws.provide.vo.RewardInviteBean;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.dao.impl.BaseDAO;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.StringUtil;

@Repository
public class RewardActivityPartDao extends BaseDAO implements IRewardActivityPartDao {
	/**
	 * 获取用户参加活动信息
	 * @param actId
	 * @param inviteBean
	 * @return
	 */
	public CmcRewardPart getPart(Long actId, RewardInviteBean inviteBean){
		String hql = new StringBuilder("from CmcRewardPart where actid=:actid")
				.append(" and userid=:userid and inviteeid=:inviteeid")
				.append(" and subservicetype=:subservicetype and serviceid=:serviceid")
				.toString();
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("actid", actId);
		params.put("userid", inviteBean.getInviterId());
		params.put("inviteeid", inviteBean.getInviteeId());
		params.put("subservicetype", inviteBean.getSubServiceType());
		params.put("serviceid", inviteBean.getServiceId());
		
		return (CmcRewardPart) load(hql, params);
	}
	
	/**
	 * 获取用户参加活动次数
	 * @param userId
	 * @param actId
	 * @param partFreq
	 * @return
	 */
	public Long getPartTimes(Long userId, Long actId, Integer partFreq){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("select count(partid) from CmcRewardPart")
			 .append(" where userid=:userid and actid=:actid ")
		;
		params.put("userid", userId);
		params.put("actid", actId);
		
		Date now = new Date();
		
		if(DictConstants.DICT_ACTPARTFREQ_NONE.equals(partFreq)){			
		}else if(DictConstants.DICT_ACTPARTFREQ_MINUTE.equals(partFreq)){
			sbHql.append(" and DATE_FORMAT(parttime, '%Y%m%d%H%i')=:parttime");
			params.put("parttime", DateUtil.format(now, "yyyyMMddHHmm"));
		}else if(DictConstants.DICT_ACTPARTFREQ_HOUR.equals(partFreq)){
			sbHql.append(" and DATE_FORMAT(parttime, '%Y%m%d%H')=:parttime");
			params.put("parttime", DateUtil.format(now, "yyyyMMddHH"));
		}else if(DictConstants.DICT_ACTPARTFREQ_DAY.equals(partFreq)){
			sbHql.append(" and DATE_FORMAT(parttime, '%Y%m%d')=:parttime");
			params.put("parttime", DateUtil.format(now, "yyyyMMdd"));
		}else if(DictConstants.DICT_ACTPARTFREQ_WEEK.equals(partFreq)){
			sbHql.append(" and DATE_FORMAT(parttime, '%Y%U')=:parttime");
			
			//mysql周为(00..53)，java为(1..54)，需要转化处理
			int weekOfYear = DateUtil.weekOfYear(now);
			String partTime = DateUtil.format(now, "yyyy")+StringUtil.fillZero(weekOfYear-1,2);
			params.put("parttime", partTime);
		}else if(DictConstants.DICT_ACTPARTFREQ_MONTH.equals(partFreq)){
			sbHql.append(" and DATE_FORMAT(parttime, '%Y%m')=:parttime");
			params.put("parttime", DateUtil.format(now, "yyyyMM"));
		}
		
		return (Long) load(sbHql.toString(), params);
	}
}
