package com.qcmz.cmc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.dao.IRewardActivityDao;
import com.qcmz.cmc.entity.CmcRewardActivity;
import com.qcmz.cmc.service.IRewardActivityService;

@Service
public class RewardActivityServiceImpl implements IRewardActivityService {
	@Autowired
	private IRewardActivityDao rewardActivityDao;
	
	/**
	 * 获取所有奖励金活动
	 * @return
	 */
	public List<CmcRewardActivity> findActivity(){
		return rewardActivityDao.findActivity();
	}
}
