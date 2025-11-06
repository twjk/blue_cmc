package com.qcmz.cmc.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.qcmz.cmc.entity.CmcLtSpd;
import com.qcmz.framework.dao.IBaseDAO;
import com.qcmz.framework.page.PageBean;

public interface ILocalTaskSpiderDao extends IBaseDAO {
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
}
