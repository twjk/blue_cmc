package com.qcmz.cmc.service;

import java.util.List;

import com.qcmz.cmc.entity.CmcCombo;

public interface ITransComboService {
	/**
	 * 获取所有套餐列表，带套餐包、图片、限定语言、限定场景
	 * @return
	 */
	public List<CmcCombo> findComboJoin();
}
