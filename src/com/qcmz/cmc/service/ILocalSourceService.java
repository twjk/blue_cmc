package com.qcmz.cmc.service;

import java.util.List;

import com.qcmz.cmc.entity.CmcLtSource;

public interface ILocalSourceService {
	/**
	 * 获取所有来源
	 * @return
	 */
	public List<CmcLtSource> findSource();
}
