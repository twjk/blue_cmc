package com.qcmz.cmc.dao;

import java.util.Date;
import java.util.List;

import com.qcmz.cmc.entity.CmcTransLog;
import com.qcmz.cmc.vo.TransLogIdRange;
import com.qcmz.framework.dao.IBaseDAO;

/**
 * 类说明：翻译日志
 * 修改历史：
 */
public interface ITransLogDao extends IBaseDAO {
	/**
	 * 获取指定时间范围内最小和最大日志编号
	 * @param start
	 * @param end
	 * @return
	 * 修改历史：
	 */
	public TransLogIdRange getIdRange(Date start, Date end);
	/**
	 * 分页获取日志列表
	 * @param lastId
	 * @param maxId
	 * @param pageSize
	 * @return
	 * 修改历史：
	 */
	public List<CmcTransLog> findLog(Long lastId, Long maxId, int pageSize);
	/**
	 * 分页获取日志列表
	 * @param from
	 * @param lastId
	 * @param pageSize
	 * @return
	 * 修改历史：
	 */
	public List<CmcTransLog> findLog(String from, Long lastId, int pageSize);
	/**
	 * 删除日志
	 * @param minId
	 * @param maxId
	 * 修改历史：
	 */
	public void deleteLog(Long minId, Long maxId);
}
