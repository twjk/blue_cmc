package com.qcmz.cmc.service;

import java.util.Date;
import java.util.List;

import com.qcmz.cmc.entity.CmcLtSpd;
import com.qcmz.cmc.entity.CmcLtSpdtask;

public interface ILocalTaskSpiderTaskService {
	/**
	 * 获取有效的采集任务，带来源
	 * @param maxTime 上次抓取时间<=maxTime
	 * @return
	 */
	public List<CmcLtSpdtask> findValidTaskJoin(Date maxTime);
	/**
	 * 保存采集结果
	 * @param spdTaskId
	 * @param lastIdentify
	 * @param spdList
	 */
	public void saveSpiderResult(Long spdTaskId, String lastIdentify, List<CmcLtSpd> spdList);
}
