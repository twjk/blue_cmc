package com.qcmz.cmc.dao;

import java.util.Date;
import java.util.List;

import com.qcmz.cmc.entity.CmcLtSpdtask;
import com.qcmz.framework.dao.IBaseDAO;

public interface ILocalTaskSpiderTaskDao extends IBaseDAO {
	/**
	 * 获取有效的采集任务，带来源
	 * @param maxTime 上次抓取时间<=maxTime
	 * @return
	 */
	public List<CmcLtSpdtask> findValidTaskJoin(Date maxTime);
	/**
	 * 更新采集结果
	 * @param spdTaskId
	 * @param lastIdentify
	 */
	public void updateSpiderResult(Long spdTaskId, String lastIdentify);
}
