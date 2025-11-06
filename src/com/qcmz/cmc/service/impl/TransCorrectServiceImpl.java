package com.qcmz.cmc.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.dao.ITransCorrectDao;
import com.qcmz.cmc.entity.CmcTransCorrect;
import com.qcmz.cmc.service.ITransCorrectService;
import com.qcmz.framework.page.PageBean;

/**
 *
 */
@Service
public class TransCorrectServiceImpl implements ITransCorrectService {
	@Autowired
	private ITransCorrectDao transCorrectDao;

	/**
	 * 分页获取列表
	 * @param map
	 * @param pageBean
	 * 修改历史：
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean){
		transCorrectDao.queryByMapTerm(map, pageBean);
	}
	
	/**
	 * 获取修正翻译列表
	 * @param uuid
	 * @param minTime
	 * @return
	 */
	public List<CmcTransCorrect> findCorrectTrans(String uuid, Date minTime){
		return transCorrectDao.findCorrectTrans(uuid, minTime);
	}
}
