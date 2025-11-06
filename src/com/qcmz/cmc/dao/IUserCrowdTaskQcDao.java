package com.qcmz.cmc.dao;

import java.util.List;

import com.qcmz.cmc.entity.CmcCtQc;
import com.qcmz.framework.dao.IBaseDAO;

public interface IUserCrowdTaskQcDao extends IBaseDAO {
	/**
	 * 获取用户任务审校列表
	 * @param utId
	 * @param qcStatus
	 * @return
	 */
	public List<CmcCtQc> findQc(String utId, Integer qcStatus);
	/**
	 * 获取待发放审校奖励的列表
	 * @return
	 */
	public List<Long> findQc4GrantQcReward();
	/**
	 * 获取超时未发放审校奖励的数量
	 * @return
	 */
	public Long getCount4UngrantQcReward();
	/**
	 * 获取用户任务审校
	 * @param utId not null
	 * @param qcStatus not null
	 * @return
	 */
	public CmcCtQc getQc(String utId, Integer qcStatus);
	/**
	 * 获取用户任务审校
	 * @param utId not null
	 * @param qcStatus not null
	 * @return
	 */
	public CmcCtQc getQc(String utId, List<Integer> qcStatuses);
	/**
	 * 更新用户任务审校已审校题目数
	 * @param qcId
	 */
	public void updateQcFinishNum(Long qcId);
}
