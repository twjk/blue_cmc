package com.qcmz.cmc.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.qcmz.cmc.entity.CmcCtUseranswer;
import com.qcmz.cmc.entity.CmcCtUsersubject;
import com.qcmz.cmc.entity.CmcCtUsertask;
import com.qcmz.cmc.ws.provide.vo.UserCrowdTaskQcListBean;
import com.qcmz.cmc.ws.provide.vo.UserCrowdTaskQcQueryBean;
import com.qcmz.cmc.ws.provide.vo.UserCrowdTaskVerifyListBean;
import com.qcmz.cmc.ws.provide.vo.UserCrowdTaskVerifyQueryBean;
import com.qcmz.framework.dao.IBaseDAO;
import com.qcmz.framework.page.PageBean;

public interface IUserCrowdTaskDao extends IBaseDAO {
	/**
	 * 分页获取列表
	 * @param map
	 * @param pageBean<CmcCtUsertask，带CmcCtTask>
	 * 修改历史：
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean);
	/**
	 * 获取最近完成任务的用户编号
	 * @param taskId
	 * @param limit
	 * @return
	 */
	public List<Long> findFinishUserId(Long taskId, int limit);
	/**
	 * 分页获取用户任务列表，带任务信息
	 * @param userId
	 * @param pageSize
	 * @param lastId
	 * @return
	 */
	public List<CmcCtUsertask> findUserTaskJoin(Long userId, int pageSize, String lastId);
	/**
	 * 获取用户指定任务列表
	 * @param userId
	 * @param taskIds
	 * @return
	 */
	public List<CmcCtUsertask> findUserTask(Long userId, Set<Long> taskIds);
	/**
	 * 获取用户的用户任务
	 * @param userId
	 * @param statuses
	 * @return
	 */
	public List<CmcCtUsertask> findUserTask(Long userId, List<Integer> statuses);
	/**
	 * 分页获取用户任务列表
	 * @param taskId
	 * @param status
	 * @param minFinishTime
	 * @param maxFinishTime
	 * @param pageSize
	 * @param lastId
	 * @return
	 */
	public List<CmcCtUsertask> findUserTaskByTaskId(Long taskId, Integer status, Date minFinishTime, Date maxFinishTime, int pageSize, String lastId);
	/**
	 * 根据设备码获取用户任务编号列表
	 * @param taskId
	 * @param uuid
	 * @param status
	 * @return
	 */
	public List<String> findUserTaskByUuid(Long taskId, String uuid, Integer status);
	/**
	 * 分页获取用户任务报名审核列表
	 * @param query
	 * @param serviceType
	 * @param pageSize
	 * @return
	 */
	public List<UserCrowdTaskVerifyListBean> findUserTask4Verify(UserCrowdTaskVerifyQueryBean query, String serviceType, int pageSize);
	/**
	 * 获取提醒用户完成的用户任务列表
	 * @return
	 */
	public List<CmcCtUsertask> findUserTask4RemindToFinish();
	/**
	 * 分页获取用户任务审校列表
	 * @param query
	 * @param pageSize
	 * @return
	 */
	public List<UserCrowdTaskQcListBean> findUserTask4Qc(UserCrowdTaskQcQueryBean query, String serviceType, int pageSize);
	/**
	 * 获取待发放任务奖励的列表
	 * @return
	 */
	public List<String> findUserTask4GrantTaskReward();
	/**
	 * 获取待发放审核奖励的列表
	 * @return
	 */
	public List<String> findUserTask4GrantVerifyReward();
	/**
	 * 获取用户任务数
	 * @param userId
	 * @param statuses
	 * @return
	 */
	public Long getCount(Long userId, List<Integer> statuses);
	/**
	 * 根据设备码获取用户任务数
	 * @param taskId
	 * @param uuid
	 * @return
	 */
	public Long getCountByUuid(Long taskId, String uuid);
	/**
	 * 获取审核数量
	 * @param serviceType not null
	 * @param verifyStatus not null
	 * @param verifyUserId
	 * @return
	 */
	public Long getVerifyCount(String serviceType, Integer verifyStatus, Long verifyUserId);
	/**
	 * 获取审校数量
	 * @param serviceType not null
	 * @param qcStatus not null
	 * @param qcUserId
	 * @return
	 */
	public Long getQcCount(String serviceType, Integer qcStatus, Long qcUserId);
	/**
	 * 获取超时未发放任务奖励的数量
	 * @return
	 */
	public Long getCount4UngrantTaskReward();
	/**
	 * 获取超时未发放审核奖励的数量
	 * @return
	 */
	public Long getCount4UngrantVerifyReward();
	/**
	 * 获取用户任务，带任务信息
	 * @param utId
	 * @return
	 */
	public CmcCtUsertask getUserTaskJoin(String utId);
	/**
	 * 获取用户任务
	 * @param userId
	 * @param taskId
	 * @return
	 */
	public CmcCtUsertask getUserTask(Long userId, Long taskId);
	/**
	 * 用户是否已参与同组任务（已取消的不算）
	 * @param userId not null
	 * @param groupCode not null
	 * @return
	 */
	public boolean isPartInGroupTask(Long userId, String groupCode);
	/**
	 * 获取新的用户任务编号
	 * @return
	 * 修改历史：
	 */
	public String newUtId();
	/**
	 * 更新国家和城市
	 * @param utId
	 * @param countryName
	 * @param cityName
	 */
	public void updateCountryAndCity(String utId, String countryName, String cityName);
	/**
	 * 更新用户任务题目数
	 * @param utId
	 */
	public void updateSubjectNum(String utId);
	/**
	 * 更新用户任务已完成题目数
	 * @param utId
	 */
	public void updateFinishSubjectNum(String utId);
	/**
	 * 更新用户奖励到账状态
	 * @param utId
	 * @param utRewardStatus
	 */
	public void updateUtRewardStatus(String utId, Integer utRewardStatus);
	/**
	 * 更新用户奖励
	 * @param utId
	 */
	public void updateUtReward(String utId);
	/**
	 * 获取用户任务题目列表
	 * @param utId not null
	 * @param taskId not null
	 * @param subjectIds
	 * @return
	 */
	public List<CmcCtUsersubject> findUserSubject(String utId, Long taskId, List<Long> subjectIds);
	/**
	 * 获取用户任务题目列表，带CmcCtSubject
	 * @param utId
	 * @param taskId
	 * @param qcId
	 * @param qcStatuses
	 * @return
	 */
	public List<CmcCtUsersubject> findUserSubjectJoin(String utId, Long taskId, Long qcId, List<Integer> qcStatuses);
	/**
	 * 获取用户任务题目编号列表
	 * @param utId not null
	 * @param taskId not null
	 * @param subjectIds
	 * @param answerStatus
	 * @return
	 */
	public List<Long> findUserSubjectId(String utId, Long taskId, List<Long> subjectIds, Integer answerStatus);
	/**
	 * 获取待发放任务奖励的用户题目列表
	 * @return
	 */
	public List<Long> findUserSubject4GrantTaskReward();
	/**
	 * 获取用户题目
	 * @param utId
	 * @param subjectId
	 * @return
	 */
	public CmcCtUsersubject getUserSubject(String utId, Long subjectId);
	/**
	 * 获取用户题目，获取用户题目，带题目、用户任务、任务
	 * @param usId
	 * @return
	 */
	public CmcCtUsersubject getUserSubjectJoin(Long usId);
	/**
	 * 获取用户任务最大题目编号
	 * @param utId
	 * @return
	 */
	public Long getMaxSubjectId(String utId);
	/**
	 * 获取用户题目数
	 * @param utId
	 * @param qcStatus
	 * @return
	 */
	public Long getUserSubjectCount(String utId, Integer qcStatus);
	/**
	 * 更新用户任务题目待审校
	 * @param utId
	 * @param taskId
	 */
	public void updateSubjectWaitQc(String utId, Long taskId);
	/**
	 * 更新用户任务题目得分
	 * @param utId
	 * @param subjectId
	 * @param score
	 */
	public void updateSubjectScore(String utId, Long subjectId, Integer score);
	/**
	 * 更新用户任务题目奖励到账状态
	 * @param utId
	 * @param qcId
	 * @param rewardStatus
	 */
	public void updateSubjectRewardStatus(String utId, Long qcId, Integer rewardStatus);
	/**
	 * 获取用户任务答案列表
	 * @param utId
	 * @param taskId
	 * @param subjectIds
	 * @param qcId
	 * @return
	 */
	public List<CmcCtUseranswer> findUserAnswer(String utId, Long taskId, List<Long> subjectIds, Long qcId);
	/**
	 * 获取用户任务答案列表
	 * @param utId
	 * @param taskId
	 * @return
	 */
	public List<CmcCtUseranswer> findUserAnswerJoinSubject(String utId, Long taskId);
	/**
	 * 获取任务的用户任务答案列表，带CmcCtUsertask
	 * @param taskId not null
	 * @param taskStatus
	 * @return
	 */
	public List<CmcCtUseranswer> findUserAnswerJoin(Long taskId, Integer taskStatus);
	/**
	 * 分页获取要删除文件的用户任务答案列表
	 * 自动取消、公司任务、已完成/已取消、指定完成/取消日期
	 * @param date 完成/取消日期 not null
	 * @param pageSize not null
	 * @param lastAnswerId
	 * @return
	 */
	public List<CmcCtUseranswer> findUserAnswer4DeleteFile(Date date, int pageSize, Long lastAnswerId);
	/**
	 * 获取用户任务答案信息，带CmcCtUsertask
	 * @param uaid
	 * @return
	 */
	public CmcCtUseranswer getUserAnswerJoin(Long uaid);
	/**
	 * 清除用户题目答案
	 * @param utId
	 * @param subjectIds
	 */
	public void clearAnswer(String utId, Set<Long> subjectIds);
	/**
	 * 更新用户任务答案得分
	 * @param uaId
	 * @param score
	 */
	public void updateAnswerScore(Long uaId, Integer score);
	/**
	 * 批量取消指定任务未完成的用户任务
	 * @param taskId not null
	 * @param reason 取消原因
	 * @param daysAgo 多少天之前申请
	 * @param operCd 操作人账号
	 * @param operName 操作人姓名
	 */
	public int cancelByTaskId(Long taskId, String reason, Integer daysAgo, String operCd, String operName);
	/**
	 * 批量取消指定用户未完成的用户任务
	 * @param userId
	 * @param reason
	 * @param operCd
	 * @param operName
	 * @return
	 */
	public int cancelByUserId(Long userId, String reason, String operCd, String operName);
}
