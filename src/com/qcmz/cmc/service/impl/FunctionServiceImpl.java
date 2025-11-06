package com.qcmz.cmc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.dao.IFunctionDao;
import com.qcmz.cmc.entity.CmcFunction;
import com.qcmz.cmc.service.IFunctionService;

/**
 * 类说明：功能维护
 * 修改历史：
 */
@Service
public class FunctionServiceImpl implements IFunctionService {
	@Autowired
	private IFunctionDao functionDao;
	/**
	 * 获取所有的功能列表
	 * @return
	 * 修改历史：
	 */
	public List<CmcFunction> findAll(){
		return functionDao.findAll();
	}
}
