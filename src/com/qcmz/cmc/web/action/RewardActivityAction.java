package com.qcmz.cmc.web.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.cache.RewardActivityMap;
import com.qcmz.cmc.entity.CmcRewardActivity;
import com.qcmz.framework.action.BaseAction;

public class RewardActivityAction extends BaseAction {
	@Autowired
	private RewardActivityMap rewardActivityMap;
	
	private List<CmcRewardActivity> list;
	
	public String doSelect(){
		list = rewardActivityMap.findSpecialActivity();
		return "select";
	}

	public List<CmcRewardActivity> getList() {
		return list;
	}

	public void setList(List<CmcRewardActivity> list) {
		this.list = list;
	}
	
}
