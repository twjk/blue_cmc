package com.qcmz.cmc.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.dao.IRewardActivityDao;
import com.qcmz.cmc.entity.CmcRewardActivity;
import com.qcmz.framework.dao.impl.BaseDAO;

@Repository
public class RewardActivityDao extends BaseDAO implements IRewardActivityDao {
	/**
	 * 获取所有奖励金活动
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcRewardActivity> findActivity(){
		return (List<CmcRewardActivity>) qryList("from CmcRewardActivity order by actid desc");
	}
}
