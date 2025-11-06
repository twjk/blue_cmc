package com.qcmz.cmc.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.dao.ILocalTaskSpiderDao;
import com.qcmz.cmc.entity.CmcJobArticle;
import com.qcmz.cmc.entity.CmcLtSpd;
import com.qcmz.cmc.entity.CmcLtTask;
import com.qcmz.cmc.service.IJobArticleService;
import com.qcmz.cmc.service.ILocalTaskService;
import com.qcmz.cmc.service.ILocalTaskSpiderService;
import com.qcmz.framework.page.PageBean;

@Service
public class LocalTaskSpiderServiceImpl implements ILocalTaskSpiderService {
	@Autowired
	private ILocalTaskSpiderDao localTaskSpiderDao;
	@Autowired
	private IJobArticleService jobArticleService;
	@Autowired
	private ILocalTaskService localTaskService;
	
	/**
	 * 分页查询数据
	 * @param map
	 * @param pageBean<CmcLtSpd>，带来源
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean){
		localTaskSpiderDao.queryByMapTerm(map, pageBean);
	}
	
	/**
	 * 分页获取采集列表
	 * @param dealProgress
	 * @param spiderTimeS
	 * @param spiderTimeE
	 * @param pageSize
	 * @param lastSpdId
	 * @return
	 */
	public List<CmcLtSpd> findSpd(String dealProgress, Date spiderTimeS, Date spiderTimeE, int pageSize, Long lastSpdId){
		return localTaskSpiderDao.findSpd(dealProgress, spiderTimeS, spiderTimeE, pageSize, lastSpdId);
	}
	
	/**
	 * 获取采集数
	 * @param dealProgress
	 * @param spiderTimeS
	 * @param spiderTimeE
	 * @param lastSpdId
	 * @return
	 */
	public Long getSpdCount(String dealProgress, Date spiderTimeS, Date spiderTimeE, Long lastSpdId){
		return localTaskSpiderDao.getSpdCount(dealProgress, spiderTimeS, spiderTimeE, lastSpdId);
	}
	
	/**
	 * 获取采集信息
	 * @param spdId
	 * @return
	 */
	public CmcLtSpd getSpd(Long spdId){
		return (CmcLtSpd) localTaskSpiderDao.load(CmcLtSpd.class, spdId);
	}
	
	/**
	 * 获取采集信息，带来源
	 * @param spdId
	 * @return
	 */
	public CmcLtSpd getSpdJoin(Long spdId){
		return localTaskSpiderDao.getSpdJoin(spdId);
	}
	
	/**
	 * 是否存在
	 * @param sourceId
	 * @param title
	 * @return
	 */
	public boolean exist(Long sourceId, String title){
		return localTaskSpiderDao.exist(sourceId, title);
	}
	
	/**
	 * 保存采集信息
	 * @param spd
	 */
	public void saveOrUpdate(CmcLtSpd spd){
		localTaskSpiderDao.saveOrUpdate(spd);
	}
	
	/**
	 * 转就业信息
	 * @param spd
	 * @param article
	 */
	public void saveOrUpdate(CmcLtSpd spd, CmcJobArticle article){
		jobArticleService.saveOrUpdate(article);
		localTaskSpiderDao.saveOrUpdate(spd);
	}
	
	/**
	 * 转就业精选
	 * @param spd
	 * @param tasks
	 */
	public void saveOrUpdate(CmcLtSpd spd, List<CmcLtTask> tasks){
		for (CmcLtTask task : tasks) {
			localTaskService.saveOrUpdate(task);
		}
		localTaskSpiderDao.saveOrUpdate(spd);
	}
}
