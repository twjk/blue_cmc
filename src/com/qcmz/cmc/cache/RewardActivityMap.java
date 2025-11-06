package com.qcmz.cmc.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.entity.CmcRewardActivity;
import com.qcmz.cmc.service.IRewardActivityService;
import com.qcmz.cmc.util.RewardUtil;
import com.qcmz.framework.cache.AbstractCacheMap;
import com.qcmz.framework.constant.SystemConstants;

@Component
public class RewardActivityMap extends AbstractCacheMap {
	@Autowired
	private IRewardActivityService rewardActivityService;
	
	private Map<Long, CmcRewardActivity> map;
	
	private List<CmcRewardActivity> validSpecialList;
	
	@Override @PostConstruct
	protected void init() {
		Map<Long, CmcRewardActivity> mapTemp = new HashMap<Long, CmcRewardActivity>();
		List<CmcRewardActivity> validSpecialListTemp = new ArrayList<CmcRewardActivity>();
		
		List<CmcRewardActivity> list = rewardActivityService.findActivity();
		for (CmcRewardActivity entity : list) {
			mapTemp.put(entity.getActid(), entity);
			if(entity.getActid()>2 && SystemConstants.STATUS_ON.equals(entity.getStatus())){
				validSpecialListTemp.add(entity);
			}
		}
		
		map = mapTemp;
		validSpecialList = validSpecialListTemp;
	}

	/**
	 * 获取活动信息
	 * @param actId
	 * @return
	 */
	public CmcRewardActivity getActivity(Long actId){
		if(safeInit(map)){
			return map.get(actId);
		}
		return null;
	}
	
	/**
	 * 获取有效的活动信息
	 * @param actId
	 * @return
	 */
	public CmcRewardActivity getValidActivity(Long actId){
		CmcRewardActivity act = getActivity(actId);
		if(act!=null && SystemConstants.STATUS_ON.equals(act.getStatus())){
			return act;
		}
		return null;
	}
	
	/**
	 * 获取活动的最高奖励金额，如果活动无效则返回通用奖励活动的最高奖励金额
	 * @param actId
	 * @return
	 */
	public Double getMaxRewardAmount(Long actId){
		CmcRewardActivity act = null;
		if(actId!=null){
			act = getValidActivity(actId);
		}
		if(act==null){
			act = getCommonActivity();
		}
		if(act!=null){
			return RewardUtil.getMaxRewardAmount(act.getInviteramount());
		}
		return 0.0;
	}
	
	/**
	 * 通用奖励活动
	 * @return
	 */
	public CmcRewardActivity getCommonActivity(){
		return getValidActivity(2L);
	}
	
	/**
	 * 安慰奖活动
	 * @return
	 */
	public CmcRewardActivity getEncouragementActivity(){
		return getValidActivity(1L);
	}
	
	/**
	 * 获取特殊奖励列表
	 * @return
	 */
	public List<CmcRewardActivity> findSpecialActivity(){
		if(safeInit(validSpecialList)){
			return validSpecialList;
		}
		return new ArrayList<CmcRewardActivity>();
	}
	
	@Override
	public void update(Object obj) {}
	@Override
	public void delete(Object obj) {}
}
