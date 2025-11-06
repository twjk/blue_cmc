package com.qcmz.cmc.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.qcmz.cmc.entity.CmcJobArticle;
import com.qcmz.cmc.entity.CmcLtSpd;
import com.qcmz.cmc.entity.CmcLtTask;
import com.qcmz.framework.page.PageBean;

public interface ILocalTaskSpiderService {
	/**
	 * 分页查询数据
	 * @param map
	 * @param pageBean<CmcLtSpd>，带来源
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean);
	/**
	 * 分页获取采集列表
	 * @param dealProgress
	 * @param spiderTimeS
	 * @param spiderTimeE
	 * @param pageSize
	 * @param lastSpdId
	 * @return
	 */
	public List<CmcLtSpd> findSpd(String dealProgress, Date spiderTimeS, Date spiderTimeE, int pageSize, Long lastSpdId);
	/**
	 * 获取采集数
	 * @param dealProgress
	 * @param spiderTimeS
	 * @param spiderTimeE
	 * @param lastSpdId
	 * @return
	 */
	public Long getSpdCount(String dealProgress, Date spiderTimeS, Date spiderTimeE, Long lastSpdId);
	/**
	 * 获取采集信息
	 * @param spdId
	 * @return
	 */
	public CmcLtSpd getSpd(Long spdId);
	/**
	 * 获取采集信息，带来源
	 * @param spdId
	 * @return
	 */
	public CmcLtSpd getSpdJoin(Long spdId);
	/**
	 * 是否存在
	 * @param sourceId
	 * @param title
	 * @return
	 */
	public boolean exist(Long sourceId, String title);
	
	/**
	 * 保存采集信息
	 * @param spd
	 */
	public void saveOrUpdate(CmcLtSpd spd);
	/**
	 * 转就业信息
	 * @param spd
	 * @param article
	 */
	public void saveOrUpdate(CmcLtSpd spd, CmcJobArticle article);
	/**
	 * 转就业精选
	 * @param spd
	 * @param tasks
	 */
	public void saveOrUpdate(CmcLtSpd spd, List<CmcLtTask> tasks);
}
