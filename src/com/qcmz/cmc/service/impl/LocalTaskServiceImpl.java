package com.qcmz.cmc.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.cache.LocalSourceMap;
import com.qcmz.cmc.cache.RewardActivityMap;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.dao.ILocalTaskDao;
import com.qcmz.cmc.dao.ILocalTaskPicDao;
import com.qcmz.cmc.entity.CmcLtTask;
import com.qcmz.cmc.service.ILocalCompanyService;
import com.qcmz.cmc.service.ILocalTaskPicService;
import com.qcmz.cmc.service.ILocalTaskService;
import com.qcmz.cmc.util.CmcUtil;
import com.qcmz.cmc.util.LocalTaskUtil;
import com.qcmz.cmc.vo.LocalTaskPageSearchBean;
import com.qcmz.cmc.vo.LocalTastSearchBean;
import com.qcmz.cmc.ws.provide.vo.LocalZhaopinJobQueryBean;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.StringUtil;

@Service
public class LocalTaskServiceImpl implements ILocalTaskService {
	@Autowired
	private ILocalTaskDao localTaskDao;
	@Autowired
	private ILocalTaskPicDao localTaskPicDao;
	@Autowired
	private ILocalTaskPicService localTaskPicService;
	@Autowired
	private ILocalCompanyService localCompanyService;
	@Autowired
	private RewardActivityMap rewardActivityMap;
	@Autowired
	private LocalSourceMap localSourceMap;
	
