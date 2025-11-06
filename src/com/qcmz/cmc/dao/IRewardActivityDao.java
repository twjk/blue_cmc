package com.qcmz.cmc.dao;

import java.util.List;

import com.qcmz.cmc.entity.CmcRewardActivity;
import com.qcmz.framework.dao.IBaseDAO;

public interface IRewardActivityDao extends IBaseDAO {
	/**
	 * 获取所有奖励金活动
	 * @return
	 */
	public List<CmcRewardActivity> findActivity();
}
