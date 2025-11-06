package com.qcmz.cmc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.dao.ILocalSourceDao;
import com.qcmz.cmc.entity.CmcLtSource;
import com.qcmz.cmc.service.ILocalSourceService;

@Service
public class LocalSourceServiceImpl implements ILocalSourceService {
	@Autowired
	private ILocalSourceDao localSourceDao;
	
	/**
	 * 获取所有来源
	 * @return
	 */
	public List<CmcLtSource> findSource(){
		return localSourceDao.findSource();
	}
}