	/**
	 * 分页查询数据
	 * @param map
	 * @param pageBean<CmcLtTask带CmcLtCompany/CmcRewardActivity>
	 */
	@SuppressWarnings("unchecked")
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean){
		localTaskDao.queryByMapTerm(map, pageBean);
		
		List<CmcLtTask> list = (List<CmcLtTask>) pageBean.getResultList();
		for (CmcLtTask entity : list) {
			if(entity.getActid()!=null){
				entity.setActivity(rewardActivityMap.getActivity(entity.getActid()));
			}
		}
	}
	
	/**
	 * 分页获取有效的同城任务列表
	 * @param search
	 * @return
	 */
	public List<CmcLtTask> findTask(LocalTaskPageSearchBean search){
		return localTaskDao.findTask(search);
	}
	
	/**
	 * 分页获取同城任务列表
	 * @param pageSize
	 * @param lastTaskId
	 * @return
	 */
	public List<CmcLtTask> findTask(int pageSize, Long lastTaskId){
		return localTaskDao.findTask(pageSize, lastTaskId);
	}
	
	/**
	 * 获取用户的同城任务列表
	 * @param search
	 * @return
	 */
	public List<CmcLtTask> findTask(LocalZhaopinJobQueryBean search){
		return localTaskDao.findTask(search);
	}
	
	/**
	 * 获取同城任务
	 * @param taskId
	 */
	public CmcLtTask getTask(Long taskId){
		return (CmcLtTask) localTaskDao.load(CmcLtTask.class, taskId);
	}
	
	/**
	 * 获取同城任务，带图片、公司、来源、活动
	 * @param taskId
	 */
	public CmcLtTask getTaskJoin(Long taskId){
		CmcLtTask task = getTask(taskId);
		if(task==null) return task;
		
		if(task.getCompanyid()!=null){
			task.setCmcLtCompany(localCompanyService.getCompany(task.getCompanyid()));
		}
		task.setCmcLtPics(localTaskPicDao.findPic(taskId));
		if(task.getSourceid()!=null){
			task.setCmcLtSource(localSourceMap.getSource(task.getSourceid()));
		}
		if(task.getActid()!=null){
			task.setActivity(rewardActivityMap.getActivity(task.getActid()));
		}
		return task;
	}
	
	/**
	 * 获取同城任务，带公司
	 * @param taskId
	 */
	public CmcLtTask getTaskJoinCompany(Long taskId){
		CmcLtTask task = getTask(taskId);
		if(task==null) return null;
		
		if(task.getCompanyid()!=null){
			task.setCmcLtCompany(localCompanyService.getCompany(task.getCompanyid()));
		}
		return task;
	}
	
	/**
	 * 获取有效的同城任务数
	 * @param search
	 * @return
	 */
	public Long getTaskCount(LocalTastSearchBean search){
		return localTaskDao.getTaskCount(search);
	}
	
	/**
	 * 查重
	 * @param taskId
	 * @param companyId
	 * @param title
	 * @return
	 */
	public boolean isRepeat(Long taskId, Long companyId, String title){
		return localTaskDao.isRepeat(taskId, companyId, title);
	}
	
	/**
	 * 保存同城任务
	 * @param bean
	 * @exception DataException
	 */
	public void saveOrUpdate(CmcLtTask bean){
		if(localTaskDao.isRepeat(bean.getTaskid(), bean.getCompanyid(), bean.getTitle())){
			throw new DataException("公司岗位已经存在");
		}
		
		if(StringUtil.isBlankOrNull(bean.getCityname())||bean.getCityid()==null){
			bean.setCityid(0L);
			bean.setCityname("");
		}
		
		boolean update = bean.getTaskid()!=null;
		Long taskId = bean.getTaskid();
		Integer refReward = LocalTaskUtil.calRefReward(bean.getMinreward(), bean.getMaxreward(), bean.getRewardtype());
		
		Date now = new Date();
		if(bean.getTaskid()==null){
			//添加
			bean.setCreatetime(now);
			if(DictConstant.DICT_LOCALTASK_STATUS_ON.equals(bean.getStatus()) 
					&& bean.getPublishtime()==null){
				bean.setPublishtime(now);
			}
			bean.setRefreward(refReward);
			localTaskDao.save(bean);
			
			bean.setSortquery(CmcUtil.getSortQuery(bean.getSortindex(), bean.getTaskid()));
			localTaskDao.update(bean);
			taskId = bean.getTaskid();
		}else{
			//修改
			CmcLtTask entity = getTask(bean.getTaskid());
			entity.setTitle(bean.getTitle());
			entity.setWorktimetype(bean.getWorktimetype());
			entity.setSourceid(bean.getSourceid());
			entity.setReward(bean.getReward());
			entity.setRewardtype(bean.getRewardtype());
			entity.setMinreward(bean.getMinreward());
			entity.setMaxreward(bean.getMaxreward());
			entity.setRefreward(refReward);
			entity.setCompanyid(bean.getCompanyid());
			entity.setContact(bean.getContact());
			entity.setCityid(bean.getCityid());
			entity.setCityname(bean.getCityname());
			entity.setAddress(bean.getAddress());
			entity.setLon(bean.getLon());
			entity.setLat(bean.getLat());
			entity.setExp(bean.getExp());
			entity.setMinexp(bean.getMinexp());
			entity.setEdu(bean.getEdu());
			entity.setMinedu(bean.getMinedu());
			entity.setPeoplenum(bean.getPeoplenum());
			entity.setJob(bean.getJob());
			entity.setJobrequire(bean.getJobrequire());
			entity.setLink(bean.getLink());
			entity.setSortindex(bean.getSortindex());
			entity.setActid(bean.getActid());
			
			if(DictConstant.DICT_LOCALTASK_STATUS_ON.equals(bean.getStatus())){
				if(!DictConstant.DICT_LOCALTASK_STATUS_ON.equals(entity.getStatus()) || entity.getPublishtime()==null){
					entity.setPublishtime(now);
				}
			}else{
				entity.setPublishtime(null);
			}
			
			entity.setSortquery(CmcUtil.getSortQuery(entity.getSortindex(), entity.getTaskid()));
			entity.setStatus(bean.getStatus());
			localTaskDao.update(entity);
		}
		
		//图片
		localTaskPicService.savePic(taskId, bean.getCmcLtPics(), update);
	}
	
	/**
	 * 更新入库
	 * @param entity
	 */
	public void update(CmcLtTask entity){
		localTaskDao.update(entity);
	}
	
	/**
	 * 删除
	 * @param id
	 */
	public void delete(Long id){
		localTaskPicService.clearPic(id);
		localTaskDao.delete(CmcLtTask.class, id);
	}
	
	/**
	 * 更新状态
	 * @param taskId
	 * @param status
	 */
	public void updateStatus(Long taskId, Integer status){
		CmcLtTask entity = getTask(taskId);
		if(DictConstant.DICT_LOCALTASK_STATUS_ON.equals(status)){
			if(!DictConstant.DICT_LOCALTASK_STATUS_ON.equals(entity.getStatus()) 
					|| entity.getPublishtime()==null){
				entity.setPublishtime(new Date());
			}
		}else{
			entity.setPublishtime(null);
		}
		entity.setStatus(status);
		
		localTaskDao.update(entity);
	}
}
