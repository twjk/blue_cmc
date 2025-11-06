package com.qcmz.cmc.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.config.TransConfig;
import com.qcmz.cmc.dao.ITransLogDao;
import com.qcmz.cmc.entity.CmcTransLog;
import com.qcmz.cmc.service.ITransLogService;
import com.qcmz.cmc.vo.TransLogIdRange;

/**
 * 类说明：翻译日志
 * 修改历史：
 */
@Service
public class TransLogServiceImpl implements ITransLogService {
	
	@Autowired
	private ITransLogDao transLogDao;
	
	
	/**
	 * 翻译日志数据
	 */
	private List<CmcTransLog> transLogs = new ArrayList<CmcTransLog>();
	/**
	 * 翻译日志锁
	 */
	private ReentrantLock logLock = new ReentrantLock();
	
	/**
	 * 获取指定时间范围内最小和最大日志编号
	 * @param start
	 * @param end
	 * @return
	 * 修改历史：
	 */
	public TransLogIdRange getIdRange(Date start, Date end){
		return transLogDao.getIdRange(start, end);
	}
	
	/**
	 * 获取日志列表
	 * @param lastId
	 * @param maxId
	 * @param pageSize
	 * @return
	 * 修改历史：
	 */
	public List<CmcTransLog> findLog(Long lastId, Long maxId, int pageSize){
		return transLogDao.findLog(lastId, maxId, pageSize);
	}
	
	/**
	 * 分页获取日志列表
	 * @param from
	 * @param lastId
	 * @param pageSize
	 * @return
	 * 修改历史：
	 */
	public List<CmcTransLog> findLog(String from, Long lastId, int pageSize){
		return transLogDao.findLog(from, lastId, pageSize);
	}
	
	/**
	 * 保存翻译日志
	 * @param log
	 * 修改历史：
	 */
	public void addLogCache(CmcTransLog log){
		logLock.lock();
		try {
			transLogs.add(log);
			if(transLogs.size()>=TransConfig.TRANS_LOG_BATCHSIZE){
				try {
					transLogDao.saveOrUpdateAll(transLogs);
				} catch (Exception e) {
				}
				transLogs.clear();
			}
		} finally{
			logLock.unlock();
		}
	}
	
	/**
	 * 保存翻译日志
	 * @param log
	 * 修改历史：
	 */
	public void addLog(CmcTransLog log){
		transLogDao.save(log);
	}
	
	/**
	 * 删除日志
	 * @param minId
	 * @param maxId
	 * 修改历史：
	 */
	public void deleteLog(Long minId, Long maxId){
		transLogDao.deleteLog(minId, maxId);
	}
}
