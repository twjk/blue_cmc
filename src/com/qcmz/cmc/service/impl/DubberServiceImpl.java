package com.qcmz.cmc.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.dao.IDubberDao;
import com.qcmz.cmc.entity.CmcDubber;
import com.qcmz.cmc.service.IDubberService;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.StringUtil;

@Service
public class DubberServiceImpl implements IDubberService {
	@Autowired
	private IDubberDao dubberDao;
	
	/**
	 * 分页查询数据
	 * @param map
	 * @param pageBean<CmcDubber>
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean){
		dubberDao.queryByMapTerm(map, pageBean);
	}
	
	/**
	 * 获取有效的配音员
	 * @param catId
	 * @return
	 */
	public List<CmcDubber> findValidDubber(Long catId){
		return dubberDao.findValidDubber(catId);
	}
	
	/**
	 * 获取配音员
	 * @param dubberId
	 */
	public CmcDubber getDubber(Long dubberId){
		return (CmcDubber) dubberDao.load(CmcDubber.class, dubberId);
	}
	
	/**
	 * 保存配音员
	 * @param bean
	 */
	public void saveOrUpdateDubber(CmcDubber bean){
		Long catId = null;
		if(StringUtil.notBlankAndNull(bean.getFullcatid())){
			catId = Long.valueOf(StringUtil.right(bean.getFullcatid(), 4));
		}
		bean.setCatid(catId);
		bean.setItemid(8L);
		
		if(bean.getDubberid()==null){
			//添加
			dubberDao.save(bean);
		}else{
			//修改
			CmcDubber entity = getDubber(bean.getDubberid());
			entity.setCatid(bean.getCatid());
			entity.setFullcatid(bean.getFullcatid());
			entity.setTitle(bean.getTitle());
			entity.setFullname(bean.getFullname());
			entity.setGender(bean.getGender());
			entity.setIcon(bean.getIcon());
			entity.setSpecialty(bean.getSpecialty());
			entity.setStyle(bean.getStyle());
			entity.setAudition(bean.getAudition());
			entity.setSortindex(bean.getSortindex());
			entity.setStatus(bean.getStatus());
			
			dubberDao.update(entity);
		}
	}
	
	/**
	 * 更新状态
	 * @param dubberId
	 * @param status
	 */
	public void updateStatus(Long dubberId, Integer status){
		dubberDao.updateStatus(dubberId, status);
	}
}
