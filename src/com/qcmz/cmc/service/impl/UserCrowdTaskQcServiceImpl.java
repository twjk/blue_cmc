package com.qcmz.cmc.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.dao.IUserCrowdTaskQcDao;
import com.qcmz.cmc.entity.CmcCtQc;
import com.qcmz.cmc.service.IUserCrowdTaskQcService;

@Service
public class UserCrowdTaskQcServiceImpl implements IUserCrowdTaskQcService {
	@Autowired
	private IUserCrowdTaskQcDao userCrowdTaskQcDao;
	
	/**
	 * 获取用户任务审校列表
	 * @param utId
	 * @param qcStatus
	 * @return
	 */
	public List<CmcCtQc> findQc(String utId, Integer qcStatus){
		return userCrowdTaskQcDao.findQc(utId, qcStatus);
	}
	
	/**
	 * 获取待发放审校奖励的列表
	 * @return
	 */
	public List<Long> findQc4GrantQcReward(){
		return userCrowdTaskQcDao.findQc4GrantQcReward();
	}
	
	/**
	 * 获取超时未发放审校奖励的数量
	 * @return
	 */
	public Long getCount4UngrantQcReward(){
		return userCrowdTaskQcDao.getCount4UngrantQcReward();
	}
	
	/**
	 * 获取用户任务审校
	 * @param qcId
	 * @return
	 */
	public CmcCtQc getQc(Long qcId){
		return (CmcCtQc) userCrowdTaskQcDao.load(CmcCtQc.class, qcId);
	}
	
	/**
	 * 获取用户任务审校
	 * @param utId not null
	 * @param qcStatus not null
	 * @return
	 */
	public CmcCtQc getQc(String utId, Integer qcStatus){
		return userCrowdTaskQcDao.getQc(utId, qcStatus);
	}
	
	/**
	 * 保存用户任务审校
	 * @param utId
	 * @param taskId
	 */
	public CmcCtQc saveQc(String utId, Long taskId){
		List<Integer> qcStatuses = new ArrayList<Integer>();
		qcStatuses.add(DictConstant.DICT_USERCROWDTASK_QCSTATUS_WAITING);
		qcStatuses.add(DictConstant.DICT_USERCROWDTASK_QCSTATUS_PROCESSING);
		CmcCtQc qc = userCrowdTaskQcDao.getQc(utId, qcStatuses);
		
		if(qc==null){
			qc = new CmcCtQc();
			qc.setUtid(utId);
			qc.setTaskid(taskId);
			qc.setQcfinishnum(0);
			qc.setQcstatus(DictConstant.DICT_USERCROWDTASK_QCSTATUS_WAITING);
			qc.setCreatetime(new Date());
			userCrowdTaskQcDao.save(qc);
		}

		return qc;
	}
	
	/**
	 * 更新
	 * @param qc
	 */
	public void updateQc(CmcCtQc qc){
		userCrowdTaskQcDao.update(qc);
	}
	
	/**
	 * 更新用户任务审校已审校题目数
	 * @param qcId
	 */
	public void updateQcFinishNum(Long qcId){
		userCrowdTaskQcDao.updateQcFinishNum(qcId);
	}
}
