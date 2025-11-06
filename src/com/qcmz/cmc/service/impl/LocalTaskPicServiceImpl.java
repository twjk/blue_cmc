package com.qcmz.cmc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.dao.ILocalTaskPicDao;
import com.qcmz.cmc.entity.CmcLtPic;
import com.qcmz.cmc.service.ILocalTaskPicService;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.util.StringUtil;

@Service
public class LocalTaskPicServiceImpl implements ILocalTaskPicService {
	@Autowired
	private ILocalTaskPicDao localTaskPicDao;
	
	/**
	 * 保存任务图片
	 * @param taskId
	 * @param pics
	 * @param update
	 */
	public void savePic(Long taskId, List<CmcLtPic> pics, boolean update){
		//清除已有图片
		if(update){
			localTaskPicDao.clearPic(taskId);
		}
		
		//重新入库
		if(pics==null || pics.isEmpty()) return;
		
		List<CmcLtPic> result = new ArrayList<CmcLtPic>();
		CmcLtPic bean = null;
		for (CmcLtPic pic : pics) {
			if(pic==null || StringUtil.isBlankOrNull(pic.getPicurl())){
				continue;
			}
			
			bean = new CmcLtPic();
			bean.setTaskid(taskId);
			bean.setPicurl(pic.getPicurl());		
			if(StringUtil.isBlankOrNull(pic.getThumburl())){
				bean.setThumburl(pic.getPicurl());
			}else{
				bean.setThumburl(pic.getThumburl());
			}
			
			if(pic.getSortindex()==null){
				bean.setSortindex(SystemConstants.SORTINDEX_DEFAULT);
			}else{
				bean.setSortindex(pic.getSortindex());
			}
			result.add(bean);
		}
		if(!result.isEmpty()){
			localTaskPicDao.saveOrUpdateAll(result);
		}
	}
	
	/**
	 * 清除任务图片
	 * @param taskId
	 */
	public void clearPic(Long taskId){
		localTaskPicDao.clearPic(taskId);
	}
}
