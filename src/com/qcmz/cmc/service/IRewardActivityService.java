package com.qcmz.cmc.service;

import java.util.List;

import com.qcmz.cmc.entity.CmcRewardActivity;

public interface IRewardActivityService {
	/**
	 * 获取所有奖励金活动
	 * @return
	 */
	public List<CmcRewardActivity> findActivity();	
}
